package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class user_interests {
    private string user_id;
    private string interest_id;

    public user_interests(){

    }

    public user_interests(string user_id, string interest_id) {
        this.user_id = user_id;
        this.interest_id = interest_id;
    }

    public string getUser_id() {
        return user_id;
    }

    public void setUser_id(string user_id) {
        this.user_id = user_id;
    }

    public string getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(string interest_id) {
        this.interest_id = interest_id;
    }
}
