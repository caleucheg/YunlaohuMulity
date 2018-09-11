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
import com.yang.yunwang.query.api.bean.wxplant.bank.WxBankPlantResp;
import com.yang.yunwang.query.api.bean.wxplant.netbank.ReqModel;
import com.yang.yunwang.query.api.bean.wxplant.netbank.WXNetBankPlantReq;
import com.yang.yunwang.query.api.bean.wxplant.netbank.WXNetBankPlantResp;
import com.yang.yunwang.query.api.bean.wxplant.netbank.WxNetTranIDReq;
import com.yang.yunwang.query.api.bean.wxplant.netbank.WxNetTranIdResp;
import com.yang.yunwang.query.api.bean.wxplant.yihui.Data;
import com.yang.yunwang.query.api.bean.wxplant.yihui.MValues;
import com.yang.yunwang.query.api.bean.wxplant.yihui.WxPayData;
import com.yang.yunwang.query.api.bean.wxplant.yihui.WxPlantYihuiReq;
import com.yang.yunwang.query.api.bean.wxplant.yihui.WxPlantYihuiResp;
import com.yang.yunwang.query.api.contract.WXPlantOrderContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/3.
 */

public class WXPlantOrderPresenter implements WXPlantOrderContract.Presenter {
    private final WXPlantOrderContract.View view;
    private final Context context;

    public WXPlantOrderPresenter(WXPlantOrderContract.View view, Context context) {
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
        switch (ConstantUtils.GETED_WX_TYPE) {
            case "102":
                reqOrderinfo(code, isPlOrderNo);
                break;
            case "104":
            case "106":
                reqOrderinfoBank(code, isPlOrderNo);
                break;
            case "108":
                getTransID(code, isPlOrderNo);
                break;
            default:
                Toast.makeText(context, "无法查到此订单！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void getTransID(final String code, boolean isPlOrderNo) {
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
                                KLog.i(value);
                                KLog.i(TextUtils.isEmpty(value.getTransactionId()));
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
        KLog.i(code);
        WXNetBankPlantReq accessToken = new WXNetBankPlantReq();
        ReqModel model = new ReqModel();
        model.setOutTradeNo(code);
        accessToken.setReqModel(model);
        accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
        accessToken.setChannelType("WX");
        QueryReService.getInstance(context)
                .getWXNetBankOrders(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<WXNetBankPlantResp>(context) {
                    @Override
                    protected void doOnNext(WXNetBankPlantResp value) {
                        if (value != null && !TextUtils.isEmpty(value.toString())) {
                            com.yang.yunwang.query.api.bean.wxplant.netbank.Data data = value.getData();
//                            KLog.i(TextUtils.isEmpty(data.getWxPayData("WxPayData")));
                            String codeD = value.getCode() + "";
                            com.yang.yunwang.query.api.bean.wxplant.netbank.WxPayData wxpaydata;
                            if (TextUtils.isEmpty(data.getWxPayData().toString()) || !TextUtils.equals("0", codeD)) {
                                wxpaydata = new com.yang.yunwang.query.api.bean.wxplant.netbank.WxPayData();
                                view.clearInfo();
                                Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                            } else {
                                wxpaydata = data.getWxPayData();
                                KLog.i(wxpaydata);
                                com.yang.yunwang.query.api.bean.wxplant.netbank.MValues m_values = wxpaydata.getMValues();
                                String tradeStatus = m_values.getTradeStatus() != null ? m_values.getTradeStatus() : "";
                                boolean orderExist = TextUtils.isEmpty(tradeStatus);
                                if (orderExist) {
                                    view.clearInfo();
                                    Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                                } else {
                                    if (TextUtils.equals("WX", m_values.getChannelType())) {
                                        String total_fee = !TextUtils.isEmpty(m_values.getTotalAmount()) ? m_values.getTotalAmount() : "0";
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
                                        if (m_values.getGmtPayment() != null) {
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
                                        view.showInfo(code, "网商-微信", money, "CNY", time_end, status, codePl);
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
        accessToken.setPayType("WX");
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        QueryReService.getInstance(context)
                .getWXBankPlantOrders(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<WxBankPlantResp>(context) {
                    @Override
                    protected void doOnNext(WxBankPlantResp value) {
                        if (value != null && !TextUtils.isEmpty(value.toString())) {
                            KLog.i(value);
                            com.yang.yunwang.query.api.bean.wxplant.bank.Data data = value.getData();
                            String codeD = value.getCode() + "";
                            com.yang.yunwang.query.api.bean.wxplant.bank.WxPayData wxpaydata;
                            if (TextUtils.isEmpty(data.getWxPayData().toString()) || !TextUtils.equals("0", codeD)) {
                                view.clearInfo();
                                wxpaydata = new com.yang.yunwang.query.api.bean.wxplant.bank.WxPayData();
                                Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                            } else {
                                wxpaydata = data.getWxPayData();
                                KLog.i(wxpaydata);
                                String result_code = wxpaydata.getResultCode();//SUCCESS
                                boolean orderExist = TextUtils.equals("0", result_code);
                                KLog.i(orderExist);
                                if (!orderExist) {
                                    view.clearInfo();
                                    Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                                } else {
                                    String wxpay_data = wxpaydata.getTradeType();
                                    if (wxpay_data.contains("weixin")) {
                                        String total_fee = wxpaydata.getTotalFee() != null ? wxpaydata.getTotalFee() : "0";
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
                                        String time = wxpaydata.getTimeEnd();
                                        if (time != null && time.length() > 12) {
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
                                        if (TextUtils.equals("104", ConstantUtils.GETED_WX_TYPE)) {
                                            type = "兴业-微信";
                                        } else if (TextUtils.equals("106", ConstantUtils.GETED_WX_TYPE)) {
                                            type = "浦发-微信";
                                        } else {
                                            type = "微信";
                                        }
                                        String money = "";
                                        try {
                                            money = AmountUtils.changeF2Y(total_fee);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        view.showInfo(code, type, money, "CNY", time_end, status, codePl);
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
//                        progressDialog.dismiss();
                        errorSearch(progressDialog);
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
                .getWXYihuiPlantOrders(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<WxPlantYihuiResp>(context) {
                    @Override
                    protected void doOnNext(WxPlantYihuiResp value) {
                        Data data = value.getData();
                        WxPayData wxpaydata;
                        if (TextUtils.isEmpty(data.getWxPayData().toString())) {
                            view.clearInfo();
                            wxpaydata = new WxPayData();
                            Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                        } else {
                            wxpaydata = data.getWxPayData();
                            KLog.i(wxpaydata);
                        }
                        MValues m_values = wxpaydata.getMValues();
                        String result_code = m_values.getResultCode();//SUCCESS
                        String return_code = m_values.getReturnCode();//SUCCESS
                        boolean orderExist = TextUtils.equals("SUCCESS", result_code) && TextUtils.equals("SUCCESS", return_code);
                        if (!orderExist) {
                            view.clearInfo();
                            Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                        } else {
                            String total_fee = m_values.getTotalFee() != null ? m_values.getTotalFee() : "0";
                            String trade_state = m_values.getTradeState();
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
                            if (m_values.getTimeEnd() != null) {
                                String time = m_values.getTimeEnd();
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
                            String code = m_values.getOutTradeNo();
                            String codePl = m_values.getTransactionId();
                            String money = "";
                            try {
                                money = AmountUtils.changeF2Y(total_fee);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            view.showInfo(code, "微信", money, "CNY", time_end, status, codePl);
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Toast.makeText(context, context.getResources().getString(R.string.wx_error_code), Toast.LENGTH_SHORT).show();
                        errorSearch(progressDialog);
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
