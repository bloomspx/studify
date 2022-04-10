package com.example.studify.views;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    ArrayList<String> tasks = new ArrayList<String>();
    ArrayList<String> times = new ArrayList<String>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTaskListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        String[] s = {"Hello", "World","yes"};
        String[] t = {"2", "3","4"};
        recycleView = binding.tasksRecyclerView;
        TaskAdapter adapter = new TaskAdapter(getActivity(), s, t);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        int id = view.getId();


        }





    // Navigator Instantiation
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}