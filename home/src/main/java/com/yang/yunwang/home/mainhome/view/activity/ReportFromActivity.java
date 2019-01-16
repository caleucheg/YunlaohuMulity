package com.yang.yunwang.home.mainhome.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kw.rxbus.RxBus;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.busevent.LoginOutEvent;
import com.yang.yunwang.base.busevent.MainHomeDialogEvent;
import com.yang.yunwang.base.busevent.ReportFilterEvent;
import com.yang.yunwang.base.busevent.ReportStopRefreshEvent;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.contract.ReportFromContract;
import com.yang.yunwang.home.mainhome.presenter.ReportFromPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ReportFromActivity extends BaseActivity implements ReportFromContract.View {
    private ReportFromContract.Presenter presenter;
//    private View view;
    /**
     * 全部商户
     */
    private TextView et_customer_search;
    /**
     * 999,99,999,999.00
     */
    private TextView tv_center_cash;
    /**
     * 1,000,000,000
     */
    private TextView tv_order_fee;
    /**
     * 1,000,000,000
     */
    private TextView tv_trade_count;
    /**
     * 1,000,000,000|100
     */
    private TextView tv_refund_fee_count;
    /**
     * 1,000,000,000
     */
    private TextView tv_wx_order_fee;
    /**
     * 1,000,000,000
     */
    private TextView tv_wx_cash_fee;
    /**
     * 1,000,000,000
     */
    private TextView tv_wx_order_trade_count;
    /**
     * 1,000000000,000|100
     */
    private TextView tv_wx_refund_fee_count;
    /**
     * 1,000,000,000
     */
    private TextView tv_zfb_order_fee;
    /**
     * 1,000,000,000
     */
    private TextView tv_zfb_cash_fee;
    /**
     * 1,000,000,000
     */
    private TextView tv_zfb_order_trade_count;
    /**
     * 1,0000000000,000|100
     */
    private TextView tv_zfb_refund_fee_count;
    private SegmentTabLayout tab_date;
    private String[] mTitles = {"昨日", "今日", "自定义"};
    /**
     * 2018-12-03 08:00:00 -- 2018-12-03 08:00:00
     */
    private TextView tv_sel_time;
    private boolean isFromArouter = false;
    private Disposable disposable;
    private String cusName;
    private String cusNo;
    private String allItem;
    private String newType;
    private ImageView iv_scale;
    private ScrollView scrollView;
    private boolean isAllCus = true;
    private ReportFilterEvent userEvent;
    private Disposable disposableL;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver br;
    private Disposable disposableR;
    private int position = 1;
    private boolean isStopRefresh = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fargment_report);
        setTitleText("交易报表");
        getLlBasetitleBack().setVisibility(View.INVISIBLE);
        new ReportFromPresenter(this, this);
//        this.view = view;
        initView();
        initListener();
        onHomePageChange();
        initRxBusEvent();

    }

    @Override
    public void setReportInfo(String centerCash, String orderFee, String tradeCount, String refundFeeCount, String centerCashWx, String orderFeeWx, String tradeCountWx, String refundFeeCountWx, String centerCashZfb, String orderFeeZfb, String tradeCountZfb, String refundFeeCountZfb) {
        tv_center_cash.setText(centerCash);
        tv_order_fee.setText(orderFee);
        tv_trade_count.setText(tradeCount);
        tv_refund_fee_count.setText(refundFeeCount);
        tv_wx_order_fee.setText(orderFeeWx);
        tv_wx_cash_fee.setText(centerCashWx);
        tv_wx_order_trade_count.setText(tradeCountWx);
        tv_wx_refund_fee_count.setText(refundFeeCountWx);
        tv_zfb_order_fee.setText(orderFeeZfb);
        tv_zfb_cash_fee.setText(centerCashZfb);
        tv_zfb_order_trade_count.setText(tradeCountZfb);
        tv_zfb_refund_fee_count.setText(refundFeeCountZfb);
        if (TextUtils.equals("--", orderFee)) {
            tv_order_fee.setTextColor(getResources().getColor(R.color.black_color_text));
            tv_trade_count.setTextColor(getResources().getColor(R.color.black_color_text));
            tv_refund_fee_count.setTextColor(getResources().getColor(R.color.black_color_text));
        } else {
            tv_order_fee.setTextColor(getResources().getColor(R.color.blue_color_text));
            tv_trade_count.setTextColor(getResources().getColor(R.color.blue_color_text));
            tv_refund_fee_count.setTextColor(getResources().getColor(R.color.blue_color_text));
        }
        dismissDialog();
    }

    private void initRxBusEvent() {
        disposable = RxBus.getInstance().register(ReportFilterEvent.class,
                AndroidSchedulers.mainThread(),
                new Consumer<ReportFilterEvent>() {
                    @Override
                    public void accept(ReportFilterEvent userEvent) {
                        KLog.i(userEvent);
                        if (userEvent.isTimeFilter()) {
                            position = 2;
                            isStopRefresh = false;
                            tv_sel_time.setVisibility(View.VISIBLE);
                            tv_sel_time.setText(userEvent.getTimeStart() + "~" + userEvent.getTimeEnd());
                            if (!TextUtils.isEmpty(cusNo)) {
                                userEvent.setCusName(cusName);
                                userEvent.setCusNo(cusNo);
                            }
                        } else {
                            tab_date.setCurrentTab(1);
                            tv_sel_time.setVisibility(View.INVISIBLE);
                            if (!userEvent.isAllCus()) {
                                ReportFromActivity.this.cusName = userEvent.getCusName();
                                ReportFromActivity.this.cusNo = userEvent.getCusNo();
                                ReportFromActivity.this.isAllCus = false;
                                et_customer_search.setText(userEvent.getCusName());
                            } else {
                                ReportFromActivity.this.cusNo = ConstantUtils.SYS_NO;
                                ReportFromActivity.this.isAllCus = true;
                                et_customer_search.setText(allItem);
                            }
                        }
                        ReportFromActivity.this.userEvent = userEvent;
                        KLog.i(isAllCus);
                        presenter.filterOrder(userEvent, ConstantUtils.NEW_TYPE, isAllCus, cusNo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(getBaseContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        KLog.i(throwable);
                    }
                });

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
        disposableR = RxBus.getInstance().register(ReportStopRefreshEvent.class,
                AndroidSchedulers.mainThread(),
                new Consumer<ReportStopRefreshEvent>() {
                    @Override
                    public void accept(ReportStopRefreshEvent userEvent) {
                        if (userEvent.isRefreshStop()) {
                            KLog.i(userEvent.isRefreshStop());
//                            isStopRefresh = true;
                            KLog.i(position);
//                            if (position==2){
                            isStopRefresh = false;
//                            }
                            KLog.i(isStopRefresh);
                            tab_date.setCurrentTab(position);

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        KLog.i(throwable.getMessage());
                    }
                });
    }


    private void onHomePageChange() {
        localBroadcastManager = LocalBroadcastManager
                .getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("pageChange");
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int pos = intent.getIntExtra("position", -1);
                KLog.i(pos);
                if (pos == 2) {
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                    tab_date.setCurrentTab(1);
                    tv_sel_time.setVisibility(View.INVISIBLE);
                    if (et_customer_search != null) {
                        cusName = "";
                        cusNo = ConstantUtils.SYS_NO;
                        isAllCus = true;
                        et_customer_search.setText(allItem);
                        if (TextUtils.equals("3", newType)) {
                            et_customer_search.setClickable(false);
                            et_customer_search.setFocusable(false);
                            KLog.i("new---");
                            String cusName = ConstantUtils.CUSTOMER;
                            KLog.i(cusName);
                            et_customer_search.setText(cusName);
                        }
                    }
                    if (!TextUtils.isEmpty(ConstantUtils.NEW_TYPE)) {
                        presenter.initData(ConstantUtils.NEW_TYPE, isAllCus, ConstantUtils.SYS_NO);
                    } else {
                        setReportInfo(
                                "--", "--", "--", "--|--",
                                "--", "--", "--", "--|--",
                                "--", "--", "--", "--|--");
                    }
                }
            }

        };
        localBroadcastManager.registerReceiver(br, intentFilter);
    }

    private void initListener() {
        et_customer_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeIntent.reportNameFilter();
            }
        });
    }

    @Override
    public void initView() {
        tab_date = (SegmentTabLayout) findViewById(R.id.tab_date);
        tab_date.setTabData(mTitles);
        tab_date.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                KLog.i(position);
                if (position != 2) {
                    ReportFromActivity.this.position = position;
                }
                KLog.i(isStopRefresh);
                if (isStopRefresh) {
                    isStopRefresh = false;
                } else {

                    if (position == 0) {
                        presenter.refreshData(userEvent, ConstantUtils.NEW_TYPE, isAllCus, cusNo);
                        isStopRefresh = false;
                    }
                    if (position == 1) {
                        presenter.initData(ConstantUtils.NEW_TYPE, isAllCus, cusNo);
                        isStopRefresh = false;
                    }
                }
                if (position == 2) {
                    HomeIntent.reportTimeFilter();
                    isStopRefresh = false;
                } else {
                    tv_sel_time.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onTabReselect(int position) {
                KLog.i(position);
                if (position != 2) {
                    ReportFromActivity.this.position = position;
                }
                isStopRefresh = false;
                if (position == 2) {
                    HomeIntent.reportTimeFilter();
                } else {
                    tv_sel_time.setVisibility(View.INVISIBLE);
                }


            }
        });
        tab_date.setCurrentTab(1);
        et_customer_search = (TextView) findViewById(R.id.et_customer_search);
        tv_center_cash = (TextView) findViewById(R.id.tv_center_cash);
        tv_order_fee = (TextView) findViewById(R.id.tv_order_fee);
        tv_trade_count = (TextView) findViewById(R.id.tv_trade_count);
        tv_refund_fee_count = (TextView) findViewById(R.id.tv_refund_fee_count);
        tv_wx_order_fee = (TextView) findViewById(R.id.tv_wx_order_fee);
        tv_wx_cash_fee = (TextView) findViewById(R.id.tv_wx_cash_fee);
        tv_wx_order_trade_count = (TextView) findViewById(R.id.tv_wx_order_trade_count);
        tv_wx_refund_fee_count = (TextView) findViewById(R.id.tv_wx_refund_fee_count);
        tv_zfb_order_fee = (TextView) findViewById(R.id.tv_zfb_order_fee);
        tv_zfb_cash_fee = (TextView) findViewById(R.id.tv_zfb_cash_fee);
        tv_zfb_order_trade_count = (TextView) findViewById(R.id.tv_zfb_order_trade_count);
        tv_zfb_refund_fee_count = (TextView) findViewById(R.id.tv_zfb_refund_fee_count);
        tv_sel_time = (TextView) findViewById(R.id.tv_sel_time);
        iv_scale = (ImageView) findViewById(R.id.iv_scale);
        newType = ConstantUtils.NEW_TYPE;
        KLog.i("newType+" + newType);
        if (TextUtils.equals("0", newType) || TextUtils.equals("2", newType)) {
            allItem = "全部商户";
        } else if (TextUtils.equals("1", newType)) {
            allItem = "全部员工";
        } else if (TextUtils.equals("3", newType)) {
            et_customer_search.setClickable(false);
            et_customer_search.setFocusable(false);
            iv_scale.setVisibility(View.INVISIBLE);
        }
        scrollView = (ScrollView) findViewById(R.id.scrollView);
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void showDialog() {
        RxBus.getInstance().send(new MainHomeDialogEvent(true));
    }

    @Override
    public void dismissDialog() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RxBus.getInstance().send(new MainHomeDialogEvent(false));
            }
        }, 200);
    }

    @Override
    public void setPresenter(ReportFromContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isFromArouter) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
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
        RxBus.getInstance().unregister(disposable);
        RxBus.getInstance().unregister(disposableL);
        RxBus.getInstance().unregister(disposableR);
    }
}
