package com.example.studify.models;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.studify.models.UserProfileModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserAppRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore firebaseFirestore;
    private UserProfileModel userProfileModel;
    private MutableLiveData<UserProfileModel> userProfileLiveData;
    private String TAG = "USER AUTHENTICATION";
    private String UserID;

    public UserAppRepository(Application application) {
        this.application = application;

        // Authenticate User
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userProfileLiveData = new MutableLiveData<>();

        // If user is logged in, retrieve UserProfileLiveData
        if (firebaseAuth.getCurrentUser() != null) {
            getUserDetails();
        }
    }

    // Updates user data to FireStore Database & FireBase Auth
    public void updateProfile(UserProfileModel changedUser) {
        user = firebaseAuth.getInstance().getCurrentUser();
        // Update User Credentials via FireStore
        DocumentReference documentReference = firebaseFirestore.collection("users").document(user.getUid());
        Map<String, Object> data = new HashMap<>();
        if (changedUser.getName() == null) {
            changedUser.setName(user.getDisplayName());
        }
        if (changedUser.getImg() == null) {
            changedUser.setImg(user.getPhotoUrl().toString());
        }
        data.put("username", changedUser.getName());
        data.put("email", user.getEmail());
        data.put("photoUri",changedUser.getImg());
        documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: userProfileModel is updated for " + user.getUid());
            }
        });

        // Update User Credentials via FirebaseAuth
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(changedUser.getName())
                .setPhotoUri(Uri.parse(changedUser.getImg()))
                .build();
        firebaseAuth.getCurrentUser().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(application, "User Profile Updated.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(application, "User Profile Update Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    //Obtains UserProfileModel from Firestore and pushes to LiveData
    private void getUserDetails() {
        user = firebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UserID = user.getUid();
            DocumentReference docRef = firebaseFirestore.collection("users").document(UserID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot userDocument = task.getResult();
                        if (userDocument.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + userDocument.getData());
                            String name = userDocument.getString("username");
                            String photoUri = userDocument.getString("photoUri");
                            String email = userDocument.getString("email");
                            userProfileModel = new UserProfileModel.Builder()
                                    .setName(name)
                                    .setImg(photoUri)
                                    .setEmail(email)
                                    .build();
                            userProfileLiveData.postValue(userProfileModel);
                            Log.d(TAG, "UserProfileModel data: " + getUserProfileLiveData());
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    public MutableLiveData<UserProfileModel> getUserProfileLiveData() { return userProfileLiveData;}
}
