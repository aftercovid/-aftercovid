package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class user_pictures {

    private String picture_id;
    private String pic_location;
    private String user_id;

    public user_pictures(){

    }

    public user_pictures(String picture_id, String pic_location, String user_id) {
        this.picture_id = picture_id;
        this.pic_location = pic_location;
        this.user_id = user_id;
    }

    public String getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(String picture_id) {
        this.picture_id = picture_id;
    }

    public String getPic_location() {
        return pic_location;
    }

    public void setPic_location(String pic_location) {
        this.pic_location = pic_location;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
