package com.yang.yunwang.query.view.order.list;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.refundsearch.refundlist.RefundListResp;
import com.yang.yunwang.query.api.contract.RefundListContract;
import com.yang.yunwang.query.api.presenter.RefundListPresenter;
import com.yang.yunwang.query.view.adapter.CommonListRecAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
@Route(path = IOrdersProvider.ORDERS_ACT_UNREFUND_LIST)
public class UnRefundListActivity extends BaseActivity implements RefundListContract.View, View.OnClickListener {

    private RefundListContract.Presenter presenter;
    private XRecyclerView rec_order_list;
    private TextView title;
    private CommonListRecAdapter commonListRecAdapter;
    //    private ImageView image_back;
//    private ImageView image_home;
    private boolean hasRefundRole = false;
    private LinearLayoutManager linearLayoutManager;
    private RefundListResp bean;
    private int[] posS;
    private int pageSize=20;

    public boolean isHasRefundRole() {
        return hasRefundRole;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_orderslist);
        setTitleText(this.getString(R.string.unrefund_search_title));
        setHomeBarVisisble(true);
        new RefundListPresenter(this, this);
        init();
        initListener();
    }

    private void init() {
        rec_order_list = (XRecyclerView) findViewById(R.id.xrec_order_list);
        rec_order_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rec_order_list.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        title = (TextView) findViewById(R.id.text_order_search_title);
        title.setText(this.getString(R.string.unrefund_search_title));
        rec_order_list.setLoadingMoreEnabled(true);
        presenter.initData();
    }



    private void initListener() {

        getLlBasetitleBack().setOnClickListener(this);
        getLlBasehomeBack().setOnClickListener(this);
        rec_order_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(UnRefundListActivity.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    presenter.refresh();
                } else {
                    Toast.makeText(UnRefundListActivity.this, R.string.refresh_error, Toast.LENGTH_SHORT).show();
                    rec_order_list.refreshComplete();
                }
            }

            @Override
            public void onLoadMore() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(UnRefundListActivity.this);
                KLog.i(isNetConn);
                boolean b = bean.getModel().get(0).getCount() <= pageSize;
                if (b){
                    rec_order_list.loadMoreComplete();
                    Toast.makeText(UnRefundListActivity.this, "已经加载全部数据", Toast.LENGTH_SHORT).show();
                }else {
                    if (isNetConn) {
                        presenter.loadMore();
                        posS=getRecyclerViewLastPosition(linearLayoutManager,bean);
                    } else {
                        Toast.makeText(UnRefundListActivity.this, R.string.loadmore_error, Toast.LENGTH_SHORT).show();
                        rec_order_list.loadMoreComplete();
                    }
                }
//                if (isNetConn) {
//                    presenter.loadMore();
//                } else {
//                    Toast.makeText(UnRefundListActivity.this, R.string.loadmore_error, Toast.LENGTH_SHORT).show();
//                    rec_order_list.loadMoreComplete();
//                }
            }
        });
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
    public void setAdapter(RefundListResp bean, boolean hasRole) {
        commonListRecAdapter = new CommonListRecAdapter(this, bean, IOrdersProvider.ORDERS_ACT_UNREFUND_LIST, R.layout.layout_refund_list_item,hasRole);
         linearLayoutManager = new LinearLayoutManager(this);
        this.bean=bean;
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commonListRecAdapter.setHasStableIds(true);
        rec_order_list.setItemViewCacheSize(5000);
        rec_order_list.setLayoutManager(linearLayoutManager);
        rec_order_list.setAdapter(commonListRecAdapter);
        rec_order_list.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(this.getResources().getColor(R.color.divide_gray_color))
                .size(getResources().getDimensionPixelSize(R.dimen.divider_1dp))
                .build());
    }

    @Override
    public void onErrorRole() {
        Toast.makeText(this, "获取退款权限失败,请重新查询", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_home) {
            HomeIntent.launchHome();
            this.finish();

        } else if (i == R.id.image_back) {
            Intent intent = getIntent();
            String camera_flag = intent.getStringExtra("camera_flag");
            if (camera_flag != null && camera_flag.equals("camera")) {
                MyBundle intent_order_search = new MyBundle();//this, UnRefundActivity.class
                String code = intent.getStringExtra("unrefund_code");
                intent_order_search.put("unrefund_code", code);
//                this.startActivity(intent_order_search);
                OrdersIntent.unRefundSearch(intent_order_search);
                this.finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            } else {
                this.finish();
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = getIntent();
            String camera_flag = intent.getStringExtra("camera_flag");
            if (camera_flag != null && camera_flag.equals("camera")) {
                MyBundle intent_order_search = new MyBundle();//this, UnRefundActivity.class
                String code = intent.getStringExtra("unrefund_code");
                intent_order_search.put("unrefund_code", code);
                OrdersIntent.unRefundSearch(intent_order_search);
                this.finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == 1) {
                if (presenter != null) {
                    presenter.initData();
                } else {
                    new RefundListPresenter(this, this);
                    presenter.initData();
                }
            }
        }
    }

    @Override
    public void setPresenter(RefundListContract.Presenter presenter) {
        this.presenter=presenter;
    }

    private int[] getRecyclerViewLastPosition(LinearLayoutManager layoutManager,RefundListResp bean) {
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
