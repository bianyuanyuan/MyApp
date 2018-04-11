package com.myapp.Data;

import java.io.Serializable;

//场地 类
public class Class implements Serializable {
    private long id;
    private String name;
    private int number;
    private int contain;

    public Class() {
        super();
    }

    public Class(long id, String name, int number, int contain) {
        super();
        this.id = id;
        this.name = name;
        this.number = number;
        this.contain = contain;
    }

    public Class(String name, int number, int contain) {
        super();
        this.name = name;
        this.number = number;
        this.contain = contain;
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

    public int getContain() {
        return contain;
    }

    public void setContain(int contain) {
        this.contain = contain;
    }
}