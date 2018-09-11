package com.yang.yunwang.base.dao;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PassageWay {

    @SerializedName("PassageWay")
    private String mPassageWay;
    @SerializedName("PassageWayName")
    private String mPassageWayName;
    @SerializedName("Remarks")
    private String mRemarks;
    @SerializedName("SysNo")
    private String mSysNo;
    @SerializedName("Type")
    private String mType;
    @SerializedName("TypeName")
    private String mTypeName;
    @SerializedName("state")
    private String mState;

    public String getPassageWay() {
        return mPassageWay;
    }

    public String getPassageWayName() {
        return mPassageWayName;
    }

    public String getRemarks() {
        return mRemarks;
    }

    public String getSysNo() {
        return mSysNo;
    }

    public String getType() {
        return mType;
    }

    public String getTypeName() {
        return mTypeName;
    }

    public String getState() {
        return mState;
    }

    @Override
    public String toString() {
        return "PassageWay{" +
                "mPassageWay='" + mPassageWay + '\'' +
                ", mPassageWayName='" + mPassageWayName + '\'' +
                ", mRemarks='" + mRemarks + '\'' +
                ", mSysNo='" + mSysNo + '\'' +
                ", mType='" + mType + '\'' +
                ", mState='" + mState + '\'' +
                ", mTypeName='" + mTypeName + '\'' +
                '}';
    }

    public static class Builder {

        private String mPassageWay;
        private String mPassageWayName;
        private String mRemarks;
        private String mSysNo;
        private String mType;
        private String mTypeName;
        private String mState;

        public PassageWay.Builder withState(String state) {
            mState = state;
            return this;
        }

        public PassageWay.Builder withPassageWay(String PassageWay) {
            mPassageWay = PassageWay;
            return this;
        }

        public PassageWay.Builder withPassageWayName(String PassageWayName) {
            mPassageWayName = PassageWayName;
            return this;
        }

        public PassageWay.Builder withRemarks(String Remarks) {
            mRemarks = Remarks;
            return this;
        }

        public PassageWay.Builder withSysNo(String SysNo) {
            mSysNo = SysNo;
            return this;
        }

        public PassageWay.Builder withType(String Type) {
            mType = Type;
            return this;
        }

        public PassageWay.Builder withTypeName(String TypeName) {
            mTypeName = TypeName;
            return this;
        }

        public PassageWay build() {
            PassageWay PassageWay = new PassageWay();
            PassageWay.mPassageWay = mPassageWay;
            PassageWay.mPassageWayName = mPassageWayName;
            PassageWay.mRemarks = mRemarks;
            PassageWay.mSysNo = mSysNo;
            PassageWay.mType = mType;
            PassageWay.mTypeName = mTypeName;
            PassageWay.mState = mState;
            return PassageWay;
        }

    }

}
