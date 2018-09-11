package com.yang.yunwang.home.mainhome.model.merchant.intf;


import com.yang.yunwang.home.mainhome.bean.MerchantBean;

public interface MerchantModelInterface {

    void initTabList();

    void initViewList();

    MerchantBean loadInstance();
}
