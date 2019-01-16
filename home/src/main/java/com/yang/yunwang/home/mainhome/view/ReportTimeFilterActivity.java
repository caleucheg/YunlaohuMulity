package com.yang.yunwang.home.mainhome.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kw.rxbus.RxBus;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.busevent.ReportFilterEvent;
import com.yang.yunwang.base.busevent.ReportStopRefreshEvent;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.util.DateTimePickDialogUtil;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.home.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Route(path = IHomeProvider.HOME_ACT_REPORT_TIME_FILTER)
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
    private SimpleDateFormat sdf;
    private long lastClickTime = 0;

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
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(calendar.getTime());
        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
        edit_order_start_time.setText(dateStart);
        edit_order_end_time.setText(dateEnd);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_search) {
            String timeStart = edit_order_start_time.getText().toString().trim();
            String timeEnd = edit_order_end_time.getText().toString().trim();
//            String startTime = edit_order_start_time.getText().toString().trim();
//            String endTime = edit_order_end_time.getText().toString().trim();
            int days = 0;
            boolean erTime = false;
            try {
                Date startDate = sdf.parse(timeStart);
                Date endDate = sdf.parse(timeEnd);
                long dateTime = endDate.getTime();
                long startDateTime = startDate.getTime();
                erTime = startDateTime > dateTime;
                days = (int) ((dateTime - startDateTime) / (1000 * 60 * 60 * 24));
                KLog.i(days);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (erTime) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("起始时间大于结束时间,请重新选择");
                dialog.setPositiveButton(this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            } else if (days > 30) {
//                Toast.makeText(this,"选择区间不能大于31天",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("最多可支持查询31天数据");
                dialog.setPositiveButton(this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            } else {
                if (NetStateUtils.isNetworkConnected(ReportTimeFilterActivity.this)) {
                    ReportFilterEvent event = new ReportFilterEvent(true, "", true, timeStart, timeEnd, "");
                    RxBus.getInstance().send(event);
                    this.finish();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ReportTimeFilterActivity.this);
                    dialog.setMessage("网络连接异常，请检查您的手机网络");
                    dialog.setPositiveButton(ReportTimeFilterActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }

        } else if (i == R.id.image_back) {
            this.finish();
            RxBus.getInstance().send(new ReportStopRefreshEvent(true, "ReportTimeFilterActivity"));
        } else {

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            long nowClickTime = System.currentTimeMillis();
            int i1 = 200;
            KLog.i((nowClickTime - lastClickTime) > i1);
            if ((nowClickTime - lastClickTime) > i1) {
                KLog.i(nowClickTime - lastClickTime);
                lastClickTime = System.currentTimeMillis();
                int i = v.getId();
                if (i == R.id.edit_order_start_time) {
                    showDate(R.id.edit_order_start_time);
                } else if (i == R.id.edit_order_end_time) {
                    showDate(R.id.edit_order_end_time);
                }
            } else {
                KLog.i(nowClickTime - lastClickTime);
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
            dateTimePicKDialog.dateTimePicKDialog(common_editext, common_editext.getText().toString().trim());
            int i = common_editext.getId();
            if (i == R.id.edit_order_start_time) {
//                edit_order_start_time.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_order_end_time) {
//                edit_order_end_time.setVisibility(View.VISIBLE);

            }
        } else {
            dateTimePicKDialog.dateTimePicKDialog(common_editext, common_editext.getText().toString().trim());
            int i = common_editext.getId();
            if (i == R.id.edit_order_start_time) {
//                edit_order_start_time.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_order_end_time) {
//                edit_order_end_time.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            RxBus.getInstance().send(new ReportStopRefreshEvent(true, "ReportTimeFilterActivity"));
        }
        return super.onKeyDown(keyCode, event);
    }
}
