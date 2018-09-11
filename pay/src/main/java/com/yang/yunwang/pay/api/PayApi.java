package com.yang.yunwang.pay.api;

import com.google.gson.JsonObject;
import com.yang.yunwang.pay.api.qr.closerorderlog.ColseOrderLogReq;
import com.yang.yunwang.pay.api.qr.commonurl.CommonUrlReq;
import com.yang.yunwang.pay.api.qr.netbankquest.NetBankQuestReq;
import com.yang.yunwang.pay.api.qr.netbankurl.NetBankUrlReq;
import com.yang.yunwang.pay.api.scan.bankpay.BankScanPayReq;
import com.yang.yunwang.pay.api.scan.netbank.NetBankScanPayReq;
import com.yang.yunwang.pay.api.scan.netbank.wx.NetBankScanWxPayResp;
import com.yang.yunwang.pay.api.scan.sendmessage.SendMessageReq;
import com.yang.yunwang.pay.api.scan.sendmessage.SendMessageResp;
import com.yang.yunwang.pay.api.scan.snowno.SnowNoReq;
import com.yang.yunwang.pay.api.scan.snowno.SnowNoResp;
import com.yang.yunwang.pay.api.scan.wxpay.WxScanPayReq;
import com.yang.yunwang.pay.api.scan.wxpay.WxScanPayResp;
import com.yang.yunwang.pay.api.scan.zfbpay.ZfbScanPayReq;
import com.yang.yunwang.pay.api.scan.zfbpay.ZfbScanPayResp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/7/19.
 */

public interface PayApi {

    @POST("IPP3OrderSysNo/IPP3Snowflake")
    Observable<SnowNoResp> getSnowNo(@Body SnowNoReq loginRequest);

    @POST("POS/POSOrderInsert")
    Observable<WxScanPayResp> wxScanPay(@Body WxScanPayReq loginRequest);

    @POST("IPP3AliPay/BarcodePayUnion")
    Observable<ZfbScanPayResp> zfbScanPay(@Body ZfbScanPayReq loginRequest);

    @POST("IPP3WSOrder/WSBarcodePayUnion")
    Observable<NetBankScanWxPayResp> netBankScanPay(@Body NetBankScanPayReq loginRequest);

    @POST("IPP3Swiftpass/BarcodePayApiUnion")
    Observable<JsonObject> bankScanPay(@Body BankScanPayReq loginRequest);

//    @POST("IPP3Order/IPP3So_MasterLog")
//    Observable<String> insertLog(@Body InsertOrderLogReq loginRequest);

    @POST("Payment/Payments/SendTemplateMessage")
    Observable<SendMessageResp> sendTemplateMessage(@Body SendMessageReq loginRequest);

    @POST("IPP3WSOrder/WSPayQuery")
    Observable<JsonObject> netbankOrderQuest(@Body NetBankQuestReq loginRequest);

    @POST("IPP3Swiftpass/OrderQueryApi")
    Observable<JsonObject> bankOrderQuest(@Body CommonUrlReq loginRequest);

    @POST("Payment/Payments/QueryWxOrder")
    Observable<JsonObject> wxOrderQuest(@Body CommonUrlReq loginRequest);

    @POST("IPP3AliPay/AliPayquery")
    Observable<JsonObject> zfbOrderQuest(@Body CommonUrlReq loginRequest);

    @POST("IPP3WSOrder/WSJsPayCreateQrCode")
    Observable<JsonObject> netbankQRUrlQuest(@Body NetBankUrlReq loginRequest);

    @POST("IPP3Swiftpass/GetWxQRCodeUrl")
    Observable<JsonObject> bankWxQRUrlQuest(@Body CommonUrlReq loginRequest);

    @POST("IPP3Swiftpass/GetAliQRCodeUrl")
    Observable<JsonObject> bankZfbQRUrlQuest(@Body CommonUrlReq loginRequest);

    @POST("Payment/Payments/GetPayUrl")
    Observable<JsonObject> wxQRUrlQuest(@Body CommonUrlReq loginRequest);

    @POST("IPP3AliPay/GetAliPayUrl")
    Observable<JsonObject> zfbQRUrlQuest(@Body CommonUrlReq loginRequest);

    @POST("IPP3WSOrder/WSPayClose")
    Observable<JsonObject> netbankOrderClose(@Body NetBankQuestReq loginRequest);

    @POST("IPP3Swiftpass/ReverseApi")
    Observable<JsonObject> bankOrderClose(@Body CommonUrlReq loginRequest);

    @POST("Payment/Payments/WXCloseOrder")
    Observable<JsonObject> wxOrderClose(@Body CommonUrlReq loginRequest);

    @POST("IPP3AliPay/AliPayClose")
    Observable<JsonObject> zfbOrderClose(@Body CommonUrlReq loginRequest);

    @POST("IPP3Customers/PayQRCodeLogInsert")
    Observable<JsonObject> closeOrderLogInsert(@Body ColseOrderLogReq loginRequest);

}
