package com.yang.yunwang.query.view.staffcollect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.query.R;

@Route(path = IOrdersProvider.ORDERS_ACT_SSTAFF_COLLECT_INFO)
public class StaffCollectInfoActivity extends BaseActivity implements View.OnClickListener {

    private EditText edit_settlment_name;
    private EditText edit_settlement_money;
    private EditText edit_settlement_cash;
    private EditText edit_settlement_cny;
    private EditText edit_settlement_count;
    //    private ImageView image_back;
//    private ImageView image_home;
//    private RecListInfoPresenterInterface presenter;
    private EditText edit_settlement_count0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_staff_collect_info);
        setTitleText(this.getString(R.string.settlement_title));
        setHomeBarVisisble(true);

        edit_settlment_name = (EditText) findViewById(R.id.edit_settlment_name);
        edit_settlement_money = (EditText) findViewById(R.id.edit_settlement_money);
        edit_settlement_cash = (EditText) findViewById(R.id.edit_settlement_cash);
        edit_settlement_cny = (EditText) findViewById(R.id.edit_settlement_cny);
        edit_settlement_count = (EditText) findViewById(R.id.edit_settlement_count);
        edit_settlement_count0 = (EditText) findViewById(R.id.edit_settlement_count0);
//        image_back = (ImageView) findViewById(R.id.image_back);
//        image_home = (ImageView) findViewById(R.id.image_home);
//        presenter = new SettlementInfoPresenter(this, this);
//        presenter.initData();
        setInfo();
    }


    public void setInfo() {
        edit_settlment_name.setText(loadInstance().getStringExtra("login_Name"));
        edit_settlement_money.setText(loadInstance().getStringExtra("display_Name"));
        edit_settlement_cash.setText(loadInstance().getStringExtra("phone_Number"));
        edit_settlement_cny.setText(loadInstance().getStringExtra("total_fee"));
        edit_settlement_count.setText(loadInstance().getStringExtra("refund_fee"));
        edit_settlement_count0.setText(loadInstance().getStringExtra("fee"));
        getLlBasehomeBack().setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
    }


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
}
