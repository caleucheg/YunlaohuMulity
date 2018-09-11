package com.yang.yunwang.query.view.order.info;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.yang.yunwang.query.api.bean.commonsearch.Model;
import com.yang.yunwang.query.api.contract.CommenListInfoContract;
import com.yang.yunwang.query.api.presenter.CommenListInfoPresenter;

import java.util.ArrayList;

@Route(path = IOrdersProvider.ORDERS_ACT_COMMON_INFO)
public class CommonListInfoActivity extends BaseActivity implements CommenListInfoContract.View {

    private EditText edit_sys_no;
    private EditText edit_customer_name;
    private EditText edit_code;
    private EditText edit_money;
    private EditText edit_time;
    private EditText edit_pay_type;
    private EditText edit_login_name;
    private EditText edit_display_name;
    private RelativeLayout relativeLayout_user;             //登录名显示区域
    private RelativeLayout relativeLayout_user_name;        //真实姓名显示区域
    private View view_user;                                 //登录名底线
    private View view_user_name;                            //真实姓名显示区域
    private CommenListInfoContract.Presenter presenter;
    private EditText edit_rate_fee;
    private EditText edit_rate;
    private EditText edit_money_type;
    private TextView textView10;
    private boolean is_top_rate = false;
    private boolean from_merch_home;
    private boolean isSettlement;
    private Model bean;
    private boolean isRateVis;
    private boolean isChangeStaffMerchTitle;
    private EditText edit_orders_search_info_star_time2a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_info_common);
        setTitleText(this.getString(R.string.rate_title));
        setHomeBarVisisble(true);
        new CommenListInfoPresenter(this, this);
        Intent intent = getIntent();
        isRateVis = intent.getBooleanExtra("isRateVis", true);
        is_top_rate = intent.getBooleanExtra("top_rate", false);
        isChangeStaffMerchTitle = intent.getBooleanExtra("isChangeStaffMerchTitle", false);
        int i = intent.getIntExtra("pos", -1);
        bean = (Model) intent.getSerializableExtra("common_bean");
        isSettlement = getIntent().getBooleanExtra("isSettlement", false);
        if (isSettlement){
            setTitleText(getString(R.string.rate_title_sett));
        }
        from_merch_home = getIntent().getBooleanExtra("from_merch_home", false);

        findViewById(R.id.view_10).setVisibility(View.VISIBLE);
        findViewById(R.id.view_9).setVisibility(View.VISIBLE);
        findViewById(R.id.rel_order_search_item_10).setVisibility(View.VISIBLE);
        findViewById(R.id.rel_order_search_item_11).setVisibility(View.VISIBLE);
        if (!isRateVis) {
            findViewById(R.id.rel_order_search_item_11).setVisibility(View.GONE);
            findViewById(R.id.view_10).setVisibility(View.GONE);
        }
        edit_sys_no = (EditText) findViewById(R.id.edit_orders_search_info_id);
        textView10 = (TextView) findViewById(R.id.textView10);
        edit_customer_name = (EditText) findViewById(R.id.edit_orders_search_info_customer);
        edit_code = (EditText) findViewById(R.id.edit_orders_search_info_code);
        edit_money = (EditText) findViewById(R.id.edit_orders_search_info_money);
        edit_time = (EditText) findViewById(R.id.edit_orders_search_info_star_time);
        edit_pay_type = (EditText) findViewById(R.id.edit_orders_search_info_pay_type);
        edit_login_name = (EditText) findViewById(R.id.edit_orders_search_info_user);
        edit_display_name = (EditText) findViewById(R.id.edit_orders_search_info_user_name);
        relativeLayout_user = (RelativeLayout) findViewById(R.id.rel_order_search_item_3);
        relativeLayout_user_name = (RelativeLayout) findViewById(R.id.rel_order_search_item_4);
        edit_rate = (EditText) findViewById(R.id.edit_orders_search_info_star_time1);
        edit_rate_fee = (EditText) findViewById(R.id.edit_orders_search_info_star_time2);
        edit_money_type = (EditText) findViewById(R.id.edit_orders_search_info_money_type);
        view_user = (View) findViewById(R.id.view_3);
        view_user_name = (View) findViewById(R.id.view_4);
        if (from_merch_home) {
            findViewById(R.id.rel_order_search_item_11).setVisibility(View.GONE);
            findViewById(R.id.view_10).setVisibility(View.GONE);
        }
        getLlBasehomeBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeIntent.launchHome();
            }
        });
        getLlBasetitleBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonListInfoActivity.this.finish();
            }
        });
        is_top_rate = getIntent().getBooleanExtra("top_rate", false);
        String t = ConstantUtils.NEW_TYPE;
        if (is_top_rate) {
            textView10.setText("上级费率");
        }
        if (TextUtils.equals(t, "3")) {
            textView10.setText("员工费率");
        } else if (TextUtils.equals(t, "2")) {
            if (is_top_rate) {
                textView10.setText("上级费率");
            } else {
                textView10.setText("商户费率");
            }
        } else if (TextUtils.equals(t, "1")) {
            if (!from_merch_home) {
                textView10.setText("员工费率");
            } else {
                textView10.setText("商户费率");
            }
        } else if (TextUtils.equals(t, "0")) {
            if (is_top_rate) {
                textView10.setText("上级费率");
            } else {
                textView10.setText("商户费率");
            }
        }
        TextView textView11 = (TextView) findViewById(R.id.textView11);
        if (TextUtils.equals(textView10.getText().toString(), "商户费率")) {
            textView11.setText(getString(R.string.sxf_s));
            findViewById(R.id.rel_order_search_item_11).setVisibility(View.VISIBLE);
            findViewById(R.id.view_10).setVisibility(View.VISIBLE);
        }
        if (isSettlement) {
            KLog.i(bean.toString());
            if (bean.getPayType() != null) {
                KLog.i(bean.getPayType().length() > 0 && !TextUtils.equals(bean.getPayType(), "null"));
                if (bean.getPayType().length() > 0 && !TextUtils.equals(bean.getPayType(), "null")) {
                    findViewById(R.id.view_10a).setVisibility(View.VISIBLE);
                    findViewById(R.id.rel_order_search_item_11a).setVisibility(View.VISIBLE);
                    edit_orders_search_info_star_time2a = (EditText) findViewById(R.id.edit_orders_search_info_star_time2a);
                    ArrayList<String> types = new ArrayList();
                    ArrayList<String> typeNames = new ArrayList();
                    for (PassageWay p : ConstantUtils.PASSAGWE_WAYS) {
                        types.add(p.getType());
                        typeNames.add(p.getTypeName());
                    }
                    int index = types.indexOf(bean.getPayType());
                    if (index > -1) {
                        edit_orders_search_info_star_time2a.setText(typeNames.get(index));
                    }
                }
            }
            ((TextView) findViewById(R.id.textView1)).setText(R.string.orders_search_info_customer);
            if (TextUtils.equals(bean.getDisplayName(), null) || TextUtils.equals(bean.getDisplayName(), "null")) {
                edit_sys_no.setText(bean.getCustomerName());
            } else {
                edit_sys_no.setText(bean.getDisplayName());
            }
            ((TextView) findViewById(R.id.textView2)).setText("交易金额");
//            KLog.i("交易金额" + bean.getMoney_list().get(i));
            Long totalFee = bean.getTotalFee();
            String s = "";
            try {
                s = AmountUtils.changeF2Y(totalFee);
            } catch (Exception e) {
                e.printStackTrace();
            }
            edit_customer_name.setText(s+"元");
            ((TextView) findViewById(R.id.textView5)).setText("实际交易金额");
//            KLog.i("实际交易金额" + bean.getFee_list().get(i));
            Long fee = bean.getFee();
            String text = "";
            try {
                text = AmountUtils.changeF2Y(fee);
            } catch (Exception e) {
                e.printStackTrace();
            }
            edit_code.setText(text+"元");
            ((TextView) findViewById(R.id.textView6)).setText("交易币种");
            edit_pay_type.setText("CNY");
            ((TextView) findViewById(R.id.textView7)).setText("交易笔数");
            edit_money.setText(bean.getTradecount());
            findViewById(R.id.rel_order_search_item_8).setVisibility(View.GONE);
            findViewById(R.id.rel_order_search_item_9).setVisibility(View.GONE);
            findViewById(R.id.view_7).setVisibility(View.GONE);
            findViewById(R.id.view_8).setVisibility(View.GONE);
            if (!is_top_rate) {
                Double rate = bean.getRate();
                edit_rate.setText(rate + "");
                Long rateFee = bean.getRateFee();
                String s1 = null;
                try {
                    s1 = AmountUtils.changeF2Y(rateFee);
                } catch (Exception e) {
                    s1="";
                }
                edit_rate_fee.setText(s1 + "");
            } else {
                Double userRate = bean.getUserRate();
                edit_rate.setText(userRate + "");
                Long userRateFee = bean.getUserRateFee();
                String s1 = null;
                try {
                    s1 = AmountUtils.changeF2Y(userRateFee);
                } catch (Exception e) {
                    s1="";

                }
                edit_rate_fee.setText(s1 + "");
            }

        } else {
            presenter.initData();
        }
        if (isChangeStaffMerchTitle) {
            ((TextView) findViewById(R.id.textView1)).setText("员工名称");
        }

    }

    @Override
    public void setInfo(String code, String customer, String orderNo, String money, String rate, String fee, String pay_type, String money_type, String date, String disName, String loginName) {
        edit_sys_no.setText(code);
        edit_customer_name.setText(customer);
        edit_code.setText(orderNo);
        edit_money.setText(money);
        edit_time.setText(date);
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
//        edit_money_type.setText(money_type);
        edit_money_type.setText("CNY");
        edit_rate.setText(rate);
        edit_rate_fee.setText(fee);
        edit_login_name.setText(loginName);
        edit_display_name.setText(disName);
        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        if (customer_type != null && customer_type.equals("1")) {
            relativeLayout_user.setVisibility(View.VISIBLE);
            relativeLayout_user_name.setVisibility(View.VISIBLE);
            view_user.setVisibility(View.VISIBLE);
            view_user_name.setVisibility(View.VISIBLE);
            edit_login_name.setText(loginName);
            edit_display_name.setText(disName);
        }
    }

    @Override
    public Intent loadInstance() {
        return getIntent();
    }

    @Override
    public void setPresenter(CommenListInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
