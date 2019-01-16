package com.yang.yunwang.base.busevent;

public class ReportStopRefreshEvent {
    boolean isRefreshStop;
    String fromW;

    public ReportStopRefreshEvent(boolean isRefresh, String fromW) {
        this.isRefreshStop = isRefresh;
        this.fromW = fromW;
    }

    public boolean isRefreshStop() {
        return isRefreshStop;
    }

    public void setRefreshStop(boolean refreshStop) {
        isRefreshStop = refreshStop;
    }

    public String getFromW() {
        return fromW;
    }

    public void setFromW(String fromW) {
        this.fromW = fromW;
    }
}
