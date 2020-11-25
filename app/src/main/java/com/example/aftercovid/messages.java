package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class messages {
    private string message_id;
    private string match_id;
    private string sender_id;
    private string reveiver_id;
    private string message_content;
    private string datetime;

    public messages(){

    }
    public messages(string message_id, string match_id, string sender_id, string reveiver_id, string message_content, string datetime) {
        this.message_id = message_id;
        this.match_id = match_id;
        this.sender_id = sender_id;
        this.reveiver_id = reveiver_id;
        this.message_content = message_content;
        this.datetime = datetime;
    }

    public string getMessage_id() {
        return message_id;
    }

    public void setMessage_id(string message_id) {
        this.message_id = message_id;
    }

    public string getMatch_id() {
        return match_id;
    }

    public void setMatch_id(string match_id) {
        this.match_id = match_id;
    }

    public string getSender_id() {
        return sender_id;
    }

    public void setSender_id(string sender_id) {
        this.sender_id = sender_id;
    }

    public string getReveiver_id() {
        return reveiver_id;
    }

    public void setReveiver_id(string reveiver_id) {
        this.reveiver_id = reveiver_id;
    }

    public string getMessage_content() {
        return message_content;
    }

    public void setMessage_content(string message_content) {
        this.message_content = message_content;
    }

    public string getDatetime() {
        return datetime;
    }

    public void setDatetime(string datetime) {
        this.datetime = datetime;
    }
}

