
package com.yang.yunwang.home.mainhome.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CommonClassProReq {

    @SerializedName("ClassID")
    private Long mClassID;
    @SerializedName("TopSysNO")
    private String mTopSysNO;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @SerializedName("Parent_id")
    private String parentId;

    public Long getClassID() {
        return mClassID;
    }

    public void setClassID(Long classID) {
        mClassID = classID;
    }

    public String getTopSysNO() {
        return mTopSysNO;
    }

    public void setTopSysNO(String topSysNO) {
        mTopSysNO = topSysNO;
    }

}
