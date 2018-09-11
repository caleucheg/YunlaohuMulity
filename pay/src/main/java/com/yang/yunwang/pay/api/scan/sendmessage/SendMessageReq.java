
package com.yang.yunwang.pay.api.scan.sendmessage;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SendMessageReq {

    @SerializedName("CustomerServiceSysNO")
    private String mCustomerServiceSysNO;
    @SerializedName("Money")
    private String mMoney;
    @SerializedName("Out_trade_no")
    private String mOutTradeNo;
    @SerializedName("SystemUserSysNO")
    private String mSystemUserSysNO;
    @SerializedName("Type")
    private String mType;

    public String getCustomerServiceSysNO() {
        return mCustomerServiceSysNO;
    }

    public void setCustomerServiceSysNO(String customerServiceSysNO) {
        mCustomerServiceSysNO = customerServiceSysNO;
    }

    public String getMoney() {
        return mMoney;
    }

    public void setMoney(String money) {
        mMoney = money;
    }

    public String getOutTradeNo() {
        return mOutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        mOutTradeNo = outTradeNo;
    }

    public String getSystemUserSysNO() {
        return mSystemUserSysNO;
    }

    public void setSystemUserSysNO(String systemUserSysNO) {
        mSystemUserSysNO = systemUserSysNO;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
