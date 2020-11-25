package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class relations {

    private String relations_id;
    private String user_id;
    private String shown_user_id;
    private String is_liked;

    public relations(){

    }

    public relations(String relations_id, String user_id, String shown_user_id, String is_liked) {
        this.relations_id = relations_id;
        this.user_id = user_id;
        this.shown_user_id = shown_user_id;
        this.is_liked = is_liked;
    }

    public String getRelations_id() {
        return relations_id;
    }

    public void setRelations_id(String relations_id) {
        this.relations_id = relations_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getShown_user_id() {
        return shown_user_id;
    }

    public void setShown_user_id(String shown_user_id) {
        this.shown_user_id = shown_user_id;
    }

    public String getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(String is_liked) {
        this.is_liked = is_liked;
    }
}
