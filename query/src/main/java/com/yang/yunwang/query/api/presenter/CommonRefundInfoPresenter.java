package com.yang.yunwang.query.api.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.yang.yunwang.query.api.contract.CommonRefundInfoContract;
import com.yang.yunwang.query.api.bean.commonrefund.Model;

/**
 * Created by Administrator on 2018/7/11.
 */

public class CommonRefundInfoPresenter implements CommonRefundInfoContract.Presenter {
    private final CommonRefundInfoContract.View view;
    private final Context context;
    private ProgressDialog progressDialog;
    private Model bean;

    public CommonRefundInfoPresenter(CommonRefundInfoContract.View view, Context context) {
        this.view=view;
        this.context=context;
        view.setPresenter(this);
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
        int i = intent.getIntExtra("pos", -1);
        bean = (Model) intent.getSerializableExtra("common_bean");
        String loginName = bean.getLoginName();
        String realName = bean.getDisplayName();
        String outOrderNo = bean.getOutTradeNo();
        String tradeType = bean.getPayType();
        String refundFee = bean.getRefundFee();
        String merchName = bean.getCustomerName();
        String refundTime = bean.getCreateTime();
        String moneyType = "CNY";
        String tradeTime = bean.getTimeStart();
        String tradeFee = bean.getTotalFee();
        view.setInfo(loginName, realName, outOrderNo, tradeType, refundFee, merchName, refundTime, moneyType, tradeTime, tradeFee);
    }
}
