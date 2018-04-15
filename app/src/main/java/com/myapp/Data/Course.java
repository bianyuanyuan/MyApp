package com.myapp.Data;

import java.io.Serializable;
/*
*课程 类
 */
public class Course implements Serializable {
    private long id;
    private String name;
    private long times;
    private long timesweek;
    private long price;

    public Course() {
        super();
    }

    public Course(long id, String name, long times, long timesweek, long price) {
        super();
        this.id = id;
        this.name = name;
        this.times = times;
        this.timesweek = timesweek;
        this.price = price;
    }

    public Course(String name, long times, long timesweek, long price) {
        super();
        this.name = name;
        this.times = times;
        this.timesweek = timesweek;
        this.price = price;
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


    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public long getTimesweek() {
        return timesweek;
    }

    public void setTimesweek(long timesweek) {
        this.timesweek = timesweek;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}