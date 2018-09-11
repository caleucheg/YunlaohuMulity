package com.yang.yunwang.query.view.merch;

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

@Route(path = IOrdersProvider.ORDERS_ACT_MERCH_SEARCH)
public class MerchantSearchActivity extends BaseActivity implements View.OnClickListener {

    private EditText edit_shop_user;
    private EditText edit_shop_name;
    //    private ImageView image_back;
//    private ImageView image_home;
    private Button btn_search;
    private String staff_id = "";
    private boolean fromHome = false;
    private boolean marchStaff = false;
    private boolean allocate;
    private boolean isFWS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_merchantsearch);
        setTitleText(this.getString(R.string.shop_search_title));
        setHomeBarVisisble(true);
        edit_shop_user = (EditText) findViewById(R.id.edit_shop_user);
        edit_shop_name = (EditText) findViewById(R.id.edit_shop_name);
        btn_search = (Button) findViewById(R.id.btn_shop_search);
        Intent intent = getIntent();
        fromHome = intent.getBooleanExtra("from_home", false);
        staff_id = intent.getStringExtra("staff_id");
        allocate = intent.getBooleanExtra("allocate", false);
        isFWS = intent.getBooleanExtra(ConstantUtils.FWS_YUANGONG, false);
        marchStaff = intent.getBooleanExtra(ConstantUtils.merchStaff, false);
        initListener();
    }

    private void initListener() {
        btn_search.setOnClickListener(this);
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

        } else if (i == R.id.btn_shop_search) {
            MyBundle intent = new MyBundle();//this, MerchantListActivity.class
            intent.put("from_home", fromHome);
            intent.put(ConstantUtils.FWS_YUANGONG, isFWS);
            intent.put("shop_user", edit_shop_user.getText().toString());
            intent.put("shop_name", edit_shop_name.getText().toString());
            intent.put("allocate", allocate);
            intent.put(ConstantUtils.merchStaff, marchStaff);
            if (staff_id != null && !staff_id.equals("")) {
                intent.put("staff_id", staff_id);
            }
            OrdersIntent.getMerchList(intent);
        }
    }
}
