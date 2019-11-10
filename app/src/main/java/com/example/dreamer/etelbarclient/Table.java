package com.example.dreamer.etelbarclient;

import com.google.firebase.database.Exclude;

public class Table {
    @Exclude private String id;
    private int viewID;
    private int number;
    public boolean isFree;

    public Table(){

    }
    public Table(int viewID, int number, boolean isFree) {
        this.viewID = viewID;
        this.number = number;
        this.isFree = isFree;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return this.number;
    }

    public int getViewID() {
        return this.viewID;
    }

    public Boolean isFree(){
        return this.isFree;
    }

    public void setStatus(boolean status){
        this.isFree = status;
    }
}
