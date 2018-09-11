package com.yang.yunwang.query.view.order.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.commondaycollect.CommonDayCollectResp;
import com.yang.yunwang.query.api.contract.CommonDayCollectContract;
import com.yang.yunwang.query.api.presenter.CommonDayCollectPresenter;
import com.yang.yunwang.query.view.adapter.CommonListRecAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

@Route(path = IOrdersProvider.ORDERS_ACT_DAY_COMMON_LIST)
public class CommonDayCollectList extends BaseActivity implements CommonDayCollectContract.View<CommonDayCollectResp>, View.OnClickListener {
    private CommonDayCollectContract.Presenter ordersSearchPresenter;
    private XRecyclerView rec_order_list;
    private CommonListRecAdapter commonListRecAdapter;
    private boolean dayCollect;
    private int[] posS;
    private int pageSize=20;
    private CommonDayCollectResp bean;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_orderslist);
        setTitleText("日交易汇总");
        setHomeBarVisisble(true);
        init();
        initListener();
    }

    private void init() {
        new CommonDayCollectPresenter(this, this);
        ordersSearchPresenter.initData();
        rec_order_list = (XRecyclerView) findViewById(R.id.xrec_order_list);
        rec_order_list.setLoadingMoreEnabled(true);
        rec_order_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rec_order_list.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        dayCollect = getIntent().getBooleanExtra(ConstantUtils.DAY_COLLECT, false);

    }

    private void initListener() {

        getLlBasetitleBack().setOnClickListener(this);
        getLlBasehomeBack().setOnClickListener(this);
        rec_order_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(CommonDayCollectList.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    ordersSearchPresenter.refresh();
                } else {
                    Toast.makeText(CommonDayCollectList.this, R.string.refresh_error, Toast.LENGTH_SHORT).show();
                    rec_order_list.refreshComplete();
                }
            }

            @Override
            public void onLoadMore() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(CommonDayCollectList.this);
                KLog.i(isNetConn);
                boolean b = bean.getModel().get(0).getCount() <= pageSize;
                if (b){
                    rec_order_list.loadMoreComplete();
                    Toast.makeText(CommonDayCollectList.this, "已经加载全部数据", Toast.LENGTH_SHORT).show();
                }else {
                    if (isNetConn) {
                        ordersSearchPresenter.loadMore();
                        posS=getRecyclerViewLastPosition(linearLayoutManager,bean);
                    } else {
                        Toast.makeText(CommonDayCollectList.this, R.string.loadmore_error, Toast.LENGTH_SHORT).show();
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
    public Intent getIntentInstance() {
        return this.getIntent();
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
    public int getRecItmesCount() {
        return rec_order_list.getAdapter().getItemCount();
    }

    @Override
    public void dataNotifyChanged() {
        commonListRecAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAdapter(final CommonDayCollectResp bean) {
        int rec_list_item;
        rec_list_item = R.layout.layout_settlment_list_item;
        commonListRecAdapter = new CommonListRecAdapter(this, bean,IOrdersProvider.ORDERS_ACT_DAY_COMMON_LIST, rec_list_item);
        this.bean=bean;
         linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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
    public void finishActivity() {
        this.finish();
    }

    @Override
    public void setRateVisF() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setPresenter(CommonDayCollectContract.Presenter presenter) {
        this.ordersSearchPresenter=presenter;
    }
    private int[] getRecyclerViewLastPosition(LinearLayoutManager layoutManager, CommonDayCollectResp bean) {
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
