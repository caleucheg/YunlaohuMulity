
package com.yang.yunwang.home.mainhome.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ResetPwdReq {

    @SerializedName("NewPassWord")
    private String mNewPassWord;
    @SerializedName("OldPassWord")
    private String mOldPassWord;
    @SerializedName("SysNo")
    private String mSysNo;
    @SerializedName("Password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassWord() {
        return mNewPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        mNewPassWord = newPassWord;
    }

    public String getOldPassWord() {
        return mOldPassWord;
    }

    public void setOldPassWord(String oldPassWord) {
        mOldPassWord = oldPassWord;
    }

    public String getSysNo() {
        return mSysNo;
    }

    public void setSysNo(String sysNo) {
        mSysNo = sysNo;
    }

}
