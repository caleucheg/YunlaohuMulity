package com.yang.yunwang.pay.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.socks.library.KLog;
import com.yang.yunwang.base.dao.PayLogBean;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.JsonUtils;
import com.yang.yunwang.base.util.PayLogQueu;
import com.yang.yunwang.pay.R;
import com.yang.yunwang.pay.api.PayReService;
import com.yang.yunwang.pay.api.PayReStringService;
import com.yang.yunwang.pay.api.scan.bankpay.BankScanPayReq;
import com.yang.yunwang.pay.api.scan.insertorder.InsertOrderLogReq;
import com.yang.yunwang.pay.api.scan.netbank.NetBankScanPayReq;
import com.yang.yunwang.pay.api.scan.netbank.ReqModel;
import com.yang.yunwang.pay.api.scan.netbank.wx.NetBankScanWxPayResp;
import com.yang.yunwang.pay.api.scan.sendmessage.SendMessageReq;
import com.yang.yunwang.pay.api.scan.sendmessage.SendMessageResp;
import com.yang.yunwang.pay.api.scan.snowno.SnowNoReq;
import com.yang.yunwang.pay.api.scan.snowno.SnowNoResp;
import com.yang.yunwang.pay.api.scan.wxpay.WxScanPayReq;
import com.yang.yunwang.pay.api.scan.wxpay.WxScanPayResp;
import com.yang.yunwang.pay.api.scan.zfbpay.ZfbScanPayReq;
import com.yang.yunwang.pay.api.scan.zfbpay.ZfbScanPayResp;
import com.yang.yunwang.pay.common.PayScUtils;
import com.yang.yunwang.pay.contract.ScanContract;
import com.yang.yunwang.pay.view.scan.ScanResultActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 */

public class ScanPresenter implements ScanContract.Presenter {
    private final ScanContract.View view;
    private final Context context;
    private final String POSID = "10";
    //    private JSONObject jsonObject;
    private Timer timer;
    private int time = 81;
    private Handler handler;
    private int status_flag = 1;
    private int timeout_flag = 0;//请求异常标示
    private String result;
    private String totalFrr;
    private String payType;
    private PayLogBean bean;
    private String orderNo;
    private boolean wxIsBank;
    private boolean zfbIsBank;
    private boolean isWX;
    private String reMarks;
    private boolean ErrorCode = false;
    private boolean isWxNetsType;
    private boolean isZfbNetsType;
    private int connect_time_out = 80;
    private String sysNo;
    private String hSysno;

    public ScanPresenter(final ScanContract.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        timer = new Timer();
        handler = new Handler(context.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                view.setResultRes(R.drawable.progress_waitting);
                if (time >= 0) {
                    view.setResultText(time + "秒", R.color.black_color);
                } else {
                    view.setResultRes(R.drawable.scan_fail_logo);
                    view.setResultText("支付失败", R.color.scan_fail);
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                }
            }
        };
    }

    @Override
    public void subscribe() {

    }

    @Override

    public void unsubscribe() {

    }

    @Override
    public void scanRquest(boolean wxIsBank, boolean zfbIsBank, String sysNo, String hSysno) {
//        time = 60;
        this.sysNo = sysNo;
        this.hSysno = hSysno;
        Intent intent = view.loadInstance();
        String total_fee_temp = intent.getStringExtra("total_fee");
        this.totalFrr = total_fee_temp;
        int total_fee = Integer.parseInt(AmountUtils.changeY2F(total_fee_temp));
        String result = intent.getStringExtra("result");
        KLog.i("orderNo" + result);
        String type = "";
        if (!TextUtils.isEmpty(result) && !result.equals("")) {
            type = result.substring(0, 2);
        } else {
            type = "";
        }
        this.wxIsBank = wxIsBank;
        this.zfbIsBank = zfbIsBank;
        switch (type) {
            case "10":
            case "11":
            case "12":
            case "13":
            case "14":
            case "15":
                if (result.length() >= 18) {
                    this.payType = "wx";
                    this.isWX = true;
                    getOrderNo(true, total_fee, result);
                } else {
                    errorScanResult();
                }
                break;
            case "25":
            case "26":
            case "27":
            case "28":
            case "29":
            case "30":
                if (result.length() >= 16 && result.length() <= 24) {
                    this.payType = "zfb";
                    this.isWX = false;
                    if (TextUtils.equals(ConstantUtils.KOUBEI_ZFB_TAG, ConstantUtils.GETED_ZFB_TYPE)) {
                        Toast.makeText(context, "不支持此类型支付", Toast.LENGTH_SHORT).show();
                        this.ErrorCode = true;
                        view.setResultText("不支持此类型支付", R.color.scan_fail);
                        view.setResultRes(R.drawable.scan_fail_logo);
                        status_flag = 0;
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                        KLog.i("***********stop********");
                    } else {
                        getOrderNo(false, total_fee, result);
                    }
                } else {
                    errorScanResult();
                }
                break;
            default:
                errorScanResult();
                break;
        }
        this.reMarks = isWX ? "WX" : "AliPay";
        KLog.i("info", "request");
    }

    private void errorScanResult() {
        this.ErrorCode = true;
        view.setResultText("扫描二维码有误", R.color.scan_fail);
        view.setResultRes(R.drawable.scan_fail_logo);
        status_flag = 0;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void getOrderNo(final boolean isWx, final int total_feeI, final String resultA) {
        //TODO check permission
//        if (!isWx && !ConstantUtils.Pay_scan_code_payment_Alipay) {
//            showDialogDiss("暂无权限");
//        } else if (isWx && !ConstantUtils.Pay_scan_code_payment) {
//            showDialogDiss("暂无权限");
//        } else {
//
//        }

        isWxNetsType = isWX && TextUtils.equals("108", ConstantUtils.GETED_WX_TYPE);
        isZfbNetsType = (!isWX && TextUtils.equals("109", ConstantUtils.GETED_ZFB_TYPE));
        SnowNoReq accessToken = new SnowNoReq();
        if (isWxNetsType || isZfbNetsType) {
            time = 151;
            connect_time_out = 150;
        } else {
            time = 81;
            connect_time_out = 80;
        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
                time--;
            }
        };
        if (timer != null) {
            timer.schedule(task, 0, 1000);
        }
        KLog.i(isWxNetsType + "-----" + ConstantUtils.GETED_WX_TYPE);
        accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
        KLog.i(ConstantUtils.GETED_WX_TYPE + ConstantUtils.GETED_ZFB_TYPE);
        if (!TextUtils.isEmpty(ConstantUtils.GETED_WX_TYPE) || !TextUtils.isEmpty(ConstantUtils.GETED_ZFB_TYPE)) {
            if (isWx && !TextUtils.isEmpty(ConstantUtils.GETED_WX_TYPE)) {
                accessToken.setOrderType("102");
            } else if (!isWx && !TextUtils.isEmpty(ConstantUtils.GETED_ZFB_TYPE)) {
                accessToken.setOrderType("103");
            } else {
                failGJump();
                return;
            }
        } else {
            failGJump();
            return;
        }
        BaseObserver<SnowNoResp> observer = new BaseObserver<SnowNoResp>(context) {
            @Override
            protected void doOnNext(SnowNoResp value) {
                String code = value.getData();
                ScanPresenter.this.orderNo = code;
                boolean wxR = code.startsWith("13");
                boolean zfbR = code.startsWith("20");
                if ((wxR && isWx) || (zfbR && !isWx)) {
                    if ((wxIsBank && isWX) || (zfbIsBank && !isWX)) {
                        bankPay(total_feeI, resultA, code);
                    } else if (!ErrorCode) {
                        if (isWx) {
                            wxPay(total_feeI, resultA, code);
                        } else {
                            zfbPay(total_feeI, resultA, code);
                        }
                    } else {
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int timeSC = 0;
                    insertOrderLog(isWx, total_feeI, code, timeSC);//插入起点
                    bean = new PayLogBean(isWx, total_feeI, code, ConstantUtils.GETED_WX_TYPE, ConstantUtils.GETED_ZFB_TYPE, false);
                } else {
                    errorScanResult();
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                Toast.makeText(context, "生成订单失败,请重新扫码", Toast.LENGTH_SHORT).show();
            }
        };

        if (!TextUtils.isEmpty(ConstantUtils.SYS_NO)) {
            PayReService.getInstance(context)
                    .getSnowNo(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {
            Toast.makeText(context, "当前网络较差,请退出重试", Toast.LENGTH_LONG).show();
        }

    }

    private void failGJump() {
        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
        view.setResultRes(R.drawable.scan_fail_logo);
        view.setResultText("支付失败", R.color.scan_fail);
        status_flag = 0;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void bankPay(long total_feeI, String resultA, String code) {
        if ((isWxNetsType && isWX) || (isZfbNetsType && !isWX)) {
            NetBankScanPayReq accessToken = new NetBankScanPayReq();
            ReqModel model = new ReqModel();
            model.setTotalAmount(total_feeI + "");
            model.setChannelType(isWX ? "WX" : "ALI");
            model.setAuthCode(resultA);
            model.setOutTradeNo(code);
            accessToken.setReqModel(model);
            accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
            PayReService.getInstance(context, connect_time_out, connect_time_out, connect_time_out)
                    .netBankScanPay(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<NetBankScanWxPayResp>(context) {
                        @Override
                        protected void doOnNext(NetBankScanWxPayResp value) {
                            if (value != null && !TextUtils.isEmpty(value.toString())) {
                                KLog.i(value);
                                long code = value.getCode();
                                Gson gson = new Gson();
                                ScanPresenter.this.result = gson.toJson(value);
                                if (code == 0) {
                                    view.setResultRes(R.drawable.scan_success_logo);
                                    view.setResultText("支付成功", R.color.scan_success);
                                    PayScUtils.success(totalFrr, ((ScanResultActivity) context).getSynthesizer(), isWX);
                                } else {
                                    view.setResultRes(R.drawable.scan_fail_logo);
                                    view.setResultText("支付失败", R.color.scan_fail);
                                }
                            } else {
                                KLog.i("res===null");
                                timeout_flag = 1;
                            }
                            status_flag = 0;
                            if (timer != null) {
                                timer.cancel();
                                timer = null;
                            }
                            if (timeout_flag == 1) {
                                showDialog("支付失败");
                                view.setResultRes(R.drawable.scan_fail_logo);
                                view.setResultText("支付失败", R.color.scan_fail);
                            }
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                            errorPay();
                            timeout_flag = 1;
                            if (timer != null) {
                                timer.cancel();
                                timer = null;
                            }
                            if (timeout_flag == 1) {
                                showDialog("支付失败");
                                view.setResultRes(R.drawable.scan_fail_logo);
                                view.setResultText("支付失败", R.color.scan_fail);
                            }
                        }
                    });

        } else {

            BankScanPayReq accessToken = new BankScanPayReq();
            accessToken.setTotalFee(total_feeI);
            accessToken.setAuthCode(resultA);
            accessToken.setCommodity("商品");
            accessToken.setOldSysNo(ConstantUtils.SYS_NO);
            accessToken.setOutTradeNo(code);
            PayReService.getInstance(context, connect_time_out, connect_time_out, connect_time_out)
                    .bankScanPay(accessToken)
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
                                    Gson gson = new Gson();
                                    ScanPresenter.this.result = gson.toJson(value);
                                    if (code == 0) {
                                        view.setResultRes(R.drawable.scan_success_logo);
                                        view.setResultText("支付成功", R.color.scan_success);
                                        PayScUtils.success(totalFrr, ((ScanResultActivity) context).getSynthesizer(), isWX);
                                    } else {
                                        view.setResultRes(R.drawable.scan_fail_logo);
                                        view.setResultText("支付失败", R.color.scan_fail);
                                    }
                                } else {
                                    KLog.i("res===null");
                                    timeout_flag = 1;
                                }
                                status_flag = 0;
                            } catch (JSONException ex) {
                                timeout_flag = 1;
                                Log.e("error", ex.getMessage());
                            } finally {
                                if (timer != null) {
                                    timer.cancel();
                                    timer = null;
                                }
                                if (timeout_flag == 1) {
                                    showDialog("支付失败");
                                    view.setResultRes(R.drawable.scan_fail_logo);
                                    view.setResultText("支付失败", R.color.scan_fail);
                                }
                            }
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                            errorPay();
                        }
                    });
        }
    }

    private void errorPay() {
        Handler handler = new Handler(context.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                status_flag = 0;
                Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertOrderLog(final boolean isWx, final long total_feeI, final String code, int timeSC) {
        timeSC++;
        InsertOrderLogReq map = new InsertOrderLogReq();
        map.setSystemUserSysNo(ConstantUtils.SYS_NO);
        map.setCustomerServiceSysNo(ConstantUtils.HIGHER_SYS_NO);
        map.setTotalFee(total_feeI);
        map.setOutTradeNo(code);
        String type;
        if (isWx) {
            type = ConstantUtils.GETED_WX_TYPE + "-Android";
        } else {
            type = ConstantUtils.GETED_ZFB_TYPE + "-Android";
        }
        map.setPayType(type);
        final int finalTimeSC = timeSC;
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
                });
    }


    private void zfbPay(long total_fee, String result, String zfbOrder) {
        ZfbScanPayReq mapZfb = new ZfbScanPayReq();
        mapZfb.setTotalAmount(total_fee);
        mapZfb.setAuthCode(result);
        mapZfb.setCustomerSysNo(ConstantUtils.HIGHER_SYS_NO);
        mapZfb.setOldSysNo(ConstantUtils.SYS_NO);
        mapZfb.setOutTradeNo(zfbOrder);
        BaseObserver<ZfbScanPayResp> observer = new BaseObserver<ZfbScanPayResp>(context) {
            @Override
            protected void doOnNext(ZfbScanPayResp value) {
                if (value != null && !TextUtils.isEmpty(value.toString())) {
                    KLog.i(value);
                    long code = value.getCode();
                    Gson gson = new Gson();
                    ScanPresenter.this.result = gson.toJson(value);
                    if (code == 0) {
                        view.setResultRes(R.drawable.scan_success_logo);
                        view.setResultText("支付成功", R.color.scan_success);
                        PayScUtils.success(totalFrr, ((ScanResultActivity) context).getSynthesizer(), isWX);
                    } else {
                        view.setResultRes(R.drawable.scan_fail_logo);
                        view.setResultText("支付失败", R.color.scan_fail);
                    }
                } else {
                    KLog.i("res===null");
                    timeout_flag = 1;
                }
                status_flag = 0;
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                if (timeout_flag == 1) {
                    showDialog("支付失败");
                    view.setResultRes(R.drawable.scan_fail_logo);
                    view.setResultText("支付失败", R.color.scan_fail);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                errorPay();
                timeout_flag = 1;
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                if (timeout_flag == 1) {
                    showDialog("支付失败");
                    view.setResultRes(R.drawable.scan_fail_logo);
                    view.setResultText("支付失败", R.color.scan_fail);
                }
            }
        };

        if (ConstantUtils.Pay_scan_code_payment_Alipay) {
            PayReService.getInstance(context, connect_time_out, connect_time_out, connect_time_out)
                    .zfbScanPay(mapZfb)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {
            showDialogDiss("暂无权限");
        }
    }

    private void wxPay(long total_fee, String result, String orderNo) {
        WxScanPayReq mapWx = new WxScanPayReq();
//        mapWx.setProductTitle(ConstantUtils.HIGHER_NAME);
        mapWx.setTotalFee(total_fee);
        mapWx.setAuthCode(result);
        mapWx.setPOSID(POSID);
        mapWx.setCustomerSysNo(ConstantUtils.HIGHER_SYS_NO);
        mapWx.setOldSysNo(ConstantUtils.SYS_NO);
        mapWx.setOutTradeNo(orderNo);
        BaseObserver<WxScanPayResp> observer = new BaseObserver<WxScanPayResp>(context) {
            @Override
            protected void doOnNext(WxScanPayResp value) {
                if (value != null && !TextUtils.isEmpty(value.toString())) {
                    KLog.i(value);
                    long code = value.getCode();
                    Gson gson = new Gson();
                    ScanPresenter.this.result = gson.toJson(value);
                    if (code == 0) {
//                    KLog.i(result);
                        view.setResultRes(R.drawable.scan_success_logo);
                        view.setResultText("支付成功", R.color.scan_success);
                        PayScUtils.success(totalFrr, ((ScanResultActivity) context).getSynthesizer(), isWX);
                    } else {
                        view.setResultRes(R.drawable.scan_fail_logo);
                        view.setResultText("支付失败", R.color.scan_fail);
                    }
                } else {
                    KLog.i("res===null");
                    timeout_flag = 1;
                }
                status_flag = 0;
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                if (timeout_flag == 1) {
                    showDialog("支付失败");
                    view.setResultRes(R.drawable.scan_fail_logo);
                    view.setResultText("支付失败", R.color.scan_fail);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                errorPay();
                timeout_flag = 1;
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                if (timeout_flag == 1) {
                    showDialog("支付失败");
                    view.setResultRes(R.drawable.scan_fail_logo);
                    view.setResultText("支付失败", R.color.scan_fail);
                }
            }
        };
        if (ConstantUtils.Pay_scan_code_payment) {
            PayReService.getInstance(context, connect_time_out, connect_time_out, connect_time_out)
                    .wxScanPay(mapWx)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {
            showDialogDiss("暂无权限");
        }
    }


    @Override
    public int returnStatus() {
        return status_flag;
    }


    @Override
    public void closeTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void showDialog(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(context.getResources().getString(R.string.alert_title));
        dialog.setMessage(message);
        dialog.setPositiveButton(context.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showDialogDiss(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(context.getResources().getString(R.string.alert_title));
        dialog.setMessage(message);
        dialog.setPositiveButton(context.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((ScanResultActivity) view).finish();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ((ScanResultActivity) view).finish();
            }
        });
        dialog.show();
    }

    @Override
    public void sendMessage() {
//        KLog.i(this.result);
        KLog.i(totalFrr);
        KLog.i(payType);
        KLog.i("high_sys" + ConstantUtils.HIGHER_SYS_NO);
        if (TextUtils.equals(payType, "zfb")) {
            sendTMessage(totalFrr, payType, ScanPresenter.this.result);
        } else {
            sendTMessage(totalFrr, payType, ScanPresenter.this.result);
        }
    }

    private void sendTMessage(String totalFrr, String payType, String resultS) {
        KLog.i("sssssss");
        KLog.i(result);
        String channelOrder;
        try {
            JSONObject result = new JSONObject(resultS);
            if (result.has("Data")) {
                String dataS = result.getString("Data");
                if ((wxIsBank && isWX) || (zfbIsBank && !isWX)) {
                    KLog.i("data-order");
                    JSONObject jsonObj = result.getJSONObject("Data");
                    channelOrder = jsonObj.has("Out_trade_no") ? jsonObj.getString("Out_trade_no") : orderNo;
                } else {
                    boolean goodJson = JsonUtils.isGoodJson(dataS);
                    if (goodJson) {
                        KLog.i("data-order1");
                        JSONObject jsonObj = result.getJSONObject("Data");
                        channelOrder = jsonObj.has("Out_trade_no") ? jsonObj.getString("Out_trade_no") : orderNo;
                    } else if ((!dataS.startsWith("<")) && dataS.startsWith("[0-9]")) {
                        KLog.i("data");
                        channelOrder = dataS;
                    } else {
                        channelOrder = orderNo;
                    }
                }
            } else {
                channelOrder = orderNo;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            channelOrder = orderNo;
        }
        SendMessageReq map = new SendMessageReq();
        map.setSystemUserSysNO(sysNo);
        map.setCustomerServiceSysNO(hSysno);
        map.setOutTradeNo(channelOrder);
        map.setMoney(totalFrr);
        String type = "";
        if ((wxIsBank && isWX) || (zfbIsBank && !isWX)) {
            if (wxIsBank && isWX) {
                if (isWxNetsType) {
                    type = "网商银行-微信";
                } else {
                    type = "银行(微信)";
                }
            } else {
                if (isZfbNetsType) {
                    type = "网商银行-支付宝";
                } else {
                    type = "银行(支付宝)";
                }
            }
        } else {
            if (TextUtils.equals(payType, "zfb")) {
                type = "支付宝";
            } else {
                type = "微信";
            }
        }
        map.setType(type);
        PayReService.getInstance(context)
                .sendTemplateMessage(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SendMessageResp>(context) {
                    @Override
                    protected void doOnNext(SendMessageResp value) {
                        if (value != null && !TextUtils.isEmpty(value.toString())) {
                            String code = value.getCode() + "";
                        } else {
                            KLog.i("res=null");
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }


    @Override
    public void reinsertLog(PayLogBean bean) {

    }
}
