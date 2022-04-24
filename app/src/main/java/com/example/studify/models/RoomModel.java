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
    private String Admin_User;
    private ArrayList<String> tasks_Lists;
    private String startTime;
    private int loop;
    //private Time
    //private HashMap<String,UserProfileModel> User_List;

    public RoomModel()
    {
        //this.startTime = null;
        this.roomID = RandomString.getAlphaNumericString(5);
        this.roomUserCount = 1;
        //this.User_List = null;
        this.User_IDs = null;
        this.tasks_Lists = new ArrayList<String>();
        this.loop = 1;
        this.Admin_User = null;
        //this.roomTimerStart = LocalDateTime.now();
    }

    public void setAdmin_User(String admin_User) {
        Admin_User = admin_User;
    }

    public String getAdmin_User() {
        return Admin_User;
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

    public String getStartTime(){
        return this.startTime;
    }
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }
    public int getLoop(){
        return this.loop;
    }
    public void  setLoop(int loop){
        this.loop = loop;
    }

    public String getRoomID() {
        return roomID;
    }
}

