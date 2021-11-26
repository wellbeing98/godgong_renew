package com.example.godgong;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Comment {

    public String comment;
    public String date;
    public String id;
    public Map<String, Boolean> stars = new HashMap<>();

    public Comment(){
        id = "";
        date = "";
        comment="";
    }

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Comment(String id,String date, String comment) {
        this.id = id;
        this.comment = comment;
        this.date = date;
    }



}
