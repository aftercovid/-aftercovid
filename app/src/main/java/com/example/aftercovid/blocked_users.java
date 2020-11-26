package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class blocked_users {
    private String block_id;
    private String user_id;
    private String blocked_user_id;
    private String is_blocked;

    public blocked_users(){

    }
    public blocked_users(String block_id, String user_id, String blocked_user_id, String is_blocked) {
        this.block_id = block_id;
        this.user_id = user_id;
        this.blocked_user_id = blocked_user_id;
        this.is_blocked = is_blocked;
    }

    public String getBlock_id() {
        return block_id;
    }

    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBlocked_user_id() {
        return blocked_user_id;
    }

    public void setBlocked_user_id(String blocked_user_id) {
        this.blocked_user_id = blocked_user_id;
    }

    public String getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(String is_blocked) {
        this.is_blocked = is_blocked;
    }
}

