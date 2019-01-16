package com.yang.yunwang.query.view.commoncard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.merchsearch.MerchListResp;
import com.yang.yunwang.query.api.bean.ordersettel.OrderSettleResp;
import com.yang.yunwang.query.api.bean.staffsearch.StaffListResp;
import com.yang.yunwang.query.api.contract.CommonCardCusContract;
import com.yang.yunwang.query.api.presenter.CommonCardCusPresenter;
import com.yang.yunwang.query.view.adapter.CommonListRecAdapter;

@Route(path = IOrdersProvider.ORDERS_ACT_COMMON_CARD_CUS_LIST)
public class CommonCardCusActivity extends BaseActivity implements CommonCardCusContract.View<Object>, View.OnClickListener {

    private TextView text_shop_info_title;
    /**
     * 筛选
     */
    private TextView tv_filter;
    private LinearLayout ll_no_data;
    private XRecyclerView xrec_order_list;
    private CommonListRecAdapter commonListRecAdapter;
    private CommonCardCusContract.Presenter presenter;
    private boolean isActive;
    private boolean isCus;
    private ProgressDialog progressDialog;
    private Object bean;
    private int[] posS;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.yang.yunwang.base.R.layout.layout_card_orderslist_new);
        new CommonCardCusPresenter(this, this);
        isActive = getIntent().getBooleanExtra("isActive", false);
        isCus = getIntent().getBooleanExtra("isCus", false);
        if (isActive) {
            setTitleText("今日活跃商户");
        } else if (isCus) {
            setTitleText("选择商户员工");
        } else {
            setTitleText("选择商户");
        }
        initView();
        if (NetStateUtils.isNetworkConnected(CommonCardCusActivity.this)) {
            presenter.initData(isActive, isCus);
        } else {
            noData(true);
        }
    }

    private void initView() {
        text_shop_info_title = (TextView) findViewById(R.id.text_shop_info_title);
        tv_filter = (TextView) findViewById(R.id.tv_filter);
        tv_filter.setOnClickListener(this);
        ll_no_data = (LinearLayout) findViewById(R.id.ll_no_data);
        xrec_order_list = (XRecyclerView) findViewById(R.id.xrec_order_list);
        xrec_order_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(CommonCardCusActivity.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    presenter.refresh();
                } else {
                    Toast.makeText(CommonCardCusActivity.this, R.string.refresh_error, Toast.LENGTH_SHORT).show();
                    xrec_order_list.refreshComplete();
                }
            }

            @Override
            public void onLoadMore() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(CommonCardCusActivity.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    presenter.loadMore();
                    posS = getRecyclerViewLastPosition(linearLayoutManager, bean);
                } else {
                    Toast.makeText(CommonCardCusActivity.this, R.string.loadmore_error, Toast.LENGTH_SHORT).show();
                    xrec_order_list.loadMoreComplete();
                }
            }
        });
        getLlBasetitleBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonCardCusActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_filter) {

        } else {

        }
    }

    @Override
    public void refreshComplete() {
        xrec_order_list.refreshComplete();
    }

    @Override
    public void loadMoreComplete(boolean hasMore) {
        if (posS != null && posS.length > 0) {
            if (hasMore) {
                linearLayoutManager.scrollToPositionWithOffset(posS[0], posS[1]);
            }
        }
        xrec_order_list.loadMoreComplete();

    }

    private int[] getRecyclerViewLastPosition(LinearLayoutManager layoutManager, Object bean) {
        int[] pos = new int[2];
        pos[0] = layoutManager.findFirstCompletelyVisibleItemPosition();
        OrientationHelper orientationHelper = OrientationHelper.createOrientationHelper(layoutManager, OrientationHelper.VERTICAL);
        int fromIndex = 0;
        int toIndex = 10;
        if (isCus) {
            StaffListResp beanR = (StaffListResp) bean;
            toIndex = beanR.getModel().size();
        } else if (isActive) {
            OrderSettleResp beanR = (OrderSettleResp) bean;
            toIndex = beanR.getModel().size();
        } else {
            MerchListResp beanR = (MerchListResp) bean;
            toIndex = beanR.getModel().size();
        }

        final int start = orientationHelper.getStartAfterPadding();
        final int end = orientationHelper.getEndAfterPadding();
        final int next = toIndex > fromIndex ? 1 : -1;
        for (int i = fromIndex; i != toIndex; i += next) {
            final View child = xrec_order_list.getChildAt(i);
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
        KLog.i(pos);
        return pos;
    }

    @Override
    public void dataNotifyChanged() {
        commonListRecAdapter.notifyDataSetChanged();
    }

    @Override
    public int getRecItmesCount() {
        return xrec_order_list.getAdapter().getItemCount();
    }

    @Override
    public Intent getIntentInstance() {
        return getIntent();
    }

    @Override
    public void setAdapter(Object bean, String inner_flag) {
        if (isCus) {
            StaffListResp beanR = (StaffListResp) bean;
            this.bean = (StaffListResp) bean;
            commonListRecAdapter = new CommonListRecAdapter(this, beanR, inner_flag, IOrdersProvider.ORDERS_ACT_COMMON_CARD_STAFF_LIST, R.layout.customer_filter_item);
        } else if (isActive) {
            OrderSettleResp beanR = (OrderSettleResp) bean;
            this.bean = (OrderSettleResp) bean;
            commonListRecAdapter = new CommonListRecAdapter(this, beanR, IOrdersProvider.ORDERS_ACT_COMMON_CARD_ACTICVE_LIST, R.layout.cus_user_allcoate_new_item);
        } else {
            MerchListResp beanR = (MerchListResp) bean;
            this.bean = (MerchListResp) bean;
            commonListRecAdapter = new CommonListRecAdapter(this, beanR, IOrdersProvider.ORDERS_ACT_COMMON_CARD_CUS_LIST, R.layout.customer_filter_item);
        }
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commonListRecAdapter.setHasStableIds(true);
        xrec_order_list.setItemViewCacheSize(5000);
        xrec_order_list.setLayoutManager(linearLayoutManager);
        xrec_order_list.setAdapter(commonListRecAdapter);
        commonListRecAdapter.notifyDataSetChanged();
    }

    @Override
    public void noData(boolean noData) {
        KLog.i("noData" + noData);
        if (noData) {
            ll_no_data.setVisibility(View.VISIBLE);
            xrec_order_list.setVisibility(View.GONE);
        } else {
            ll_no_data.setVisibility(View.GONE);
            xrec_order_list.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle(this.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(this.getResources().getString(R.string.orders_search_waitting));
        progressDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void setPresenter(CommonCardCusContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isCus() {
        return isCus;
    }
}
