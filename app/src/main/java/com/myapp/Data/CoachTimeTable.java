package com.myapp.Data;


public class CoachTimeTable {
    private long id;
    private String periods;
    private long coach_id;

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

    public long getCoach_id() {
        return coach_id;
    }

    public void setCoach_id(long coach_id) {
        this.coach_id = coach_id;
    }
}
