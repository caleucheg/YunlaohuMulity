package com.yang.yunwang.query.view.staffcollect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.dao.PassageWay;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.DateTimePickDialogUtil;
import com.yang.yunwang.query.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

@Route(path = IOrdersProvider.ORDERS_ACT_SSTAFF_COLLECT_SEARCH)
public class StaffCollectSearchActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    DateTimePickDialogUtil dateTimePicKDialog;
    private Button btn_search;
    private Button btn_settlement;
    //    private ImageView image_back;
    private ImageView image_clear_starttime;
    private ImageView image_clear_endtime;
    private ImageView image_scan;
    //    private ImageView image_home;
    private EditText edit_order_code;               //订单号
    private EditText edit_start_time;                //开始时间
    private EditText edit_end_time;                     //结束时间
    private EditText edit_staff_loginname;        //商户用户名
    private EditText edit_staff_real_name;//商户名称
    private EditText common_editext;
    private AppCompatSpinner spinner_pay_type;
    private String staff_id;    //判断是否有员工列表进入
    private String code;        //判断是否由扫码界面进入
    private EditText edit_staff_phone;
    private ArrayList<String> types;
    private ArrayList<String> typeNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_staff_collect_search);

        setTitleText(this.getString(R.string.staff_collect_list_title));
        setHomeBarVisisble(true);
        init();
        initListener();
    }

    private void init() {
        edit_order_code = (EditText) findViewById(R.id.edit_order_code);
        edit_start_time = (EditText) findViewById(R.id.edit_order_start_time);
        edit_end_time = (EditText) findViewById(R.id.edit_order_end_time);
        edit_staff_loginname = (EditText) findViewById(R.id.edit_order_search_customer);
        edit_staff_real_name = (EditText) findViewById(R.id.edit_orders_search_customer_name);
        edit_staff_phone = (EditText) findViewById(R.id.edit_orders_search_customer_name1);
        spinner_pay_type = (AppCompatSpinner) findViewById(R.id.spinner_orders_pay_type);
        btn_search = (Button) findViewById(R.id.btn_order_search);
        btn_settlement = (Button) findViewById(R.id.btn_order_settlement);
//        image_back = (ImageView) findViewById(R.id.image_back);
        image_clear_starttime = (ImageView) findViewById(R.id.image_clear_starttime);
        image_clear_endtime = (ImageView) findViewById(R.id.image_clear_endtime);
        image_scan = (ImageView) findViewById(R.id.image_scan);
//        image_home = (ImageView) findViewById(R.id.image_home);
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
        edit_start_time.setText(dateStart);
        edit_end_time.setText(date);
        Intent intent = getIntent();
        staff_id = intent.getStringExtra("staff_id");
        String customer = intent.getStringExtra("shop_info_customer");
        code = intent.getStringExtra("order_code");
        if (customer != null && !customer.equals("")) {
            edit_staff_loginname.setText(customer);
            edit_staff_loginname.setTextColor(this.getResources().getColor(R.color.grey_error));
            edit_staff_loginname.setCursorVisible(false);
            edit_staff_loginname.setFocusable(false);
            edit_staff_loginname.setFocusableInTouchMode(false);
        }
        if (code != null && !code.equals("")) {
            edit_order_code.setText(code);
        }
    }

    private void initListener() {
        edit_start_time.setOnTouchListener(this);
        edit_end_time.setOnTouchListener(this);
        btn_search.setOnClickListener(this);
//        image_back.setOnClickListener(this);
        btn_settlement.setOnClickListener(this);
        image_clear_starttime.setOnClickListener(this);
        image_clear_endtime.setOnClickListener(this);
//        image_clear_code.setOnClickListener(this);
        image_scan.setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
        getLlBasehomeBack().setOnClickListener(this);
//        image_home.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.image_home) {
            HomeIntent.launchHome();
            this.finish();

        } else if (i == R.id.image_clear_starttime) {
            edit_start_time.setText("");
            image_clear_starttime.setVisibility(View.GONE);

        } else if (i == R.id.image_clear_endtime) {
            edit_end_time.setText("");
            image_clear_endtime.setVisibility(View.GONE);

        } else if (i == R.id.image_back) {
            if (code != null && !code.equals("")) {
                HomeIntent.launchHome();
                this.finish();
            } else {
                this.finish();
            }

        } else if (i == R.id.btn_order_search) {
            MyBundle intent = new MyBundle();//this, StaffColletListActivity.class
            intent.put("order_code", edit_order_code.getText().toString());
            if (edit_start_time.getText().toString().equals("")) {
                intent.put("order_start_time", "");
            } else {
                intent.put("order_start_time", edit_start_time.getText().toString());
            }
            if (edit_end_time.getText().toString().equals("")) {
                intent.put("order_end_time", "");
            } else {
                intent.put("order_end_time", edit_end_time.getText().toString());
            }
            intent.put("order_search_customer", edit_staff_loginname.getText().toString());
            intent.put("orders_search_customer_name", edit_staff_real_name.getText().toString());
            intent.put("edit_staff_phone", edit_staff_phone.getText().toString());
            String typePayS = types.get(typeNames.indexOf(spinner_pay_type.getSelectedItem()));
            intent.put("orders_search_pay_type", typePayS);
            if (staff_id != null && !staff_id.equals("")) {
                intent.put("staff_id", staff_id);
            }
            OrdersIntent.getSStafCollectList(intent);
//            this.startActivity(intent);

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int i = v.getId();
            if (i == R.id.edit_order_start_time) {
                showDate(R.id.edit_order_start_time);

            } else if (i == R.id.edit_order_end_time) {
                showDate(R.id.edit_order_end_time);

            }
        }
        return true;
    }

    private void showDate(int id) {
        common_editext = (EditText) findViewById(id);
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        //获取日期
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //默认日期选择器
        if (dateTimePicKDialog == null) {
            dateTimePicKDialog = new DateTimePickDialogUtil(
                    StaffCollectSearchActivity.this, common_editext.getText().toString().trim());
            dateTimePicKDialog.dateTimePicKDialog(common_editext, common_editext.getText().toString().trim());
            int i = common_editext.getId();
            if (i == R.id.edit_order_start_time) {
                image_clear_starttime.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_order_end_time) {
                image_clear_endtime.setVisibility(View.VISIBLE);

            }
        } else {
            dateTimePicKDialog.dateTimePicKDialog(common_editext, common_editext.getText().toString().trim());
            int i = common_editext.getId();
            if (i == R.id.edit_order_start_time) {
                image_clear_starttime.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_order_end_time) {
                image_clear_endtime.setVisibility(View.VISIBLE);

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
