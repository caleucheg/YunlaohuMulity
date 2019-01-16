package com.yang.yunwang.home.mainhome.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.kw.rxbus.RxBus;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.busevent.LoginOutEvent;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.base.view.adapter.CommonImageTextRecAdapter;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.contract.StaffHomeContract;
import com.yang.yunwang.home.mainhome.presenter.StaffHomePresenter;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class StaffHomeActivity extends BaseActivity implements StaffHomeContract.View, View.OnClickListener {

    private RecyclerView recyclerView_main;
    private RecyclerView recyclerView_sub;
    private TextView text_top_name;
    private TextView title;
    private StaffHomeContract.Presenter staffHomePresenter;
    private ImageView image_staff_portrait;
    private boolean isFromArouter = false;
    private TextView title_cash_num;
    private TextView left_cash_num;
    private TextView right_cash_num;
    private TextView right_cash_name;
    private Disposable disposableL;
    private String customer_type;
    private PullToRefreshScrollView pullToRefreshScrollView;
    private TextView tv_rmb_seb;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        getLlBasetitleBack().setVisibility(View.INVISIBLE);
        ConstantUtils.HIGHER_SYS_NO = CommonShare.getHomeData(this, "HIGHER_SYS_NO", "");
        ConstantUtils.STAFF_TYPE = CommonShare.getHomeData(this, "STAFF_TYPE", "");
        ConstantUtils.HIGHER_NAME = CommonShare.getHomeData(this, "HIGHER_NAME", "");
        setTitleText("翼惠支付");
        recyclerView_main = (RecyclerView) findViewById(R.id.recycle_staff_main);
        recyclerView_sub = (RecyclerView) findViewById(R.id.recycle_staff_sub);
        image_staff_portrait = (ImageView) findViewById(R.id.image_staff_portrait);
        title = (TextView) findViewById(R.id.text_staff_customer);
        text_top_name = (TextView) findViewById(R.id.text_staff_top_name);
        title_cash_num = (TextView) findViewById(R.id.title_cash_num);
        left_cash_num = (TextView) findViewById(R.id.left_cash_num);
        right_cash_num = (TextView) findViewById(R.id.right_cash_num);
        right_cash_name = (TextView) findViewById(R.id.right_cash_name);
        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        tv_rmb_seb = (TextView) findViewById(R.id.tv_rmb_seb);
        customer_type = ConstantUtils.NEW_TYPE;
        if (customer_type != null && !customer_type.equals("")) {
            KLog.i("customer_type1==" + customer_type);
            if (TextUtils.equals("2", customer_type)) {
                KLog.i("customer_type2==" + customer_type);
                KLog.i("line----2");
//                right_cash_name.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
//                right_cash_name.getPaint().setAntiAlias(true);//抗锯齿

            } else {
                right_cash_name.setText("订单金额");
                KLog.i("customer_type3+" + customer_type);
            }
        } else {
            KLog.i("customer_type4+" + customer_type);
        }
        initData();
        initListener();
        onHomePageChange();
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (NetStateUtils.isNetworkConnected(StaffHomeActivity.this)) {
                    ConstantUtils.IS_ATFER_LOGIN_INIT = true;
                    if (staffHomePresenter != null) {
                        staffHomePresenter.initHomePage();
                    } else {
                        new StaffHomePresenter(StaffHomeActivity.this, StaffHomeActivity.this);
                        //装配数据
                        staffHomePresenter.initData(ConstantUtils.SYS_NO, ConstantUtils.CUSTOMER);
                    }
                } else {
                    setErrorTop(ConstantUtils.STAFF_TYPE);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(StaffHomeActivity.this);
                    dialog.setMessage("网络连接异常，请检查您的手机网络");
                    dialog.setPositiveButton(StaffHomeActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    pullToRefreshScrollView.onRefreshComplete();
                }

            }
        });
    }

    private void getActiveTop() {
        if (customer_type != null && !customer_type.equals("")) {
            if (TextUtils.equals("2", customer_type)) {
                final String activeNum = right_cash_num.getText().toString();
                if (!TextUtils.equals("--", activeNum) && !TextUtils.equals("0", activeNum)) {
                    KLog.i("line----3");
                    right_cash_name.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                    right_cash_name.getPaint().setAntiAlias(true);//抗锯齿
                    right_cash_name.invalidate();
                } else {
                    KLog.i("line----");
                    right_cash_name.getPaint().setFlags(0);
                    right_cash_name.getPaint().setAntiAlias(true);//抗锯齿
                    right_cash_name.invalidate();
                }
                LinearLayout ll_active_cus = (LinearLayout) findViewById(R.id.ll_active_cus);
                ll_active_cus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.equals("--", activeNum) && !TextUtils.equals("0", activeNum)) {
                            MyBundle intent_order_search = new MyBundle();
                            intent_order_search.put("isActive", true);
                            OrdersIntent.commonCusCardList(intent_order_search);
                        }
                    }
                });
            } else {
                right_cash_name.setText("订单金额");
            }
        }
    }

    private void onHomePageChange() {
        final LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                .getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        KLog.i("onHomepage");
        intentFilter.addAction("pageChange");
        final BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int pos = intent.getIntExtra("position", -1);
                KLog.i(pos);
                if (pos == 0) {
                    KLog.i("page-change");
//                    staffHomePresenter.initHomePage();
                    if (NetStateUtils.isNetworkConnected(StaffHomeActivity.this)) {
                        pullToRefreshScrollView.setRefreshing();
                    } else {
                        setErrorTop(ConstantUtils.STAFF_TYPE);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(StaffHomeActivity.this);
                        dialog.setMessage("网络连接异常，请检查您的手机网络");
                        dialog.setPositiveButton(StaffHomeActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                }
            }

        };
        localBroadcastManager.registerReceiver(br, intentFilter);
        disposableL = RxBus.getInstance().register(LoginOutEvent.class, Schedulers.newThread(),
                new Consumer<LoginOutEvent>() {
                    @Override
                    public void accept(LoginOutEvent userEvent) {
                        if (userEvent.isLogunOut()) {
                            KLog.i(userEvent.isLogunOut());
                            if (localBroadcastManager != null && br != null) {
                                localBroadcastManager.unregisterReceiver(br);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        KLog.i(throwable.getMessage());
                    }
                });
    }

    private void setErrorTop(String customer_type) {
        setHomeInfo(customer_type, "--", "--", "--");
        if (customer_type != null && !customer_type.equals("")) {
            //  商户/服务商角色
            if (TextUtils.equals("0", customer_type)) {
                setHomeActiveCus("--");
            }
        }
    }

    private void initData() {
        new StaffHomePresenter(this, this);
        //装配数据
        staffHomePresenter.initData(ConstantUtils.SYS_NO, ConstantUtils.CUSTOMER);
    }

    private void initListener() {
        image_staff_portrait.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
//        MainHomeActivity activity = (MainHomeActivity) getActivity();
//        activity.parseMineFragment();
    }

    @Override
    public void setPresenter(StaffHomeContract.Presenter presenter) {
        staffHomePresenter = presenter;
    }

    @Override
    public void setMainAdapter(List<String> main_list, int[] main_res, int[] main_res_selected, List<String> actios, Bundle[] bundles) {
        CommonImageTextRecAdapter commonImageTextRecAdapter = new CommonImageTextRecAdapter(this, main_list, main_res, main_res_selected, actios, bundles, R.layout.main_item);
        recyclerView_main.setLayoutManager(new GridLayoutManager(this, main_list.size()));
        recyclerView_main.setAdapter(commonImageTextRecAdapter);
    }

    @Override
    public void setSubAdapter(List<String> sub_list, int[] sub_res, int[] sub_res_selected, List<String> actios, Bundle[] bundles) {
        CommonImageTextRecAdapter commonImageTextRecAdapter = new CommonImageTextRecAdapter(this, sub_list, sub_res, sub_res_selected, actios, bundles, R.layout.rec_menu_item_new);
        recyclerView_sub.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView_sub.setAdapter(commonImageTextRecAdapter);
//        recyclerView_sub.addItemDecoration(new DividerGridItemDecoration(this));
    }


    @Override
    public void setHeaderTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setTopName(String top_name) {
        this.text_top_name.setText(top_name);
    }

    @Override
    public void setHomeInfo(String cusType, String cashFee, String tradeCount, String orderFee) {
        KLog.i("HomeTop");
        if (cusType != null && !cusType.equals("")) {
            //  商户/服务商角色
            switch (cusType) {
                case "0":
                    title_cash_num.setText(cashFee);
                    left_cash_num.setText(tradeCount);

                    break;
                case "1":
                    title_cash_num.setText(cashFee);
                    left_cash_num.setText(tradeCount);
                    if (TextUtils.equals("--", orderFee)) {
                        right_cash_num.setText(orderFee);
                    } else {
                        right_cash_num.setText("¥" + orderFee);
                    }

                    break;
            }
            if (TextUtils.equals(tradeCount, "--")) {
                left_cash_num.setTextColor(getResources().getColor(R.color.black_color_text));
                if (TextUtils.equals("1", cusType)) {
                    right_cash_num.setTextColor(getResources().getColor(R.color.black_color_text));
                }
                tv_rmb_seb.setVisibility(View.GONE);
            } else {
                left_cash_num.setTextColor(getResources().getColor(R.color.blue_color_text));
                if (TextUtils.equals("1", cusType)) {
                    right_cash_num.setTextColor(getResources().getColor(R.color.blue_color_text));
                }
                tv_rmb_seb.setVisibility(View.VISIBLE);
            }

        }
        pullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void setHomeActiveCus(String activeCus) {
        right_cash_num.setText(activeCus);
        if (TextUtils.equals(activeCus, "--")) {
            right_cash_num.setTextColor(getResources().getColor(R.color.black_color_text));
        } else {
            right_cash_num.setTextColor(getResources().getColor(R.color.blue_color_text));
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getActiveTop();
            }
        }, 100);
        pullToRefreshScrollView.onRefreshComplete();
    }


    public void requestOnNetworkTimeLong() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(this.getResources().getString(R.string.alert_title));
        dialog.setMessage(this.getResources().getString(R.string.request_too_long));
        dialog.setPositiveButton(this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
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

    @Override
    protected void onResume() {
        super.onResume();
        KLog.i("onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(disposableL);
    }
}
