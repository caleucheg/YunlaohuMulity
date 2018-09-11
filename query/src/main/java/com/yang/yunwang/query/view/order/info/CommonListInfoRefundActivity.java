package com.yang.yunwang.query.view.order.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.dao.PassageWay;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.commonrefund.Model;
import com.yang.yunwang.query.api.contract.CommonRefundInfoContract;
import com.yang.yunwang.query.api.presenter.CommonRefundInfoPresenter;

import java.util.ArrayList;

@Route(path = IOrdersProvider.ORDERS_ACT_COMMON_REFUND_INFO)
public class CommonListInfoRefundActivity extends BaseActivity implements CommonRefundInfoContract.View {

    private EditText edit_sys_no;
    private EditText edit_customer_name;
    private EditText edit_code;
    private EditText edit_money;
    private EditText edit_time;
    private EditText edit_pay_type;
    private EditText edit_login_name;
    private EditText edit_display_name;
    private CommonRefundInfoContract.Presenter presenter;
    private EditText edit_rate_fee;
    private EditText edit_rate;
    private EditText edit_money_type;
    private Model bean;
    private EditText loginName;
    private EditText realName;
    private EditText outOrderNo;
    private EditText tradeType;
    private EditText refundFee;
    private EditText merchName;
    private EditText refundTime;
    private EditText moneyType;
    private EditText tradeTime;
    private EditText tradeFee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_info_refund);
        setTitleText("退款查询信息");
        setHomeBarVisisble(true);
        new CommonRefundInfoPresenter(this, this);
        Intent intent = getIntent();
        bean = (Model) intent.getSerializableExtra("common_bean");
        loginName = (EditText) findViewById(R.id.edit_orders_search_info_id);
        realName = (EditText) findViewById(R.id.edit_orders_search_info_customer);
        outOrderNo = (EditText) findViewById(R.id.edit_orders_search_info_code);
        edit_pay_type = (EditText) findViewById(R.id.edit_orders_search_info_money);
        tradeType = (EditText) findViewById(R.id.edit_orders_search_info_money);
        refundFee = (EditText) findViewById(R.id.edit_orders_search_info_star_time);
        merchName = (EditText) findViewById(R.id.edit_orders_search_info_pay_type);
        refundTime = (EditText) findViewById(R.id.edit_orders_search_info_star_time12);
        moneyType = (EditText) findViewById(R.id.edit_orders_search_info_star_time1);
        tradeTime = (EditText) findViewById(R.id.edit_orders_search_info_star_time2);
        tradeFee = (EditText) findViewById(R.id.edit_orders_search_info_money_type);


        getLlBasehomeBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeIntent.launchHome();
                CommonListInfoRefundActivity.this.finish();
            }
        });
        getLlBasetitleBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonListInfoRefundActivity.this.finish();
            }
        });
        presenter.initData();
    }


    @Override
    public Intent loadInstance() {
        return getIntent();
    }

    @Override
    public void setInfo(String loginName, String realName, String outOrderNo, String tradeType, String refundFee, String merchName, String refundTime, String moneyType, String tradeTime, String tradeFee) {
        this.loginName.setText(loginName);
        this.realName.setText(realName);
        this.outOrderNo.setText(outOrderNo);
//        this.tradeType.setText(tradeType);
        ArrayList<String> types = new ArrayList();
        ArrayList<String> typeNames = new ArrayList();
        for (PassageWay p : ConstantUtils.PASSAGWE_WAYS) {
            types.add(p.getType());
            typeNames.add(p.getTypeName());
        }
        int index = types.indexOf(tradeType);
        if (index > -1) {
            edit_pay_type.setText(typeNames.get(index));
        }
        String text = "";
        try {
            text = AmountUtils.changeF2Y(refundFee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.refundFee.setText(text);
        this.merchName.setText(merchName);
        this.refundTime.setText(refundTime);
        this.moneyType.setText(moneyType);
        this.tradeTime.setText(tradeTime);
        String text1 = "";
        try {
            text1 = AmountUtils.changeF2Y(tradeFee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.tradeFee.setText(text1);
    }

    @Override
    public void setPresenter(CommonRefundInfoContract.Presenter presenter) {
        this.presenter=presenter;
    }
}
