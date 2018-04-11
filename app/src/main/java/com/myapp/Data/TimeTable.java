package com.myapp.Data;

import java.io.Serializable;

/**
 * Created by 540 on 2018/4/8.
 * 时间表类
 */

public class TimeTable implements Serializable {
    private long id;
    private String time;
    private String week;

    public TimeTable(long id, String time, String week) {
        this.id = id;
        this.time = time;
        this.week = week;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}