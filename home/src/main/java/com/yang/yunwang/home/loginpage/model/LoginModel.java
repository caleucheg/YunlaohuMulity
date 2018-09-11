package com.yang.yunwang.home.loginpage.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.yang.yunwang.home.R;
import com.yang.yunwang.home.loginpage.model.intf.LoginModelInterface;

import java.util.ArrayList;
import java.util.List;

public class LoginModel implements LoginModelInterface {

    private LoginBean bean;
    private Context context;

    public LoginModel(Context context) {
        bean = new LoginBean();
        this.context = context;
    }

    @Override
    public void initTabList() {
        List<String> list_tabs = new ArrayList<String>();
        list_tabs.add(context.getResources().getString(R.string.tab_merchant));
        list_tabs.add(context.getResources().getString(R.string.tab_staff));
        bean.setTab_list(list_tabs);
    }

    @Override
    public void initViewList() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view_1 = inflater.inflate(R.layout.viewpager_login_item_merchant, null);
        View view_2 = inflater.inflate(R.layout.viewpager_login_item_staff, null);
        View[] views = new View[]{view_1, view_2};
        List<View> list_views = new ArrayList<View>();
        for (int i = 0; i < views.length; i++) {
            list_views.add(views[i]);
        }
        bean.setView_list(list_views);
    }

    @Override
    public LoginBean loadInstance() {
        if (bean != null) {
            return bean;
        } else {
            return null;
        }
    }
}
