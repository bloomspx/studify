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
    private AuthAppRepository authAppRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;
    private MutableLiveData<String> timerLeftLiveData;
    private MutableLiveData<String> groupTimerLeftLiveDate;

    private TimerRepository timerRepository;
    private GroupTimeRepository groupTimeRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        authAppRepository = new AuthAppRepository(application);
        userLiveData = authAppRepository.getUserMutableLiveData();
        loggedOutLiveData = authAppRepository.getLoggedOutLiveData();
        timerRepository = new TimerRepository(application);
        groupTimeRepository = new GroupTimeRepository(application);
        timerLeftLiveData = timerRepository.getTimeLeftLiveData();

        groupTimerLeftLiveDate = groupTimeRepository.getTimeLeftLiveData();

    }
    public void logOut() { authAppRepository.logOut();}

//    public void changeEmail(String email) { authAppRepository.changeEmail(email); }
//
//    public void changePassword(String password) { authAppRepository.changePassword(password); }

    public void deleteProfile()  {authAppRepository.deleteProfile();}

    public void startCountdown(int count){timerRepository.startStop(count);}

    public void joinRoom(String roomID){groupTimeRepository.join(roomID);}

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    public MutableLiveData<String> getTimerLeftLiveData() {
        return timerLeftLiveData;
    }


    public  MutableLiveData<String> getGroupTimerLeftLiveDate(){return groupTimerLeftLiveDate;}

}