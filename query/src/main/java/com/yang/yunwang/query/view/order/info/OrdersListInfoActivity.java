package com.yang.yunwang.query.view.order.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.dao.PassageWay;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.contract.OrderListInfoContract;
import com.yang.yunwang.query.api.presenter.OrderListInfoPresenter;

import java.util.ArrayList;

@Route(path = IOrdersProvider.ORDERS_ACT_ORDER_LIST_INFO)
public class OrdersListInfoActivity extends BaseActivity implements OrderListInfoContract.View {

    private EditText edit_sys_no;
    private EditText edit_customer_name;
    private EditText edit_code;
    private EditText edit_money;
    private EditText edit_time;
    private EditText edit_pay_type;
    private EditText edit_login_name;
    private EditText edit_display_name;
    //    private ImageView image_back;
//    private ImageView image_home;
    private RelativeLayout relativeLayout_user;             //登录名显示区域
    private RelativeLayout relativeLayout_user_name;        //真实姓名显示区域
    private View view_user;                                 //登录名底线
    private View view_user_name;                            //真实姓名显示区域
//    private RecListInfoPresenterInterface presenter;
    private EditText edit_money_cash;
    private OrderListInfoContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_info);
        setTitleText(this.getString(R.string.orders_search_info_title));
        new OrderListInfoPresenter(this,this);
        setHomeBarVisisble(true);

        edit_sys_no = (EditText) findViewById(R.id.edit_orders_search_info_id);
        edit_customer_name = (EditText) findViewById(R.id.edit_orders_search_info_customer);
        edit_code = (EditText) findViewById(R.id.edit_orders_search_info_code);
        edit_money = (EditText) findViewById(R.id.edit_orders_search_info_money);
        edit_money_cash = (EditText) findViewById(R.id.edit_orders_search_info_money2);
        edit_time = (EditText) findViewById(R.id.edit_orders_search_info_star_time);
        edit_pay_type = (EditText) findViewById(R.id.edit_orders_search_info_pay_type);
        edit_login_name = (EditText) findViewById(R.id.edit_orders_search_info_user);
        edit_display_name = (EditText) findViewById(R.id.edit_orders_search_info_user_name);
        relativeLayout_user = (RelativeLayout) findViewById(R.id.rel_order_search_item_3);
        relativeLayout_user_name = (RelativeLayout) findViewById(R.id.rel_order_search_item_4);
        view_user = (View) findViewById(R.id.view_3);
        view_user_name = (View) findViewById(R.id.view_4);

        getLlBasehomeBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeIntent.launchHome();
            }
        });
        getLlBasetitleBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrdersListInfoActivity.this.finish();
            }
        });
        presenter.initData();

    }


    public void setInfo(String sys_no, String customer, String code, String pay_type, String money, String date, String loginname, String displayname, String moneyCash) {
        edit_sys_no.setText(sys_no);
        edit_customer_name.setText(customer);
        edit_code.setText(code);
        edit_money.setText(money);
        edit_time.setText(date);
        edit_money_cash.setText(moneyCash);
//        104=兴业微信； 105=兴业支付宝 ； 106=浦发微信；  107=浦发支付宝
        ArrayList<String> types = new ArrayList();
        ArrayList<String> typeNames = new ArrayList();
        for (PassageWay p : ConstantUtils.PASSAGWE_WAYS) {
            types.add(p.getType());
            typeNames.add(p.getTypeName());
        }
        int index = types.indexOf(pay_type);
        if (index > -1) {
            edit_pay_type.setText(typeNames.get(index));
        }
        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        if (customer_type != null && customer_type.equals("1")) {
            relativeLayout_user.setVisibility(View.VISIBLE);
            relativeLayout_user_name.setVisibility(View.VISIBLE);
            view_user.setVisibility(View.VISIBLE);
            view_user_name.setVisibility(View.VISIBLE);
            edit_login_name.setText(loginname);
            edit_display_name.setText(displayname);
        }
    }


    public Intent loadInstance() {
        return getIntent();
    }

    @Override
    public void setPresenter(OrderListInfoContract.Presenter presenter) {
        this.presenter=presenter;
    }
}
