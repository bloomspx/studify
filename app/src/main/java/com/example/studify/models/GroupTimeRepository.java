package com.example.studify.models;


import android.app.Application;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GroupTimeRepository {
    private Application application;
    private CountDownTimer countDownTimer;
    private CountDownTimer breakcountDownTimer;
    private long timeLeftInMilliseconds = 10000;
    private long breaktimeLeftInMilliseconds = 5000;
    private RoomModel room;


    private String timeLeftText;
    private MutableLiveData<String> timeLeftLiveData;
    private MutableLiveData<Boolean> isRunning;
    private int count = 1;
    private Boolean isBreak = false;
    private Boolean isFirsttime = true;
    private FirebaseFirestore db;

    public GroupTimeRepository(Application application){
        this.application = application;
        timeLeftLiveData = new MutableLiveData<>();
        isRunning = new MutableLiveData<>();
        db = FirebaseFirestore.getInstance();
        room = new RoomModel();

    }

    public void join(String RoomID){
        DocumentReference docRef = db.collection("rooms").document(RoomID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("Doument Data :" + document.getData());
                        room.setLoop(Integer.parseInt(document.get("loop").toString()));
                        room.setRoomID(document.get("roomID").toString());
                        if(document.get("startTime") != null)
                        {
                            room.setStartTime(document.get("startTime").toString());
                            System.out.println(room.getRoomID());
                            System.out.println(System.currentTimeMillis());
                            System.out.println(room.getStartTime());
                            long currentTime = System.currentTimeMillis();
                            long startTime = Long.parseLong(room.getStartTime());
                            int finishedCount = (int)(currentTime - startTime) / 1500000;
                            count = room.getLoop() - finishedCount;
                            timeLeftInMilliseconds = 1500000- ((currentTime - startTime) % 1500000);
                            breaktimeLeftInMilliseconds = 300000;
                            while(count > 0){
                                //isRunning.postValue(true);
                                startTimer();
                                count = count -1;
                            }
                        }
                        else
                        //String currentTime = Long.toString(System.currentTimeMillis());
                        {
                            DocumentReference washingtonRef = db.collection("rooms").document("X0BnGnpkgifMHgldCYPA");

// Set the "isCapital" field of the city 'DC'
                            washingtonRef
                                    .update("startTime", System.currentTimeMillis())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            System.out.println("Success");
                                            breaktimeLeftInMilliseconds =300000;
                                            timeLeftInMilliseconds = 1500000;
                                            //startBreakTimer();// for waiting the group mate 5 min
                                            count = room.getLoop();
                                            while(count>0){
                                                startTimer();
                                                count = count -1;
                                            }

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            //Log.w(TAG, "Error updating document", e);
                                            System.out.println("Error");
                                        }
                                    });
                            //isRunning.postValue(true);



                        }

                    } else {
                        System.out.println("no document is found");
                    }
                } else {
                    System.out.println("Error");

                }
            }
        });

    }





    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();


            }

            @Override
            public void onFinish() {
                isBreak = true;
                breaktimeLeftInMilliseconds =300000;
                startBreakTimer();

            }


        }.start();

    }

    public void startBreakTimer() {
        breakcountDownTimer = new CountDownTimer(breaktimeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                breaktimeLeftInMilliseconds = l;
                updateBreakTimer();

            }

            @Override
            public void onFinish() {
                isBreak = false;
                timeLeftInMilliseconds = 1500000;
            }
        }.start();
    }

    public void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;


        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;
        timeLeftLiveData.postValue(timeLeftText);

    }

    public void updateBreakTimer() {
        int minutes = (int) breaktimeLeftInMilliseconds / 60000;
        int seconds = (int) breaktimeLeftInMilliseconds % 60000 / 1000;


        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;
        timeLeftLiveData.postValue(timeLeftText);

    }

    public int getCount() {
        return count;
    }

    public MutableLiveData<String> getTimeLeftLiveData() {
        return timeLeftLiveData;
    }
    public MutableLiveData<Boolean> getIsRunning() {
        return isRunning;
    }
}
