package com.myapp.Data;

import java.io.Serializable;


public class Coach implements Serializable {
    private long id;
    private String name;
    private int age;
    private String sex;
    private String like;
    private String phoneNumber;


    public Coach() {
        super();
    }

    public Coach(long id, String name, int age, String sex, String like, String phoneNumber
    ) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.like = like;
        this.phoneNumber = phoneNumber;
    }

    public Coach(String name, int age, String sex, String like, String phoneNumber) {
        super();
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.like = like;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;

    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}