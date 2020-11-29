package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Relations {

    private String uid;
    private String suid;
    private Boolean isLiked;

    public Relations(String uid, String suid, Boolean isLiked) {
        this.uid = uid;
        this.suid = suid;
        this.isLiked = isLiked;
    }

    public Relations() {
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

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }
}
