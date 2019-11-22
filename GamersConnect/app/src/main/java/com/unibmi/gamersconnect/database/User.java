package com.unibmi.gamersconnect.database;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String password;
    public String description;
    public int profilePic;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    /*public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.description = "";
    }*/

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }
}
