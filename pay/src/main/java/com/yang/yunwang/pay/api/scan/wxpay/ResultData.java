
package com.yang.yunwang.pay.api.scan.wxpay;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ResultData {

    @SerializedName("m_values")
    private MValues mMValues;

    public MValues getMValues() {
        return mMValues;
    }

    public void setMValues(MValues mValues) {
        mMValues = mValues;
    }

}
