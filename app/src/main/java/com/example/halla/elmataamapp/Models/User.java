package com.example.halla.elmataamapp.Models;

import org.json.JSONObject;

/**
 * Created by user on 2/5/2016.
 */
public class User extends JSONObject {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
