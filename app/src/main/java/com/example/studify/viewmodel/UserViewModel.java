package com.example.studify.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.studify.models.AuthAppRepository;
import com.example.studify.models.UserAppRepository;
import com.example.studify.models.UserProfile;

public class UserViewModel extends AndroidViewModel {
    private AuthAppRepository authAppRepository;
    private UserAppRepository userAppRepository;
    private MutableLiveData<Boolean> loggedOutLiveData;
    private final MutableLiveData<UserProfile> userProfileLiveData;

    // LoginViewModel used to hold LiveData for User data changes
    public UserViewModel(@NonNull Application application) {
        super(application);

        authAppRepository = new AuthAppRepository(application);
        userAppRepository = new UserAppRepository(application);
        loggedOutLiveData = authAppRepository.getLoggedOutLiveData();
        userProfileLiveData = userAppRepository.getUserProfileLiveData();;

    }

    // AuthAppRepository
    public void logOut() { authAppRepository.logOut();}

    public void deleteProfile()  {authAppRepository.deleteProfile();}

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    // UserAppRepository
    public void updateProfile(UserProfile user) { userAppRepository.updateProfile(user); }

    public MutableLiveData<UserProfile> getUserProfileLiveData() { return userProfileLiveData;}

}