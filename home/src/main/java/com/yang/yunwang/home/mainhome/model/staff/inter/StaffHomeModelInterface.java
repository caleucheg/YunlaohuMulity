package com.yang.yunwang.home.mainhome.model.staff.inter;


import com.yang.yunwang.home.mainhome.bean.StaffHomeBean;

public interface StaffHomeModelInterface {

    void initBannerList();

    void initMainFunctionList();

    /**
     * 服务商员工主菜单列表
     */
    void initServiceMainFunctionList();

    void initServiceSubFunctionList();

    void initSubFunctionList();

    StaffHomeBean loadInstance();

    void setInfos(String sys_no, String customer);
}
