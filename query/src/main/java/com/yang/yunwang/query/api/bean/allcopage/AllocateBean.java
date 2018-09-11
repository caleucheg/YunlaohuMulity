package com.yang.yunwang.query.api.bean.allcopage;

import java.util.List;

/**
 * Created by Administrator on 2018/7/16.
 */

public class AllocateBean {
    private List<String> list_name;
    private List<Integer> list_flag;
    private List<String> list_cno;
    private List<String> list_editor;
    private List<String> list_creater;

    public List<String> getList_name() {
        return list_name;
    }

    public void setList_name(List<String> list_name) {
        this.list_name = list_name;
    }

    public List<Integer> getList_flag() {
        return list_flag;
    }

    public void setList_flag(List<Integer> list_flag) {
        this.list_flag = list_flag;
    }

    public List<String> getList_cno() {
        return list_cno;
    }

    public void setList_cno(List<String> list_cno) {
        this.list_cno = list_cno;
    }

    public List<String> getList_editor() {
        return list_editor;
    }

    public void setList_editor(List<String> list_editor) {
        this.list_editor = list_editor;
    }

    public List<String> getList_creater() {
        return list_creater;
    }

    public void setList_creater(List<String> list_creater) {
        this.list_creater = list_creater;
    }

    @Override
    public String toString() {
        return "AllocateBean{" +
                "list_name=" + list_name +
                ", list_flag=" + list_flag +
                ", list_cno=" + list_cno +
                ", list_editor=" + list_editor +
                ", list_creater=" + list_creater +
                '}';
    }
}
