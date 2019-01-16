package com.yang.yunwang.base.busevent;

public class ReportFilterEvent {
    boolean isAllCus;
    String cusNo;
    boolean isTimeFilter;
    String timeStart;
    String timeEnd;
    String cusName;

    public ReportFilterEvent(boolean isAllCus, String cusNo, boolean isTimeFilter, String timeStart, String timeEnd, String cusName) {

        this.isAllCus = isAllCus;
        this.cusNo = cusNo;
        this.isTimeFilter = isTimeFilter;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.cusName = cusName;
    }

    public boolean isAllCus() {
        return isAllCus;
    }

    public void setAllCus(boolean allCus) {
        isAllCus = allCus;
    }

    @Override
    public String toString() {
        return "ReportFilterEvent{" +
                "isAllCus=" + isAllCus +
                ", cusNo='" + cusNo + '\'' +
                ", isTimeFilter=" + isTimeFilter +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", cusName='" + cusName + '\'' +
                '}';
    }

    public String getCusNo() {
        return cusNo;
    }

    public void setCusNo(String cusNo) {
        this.cusNo = cusNo;
    }

    public boolean isTimeFilter() {
        return isTimeFilter;
    }

    public void setTimeFilter(boolean timeFilter) {
        isTimeFilter = timeFilter;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }
}
