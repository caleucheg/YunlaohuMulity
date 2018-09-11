package com.yang.yunwang.query.view.order.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.contract.OrderSettleInfoContract;
import com.yang.yunwang.query.api.presenter.OrderSettleInfoPresenter;

@Route(path = IOrdersProvider.ORDERS_ACT_ORDER_SETTLE_INFO)
public class SettlementInfoActivity extends BaseActivity implements OrderSettleInfoContract.View, View.OnClickListener {

    private EditText edit_settlment_name;
    private EditText edit_settlement_money;
    private EditText edit_settlement_cash;
    private EditText edit_settlement_cny;
    private EditText edit_settlement_count;
    private OrderSettleInfoContract.Presenter presenter;
    private EditText edit_settlement_cashr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settlementinfo);
        setTitleText(this.getString(R.string.settlement_title));
        setHomeBarVisisble(true);

        edit_settlment_name = (EditText) findViewById(R.id.edit_settlment_name);
        edit_settlement_money = (EditText) findViewById(R.id.edit_settlement_money);
        edit_settlement_cash = (EditText) findViewById(R.id.edit_settlement_cash);
        edit_settlement_cny = (EditText) findViewById(R.id.edit_settlement_cny);
        edit_settlement_count = (EditText) findViewById(R.id.edit_settlement_count);
        edit_settlement_cashr = (EditText) findViewById(R.id.edit_settlement_cashr);
        if (!ConstantUtils.NEW_TYPE.equals("3")) {
            findViewById(R.id.rel_settlement_item_3r).setVisibility(View.GONE);
            findViewById(R.id.view_2r).setVisibility(View.GONE);
        }
        new OrderSettleInfoPresenter(this, this);
        presenter.initData();
    }

    @Override
    public void setInfo(String name, String money, String cash, String cny, String count) {
        edit_settlment_name.setText(name);
        edit_settlement_money.setText(money + "元");
        if (cash.endsWith("元")) {
            edit_settlement_cash.setText(cash.substring(0, cash.length() - 1) + "元");
        } else {
            edit_settlement_cash.setText(cash + "元");
        }
        edit_settlement_cny.setText(cny);
        edit_settlement_count.setText(count);
        String[] f = cash.split("元");
        long refundF = Long.parseLong(AmountUtils.changeY2F(money)) - Long.parseLong(AmountUtils.changeY2F(f[0]));
        String rs = "-1";
        try {
            rs = AmountUtils.changeF2Y(refundF + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        edit_settlement_cashr.setText(rs + "元");
        getLlBasehomeBack().setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
    }

    @Override
    public Intent loadInstance() {
        return getIntent();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_home) {
            HomeIntent.launchHome();
            this.finish();

        } else if (i == R.id.image_back) {
            this.finish();

        }
    }

    @Override
    public void setPresenter(OrderSettleInfoContract.Presenter presenter) {
        this.presenter=presenter;
    }
}
