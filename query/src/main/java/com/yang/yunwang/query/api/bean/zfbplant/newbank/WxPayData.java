
package com.yang.yunwang.query.api.bean.zfbplant.newbank;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class WxPayData {

    @SerializedName("m_values")
    private MValues mMValues;

    public MValues getMValues() {
        return mMValues;
    }

    public void setMValues(MValues mValues) {
        mMValues = mValues;
    }

}
