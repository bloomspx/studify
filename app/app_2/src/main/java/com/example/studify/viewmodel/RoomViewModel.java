package com.example.studify.viewmodel;

import android.app.Application;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.studify.models.AuthAppRepository;
import com.example.studify.models.GroupTimeRepository;
import com.example.studify.models.RoomAppRepository;
import com.example.studify.models.RoomModel;
import com.example.studify.models.UserAppRepository;

public class RoomViewModel extends AndroidViewModel {
    private AuthAppRepository authAppRepository;
    private RoomAppRepository roomAppRepository;
    private UserAppRepository userAppRepository;
    private GroupTimeRepository groupTimeRepository;
    private MutableLiveData<String> groupTimerLeftLiveDate;

    public RoomViewModel(@NonNull Application application) {
        super(application);

        authAppRepository = new AuthAppRepository(application);
        roomAppRepository = new RoomAppRepository(application);
        userAppRepository = new UserAppRepository(application);
        groupTimeRepository = new GroupTimeRepository(application);
        groupTimerLeftLiveDate = groupTimeRepository.getTimeLeftLiveData();
    }

    public void createRoom(RoomModel room){ roomAppRepository.createRoom(room);}

    public void joinRoom(String roomID){roomAppRepository.joinRoom(roomID);}

    public void startGroupTimer(String roomID){groupTimeRepository.join(roomID);}

    public  MutableLiveData<String> getGroupTimerLeftLiveDate(){return groupTimerLeftLiveDate;}


    //public void addTask(String taskName) { roomAppRepository.addTask(taskName); }


    //public void updateTask(String taskName) { roomAppRepository.updateTask(taskName); }
}
