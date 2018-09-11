package com.yang.yunwang.home.mainhome.bean;


import android.support.v4.app.Fragment;

import java.util.List;

public class MerchantBean {

    private List<Fragment> view_list;
    private List<String> tab_list;
    private int[] tab_res;

    public List<Fragment> getView_list() {
        return view_list;
    }

    public void setView_list(List<Fragment> view_list) {
        this.view_list = view_list;
    }

    public List<String> getTab_list() {
        return tab_list;
    }

    public void setTab_list(List<String> tab_list) {
        this.tab_list = tab_list;
    }

    public int[] getTab_res() {
        return tab_res;
    }

    public void setTab_res(int[] tab_res) {
        this.tab_res = tab_res;
    }
}
