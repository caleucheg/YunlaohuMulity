package com.yang.yunwang.query.view.merch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.PatternUtils;
import com.yang.yunwang.base.view.adapter.MySpinnerAdapter;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.merchinfo.rates.MerchRateResp;
import com.yang.yunwang.query.api.contract.MerchInfoContract;
import com.yang.yunwang.query.api.presenter.MerchInfoPresenter;

import java.util.ArrayList;

@Route(path = IOrdersProvider.ORDERS_ACT_MERCH_INFO)
public class MerchantInfoActivity extends BaseActivity implements MerchInfoContract.View, View.OnClickListener {

    private EditText edit_shop_info_id;
    private EditText edit_shop_info_customer;
    private EditText edit_shop_info_customername;
    private EditText edit_shop_info_tel;
    private EditText edit_shop_info_type;
    private EditText edit_shop_info_time;
    private EditText edit_shop_info_email;
    private EditText edit_shop_info_fax;
    private EditText edit_shop_info_address;
    private EditText edit_shop_info_top;
    private TextView text_controller;
    private MerchInfoContract.Presenter presenter;
    private TextView btn_shop_info_control1;

    private TextView btn_shop_info_control2;
    private boolean fromHome = false;
    private boolean merchStaff = false;
    private TextView btn_shop_info_control3;
    private BottomSheetDialog bottomSheetDialog;
    private View view_actionsheet;
    private boolean alloacte;
    private EditText edit_shop_info_top1;
    //    private EditText edit_shop_info_top102;
    private boolean isFWS;
    private String staff_id;
    private Spinner sp_rate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_merchantinfo);
        setTitleText(this.getString(R.string.shop_info_title));
        setHomeBarVisisble(true);
        init();
        initListener();
    }

    private void init() {
        new MerchInfoPresenter(this, this);
        fromHome = getIntent().getBooleanExtra("from_home", false);
        merchStaff = getIntent().getBooleanExtra(ConstantUtils.merchStaff, false);
        alloacte = getIntent().getBooleanExtra("allocate", false);
        isFWS = getIntent().getBooleanExtra(ConstantUtils.FWS_YUANGONG, false);
        staff_id = getIntent().getStringExtra("staff_id");
        KLog.i("frome_home", fromHome + "-------"+merchStaff+alloacte+isFWS);
        edit_shop_info_id = (EditText) findViewById(R.id.edit_shop_info_id);
        edit_shop_info_customer = (EditText) findViewById(R.id.edit_shop_info_customer);
        edit_shop_info_customername = (EditText) findViewById(R.id.edit_shop_info_customername);
        edit_shop_info_tel = (EditText) findViewById(R.id.edit_shop_info_tel);
        edit_shop_info_type = (EditText) findViewById(R.id.edit_shop_info_type);
        edit_shop_info_time = (EditText) findViewById(R.id.edit_shop_info_time);
        edit_shop_info_email = (EditText) findViewById(R.id.edit_shop_info_email);
        edit_shop_info_fax = (EditText) findViewById(R.id.edit_shop_info_fax);
        edit_shop_info_address = (EditText) findViewById(R.id.edit_shop_info_address);
        edit_shop_info_top = (EditText) findViewById(R.id.edit_shop_info_top);
        text_controller = (TextView) findViewById(R.id.btn_shop_info_control);
        btn_shop_info_control1 = (TextView) findViewById(R.id.btn_shop_info_control1);
        btn_shop_info_control2 = (TextView) findViewById(R.id.btn_shop_info_control2);
        btn_shop_info_control3 = (TextView) findViewById(R.id.btn_shop_info_control3);
        edit_shop_info_top1 = (EditText) findViewById(R.id.edit_shop_info_top1);
//        edit_shop_info_top102 = (EditText) findViewById(R.id.edit_shop_info_top102);
        sp_rate = (Spinner) findViewById(R.id.sp_rate);
        setVis();

        presenter.initData();
    }

    private void setVis() {
        if (ConstantUtils.NEW_TYPE.equals("2")) {
            LinearLayout rel_s = (LinearLayout) findViewById(R.id.linear_unrefund_status_background3);
            rel_s.setVisibility(View.VISIBLE);
        }
        if (alloacte) {
            LinearLayout rel_s = (LinearLayout) findViewById(R.id.linear_unrefund_status_background3);
            rel_s.setVisibility(View.GONE);
        }
        if (fromHome) {
            View view_6 = (View) findViewById(R.id.view_6);
            view_6.setVisibility(View.GONE);
            RelativeLayout rel_shop_info_item_7 = (RelativeLayout) findViewById(R.id.rel_shop_info_item_7);
            rel_shop_info_item_7.setVisibility(View.GONE);
            View view_7 = (View) findViewById(R.id.view_7);
            view_7.setVisibility(View.GONE);
            RelativeLayout rel_shop_info_item_8 = (RelativeLayout) findViewById(R.id.rel_shop_info_item_8);
            rel_shop_info_item_8.setVisibility(View.GONE);
            View view_9 = (View) findViewById(R.id.view_9);
            view_9.setVisibility(View.GONE);
            RelativeLayout rel_shop_info_item_10 = (RelativeLayout) findViewById(R.id.rel_shop_info_item_10);
            rel_shop_info_item_10.setVisibility(View.GONE);
            View view_10 = (View) findViewById(R.id.view_10);
            view_10.setVisibility(View.GONE);
            LinearLayout linear_unrefund_status_background2 = (LinearLayout) findViewById(R.id.linear_unrefund_status_background2);
            linear_unrefund_status_background2.setVisibility(View.GONE);
            LinearLayout linear_unrefund_status_background1 = (LinearLayout) findViewById(R.id.linear_unrefund_status_background1);
            linear_unrefund_status_background1.setVisibility(View.GONE);

            RelativeLayout rel_shop_info_item_101 = (RelativeLayout) findViewById(R.id.rel_shop_info_item_101);
            View view_91 = findViewById(R.id.view_10);
            View view_92 = findViewById(R.id.view_10);
            RelativeLayout rel_shop_info_item_102 = (RelativeLayout) findViewById(R.id.rel_shop_info_item_102);
            rel_shop_info_item_101.setVisibility(View.GONE);
            view_91.setVisibility(View.GONE);
            view_92.setVisibility(View.GONE);
            rel_shop_info_item_102.setVisibility(View.GONE);

            text_controller.setText("商户权限分配");
        }

        if (merchStaff&&!alloacte) {
//            btn_shop_info_control1.setText("上级费率订单");
            LinearLayout linear_unrefund_status_background1 = (LinearLayout) findViewById(R.id.linear_unrefund_status_background1);
            linear_unrefund_status_background1.setVisibility(View.GONE);
            LinearLayout rel_ = (LinearLayout) findViewById(R.id.linear_unrefund_status_background4);
            rel_.setVisibility(View.VISIBLE);
            TextView btn_shop_info_control4 = (TextView) findViewById(R.id.btn_shop_info_control4);
            btn_shop_info_control4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 服务商员工对应商户上级费率
                    MyBundle intent = new MyBundle();//this, CommonSearchActivity.class
                    KLog.i(ConstantUtils.SYS_NO);
                    intent.put("staff_id", ConstantUtils.SYS_NO);
                    intent.put("top_rate", true);
                    intent.put("shop_id", edit_shop_info_customer.getText().toString());
                    intent.put("page_title", "上级费率查询");
                    intent.put("merch_staff", true);
                    intent.put("show_customer", false);
//                    startActivity(intent);
                    OrdersIntent.getCommonSearch(intent);
                }
            });
        }
        if (isFWS) {
            LinearLayout rel_s = (LinearLayout) findViewById(R.id.linear_unrefund_status_background3);
            rel_s.setVisibility(View.GONE);
            LinearLayout rel_ = (LinearLayout) findViewById(R.id.linear_unrefund_status_background4);
            rel_.setVisibility(View.VISIBLE);
            TextView btn_shop_info_control4 = (TextView) findViewById(R.id.btn_shop_info_control4);
            btn_shop_info_control4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 服务商员工对应商户上级费率
                    MyBundle intent = new MyBundle();//MerchantInfoActivity.this, CommonSearchActivity.class
                    intent.put("staff_id", staff_id);
                    intent.put("top_rate", true);
                    intent.put("order_search_customer", edit_shop_info_customer.getText().toString().trim());
//                    startActivity(intent);
                    OrdersIntent.getCommonSearch(intent);
                }
            });
        }
    }

    private void initListener() {
        text_controller.setOnClickListener(this);
        btn_shop_info_control1.setOnClickListener(this);
        btn_shop_info_control2.setOnClickListener(this);
        btn_shop_info_control3.setOnClickListener(this);
        getLlBasehomeBack().setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
    }

    @Override
    public void setInfo(String shop_id, String shop_user, String shop_name, String shop_time, String shop_tel, String shop_email, String shop_fax, String shop_address, String rate, String user_rate) {
        edit_shop_info_id.setText(shop_id);
        edit_shop_info_customer.setText(shop_user);
        edit_shop_info_customername.setText(shop_name);
        edit_shop_info_time.setText(shop_time);
        edit_shop_info_tel.setText(shop_tel);
        edit_shop_info_type.setText("商户");
        edit_shop_info_email.setText(shop_email);
        edit_shop_info_fax.setText(shop_fax);
        edit_shop_info_address.setText(shop_address);
        edit_shop_info_top1.setText(user_rate);
//        edit_shop_info_top102.setText(rate);
//        edit_shop_info_top.setText(ConstantUtils.CUSTOMER);

    }

    @Override
    public Intent loadInstance() {
        return getIntent();
    }

    @Override
    public void setNameData(String disName) {
        edit_shop_info_top.setText(disName);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_home) {
            HomeIntent.launchHome();
            this.finish();

        } else if (i == R.id.image_back) {
            this.finish();

        } else if (i == R.id.btn_shop_info_control) {
            if (fromHome) {
                presenter.initDATAString("CustomerServiceSysNo", ConstantUtils.URL_SHOP_ROLE_LIST, "", ConstantUtils.URL_ROLE_LIST, fromHome);
            } else {
                MyBundle intent;
                intent = new MyBundle();//this, OrdersSearchActivity.class
                intent.put("order_search_customer", edit_shop_info_customer.getText().toString());
                intent.put("SHOP_id", edit_shop_info_id.getText().toString().trim());
//                this.startActivity(intent);
                OrdersIntent.orderSearch(intent);
            }

        } else if (i == R.id.btn_shop_info_control1) {
            KLog.i(TextUtils.isEmpty(ConstantUtils.SYS_NO));
            if (TextUtils.isEmpty(ConstantUtils.SYS_NO)) {
                Toast.makeText(MerchantInfoActivity.this, "查询有误,请退出重试.", Toast.LENGTH_SHORT).show();
            } else {
                if (merchStaff) {
//                    MyBundle intent = new MyBundle();//this, CommonSearchActivity.class
//                    KLog.i(ConstantUtils.SYS_NO);
//                    intent.put("staff_id", ConstantUtils.SYS_NO);
//                    intent.put("top_rate", true);
//                    intent.put("shop_id", edit_shop_info_customer.getText().toString());
//                    intent.put("page_title", "上级费率查询");
//                    intent.put("merch_staff", true);
//                    intent.put("show_customer", false);
////                    startActivity(intent);
//                    OrdersIntent.getCommonSearch(intent);
                } else {
                    String key1 = "CustomerServiceSysNo";
                    String url1 = ConstantUtils.URL_ALLOCATE_UP_COLLECT;
                    String key2 = "CustomerServiceSysNo";
                    String url2 = ConstantUtils.URL_ALLOCATE_ALL_COLLECT;
                    presenter.initDATA(key1, url1, key2, url2, fromHome);
                }
            }

        } else if (i == R.id.btn_shop_info_control2) {
            MyBundle intent2 = new MyBundle();//this, CommonSearchActivity.class
            KLog.i(merchStaff + "--=-=merchStaff");
            if (TextUtils.equals(ConstantUtils.NEW_TYPE, "2")) {
                intent2.put("staff_id", ConstantUtils.SYS_NO);
                intent2.put("SHOP_id", edit_shop_info_id.getText().toString().trim());
            }
            intent2.put(ConstantUtils.merchStaff, merchStaff);
            intent2.put("shop_id", getIntent().getStringExtra("shop_id"));
//            KLog.i(getIntent().getString("shop_id"));
            intent2.put("customer", edit_shop_info_customer.getText().toString().trim());
            intent2.put("page_title", "商户费率查询");
            OrdersIntent.getCommonSearch(intent2);
//            this.startActivity(intent2);

        } else if (i == R.id.btn_shop_info_control3) {
            bottomSheetDialog = new BottomSheetDialog(this);
            view_actionsheet = getLayoutInflater().inflate(R.layout.layout_update_rate_actionsheet, null);
            ImageView image_close = (ImageView) view_actionsheet.findViewById(R.id.image_close);
            final EditText edit_rate = (EditText) view_actionsheet.findViewById(R.id.edit_unrefund_controller_password);
            Button btn_refund = (Button) view_actionsheet.findViewById(R.id.btn_refund);
            btn_refund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String rate = edit_rate.getText().toString().trim();
                    if (PatternUtils.isRateNumber(rate)) {
                        presenter.updateRate(rate);
                    } else {
                        Toast.makeText(MerchantInfoActivity.this, "费率格式不正确", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            image_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetDialog.dismiss();
                }
            });
            bottomSheetDialog.show();
            //此处利用getWindow防止出现键盘遮挡底部对话框的文本输入区域
            bottomSheetDialog.getWindow().setContentView(view_actionsheet);
            bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            bottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
            bottomSheetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        }
    }


    @Override
    public void setRateInfo(ArrayList<MerchRateResp> jsonArray) {
        ArrayList<String> typeName = new ArrayList<String>();
        typeName.addAll(ConstantUtils.WX_TypeName);
        typeName.addAll(ConstantUtils.ZFB_TypeName);
        ArrayList<String> typeNameR = new ArrayList<String>();
        for (int i = 0; i < typeName.size(); i++) {
            typeNameR.add(typeName.get(i) + "费率");
        }
        ArrayList<String> list;
        list = new ArrayList<>();
        list.addAll(ConstantUtils.WX_Type);
        list.addAll(ConstantUtils.ZFB_Type);
        ArrayList<String> rates = new ArrayList<String>();
        rates.addAll(list);
        for (int mi = 0; mi < jsonArray.size(); mi++) {
            MerchRateResp merchRateResp = jsonArray.get(mi);
            String way = merchRateResp.getType() + "";
            rates.set(list.indexOf(way), merchRateResp.getRate() + "");
        }
        MySpinnerAdapter adapter = new MySpinnerAdapter(this, rates, list, typeNameR);
        sp_rate.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void dismissBottomSheetDialog() {
        bottomSheetDialog.dismiss();
    }

    @Override
    public void setPresenter(MerchInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
