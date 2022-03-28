package com.example.studify.models;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RoomAppRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "ROOM";

    public RoomAppRepository(Application application) {
        this.application = application;

        // Authenticate User
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    public void addTask(String taskName) {


    }

    public void updateTask(String taskName) {

    }


}
