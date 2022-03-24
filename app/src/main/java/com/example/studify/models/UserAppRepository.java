package com.example.studify.models;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserAppRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore firebaseFirestore;
    private UserProfile userProfile;
    private MutableLiveData<FirebaseUser> userLiveData;

    public UserAppRepository(Application application) {
        this.application = application;

        // Authenticate User
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userLiveData = new MutableLiveData<>();

    }

    // TODO: unfinished inprogress
    public UserProfile getUserDetails() {
        user = firebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            Uri photoUrl = user.getPhotoUrl();
            userProfile = new UserProfile.Builder()
                    .setName(name)
                    .setImg(photoUrl)
                    .build();
        }
        return userProfile;
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
}
