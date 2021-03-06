
package com.yang.yunwang.home.mainhome.bean.ordersearch;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PagingInfo {

    @SerializedName("PageNumber")
    private Long mPageNumber;
    @SerializedName("PageSize")
    private Long mPageSize;

    public Long getPageNumber() {
        return mPageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        mPageNumber = pageNumber;
    }

    public Long getPageSize() {
        return mPageSize;
    }

    public void setPageSize(Long pageSize) {
        mPageSize = pageSize;
    }

}
