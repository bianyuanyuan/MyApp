package com.myapp.BmobData;


import cn.bmob.v3.BmobObject;

//教练 类
public class Coach  extends BmobObject {
    private long id;
    private String name;
    private int age;
    private String sex;
    private String phoneNumber;
    private long teach_year;
    private long charge;
    private String teach_course;
    private String time_two;
    private String time_four;

    public String getTime_two() {
        return time_two;
    }

    public void setTime_two(String time_two) {
        this.time_two = time_two;
    }

    public String getTime_four() {
        return time_four;
    }

    public void setTime_four(String time_four) {
        this.time_four = time_four;
    }

    public Coach() {
        super();
    }

    public Coach(long id, String name, int age, String sex, String phoneNumber, long teach_year, long charge, String teach_course
    ) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.teach_year = teach_year;
        this.charge = charge;
        this.teach_course = teach_course;
    }

    public Coach(String name, int age, String sex, String phoneNumber, long teach_year, long charge, String teach_course) {
        super();
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.teach_year = teach_year;
        this.charge = charge;
        this.teach_course = teach_course;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getTeach_year() {
        return teach_year;
    }

    public void setTeach_year(long teach_year) {
        this.teach_year = teach_year;
    }

    public long getCharge() {
        return charge;
    }

    public void setCharge(long charge) {
        this.charge = charge;
    }

    public String getTeach_course() {
        return teach_course;
    }

    public void setTeach_course(String teach_course) {
        this.teach_course = teach_course;
    }
}