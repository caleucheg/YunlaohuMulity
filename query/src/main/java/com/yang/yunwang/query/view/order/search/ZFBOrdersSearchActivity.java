package com.yang.yunwang.query.view.order.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IAppProvider;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.moduleinterface.router.MyRouter;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.contract.ZFBPlantOrderContract;
import com.yang.yunwang.query.api.presenter.ZFBPlantOrderPresenter;

@Route(path = IOrdersProvider.ORDERS_ACT_ZFB_PLANT_SEARCH)
public class ZFBOrdersSearchActivity extends BaseActivity implements ZFBPlantOrderContract.View, View.OnClickListener {

    private EditText edit_single_code;
    private Button btn_single_search;
    private TextView text_code;
    private TextView text_money;
    private TextView text_time;
    private TextView text_pay_type;
    private TextView text_money_type;
    private TextView text_status;
    private RelativeLayout rel_info_area;
    private ImageView image_scan;
    private String code;        //判断是否由扫码界面进入
    private ZFBPlantOrderContract.Presenter presenter;
    private TextView text_pl_code;
    private EditText edit_single_order_code2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_singleorderssearch);
        setTitleText(this.getString(R.string.zfb_orders_search_title));
        new ZFBPlantOrderPresenter(this, this);
        init();
        initListener();
    }

    private void init() {
        edit_single_code = (EditText) findViewById(R.id.edit_single_order_code);
        btn_single_search = (Button) findViewById(R.id.btn_single_order_search);
        text_code = (TextView) findViewById(R.id.text_single_order_code);
        text_money = (TextView) findViewById(R.id.text_single_order_money);
        text_time = (TextView) findViewById(R.id.text_single_order_time);
        text_pay_type = (TextView) findViewById(R.id.text_single_order_type);
        text_status = (TextView) findViewById(R.id.text_single_order_status);
        text_money_type = (TextView) findViewById(R.id.text_single_order_cny);
        rel_info_area = (RelativeLayout) findViewById(R.id.rel_single_info_area);
        text_pl_code = (TextView) findViewById(R.id.text_single_order_code2);
        edit_single_order_code2 = (EditText) findViewById(R.id.edit_single_order_code2);
        image_scan = (ImageView) findViewById(R.id.image_scan);
        Intent intent = getIntent();
        code = intent.getStringExtra("zfb_code");
        if (code != null && !code.equals("")) {
            edit_single_code.setText(code);
            presenter.requestPlatformInfo(code, false);
        }
    }

    private void initListener() {
        btn_single_search.setOnClickListener(this);
        image_scan.setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_scan) {
            MyBundle intent_scan = new MyBundle();
            intent_scan.put("flag_from", IOrdersProvider.ORDERS_ACT_ZFB_PLANT_SEARCH);
            MyRouter.newInstance(IAppProvider.APP_ACT_CAPTURE)
                    .withBundle(intent_scan)
                    .navigation();
        } else if (i == R.id.image_back) {
            if (code != null && !code.equals("")) {
                HomeIntent.launchHome();
                this.finish();
            } else {
                this.finish();
            }

        } else if (i == R.id.btn_single_order_search) {
            if ((!edit_single_code.getText().toString().equals("")) || (!edit_single_order_code2.getText().toString().equals(""))) {
                boolean orderNoB = edit_single_code.getText().toString().length() > 0;
                boolean plOrderNoB = edit_single_order_code2.getText().toString().length() > 0;
                if (orderNoB && plOrderNoB) {
                    Toast.makeText(this, R.string.select_one, Toast.LENGTH_LONG).show();
                } else {
                    if (orderNoB) {
                        presenter.requestPlatformInfo(edit_single_code.getText().toString().trim(), false);
                    } else if (plOrderNoB) {
                        presenter.requestPlatformInfo(edit_single_order_code2.getText().toString().trim(), true);
                    } else {
                        Toast.makeText(this, this.getResources().getString(R.string.wx_null_code), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, this.getResources().getString(R.string.wx_null_code), Toast.LENGTH_SHORT).show();
            }
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                imm.hideSoftInputFromWindow(edit_single_code.getWindowToken(), 0);
            } else {
                imm.showSoftInput(edit_single_code, InputMethodManager.SHOW_FORCED);
            }

        }
    }

    @Override
    public void showInfo(String code, String pay_type, String money, String money_type, String time, String status, String codePl) {
        if (rel_info_area.getVisibility() != View.VISIBLE) {
            rel_info_area.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_show);
            rel_info_area.startAnimation(animation);
        }
        text_code.setText(code);
        text_pl_code.setText(codePl);
        text_money.setText(money);
        text_time.setText(time);
        text_pay_type.setText(pay_type);
        text_status.setText(status);
        text_money_type.setText(money_type);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    public void clearInfo() {
        if (rel_info_area.getVisibility() != View.GONE) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_hide);
            rel_info_area.startAnimation(animation);
            rel_info_area.setVisibility(View.GONE);
        }
        text_code.setText("");
        text_pl_code.setText("");
        text_money.setText("");
        text_time.setText("");
        text_pay_type.setText("");
        text_status.setText("");
        text_money_type.setText("");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (code != null && !code.equals("")) {
                HomeIntent.launchHome();
                this.finish();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setPresenter(ZFBPlantOrderContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
