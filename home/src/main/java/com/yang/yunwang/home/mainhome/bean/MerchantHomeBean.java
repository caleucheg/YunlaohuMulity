package com.yang.yunwang.home.mainhome.bean;

import android.os.Bundle;

import java.util.List;

public class MerchantHomeBean {

    private List<String> menu_list;
    private int[] reses;
    private int[] reses_select;
//    private Intent[] intents;
    private List<String> actions;
    private Bundle[] bundles;

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public Bundle[] getBundles() {
        return bundles;
    }

    public void setBundles(Bundle[] bundles) {
        this.bundles = bundles;
    }

    public List<String> getMenu_list() {
        return menu_list;
    }

    public void setMenu_list(List<String> menu_list) {
        this.menu_list = menu_list;
    }

    public int[] getReses() {
        return reses;
    }

    public void setReses(int[] reses) {
        this.reses = reses;
    }

    public int[] getReses_select() {
        return reses_select;
    }

    public void setReses_select(int[] reses_select) {
        this.reses_select = reses_select;
    }

//    public Intent[] getIntents() {
//        return intents;
//    }
//
//    public void setIntents(Intent[] intents) {
//        this.intents = intents;
//    }
}
