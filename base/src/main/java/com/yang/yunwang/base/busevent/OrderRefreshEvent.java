package com.yang.yunwang.base.busevent;

public class OrderRefreshEvent {
    boolean isRefresh;
    String from;
    boolean isFromArouter;

    public OrderRefreshEvent(boolean isRefresh, String from, boolean isFromArouter) {

        this.isRefresh = isRefresh;
        this.from = from;
        this.isFromArouter = isFromArouter;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isRefresh() {

        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public boolean isFromArouter() {
        return isFromArouter;
    }

    public void setFromArouter(boolean fromArouter) {
        isFromArouter = fromArouter;
    }
}
