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
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.bean.ordersearch.OrderSearchRespNew;
import com.yang.yunwang.home.mainhome.contract.NewOrderListContract;
import com.yang.yunwang.home.mainhome.presenter.NewOrderListPresenter;
import com.yang.yunwang.home.mainhome.view.adapter.CommonHomeListRecAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

public class NewOrderFramgent extends Fragment implements NewOrderListContract.View<OrderSearchRespNew>, View.OnClickListener {
    NewOrderListContract.Presenter ordersSearchPresenter;
    private XRecyclerView rec_order_list;
    private CommonHomeListRecAdapter commonListRecAdapter;
    private View view;
    private View imageBack;
    private TextView tv_filter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_orderslist_new, null);
        this.view = view;
        new NewOrderListPresenter(this.getContext(), this);
        onHomePageChange();
        init();
        initListener();
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
                if (pos == 1) {
                    ordersSearchPresenter.initData();
                }
            }

        };
        localBroadcastManager.registerReceiver(br, intentFilter);
    }

    private void init() {
        rec_order_list = (XRecyclerView) view.findViewById(R.id.xrec_order_list);
        imageBack = view.findViewById(R.id.imageView_no_data);
        rec_order_list.setLoadingMoreEnabled(true);
        rec_order_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rec_order_list.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        tv_filter = (TextView) view.findViewById(R.id.tv_filter);
    }

    private void initListener() {

//        getLlBasetitleBack().setOnClickListener(this);
//        getLlBasehomeBack().setOnClickListener(this);
        rec_order_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(NewOrderFramgent.this.getContext());
                KLog.i(isNetConn);
                if (isNetConn) {
                    ordersSearchPresenter.refresh();
                } else {
                    Toast.makeText(NewOrderFramgent.this.getContext(), R.string.refresh_error, Toast.LENGTH_SHORT).show();
                    rec_order_list.refreshComplete();
                }
            }

            @Override
            public void onLoadMore() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(NewOrderFramgent.this.getContext());
                KLog.i(isNetConn);
                if (isNetConn) {
                    ordersSearchPresenter.loadMore();
                } else {
                    Toast.makeText(NewOrderFramgent.this.getContext(), R.string.loadmore_error, Toast.LENGTH_SHORT).show();
                    rec_order_list.loadMoreComplete();
                }
            }
        });
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo jump filter
                HomeIntent.orderFilter();
            }
        });
    }

    @Override
    public void onClick(View view) {
//        int i = view.getId();
//        if (i == R.id.image_home) {
//            HomeIntent.launchHome();
//            this.finish();
//
//        } else if (i == R.id.image_back) {
//            Intent intent = getIntent();
//            String camera_flag = intent.getStringExtra("camera_flag");
//            if (camera_flag != null && camera_flag.equals("camera")) {
//                MyBundle intent_order_search = new MyBundle();
//                String code = intent.getStringExtra("order_code");
//                intent_order_search.put("order_code", code);
//                OrdersIntent.orderSearch(intent_order_search);
//            } else {
//                this.finish();
//            }
//
//        }
    }

    @Override
    public Intent getIntentInstance() {

        return this.getActivity().getIntent();
    }

    @Override
    public void refreshComplete() {
        rec_order_list.refreshComplete();
    }

    @Override
    public void loadMoreComplete() {
        rec_order_list.loadMoreComplete();
    }

    @Override
    public int getRecItmesCount() {
        return rec_order_list.getAdapter().getItemCount();
    }

    @Override
    public void dataNotifyChanged() {
        commonListRecAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAdapter(OrderSearchRespNew bean) {
        commonListRecAdapter = new CommonHomeListRecAdapter(this.getContext(), bean, IOrdersProvider.ORDERS_ACT_ORDER_SEARCH, R.layout.rec_list_item);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commonListRecAdapter.setHasStableIds(true);
        rec_order_list.setItemViewCacheSize(5000);
        rec_order_list.setLayoutManager(linearLayoutManager);
        rec_order_list.setAdapter(commonListRecAdapter);
        rec_order_list.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this.getActivity().getApplicationContext())
                .color(this.getResources().getColor(R.color.divide_gray_color))
                .size(getResources().getDimensionPixelSize(R.dimen.divider_1dp))
                .build());
        commonListRecAdapter.notifyDataSetChanged();
    }

    @Override
    public void noData(boolean noData) {
        if (noData) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public void setPresenter(NewOrderListContract.Presenter presenter) {
        this.ordersSearchPresenter = presenter;
    }
}
