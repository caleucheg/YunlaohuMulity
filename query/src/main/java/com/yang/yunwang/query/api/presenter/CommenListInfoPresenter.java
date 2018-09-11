package com.yang.yunwang.query.api.presenter;

import android.content.Context;
import android.content.Intent;

import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.query.api.bean.commonsearch.Model;
import com.yang.yunwang.query.api.contract.CommenListInfoContract;

/**
 * Created by Administrator on 2018/7/10.
 */

public class CommenListInfoPresenter implements CommenListInfoContract.Presenter {
    private final CommenListInfoContract.View view;
    private final Context context;
    private Model bean;
    private boolean is_top_rate = false;

    public CommenListInfoPresenter(CommenListInfoContract.View view, Context context) {
        this.view = view;
        this.context = context;
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
//        String code = intent.getStringExtra("code");
        is_top_rate = intent.getBooleanExtra("top_rate", false);
        int i = intent.getIntExtra("pos", -1);
        bean = (Model) intent.getSerializableExtra("common_bean");
        String code1 = bean.getSysNo() + "";
        String customer = bean.getCustomerName();
        String orderNo = bean.getOutTradeNo();
        String money = "";
        try {
            money = AmountUtils.changeF2Y(bean.getTotalFee());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String rate = "";
        String fee = "";
        if (!is_top_rate) {
            rate = bean.getRate() + "";
            String s = null;
            try {
                s = AmountUtils.changeF2Y(bean.getRateFee());
            } catch (Exception e) {
                s="";

            }
            fee = s + "";
        } else {
            rate = bean.getUserRate() + "";
            String s = null;
            try {
                s = AmountUtils.changeF2Y(bean.getUserRateFee());
            } catch (Exception e) {
                s="";
            }
            fee = s + "";
        }
        String payType = bean.getPayType();
        String money_type = bean.getCashFeeType();
        String date = bean.getTimeStart();
        String disName = bean.getDisplayName();
        String loginName = bean.getLoginName();
        view.setInfo(code1, customer, orderNo, money, rate, fee, payType, money_type, date, disName, loginName);
    }
}
