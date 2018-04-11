package com.myapp.Data;

import java.io.Serializable;

public class Course implements Serializable {
    private long id;
    private String name;
    private long student_id;
    private long coach_id;
    private long times;
    private long timesweek;

    public Course() {
        super();
    }

    public Course(long id, String name, long student_id, long coach_id, long times, long timesweek) {
        super();
        this.id = id;
        this.name = name;
        this.student_id = student_id;
        this.coach_id = coach_id;
        this.times = times;
        this.timesweek = timesweek;
    }

    public Course(String name, long student_id, long coach_id, long times, long timesweek) {
        super();
        this.name = name;
        this.student_id = student_id;
        this.coach_id = coach_id;
        this.times = times;
        this.timesweek = timesweek;
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

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

    public long getCoach_id() {
        return coach_id;
    }

    public void setCoach_id(long coach_id) {
        this.coach_id = coach_id;
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
}