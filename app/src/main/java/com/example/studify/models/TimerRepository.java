package com.example.studify.models;

import android.app.Application;
import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

public class TimerRepository {
    private Application application;
    private CountDownTimer countDownTimer;
    private CountDownTimer breakcountDownTimer;
    private long timeLeftInMilliseconds = 10000;
    private long breaktimeLeftInMilliseconds = 5000;

    private boolean timerRunning;
    private String timeLeftText;
    private MutableLiveData<String> timeLeftLiveData;
    private int count = 2;
    private Boolean isBreak = false;
    private Boolean isFirsttime = true;

    public TimerRepository(Application application){
        this.application = application;
        timeLeftLiveData = new MutableLiveData<>();


    }


    public void startStop(int countIn){
        if(isFirsttime){
            count = countIn;
            isFirsttime = false;
        }
        if (timerRunning){
            System.out.println("I'm HERE!");
            stopTimer();
        }
        else{
            if (!isBreak)
            {
                startTimer();
            }
            else{
                startBreakTimer();
            }
        }

    }


    public void startTimer(){
        timerRunning = true;
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();

            }

            @Override
            public void onFinish() {
                isBreak = true;
                startBreakTimer();

            }


        }.start();

    }

    public void startBreakTimer(){
        timerRunning = true;
        breakcountDownTimer = new CountDownTimer(breaktimeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                breaktimeLeftInMilliseconds = l;
                updateBreakTimer();

            }

            @Override
            public void onFinish() {
                isBreak = false;
                int Intcount = (int) count -1;
                count = Intcount;


                if(count > 0) {
                    timeLeftInMilliseconds = 10000;
                    breaktimeLeftInMilliseconds= 5000;
                    startTimer();

                }
                else{
                    isFirsttime = true;
                    timeLeftInMilliseconds = 10000;
                    breaktimeLeftInMilliseconds= 5000;
                    timeLeftText = "2:00";
                    timeLeftLiveData.postValue(timeLeftText);
                }



            }
        }.start();
    }


    public void stopTimer(){
        if(isBreak = true){
            breakcountDownTimer.cancel();
            timerRunning = false;
        }
        else{
            countDownTimer.cancel();
            timerRunning = false;
        }


    }

    public void updateTimer(){
        int minutes = (int) timeLeftInMilliseconds /60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;


        timeLeftText = ""+ minutes;
        timeLeftText += ":";
        if  (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;
        timeLeftLiveData.postValue(timeLeftText);

    }

    public void updateBreakTimer(){
        int minutes = (int) breaktimeLeftInMilliseconds /60000;
        int seconds = (int) breaktimeLeftInMilliseconds % 60000 / 1000;


        timeLeftText = ""+ minutes;
        timeLeftText += ":";
        if  (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;
        timeLeftLiveData.postValue(timeLeftText);

    }
    public MutableLiveData<String>getTimeLeftLiveData(){return timeLeftLiveData;}

}
