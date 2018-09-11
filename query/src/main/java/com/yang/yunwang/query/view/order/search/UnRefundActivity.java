package com.yang.yunwang.query.view.order.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.moduleinterface.provider.IAppProvider;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.moduleinterface.router.MyRouter;
import com.yang.yunwang.base.util.DateTimePickDialogUtil;
import com.yang.yunwang.query.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@Route(path = IOrdersProvider.ORDERS_ACT_UNREFUND_SEARCH)
public class UnRefundActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    DateTimePickDialogUtil dateTimePicKDialog;
    private Button btn_search;
    //    private ImageView image_back;
    private ImageView image_clear_starttime;
    private ImageView image_clear_endtime;
    private ImageView image_scan;
    //    private ImageView image_home;
    private EditText edit_unrefund_code;                          //订单号
    private EditText edit_unrefund_start_time;                   //交易开始时间
    private EditText edit_unrefund_end_time;                     //交易结束时间
    private EditText common_edittext;
    private String code;        //判断是否由扫码界面进入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_unrefund);
        setTitleText(this.getString(R.string.unrefund_search_title));
        setHomeBarVisisble(true);

        init();
        initListener();
    }

    private void init() {
        edit_unrefund_code = (EditText) findViewById(R.id.edit_order_code);
        edit_unrefund_start_time = (EditText) findViewById(R.id.edit_unrefund_start_time);
        edit_unrefund_end_time = (EditText) findViewById(R.id.edit_unrefund_end_time);
        btn_search = (Button) findViewById(R.id.btn_order_search);
        image_clear_starttime = (ImageView) findViewById(R.id.image_clear_starttime);
        image_clear_endtime = (ImageView) findViewById(R.id.image_clear_endtime);
        image_scan = (ImageView) findViewById(R.id.image_scan);
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        //获取日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(calendar.getTime());
        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        edit_unrefund_start_time.setText(dateStart);
        edit_unrefund_end_time.setText(date);
        Intent intent = getIntent();
        code = intent.getStringExtra("unrefund_code");
        if (code != null && !code.equals("")) {
            edit_unrefund_code.setText(code);
        }
    }

    private void initListener() {
        image_clear_starttime.setOnClickListener(this);
        image_clear_endtime.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        edit_unrefund_start_time.setOnTouchListener(this);
        edit_unrefund_end_time.setOnTouchListener(this);
        image_scan.setOnClickListener(this);
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
//            Intent intent_scan = new Intent(this, MipcaActivityCapture.class);
            MyBundle intent_scan = new MyBundle();
            intent_scan.put("flag_from", this.getClass().getName());
            if (edit_unrefund_start_time.getText().toString().equals("")) {
                intent_scan.put("unrefund_start_time", "");
            } else {
                intent_scan.put("unrefund_start_time", edit_unrefund_start_time.getText().toString());
            }
            if (edit_unrefund_end_time.getText().toString().equals("")) {
                intent_scan.put("unrefund_end_time", "");
            } else {
                intent_scan.put("unrefund_end_time", edit_unrefund_end_time.getText().toString());
            }
            MyRouter.newInstance(IAppProvider.APP_ACT_CAPTURE)
                    .withBundle(intent_scan)
                    .navigation();
//            this.startActivity(intent_scan);

        } else if (i == R.id.image_clear_starttime) {
            edit_unrefund_start_time.setText("");
            image_clear_starttime.setVisibility(View.GONE);

        } else if (i == R.id.image_clear_endtime) {
            edit_unrefund_end_time.setText("");
            image_clear_endtime.setVisibility(View.GONE);

        } else if (i == R.id.image_back) {
            if (code != null && !code.equals("")) {
               HomeIntent.launchHome();
                this.finish();
            } else {
                this.finish();
            }

        } else if (i == R.id.btn_order_search) {
            MyBundle intent=new MyBundle();
            intent.put("unrefund_code", edit_unrefund_code.getText().toString());
            if (edit_unrefund_start_time.getText().toString().equals("")) {
                intent.put("unrefund_start_time", "");
            } else {
                intent.put("unrefund_start_time", edit_unrefund_start_time.getText().toString());
            }
            if (edit_unrefund_end_time.getText().toString().equals("")) {
                intent.put("unrefund_end_time", "");
            } else {
                intent.put("unrefund_end_time", edit_unrefund_end_time.getText().toString());
            }
            OrdersIntent.unRefundList(intent);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int i = view.getId();
        if (i == R.id.edit_unrefund_start_time) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                showDate(R.id.edit_unrefund_start_time);
            }

        } else if (i == R.id.edit_unrefund_end_time) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                showDate(R.id.edit_unrefund_end_time);
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
                    UnRefundActivity.this, common_edittext.getText().toString().trim());
            dateTimePicKDialog.dateTimePicKDialog(common_edittext);
            int i = common_edittext.getId();
            if (i == R.id.edit_unrefund_start_time) {
                image_clear_starttime.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_unrefund_end_time) {
                image_clear_endtime.setVisibility(View.VISIBLE);

            }
        } else {
            dateTimePicKDialog.dateTimePicKDialog(common_edittext);
            int i = common_edittext.getId();
            if (i == R.id.edit_unrefund_start_time) {
                image_clear_starttime.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_unrefund_end_time) {
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
