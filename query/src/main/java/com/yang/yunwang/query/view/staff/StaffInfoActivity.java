package com.yang.yunwang.query.view.staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.contract.StaffInfoContract;
import com.yang.yunwang.query.api.presenter.StaffInfoPresenter;


@Route(path = IOrdersProvider.ORDERS_ACT_STAFF_INFO)
public class StaffInfoActivity extends BaseActivity implements StaffInfoContract.View, View.OnClickListener {

    private EditText edit_staff_info_id;
    private EditText edit_staff_info_customer;
    private EditText edit_staff_info_display;
    private EditText edit_staff_info_tel;
    private EditText edit_staff_info_email;
    private EditText edit_staff_info_shopid;
    private EditText edit_staff_info_time;
    private TextView btn_staff_info_control;
    private StaffInfoContract.Presenter presenter;
    private TextView top_rate;
    private TextView tenants_rate;
    private TextView registC;
    private TextView regist_btn;
    private boolean isFromHome = false;
    private LinearLayout linear_unrefund_status_background2;
    private LinearLayout linear_unrefund_status_background3;
    private LinearLayout linear_unrefund_status_background;
    private boolean isFromMerch = false;
    private boolean isFromHomeDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_staffinfo);
        setTitleText(this.getString(R.string.staff_info_title));
        setHomeBarVisisble(true);
        new StaffInfoPresenter(this, this);
        init();
        initListener();
    }

    private void init() {
        isFromHomeDis = getIntent().getBooleanExtra(ConstantUtils.fromHomeDis, false);
        isFromHome = getIntent().getBooleanExtra(ConstantUtils.fromHome, false);
        isFromMerch = getIntent().getBooleanExtra(ConstantUtils.fromMerch, false);
        linear_unrefund_status_background2 = (LinearLayout) findViewById(R.id.linear_unrefund_status_background2);
        linear_unrefund_status_background3 = (LinearLayout) findViewById(R.id.linear_unrefund_status_background3);
        linear_unrefund_status_background = (LinearLayout) findViewById(R.id.linear_unrefund_status_background);
        if (isFromHomeDis) {
            findViewById(R.id.rel_staff_info_item_1).setVisibility(View.GONE);
            findViewById(R.id.rel_staff_info_item_6).setVisibility(View.GONE);
            findViewById(R.id.rel_staff_info_item_7).setVisibility(View.GONE);
            findViewById(R.id.view_6).setVisibility(View.GONE);
            findViewById(R.id.view_5).setVisibility(View.GONE);
            findViewById(R.id.view_1).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textView3)).setText("真实姓名");
        }
        if (isFromHome && !isFromMerch) {
            findViewById(R.id.rel_staff_info_item_1).setVisibility(View.GONE);
            findViewById(R.id.rel_staff_info_item_6).setVisibility(View.GONE);
            findViewById(R.id.rel_staff_info_item_7).setVisibility(View.GONE);
            findViewById(R.id.view_6).setVisibility(View.GONE);
            findViewById(R.id.view_5).setVisibility(View.GONE);
            findViewById(R.id.view_1).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textView3)).setText("真实姓名");
            linear_unrefund_status_background2.setVisibility(View.GONE);
            linear_unrefund_status_background3.setVisibility(View.GONE);
            linear_unrefund_status_background.setVisibility(View.GONE);
        }
        edit_staff_info_id = (EditText) findViewById(R.id.edit_staff_info_id);
        edit_staff_info_customer = (EditText) findViewById(R.id.edit_staff_info_customer);
        edit_staff_info_display = (EditText) findViewById(R.id.edit_staff_info_display);
        edit_staff_info_tel = (EditText) findViewById(R.id.edit_staff_info_tel);
        edit_staff_info_email = (EditText) findViewById(R.id.edit_staff_info_email);
        edit_staff_info_shopid = (EditText) findViewById(R.id.edit_staff_info_shopid);
        edit_staff_info_time = (EditText) findViewById(R.id.edit_staff_info_time);
        btn_staff_info_control = (TextView) findViewById(R.id.btn_staff_info_control);
        if (isFromMerch) {
            linear_unrefund_status_background2.setVisibility(View.GONE);
            linear_unrefund_status_background3.setVisibility(View.GONE);
        }
        tenants_rate = (TextView) findViewById(R.id.btn_staff_info_control1);
        KLog.i(isFromHome +"---"+ !isFromMerch);
        if (isFromHome && !isFromMerch) {
            tenants_rate.setText("权限分配");
        }
        if (isFromHome && isFromMerch) {
            tenants_rate.setText("员工费率订单");
        }
        tenants_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KLog.i(isFromMerch + "fromMerch1");
                KLog.i(isFromHome + "isFromHome");
                if (isFromHome) {
                    if (isFromMerch) {
                        MyBundle intent_tenants_rate = new MyBundle();//StaffInfoActivity.this, CommonSearchActivity.class
                        intent_tenants_rate.put("staff_id", edit_staff_info_id.getText().toString());
                        intent_tenants_rate.put("from_merch", isFromMerch);
//                        startActivity(intent_tenants_rate);
                        OrdersIntent.getCommonSearch(intent_tenants_rate);
                    } else {
                        KLog.i("------------yuan--gong-----");
                        presenter.initDATAString("SystemUserSysNo", ConstantUtils.URL_STAFF_ROLE_LIST, "", ConstantUtils.URL_ROLE_LIST, isFromHome,edit_staff_info_id.getText().toString());
                    }

                } else {
                    MyBundle intent_tenants_rate = new MyBundle();//StaffInfoActivity.this, CommonSearchActivity.class
                    intent_tenants_rate.put("staff_id", edit_staff_info_id.getText().toString());
                    OrdersIntent.getCommonSearch(intent_tenants_rate);

//                    startActivity(intent_tenants_rate);
                }
            }
        });
        top_rate = (TextView) findViewById(R.id.btn_staff_info_control2);
        top_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBundle intent_top_rate = new MyBundle();//StaffInfoActivity.this, CommonSearchActivity.class
                intent_top_rate.put("staff_id", edit_staff_info_id.getText().toString());
                intent_top_rate.put("top_rate", true);
//                startActivity(intent_top_rate);
                OrdersIntent.getCommonSearch(intent_top_rate);

            }
        });
        regist_btn = (TextView) findViewById(R.id.btn_staff_info_control3);
        regist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBundle intent_regist = new MyBundle();//StaffInfoActivity.this, RegistInfoActivity.class
                intent_regist.put("staff_id", edit_staff_info_id.getText().toString());
//                startActivity(intent_regist);
                OrdersIntent.getRegistPage(intent_regist);
            }
        });
        registC = (TextView) findViewById(R.id.textView11);
        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        switch (customer_type) {
            case "0":
                btn_staff_info_control.setText("查看商户");
                break;
            case "1":
                btn_staff_info_control.setText("查看订单");
                break;
        }

        presenter.initData();
    }

    private void initListener() {
        btn_staff_info_control.setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
        getLlBasehomeBack().setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_home) {
            HomeIntent.launchHome();
            this.finish();

        } else if (i == R.id.image_back) {
            this.finish();

        } else if (i == R.id.btn_staff_info_control) {
            String customer_type = ConstantUtils.CUSTOMERS_TYPE;
            switch (customer_type) {
                case "0":
                    MyBundle intent_shop = new MyBundle();//this, MerchantSearchActivity.class
                    intent_shop.put("staff_id", edit_staff_info_id.getText().toString());
                    intent_shop.put(ConstantUtils.FWS_YUANGONG, true);
//                    this.startActivity(intent_shop);
                    OrdersIntent.getMerchSearch(intent_shop);
                    break;
                case "1":
                    MyBundle intent_orders = new MyBundle();//this, OrdersSearchActivity.class
                    intent_orders.put("staff_id", edit_staff_info_id.getText().toString());
//                    this.startActivity(intent_orders);
                    OrdersIntent.orderSearch(intent_orders);
                    break;
            }

        }
    }

    @Override
    public void setInfo(String staff_id,
                        String staff_customer,
                        String staff_display,
                        String staff_tel,
                        String staff_email,
                        String staff_shopid,
                        String staff_time) {
        edit_staff_info_id.setText(staff_id);
        edit_staff_info_customer.setText(staff_customer);
        edit_staff_info_display.setText(staff_display);
        edit_staff_info_tel.setText(staff_tel);
        edit_staff_info_email.setText(staff_email);
        edit_staff_info_shopid.setText(staff_shopid);
        edit_staff_info_time.setText(staff_time);
    }

    @Override
    public Intent loadInstance() {
        return getIntent();
    }


    @Override
    public void setPresenter(StaffInfoContract.Presenter presenter) {
        this.presenter=presenter;
    }
}
