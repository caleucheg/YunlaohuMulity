package com.yang.yunwang.base.busevent;

public class OrderFilterEvent {
    String orderNum;
    String cusLoginName;
    String cusName;
    String tradeType;
    String startTime;
    String endTime;
    boolean isFromA;

    public OrderFilterEvent(String orderNum, String cusLoginName, String cusName, String tradeType, String startTime, String endTime, boolean isFromA) {
        this.orderNum = orderNum;
        this.cusLoginName = cusLoginName;
        this.cusName = cusName;
        this.tradeType = tradeType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isFromA = isFromA;
    }

    public boolean isFromA() {
        return isFromA;
    }

    public void setFromA(boolean fromA) {
        isFromA = fromA;
    }

    @Override
    public String toString() {
        return "OrderFilterEvent{" +
                "orderNum='" + orderNum + '\'' +
                ", cusLoginName='" + cusLoginName + '\'' +
                ", cusName='" + cusName + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", isFromA=" + isFromA +
                '}';
    }

    public String getOrderNum() {
        return orderNum;
    }

    public String getCusLoginName() {
        return cusLoginName;
    }

    public String getCusName() {
        return cusName;
    }

    public String getTradeType() {
        return tradeType;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
