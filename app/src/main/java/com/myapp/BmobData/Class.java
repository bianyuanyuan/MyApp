package com.myapp.BmobData;


import cn.bmob.v3.BmobObject;

//场地 类
public class Class extends BmobObject {
    private long id;
    private String name;
    private String  position;
    private int contain;

    public Class() {
        super();
    }

    public Class(long id, String name, String position, int contain) {
        super();
        this.id = id;
        this.name = name;
        this.position=position;
        this.contain = contain;
    }

    public Class(String name, String position, int contain) {
        super();
        this.name = name;
        this.position=position;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getContain() {
        return contain;
    }

    public void setContain(int contain) {
        this.contain = contain;
    }
}