package com.example.studify.models;

import com.google.firebase.firestore.auth.User;
import com.google.type.DateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RoomModel {
    private String roomID;
    private int roomUserCount;
    //private DateTime roomTimerStart;
    private ArrayList<String> User_IDs;
    private ArrayList<String> tasks_Lists;
    //private Time
    //private HashMap<String,UserProfile> User_List;

    public RoomModel()
    {
        this.roomID = UUID.randomUUID().toString();
        this.roomUserCount = 1;
        //this.User_List = null;
        this.User_IDs = null;
        this.tasks_Lists = new ArrayList<String>();
        //this.roomTimerStart = LocalDateTime.now();
    }

    public void setTasks_Lists(ArrayList<String> tasks_Lists) { this.tasks_Lists = tasks_Lists; }

    public void add_tasks_lists(String input){this.tasks_Lists.add(input);}

    public void remove_tasks_lists(String input){this.tasks_Lists.remove(input);}
    //public void setTasks_Lists(ArrayList<String> tasks_Lists) { this.tasks_Lists = tasks_Lists; }

    //public void update_tasks_lists(String input){this.tasks_Lists.add(input);}

    public ArrayList<String> getTasks_Lists() { return tasks_Lists; }

    public void setUser_IDs(ArrayList<String> user_IDs) {
        User_IDs = user_IDs;
    }

    public ArrayList<String> getUser_IDs() {
        return User_IDs;
    }

    public int getRoomUserCount() {
        return roomUserCount;
    }

    public void setRoomUserCount(int roomUserCount) {
        this.roomUserCount = roomUserCount;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomID() {
        return roomID;
    }
}

