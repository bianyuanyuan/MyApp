package com.myapp.Data;

import java.io.Serializable;


public class Class implements Serializable {
    private long id;
    private String name;
    private int number;

    public Class() {
        super();
    }

    public Class(long id, String name, int number) {
        super();
        this.id = id;
        this.name = name;
        this.number=number;
    }

    public Class(String name, int number) {
        super();
        this.name = name;
        this.number=number;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}