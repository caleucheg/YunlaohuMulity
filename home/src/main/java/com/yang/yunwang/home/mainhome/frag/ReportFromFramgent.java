package com.yang.yunwang.home.mainhome.frag;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.socks.library.KLog;
import com.yang.yunwang.base.ui.ClearEditText;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.contract.ReportFromContract;
import com.yang.yunwang.home.mainhome.presenter.ReportFromPresenter;

public class ReportFromFramgent extends Fragment implements ReportFromContract.View {
    private ReportFromContract.Presenter presenter;
    private View view;
    /**
     * 全部商户
     */
    private ClearEditText et_customer_search;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fargment_report, null);
        new ReportFromPresenter(getActivity(), this);
        this.view = view;
        initView();
        initListener();
        onHomePageChange();
        return view;
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
                KLog.i(pos);
                if (pos == 2) {
                    presenter.initData();
                }
            }

        };
        localBroadcastManager.registerReceiver(br, intentFilter);
    }

    private void initListener() {
        et_customer_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void initView() {
        tab_date = (SegmentTabLayout) view.findViewById(R.id.tab_date);
        tab_date.setTabData(mTitles);
        tab_date.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        et_customer_search = (ClearEditText) view.findViewById(R.id.et_customer_search);
        tv_center_cash = (TextView) view.findViewById(R.id.tv_center_cash);
        tv_order_fee = (TextView) view.findViewById(R.id.tv_order_fee);
        tv_trade_count = (TextView) view.findViewById(R.id.tv_trade_count);
        tv_refund_fee_count = (TextView) view.findViewById(R.id.tv_refund_fee_count);
        tv_wx_order_fee = (TextView) view.findViewById(R.id.tv_wx_order_fee);
        tv_wx_cash_fee = (TextView) view.findViewById(R.id.tv_wx_cash_fee);
        tv_wx_order_trade_count = (TextView) view.findViewById(R.id.tv_wx_order_trade_count);
        tv_wx_refund_fee_count = (TextView) view.findViewById(R.id.tv_wx_refund_fee_count);
        tv_zfb_order_fee = (TextView) view.findViewById(R.id.tv_zfb_order_fee);
        tv_zfb_cash_fee = (TextView) view.findViewById(R.id.tv_zfb_cash_fee);
        tv_zfb_order_trade_count = (TextView) view.findViewById(R.id.tv_zfb_order_trade_count);
        tv_zfb_refund_fee_count = (TextView) view.findViewById(R.id.tv_zfb_refund_fee_count);
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void setPresenter(ReportFromContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
