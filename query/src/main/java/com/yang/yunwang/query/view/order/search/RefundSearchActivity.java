package com.yang.yunwang.query.view.order.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.dao.PassageWay;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.moduleinterface.provider.IAppProvider;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.moduleinterface.router.MyRouter;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.DateTimePickDialogUtil;
import com.yang.yunwang.query.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

@Route(path = IOrdersProvider.ORDERS_ACT_REFUND_SEARCH)
public class RefundSearchActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    DateTimePickDialogUtil dateTimePicKDialog;
    private Button btn_search;
    //    private ImageView image_back;
    private ImageView image_clear_starttime;
    private ImageView image_clear_endtime;
    private ImageView image_clear_refund_starttime;
    private ImageView image_clear_refund_endtime;
    private ImageView image_scan;
    //    private ImageView image_home;
    private EditText edit_refund_code;                          //订单号
    private EditText edit_order_start_time;                   //订单开始时间
    private EditText edit_order_end_time;                     //订单结束时间
    private EditText edit_refund_start_time;                    //退款开始时间
    private EditText edit_refund_end_time;                      //退款结束时间
    private EditText common_edittext;                           //公共时间输入框
    private String code;//判断是否由扫码界面进入
    private AppCompatSpinner spinner_pay_type;
    private ArrayList<String> types;
    private ArrayList<String> typeNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_refundsearch);
        setTitleText(this.getString(R.string.refund_search_title));
        setHomeBarVisisble(true);
        init();
        initListener();
    }

    private void init() {
        edit_refund_code = (EditText) findViewById(R.id.edit_order_code);
        edit_order_start_time = (EditText) findViewById(R.id.edit_order_start_time);
        edit_order_end_time = (EditText) findViewById(R.id.edit_order_end_time);
        edit_refund_start_time = (EditText) findViewById(R.id.edit_refund_start_time);
        edit_refund_end_time = (EditText) findViewById(R.id.edit_refund_end_time);
        btn_search = (Button) findViewById(R.id.btn_order_search);
//        image_back = (ImageView) findViewById(R.id.image_back);
//        image_home = (ImageView) findViewById(R.id.image_home);
        image_clear_starttime = (ImageView) findViewById(R.id.image_clear_starttime);
        image_clear_endtime = (ImageView) findViewById(R.id.image_clear_endtime);
        image_clear_refund_starttime = (ImageView) findViewById(R.id.image_clear_refund_starttime);
        image_clear_refund_endtime = (ImageView) findViewById(R.id.image_clear_refund_endtime);
        image_scan = (ImageView) findViewById(R.id.image_scan);
        spinner_pay_type = (AppCompatSpinner) findViewById(R.id.spinner_orders_pay_type);
        types = new ArrayList<String>();
        typeNames = new ArrayList<String>();
        typeNames.add("全部");
        types.add("");
        for (PassageWay p : ConstantUtils.PASSAGWE_WAYS) {
            types.add(p.getType());
            typeNames.add(p.getTypeName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, typeNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_pay_type.setAdapter(spinnerAdapter);
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        //获取日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(calendar.getTime());
        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        edit_order_start_time.setText(dateStart);
        edit_order_end_time.setText(date);
        edit_refund_start_time.setText(dateStart);
        edit_refund_end_time.setText(date);
        Intent intent = getIntent();
        code = intent.getStringExtra("refund_code");
        if (code != null && !code.equals("")) {
            edit_refund_code.setText(code);
        }
        if (TextUtils.equals(ConstantUtils.NEW_TYPE,"3")){
           findViewById(R.id.rel_order_search_area_2).setVisibility(View.GONE);
        }
    }

    private void initListener() {
//        image_back.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        edit_order_start_time.setOnTouchListener(this);
        edit_order_end_time.setOnTouchListener(this);
        edit_refund_start_time.setOnTouchListener(this);
        edit_refund_end_time.setOnTouchListener(this);
        image_clear_starttime.setOnClickListener(this);
        image_clear_endtime.setOnClickListener(this);
        image_clear_refund_starttime.setOnClickListener(this);
        image_clear_refund_endtime.setOnClickListener(this);
        image_scan.setOnClickListener(this);
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

        } else if (i == R.id.image_scan) {
            MyBundle intent_scan = new MyBundle();
            intent_scan.put("flag_from", this.getClass().getName());
//            String order_start_time_temp = edit_order_start_time.getText().toString().equals("") ? "" : edit_order_start_time.getText().toString();
//            String order_end_time_temp = edit_order_end_time.getText().toString().equals("") ? "" : edit_order_end_time.getText().toString();
//            intent_scan.put("order_start_time", order_start_time_temp);
//            intent_scan.put("order_end_time", order_end_time_temp);
            intent_scan.put("refund_start_time", edit_refund_start_time.getText().toString().equals("") ? "" : edit_refund_start_time.getText().toString());
            intent_scan.put("refund_end_time", edit_refund_end_time.getText().toString().equals("") ? "" : edit_refund_end_time.getText().toString());
//            this.startActivity(intent_scan);
            MyRouter.newInstance(IAppProvider.APP_ACT_CAPTURE)
                    .withBundle(intent_scan)
                    .navigation();

        } else if (i == R.id.image_clear_starttime) {
            edit_order_start_time.setText("");
            image_clear_starttime.setVisibility(View.GONE);

        } else if (i == R.id.image_clear_endtime) {
            edit_order_end_time.setText("");
            image_clear_endtime.setVisibility(View.GONE);

        } else if (i == R.id.image_clear_refund_starttime) {
            edit_refund_start_time.setText("");
            image_clear_refund_starttime.setVisibility(View.GONE);

        } else if (i == R.id.image_clear_refund_endtime) {
            edit_refund_end_time.setText("");
            image_clear_refund_endtime.setVisibility(View.GONE);

        } else if (i == R.id.image_back) {
            if (code != null && !code.equals("")) {
               HomeIntent.launchHome();
                this.finish();
            } else {
                this.finish();
            }

        } else if (i == R.id.btn_order_search) {
            MyBundle intent = new MyBundle();//this, RefundListActivity.class
            intent.put("refund_code", edit_refund_code.getText().toString());
//            String order_start_time = edit_order_start_time.getText().toString().equals("") ? "" : edit_order_start_time.getText().toString();
//            String order_end_time = edit_order_end_time.getText().toString().equals("") ? "" : edit_order_end_time.getText().toString();
//            intent.put("order_start_time", order_start_time);
//            intent.put("order_end_time", order_end_time);
            String value = edit_refund_start_time.getText().toString().equals("") ? "" : edit_refund_start_time.getText().toString();
            intent.put("refund_start_time", value);
            String value1 = edit_refund_end_time.getText().toString().equals("") ? "" : edit_refund_end_time.getText().toString();
            intent.put("refund_end_time", value1);
            String typePayS = types.get(typeNames.indexOf(spinner_pay_type.getSelectedItem()));
            intent.put("pay_type", typePayS);
//            KLog.i(order_start_time + "------" + order_end_time);
            KLog.i(value + "------" + value1);
            OrdersIntent.refundList(intent);
//            this.startActivity(intent);

        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            int i = view.getId();
            if (i == R.id.edit_order_start_time) {
                showDate(R.id.edit_order_start_time);

            } else if (i == R.id.edit_order_end_time) {
                showDate(R.id.edit_order_end_time);

            } else if (i == R.id.edit_refund_start_time) {
                showDate(R.id.edit_refund_start_time);

            } else if (i == R.id.edit_refund_end_time) {
                showDate(R.id.edit_refund_end_time);

            }
        }
        return true;
    }

    private void showDate(int id) {
        common_edittext = (EditText) findViewById(id);
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        //获取日期
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (dateTimePicKDialog == null) {
            dateTimePicKDialog = new DateTimePickDialogUtil(
                    RefundSearchActivity.this, common_edittext.getText().toString().trim());
            dateTimePicKDialog.dateTimePicKDialog(common_edittext);
            int i = common_edittext.getId();
            if (i == R.id.edit_order_start_time) {
                image_clear_starttime.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_order_end_time) {
                image_clear_endtime.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_refund_start_time) {
                image_clear_refund_starttime.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_refund_end_time) {
                image_clear_refund_endtime.setVisibility(View.VISIBLE);

            }
        } else {
            dateTimePicKDialog.dateTimePicKDialog(common_edittext);
            int i = common_edittext.getId();
            if (i == R.id.edit_order_start_time) {
                image_clear_starttime.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_order_end_time) {
                image_clear_endtime.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_refund_start_time) {
                image_clear_refund_starttime.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_refund_end_time) {
                image_clear_refund_endtime.setVisibility(View.VISIBLE);

            }
        }
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
}
