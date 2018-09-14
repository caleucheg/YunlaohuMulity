package com.yang.yunwang.query.api.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.basereq.BaseInfoService;
import com.yang.yunwang.base.basereq.bean.merchlogin.MerchLoginResp;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigReq;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigResp;
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
import com.yang.yunwang.query.api.bean.refundsearch.refundrole.Datum;
import com.yang.yunwang.query.api.bean.refundsearch.refundrole.RefundRoleReq;
import com.yang.yunwang.query.api.bean.refundsearch.refundrole.RefundRoleResp;
import com.yang.yunwang.query.api.bean.unrefund.bank.BankUnrefundReq;
import com.yang.yunwang.query.api.bean.unrefund.bank.BankUnrefundResp;
import com.yang.yunwang.query.api.bean.unrefund.netbank.NetBankUnrefundReq;
import com.yang.yunwang.query.api.bean.unrefund.netbank.NetBankUnrefundResp;
import com.yang.yunwang.query.api.bean.unrefund.netbank.ReqModel;
import com.yang.yunwang.query.api.bean.unrefund.yihui.wx.WxYihuiUnrefundReq;
import com.yang.yunwang.query.api.bean.unrefund.yihui.wx.WxYihuiUnrefundResp;
import com.yang.yunwang.query.api.bean.unrefund.yihui.zfb.ZfbYihuiUnrefundReq;
import com.yang.yunwang.query.api.bean.unrefund.yihui.zfb.ZfbYihuiUnrefundResp;
import com.yang.yunwang.query.api.contract.MerchRefundInfoContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/6.
 */

public class MerchRefundInfoPresenter implements MerchRefundInfoContract.Presenter {
    private final MerchRefundInfoContract.View view;
    private final Context context;
    private String old_sys_no;
    private boolean hasRole = false;
    private ProgressDialog progressDialog;
    private String orderNo;
    private Intent intent;

    public MerchRefundInfoPresenter(MerchRefundInfoContract.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
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
    public void initData(Intent intent) {
        this.intent = intent;
        orderNo = intent.getStringExtra("code_list");
        getPayConfig(ConstantUtils.SYS_NO);
        initRole(intent);
    }

    @Override
    public void refreshData() {
        RefundListReq accessToken = new RefundListReq();
        accessToken.setOutTradeNo(orderNo);
        accessToken.setSystemUserSysNo(old_sys_no);
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
                                String status = AmountUtils.changeF2Y(item.getFee());
                                String total_FEE = AmountUtils.changeF2Y(item.getTotalFee());
                                String refund = AmountUtils.changeF2Y(item.getRefundFee());
                                String refund_count = item.getRefundCount() + "";
                                view.refreshData(total_FEE, status, refund, refund_count);
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

    private void errorRefundResult() {
        view.closeActionSheet();
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    private void showRefundResult(String success) {
        if (success.equals("0")) {
            //成功
            Toast.makeText(context, "退款成功！", Toast.LENGTH_SHORT).show();
            view.changStatus();
            initData(intent);
        } else {
            //失败
            Toast.makeText(context, "退款失败！", Toast.LENGTH_SHORT).show();
        }
        view.closeActionSheet();
        progressDialog.dismiss();
    }

    private void doRefund(MerchLoginResp result, String type, String refund, String total_fee, String sys_no, String staff_sys_no, String code, String transaction_id_list) {
        if (result != null && !TextUtils.isEmpty(result.toString())) {
            String result_code = result.getCode().toString();
            if (result_code.equals("0")) {
                //验证成功后使用退款接口进行退款
                if (type.equals(context.getString(R.string.wx_type))) {
                    long refund_param = Integer.parseInt(AmountUtils.changeY2F(refund));
                    long total_param = Integer.parseInt(total_fee);
                    long sys_no_param = Integer.parseInt(sys_no);
                    long staff_sys_no_param = Integer.parseInt(old_sys_no);
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
                    long staff_sys_no_param = Integer.parseInt(old_sys_no);
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
                        int staff_sys_no_param = Integer.parseInt(old_sys_no);
                        accessToken.setReqModel(mapReq);
                        accessToken.setSystemUserSysNo(staff_sys_no_param + "");
                        //去掉transactionId
//                        accessToken.setTransactionId(transaction_id_list);
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
                        long staff_sys_no_param = Integer.parseInt(old_sys_no);
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
                    if (progressDialog!=null){
                        progressDialog.dismiss();
                    }
                    return;
                }


            } else {
                Toast.makeText(context, "您所输入的密码有误，请核实后重新输入！", Toast.LENGTH_SHORT).show();
                if (progressDialog!=null){
                    progressDialog.dismiss();
                }
            }
        } else {
            Toast.makeText(context, "获取信息失败,请稍后重试！", Toast.LENGTH_SHORT).show();
            if (progressDialog!=null){
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void refundFee(String password, final String code, final String refund, final String total_fee, final String sys_no, final String type, final String transaction_id_list) {
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.show();
        ServiceManager
                .getInstance()
                .getHomeProvider()
                .merchLogin(ConstantUtils.USER, password)
                .subscribe(new BaseObserver<MerchLoginResp>(context) {
                    @Override
                    protected void doOnNext(MerchLoginResp result) {
                        doRefund(result, type, refund, total_fee, sys_no, old_sys_no, code, transaction_id_list);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Toast.makeText(context, "获取信息失败,请稍后重试！", Toast.LENGTH_SHORT).show();
                        if (progressDialog!=null){
                            progressDialog.dismiss();
                        }
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

    private void initRole(Intent intent) {
        old_sys_no = intent.getStringExtra("Old_sys_no");
        RefundRoleReq accessToken = new RefundRoleReq();
        accessToken.setSystemUserSysNo(old_sys_no);
        QueryReService.getInstance(context)
                .getRefundRole(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<RefundRoleResp>(context) {
                    @Override
                    protected void doOnNext(RefundRoleResp result) {
                        if (result != null && !TextUtils.isEmpty(result.toString())) {
                            KLog.i(result);
                            String code = result.getCode() + "";
                            if (code.equals("0")) {
                                List<Datum> jsonArray = result.getData();
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    hasRole = jsonArray.get(i).getApplicationSysNo() == 1;
                                }
                            } else {
                                hasRole = false;
                            }
                            view.init(hasRole, true);
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        view.init(hasRole, false);
                    }
                });

    }
}
