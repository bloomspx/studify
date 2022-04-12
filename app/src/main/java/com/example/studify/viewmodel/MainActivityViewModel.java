package com.example.studify.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.studify.models.AuthAppRepository;
import com.example.studify.models.GroupTimeRepository;
import com.example.studify.models.TimerRepository;
import com.google.firebase.auth.FirebaseUser;

public class MainActivityViewModel extends AndroidViewModel {


    private MutableLiveData<String> timerLeftLiveData;
    private MutableLiveData<Boolean>isFinished;

    private TimerRepository timerRepository;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        timerRepository = new TimerRepository(application);

        timerLeftLiveData = timerRepository.getTimeLeftLiveData();
        isFinished = timerRepository.getIsFinished();

    }

    public void startCountdown(int count){timerRepository.startStop(count);}

    public MutableLiveData<String> getTimerLeftLiveData() {
        return timerLeftLiveData;
    }
    public MutableLiveData<Boolean>getIsFinished(){return isFinished;}




}