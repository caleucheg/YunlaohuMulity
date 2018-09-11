package com.yang.yunwang.query.api.model;

import android.content.Context;

import com.yang.yunwang.query.api.bean.allcopage.AllocateBean;
import com.yang.yunwang.query.api.contract.AllcoatePageContract;

import java.util.List;

/**
 * Created by Administrator on 2018/7/16.
 */

public class AllcoatePageModel implements AllcoatePageContract.Model {
    private Context context;
    private AllocateBean bean;

    public AllcoatePageModel(Context context) {
        this.context = context;
        bean = new AllocateBean();
    }

    @Override
    public void initOrderListData(List<String> name_list, List<String> cno_list, List<Integer> flag_list, List<String> eidter_list, List<String> creater_list) {
        bean.setList_name(name_list);
        bean.setList_cno(cno_list);
        bean.setList_flag(flag_list);
        if (eidter_list != null) {
            bean.setList_editor(eidter_list);
            bean.setList_creater(creater_list);
        }
    }

    @Override
    public void addData(List<String> name_list, List<String> cno_list, List<Integer> flag_list, List<String> eidter_list, List<String> creater_list) {

        List<String> temp_name = bean.getList_name();
        List<String> temp_cno = bean.getList_cno();
        List<Integer> temp_flag = bean.getList_flag();
        List<String> temp_editor = bean.getList_editor();
        List<String> temp_creater = bean.getList_creater();
        for (int i = 0; i < temp_cno.size(); i++) {
            int pos = cno_list.indexOf(temp_cno.get(i));
            if (pos!=-1){
                name_list.remove(pos);
                cno_list.remove(pos);
                flag_list.remove(pos);
            }

        }

        if (temp_name != null) {
//            name_list.removeAll(temp_name);
//            cno_list.removeAll(temp_cno);
//            flag_list.removeAll(temp_flag);
            bean.getList_name().addAll(name_list);
            bean.getList_cno().addAll(cno_list);
            bean.getList_flag().addAll(flag_list);
        }

    }

    @Override
    public void clear() {
        bean.getList_name().clear();
        bean.getList_cno().clear();
        bean.getList_flag().clear();
        if (bean.getList_editor() != null) {
            bean.getList_editor().clear();
            bean.getList_creater().clear();
        }
    }

    @Override
    public AllocateBean loadInstance() {
        if (bean != null) {
            return bean;
        } else {
            return null;
        }
    }
}
