package com.example.studify.views;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studify.R;
import com.example.studify.databinding.FragmentRoomListBinding;
import com.example.studify.databinding.FragmentTaskListBinding;
import com.example.studify.viewmodel.UserViewModel;
import java.util.ArrayList;

public class TaskListFragment extends Fragment implements View.OnClickListener {
    private FragmentTaskListBinding binding;
    RecyclerView recycleView;
    private NavController navController;
    TaskAdapter adapter;
    ArrayList<String> tasks = new ArrayList<String>();
    ArrayList<String> times = new ArrayList<String>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTaskListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        tasks.add("Sample Task");
        times.add("3");

        recycleView = binding.tasksRecyclerView;
        adapter = new TaskAdapter(getActivity(), tasks, times);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        int id = view.getId();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.addButton.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == binding.addButton.getId()) {
            System.out.println("yes");
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            int height = Resources.getSystem().getDisplayMetrics().heightPixels;

            View popupView = getLayoutInflater().inflate(R.layout.add_task_popup, null);
            PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            //close the popup window when cross is clicked
            ImageButton close = popupView.findViewById(R.id.closeButton);
            Button add = popupView.findViewById(R.id.addTaskButton);
            close.setOnClickListener(new View.OnClickListener() {
                public void onClick(View popupView) {
                    popupWindow.dismiss();
                }
            });
            add.setOnClickListener(new View.OnClickListener(){
                public void onClick(View popupView) {
                    EditText taskName = (EditText) popupWindow.getContentView().findViewById(R.id.taskNameOption);
                    EditText loopNo = (EditText) popupWindow.getContentView().findViewById(R.id.loopNumberOption);
                    String taskString = taskName.getText().toString();
                    String loopString = loopNo.getText().toString();
                    tasks.add(taskString);
                    times.add(loopString);
                    adapter.notifyItemInserted(tasks.size() - 1);
                    popupWindow.dismiss();
                }
            });

        }

        }


    // Navigator Instantiation
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}