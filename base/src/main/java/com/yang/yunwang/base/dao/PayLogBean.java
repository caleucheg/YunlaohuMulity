package com.yang.yunwang.base.dao;

/**
 * Created by Administrator on 2017/3/10.
 */
public class PayLogBean {
    private final String getedWxType;
    private final String getedZfbType;

    private final boolean isQR;

    public boolean isWx;
    public int total_feeI;
    public String code;

    public PayLogBean(boolean isWx, int total_feeI, String code, String getedWxType, String getedZfbType, boolean isQR) {
        this.isWx = isWx;
        this.total_feeI = total_feeI;
        this.code = code;
        this.getedWxType = getedWxType;
        this.getedZfbType = getedZfbType;
        this.isQR = isQR;
    }

    public String getGetedWxType() {
        return getedWxType;
    }

    public String getGetedZfbType() {
        return getedZfbType;
    }

    public boolean isQR() {
        return isQR;
    }

    public boolean isWx() {
        return isWx;
    }

    public void setWx(boolean wx) {
        isWx = wx;
    }

    public int getTotal_feeI() {
        return total_feeI;
    }

    public void setTotal_feeI(int total_feeI) {
        this.total_feeI = total_feeI;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
