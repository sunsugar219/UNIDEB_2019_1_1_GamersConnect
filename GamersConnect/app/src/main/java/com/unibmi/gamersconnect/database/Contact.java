package com.unibmi.gamersconnect.database;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

public class Contact {

    public String username;
    public String email;

    public Contact() {
        // Default constructor required for calls to DataSnapshot.getValue(Contact.class)
    }


    public Contact (String username, String email) {
        this.username = username;
        this.email = email;
    }

}

