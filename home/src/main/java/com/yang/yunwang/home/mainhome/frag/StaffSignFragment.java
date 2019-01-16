package com.yang.yunwang.home.mainhome.frag;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatSpinner;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.dao.PassageWay;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.DateTimePickDialogUtil;
import com.yang.yunwang.home.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class StaffSignFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
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
    private EditText edit_order_search_customer;        //商户用户名
    private EditText edit_orders_search_customer_name;//商户名称
    private EditText common_editext;
    private AppCompatSpinner spinner_pay_type;
    private String staff_id;    //判断是否有员工列表进入
    private String code;        //判断是否由扫码界面进入
    private View view;
    private ArrayList<String> types;
    private ArrayList<String> typeNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_orderssearch_fragment, null);
        this.view = view;
        init(view);
        initListener();
        return view;
    }

    private void init(View view) {
        edit_order_code = (EditText) view.findViewById(R.id.edit_order_code);
        edit_start_time = (EditText) view.findViewById(R.id.edit_order_start_time);
        edit_end_time = (EditText) view.findViewById(R.id.edit_order_end_time);
        edit_order_search_customer = (EditText) view.findViewById(R.id.edit_order_search_customer);
        edit_orders_search_customer_name = (EditText) view.findViewById(R.id.edit_orders_search_customer_name);
        spinner_pay_type = (AppCompatSpinner) view.findViewById(R.id.spinner_orders_pay_type);
        btn_search = (Button) view.findViewById(R.id.btn_order_search);
        btn_settlement = (Button) view.findViewById(R.id.btn_order_settlement);
//        image_back = (ImageView) findViewById(R.id.image_back);
        image_clear_starttime = (ImageView) view.findViewById(R.id.image_clear_starttime);
        image_clear_endtime = (ImageView) view.findViewById(R.id.image_clear_endtime);
        image_scan = (ImageView) view.findViewById(R.id.image_scan);
//        image_home = (ImageView) findViewById(R.id.image_home);
        types = new ArrayList<String>();
        typeNames = new ArrayList<String>();
        typeNames.add("全部");
        types.add("");
        for (PassageWay p : ConstantUtils.PASSAGWE_WAYS) {
            types.add(p.getType());
            typeNames.add(p.getTypeName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, typeNames);
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

        staff_id = "";
        String customer = "";
        code = "";
        if (customer != null && !customer.equals("")) {
            edit_order_search_customer.setText(customer);
            edit_order_search_customer.setTextColor(this.getResources().getColor(R.color.grey_error));
            edit_order_search_customer.setCursorVisible(false);
            edit_order_search_customer.setFocusable(false);
            edit_order_search_customer.setFocusableInTouchMode(false);
        }
        if (code != null && !code.equals("")) {
            edit_order_code.setText(code);
        }
        String name = ((TextView) view.findViewById(R.id.textView4)).getText().toString();
        if (TextUtils.equals("商户用户名", name)) {
            edit_order_search_customer.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        onHomePageChange();
    }

    private void initListener() {
        edit_start_time.setOnTouchListener(this);
        edit_end_time.setOnTouchListener(this);
        btn_search.setOnClickListener(this);
        btn_settlement.setOnClickListener(this);
        image_clear_starttime.setOnClickListener(this);
        image_clear_endtime.setOnClickListener(this);
        image_scan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.image_scan) {
//            Intent intent_scan = new Intent(getActivity(), MainHomeActivity.class);
            MyBundle intent_scan = new MyBundle();
            intent_scan.put("flag_from", this.getClass().getName());
            KLog.i(this.getClass().getName());
            intent_scan.put("isFrag",true);
            if (edit_start_time.getText().toString().equals("")) {
                intent_scan.put("order_start_time", "");
            } else {
                intent_scan.put("order_start_time", edit_start_time.getText().toString());
            }
            if (edit_end_time.getText().toString().equals("")) {
                intent_scan.put("order_end_time", "");
            } else {
                intent_scan.put("order_end_time", edit_end_time.getText().toString());
            }
            intent_scan.put("order_search_customer", edit_order_search_customer.getText().toString());
            intent_scan.put("orders_search_customer_name", edit_orders_search_customer_name.getText().toString());
            String typePayS = types.get(typeNames.indexOf(spinner_pay_type.getSelectedItem()));
            intent_scan.put("orders_search_pay_type", typePayS);
            if (staff_id != null && !staff_id.equals("")) {
                intent_scan.put("staff_id", staff_id);
            }
            HomeIntent.scanPic(intent_scan);

        } else if (i == R.id.image_clear_starttime) {
            edit_start_time.setText("");
            image_clear_starttime.setVisibility(View.GONE);

        } else if (i == R.id.image_clear_endtime) {
            edit_end_time.setText("");
            image_clear_endtime.setVisibility(View.GONE);

        } else if (i == R.id.btn_order_search) {
//            Intent intent = new Intent(getActivity(), MainHomeActivity.class);
            MyBundle intent = new MyBundle();
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
            intent.put("order_search_customer", edit_order_search_customer.getText().toString());
            intent.put("orders_search_customer_name", edit_orders_search_customer_name.getText().toString());
            String typePay = types.get(typeNames.indexOf(spinner_pay_type.getSelectedItem()));
            intent.put("orders_search_pay_type", typePay);
            if (staff_id != null && !staff_id.equals("")) {
                intent.put("staff_id", staff_id);
            }
            if (ConstantUtils.Order_order_search) {
                HomeIntent.orderLists(intent);
            } else {
                Toast.makeText(getActivity(), "暂无权限", Toast.LENGTH_SHORT).show();
            }

        } else if (i == R.id.btn_order_settlement) {
//            Intent intent_settlement = new Intent(getActivity(), MainHomeActivity.class);
            MyBundle intent_settlement = new MyBundle();
            intent_settlement.put("order_code", edit_order_code.getText().toString());
            if (edit_start_time.getText().toString().equals("")) {
                intent_settlement.put("order_start_time", "");
            } else {
                intent_settlement.put("order_start_time", edit_start_time.getText().toString());
            }
            if (edit_end_time.getText().toString().equals("")) {
                intent_settlement.put("order_end_time", "");
            } else {
                intent_settlement.put("order_end_time", edit_end_time.getText().toString());
            }
            intent_settlement.put("order_search_customer", edit_order_search_customer.getText().toString());
            intent_settlement.put("orders_search_customer_name", edit_orders_search_customer_name.getText().toString());
            String typePaySt = types.get(typeNames.indexOf(spinner_pay_type.getSelectedItem()));
            intent_settlement.put("orders_search_pay_type", typePaySt);
            if (staff_id != null && !staff_id.equals("")) {
                intent_settlement.put("staff_id", staff_id);
            }
            if (ConstantUtils.Order_order_search) {
                HomeIntent.settlementList(intent_settlement);
            } else {
                Toast.makeText(getActivity(), "暂无权限", Toast.LENGTH_SHORT).show();
            }

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
        common_editext = (EditText) view.findViewById(id);
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        //获取日期
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //默认日期选择器
        if (dateTimePicKDialog == null) {
            dateTimePicKDialog = new DateTimePickDialogUtil(
                    getActivity(), common_editext.getText().toString().trim());
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

    private void onHomePageChange() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                .getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("pageChange");
        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int pos = intent.getIntExtra("position", -1);
                if (pos == 1) {
                    if (edit_end_time != null) {
                        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String date = sdf.format(calendar.getTime());
                        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
                        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
                        KLog.i("date--c");
                        edit_start_time.setText(dateStart);
                        edit_end_time.setText(date);
                        image_clear_starttime.setVisibility(View.VISIBLE);
                        image_clear_endtime.setVisibility(View.VISIBLE);
                    }
                }
            }

        };
        localBroadcastManager.registerReceiver(br, intentFilter);
    }

}
