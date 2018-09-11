package com.yang.yunwang.pay.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.socks.library.KLog;
import com.yang.yunwang.base.dao.PayLogBean;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.PayLogQueu;
import com.yang.yunwang.pay.R;
import com.yang.yunwang.pay.api.PayReService;
import com.yang.yunwang.pay.api.PayReStringService;
import com.yang.yunwang.pay.api.qr.closerorderlog.ColseOrderLogReq;
import com.yang.yunwang.pay.api.qr.commonurl.CommonUrlReq;
import com.yang.yunwang.pay.api.qr.netbankquest.NetBankQuestReq;
import com.yang.yunwang.pay.api.qr.netbankquest.ReqModel;
import com.yang.yunwang.pay.api.qr.netbankurl.NetBankUrlReq;
import com.yang.yunwang.pay.api.scan.insertorder.InsertOrderLogReq;
import com.yang.yunwang.pay.common.PayScUtils;
import com.yang.yunwang.pay.contract.QrCodeContract;
import com.yang.yunwang.pay.view.qr.QRCodeActivity;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/18.
 */

public class QrCodePresenter implements QrCodeContract.Presenter {
    private final QrCodeContract.View view;
    private Handler handler;
    private Context context;
    private int time = 0;//支付倒计时，在生成二维码时，赋值60
    private Timer timer;
    private String data_code;
    private ProgressDialog progressDialog;

    private int orderWay = 0;

    private int result_flag = 0;//验证并判断第0秒发送最后一次请求是否有结果，有结果为1,0为没结果
    //    private boolean isBank = false;
    private String fee;
    private boolean wxIsBank;
    private boolean zfbIsBank;
    private boolean isWX;
    private String reMarks;
    private boolean isWXNetBank = false;
    private boolean isZFbNetBank = false;
    private String totalFee;
    private int logCount = 0;
    private PayLogBean bean;
    private int connect_time_out = 80000;

    public QrCodePresenter(QrCodeContract.View view, final Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        //Done switch version
        if (!DycLibIntent.hasModule()) {
            if (!JPushInterface.getConnectionState(context)) {
                KLog.i("dis-conn");
                JPushInterface.resumePush(context);
            }
            if (JPushInterface.isPushStopped(context)) {
                KLog.i("stop-push");
                JPushInterface.resumePush(context);
            }
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        handler = new Handler(context.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                Toast.makeText(QrCodePresenter.this.context, "" + time, Toast.LENGTH_SHORT).show();
                result_flag = 0;
                if (time > 0) {
                    //60秒每隔3秒轮询查询平台订单号状态
                    requestOrder();
                } else {
                    closeOrder();
                    if (time <= 0) {
                        KLog.i(time);
                        try {
                            if ((wxIsBank && isWX) || (zfbIsBank && !isWX)) {
                                if ((isWXNetBank && isWX) || (isZFbNetBank && !isWX)) {
                                    NetBankQuestReq params = new NetBankQuestReq();
                                    ReqModel mapReq = new ReqModel();
                                    mapReq.setOutTradeNo(data_code);
                                    params.setReqModel(mapReq);
                                    params.setSystemUserSysNo(ConstantUtils.SYS_NO);
                                    params.setChannelType(isWX ? "WX" : "ALI");
                                    PayReService.getInstance(context)
                                            .netbankOrderQuest(params)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new BaseObserver<JsonObject>(context) {
                                                @Override
                                                protected void doOnNext(JsonObject value) {
                                                    try {
                                                        JSONObject result = new JSONObject(value.toString());
                                                        if (result != null && !TextUtils.isEmpty(result.toString())) {
                                                            KLog.i(result);
                                                            if ((isWXNetBank && isWX) || (isZFbNetBank && !isWX)) {
                                                                int code = result.getInt("Code");
                                                                if (code == 0) {
                                                                    JSONObject data = result.getJSONObject("Data");
                                                                    JSONObject wxpaydata = data.getJSONObject("WxPayData");
                                                                    String trade_state = wxpaydata.getJSONObject("m_values").getString("TradeStatus");
                                                                    if (trade_state.equals("succ")) {
                                                                        paySuccess();
                                                                    }
                                                                }
                                                                result_flag = 1;
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                @Override
                                                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                                                }
                                            });
                                } else {

                                    CommonUrlReq params = new CommonUrlReq();
                                    params.setPayType(reMarks);
                                    params.setOutTradeNo(data_code);
                                    params.setSystemUserSysNo(ConstantUtils.SYS_NO);
                                    PayReService.getInstance(context)
                                            .bankOrderQuest(params)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new BaseObserver<JsonObject>(context) {
                                                @Override
                                                protected void doOnNext(JsonObject value) {
                                                    try {
                                                        JSONObject result = new JSONObject(value.toString());
                                                        if (result != null && !TextUtils.isEmpty(result.toString())) {
                                                            String description = result.getString("Description");
                                                            JSONObject data = result.getJSONObject("Data");
                                                            JSONObject wxpaydata = data.getJSONObject("WxPayData");
                                                            if (!description.equals("ORDERNOTEXIST")) {
                                                                String trade_state = wxpaydata.getString("trade_state");
                                                                if (trade_state.equals("SUCCESS")) {
                                                                    playSc();
                                                                    QrCodePresenter.this.view.setResult(QrCodePresenter.this.context.getResources().getString(R.string.qr_success), QrCodePresenter.this.context.getResources().getColor(R.color.label_qr_success), orderWay);
                                                                }
                                                            }
                                                            result_flag = 1;
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                @Override
                                                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                                                }
                                            });

                                }

                            } else {
                                switch (orderWay) {
                                    case 0:
                                        CommonUrlReq accessToken = new CommonUrlReq();
                                        accessToken.setOutTradeNo(data_code);
                                        accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
                                        PayReService.getInstance(context)
                                                .wxOrderQuest(accessToken)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new BaseObserver<JsonObject>(context) {
                                                    @Override
                                                    protected void doOnNext(JsonObject value) {
                                                        try {
                                                            JSONObject result = new JSONObject(value.toString());
                                                            if (result != null && !TextUtils.isEmpty(result.toString())) {
                                                                String description = result.getString("Description");
                                                                JSONObject data = result.getJSONObject("Data");
                                                                JSONObject wxpaydata = data.getJSONObject("WxPayData");
                                                                JSONObject m_values = wxpaydata.getJSONObject("m_values");
                                                                if (!description.equals("ORDERNOTEXIST")) {
                                                                    String trade_state = m_values.getString("trade_state");
                                                                    if (trade_state.equals("SUCCESS")) {
                                                                        playSc();
                                                                        QrCodePresenter.this.view.setResult(QrCodePresenter.this.context.getResources().getString(R.string.qr_success), QrCodePresenter.this.context.getResources().getColor(R.color.label_qr_success), orderWay);
                                                                    }
                                                                }
                                                                result_flag = 1;
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                                                    }
                                                });

                                        break;
                                    case 1:
                                        CommonUrlReq accessToken1 = new CommonUrlReq();
                                        accessToken1.setOutTradeNo(data_code);
                                        accessToken1.setSystemUserSysNo(ConstantUtils.SYS_NO);
                                        PayReService.getInstance(context)
                                                .zfbOrderQuest(accessToken1)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new BaseObserver<JsonObject>(context) {
                                                    @Override
                                                    protected void doOnNext(JsonObject value) {
                                                        try {
                                                            JSONObject result = new JSONObject(value.toString());
                                                            if (result != null && !TextUtils.isEmpty(result.toString())) {
                                                                String success = result.getString("Code");
                                                                JSONObject data = result.getJSONObject("Data");
                                                                JSONObject wxpaydata = new JSONObject(data.getString("WxPayData"));
                                                                JSONObject m_values = new JSONObject(wxpaydata.getString("alipay_trade_query_response"));
                                                                if (success.equals("0")) {
                                                                    String trade_state = m_values.getString("trade_status");
                                                                    if (trade_state.equals("TRADE_SUCCESS")) {
                                                                        playSc();
                                                                        QrCodePresenter.this.view.setResult(QrCodePresenter.this.context.getResources().getString(R.string.qr_success), QrCodePresenter.this.context.getResources().getColor(R.color.label_qr_success), orderWay);
                                                                    }
                                                                }
                                                                result_flag = 1;
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                                                    }
                                                });

                                        break;
                                }
                            }
                        } catch (Exception ex) {
                            Log.e("error", ex.getMessage());
                        } finally {
                            if (timer != null) {
                                timer.cancel();
                                timer = null;
                            }
                        }
                    }
                }
            }
        };
    }

    private void errorPay() {
        QrCodePresenter.this.view.clearQR();
        QrCodePresenter.this.view.setResult(QrCodePresenter.this.context.getResources().getString(R.string.qr_error), QrCodePresenter.this.context.getResources().getColor(R.color.label_qr_error), orderWay);
//        closeOrder();
    }

    @Override
    public void requestQRURL(int orderWay, String total_fee, final boolean wxIsBank, final boolean zfbIsBank) {
        this.orderWay = orderWay;
        this.isWX = orderWay == 0;
        this.reMarks = isWX ? "WX" : "AliPay";
        this.wxIsBank = wxIsBank;
        this.zfbIsBank = zfbIsBank;
        this.fee = total_fee;
        isWXNetBank = false;
        isZFbNetBank = false;
        KLog.i("---pay" + wxIsBank + isWX + zfbIsBank + reMarks);
        time = 80;
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.show();
        connect_time_out = 80000;
        final int finalTotal_fee = Integer.parseInt(AmountUtils.changeY2F(total_fee));
        BaseObserver<JsonObject> observer = new BaseObserver<JsonObject>(context) {
            @Override
            protected void doOnNext(JsonObject value) {
                try {
                    JSONObject result = new JSONObject(value.toString());
                    if (result != null && !TextUtils.isEmpty(result.toString())) {
                        KLog.i(result);
                        String success = result.getString("Code");
                        if (success.equals("0")) {
                            String result_url;
                            if ((wxIsBank && isWX) || (zfbIsBank && !isWX)) {
                                KLog.i("bank---pay" + wxIsBank + isWX + zfbIsBank);
                                if ((TextUtils.equals(ConstantUtils.GETED_WX_TYPE, "108") && isWX) || (TextUtils.equals(ConstantUtils.GETED_ZFB_TYPE, "109") && !isWX)) {
                                    result_url = result.getJSONObject("Data").getString("QrCodeUrl");
                                    data_code = result.getJSONObject("Data").getString("OutTradeNo");
                                    time = 150;
                                    connect_time_out = 150000;
                                } else {
                                    result_url = result.getJSONObject("Data").getJSONObject("PayData").getString("code_url");
                                    data_code = result.getJSONObject("Data").getJSONObject("PayData").getString("out_trade_no");
                                }
                            } else {
                                KLog.i("bank---pay" + wxIsBank + isWX + zfbIsBank);
                                result_url = result.getString("Description");
                                data_code = result.getString("Data");
                            }
                            int timeSC = 0;
                            insertOrderLog(isWX, finalTotal_fee, data_code, timeSC);
                            bean = new PayLogBean(isWX, finalTotal_fee, data_code, ConstantUtils.GETED_WX_TYPE, ConstantUtils.GETED_ZFB_TYPE, true);
                            view.creatQRCode(result_url);
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    Message msg = handler.obtainMessage();
                                    handler.sendMessage(msg);
                                    time = time - 3;
                                }
                            };
                            if (timer == null) {
                                timer = new Timer();
                            } else {
                                timer.cancel();
                                timer = null;
                                timer = new Timer();
                            }
                            timer.schedule(task, 0, 3000);
                        } else {
                            Toast.makeText(context, "请求二维码失败,请退出重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    Log.e("error", ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                progressDialog.dismiss();
            }
        };

        try {
            boolean isNetBank = (isWXNetBank && isWX) || (isZFbNetBank && !isWX);
            switch (orderWay) {
                case 0:
                    total_fee = AmountUtils.changeY2F(total_fee);
                    if (wxIsBank) {
                        if (TextUtils.equals("108", ConstantUtils.GETED_WX_TYPE)) {
                            isWXNetBank = true;
                            NetBankUrlReq accessToken = new NetBankUrlReq();
                            com.yang.yunwang.pay.api.qr.netbankurl.ReqModel mapReq = new com.yang.yunwang.pay.api.qr.netbankurl.ReqModel();
                            mapReq.setTotalAmount(total_fee);
                            mapReq.setChannelType(isWX ? "WX" : "ALI");
                            accessToken.setReqModel(mapReq);
                            accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
                            if (isWX && TextUtils.isEmpty(ConstantUtils.GETED_WX_TYPE)) {
                                Toast.makeText(context, "支付失败,请退出重试", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else if (!isWX && TextUtils.isEmpty(ConstantUtils.GETED_ZFB_TYPE)) {
                                Toast.makeText(context, "支付失败,请退出重试", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                PayReService.getInstance(context)
                                        .netbankQRUrlQuest(accessToken)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(observer);
                            }

                        } else {
                            CommonUrlReq accessToken = new CommonUrlReq();
                            accessToken.setTotalFee(total_fee);
                            accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
                            if (isWX && TextUtils.isEmpty(ConstantUtils.GETED_WX_TYPE)) {
                                Toast.makeText(context, "支付失败,请退出重试", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else if (!isWX && TextUtils.isEmpty(ConstantUtils.GETED_ZFB_TYPE)) {
                                Toast.makeText(context, "支付失败,请退出重试", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                PayReService.getInstance(context)
                                        .bankWxQRUrlQuest(accessToken)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(observer);
                            }

                        }
                    } else {
                        CommonUrlReq accessToken = new CommonUrlReq();
                        accessToken.setTotalFee(total_fee);
                        accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
                        if (isWX && TextUtils.isEmpty(ConstantUtils.GETED_WX_TYPE)) {
                            Toast.makeText(context, "支付失败,请退出重试", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else if (!isWX && TextUtils.isEmpty(ConstantUtils.GETED_ZFB_TYPE)) {
                            Toast.makeText(context, "支付失败,请退出重试", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            PayReService.getInstance(context)
                                    .wxQRUrlQuest(accessToken)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(observer);
                        }

                    }
                    break;
                case 1:
                    total_fee = AmountUtils.changeY2F(total_fee);
                    if (zfbIsBank) {
                        if (TextUtils.equals("109", ConstantUtils.GETED_ZFB_TYPE)) {

                            isZFbNetBank = true;
                            NetBankUrlReq accessToken = new NetBankUrlReq();
                            com.yang.yunwang.pay.api.qr.netbankurl.ReqModel mapReq = new com.yang.yunwang.pay.api.qr.netbankurl.ReqModel();
                            mapReq.setTotalAmount(total_fee);
                            mapReq.setChannelType(isWX ? "WX" : "ALI");
                            accessToken.setReqModel(mapReq);
                            accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
                            if (isWX && TextUtils.isEmpty(ConstantUtils.GETED_WX_TYPE)) {
                                Toast.makeText(context, "支付失败,请退出重试", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else if (!isWX && TextUtils.isEmpty(ConstantUtils.GETED_ZFB_TYPE)) {
                                Toast.makeText(context, "支付失败,请退出重试", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                PayReService.getInstance(context)
                                        .netbankQRUrlQuest(accessToken)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(observer);
                            }

                        } else {
                            CommonUrlReq accessToken = new CommonUrlReq();
                            accessToken.setTotalFee(total_fee);
                            accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
                            if (isWX && TextUtils.isEmpty(ConstantUtils.GETED_WX_TYPE)) {
                                Toast.makeText(context, "支付失败,请退出重试", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else if (!isWX && TextUtils.isEmpty(ConstantUtils.GETED_ZFB_TYPE)) {
                                Toast.makeText(context, "支付失败,请退出重试", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                PayReService.getInstance(context)
                                        .bankZfbQRUrlQuest(accessToken)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(observer);
                            }

                        }
                    } else {
                        CommonUrlReq accessToken = new CommonUrlReq();
                        accessToken.setTotalFee(total_fee);
                        accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
                        if (isWX && TextUtils.isEmpty(ConstantUtils.GETED_WX_TYPE)) {
                            Toast.makeText(context, "支付失败,请退出重试", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else if (!isWX && TextUtils.isEmpty(ConstantUtils.GETED_ZFB_TYPE)) {
                            Toast.makeText(context, "支付失败,请退出重试", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            PayReService.getInstance(context)
                                    .zfbQRUrlQuest(accessToken)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(observer);
                        }

                    }
                    break;
            }
            this.totalFee = total_fee;
        } catch (Exception ex) {
            KLog.e("error", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void insertOrderLog(final boolean isWx, final long total_feeI, final String code, int timeSC) {
        timeSC++;
        final int finalTimeSC = timeSC;
        InsertOrderLogReq map = new InsertOrderLogReq();
        map.setSystemUserSysNo(ConstantUtils.SYS_NO);
        map.setCustomerServiceSysNo(ConstantUtils.HIGHER_SYS_NO);
        map.setTotalFee(total_feeI);
        map.setOutTradeNo(code);
        String type;
        if (isWx) {
            type = ConstantUtils.GETED_WX_TYPE + "-Android[QRCode]";
        } else {
            type = ConstantUtils.GETED_ZFB_TYPE + "-Android[QRCode]";
        }
        map.setPayType(type);
        PayReStringService.getInstance(context)
                .insertLog(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context) {
                    @Override
                    protected void doOnNext(String value) {
                        boolean b = Boolean.parseBoolean(value);
                        if (b) {
                            KLog.i("日志插入成功");
                        } else {
                            if (finalTimeSC < 5) {
                                KLog.i("再次插入日志---" + finalTimeSC);
                                insertOrderLog(isWx, total_feeI, code, finalTimeSC);
                            } else {
                                boolean logB = PayLogQueu.getInstance().offer(bean);
                                if (!logB) {
                                    KLog.i("insertPayLog------failure" + logB);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }

    @Override
    public void closeTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void requestOrder() {
        if ((wxIsBank && isWX) || (zfbIsBank && !isWX)) {
            if ((isWXNetBank && isWX) || (isZFbNetBank && !isWX)) {
                NetBankQuestReq params = new NetBankQuestReq();
                ReqModel mapReq = new ReqModel();
                mapReq.setOutTradeNo(data_code);
                params.setReqModel(mapReq);
                params.setSystemUserSysNo(ConstantUtils.SYS_NO);
                params.setChannelType(isWX ? "WX" : "ALI");
                PayReService.getInstance(context)
                        .netbankOrderQuest(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<JsonObject>(context) {
                            @Override
                            protected void doOnNext(JsonObject value) {
                                try {
                                    JSONObject result = new JSONObject(value.toString());
                                    if (result != null && !TextUtils.isEmpty(result.toString())) {
                                        KLog.i(result);
                                        if ((isWXNetBank && isWX) || (isZFbNetBank && !isWX)) {
                                            int code = result.getInt("Code");
                                            if (code == 0) {
                                                JSONObject data = result.getJSONObject("Data");
                                                JSONObject wxpaydata = data.getJSONObject("WxPayData");
                                                String trade_state = wxpaydata.getJSONObject("m_values").getString("TradeStatus");
                                                if (trade_state.equals("succ")) {
                                                    paySuccess();
                                                }
                                            } else if (code == 102) {
                                                payConfigError();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                            }
                        });

            } else {
                CommonUrlReq params = new CommonUrlReq();
                params.setPayType(reMarks);
                params.setOutTradeNo(data_code);
                params.setSystemUserSysNo(ConstantUtils.SYS_NO);
                PayReService.getInstance(context)
                        .bankOrderQuest(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<JsonObject>(context) {
                            @Override
                            protected void doOnNext(JsonObject value) {
                                try {
                                    JSONObject result = new JSONObject(value.toString());
                                    if (result != null && !TextUtils.isEmpty(result.toString())) {
                                        int code = result.getInt("Code");
                                        if (code == 0) {
                                            String description = result.getString("Description");
                                            JSONObject data = result.getJSONObject("Data");
                                            JSONObject wxpaydata = data.getJSONObject("WxPayData");
                                            if (!description.equals("ORDERNOTEXIST")) {
                                                String trade_state = wxpaydata.getString("trade_state");
                                                if (trade_state.equals("SUCCESS")) {
                                                    paySuccess();
                                                }
                                            }
                                        } else if (code == 102) {
                                            payConfigError();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                            }
                        });

            }
        } else {
            switch (orderWay) {
                case 0:
                    CommonUrlReq accessToken = new CommonUrlReq();
                    accessToken.setOutTradeNo(data_code);
                    accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
                    PayReService.getInstance(context)
                            .wxOrderQuest(accessToken)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<JsonObject>(context) {
                                @Override
                                protected void doOnNext(JsonObject value) {
                                    try {
                                        JSONObject result = new JSONObject(value.toString());
                                        if (result != null && !TextUtils.isEmpty(result.toString())) {
                                            KLog.i(result);
                                            int code = result.getInt("Code");
                                            if (code == 0) {
                                                String description = result.getString("Description");
                                                JSONObject data = result.getJSONObject("Data");
                                                JSONObject wxpaydata = data.getJSONObject("WxPayData");
                                                JSONObject m_values = wxpaydata.getJSONObject("m_values");
                                                if (!description.equals("ORDERNOTEXIST")) {
                                                    String trade_state = m_values.getString("trade_state");
                                                    if (trade_state.equals("SUCCESS")) {
                                                        paySuccess();
                                                    }
                                                }
                                            } else if (code == 9001) {
                                                payConfigError();
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                                }
                            });
                    break;
                case 1:
                    CommonUrlReq accessToken1 = new CommonUrlReq();
                    accessToken1.setOutTradeNo(data_code);
                    accessToken1.setSystemUserSysNo(ConstantUtils.SYS_NO);
                    PayReService.getInstance(context)
                            .zfbOrderQuest(accessToken1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<JsonObject>(context) {
                                @Override
                                protected void doOnNext(JsonObject value) {
                                    try {
                                        JSONObject result = new JSONObject(value.toString());
                                        if (result != null && !TextUtils.isEmpty(result.toString())) {
                                            KLog.i(result);
                                            String success = result.getString("Code");
                                            int code = result.getInt("Code");
                                            JSONObject data = result.getJSONObject("Data");
                                            if (code == 0) {
                                                JSONObject wxpaydata = new JSONObject(data.getString("WxPayData"));
                                                JSONObject m_values = new JSONObject(wxpaydata.getString("alipay_trade_query_response"));
                                                KLog.i("success");
                                                String trade_state = m_values.getString("trade_status");
                                                if (trade_state.equals("TRADE_SUCCESS")) {
                                                    paySuccess();
                                                }
                                            } else if (code == 102) {
                                                payConfigError();
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                                }
                            });

                    break;
            }
        }
    }

    private void paySuccess() {
        playSc();
        QrCodePresenter.this.view.setResult(QrCodePresenter.this.context.getResources().getString(R.string.qr_success), QrCodePresenter.this.context.getResources().getColor(R.color.label_qr_success), orderWay);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void payConfigError() {
        QrCodePresenter.this.view.clearQR();
        QrCodePresenter.this.view.setResult(context.getString(R.string.pay_config_error), QrCodePresenter.this.context.getResources().getColor(R.color.label_qr_error), orderWay);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void playSc() {
        //Done switch
        if (DycLibIntent.hasModule()) {
            PayScUtils.success(fee, ((QRCodeActivity) context).getSynthesizer(),isWX);
        }
    }

    private void closeOrder() {
        BaseObserver<JsonObject> observer = new BaseObserver<JsonObject>(context) {
            @Override
            protected void doOnNext(JsonObject value) {
                try {
                    JSONObject result = new JSONObject(value.toString());
                    KLog.i(result);
                    if (result != null && !TextUtils.isEmpty(result.toString())) {
                        int code = result.getInt("Code");
                        insertCloseOrderLog(code);
                        if (code == 0) {
                            errorPay();
                            KLog.i("状态异常,订单已关闭");
                        } else {
                            KLog.i("状态异常,订单关闭失败");
                            queryPlantResuslt();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

            }
        };
        if ((wxIsBank && isWX) || (zfbIsBank && !isWX)) {
            if ((isWXNetBank && isWX) || (isZFbNetBank && !isWX)) {
                ReqModel mapReq = new ReqModel();
                mapReq.setOutTradeNo(data_code);
                NetBankQuestReq accessToken = new NetBankQuestReq();
                accessToken.setReqModel(mapReq);
                accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
                accessToken.setChannelType(isWX ? "WX" : "ALI");
                PayReService.getInstance(context)
                        .netbankOrderClose(accessToken)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer);
            } else {
                CommonUrlReq map = new CommonUrlReq();
                map.setPayType(reMarks);
                map.setOutTradeNo(data_code);
                map.setSystemUserSysNo(ConstantUtils.SYS_NO);
                PayReService.getInstance(context)
                        .bankOrderClose(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer);

            }
        } else {
            switch (orderWay) {
                case 0:
                    CommonUrlReq accessToken = new CommonUrlReq();
                    accessToken.setOutTradeNo(data_code);
                    accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
                    PayReService.getInstance(context)
                            .wxOrderClose(accessToken)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(observer);
                    break;
                case 1:
//                    actionUrl = ConstantUtils.URL_ZFB_ORDER_CANCEL;
                    CommonUrlReq accessToken1 = new CommonUrlReq();
                    accessToken1.setOutTradeNo(data_code);
                    accessToken1.setSystemUserSysNo(ConstantUtils.SYS_NO);
                    PayReService.getInstance(context)
                            .zfbOrderClose(accessToken1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(observer);
                    break;
            }
        }

    }

    private void insertCloseOrderLog(int code) {

        ColseOrderLogReq map = new ColseOrderLogReq();
        map.setSystemUserSysNo(ConstantUtils.SYS_NO);
        map.setOutTradeNo(data_code);
        map.setTotalFee(this.totalFee);
        KLog.i(isWX);
        if (isWX) {
            map.setPayType(ConstantUtils.GETED_WX_TYPE);
        } else {
            map.setPayType(ConstantUtils.GETED_ZFB_TYPE);
        }
        if (code == 0) {
            map.setState("订单关闭成功");
        } else {
            map.setState("订单关闭失败");
        }
        map.setPaymentEnd("Android");
        PayReService.getInstance(context)
                .closeOrderLogInsert(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<JsonObject>(context) {
                    @Override
                    protected void doOnNext(JsonObject value) {
                        try {
                            JSONObject result = new JSONObject(value.toString());
                            KLog.i(result);
                            if (result != null && !TextUtils.isEmpty(result.toString())) {
                                int code = result.getInt("Code");
                                if (code == 0) {
                                    KLog.i("插入日志成功");
                                } else {
                                    KLog.i("插入日志失败");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });


    }

    private void queryPlantResuslt() {
        if ((wxIsBank && isWX) || (zfbIsBank && !isWX)) {
            if ((isWXNetBank && isWX) || (isZFbNetBank && !isWX)) {
                NetBankQuestReq params = new NetBankQuestReq();
                ReqModel mapReq = new ReqModel();
                mapReq.setOutTradeNo(data_code);
                params.setReqModel(mapReq);
                params.setSystemUserSysNo(ConstantUtils.SYS_NO);
                params.setChannelType(isWX ? "WX" : "ALI");
                PayReService.getInstance(context)
                        .netbankOrderQuest(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<JsonObject>(context) {
                            @Override
                            protected void doOnNext(JsonObject value) {
                                try {
                                    JSONObject result = new JSONObject(value.toString());
                                    if (result != null && !TextUtils.isEmpty(result.toString())) {
                                        KLog.i(result);
                                        if ((isWXNetBank && isWX) || (isZFbNetBank && !isWX)) {
                                            int code = result.getInt("Code");
                                            if (code == 0) {
                                                JSONObject data = result.getJSONObject("Data");
                                                JSONObject wxpaydata = data.getJSONObject("WxPayData");
                                                String trade_state = wxpaydata.getJSONObject("m_values").getString("TradeStatus");
                                                if (trade_state.equals("succ")) {
                                                    paySuccess();
                                                } else {
                                                    errorPay();
                                                }
                                            } else if (code == 102) {
                                                errorPay();
                                            } else {
                                                errorPay();
                                            }
                                        }
                                    } else {
                                        errorPay();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                errorPay();
                            }
                        });
            } else {
                CommonUrlReq params = new CommonUrlReq();
                params.setPayType(reMarks);
                params.setOutTradeNo(data_code);
                params.setSystemUserSysNo(ConstantUtils.SYS_NO);
                PayReService.getInstance(context)
                        .bankOrderQuest(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<JsonObject>(context) {
                            @Override
                            protected void doOnNext(JsonObject value) {
                                try {
                                    JSONObject result = new JSONObject(value.toString());
                                    if (result != null && !TextUtils.isEmpty(result.toString())) {
                                        KLog.i(result);
                                        int code = result.getInt("Code");
                                        if (code == 0) {
                                            String description = result.getString("Description");
                                            JSONObject data = result.getJSONObject("Data");
                                            JSONObject wxpaydata = data.getJSONObject("WxPayData");
                                            if (!description.equals("ORDERNOTEXIST")) {
                                                String trade_state = wxpaydata.getString("trade_state");
                                                if (trade_state.equals("SUCCESS")) {
                                                    paySuccess();
                                                } else {
                                                    errorPay();
                                                }
                                            } else {
                                                errorPay();
                                            }
                                        } else if (code == 102) {
                                            errorPay();
                                        } else {
                                            errorPay();
                                        }
                                    } else {
                                        errorPay();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                errorPay();
                            }
                        });

            }

        } else {
            switch (orderWay) {
                case 0:
                    CommonUrlReq accessToken = new CommonUrlReq();
                    accessToken.setOutTradeNo(data_code);
                    accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
                    PayReService.getInstance(context)
                            .wxOrderQuest(accessToken)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<JsonObject>(context) {
                                @Override
                                protected void doOnNext(JsonObject value) {
                                    try {
                                        JSONObject result = new JSONObject(value.toString());
                                        if (result != null && !TextUtils.isEmpty(result.toString())) {
                                            KLog.i(result);
                                            int code = result.getInt("Code");
                                            if (code == 0) {
                                                String description = result.getString("Description");
                                                JSONObject data = result.getJSONObject("Data");
                                                JSONObject wxpaydata = data.getJSONObject("WxPayData");
                                                JSONObject m_values = wxpaydata.getJSONObject("m_values");
                                                if (!description.equals("ORDERNOTEXIST")) {
                                                    String trade_state = m_values.getString("trade_state");
                                                    if (trade_state.equals("SUCCESS")) {
                                                        paySuccess();
                                                    } else {
                                                        errorPay();
                                                    }
                                                } else {
                                                    errorPay();
                                                }
                                            } else if (code == 9001) {
                                                errorPay();
                                            } else {
                                                errorPay();
                                            }
                                        } else {
                                            errorPay();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                    errorPay();
                                }
                            });
                    break;
                case 1:
                    CommonUrlReq accessToken1 = new CommonUrlReq();
                    accessToken1.setOutTradeNo(data_code);
                    accessToken1.setSystemUserSysNo(ConstantUtils.SYS_NO);
                    PayReService.getInstance(context)
                            .zfbOrderQuest(accessToken1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<JsonObject>(context) {
                                @Override
                                protected void doOnNext(JsonObject value) {
                                    try {
                                        JSONObject result = new JSONObject(value.toString());
                                        if (result != null && !TextUtils.isEmpty(result.toString())) {
                                            KLog.i(result);
                                            String success = result.getString("Code");
                                            int code = result.getInt("Code");
                                            JSONObject data = result.getJSONObject("Data");
                                            if (code == 0) {
                                                JSONObject wxpaydata = new JSONObject(data.getString("WxPayData"));
                                                JSONObject m_values = new JSONObject(wxpaydata.getString("alipay_trade_query_response"));
                                                KLog.i("success");
                                                String trade_state = m_values.getString("trade_status");
                                                if (trade_state.equals("TRADE_SUCCESS")) {
                                                    paySuccess();
                                                } else {
                                                    errorPay();
                                                }
                                            } else if (code == 102) {
                                                errorPay();
                                            } else {
                                                errorPay();
                                            }
                                        } else {
                                            errorPay();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                    errorPay();
                                }
                            });
                    break;
            }
        }


    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
