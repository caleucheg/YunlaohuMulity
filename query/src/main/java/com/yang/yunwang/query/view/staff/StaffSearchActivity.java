package com.yang.yunwang.query.view.staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;

@Route(path = IOrdersProvider.ORDERS_ACT_STAFF_SEARCH)
public class StaffSearchActivity extends BaseActivity implements View.OnClickListener {

    private EditText edit_staff_search_customer;
    private EditText edit_staff_search_tel;
    private Button btn_staff_search;
    //    private ImageView image_back;
//    private ImageView image_home;
    private String flag = "";
    private boolean fromMerch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_staffsearch);
        setTitleText(this.getString(R.string.staff_search_title));
        setHomeBarVisisble(true);

        init();

        initListener();
    }

    private void init() {
        edit_staff_search_customer = (EditText) findViewById(R.id.edit_staff_search_customer);
        edit_staff_search_tel = (EditText) findViewById(R.id.edit_staff_search_tel);
        btn_staff_search = (Button) findViewById(R.id.btn_staff_search);
//        image_back = (ImageView) findViewById(R.id.image_back);
//        image_home = (ImageView) findViewById(R.id.image_home);
        Intent intent = getIntent();
        if (intent != null) {
            fromMerch = intent.getBooleanExtra("from_merch", false);
            flag = intent.getStringExtra("flag");
        }
    }

    private void initListener() {
        btn_staff_search.setOnClickListener(this);
//        image_back.setOnClickListener(this);
//        image_home.setOnClickListener(this);
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

        } else if (i == R.id.btn_staff_search) {
            MyBundle intent = new MyBundle();//this, StaffListActivity.class
            intent.put("staff_customer", edit_staff_search_customer.getText().toString());
            intent.put("staff_tel", edit_staff_search_tel.getText().toString());
            if (flag != null && flag.equals("intent_merchant_qr")) {
                intent.put("flag", flag);
            }
            intent.put(ConstantUtils.fromHomeDis, getIntent().getBooleanExtra(ConstantUtils.fromHomeDis, false));
            intent.put(ConstantUtils.fromMerch, fromMerch);
            intent.put(ConstantUtils.fromHome, getIntent().getBooleanExtra(ConstantUtils.fromHome, false));
//            this.startActivity(intent);
            OrdersIntent.getStaffList(intent);
        }
    }
}
