package com.yang.yunwang.base.dao;

import java.io.Serializable;

/**
 *  on 2017/4/16.
 *
 * description：
 * update by:
 * update day:
 */
public class UserInfo implements Serializable {
    private String name;
    private int age;

    public UserInfo() {
    }

    public UserInfo(String name, int age) {
        this.name = name;
        this.age = age;
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
}
