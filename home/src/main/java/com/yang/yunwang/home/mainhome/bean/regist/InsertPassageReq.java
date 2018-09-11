
package com.yang.yunwang.home.mainhome.bean.regist;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class InsertPassageReq {

    @SerializedName("CustomerSysNo")
    private String mCustomerSysNo;
    @SerializedName("PassageWay")
    private String mPassageWay;
    @SerializedName("Rate")
    private String mRate;
    @SerializedName("Remarks")
    private String mRemarks;
    @SerializedName("Type")
    private String mType;

    public String getCustomerSysNo() {
        return mCustomerSysNo;
    }

    public void setCustomerSysNo(String customerSysNo) {
        mCustomerSysNo = customerSysNo;
    }

    public String getPassageWay() {
        return mPassageWay;
    }

    public void setPassageWay(String passageWay) {
        mPassageWay = passageWay;
    }

    public String getRate() {
        return mRate;
    }

    public void setRate(String rate) {
        mRate = rate;
    }

    public String getRemarks() {
        return mRemarks;
    }

    public void setRemarks(String remarks) {
        mRemarks = remarks;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
