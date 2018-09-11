package com.yang.yunwang.query.view.order.list;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.ordersettel.OrderSettleResp;
import com.yang.yunwang.query.api.contract.OrderSettlementContract;
import com.yang.yunwang.query.api.presenter.OrderSettlementPresenter;
import com.yang.yunwang.query.view.adapter.CommonListRecAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

@Route(path = IOrdersProvider.ORDERS_ACT_ORDER_SETTLE)
public class SettlementListActivity extends BaseActivity implements OrderSettlementContract.View, View.OnClickListener {

    private TextView title;
    private XRecyclerView rec_order_list;
    private CommonListRecAdapter commonListRecAdapter;
    //    private ImageView image_back;
//    private ImageView image_home;
    private OrderSettlementContract.Presenter presenter;
    private OrderSettleResp bean;
    private LinearLayoutManager linearLayoutManager;
    private int[] posS;
    private int pageSize=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_orderslist);
        setTitleText(this.getString(R.string.settlement_list_title));
        setHomeBarVisisble(true);

        initUI();
        initListener();
    }

    private void initUI() {
        title = (TextView) findViewById(R.id.text_order_search_title);
//        image_home = (ImageView) findViewById(R.id.image_home);
        title.setText(this.getString(R.string.settlement_list_title));
        rec_order_list = (XRecyclerView) findViewById(R.id.xrec_order_list);
        rec_order_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rec_order_list.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rec_order_list.setLoadingMoreEnabled(true);
//        image_back = (ImageView) findViewById(R.id.image_back);
        new OrderSettlementPresenter(this, this);
        presenter.initData();
    }

    private void initListener() {
//        image_back.setOnClickListener(this);
//        image_home.setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
        getLlBasehomeBack().setOnClickListener(this);
        rec_order_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(SettlementListActivity.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    presenter.refresh();
                } else {
                    Toast.makeText(SettlementListActivity.this, R.string.refresh_error, Toast.LENGTH_SHORT).show();
                    rec_order_list.refreshComplete();
                }
            }

            @Override
            public void onLoadMore() {
                KLog.i("loadmore");
                boolean isNetConn = NetStateUtils.isNetworkConnected(SettlementListActivity.this);
                boolean b = bean.getModel().get(0).getCount() <= pageSize;
                if (b){
                    rec_order_list.loadMoreComplete();
                    Toast.makeText(SettlementListActivity.this, "已经加载全部数据", Toast.LENGTH_SHORT).show();
                }else {
                    if (isNetConn) {
                        presenter.loadMore();
                        posS=getRecyclerViewLastPosition(linearLayoutManager,bean);
                    } else {
                        Toast.makeText(SettlementListActivity.this, R.string.loadmore_error, Toast.LENGTH_SHORT).show();
                        rec_order_list.loadMoreComplete();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_home) {
            HomeIntent.launchHome();
            this.finish();

        } else if (i == R.id.image_back) {
            this.finish();

        }
    }

    @Override
    public void refreshComplete() {
        rec_order_list.refreshComplete();
    }

    @Override
    public void loadMoreComplete(boolean isScroll) {
        if (posS!=null&&posS.length>0){
            if (isScroll){
                linearLayoutManager.scrollToPositionWithOffset(posS[0],posS[1]);
            }
        }
        rec_order_list.loadMoreComplete();
    }

    @Override
    public void dataNotifyChanged() {
        commonListRecAdapter.notifyDataSetChanged();
    }

    @Override
    public int getRecItmesCount() {
        return rec_order_list.getAdapter().getItemCount();
    }

    @Override
    public Intent getIntentInstance() {
        return getIntent();
    }

    @Override
    public void setAdapter(OrderSettleResp bean, String staff_id) {
        commonListRecAdapter = new CommonListRecAdapter(this, bean, staff_id, IOrdersProvider.ORDERS_ACT_ORDER_SETTLE, R.layout.layout_settlment_list_item);
         linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.bean=bean;
        commonListRecAdapter.setHasStableIds(true);
        rec_order_list.setItemViewCacheSize(5000);
        rec_order_list.setLayoutManager(linearLayoutManager);
        rec_order_list.setAdapter(commonListRecAdapter);
        rec_order_list.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(this.getResources().getColor(R.color.divide_gray_color))
                .size(getResources().getDimensionPixelSize(R.dimen.divider_1dp))
                .build());
        commonListRecAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(OrderSettlementContract.Presenter presenter) {
        this.presenter=presenter;
    }

    private int[] getRecyclerViewLastPosition(LinearLayoutManager layoutManager,OrderSettleResp bean) {
        int[] pos = new int[2];
        pos[0] = layoutManager.findFirstCompletelyVisibleItemPosition();
        OrientationHelper orientationHelper = OrientationHelper.createOrientationHelper(layoutManager, OrientationHelper.VERTICAL);
        int fromIndex = 0;
        int toIndex = bean.getModel().size();
        final int start = orientationHelper.getStartAfterPadding();
        final int end = orientationHelper.getEndAfterPadding();
        final int next = toIndex > fromIndex? 1 : -1;
        for (int i = fromIndex; i != toIndex; i += next) {
            final View child = rec_order_list.getChildAt(i);
            final int childStart = orientationHelper.getDecoratedStart(child);
            final int childEnd = orientationHelper.getDecoratedEnd(child);
            if (childStart < end && childEnd > start) {
                if (childStart >= start && childEnd <= end) {
                    pos[1] = childStart;
                    KLog.i("position = " + pos[0] + " off = " + pos[1]);
                    return pos;
                }
            }
        }
        return pos;
    }
}
