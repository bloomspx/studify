package com.example.studify.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studify.R;
import com.example.studify.databinding.DialogInputTaskBinding;
import com.example.studify.databinding.DialogUpdateTaskBinding;
import com.example.studify.databinding.FragmentTaskListBinding;
import com.example.studify.models.AddTaskModel;
import com.example.studify.models.RoomModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TaskListFragment extends Fragment implements View.OnClickListener {
    private @NonNull FragmentTaskListBinding binding;
    private com.example.studify.viewmodel.RoomViewModel RoomViewModel;
    private NavController navController;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String UserID;
    private RoomModel Room;

    private ProgressDialog loader;

    private String key = "";
    private String taskName;
    private String taskTime;

    //TaskListFragment()
    //{Room = new RoomModel();}
    public void setArguments (Bundle args)
    {
        Room = new RoomModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTaskListBinding.inflate(getLayoutInflater());
        //RoomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        //room = new ViewModelProvider(this).get(Room.class);
        RoomViewModel = new ViewModelProvider(this).get(com.example.studify.viewmodel.RoomViewModel.class);
        Room = new RoomModel();
        // Room = new ViewModelProvider(this).get(com.example.studify.models.Room.class)
        // Room =  new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(Room.class);
        return binding.getRoot();
    }


    public void onClick(View view) {
//        int id = view.getId();
//        if (id == binding.taskListCreateRoomButton.getId()) {
//            // tasksList = room.getTasks_Lists();
//            RoomViewModel.createRoom(Room);
//            Navigation.findNavController(view).navigate(R.id.action_taskListFragment_to_roomFragment);
////            RoomViewModel.createRoom(Room);
//        }

    }

    private void addTask() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getContext());
        DialogInputTaskBinding dialogBinding = DialogInputTaskBinding.inflate(getLayoutInflater());
        myDialog.setView(dialogBinding.getRoot());

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        dialogBinding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mTaskName = dialogBinding.taskNameHint.getText().toString().trim();
                String mTaskTime = dialogBinding.taskTimeHint.getText().toString().trim();
                String id = reference.push().getKey();

                if (TextUtils.isEmpty(mTaskName)) {
                    dialogBinding.taskNameHint.setError("Task Required");
                    return;
                }
                if (TextUtils.isEmpty(mTaskTime)) {
                    dialogBinding.taskTimeHint.setError("Time Required");
                    return;
                } else {
                    loader.setMessage("Adding your data");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    AddTaskModel model = new AddTaskModel(mTaskName, mTaskTime, id);
                    Room.add_tasks_lists(mTaskName);
                    System.out.println("**Add Task**");
                    System.out.println(Room.getTasks_Lists());

                    reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Task has been inserted successfully", Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(getContext(), "Failed: " + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void updateTask() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
        DialogUpdateTaskBinding dialogBinding = DialogUpdateTaskBinding.inflate(getLayoutInflater());
        myDialog.setView(dialogBinding.getRoot());

        final AlertDialog dialog = myDialog.create();

        final EditText mTaskName = dialogBinding.mEditTaskName;
        final EditText mTaskTime = dialogBinding.mEditTaskTime;

        mTaskName.setText(taskName);
        mTaskName.setSelection(taskName.length());

        mTaskTime.setText(taskTime);
//        mTaskTime.setSelection(taskTime.length());

        Button delButton = dialogBinding.deleteButton;
        Button updateButton = dialogBinding.updateButton;

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskName = mTaskName.getText().toString().trim();
                taskTime = mTaskTime.getText().toString().trim();

                AddTaskModel model = new AddTaskModel(taskName, taskTime, key);
                Room.add_tasks_lists(taskName);
                System.out.println("**Update Task**");
                System.out.println(Room.getTasks_Lists());

                reference.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Data has been updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            String err = task.getException().toString();
                            Toast.makeText(getContext(), "update failed "+err, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialog.dismiss();

            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Task deleted successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            String err = task.getException().toString();
                            Toast.makeText(getContext(), "Failed to delete task "+ err, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AddTaskModel> options = new FirebaseRecyclerOptions.Builder<AddTaskModel>()
                .setQuery(reference, AddTaskModel.class)
                .build();

        FirebaseRecyclerAdapter<AddTaskModel, MyViewHolder> adapter = new FirebaseRecyclerAdapter<AddTaskModel, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull final AddTaskModel model) {
                holder.setTask(model.getTask());
                holder.setTime(model.getTime());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        key = getRef(position).getKey();
                        taskName = model.getTask();
                        taskTime = model.getTime();

                        updateTask();
                    }
                });


            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieved_layout, parent, false);
                return new MyViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTask(String taskName) {
            TextView taskTextView = mView.findViewById(R.id.taskTv);
            taskTextView.setText(taskName);
        }

        public void setTime(String taskTime) {
            TextView timeTextView = mView.findViewById(R.id.timeTv);
            timeTextView.setText(taskTime);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        recyclerView = binding.taskListRecyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        loader = new ProgressDialog(getContext());

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        UserID = mUser.getUid();
        reference = FirebaseDatabase.getInstance("https://studify-eaed6-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("tasks").child(UserID);

        binding.taskListFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
        binding.taskListFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        binding.taskListCreateRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RoomViewModel.createRoom(Room);
                Navigation.findNavController(view).navigate(R.id.action_taskListFragment_to_roomFragment);
            }
        });
    }
}
