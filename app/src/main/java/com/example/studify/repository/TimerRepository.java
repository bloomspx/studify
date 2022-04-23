package com.example.studify.repository;

import android.app.Application;
import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

public class TimerRepository {
    private Application application;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 1500000;

    private boolean timerRunning;
    private String timeLeftText;
    private MutableLiveData<String> timeLeftLiveData;
    private MutableLiveData<Boolean> isFinished;
    private int count = 2;
    private Boolean isFirsttime = true;

    public TimerRepository(Application application){
        this.application = application;
        timeLeftLiveData = new MutableLiveData<>();
        isFinished = new MutableLiveData<>();

    }


    public void startStop(int countIn){
        if (isFirsttime){
            count = countIn * 2;
            isFinished.postValue(false);
        }
        if (timerRunning){
            stopTimer();
        }
        else{
            startTimer();
        }

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
                count = count - 1;
                System.out.println(count);
                // check still count left or not
                if (count > 0) {
                    if (count % 2 == 1) {
                        timeLeftInMilliseconds = 300000;// for breaktime
                    } else {
                        timeLeftInMilliseconds = 1500000;// for studytime
                    }
                    startTimer();
                }

                //Once finish loop, resetting all the values to default.
                else if( count == 0) {
                    timeLeftInMilliseconds = 1500000;
                    timerRunning = false;
                    isFinished.postValue(true);
                    isFirsttime = true;
                }


            }


        }.start();
        timerRunning = true;

    }

    public void stopTimer(){
        countDownTimer.cancel();
        timerRunning = false;
    }

    //converting timeLeftInMilliseconds in to String in Format "MM:SS"
    public void updateTimer(){
        int minutes = (int) timeLeftInMilliseconds /60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;


        timeLeftText = ""+ minutes;
        timeLeftText += ":";
        if  (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;
        timeLeftLiveData.postValue(timeLeftText);

    }

    public MutableLiveData<String>getTimeLeftLiveData(){return timeLeftLiveData;}
    public MutableLiveData<Boolean>getIsFinished(){return isFinished;}

}