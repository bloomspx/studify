package com.example.studify.views;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.studify.databinding.FragmentMainBinding;
import com.example.studify.databinding.FragmentRoomBinding;
import com.example.studify.databinding.FragmentPomodoroBinding;
import com.example.studify.viewmodel.MainActivityViewModel;
import com.example.studify.viewmodel.RoomViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.lang.reflect.Array;
import java.util.ArrayList;

import io.grpc.internal.JsonUtil;

public class PomodoroFragment extends Fragment {
    private @NonNull FragmentPomodoroBinding binding;
    // private MainActivityViewModel MainActivityViewModel;
    private RoomViewModel roomViewModel;
    private NavController navController;
    private String roomID = "";
    private ArrayList<String> task_list = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPomodoroBinding.inflate(getLayoutInflater());
        System.out.println("IN POMODORO FRAGMENT");
        //Todo : Import the display data from firebase.

        //MainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        System.out.println("In Room Fragment");
        getParentFragmentManager().setFragmentResultListener("RoomIDdata", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                roomID = result.getString("RoomID");
                //**********
                //roomID = "de9c6d16-aaa0-4f06-aeef-cdf8cb881a0d";
                //**********
                System.out.println(roomID.toString());
                roomViewModel.startGroupTimer(roomID);

            }

        });



        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        roomViewModel.getGroupTimerLeftLiveDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String timerLeft) {
                //binding.timer.setText(timerLeft);
            }
        });
    }


}