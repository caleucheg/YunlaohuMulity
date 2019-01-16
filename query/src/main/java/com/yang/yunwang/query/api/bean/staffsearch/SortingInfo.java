
package com.yang.yunwang.query.api.bean.staffsearch;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SortingInfo {

    @SerializedName("SortField")
    private String mSortField;
    @SerializedName("SortOrder")
    private String mSortOrder;


    public String getSortField() {
        return mSortField;
    }

    public void setSortField(String sortField) {
        mSortField = sortField;
    }

    public String getSortOrder() {
        return mSortOrder;
    }

    public void setSortOrder(String sortOrder) {
        mSortOrder = sortOrder;
    }


}
