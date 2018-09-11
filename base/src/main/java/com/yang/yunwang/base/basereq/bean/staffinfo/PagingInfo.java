
package com.yang.yunwang.base.basereq.bean.staffinfo;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class PagingInfo {

    @SerializedName("PageNumber")
    private String mPageNumber;
    @SerializedName("PageSize")
    private String mPageSize;

    public String getPageNumber() {
        return mPageNumber;
    }

    public void setPageNumber(String pageNumber) {
        mPageNumber = pageNumber;
    }

    public String getPageSize() {
        return mPageSize;
    }

    public void setPageSize(String pageSize) {
        mPageSize = pageSize;
    }

}
