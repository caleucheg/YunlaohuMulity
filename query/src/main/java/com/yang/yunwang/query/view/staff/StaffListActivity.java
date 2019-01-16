package com.yang.yunwang.query.view.staff;

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
import com.yang.yunwang.query.api.bean.staffsearch.StaffListResp;
import com.yang.yunwang.query.api.contract.StaffListContract;
import com.yang.yunwang.query.api.presenter.StaffListPresenter;
import com.yang.yunwang.query.view.adapter.CommonListRecAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

@Route(path = IOrdersProvider.ORDERS_ACT_STAFF_LIST)
public class StaffListActivity extends BaseActivity implements StaffListContract.View, View.OnClickListener {

    private StaffListContract.Presenter presenter;
    private XRecyclerView rec_order_list;
    private TextView title;
    private boolean fromMerch = false;

    private CommonListRecAdapter commonListRecAdapter;
    private boolean from_home = false;
    private boolean fromHomeDis;

    public boolean isFromHomeDis() {
        return fromHomeDis;
    }

    public boolean isFromMerch() {
        return fromMerch;
    }

    public boolean isFrom_home() {
        return from_home;
    }
//    private ImageView image_back;
//    private ImageView image_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_orderslist);
        setTitleText(this.getString(R.string.staff_search_title));
        setHomeBarVisisble(true);
        init();
        initListener();
    }

    private void init() {
        new StaffListPresenter(this, this);
        fromHomeDis = getIntent().getBooleanExtra(ConstantUtils.fromHomeDis, false);
        from_home = getIntent().getBooleanExtra(ConstantUtils.fromHome, false);
        fromMerch = getIntent().getBooleanExtra(ConstantUtils.fromMerch, false);
        rec_order_list = (XRecyclerView) findViewById(R.id.xrec_order_list);
        rec_order_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rec_order_list.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rec_order_list.setLoadingMoreEnabled(true);
        title = (TextView) findViewById(R.id.text_order_search_title);
        title.setText(this.getString(R.string.staff_search_title));
//        image_back = (ImageView) findViewById(R.id.image_back);
//        image_home = (ImageView) findViewById(R.id.image_home);
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
                boolean isNetConn = NetStateUtils.isNetworkConnected(StaffListActivity.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    presenter.refresh();
                } else {
                    Toast.makeText(StaffListActivity.this, R.string.refresh_error, Toast.LENGTH_SHORT).show();
                    rec_order_list.refreshComplete();
                }
            }

            @Override
            public void onLoadMore() {

                boolean isNetConn = NetStateUtils.isNetworkConnected(StaffListActivity.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    presenter.loadMore();
                } else {
                    rec_order_list.loadMoreComplete();
                    Toast.makeText(StaffListActivity.this, R.string.loadmore_error, Toast.LENGTH_SHORT).show();
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
    public void setAdapter(StaffListResp bean, String inner_flag) {
        KLog.i(inner_flag);
        commonListRecAdapter = new CommonListRecAdapter(this, bean, inner_flag, IOrdersProvider.ORDERS_ACT_STAFF_LIST, R.layout.layout_stafflist_item);
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
    }

    @Override
    public void setPresenter(StaffListContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
