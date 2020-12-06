package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserInterest {

    private String uid;
    private String interest;

    public UserInterest(String uid, String interest) {
        this.uid = uid;
        this.interest = interest;
    }

    public UserInterest() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
