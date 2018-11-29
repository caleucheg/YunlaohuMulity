package com.yang.yunwang.home.mainhome.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.ui.ClearEditText;
import com.yang.yunwang.home.R;

public class ReportNameFilterActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 全部商户
     */
    private TextView tv_all_customer;
    /**
     * 商户查询
     */
    private TextView tv_switch_customer;
    /**
     * 商户登录名
     */
    private ClearEditText et_login_name;
    /**
     * 商户名称
     */
    private ClearEditText et_customer_name;
    /**
     * 查 询
     */
    private Button btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_name_filter);
        initView();
        setTitleText("选择商户");

    }

    private void initView() {
        tv_all_customer = (TextView) findViewById(R.id.tv_all_customer);
        tv_all_customer.setOnClickListener(this);
        tv_switch_customer = (TextView) findViewById(R.id.tv_switch_customer);
        tv_switch_customer.setOnClickListener(this);
        et_login_name = (ClearEditText) findViewById(R.id.et_login_name);
        et_customer_name = (ClearEditText) findViewById(R.id.et_customer_name);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_all_customer) {

        } else if (i == R.id.tv_switch_customer) {

        } else if (i == R.id.btn_search) {

        } else {

        }
    }
}
