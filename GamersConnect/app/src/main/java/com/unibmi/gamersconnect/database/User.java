package com.unibmi.gamersconnect.database;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User {
    private String uid;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("username", username);
        result.put("email", email);
        result.put("password", password);
        result.put("profilePic", profilePic);
        result.put("description", description);
        return result;
    }

}
