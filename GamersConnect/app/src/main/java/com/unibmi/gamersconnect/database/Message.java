package com.unibmi.gamersconnect.database;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Message {
    private String messageId;

    public String username;
    public String expectedDate;
    public String venue;
    public String description;
    public long timeOfPost;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    /*public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.description = "";
    }*/
    public Message(String date, String venue) {
        this.username = "testUser";
        this.expectedDate = date;
        this.venue = venue;
        this.description = "";
        this.timeOfPost = System.currentTimeMillis();
    }
    public Message(String date, String venue, String description) {
        this.username = "testUser";
        this.expectedDate = date;
        this.venue = venue;
        this.description = description;
        this.timeOfPost = System.currentTimeMillis();
    }

}
