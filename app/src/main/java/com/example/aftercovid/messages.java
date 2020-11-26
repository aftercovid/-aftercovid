package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class messages {
    private String message_id;
    private String match_id;
    private String sender_id;
    private String reveiver_id;
    private String message_content;
    private String datetime;

    public messages(){

    }
    public messages(String message_id, String match_id, String sender_id, String reveiver_id, String message_content, String datetime) {
        this.message_id = message_id;
        this.match_id = match_id;
        this.sender_id = sender_id;
        this.reveiver_id = reveiver_id;
        this.message_content = message_content;
        this.datetime = datetime;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReveiver_id() {
        return reveiver_id;
    }

    public void setReveiver_id(String reveiver_id) {
        this.reveiver_id = reveiver_id;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}

