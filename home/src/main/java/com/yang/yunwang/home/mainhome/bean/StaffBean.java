package com.yang.yunwang.home.mainhome.bean;

import java.util.List;

public class StaffBean {

    private List<android.view.View> view_list;
    private List<String> tab_list;
    private int[] tab_res;


    public int[] getTab_res() {
        return tab_res;
    }

    public void setTab_res(int[] tab_res) {
        this.tab_res = tab_res;
    }

    public List<android.view.View> getView_list() {
        return view_list;
    }

    public void setView_list(List<android.view.View> view_list) {
        this.view_list = view_list;
    }

    public List<String> getTab_list() {
        return tab_list;
    }

    public void setTab_list(List<String> tab_list) {
        this.tab_list = tab_list;
    }


}
