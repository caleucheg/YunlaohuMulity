package com.yang.yunwang.query.view.order.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.dao.PassageWay;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;

import java.util.ArrayList;

@Route(path = IOrdersProvider.ORDERS_ACT_DAY_COMMON_INFO)
public class DaySettlementInfoActivity extends BaseActivity implements View.OnClickListener {

    private TextView edit_settlment_name;
    private TextView edit_settlement_money;
    private TextView edit_settlement_cash;
    private TextView edit_settlement_cny;
    private TextView edit_settlement_count;
    private TextView edit_settlement_cashr;
    private TextView edit_settlment_name4;
    private TextView edit_settlment_name3;
    private TextView edit_settlment_name2;
    private TextView edit_settlment_name41;
    private TextView edit_settlment_name42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_day_settlementinfo);
        setTitleText("日交易汇总详情");
        KLog.i("huizong");
        setHomeBarVisisble(true);
        edit_settlment_name = (TextView) findViewById(R.id.edit_settlment_name);
        edit_settlement_money = (TextView) findViewById(R.id.edit_settlement_money);
        edit_settlement_cash = (TextView) findViewById(R.id.edit_settlement_cash);
        edit_settlement_cny = (TextView) findViewById(R.id.edit_settlement_cny);
        edit_settlement_count = (TextView) findViewById(R.id.edit_settlement_count);
        edit_settlement_cashr = (TextView) findViewById(R.id.edit_settlement_cashr);
        edit_settlment_name4 = (TextView) findViewById(R.id.edit_settlment_name4);
        edit_settlment_name3 = (TextView) findViewById(R.id.edit_settlment_name3);
        edit_settlment_name2 = (TextView) findViewById(R.id.edit_settlment_name2);
        edit_settlment_name41 = (TextView) findViewById(R.id.edit_settlment_name41);
        edit_settlment_name42 = (TextView) findViewById(R.id.edit_settlment_name42);
        Intent intent = getIntent();
        String loginName = intent.getStringExtra("loginName");
        String displayName = intent.getStringExtra("displayName");
        String orderTime = intent.getStringExtra("OrderTime");
        long tradeCount = intent.getLongExtra("TradeCount", 0L);
        long total_fee = intent.getLongExtra("Total_fee", 0L);
        long cash_fee = intent.getLongExtra("Cash_fee", 0L);
        long refund_fee = intent.getLongExtra("Refund_fee", 0L);
        long cash_refund_fee = intent.getLongExtra("Cash_refund_fee", 0L);
        long fee = intent.getLongExtra("Fee", 0L);
        edit_settlment_name.setText(displayName);
        edit_settlment_name2.setText(loginName);
        edit_settlment_name3.setText(orderTime);
        String s = "";
        String s1 = "";
        String s2 = "";
        String s3 = "";
        String s4 = "";
        try {
            s = AmountUtils.changeF2Y(total_fee);
            s1 = AmountUtils.changeF2Y(cash_fee);
            s2 = AmountUtils.changeF2Y(refund_fee);
            s3 = AmountUtils.changeF2Y(cash_refund_fee);
            s4 = AmountUtils.changeF2Y(fee);
        } catch (Exception e) {
            e.printStackTrace();
        }

        edit_settlement_money.setText(s + "元");
        edit_settlement_cash.setText(s1 + "元");
        edit_settlement_cashr.setText(s2 + "元");
        edit_settlement_cny.setText(s3 + "元");
        edit_settlment_name4.setText(s4 + "元");
        edit_settlement_count.setText(tradeCount + "笔");
        long money = intent.getLongExtra("Money",0L);
        String pay_type = intent.getStringExtra("Pay_Type");
        String s5 = "";
        try {
            s5 = AmountUtils.changeF2Y(money);
        } catch (Exception e) {
            e.printStackTrace();
        }
        edit_settlment_name41.setText(s5 + "元");
        ArrayList<String> types = new ArrayList();
        ArrayList<String> typeNames = new ArrayList();
        for (PassageWay p : ConstantUtils.PASSAGWE_WAYS) {
            types.add(p.getType());
            typeNames.add(p.getTypeName());
        }
        int index = types.indexOf(pay_type);
        if (index > -1) {
            edit_settlment_name42.setText(typeNames.get(index));
        }
        getLlBasehomeBack().setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
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
}
