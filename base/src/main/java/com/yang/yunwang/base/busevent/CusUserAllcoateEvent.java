package com.yang.yunwang.base.busevent;

public class CusUserAllcoateEvent {
    String tradeType;
    String timeType;
    String startTime;
    String endTime;
    String diplayName;
    String loginName;
    String remarks;

    public CusUserAllcoateEvent(String tradeType, String timeType, String startTime, String endTime, String diplayName, String loginName, String remarks) {
        this.tradeType = tradeType;
        this.timeType = timeType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.diplayName = diplayName;
        this.loginName = loginName;
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "CusUserAllcoateEvent{" +
                "tradeType='" + tradeType + '\'' +
                ", timeType='" + timeType + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", diplayName='" + diplayName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDiplayName() {
        return diplayName;
    }

    public void setDiplayName(String diplayName) {
        this.diplayName = diplayName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
