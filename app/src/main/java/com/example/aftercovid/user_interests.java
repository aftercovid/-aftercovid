package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class user_interests {
    private String user_id;
    private String interest_id;

    public user_interests(){

    }

    public user_interests(String user_id, String interest_id) {
        this.user_id = user_id;
        this.interest_id = interest_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(String interest_id) {
        this.interest_id = interest_id;
    }
}
