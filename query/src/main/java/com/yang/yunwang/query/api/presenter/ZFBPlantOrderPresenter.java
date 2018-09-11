package com.yang.yunwang.query.api.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.basereq.BaseInfoService;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigReq;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigResp;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.QueryReService;
import com.yang.yunwang.query.api.bean.wxplant.bank.WxBankPlantReq;
import com.yang.yunwang.query.api.bean.wxplant.netbank.ReqModel;
import com.yang.yunwang.query.api.bean.wxplant.netbank.WXNetBankPlantReq;
import com.yang.yunwang.query.api.bean.wxplant.netbank.WxNetTranIDReq;
import com.yang.yunwang.query.api.bean.wxplant.netbank.WxNetTranIdResp;
import com.yang.yunwang.query.api.bean.wxplant.yihui.WxPlantYihuiReq;
import com.yang.yunwang.query.api.bean.zfbplant.bank.ZFBBankPlantResp;
import com.yang.yunwang.query.api.bean.zfbplant.newbank.ZFBNetBankPlantResp;
import com.yang.yunwang.query.api.bean.zfbplant.yihui.ZFBYihuiPlantResp;
import com.yang.yunwang.query.api.contract.ZFBPlantOrderContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 */

public class ZFBPlantOrderPresenter implements ZFBPlantOrderContract.Presenter {
    private final ZFBPlantOrderContract.View view;
    private final Context context;

    public ZFBPlantOrderPresenter(ZFBPlantOrderContract.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        getPayConfig(ConstantUtils.HIGHER_SYS_NO);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void requestPlatformInfo(String code, boolean isPlOrderNo) {
        switch (ConstantUtils.GETED_ZFB_TYPE) {
            case "103":
                reqOrderinfo(code, isPlOrderNo);
                break;
            case "105":
            case "107":
                reqOrderinfoBank(code, isPlOrderNo);
                break;
            case "109":
                getTransID(code, isPlOrderNo);
                break;
            default:
                Toast.makeText(context, "无法查到此订单！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void getTransID(String code, boolean isPlOrderNo) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (isPlOrderNo) {
            reqOrderinfoNetBank(code, progressDialog);
        } else {
            WxNetTranIDReq accessToken = new WxNetTranIDReq();
            accessToken.setOutTradeNo(code);
            accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
            QueryReService.getInstance(context)
                    .getWXTransID(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<WxNetTranIdResp>(context) {
                        @Override
                        protected void doOnNext(WxNetTranIdResp value) {
                            if (value != null && !TextUtils.isEmpty(value.toString())) {
                                if (!TextUtils.isEmpty(value.getTransactionId())) {
                                    String transaction_id = value.getTransactionId();
                                    if (TextUtils.isEmpty(transaction_id) || TextUtils.equals("null", transaction_id)) {
                                        errorSearch(progressDialog);
                                    } else {
                                        reqOrderinfoNetBank(transaction_id, progressDialog);
                                    }
                                } else {
                                    errorSearch(progressDialog);
                                }
                            } else {
                                errorSearch(progressDialog);
                            }
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                            errorSearch(progressDialog);
                        }
                    });
        }

    }

    private void errorSearch(ProgressDialog progressDialog) {
        progressDialog.dismiss();
        view.clearInfo();
        Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
    }

    private void reqOrderinfoNetBank(final String code, final ProgressDialog progressDialog) {
        WXNetBankPlantReq accessToken = new WXNetBankPlantReq();
        ReqModel model = new ReqModel();
        model.setOutTradeNo(code);
        accessToken.setReqModel(model);
        accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
        accessToken.setChannelType("ALI");
        QueryReService.getInstance(context)
                .getZFBNetBankOrders(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ZFBNetBankPlantResp>(context) {
                    @Override
                    protected void doOnNext(ZFBNetBankPlantResp result) {
                        if (result != null && !TextUtils.isEmpty(result.toString())) {
                            KLog.i(result);
                            com.yang.yunwang.query.api.bean.zfbplant.newbank.Data data = result.getData();
                            com.yang.yunwang.query.api.bean.zfbplant.newbank.WxPayData wxpaydata;
                            String codeD = result.getCode()+"";
                            if (TextUtils.isEmpty(data.getWxPayData().toString()) || !TextUtils.equals("0", codeD)) {
                                wxpaydata = new com.yang.yunwang.query.api.bean.zfbplant.newbank.WxPayData();
                                view.clearInfo();
                                Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                            } else {
                                wxpaydata = data.getWxPayData();
                                KLog.i(wxpaydata);
                                com.yang.yunwang.query.api.bean.zfbplant.newbank.MValues m_values = wxpaydata.getMValues();
                                String tradeStatus =!TextUtils.isEmpty( m_values.getTradeStatus() )? m_values.getTradeStatus() : "";
                                boolean orderExist = TextUtils.isEmpty(tradeStatus);
                                if (orderExist) {
                                    view.clearInfo();
                                    Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                                } else {
                                    if (TextUtils.equals("ALI", m_values.getChannelType())) {
                                        String total_fee =! TextUtils.isEmpty(m_values.getTotalAmount()) ? m_values.getTotalAmount() : "0";
                                        String status = "";
                                        switch (tradeStatus) {
                                            case "succ":
                                                status = "支付成功";
                                                break;
                                            case "fail":
                                                status = "失败";
                                                break;
                                            case "paying":
                                                status = "支付中";
                                                break;
                                            case "closed":
                                                status = "已关单";//"已撤销";
                                                break;
                                            case "cancel":
                                                status = "已撤消";
                                                break;
                                        }
                                        String time_end;
                                        if (!TextUtils.isEmpty(m_values.getGmtPayment())) {
                                            String time = m_values.getGmtPayment();
                                            time_end = time;
                                        } else {
                                            time_end = "无";
                                        }
                                        String code = m_values.getPayChannelOrderNo();
                                        String codePl = m_values.getOutTradeNo();
                                        String money = "";
                                        try {
                                            money = AmountUtils.changeF2Y(total_fee);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        view.showInfo(code, "网商-支付宝", money, "CNY", time_end, status, codePl);

                                    } else {
                                        view.clearInfo();
                                        Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                        } else {
                            view.clearInfo();
                            Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        progressDialog.dismiss();
                    }
                });
    }

    private void reqOrderinfoBank(final String code, boolean isPlOrderNo) {
        WxBankPlantReq accessToken = new WxBankPlantReq();
        if (isPlOrderNo) {
            accessToken.setOutTradeNo(code);
            accessToken.setTransactionId("");
        } else {
            accessToken.setOutTradeNo("");
            accessToken.setTransactionId(code);
        }
        accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
        accessToken.setPayType("AliPay");
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        QueryReService.getInstance(context)
                .getZFBBankPlantOrders(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ZFBBankPlantResp>(context) {
                    @Override
                    protected void doOnNext(ZFBBankPlantResp value) {
                        if (value != null && !TextUtils.isEmpty(value.toString())) {
                            com.yang.yunwang.query.api.bean.zfbplant.bank.Data dataA = value.getData();
                            com.yang.yunwang.query.api.bean.zfbplant.bank.WxPayData wxpaydata;
                            String codeD = value.getCode() + "";
                            if (TextUtils.isEmpty(dataA.getWxPayData().toString()) || !TextUtils.equals("0", codeD)) {
                                view.clearInfo();
                                wxpaydata = new com.yang.yunwang.query.api.bean.zfbplant.bank.WxPayData();
                                Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                            } else {
                                wxpaydata = dataA.getWxPayData();
                                KLog.i(wxpaydata);
                                String result_code = wxpaydata.getResultCode();//SUCCESS
                                boolean orderExist = TextUtils.equals("0", result_code);
                                if (!orderExist) {
                                    view.clearInfo();
                                    Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                                } else {
                                    String wxpay_data = wxpaydata.getTradeType();
                                    if (wxpay_data.contains("alipay")) {
                                        String total_fee = !TextUtils.isEmpty(wxpaydata.getTotalFee()) ? wxpaydata.getTotalFee() : "0";
                                        String trade_state = wxpaydata.getTradeState();
                                        String status = "";
                                        switch (trade_state) {
                                            case "SUCCESS":
                                                status = "支付成功";
                                                break;
                                            case "REFUND":
                                                status = "转入退款";
                                                break;
                                            case "NOTPAY":
                                                status = "未支付";
                                                break;
                                            case "CLOSED":
                                                status = "已关闭";
                                                break;
                                            case "REVOKED":
                                                status = "已撤销（刷卡支付）";
                                                break;
                                            case "USERPAYING":
                                                status = "用户支付中";
                                                break;
                                            case "PAYERROR":
                                                status = "支付失败";
                                                break;
                                        }
                                        String time_end;
                                        if (!TextUtils.isEmpty(wxpaydata.getTimeEnd())) {
                                            String time = wxpaydata.getTimeEnd();
                                            String year = time.substring(0, 4);
                                            String month = time.substring(4, 6);
                                            String day = time.substring(6, 8);
                                            String hour = time.substring(8, 10);
                                            String minute = time.substring(10, 12);
                                            String second = time.substring(12, 14);
                                            time_end = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
                                        } else {
                                            time_end = "无";
                                        }
                                        String codePl = wxpaydata.getOutTradeNo();
                                        String code = wxpaydata.getTransactionId();
                                        String type;
                                        if (TextUtils.equals("105", ConstantUtils.GETED_ZFB_TYPE)) {
                                            type = "兴业-支付宝";
                                        } else if (TextUtils.equals("107", ConstantUtils.GETED_ZFB_TYPE)) {
                                            type = "浦发-支付宝";
                                        } else {
                                            type = "支付宝";
                                        }
                                        String money = "";
                                        try {
                                            money = AmountUtils.changeF2Y(total_fee);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        view.showInfo(code, type, money, "CNY", time_end, status, codePl);

                                    } else {
                                        Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                                        view.clearInfo();
                                    }
                                }
                            }

                        } else {
                            Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                            view.clearInfo();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        progressDialog.dismiss();
                    }
                });

    }


    private void reqOrderinfo(String code, boolean isPlOrderNo) {
        WxPlantYihuiReq accessToken = new WxPlantYihuiReq();
        if (isPlOrderNo) {
            accessToken.setOutTradeNo("");
            accessToken.setTransactionId(code);
        } else {
            accessToken.setOutTradeNo(code);
        }
        accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        QueryReService.getInstance(context)
                .getZFBYihuiPlantOrders(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ZFBYihuiPlantResp>(context) {
                    @Override
                    protected void doOnNext(ZFBYihuiPlantResp value) {
                        try {
                            if (value != null && !TextUtils.isEmpty(value.toString())) {
                                String success = value.getCode() + "";
                                com.yang.yunwang.query.api.bean.zfbplant.yihui.Data dataA = value.getData();
                                JSONObject wxpaydata = null;
                                wxpaydata = new JSONObject(dataA.getWxPayData());
                                JSONObject m_values = new JSONObject(wxpaydata.getString("alipay_trade_query_response"));
                                if (!success.equals("0")) {
                                    String err_code = m_values.getString("code");
                                    if (err_code.equals("40004")) {
                                        view.clearInfo();
                                        Toast.makeText(context, context.getResources().getString(R.string.zfb_error_code), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    String total_fee = m_values.getString("total_amount");
                                    String trade_state = m_values.getString("trade_status");
                                    String status = "";
                                    switch (trade_state) {
                                        case "WAIT_BUYER_PAY":
                                            status = "交易创建，等待买家付款";
                                            break;
                                        case "TRADE_CLOSED":
                                            status = "未付款交易超时关闭，或支付完成后全额退款";
                                            break;
                                        case "TRADE_SUCCESS":
                                            status = "交易支付成功";
                                            break;
                                        case "TRADE_FINISHED":
                                            status = "交易结束，不可退款";
                                            break;
                                    }
                                    String time = m_values.getString("send_pay_date");
                                    String code = m_values.getString("out_trade_no");
                                    String codePl = m_values.getString("trade_no");
                                    view.showInfo(code, "支付宝", total_fee, "CNY", time, status, codePl);
                                }
                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.zfb_error_result), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        progressDialog.dismiss();
                    }
                });
    }

    private void getPayConfig(String higherSysNo) {
        PayConfigReq accessToken = new PayConfigReq();
        accessToken.setCustomerSysNo(higherSysNo);
        BaseInfoService.getInstance(context)
                .getPayConfig(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new BaseObserver<List<PayConfigResp>>(context) {
                    @Override
                    protected void doOnNext(List<PayConfigResp> value) {
                        if (value.size() != 0) {
                            for (int i = 0; i < value.size(); i++) {
                                String remarks = value.get(i).getRemarks();
                                if (TextUtils.equals(remarks, "WX")) {
                                    ConstantUtils.GETED_WX_TYPE = value.get(i).getType() + "";
                                } else if (TextUtils.equals(remarks, "AliPay")) {
                                    ConstantUtils.GETED_ZFB_TYPE = value.get(i).getType() + "";
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }
}
