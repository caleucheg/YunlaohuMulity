package com.yang.yunwang.home.mainhome.frag;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.DateTimePickDialogUtil;
import com.yang.yunwang.home.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StaffRefundFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
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
    private String code = "";        //判断是否由扫码界面进入
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_unrefund_ftagment, null);
        init(view);
        this.view = view;
        initListener();
        return view;
    }


    private void init(View view) {
        edit_unrefund_code = (EditText) view.findViewById(R.id.edit_order_code);
        edit_unrefund_start_time = (EditText) view.findViewById(R.id.edit_unrefund_start_time);
        edit_unrefund_end_time = (EditText) view.findViewById(R.id.edit_unrefund_end_time);
        btn_search = (Button) view.findViewById(R.id.btn_order_search);
//        image_back = (ImageView) findViewById(R.id.image_back);
        image_clear_starttime = (ImageView) view.findViewById(R.id.image_clear_starttime);
        image_clear_endtime = (ImageView) view.findViewById(R.id.image_clear_endtime);
        image_scan = (ImageView) view.findViewById(R.id.image_scan);
//        image_home = (ImageView) findViewById(R.id.image_home);
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        //获取日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(calendar.getTime());
        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        edit_unrefund_start_time.setText(dateStart);
        edit_unrefund_end_time.setText(date);
        onHomePageChange();
    }

    private void initListener() {
        image_clear_starttime.setOnClickListener(this);
        image_clear_endtime.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        edit_unrefund_start_time.setOnTouchListener(this);
        edit_unrefund_end_time.setOnTouchListener(this);
        image_scan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_scan) {
            MyBundle intent_scan=new MyBundle();
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
            HomeIntent.scanPic(intent_scan);

        } else if (i == R.id.image_clear_starttime) {
            edit_unrefund_start_time.setText("");
            image_clear_starttime.setVisibility(View.GONE);

        } else if (i == R.id.image_clear_endtime) {
            edit_unrefund_end_time.setText("");
            image_clear_endtime.setVisibility(View.GONE);

        } else if (i == R.id.btn_order_search) {
//            Intent intent = new Intent(getActivity(), MainHomeActivity.class);
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
            if (ConstantUtils.Refund_refund) {
                HomeIntent.unRefundList(intent);
            } else {
                Toast.makeText(getActivity(), "暂无权限", Toast.LENGTH_SHORT).show();
            }

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
        common_edittext = (EditText) view.findViewById(id);
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        //获取日期
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (dateTimePicKDialog == null) {
            dateTimePicKDialog = new DateTimePickDialogUtil(
                    getActivity(), common_edittext.getText().toString().trim());
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

    private void onHomePageChange() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                .getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("pageChange");
        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int pos = intent.getIntExtra("position", -1);
                if (pos == 2) {
                    if (edit_unrefund_end_time != null) {
                        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
                        //获取日期
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String date = sdf.format(calendar.getTime());
                        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
                        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
                        edit_unrefund_start_time.setText(dateStart);
                        edit_unrefund_end_time.setText(date);
                        image_clear_starttime.setVisibility(View.VISIBLE);
                        image_clear_endtime.setVisibility(View.VISIBLE);
                    }
                }
            }

        };
        localBroadcastManager.registerReceiver(br, intentFilter);
    }

}
