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
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.base.view.adapter.CommonImageTextRecAdapter;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.contract.MerchHomeContract;
import com.yang.yunwang.home.mainhome.presenter.MerchHomePresenter;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MerchantHomeActivity extends BaseActivity implements MerchHomeContract.View {

    private TextView text_total_fee;
    private TextView text_orders_count;
    private TextView text_total_cash;
    private RecyclerView rec_menu;
    private PullToRefreshScrollView pullToRefreshScrollView;

    private MerchHomeContract.Presenter presnter;
    private TextView title_cash_num;
    private TextView right_cash_num;
    private TextView left_cash_num;
    private boolean isFromArouter = false;
    private TextView right_cash_name;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver br;
    private Disposable disposableL;
    private String customer_type;
    private TextView tv_rmb_seb;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_merchanthome);
        setTitleText("翼惠支付");
        getLlBasetitleBack().setVisibility(View.INVISIBLE);
        new MerchHomePresenter(this, this);
        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                ConstantUtils.IS_ATFER_LOGIN_INIT = true;
                if (NetStateUtils.isNetworkConnected(MerchantHomeActivity.this)) {
                    if (presnter != null) {
                        presnter.refreshHomeTopData();
                    } else {
                        new MerchHomePresenter(MerchantHomeActivity.this, MerchantHomeActivity.this);
                        presnter.initData();
                    }
                } else {
                    String customer_type = ConstantUtils.CUSTOMERS_TYPE;
                    setErrorTop(customer_type);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MerchantHomeActivity.this);
                    dialog.setMessage("网络连接异常，请检查您的手机网络");
                    dialog.setPositiveButton(MerchantHomeActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
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
        text_total_fee = (TextView) findViewById(R.id.text_total_fee);
        text_orders_count = (TextView) findViewById(R.id.text_orders_count);
        text_total_cash = (TextView) findViewById(R.id.text_total_cash);
        rec_menu = (RecyclerView) findViewById(R.id.rec_menu);
        title_cash_num = (TextView) findViewById(R.id.title_cash_num);
        left_cash_num = (TextView) findViewById(R.id.left_cash_num);
        right_cash_num = (TextView) findViewById(R.id.right_cash_num);
        right_cash_name = (TextView) findViewById(R.id.right_cash_name);
        tv_rmb_seb = (TextView) findViewById(R.id.tv_rmb_seb);
        customer_type = ConstantUtils.CUSTOMERS_TYPE;
        if (customer_type != null && !customer_type.equals("")) {
            if (TextUtils.equals("0", customer_type)) {
                KLog.i("isser1");
                KLog.i("isser2");
                right_cash_name.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                right_cash_name.getPaint().setAntiAlias(true);//抗锯齿

            } else {
                right_cash_name.setText("订单金额");
                KLog.i("isser4");
            }
        }
        presnter.initData();
        onHomePageChange();


    }

    private void setActiveTop(String customer_type) {
        if (customer_type != null && !customer_type.equals("")) {
            if (TextUtils.equals("0", customer_type)) {
                KLog.i("isser5");
                final String activeNum = right_cash_num.getText().toString();
                if (!TextUtils.equals("--", activeNum) && !TextUtils.equals("0", activeNum)) {
                    KLog.i("isser8");
                    right_cash_name.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                    right_cash_name.getPaint().setAntiAlias(true);//抗锯齿
                    right_cash_name.invalidate();
                } else {
                    right_cash_name.getPaint().setFlags(0);
                    right_cash_name.getPaint().setAntiAlias(true);//抗锯齿
                    right_cash_name.invalidate();
                    KLog.i("isser6");
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
                KLog.i("isser7");
            }
        }
    }

    private void onHomePageChange() {
        localBroadcastManager = LocalBroadcastManager
                .getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        KLog.i("onHomepage");
        intentFilter.addAction("pageChange");
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int pos = intent.getIntExtra("position", -1);
                KLog.i(pos);
                if (pos == 0) {
                    String customer_type = ConstantUtils.CUSTOMERS_TYPE;
                    KLog.i("page-change");
//                    presnter.refreshHomeTopData();
                    if (NetStateUtils.isNetworkConnected(MerchantHomeActivity.this)) {
                        pullToRefreshScrollView.setRefreshing();
                    } else {
                        setErrorTop(customer_type);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MerchantHomeActivity.this);
                        dialog.setMessage("网络连接异常，请检查您的手机网络");
                        dialog.setPositiveButton(MerchantHomeActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
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


    @Override
    public void setMenuAdapter(List<String> menu_list, int[] menu_res, int[] menu_res_selected, List<String> actios, Bundle[] bundles) {
        CommonImageTextRecAdapter commonImageTextRecAdapter = new CommonImageTextRecAdapter(this, menu_list, menu_res, menu_res_selected, actios, bundles, R.layout.rec_menu_item_new);
        rec_menu.setLayoutManager(new GridLayoutManager(this, 4));
        rec_menu.setAdapter(commonImageTextRecAdapter);
    }

    @Override
    public void setInfo(String total_fee, int orders_count, String total_cash) {
        text_total_fee.setText(total_fee);
        text_orders_count.setText("交易笔数：" + orders_count);
        text_total_cash.setText("汇总金额：" + total_cash);
        pullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void setHomeInfo(final String customer_type, String cashFee, String tradeCount, String orderFee) {
        KLog.i("HomeTop");
        if (customer_type != null && !customer_type.equals("")) {
            //  商户/服务商角色
            switch (customer_type) {
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
                if (TextUtils.equals("1", customer_type)) {
                    right_cash_num.setTextColor(getResources().getColor(R.color.black_color_text));
                }
                tv_rmb_seb.setVisibility(View.GONE);
            } else {
                left_cash_num.setTextColor(getResources().getColor(R.color.blue_color_text));
                if (TextUtils.equals("1", customer_type)) {
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
        handler.post(new Runnable() {
            @Override
            public void run() {
                setActiveTop(customer_type);
            }
        });
    }

    @Override
    public void setPresenter(MerchHomeContract.Presenter presenter) {
        this.presnter = presenter;
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
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(disposableL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        KLog.i("onResume");
    }
}
