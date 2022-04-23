package com.example.studify.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.studify.repository.GroupTimeRepository;
import com.example.studify.repository.RoomAppRepository;
import com.example.studify.models.RoomModel;

public class RoomViewModel extends AndroidViewModel {

    private RoomAppRepository roomAppRepository;
    private GroupTimeRepository groupTimeRepository;
    private MutableLiveData<String> groupTimerLeftLiveDate;

    public RoomViewModel(@NonNull Application application) {
        super(application);

        roomAppRepository = new RoomAppRepository(application);

        groupTimeRepository = new GroupTimeRepository(application);
        groupTimerLeftLiveDate = groupTimeRepository.getTimeLeftLiveData();
    }

    public void createRoom(RoomModel room){ roomAppRepository.createRoom(room);}

    //public void joinRoom(String roomID){roomAppRepository.joinRoom(roomID);}

    public void startGroupTimer(String roomID){groupTimeRepository.join(roomID);}

    public  MutableLiveData<String> getGroupTimerLeftLiveDate(){return groupTimerLeftLiveDate;}

}
