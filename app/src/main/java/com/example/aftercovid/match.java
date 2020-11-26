package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class match {
    private String match_id;
    private String relation_id_first;
    private String relation_id_second;
    private String user_id_first;
    private String user_id_second;
    private String block_id;

    public match(){

    }
    public match(String match_id, String relation_id_first, String relation_id_second, String user_id_first, String user_id_second, String block_id) {
        this.match_id = match_id;
        this.relation_id_first = relation_id_first;
        this.relation_id_second = relation_id_second;
        this.user_id_first = user_id_first;
        this.user_id_second = user_id_second;
        this.block_id = block_id;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getRelation_id_first() {
        return relation_id_first;
    }

    public void setRelation_id_first(String relation_id_first) {
        this.relation_id_first = relation_id_first;
    }

    public String getRelation_id_second() {
        return relation_id_second;
    }

    public void setRelation_id_second(String relation_id_second) {
        this.relation_id_second = relation_id_second;
    }

    public String getUser_id_first() {
        return user_id_first;
    }

    public void setUser_id_first(String user_id_first) {
        this.user_id_first = user_id_first;
    }

    public String getUser_id_second() {
        return user_id_second;
    }

    public void setUser_id_second(String user_id_second) {
        this.user_id_second = user_id_second;
    }

    public String getBlock_id() {
        return block_id;
    }

    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }
}
