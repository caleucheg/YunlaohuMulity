package com.yang.yunwang.query.view.order.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.dao.PassageWay;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.contract.OrderListInfoContract;
import com.yang.yunwang.query.api.presenter.OrderListInfoPresenter;

import java.util.ArrayList;

@Route(path = IOrdersProvider.ORDERS_ACT_NEW_ORDER_LIST_INFO)
public class NewOrdersListInfoActivity extends BaseActivity implements OrderListInfoContract.View {

    //    private EditText edit_sys_no;
//    private EditText edit_customer_name;
//    private EditText edit_code;
//    private EditText edit_money;
//    private EditText edit_time;
//    private EditText edit_pay_type;
//    private EditText edit_login_name;
//    private EditText edit_display_name;
    //    private ImageView image_back;
//    private ImageView image_home;
//    private RelativeLayout relativeLayout_user;             //登录名显示区域
//    private RelativeLayout relativeLayout_user_name;        //真实姓名显示区域
//    private View view_user;                                 //登录名底线
//    private View view_user_name;                            //真实姓名显示区域
    //    private RecListInfoPresenterInterface presenter;
//    private EditText edit_money_cash;
    private OrderListInfoContract.Presenter presenter;
    /**
     * 100000000
     */
    private TextView tv_total_fee;
    /**
     * 100000000
     */
    private TextView tv_cash_fee;
    /**
     * 100000000
     */
    private TextView tv_order_num;
    /**
     * 10000000000000000000000000000
     */
    private TextView tv_customer_name;
    /**
     * 100000000
     */
    private TextView tc_trade_type;
    /**
     * 100000000
     */
    private TextView tv_order_time;
    /**
     * 100000000
     */
    private TextView tv_inner_order_num;
    private TextView tv_staff_name;
    private LinearLayout ll_staff_name;
    private View line_staff_name;
    private boolean isFromArouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ll_order_detials);
        initView();
        setTitleText(this.getString(R.string.orders_search_info_title));
        new OrderListInfoPresenter(this, this);
        isFromArouter = getIntent().getBooleanExtra("isFromArouter", false);
        setHomeBarVisisble(false);
        getLlBasetitleBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewOrdersListInfoActivity.this.finish();
            }
        });
        presenter.initData();
        String type = ConstantUtils.NEW_TYPE;
        switch (type) {
            case "0":
                ll_staff_name.setVisibility(View.VISIBLE);
                line_staff_name.setVisibility(View.VISIBLE);
                break;
            case "1":
                if (!isFromArouter) {
                    ll_staff_name.setVisibility(View.VISIBLE);
                    line_staff_name.setVisibility(View.VISIBLE);
                }
                break;
            case "2":
                ll_staff_name.setVisibility(View.VISIBLE);
                line_staff_name.setVisibility(View.VISIBLE);
                break;
        }
    }


    public void setInfo(String sys_no, String customer, String code, String pay_type, String money, String date, String loginname, String displayname, String moneyCash) {
        tv_inner_order_num.setText(sys_no);
        tv_customer_name.setText(customer);
        tv_order_num.setText(code);
        tv_total_fee.setText(money);
        tv_order_time.setText(date);
        tv_cash_fee.setText(moneyCash);
        tv_staff_name.setText(displayname);
//        104=兴业微信； 105=兴业支付宝 ； 106=浦发微信；  107=浦发支付宝
        ArrayList<String> types = new ArrayList();
        ArrayList<String> typeNames = new ArrayList();
        for (PassageWay p : ConstantUtils.PASSAGWE_WAYS) {
            types.add(p.getType());
            typeNames.add(p.getTypeName());
        }
        int index = types.indexOf(pay_type);
        if (index > -1) {
            tc_trade_type.setText(typeNames.get(index));
        }


    }


    public Intent loadInstance() {
        return getIntent();
    }

    @Override
    public void setPresenter(OrderListInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void initView() {
        tv_total_fee = (TextView) findViewById(R.id.tv_total_fee);
        tv_cash_fee = (TextView) findViewById(R.id.tv_cash_fee);
        tv_order_num = (TextView) findViewById(R.id.tv_order_num);
        tv_customer_name = (TextView) findViewById(R.id.tv_customer_name);
        tc_trade_type = (TextView) findViewById(R.id.tc_trade_type);
        tv_order_time = (TextView) findViewById(R.id.tv_order_time);
        tv_inner_order_num = (TextView) findViewById(R.id.tv_inner_order_num);
        tv_staff_name = (TextView) findViewById(R.id.tv_staff_name);
        ll_staff_name = (LinearLayout) findViewById(R.id.ll_staff_name);
        line_staff_name = (View) findViewById(R.id.line_staff_name);
    }
}
