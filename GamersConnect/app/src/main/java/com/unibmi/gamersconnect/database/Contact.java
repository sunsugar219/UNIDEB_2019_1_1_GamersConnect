package com.unibmi.gamersconnect.database;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

public class Contact {

    public String uid;
    public String fid;

    public Contact() {
        // Default constructor required for calls to DataSnapshot.getValue(Contact.class)
    }


    public Contact (String username1, String username2) {
        this.uid = username1;
        this.fid= username2;
    }
    public String getUid(){return uid;}
    public String getFid(){return fid;}

}

