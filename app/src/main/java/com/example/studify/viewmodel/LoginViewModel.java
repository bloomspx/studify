package com.example.studify.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.studify.models.AuthAppRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends AndroidViewModel {
    private AuthAppRepository authAppRepository;
    private MutableLiveData<FirebaseUser> userLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        authAppRepository = new AuthAppRepository(application);
        userLiveData = authAppRepository.getUserMutableLiveData();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void login(String email, String password) {
        authAppRepository.login(email, password);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(String name, String email, String password) {
        authAppRepository.register(name, email, password); }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void resetPassword(String email) {
        authAppRepository.resetPassword(email);
    }


    // Getter function to export LiveData to the View
    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
}
