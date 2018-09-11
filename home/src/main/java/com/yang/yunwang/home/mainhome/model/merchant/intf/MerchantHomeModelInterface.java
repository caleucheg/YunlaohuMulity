package com.yang.yunwang.home.mainhome.model.merchant.intf;


import com.yang.yunwang.home.mainhome.bean.MerchantHomeBean;

public interface MerchantHomeModelInterface {

    void initServiceRecMenu();

    void initMerchantRecMenu();

    MerchantHomeBean loadInstance();
}
