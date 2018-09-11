package com.yang.yunwang.base.basereq.bean.merchinfo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/6/11.
 */

public class MerchInfoReq {
    @SerializedName("Customer")
    private String mCustomer;


    public String getCustomer() {
        return mCustomer;
    }

    public void setCustomer(String customer) {
        mCustomer = customer;
    }
}
