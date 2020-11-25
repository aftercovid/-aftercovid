package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class interests {
    private string interests_id;
    private string interests;

    public interests(){

    }
    public interests(string interests_id, string interests) {
        this.interests_id = interests_id;
        this.interests = interests;
    }

    public string getInterests_id() {
        return interests_id;
    }

    public void setInterests_id(string interests_id) {
        this.interests_id = interests_id;
    }

    public string getInterests() {
        return interests;
    }

    public void setInterests(string interests) {
        this.interests = interests;
    }
}
