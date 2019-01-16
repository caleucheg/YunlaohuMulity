package com.yang.yunwang.home.mainhome.view.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.kw.rxbus.RxBus;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.busevent.LoginOutEvent;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.JPUtils;
import com.yang.yunwang.home.R;

import cn.jpush.android.api.JPushInterface;

public class MineActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rel_password;
    private RelativeLayout rel_userinfo;
    private Button btn_logout;
    private String customer_type;
    private String staff_type;
    private RelativeLayout rel_mine_item_2;
    private RelativeLayout rel_mine_item_2S;
    private boolean isFromArouter = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer_type = ConstantUtils.CUSTOMERS_TYPE;
        staff_type = ConstantUtils.STAFF_TYPE;
        View view;
        KLog.i("===1====1====1==" + ConstantUtils.STAFF_TYPE);
        if ((customer_type != null && !customer_type.equals("")) || (staff_type != null && !staff_type.equals("") && staff_type.equals("0"))) {
//            服务商与商户角色或服务商员工
            setContentView(R.layout.fragment_mine_merchant);

            rel_password = (RelativeLayout) findViewById(R.id.rel_mine_item_1);
            btn_logout = (Button) findViewById(R.id.btn_logout);
            rel_mine_item_2 = (RelativeLayout) findViewById(R.id.rel_mine_item_2);
            if (customer_type != null && customer_type.equals("1")) {
                rel_mine_item_2.setVisibility(View.VISIBLE);
                rel_mine_item_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyBundle bundle = new MyBundle();
                        bundle.put("from_merchant", true);
                        HomeIntent.updateInfo(bundle);
                    }
                });
            }
            rel_password.setOnClickListener(this);
            btn_logout.setOnClickListener(this);
        } else {
//            员工角色
            setContentView(R.layout.fragment_mine);
            KLog.i("===1====1====1" + ConstantUtils.STAFF_TYPE);
            rel_mine_item_2S = (RelativeLayout) findViewById(R.id.rel_mine_item_2);
            rel_mine_item_2S.setVisibility(View.VISIBLE);
            rel_password = (RelativeLayout) findViewById(R.id.rel_mine_item_1);
            rel_userinfo = (RelativeLayout) findViewById(R.id.rel_mine_item_2);
            btn_logout = (Button) findViewById(R.id.btn_logout);
            rel_password.setOnClickListener(this);
            rel_userinfo.setOnClickListener(this);
            btn_logout.setOnClickListener(this);

//            Button button_test = findViewById(R.id.button_test);
//            button_test.setVisibility(View.VISIBLE);
//            button_test.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try {
////                        KLog.i(AmountUtils.changeF2Y(10000000L));
//
////                        HomeIntent.reportTimeFilter();
//
//                        Intent intent = new Intent(MineActivity.this, NewTestActivity.class);
//                        startActivity(intent);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
        }
        setTitleText("我的");
        getLlBasetitleBack().setVisibility(View.INVISIBLE);
    }


    @Override
    public void onResume() {
        super.onResume();
        staff_type = ConstantUtils.STAFF_TYPE;
//        KLog.i(customer_type + "------------------c------");
//        KLog.i(staff_type + "----------------------s--------");
//        KLog.i((customer_type != null && !customer_type.equals("")) + "" + (staff_type != null && !staff_type.equals("") && staff_type.equals("0")));
        if (rel_mine_item_2S != null) {
            rel_mine_item_2S.setVisibility(View.GONE);
        }
        if (rel_mine_item_2 != null) {
            rel_mine_item_2.setVisibility(View.GONE);
        }

        if ((customer_type != null && !customer_type.equals("")) || (staff_type != null && !staff_type.equals("") && staff_type.equals("0"))) {
//            服务商与商户角色或服务商员工
            if (customer_type != null && customer_type.equals("1")) {
                rel_mine_item_2.setVisibility(View.VISIBLE);
            }
        } else {
            if (rel_mine_item_2S != null) {
                rel_mine_item_2S.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.rel_mine_item_1) {
            HomeIntent.passWord();

        } else if (i == R.id.rel_mine_item_2) {
            HomeIntent.updateInfo();
        } else if (i == R.id.btn_logout) {
            RxBus.getInstance().send(new LoginOutEvent(true));
            long lastLoginTime = CommonShare.getLastLoginTime(this);
            long nowTime = System.currentTimeMillis();
            KLog.i(lastLoginTime);
            KLog.i(nowTime);
            int days = (int) ((nowTime - lastLoginTime) / (1000 * 60 * 60 * 24));
            KLog.i(days);
            if (days > 6) {
                CommonShare.clearLogin(this);
            }
            HomeIntent.login();
            CommonShare.clear(this);
            JPUtils.cleanTags(this);
            CommonShare.putJPushIDBoolean(this, false);
            JPushInterface.clearAllNotifications(this);
            ConstantUtils.SYS_NO = "";
            ConstantUtils.HIGHER_SYS_NO = "";
            ConstantUtils.CUSTOMER = "";
            ConstantUtils.CUSTOMERS_TYPE = "";
            ConstantUtils.NEW_TYPE = "-1";
            ConstantUtils.STAFF_TYPE = "";
            ConstantUtils.HIGHER_NAME = "";
            ConstantUtils.IS_ATFER_LOGIN_INIT = false;
            ConstantUtils.INIT_ALLOCATE = false;
            ConstantUtils.NEW_TYPE = "-1";
            ConstantUtils.initAllocate();
            if (rel_mine_item_2S != null) {
                rel_mine_item_2S.setVisibility(View.GONE);
            }
            if (rel_mine_item_2 != null) {
                rel_mine_item_2.setVisibility(View.GONE);
            }
            //TODO Main Finish
            this.finish();

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isFromArouter) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
                dialog.setTitle(this.getResources().getString(R.string.alert_title));
                //Done switch version
                if (DycLibIntent.hasModule()) {
                    dialog.setMessage(this.getResources().getString(R.string.dexit));
                } else {
                    dialog.setMessage(this.getResources().getString(R.string.exit));
                }
                dialog.setPositiveButton(this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
                dialog.setNegativeButton(this.getResources().getString(R.string.alert_native), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
