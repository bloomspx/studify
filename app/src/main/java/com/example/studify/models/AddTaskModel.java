package com.example.studify.models;

public class AddTaskModel {

    private String task, time, id;

    public AddTaskModel() {
    }

    public AddTaskModel(String task, String time, String id) {
        this.task = task;
        this.time = time;
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
