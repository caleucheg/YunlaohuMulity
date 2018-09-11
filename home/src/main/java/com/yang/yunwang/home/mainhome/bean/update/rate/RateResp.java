
package com.yang.yunwang.home.mainhome.bean.update.rate;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RateResp {

    @SerializedName("CustomerSysNo")
    private Long mCustomerSysNo;
    @SerializedName("PassageWay")
    private String mPassageWay;
    @SerializedName("Rate")
    private Double mRate;
    @SerializedName("Type")
    private Long mType;

    public Long getCustomerSysNo() {
        return mCustomerSysNo;
    }

    public void setCustomerSysNo(Long customerSysNo) {
        mCustomerSysNo = customerSysNo;
    }

    public String getPassageWay() {
        return mPassageWay;
    }

    public void setPassageWay(String passageWay) {
        mPassageWay = passageWay;
    }

    public Double getRate() {
        return mRate;
    }

    public void setRate(Double rate) {
        mRate = rate;
    }

    public Long getType() {
        return mType;
    }

    public void setType(Long type) {
        mType = type;
    }

}
