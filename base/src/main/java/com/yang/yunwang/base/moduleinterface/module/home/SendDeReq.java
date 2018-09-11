package com.yang.yunwang.base.moduleinterface.module.home;

import java.io.Serializable;

public class SendDeReq implements Serializable{
    public SendDeReq(String startTime, String endTime, String pickBill_PickNo, String orders_OrderNo, String pickBill_TruckNo, String client_ClientName, String DD_Sort_ID, String DD_Spec_ID, int DD_Pack_ID, int DD_Brand_ID, int DD_ProductType_ID, String batchNo_Name, String HTBH, int DD_Factory_ID, String pickBill_State, int userID) {
        StartTime = startTime;
        EndTime = endTime;
        PickBill_PickNo = pickBill_PickNo;
        Orders_OrderNo = orders_OrderNo;
        PickBill_TruckNo = pickBill_TruckNo;
        Client_ClientName = client_ClientName;
        this.DD_Sort_ID = DD_Sort_ID;
        this.DD_Spec_ID = DD_Spec_ID;
        this.DD_Pack_ID = DD_Pack_ID;
        this.DD_Brand_ID = DD_Brand_ID;
        this.DD_ProductType_ID = DD_ProductType_ID;
        BatchNo_Name = batchNo_Name;
        this.HTBH = HTBH;
        this.DD_Factory_ID = DD_Factory_ID;
        PickBill_State = pickBill_State;
        UserID = userID;
    }

    @Override
    public String toString() {
        return "SendDeReq{" +
                "StartTime='" + StartTime + '\'' +
                ", EndTime='" + EndTime + '\'' +
                ", PickBill_PickNo='" + PickBill_PickNo + '\'' +
                ", Orders_OrderNo='" + Orders_OrderNo + '\'' +
                ", PickBill_TruckNo='" + PickBill_TruckNo + '\'' +
                ", Client_ClientName='" + Client_ClientName + '\'' +
                ", DD_Sort_ID='" + DD_Sort_ID + '\'' +
                ", DD_Spec_ID='" + DD_Spec_ID + '\'' +
                ", DD_Pack_ID=" + DD_Pack_ID +
                ", DD_Brand_ID=" + DD_Brand_ID +
                ", DD_ProductType_ID=" + DD_ProductType_ID +
                ", BatchNo_Name='" + BatchNo_Name + '\'' +
                ", HTBH='" + HTBH + '\'' +
                ", DD_Factory_ID=" + DD_Factory_ID +
                ", PickBill_State='" + PickBill_State + '\'' +
                ", UserID=" + UserID +
                '}';
    }

    /**
     * StartTime : 2018-04-10 00:00:00
     * EndTime : 2018-04-15 00:00:00
     * PickBill_PickNo :
     * Orders_OrderNo :
     * PickBill_TruckNo :
     * Client_ClientName :
     * DD_Sort_ID :
     * DD_Spec_ID :
     * DD_Pack_ID : 0
     * DD_Brand_ID : 0
     * DD_ProductType_ID : 0
     * BatchNo_Name :
     * HTBH :
     * DD_Factory_ID : 0
     * PickBill_State :
     * UserID : 1000
     */

    private String StartTime;
    private String EndTime;
    private String PickBill_PickNo;
    private String Orders_OrderNo;
    private String PickBill_TruckNo;
    private String Client_ClientName;
    private String DD_Sort_ID;
    private String DD_Spec_ID;
    private int DD_Pack_ID;
    private int DD_Brand_ID;
    private int DD_ProductType_ID;
    private String BatchNo_Name;
    private String HTBH;
    private int DD_Factory_ID;
    private String PickBill_State;
    private int UserID;

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public String getPickBill_PickNo() {
        return PickBill_PickNo;
    }

    public void setPickBill_PickNo(String PickBill_PickNo) {
        this.PickBill_PickNo = PickBill_PickNo;
    }

    public String getOrders_OrderNo() {
        return Orders_OrderNo;
    }

    public void setOrders_OrderNo(String Orders_OrderNo) {
        this.Orders_OrderNo = Orders_OrderNo;
    }

    public String getPickBill_TruckNo() {
        return PickBill_TruckNo;
    }

    public void setPickBill_TruckNo(String PickBill_TruckNo) {
        this.PickBill_TruckNo = PickBill_TruckNo;
    }

    public String getClient_ClientName() {
        return Client_ClientName;
    }

    public void setClient_ClientName(String Client_ClientName) {
        this.Client_ClientName = Client_ClientName;
    }

    public String getDD_Sort_ID() {
        return DD_Sort_ID;
    }

    public void setDD_Sort_ID(String DD_Sort_ID) {
        this.DD_Sort_ID = DD_Sort_ID;
    }

    public String getDD_Spec_ID() {
        return DD_Spec_ID;
    }

    public void setDD_Spec_ID(String DD_Spec_ID) {
        this.DD_Spec_ID = DD_Spec_ID;
    }

    public int getDD_Pack_ID() {
        return DD_Pack_ID;
    }

    public void setDD_Pack_ID(int DD_Pack_ID) {
        this.DD_Pack_ID = DD_Pack_ID;
    }

    public int getDD_Brand_ID() {
        return DD_Brand_ID;
    }

    public void setDD_Brand_ID(int DD_Brand_ID) {
        this.DD_Brand_ID = DD_Brand_ID;
    }

    public int getDD_ProductType_ID() {
        return DD_ProductType_ID;
    }

    public void setDD_ProductType_ID(int DD_ProductType_ID) {
        this.DD_ProductType_ID = DD_ProductType_ID;
    }

    public String getBatchNo_Name() {
        return BatchNo_Name;
    }

    public void setBatchNo_Name(String BatchNo_Name) {
        this.BatchNo_Name = BatchNo_Name;
    }

    public String getHTBH() {
        return HTBH;
    }

    public void setHTBH(String HTBH) {
        this.HTBH = HTBH;
    }

    public int getDD_Factory_ID() {
        return DD_Factory_ID;
    }

    public void setDD_Factory_ID(int DD_Factory_ID) {
        this.DD_Factory_ID = DD_Factory_ID;
    }

    public String getPickBill_State() {
        return PickBill_State;
    }

    public void setPickBill_State(String PickBill_State) {
        this.PickBill_State = PickBill_State;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }
}
