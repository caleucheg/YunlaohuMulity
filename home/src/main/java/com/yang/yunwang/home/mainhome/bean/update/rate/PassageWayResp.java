
package com.yang.yunwang.home.mainhome.bean.update.rate;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PassageWayResp {

    @SerializedName("CustomerSysNo")
    private Long mCustomerSysNo;
    @SerializedName("PassageWay")
    private Long mPassageWay;
    @SerializedName("Remarks")
    private String mRemarks;
    @SerializedName("SysNo")
    private Long mSysNo;
    @SerializedName("Type")
    private Long mType;

    public Long getCustomerSysNo() {
        return mCustomerSysNo;
    }

    public void setCustomerSysNo(Long customerSysNo) {
        mCustomerSysNo = customerSysNo;
    }

    public Long getPassageWay() {
        return mPassageWay;
    }

    public void setPassageWay(Long passageWay) {
        mPassageWay = passageWay;
    }

    public String getRemarks() {
        return mRemarks;
    }

    public void setRemarks(String remarks) {
        mRemarks = remarks;
    }

    public Long getSysNo() {
        return mSysNo;
    }

    public void setSysNo(Long sysNo) {
        mSysNo = sysNo;
    }

    public Long getType() {
        return mType;
    }

    public void setType(Long type) {
        mType = type;
    }

}
