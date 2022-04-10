package com.example.studify.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.studify.models.AuthAppRepository;
import com.google.firebase.auth.FirebaseUser;

public class MainActivityViewModel extends AndroidViewModel {
    private AuthAppRepository authAppRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        authAppRepository = new AuthAppRepository(application);
        userLiveData = authAppRepository.getUserMutableLiveData();
        loggedOutLiveData = authAppRepository.getLoggedOutLiveData();

    }
    public void logOut() { authAppRepository.logOut();}

    //public void changeEmail(String email) { authAppRepository.changeEmail(email); }

    //public void changePassword(String password) { authAppRepository.changePassword(password); }

    public void deleteProfile()  {authAppRepository.deleteProfile();}

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

}