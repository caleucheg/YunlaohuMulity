package com.yang.yunwang.home.mainhome.bean;

import android.os.Bundle;

import java.util.List;

public class StaffHomeBean {

    private List<String> main_function_list;
    private List<String> sub_function_list;
    //    private List<View> banner_list;
//    private int[] banner_res;
    private int[] main_function_res;
    private int[] main_function_res_selected;
    private int[] sub_function_res;
    private int[] sub_function_res_selected;
//    private Intent[] main_intents;
//    private Intent[] sub_intents;
    private String sys_no;//主键
    private String customer;//用户名
    private String staff_top_name;//上级名称

    private List<String> mianActions;
    private Bundle[] mainBundles;
    private List<String> subActions;

    public List<String> getMianActions() {
        return mianActions;
    }

    public void setMianActions(List<String> mianActions) {
        this.mianActions = mianActions;
    }

    public Bundle[] getMainBundles() {
        return mainBundles;
    }

    public void setMainBundles(Bundle[] mainBundles) {
        this.mainBundles = mainBundles;
    }

    public List<String> getSubActions() {
        return subActions;
    }

    public void setSubActions(List<String> subActions) {
        this.subActions = subActions;
    }

    public Bundle[] getSubBundles() {
        return subBundles;
    }

    public void setSubBundles(Bundle[] subBundles) {
        this.subBundles = subBundles;
    }

    private Bundle[] subBundles;

    public String getStaff_top_name() {
        return staff_top_name;
    }

    public void setStaff_top_name(String staff_top_name) {
        this.staff_top_name = staff_top_name;
    }

    public String getSys_no() {
        return sys_no;
    }

    public void setSys_no(String sys_no) {
        this.sys_no = sys_no;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

  /*  public int[] getBanner_res() {
        return banner_res;
    }*/

    /*public void setBanner_res(int[] banner_res) {
        this.banner_res = banner_res;
    }*/

    public List<String> getMain_function_list() {
        return main_function_list;
    }

    public void setMain_function_list(List<String> main_function_list) {
        this.main_function_list = main_function_list;
    }

    public List<String> getSub_function_list() {
        return sub_function_list;
    }

    public void setSub_function_list(List<String> sub_function_list) {
        this.sub_function_list = sub_function_list;
    }

    /*public List<View> getBanner_list() {
        return banner_list;
    }*/

    /*public void setBanner_list(List<View> banner_list) {
        this.banner_list = banner_list;
    }*/

    public int[] getMain_function_res() {
        return main_function_res;
    }

    public void setMain_function_res(int[] main_function_res) {
        this.main_function_res = main_function_res;
    }

    public int[] getMain_function_res_selected() {
        return main_function_res_selected;
    }

    public void setMain_function_res_selected(int[] main_function_res_selected) {
        this.main_function_res_selected = main_function_res_selected;
    }

    public int[] getSub_function_res() {
        return sub_function_res;
    }

    public void setSub_function_res(int[] sub_function_res) {
        this.sub_function_res = sub_function_res;
    }

    public int[] getSub_function_res_selected() {
        return sub_function_res_selected;
    }

    public void setSub_function_res_selected(int[] sub_function_res_selected) {
        this.sub_function_res_selected = sub_function_res_selected;
    }

//    public Intent[] getMain_intents() {
//        return main_intents;
//    }
//
//    public void setMain_intents(Intent[] main_intents) {
//        this.main_intents = main_intents;
//    }
//
//    public Intent[] getSub_intents() {
//        return sub_intents;
//    }
//
//    public void setSub_intents(Intent[] sub_intents) {
//        this.sub_intents = sub_intents;
//    }
}
