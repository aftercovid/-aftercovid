package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class interests {
    private String interests_id;
    private String interests;

    public interests(){

    }
    public interests(String interests_id, String interests) {
        this.interests_id = interests_id;
        this.interests = interests;
    }

    public String getInterests_id() {
        return interests_id;
    }

    public void setInterests_id(String interests_id) {
        this.interests_id = interests_id;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }
}
