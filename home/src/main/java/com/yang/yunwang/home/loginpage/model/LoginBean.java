package com.yang.yunwang.home.loginpage.model;


import android.view.View;

import java.util.List;

public class LoginBean {

    private List<View> view_list;
    private List<String> tab_list;
    private String user_name;

    public List<View> getView_list() {
        return view_list;
    }

    public void setView_list(List<View> view_list) {
        this.view_list = view_list;
    }

    public List<String> getTab_list() {
        return tab_list;
    }

    public void setTab_list(List<String> tab_list) {
        this.tab_list = tab_list;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
