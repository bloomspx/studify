package com.example.studify.views;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studify.R;
import com.example.studify.databinding.FragmentRoomAdminBinding;
import com.example.studify.models.RoomModel;
import com.example.studify.viewmodel.RoomViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RoomAdminFragment extends Fragment implements View.OnClickListener {
    private @NonNull FragmentRoomAdminBinding binding;
   // private MainActivityViewModel MainActivityViewModel;
    private RoomViewModel roomViewModel;
    private String dummy;
    private NavController navController;
    private String roomID = "";
    private ArrayList<String> task_list = null;
    private FirebaseFirestore db;
    private DocumentReference docRef;
    private ProgressDialog loader;
    private DatabaseReference reference;
    private CollectionReference reference_col;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String UserID;
    private ArrayList<String> tasks;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Adapter madapter;
    private DocumentSnapshot documentSnapshot;
    private RoomModel room;
    private FirebaseAuth firebaseAuth;
    private Integer room_user_count;


    public RoomAdminFragment() {

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.leaveRoom.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRoomAdminBinding.inflate(getLayoutInflater());
        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        System.out.println("In Room Fragment");
        getParentFragmentManager().setFragmentResultListener("RoomIDdata", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                roomID = result.getString("RoomID");
                System.out.println(roomID.toString());
                roomViewModel.startGroupTimer(roomID);
                db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("rooms").document(roomID);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        room = documentSnapshot.toObject(RoomModel.class);
                        tasks = room.getTasks_Lists();
                        room_user_count=room.getUser_IDs().size();
                        mRecyclerView = binding.roomRecyclerView;
                        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        System.out.println(room.getTasks_Lists());
                        madapter = new Adapter(tasks, getContext(),mRecyclerView);
                        mRecyclerView.setAdapter(madapter);
                        binding.RoomIDCard.setText("Room ID: " + roomID);
                    }
                });

            }

        });

        return binding.getRoot();
    }





    public void onClick(View view) {
        int id = view.getId();
        if (id == binding.leaveRoom.getId()) {
            System.out.println("Inside Leave Room");
            db = FirebaseFirestore.getInstance();
            // RoomViewModel.createRoom(room.getTasks_Lists());
            db.collection("rooms").document(roomID).update("user_IDs", FieldValue.arrayRemove(firebaseAuth.getCurrentUser().getUid()));
            Navigation.findNavController(view).navigate(R.id.action_roomFragment_to_roomListFragment);
        }

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

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        roomViewModel.getGroupTimerLeftLiveDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String timerLeft) {
                binding.timer.setText(timerLeft);
            }
        });
        binding.DeleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Inside Delete Room");
                db.collection("rooms").document(roomID).delete();
                System.out.println("Navigaion View");
                Navigation.findNavController(view).navigate(R.id.action_roomFragment_to_roomListFragment);

            }
        });


    }


}
