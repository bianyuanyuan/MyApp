package com.myapp.BmobData;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private Integer level;
    private String desc;//个人简介


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
