package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class match {
    private string match_id;
    private string relation_id_first;
    private string relation_id_second;
    private string user_id_first;
    private string user_id_second;
    private string block_id;

    public match(){

    }
    public match(string match_id, string relation_id_first, string relation_id_second, string user_id_first, string user_id_second, string block_id) {
        this.match_id = match_id;
        this.relation_id_first = relation_id_first;
        this.relation_id_second = relation_id_second;
        this.user_id_first = user_id_first;
        this.user_id_second = user_id_second;
        this.block_id = block_id;
    }

    public string getMatch_id() {
        return match_id;
    }

    public void setMatch_id(string match_id) {
        this.match_id = match_id;
    }

    public string getRelation_id_first() {
        return relation_id_first;
    }

    public void setRelation_id_first(string relation_id_first) {
        this.relation_id_first = relation_id_first;
    }

    public string getRelation_id_second() {
        return relation_id_second;
    }

    public void setRelation_id_second(string relation_id_second) {
        this.relation_id_second = relation_id_second;
    }

    public string getUser_id_first() {
        return user_id_first;
    }

    public void setUser_id_first(string user_id_first) {
        this.user_id_first = user_id_first;
    }

    public string getUser_id_second() {
        return user_id_second;
    }

    public void setUser_id_second(string user_id_second) {
        this.user_id_second = user_id_second;
    }

    public string getBlock_id() {
        return block_id;
    }

    public void setBlock_id(string block_id) {
        this.block_id = block_id;
    }
}
