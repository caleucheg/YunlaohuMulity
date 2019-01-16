package com.yang.yunwang.query.api.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.socks.library.KLog;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.query.api.QueryReService;
import com.yang.yunwang.query.api.bean.ordersearch.Model;
import com.yang.yunwang.query.api.bean.ordersearch.OrderSearchReq;
import com.yang.yunwang.query.api.bean.ordersearch.OrderSearchResp;
import com.yang.yunwang.query.api.contract.OrderListInfoContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/2.
 */

public class OrderListInfoPresenter implements OrderListInfoContract.Presenter {
    private final OrderListInfoContract.View view;
    private final Context context;

    public OrderListInfoPresenter(OrderListInfoContract.View view, Context context) {
        this.view = view;
        view.setPresenter(this);
        this.context = context;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void initData() {
        Intent intent = view.loadInstance();
        boolean isNoti = intent.getBooleanExtra("noti", false);
        if (isNoti) {
            String code = intent.getStringExtra("code");
            String time_Start = intent.getStringExtra("Time_Start");
            String time_End = intent.getStringExtra("Time_End");
            OrderSearchReq accessToken = new OrderSearchReq();
            accessToken.setOutTradeNo(code);
            String sysNo = CommonShare.getLoginData(context, "SysNo", "");
            accessToken.setSystemUserSysNo(sysNo);
            if (!TextUtils.isEmpty(time_End)) {
                accessToken.setTimeEnd(time_End);
            }
            if (!TextUtils.isEmpty(time_Start)) {
                accessToken.setTimeStart(time_Start);
            }
            QueryReService.getInstance(context)
                    .getStaffOrders(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<OrderSearchResp>(context) {
                        @Override
                        protected void doOnNext(OrderSearchResp value) {
                            if (value.getModel().size() > 0) {
                                Model bean = value.getModel().get(0);
                                String sys_no = bean.getSysNo() + "";
                                String code = bean.getOutTradeNo();
                                String money = "";
                                String moneyCash = "";
                                try {
                                    money = AmountUtils.changeF2YWithDDD(bean.getTotalFee());
                                    moneyCash = AmountUtils.changeF2YWithDDD(bean.getCashFee());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                String date = bean.getTimeStart();
                                String pay_type = bean.getPayType();
                                String customer = bean.getCustomerName();
                                String loginname = bean.getLoginName();
                                String displayname = bean.getDisplayName();
                                view.setInfo(sys_no, customer, code, pay_type, money, date, loginname, displayname, moneyCash);
                            } else {
                                Toast.makeText(context, "暂无此订单数据", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                            Toast.makeText(context, "网络异常,暂无此订单数据", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            boolean isNewOrder = intent.getBooleanExtra("isNewOrder", false);
            if (isNewOrder) {
                String json = intent.getStringExtra("bean");
                if (!TextUtils.isEmpty(json)) {
                    Gson gson = new Gson();
                    Model bean = gson.fromJson(json, Model.class);
                    KLog.i(bean);
                    String sys_no = bean.getSysNo() + "";
                    String code = bean.getOutTradeNo();
                    String money = "";
                    String moneyCash = "";
                    try {
                        money = AmountUtils.changeF2YWithDDD(bean.getTotalFee());
                        moneyCash = AmountUtils.changeF2YWithDDD(bean.getCashFee());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String date = bean.getTimeStart();
                    String pay_type = bean.getPayType();
                    String customer = bean.getCustomerName();
                    String loginname = bean.getLoginName();
                    String displayname = bean.getDisplayName();
                    view.setInfo(sys_no, customer, code, pay_type, money, date, loginname, displayname, moneyCash);
                }

            } else {
                Model bean = (Model) intent.getSerializableExtra("bean");
                KLog.i(bean);
                String sys_no = bean.getSysNo() + "";
                String code = bean.getOutTradeNo();
                String money = "";
                String moneyCash = "";
                try {
                    money = AmountUtils.changeF2Y(bean.getTotalFee());
                    moneyCash = AmountUtils.changeF2Y(bean.getCashFee());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String date = bean.getTimeStart();
                String pay_type = bean.getPayType();
                String customer = bean.getCustomerName();
                String loginname = bean.getLoginName();
                String displayname = bean.getDisplayName();
                view.setInfo(sys_no, customer, code, pay_type, money, date, loginname, displayname, moneyCash);
            }
        }

    }
}
