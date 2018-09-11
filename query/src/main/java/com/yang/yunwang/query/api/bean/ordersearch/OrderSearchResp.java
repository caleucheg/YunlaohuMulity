
package com.yang.yunwang.query.api.bean.ordersearch;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class OrderSearchResp {

    @SerializedName("model")
    private List<Model> mModel;
    @SerializedName("totalCount")
    private Long mTotalCount;

    public List<Model> getModel() {
        return mModel;
    }

    public void setModel(List<Model> model) {
        mModel = model;
    }

    public Long getTotalCount() {
        return mTotalCount;
    }

    public void setTotalCount(Long totalCount) {
        mTotalCount = totalCount;
    }

}
