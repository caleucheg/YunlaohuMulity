package com.yang.yunwang.home.mainhome.view;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.util.DateTimePickDialogUtil;
import com.yang.yunwang.home.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReportTimeFilterActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    /**
     * 请选择时间
     */
    private EditText edit_order_start_time;
    /**
     * 请选择时间
     */
    private EditText edit_order_end_time;
    /**
     * 查 询
     */
    private Button btn_search;
    private DateTimePickDialogUtil dateTimePicKDialog;
    private EditText common_editext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_time_filter);
        initView();
        setTitleText("交易报表");

    }

    private void initView() {
        edit_order_start_time = (EditText) findViewById(R.id.edit_order_start_time);
        edit_order_end_time = (EditText) findViewById(R.id.edit_order_end_time);
        edit_order_start_time.setOnTouchListener(this);
        edit_order_end_time.setOnTouchListener(this);
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        //获取日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(calendar.getTime());
        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        edit_order_start_time.setText(dateStart);
        edit_order_end_time.setText(date);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_search) {

        } else {

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

        if (dateTimePicKDialog == null) {
            dateTimePicKDialog = new DateTimePickDialogUtil(
                    this, common_editext.getText().toString().trim());
            dateTimePicKDialog.dateTimePicKDialog(common_editext);
            int i = common_editext.getId();
            if (i == R.id.edit_order_start_time) {
                edit_order_start_time.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_order_end_time) {
                edit_order_end_time.setVisibility(View.VISIBLE);

            }
        } else {
            dateTimePicKDialog.dateTimePicKDialog(common_editext);
            int i = common_editext.getId();
            if (i == R.id.edit_order_start_time) {
                edit_order_start_time.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_order_end_time) {
                edit_order_end_time.setVisibility(View.VISIBLE);

            }
        }
    }
}
