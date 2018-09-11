package com.yang.yunwang.query.api.presenter;

import android.content.Context;
import android.content.Intent;

import com.yang.yunwang.query.api.contract.OrderSettleInfoContract;

/**
 * Created by Administrator on 2018/7/5.
 */

public class OrderSettleInfoPresenter implements OrderSettleInfoContract.Presenter {
    private final OrderSettleInfoContract.View view;
    private final Context context;

    public OrderSettleInfoPresenter(OrderSettleInfoContract.View view, Context context) {
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
        String name = intent.getStringExtra("names");
        String money = intent.getStringExtra("money");
        String cash = intent.getStringExtra("cash");
        String cny_type = intent.getStringExtra("cny_type");
        String count = intent.getStringExtra("count");
        view.setInfo(name, money, cash, cny_type, count);
    }
}
