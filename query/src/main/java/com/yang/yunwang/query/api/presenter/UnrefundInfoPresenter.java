package com.yang.yunwang.query.api.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.basereq.BaseInfoService;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigReq;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigResp;
import com.yang.yunwang.base.basereq.bean.stafflogin.StaffLoginResp;
import com.yang.yunwang.base.moduleinterface.router.ServiceManager;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.QueryReService;
import com.yang.yunwang.query.api.bean.refundsearch.refundlist.Model;
import com.yang.yunwang.query.api.bean.refundsearch.refundlist.RefundListReq;
import com.yang.yunwang.query.api.bean.refundsearch.refundlist.RefundListResp;
import com.yang.yunwang.query.api.bean.unrefund.bank.BankUnrefundReq;
import com.yang.yunwang.query.api.bean.unrefund.bank.BankUnrefundResp;
import com.yang.yunwang.query.api.bean.unrefund.netbank.NetBankUnrefundReq;
import com.yang.yunwang.query.api.bean.unrefund.netbank.NetBankUnrefundResp;
import com.yang.yunwang.query.api.bean.unrefund.netbank.ReqModel;
import com.yang.yunwang.query.api.bean.unrefund.yihui.wx.WxYihuiUnrefundReq;
import com.yang.yunwang.query.api.bean.unrefund.yihui.wx.WxYihuiUnrefundResp;
import com.yang.yunwang.query.api.bean.unrefund.yihui.zfb.ZfbYihuiUnrefundReq;
import com.yang.yunwang.query.api.bean.unrefund.yihui.zfb.ZfbYihuiUnrefundResp;
import com.yang.yunwang.query.api.contract.UnrefundInfoContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 */

public class UnrefundInfoPresenter implements UnrefundInfoContract.Presenter {
    private final UnrefundInfoContract.View view;
    private final Context context;
    private ProgressDialog progressDialog;
    private String transaction_id;

    public UnrefundInfoPresenter(UnrefundInfoContract.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        getPayConfig(ConstantUtils.HIGHER_SYS_NO);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void initData(boolean isFirst) {
        Intent intent = view.loadIntentInstance();
        String code = intent.getStringExtra("code_list");
        Model refundBean = (Model) intent.getSerializableExtra("refundbean");
        RefundListReq accessToken = new RefundListReq();
        accessToken.setOutTradeNo(code);
        accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (isFirst) {
            KLog.i("first");
            try {
                parseData(refundBean);
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        } else {
            initRData(accessToken);
        }
    }

    private void initRData(RefundListReq accessToken) {
        QueryReService.getInstance(context)
                .getUnrefundList(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<RefundListResp>(context) {
                    @Override
                    protected void doOnNext(RefundListResp value) {
                        try {
                            if (value.getModel().size() > 0) {
                                Model item = value.getModel().get(0);
                                String sys_no = item.getSysNo() + "";
                                String code = item.getOutTradeNo();
                                String order_time = item.getTimeStart();
                                String money = AmountUtils.changeF2Y(item.getCashFee());
                                String refund;
                                if (!TextUtils.isEmpty(item.getTransactionId())) {
                                    transaction_id = item.getTransactionId();
                                }
                                if (!TextUtils.isEmpty(item.getRefundFee() + "")) {
                                    refund = AmountUtils.changeF2Y(item.getRefundFee());
                                } else {
                                    refund = AmountUtils.changeF2Y(item.getRefundFee());
                                }
                                String money_releas = AmountUtils.changeF2Y(item.getFee());
                                String refund_count = item.getRefundCount() + "";
                                String pay_type = item.getPayType();
                                String status = AmountUtils.changeF2Y(item.getFee());
                                String total_fee = item.getTotalFee() + "";
                                String total_FEE = AmountUtils.changeF2Y(item.getTotalFee());
                                view.setInfo(sys_no, code, pay_type, order_time, money, refund, money_releas, refund_count, status, total_fee, total_FEE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        progressDialog.dismiss();
                    }
                });
    }

    private void parseData(Model item) throws Exception {
        String sys_no = item.getSysNo() + "";
        String code = item.getOutTradeNo();
        String order_time = item.getTimeStart();
        String money = AmountUtils.changeF2Y(item.getCashFee());
        String refund;
        if (TextUtils.isEmpty(item.getTransactionId())) {
            transaction_id = item.getTransactionId();
        }
        if (TextUtils.isEmpty(item.getRefundFee() + "")) {
            refund = AmountUtils.changeF2Y(item.getRefundFee());
        } else {
            refund = AmountUtils.changeF2Y(item.getRefundFee());
        }
        String money_releas = AmountUtils.changeF2Y(item.getFee());
        String refund_count = item.getRefundCount() + "";
        String pay_type = item.getPayType();
        String status = AmountUtils.changeF2Y(item.getFee());
        String total_fee = item.getTotalFee() + "";
        String total_FEE = AmountUtils.changeF2Y(item.getTotalFee());
        view.setInfo(sys_no, code, pay_type, order_time, money, refund, money_releas, refund_count, status, total_fee, total_FEE);
        progressDialog.dismiss();
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


    @Override
    public void Refund(String password, final String code, final String refund, final String total_fee, final String sys_no, final String staff_sys_no, final String type) {
        ServiceManager
                .getInstance()
                .getHomeProvider()
                .staffLogin(ConstantUtils.USER, password)
                .subscribe(new BaseObserver<StaffLoginResp>(context) {
                    @Override
                    protected void doOnNext(StaffLoginResp result) {
                        doRefund(result, type, refund, total_fee, sys_no, staff_sys_no, code);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Toast.makeText(context, "获取信息失败,请稍后重试！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void doRefund(StaffLoginResp result, String type, String refund, String total_fee, String sys_no, String staff_sys_no, String code) {
        if (result != null && !TextUtils.isEmpty(result.toString())) {
            String result_code = result.getCode().toString();
            if (result_code.equals("0")) {
                //验证成功后使用退款接口进行退款
                if (type.equals(context.getString(R.string.wx_type))) {

                    long refund_param = Integer.parseInt(AmountUtils.changeY2F(refund));
                    long total_param = Integer.parseInt(total_fee);
                    long sys_no_param = Integer.parseInt(sys_no);
                    long staff_sys_no_param = Integer.parseInt(staff_sys_no);
                    WxYihuiUnrefundReq accessToken = new WxYihuiUnrefundReq();
                    accessToken.setOutTradeNo(code);
                    accessToken.setRefundFee(refund_param);
                    accessToken.setTotalFee(total_param);
                    accessToken.setSOSysNo(sys_no_param);
                    accessToken.setYwMchId2(staff_sys_no_param);
                    QueryReService.getInstance(context)
                            .doWxYihuiRefund(accessToken)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<WxYihuiUnrefundResp>(context) {
                                @Override
                                protected void doOnNext(WxYihuiUnrefundResp value) {
                                    if (value != null && !TextUtils.isEmpty(value.toString())) {
                                        KLog.i(value);
                                        String success = value.getCode() + "";
                                        showRefundResult(success);
                                    } else {
                                        errorRefundResult();
                                    }
                                }

                                @Override
                                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                    errorRefundResult();
                                }
                            });
                } else if (type.equals(context.getString(R.string.ali_type))) {
                    String refund_param = AmountUtils.changeY2F(refund);
                    long staff_sys_no_param = Integer.parseInt(staff_sys_no);
                    ZfbYihuiUnrefundReq accessToken = new ZfbYihuiUnrefundReq();
                    accessToken.setOutTradeNo(code);
                    accessToken.setRefundFee(refund_param);
                    accessToken.setTotalFee(total_fee);
                    accessToken.setSOSysNo(sys_no);
                    accessToken.setYwMchId2(staff_sys_no_param);
                    QueryReService.getInstance(context)
                            .doZfbYihuiRefund(accessToken)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<ZfbYihuiUnrefundResp>(context) {
                                @Override
                                protected void doOnNext(ZfbYihuiUnrefundResp value) {
                                    if (value != null && !TextUtils.isEmpty(value.toString())) {
                                        KLog.i(value);
                                        String success = value.getCode() + "";
                                        showRefundResult(success);
                                    } else {
                                        errorRefundResult();
                                    }
                                }

                                @Override
                                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                    errorRefundResult();
                                }
                            });

                } else if (ConstantUtils.WX_Type.contains(type) || ConstantUtils.ZFB_Type.contains(type)) {
                    if (TextUtils.equals("108", type) || TextUtils.equals("109", type)) {
                        NetBankUnrefundReq accessToken = new NetBankUnrefundReq();
                        if (ConstantUtils.WX_Type.contains(type)) {
                            accessToken.setChannelType("WX");
                        } else if (ConstantUtils.ZFB_Type.contains(type)) {
                            accessToken.setChannelType("ALI");
                        }
                        String refund_param = AmountUtils.changeY2F(refund);
                        ReqModel mapReq = new ReqModel();
                        mapReq.setOutTradeNo(code);
                        mapReq.setRefundAmount(refund_param);
                        mapReq.setTotalFee(total_fee);

                        accessToken.setReqModel(mapReq);
                        accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
                        //
//                        accessToken.setTransactionId(transaction_id);
                        QueryReService.getInstance(context)
                                .doNetBankRefund(accessToken)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new BaseObserver<NetBankUnrefundResp>(context) {
                                    @Override
                                    protected void doOnNext(NetBankUnrefundResp value) {
                                        if (value != null && !TextUtils.isEmpty(value.toString())) {
                                            KLog.i(value);
                                            String success = value.getCode() + "";
                                            showRefundResult(success);
                                        } else {
                                            errorRefundResult();
                                        }
                                    }

                                    @Override
                                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                        errorRefundResult();
                                    }
                                });

                    } else {
                        BankUnrefundReq accessToken = new BankUnrefundReq();
                        if (ConstantUtils.WX_Type.contains(type)) {
                            accessToken.setPayType("WX");
                        } else if (ConstantUtils.ZFB_Type.contains(type)) {
                            accessToken.setPayType("AliPay");
                        }
                        String refund_param = AmountUtils.changeY2F(refund);
                        long staff_sys_no_param = Integer.parseInt(staff_sys_no);
                        accessToken.setTransactionId(code);
                        accessToken.setRefundFee(refund_param);
                        accessToken.setTotalFee(total_fee);
                        accessToken.setSOSysNo(sys_no);
                        accessToken.setYwMchId2(staff_sys_no_param);
                        QueryReService.getInstance(context)
                                .doBankRefund(accessToken)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new BaseObserver<BankUnrefundResp>(context) {
                                    @Override
                                    protected void doOnNext(BankUnrefundResp value) {
                                        if (value != null && !TextUtils.isEmpty(value.toString())) {
                                            KLog.i(value);
                                            String success = value.getCode() + "";
                                            showRefundResult(success);
                                        } else {
                                            errorRefundResult();
                                        }
                                    }

                                    @Override
                                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                        errorRefundResult();
                                    }
                                });
                    }
                } else {
                    Toast.makeText(context, "配置信息已更改,请登录重试.", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
                progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
                progressDialog.show();

            } else {
                Toast.makeText(context, "您所输入的密码有误，请核实后重新输入！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "获取信息失败,请稍后重试！", Toast.LENGTH_SHORT).show();
        }
    }

    private void errorRefundResult() {
        view.closeActionSheet();
        progressDialog.dismiss();
    }

    private void showRefundResult(String success) {
        if (success.equals("0")) {
            //成功
            Toast.makeText(context, "退款成功！", Toast.LENGTH_SHORT).show();
            view.changStatus();
            initData(false);
        } else {
            //失败
            Toast.makeText(context, "退款失败！", Toast.LENGTH_SHORT).show();
        }
        view.closeActionSheet();
        progressDialog.dismiss();
    }
}
