package com.yang.yunwang.home.mainhome.frag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.JPUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.view.activity.NewTestActivity;

import cn.jpush.android.api.JPushInterface;

public class MineFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout rel_password;
    private RelativeLayout rel_userinfo;
    private Button btn_logout;
    private String customer_type;
    private String staff_type;
    private RelativeLayout rel_mine_item_2;
    private RelativeLayout rel_mine_item_2S;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        customer_type = ConstantUtils.CUSTOMERS_TYPE;
        staff_type = ConstantUtils.STAFF_TYPE;
        View view;
        KLog.i("===1====1====1==" + ConstantUtils.STAFF_TYPE);
        if ((customer_type != null && !customer_type.equals("")) || (staff_type != null && !staff_type.equals("") && staff_type.equals("0"))) {
//            服务商与商户角色或服务商员工
            view = inflater.inflate(R.layout.fragment_mine_merchant, null);
            rel_password = (RelativeLayout) view.findViewById(R.id.rel_mine_item_1);
            btn_logout = (Button) view.findViewById(R.id.btn_logout);
            rel_mine_item_2 = (RelativeLayout) view.findViewById(R.id.rel_mine_item_2);
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
            view = inflater.inflate(R.layout.fragment_mine, null);
            KLog.i("===1====1====1" + ConstantUtils.STAFF_TYPE);
            rel_mine_item_2S = (RelativeLayout) view.findViewById(R.id.rel_mine_item_2);
            rel_mine_item_2S.setVisibility(View.VISIBLE);
            rel_password = (RelativeLayout) view.findViewById(R.id.rel_mine_item_1);
            rel_userinfo = (RelativeLayout) view.findViewById(R.id.rel_mine_item_2);
            btn_logout = (Button) view.findViewById(R.id.btn_logout);
            rel_password.setOnClickListener(this);
            rel_userinfo.setOnClickListener(this);
            btn_logout.setOnClickListener(this);

            Button button_test = view.findViewById(R.id.button_test);
            button_test.setVisibility(View.VISIBLE);
            button_test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
//                        KLog.i(AmountUtils.changeF2Y(10000000L));

//                        HomeIntent.reportTimeFilter();

                        Intent intent = new Intent(MineFragment.this.getContext(), NewTestActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return view;
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
            HomeIntent.login();
            CommonShare.clear(this.getContext());
            JPUtils.cleanTags(getContext());
            CommonShare.putJPushIDBoolean(getContext(), false);
            JPushInterface.clearAllNotifications(getContext());
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
            getActivity().finish();

        }
    }
}
