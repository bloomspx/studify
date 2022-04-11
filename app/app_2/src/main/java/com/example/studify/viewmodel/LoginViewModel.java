package com.example.studify.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.studify.models.AuthAppRepository;
import com.example.studify.models.UserProfile;
import com.example.studify.views.MainActivity;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends AndroidViewModel {
    private AuthAppRepository authAppRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;

    // LoginViewModel used to hold LiveData for Login Authentication
    public LoginViewModel(@NonNull Application application) {
        super(application);
        authAppRepository = new AuthAppRepository(application);
        userLiveData = authAppRepository.getUserMutableLiveData();
        loggedOutLiveData = authAppRepository.getLoggedOutLiveData();

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void login(String email, String password) {
        authAppRepository.login(email, password);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(UserProfile user) {
        authAppRepository.register(user); }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void resetPassword(String email) {
        authAppRepository.resetPassword(email);
    }


    // Getter function to export LiveData to the View
    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }


}
