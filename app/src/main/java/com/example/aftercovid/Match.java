package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Match {

    private String uid;
    private String suid;

    public Match() {
    }

    public Match(String uid, String suid) {
        this.uid = uid;
        this.suid = suid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }
}
