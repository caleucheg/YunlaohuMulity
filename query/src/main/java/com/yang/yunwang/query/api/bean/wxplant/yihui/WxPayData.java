
package com.yang.yunwang.query.api.bean.wxplant.yihui;

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

    @Override
    public String toString() {
        return "WxPayData{" +
                "mMValues=" + mMValues +
                '}';
    }
}
