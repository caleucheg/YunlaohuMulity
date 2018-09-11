package com.yang.yunwang.query.view.staffcollect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
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
import com.yang.yunwang.query.api.bean.susersettle.list.SStaffCollectResp;
import com.yang.yunwang.query.api.contract.SStaffCollectContract;
import com.yang.yunwang.query.api.presenter.SStaffCollectPresenter;
import com.yang.yunwang.query.view.adapter.CommonListRecAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


@Route(path = IOrdersProvider.ORDERS_ACT_SSTAFF_COLLECT_LIST)
public class StaffColletListActivity extends BaseActivity implements SStaffCollectContract.View<SStaffCollectResp>, View.OnClickListener {

    private SStaffCollectContract.Presenter ordersSearchPresenter;
    private XRecyclerView rec_order_list;
    private CommonListRecAdapter commonListRecAdapter;
//    private ImageView image_back;
//    private ImageView image_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_orderslist);
        setTitleText("汇总");
        setHomeBarVisisble(true);

        init();
        initListener();
    }

    private void init() {
         new SStaffCollectPresenter(this, this);
        rec_order_list = (XRecyclerView) findViewById(R.id.xrec_order_list);
        rec_order_list.setLoadingMoreEnabled(true);
        rec_order_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rec_order_list.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        image_back = (ImageView) findViewById(R.id.image_back);
//        image_home = (ImageView) findViewById(R.id.image_home);
        ordersSearchPresenter.initData();
    }

    private void initListener() {
//        image_back.setOnClickListener(this);
//        image_home.setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
        getLlBasehomeBack().setOnClickListener(this);
        rec_order_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(StaffColletListActivity.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    ordersSearchPresenter.refresh();
                } else {
                    Toast.makeText(StaffColletListActivity.this, R.string.refresh_error, Toast.LENGTH_SHORT).show();
                    rec_order_list.refreshComplete();
                }
            }

            @Override
            public void onLoadMore() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(StaffColletListActivity.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    ordersSearchPresenter.loadMore();
                } else {
                    Toast.makeText(StaffColletListActivity.this, R.string.loadmore_error, Toast.LENGTH_SHORT).show();
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
            Intent intent = getIntent();
            String camera_flag = intent.getStringExtra("camera_flag");
            if (camera_flag != null && camera_flag.equals("camera")) {
                MyBundle intent_order_search = new MyBundle();//this, OrdersSearchActivity.class
                String code = intent.getStringExtra("order_code");
                intent_order_search.put("order_code", code);
                OrdersIntent.orderSearch(intent_order_search);
//                this.startActivity(intent_order_search);
            } else {
                this.finish();
            }

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
    public void setAdapter(SStaffCollectResp bean) {
        commonListRecAdapter = new CommonListRecAdapter(this, bean, IOrdersProvider.ORDERS_ACT_SSTAFF_COLLECT_LIST, R.layout.staff_collect_list_item);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rec_order_list.setLayoutManager(linearLayoutManager);
        commonListRecAdapter.setHasStableIds(true);
        rec_order_list.setItemViewCacheSize(5000);
        rec_order_list.setAdapter(commonListRecAdapter);
        rec_order_list.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(this.getResources().getColor(R.color.divide_gray_color))
                .size(getResources().getDimensionPixelSize(R.dimen.divider_1dp))
                .build());
        commonListRecAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = getIntent();
            String camera_flag = intent.getStringExtra("camera_flag");
            if (camera_flag != null && camera_flag.equals("camera")) {
                MyBundle intent_order_search = new MyBundle();//this, OrdersSearchActivity.class
                String code = intent.getStringExtra("order_code");
                intent_order_search.put("order_code", code);
                OrdersIntent.orderSearch(intent_order_search);
//                this.startActivity(intent_order_search);
                this.finish();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setPresenter(SStaffCollectContract.Presenter presenter) {
        this.ordersSearchPresenter=presenter;
    }
}
