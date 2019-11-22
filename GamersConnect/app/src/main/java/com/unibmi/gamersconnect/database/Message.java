package com.unibmi.gamersconnect.database;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

import java.util.Map;

@IgnoreExtraProperties
public class Message {
    private String uid;
    private int picIndex;
    private String author;
    private String date;
    private String venue;
    private String description;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }

    public Message(String uid, int picIndex, String author, String date, String venue, String description) {
        this.uid = uid;
        this.picIndex = picIndex;
        this.author = author;
        this.date = date;
        this.venue = venue;
        this.description = description;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("picIndex", picIndex);
        result.put("author", author);
        result.put("date", date);
        result.put("venue", venue);
        result.put("description", description);
        return result;
    }

    public String getUid() {
        return uid;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getVenue() {
        return venue;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setPicIndex(int picIndex) {
        this.picIndex = picIndex;
    }

    public int getPicIndex() {
        return picIndex;
    }
}