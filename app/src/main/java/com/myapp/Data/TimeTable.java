package com.myapp.Data;

import java.io.Serializable;

/**
 * Created by 540 on 2018/4/8.
 * 时间表类
 */

public class TimeTable implements Serializable {
    private long id;
    private String periods;
    private String week;
    private long time;
    private String t_start;
    private String t_end;

    public TimeTable(long id, String periods, String week, long time, String t_start, String t_end) {
        this.id = id;
        this.periods = periods;
        this.week = week;
        this.time = time;
        this.t_start = t_start;
        this.t_end = t_end;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getT_start() {
        return t_start;
    }

    public void setT_start(String t_start) {
        this.t_start = t_start;
    }

    public String getT_end() {
        return t_end;
    }

    public void setT_end(String t_end) {
        this.t_end = t_end;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}