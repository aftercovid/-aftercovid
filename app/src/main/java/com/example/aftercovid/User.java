package com.example.aftercovid;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    //private String userId;
    private String firstName;
    //private String gender;
    private String description;
    //private String location;
    private Integer age;


    //dodaj gender tu pozniej i location i userid
    public User(String firstName, String description, Integer age) {
        //this.userId = userId;
        this.firstName = firstName;
        //this.gender = gender;
        this.description = description;
        //this.location = location;
        this.age = age;
    }

    public User() {
    }

//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
