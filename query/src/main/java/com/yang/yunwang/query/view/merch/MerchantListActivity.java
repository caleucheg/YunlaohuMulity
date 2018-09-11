package com.yang.yunwang.query.view.merch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.merchsearch.MerchListResp;
import com.yang.yunwang.query.api.contract.MerchListContract;
import com.yang.yunwang.query.api.presenter.MerchListPresenter;
import com.yang.yunwang.query.view.adapter.CommonListRecAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

@Route(path = IOrdersProvider.ORDERS_ACT_MERCH_LIST)
public class MerchantListActivity extends BaseActivity implements MerchListContract.View<MerchListResp>, View.OnClickListener {

    private MerchListContract.Presenter presenter;
    private XRecyclerView rec_order_list;
    private TextView title;
    private CommonListRecAdapter commonListRecAdapter;
    private boolean merchStaff = false;
    private boolean from_home = false;
    private boolean allocate;
    private String staff_id;
    private boolean isFWS;

    public String getStaff_id() {
        return staff_id;
    }

    public boolean isFWS() {
        return isFWS;
    }

    public boolean isAllocate() {
        return allocate;
    }

    public boolean isMerchStaff() {
        return merchStaff;
    }

    public boolean isFrom_home() {
        return from_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_orderslist);
        setTitleText(this.getString(R.string.shop_search_title));
        setHomeBarVisisble(true);
        init();
        initListener();
    }

    private void init() {
        new MerchListPresenter(this, this);
        allocate = getIntent().getBooleanExtra("allocate", false);
        from_home = getIntent().getBooleanExtra("from_home", false);
        merchStaff = getIntent().getBooleanExtra(ConstantUtils.merchStaff, false);
        staff_id = getIntent().getStringExtra("staff_id");
        isFWS = getIntent().getBooleanExtra(ConstantUtils.FWS_YUANGONG, false);
        rec_order_list = (XRecyclerView) findViewById(R.id.xrec_order_list);
        rec_order_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rec_order_list.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rec_order_list.setLoadingMoreEnabled(true);
        title = (TextView) findViewById(R.id.text_order_search_title);
        title.setText(this.getString(R.string.shop_search_title));
        presenter.initData();
    }

    private void initListener() {
        getLlBasetitleBack().setOnClickListener(this);
        getLlBasehomeBack().setOnClickListener(this);
        rec_order_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(MerchantListActivity.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    presenter.refresh();
                } else {
                    Toast.makeText(MerchantListActivity.this, R.string.refresh_error, Toast.LENGTH_SHORT).show();
                    rec_order_list.refreshComplete();
                }
            }

            @Override
            public void onLoadMore() {

                boolean isNetConn = NetStateUtils.isNetworkConnected(MerchantListActivity.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    presenter.loadMore();
                } else {
                    Toast.makeText(MerchantListActivity.this, R.string.loadmore_error, Toast.LENGTH_SHORT).show();
                    rec_order_list.loadMoreComplete();
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
    public void loadMoreComplete() {
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
    public void setAdapter(MerchListResp bean) {
        commonListRecAdapter = new CommonListRecAdapter(this, bean, IOrdersProvider.ORDERS_ACT_MERCH_LIST, R.layout.layout_shop_listitem);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
//        KLog.i(bean.getSys_no().size());
    }

    @Override
    public void setPresenter(MerchListContract.Presenter presenter) {
        this.presenter=presenter;
    }
}
