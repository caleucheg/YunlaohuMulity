
package com.yang.yunwang.home.mainhome.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CusClassResp {

    @SerializedName("ClassID")
    private String mClassID;
    @SerializedName("ClassName")
    private String mClassName;
    @SerializedName("Field_One")
    private String mFieldOne;
    @SerializedName("Field_Three")
    private String mFieldThree;
    @SerializedName("Field_Two")
    private String mFieldTwo;
    @SerializedName("SysNo")
    private String mSysNo;
    @SerializedName("TopSysNO")
    private String mTopSysNO;

    public String getClassID() {
        return mClassID;
    }

    public void setClassID(String classID) {
        mClassID = classID;
    }

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        mClassName = className;
    }

    public String getFieldOne() {
        return mFieldOne;
    }

    public void setFieldOne(String fieldOne) {
        mFieldOne = fieldOne;
    }

    public String getFieldThree() {
        return mFieldThree;
    }

    public void setFieldThree(String fieldThree) {
        mFieldThree = fieldThree;
    }

    public String getFieldTwo() {
        return mFieldTwo;
    }

    public void setFieldTwo(String fieldTwo) {
        mFieldTwo = fieldTwo;
    }

    public String getSysNo() {
        return mSysNo;
    }

    public void setSysNo(String sysNo) {
        mSysNo = sysNo;
    }

    public String getTopSysNO() {
        return mTopSysNO;
    }

    public void setTopSysNO(String topSysNO) {
        mTopSysNO = topSysNO;
    }

}
