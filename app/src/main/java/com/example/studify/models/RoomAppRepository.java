package com.example.studify.models;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomAppRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore firebaseFirestore;
    private FieldValue FieldValue;
    private FirebaseFirestore db;
    private RoomModel room;
    //private Boolean roomExists;
    private String TAG = "ROOM";

    public RoomAppRepository(Application application) {
        this.application = application;
        //roomExists = false;

        // Authenticate User
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }
    public void createRoom()
    {
        System.out.println("*****************Hey I'm Here-3");
        db = FirebaseFirestore.getInstance();
        ArrayList<String> User_IDs = new ArrayList<String>();
        User_IDs.add(firebaseAuth.getCurrentUser().getUid());
        room = new RoomModel();
        room.setUser_IDs(User_IDs);
        String roomID = room.getRoomID();

        db.collection("rooms").document(roomID).set(room);

    }
    public void joinRoom(String ID) {
        System.out.println("Welcome to HELL!");
        String roomID = ID;
        //Boolean roomExists = false;
        System.out.println("roomID:" + roomID);
        db = FirebaseFirestore.getInstance();
        db.collection("rooms").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        System.out.println("Sucker!");
                        System.out.println("Document ID:" + document.getId());

                        if (roomID.equals(document.getId())) {
                            db.collection("rooms").document(document.getId()).update("user_IDs", FieldValue.arrayUnion(firebaseAuth.getCurrentUser().getUid()));
                            db.collection("rooms").document(document.getId()).update("roomUserCount", FieldValue.increment(1));





                        }
                        else {
                            Toast.makeText(application, "Incorrect ID", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
        /*
        if(roomExists==true){
            return true;
        }
        else
        {
            return false;
        }

         */
    }
        /*
         db.collection("rooms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (roomID == document.getId()) {

                                }

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        }
                            else {
                                Log.d(TAG, "Error getting documents: ", task.getException());

                            }

                    }
                });

         */





    public void addTask(String taskName) {


    }

    public void updateTask(String taskName) {

    }


}
