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

public class PomodoroFragment extends Fragment implements  View.OnClickListener {
    private @NonNull FragmentPomodoroBinding binding;
    // private MainActivityViewModel MainActivityViewModel;
    private MainActivityViewModel MainActivityViewModel;
    private int count =1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPomodoroBinding.inflate(getLayoutInflater());
        MainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.buttonStartCountdown.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == binding.buttonStartCountdown.getId()) {
            count = Integer.parseInt(binding.editCount.getText().toString());
            if(binding.buttonStartCountdown.getText() == "PAUSE"){
                binding.buttonStartCountdown.setText("START");
            }
            else{
                binding.buttonStartCountdown.setText("PAUSE");
            }

            MainActivityViewModel.startCountdown(count);

        }



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // LiveData Observer - if succesful, navigate back to AuthActivity
        MainActivityViewModel.getTimerLeftLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String timerLeft) {
                binding.textClock.setText(timerLeft);
            }
        });
        MainActivityViewModel.getIsFinished().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isFinished) {
                if(isFinished){
                    binding.textClock.setText("25:00");
                    binding.buttonStartCountdown.setText("Start");
                    binding.editCount.setText("");
                }
            }
        });

    }


}