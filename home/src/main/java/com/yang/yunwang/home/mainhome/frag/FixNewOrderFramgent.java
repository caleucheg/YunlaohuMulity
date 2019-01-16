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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yang.yunwang.base.view.LoadMoreRecyclerView;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.bean.ordersearch.OrderSearchRespNew;
import com.yang.yunwang.home.mainhome.contract.NewOrderListContract;
import com.yang.yunwang.home.mainhome.presenter.NewOrderListPresenter;
import com.yang.yunwang.home.mainhome.view.adapter.CommonHomeListRecAdapter;

public class FixNewOrderFramgent extends Fragment implements NewOrderListContract.View<OrderSearchRespNew>, View.OnClickListener {
    NewOrderListContract.Presenter ordersSearchPresenter;
    //    private RecyclerView rec_order_list;
    private CommonHomeListRecAdapter commonListRecAdapter;
    private View view;
    private View imageBack;
    private TextView tv_filter;
    private LoadMoreRecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_orderslist_fixnew, container, false);
        this.view = view;
        new NewOrderListPresenter(this.getActivity(), this);
        onHomePageChange();
        init();
        initListener();
//        ordersSearchPresenter.initData();
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
                    KLog.i("page-change");
                    ordersSearchPresenter.initData();
                }
            }

        };
        localBroadcastManager.registerReceiver(br, intentFilter);
    }

    private void init() {
//        rec_order_list = (RecyclerView) view.findViewById(R.id.xrec_order_list);
        imageBack = view.findViewById(R.id.imageView_no_data);
        mRecyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAutoLoadMoreEnable(false);
//        rec_order_list.setLoadingMoreEnabled(true);
//        rec_order_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        rec_order_list.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        tv_filter = (TextView) view.findViewById(R.id.tv_filter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        TextView text_shop_info_title = (TextView) view.findViewById(R.id.text_shop_info_title);
        text_shop_info_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordersSearchPresenter.loadMoreN();
            }
        });
    }

    private void initListener() {
        mRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                ordersSearchPresenter.loadMore();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ordersSearchPresenter.refresh();
            }
        });
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo jump filter
//                HomeIntent.orderFilter();
                ordersSearchPresenter.initData();
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
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMoreComplete(boolean hasMore) {
        KLog.i("data l");
        swipeRefreshLayout.setRefreshing(false);
        mRecyclerView.notifyMoreFinish(hasMore);
    }

    @Override
    public int getRecItmesCount() {
        return mRecyclerView.getAdapter().getItemCount();
    }

    @Override
    public void dataNotifyChanged() {
        KLog.i("data c");

    }

    @Override
    public void setAdapter(OrderSearchRespNew bean) {
        KLog.i("set a");
//        commonListRecAdapter = new CommonHomeListRecAdapter(this.getContext(), bean, IHomeProvider.NEW_ORDER, R.layout.new_rec_order_item);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commonListRecAdapter.setHasStableIds(true);
//        rec_order_list.setItemViewCacheSize(100);
//        rec_order_list.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(commonListRecAdapter);
//        rec_order_list.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this.getActivity().getApplicationContext())
//                .color(this.getResources().getColor(R.color.divide_gray_color))
//                .size(getResources().getDimensionPixelSize(R.dimen.divider_1dp))
//                .build());
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
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void dismissLoadMore() {

    }

    @Override
    public void setPresenter(NewOrderListContract.Presenter presenter) {
        this.ordersSearchPresenter = presenter;
    }
}
