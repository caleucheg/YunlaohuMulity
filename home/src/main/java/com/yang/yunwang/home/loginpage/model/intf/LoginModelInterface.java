package com.yang.yunwang.home.loginpage.model.intf;


import com.yang.yunwang.home.loginpage.model.LoginBean;

public interface LoginModelInterface {

    void initTabList();

    void initViewList();

    LoginBean loadInstance();
}
