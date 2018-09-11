package com.yang.yunwang.pay.api;

import android.content.Context;

import com.google.gson.JsonObject;
import com.yang.yunwang.base.ret.HttpsServiceGenerator;
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

/**
 * Created by Administrator on 2018/7/19.
 */

public class PayReService {
    private static PayReService balanceModel;
    private PayApi mBalanceService;

    public static PayReService getInstance(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        if (balanceModel == null) {
            balanceModel = new PayReService(context, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
        }
        return balanceModel;
    }

    public static PayReService getInstance(Context context) {
        if (balanceModel == null) {
            balanceModel = new PayReService(context);
        }
        return balanceModel;
    }

    private PayReService(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        mBalanceService = HttpsServiceGenerator.createService(PayApi.class, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
    }

    private PayReService(Context context) {
        mBalanceService = HttpsServiceGenerator.createService(PayApi.class);
    }

    /**
     * IPP3OrderSysNo/IPP3Snowflake
     *
     * @param accessToken a
     * @return a
     */
    public Observable<SnowNoResp> getSnowNo(SnowNoReq accessToken) {
        return mBalanceService.getSnowNo(accessToken);
    }

    /**
     * POS/POSOrderInsert
     *
     * @param accessToken a
     * @return a
     */
    public Observable<WxScanPayResp> wxScanPay(WxScanPayReq accessToken) {
        return mBalanceService.wxScanPay(accessToken);
    }

    /**
     * IPP3AliPay/BarcodePayUnion
     *
     * @param accessToken a
     * @return a
     */
    public Observable<ZfbScanPayResp> zfbScanPay(ZfbScanPayReq accessToken) {
        return mBalanceService.zfbScanPay(accessToken);
    }

    /**
     * IPP3WSOrder/WSBarcodePayUnion
     *
     * @param accessToken a
     * @return a
     */
    public Observable<NetBankScanWxPayResp> netBankScanPay(NetBankScanPayReq accessToken) {
        return mBalanceService.netBankScanPay(accessToken);
    }

    /**
     * IPP3Swiftpass/BarcodePayApiUnion
     *
     * @param accessToken a
     * @return a
     */
    public Observable<JsonObject> bankScanPay(BankScanPayReq accessToken) {
        return mBalanceService.bankScanPay(accessToken);
    }

//    /**
//     * IPP3Order/IPP3So_MasterLog
//     *
//     * @param accessToken a
//     * @return a
//     */
//    public Observable<String> insertLog(InsertOrderLogReq accessToken) {
//        return mBalanceService.insertLog(accessToken);
//    }

    /**
     * Payment/Payments/SendTemplateMessage
     *
     * @param accessToken a
     * @return a
     */
    public Observable<SendMessageResp> sendTemplateMessage(SendMessageReq accessToken) {
        return mBalanceService.sendTemplateMessage(accessToken);
    }


    /**IPP3WSOrder/WSPayQuery")
     *
     * @param accessToken
     * @return
     */
    public Observable<JsonObject> netbankOrderQuest(NetBankQuestReq accessToken) {
        return mBalanceService.netbankOrderQuest(accessToken);
    }

    /**IPP3Swiftpass/OrderQueryApi")
     *
     * @param accessToken
     * @return
     */
//    Observable<String> (@Body  accessToken);
    public Observable<JsonObject> bankOrderQuest(CommonUrlReq accessToken) {
        return mBalanceService.bankOrderQuest(accessToken);
    }

    /**Payment/Payments/QueryWxOrder")
     *
     * @param accessToken
     * @return
     */
//    Observable<String> (@Body CommonUrlReq accessToken);
    public Observable<JsonObject> wxOrderQuest(CommonUrlReq accessToken) {
        return mBalanceService.wxOrderQuest(accessToken);
    }

    /**IPP3AliPay/AliPayquery")
     *
     * @param accessToken
     * @return
     */
//    Observable<String> (@Body CommonUrlReq accessToken);
    public Observable<JsonObject> zfbOrderQuest(CommonUrlReq accessToken) {
        return mBalanceService.zfbOrderQuest(accessToken);
    }

    /**IPP3WSOrder/WSJsPayCreateQrCode")
     *
     * @param accessToken
     * @return
     */
    public Observable<JsonObject> netbankQRUrlQuest(NetBankUrlReq accessToken) {
        return mBalanceService.netbankQRUrlQuest(accessToken);
    }

    /**IPP3Swiftpass/GetWxQRCodeUrl")
     *
     * @param accessToken
     * @return
     */
    public Observable<JsonObject> bankWxQRUrlQuest(CommonUrlReq accessToken) {
        return mBalanceService.bankWxQRUrlQuest(accessToken);
    }

    /**IPP3Swiftpass/GetWxQRCodeUrl")
     *
     */
//    Observable<String> (@Body CommonUrlReq accessToken);
    public Observable<JsonObject> bankZfbQRUrlQuest(CommonUrlReq accessToken) {
        return mBalanceService.bankZfbQRUrlQuest(accessToken);
    }

    /**Payment/Payments/GetPayUrl")
     *
     * @param accessToken
     * @return
     */
//    Observable<String> (@Body CommonUrlReq accessToken);
    public Observable<JsonObject> wxQRUrlQuest(CommonUrlReq accessToken) {
        return mBalanceService.wxQRUrlQuest(accessToken);
    }

    /**IPP3AliPay/GetAliPayUrl")
     *
     * @param accessToken
     * @return
     */
//    Observable<String> (@Body CommonUrlReq accessToken);
    public Observable<JsonObject> zfbQRUrlQuest(CommonUrlReq accessToken) {
        return mBalanceService.zfbQRUrlQuest(accessToken);
    }

    /**IPP3WSOrder/WSPayClose")
     *
     * @param accessToken
     * @return
     */
//    Observable<String> (@Body  accessToken);
    public Observable<JsonObject> netbankOrderClose(NetBankQuestReq accessToken) {
        return mBalanceService.netbankOrderClose(accessToken);
    }

    /**IPP3Swiftpass/ReverseApi")
     *
     * @param accessToken
     * @return
     */
//    Observable<String> (@Body CommonUrlReq accessToken);
    public Observable<JsonObject> bankOrderClose(CommonUrlReq accessToken) {
        return mBalanceService.bankOrderClose(accessToken);
    }

    /**Payment/Payments/WXCloseOrder")
     *
     * @param accessToken
     * @return
     */
//    Observable<String> (@Body CommonUrlReq accessToken);
    public Observable<JsonObject> wxOrderClose(CommonUrlReq accessToken) {
        return mBalanceService.wxOrderClose(accessToken);
    }

    /**IPP3AliPay/AliPayClose")
     *
     * @param accessToken
     * @return
     */
//    Observable<String> (@Body CommonUrlReq accessToken);
    public Observable<JsonObject> zfbOrderClose(CommonUrlReq accessToken) {
        return mBalanceService.zfbOrderClose(accessToken);
    }

    /**IPP3Customers/PayQRCodeLogInsert")
     *
     * @param accessToken
     * @return
     */
//    Observable<String> (@Body ColseOrderLogReq accessToken);
    public Observable<JsonObject> closeOrderLogInsert(ColseOrderLogReq accessToken) {
        return mBalanceService.closeOrderLogInsert(accessToken);
    }
}
