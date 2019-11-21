package com.unibmi.gamersconnect.database;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

import java.util.Map;

@IgnoreExtraProperties
public class Message {
    public String uid;
    public String author;
    public String date;
    public String venue;
    public String description;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }

    public Message(String uid, String author, String date, String venue, String description) {
        this.uid = uid;
        this.author = author;
        this.date = date;
        this.venue = venue;
        this.description = description;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("date", date);
        result.put("venue", venue);
        result.put("description", description);
        return result;
    }
}