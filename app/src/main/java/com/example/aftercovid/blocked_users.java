package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class blocked_users {
    private string block_id;
    private string user_id;
    private string blocked_user_id;
    private string is_blocked;

    public blocked_users(){

    }
    public blocked_users(string block_id, string user_id, string blocked_user_id, string is_blocked) {
        this.block_id = block_id;
        this.user_id = user_id;
        this.blocked_user_id = blocked_user_id;
        this.is_blocked = is_blocked;
    }

    public string getBlock_id() {
        return block_id;
    }

    public void setBlock_id(string block_id) {
        this.block_id = block_id;
    }

    public string getUser_id() {
        return user_id;
    }

    public void setUser_id(string user_id) {
        this.user_id = user_id;
    }

    public string getBlocked_user_id() {
        return blocked_user_id;
    }

    public void setBlocked_user_id(string blocked_user_id) {
        this.blocked_user_id = blocked_user_id;
    }

    public string getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(string is_blocked) {
        this.is_blocked = is_blocked;
    }
}

