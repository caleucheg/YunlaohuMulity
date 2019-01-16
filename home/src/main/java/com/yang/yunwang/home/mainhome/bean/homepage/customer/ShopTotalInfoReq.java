
package com.yang.yunwang.home.mainhome.bean.homepage.customer;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ShopTotalInfoReq {

    @SerializedName("CustomerSysNo")
    private Long mCustomerSysNo;
    @SerializedName("Pay_Type")
    private String mPayType;
    @SerializedName("Time_end")
    private String mTimeEnd;
    @SerializedName("Time_Start")
    private String mTimeStart;

    public Long getCustomerSysNo() {
        return mCustomerSysNo;
    }

    public void setCustomerSysNo(Long customerSysNo) {
        mCustomerSysNo = customerSysNo;
    }

    public String getPayType() {
        return mPayType;
    }

    public void setPayType(String payType) {
        mPayType = payType;
    }

    public String getTimeEnd() {
        return mTimeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        mTimeEnd = timeEnd;
    }

    public String getTimeStart() {
        return mTimeStart;
    }

    public void setTimeStart(String timeStart) {
        mTimeStart = timeStart;
    }

}
