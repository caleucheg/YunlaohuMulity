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
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.commonsearch.CommonOrdersResp;
import com.yang.yunwang.query.api.contract.CommonListContract;
import com.yang.yunwang.query.api.presenter.CommonListPresenter;
import com.yang.yunwang.query.view.adapter.CommonListRecAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


@Route(path = IOrdersProvider.ORDERS_ACT_COMMON_LIST)
public class CommonListActivity extends BaseActivity implements CommonListContract.View<CommonOrdersResp>, View.OnClickListener {

    private CommonListContract.Presenter ordersSearchPresenter;
    private XRecyclerView rec_order_list;
    private CommonListRecAdapter commonListRecAdapter;
    private boolean merchStaff = false;

    private boolean is_top_rate = false;
    private boolean isFromMerch = false;
    private boolean isUserRate;
    private boolean isMerchHome = false;
    private boolean rateVis = true;
    private boolean isSettlement;
    private boolean ChangeStaffMerchTitle = false;
    private LinearLayoutManager linearLayoutManager;
    private CommonOrdersResp bean;
    private int[] posS;
    private int pageSize=20;

    public boolean isChangeStaffMerchTitle() {
        return ChangeStaffMerchTitle;
    }

    public void setChangeStaffMerchTitle(boolean changeStaffMerchTitle) {
        ChangeStaffMerchTitle = changeStaffMerchTitle;
    }

    public boolean isRateVis() {
        return rateVis;
    }

    public boolean isSettlement() {
        return isSettlement;
    }

    public boolean isUserRate() {
        return isUserRate;
    }

    public boolean isMerchHome() {
        return isMerchHome;
    }

    public boolean isMerchStaff() {
        return merchStaff;
    }

    public boolean is_top_rate() {
        return is_top_rate;
    }
//    private ImageView image_back;
//    private ImageView image_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_orderslist);
        setTitleText(getString(R.string.rate_title));
        setHomeBarVisisble(true);
        init();
        initListener();
    }

    private void init() {
        new CommonListPresenter(this, this);
        ordersSearchPresenter.initData();
        rec_order_list = (XRecyclerView) findViewById(R.id.xrec_order_list);
        rec_order_list.setLoadingMoreEnabled(true);
        rec_order_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rec_order_list.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        image_back = (ImageView) findViewById(R.id.image_back);
//        image_home = (ImageView) findViewById(R.id.image_home);
        is_top_rate = getIntent().getBooleanExtra("top_rate", false);
        merchStaff = getIntent().getBooleanExtra("merch_staff", false);
        isFromMerch = getIntent().getBooleanExtra("from_merch", false);
        isMerchHome = getIntent().getBooleanExtra("from_home_merch", false);
        isUserRate = getIntent().getBooleanExtra("isUserRate", false);
        isSettlement = getIntent().getBooleanExtra("settlement", false);
        if (isSettlement){
            setTitleText(getString(R.string.rate_title_sett));
        }
//        KLog.i(is_top_rate);
    }

    private void initListener() {
//        image_back.setOnClickListener(this);
//        image_home.setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
        getLlBasehomeBack().setOnClickListener(this);
        rec_order_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(CommonListActivity.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    ordersSearchPresenter.refresh();
                } else {
                    Toast.makeText(CommonListActivity.this, R.string.refresh_error, Toast.LENGTH_SHORT).show();
                    rec_order_list.refreshComplete();
                }
            }

            @Override
            public void onLoadMore() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(CommonListActivity.this);
                KLog.i(isNetConn);
                //
                boolean b = bean.getModel().get(0).getCount() <= pageSize;
                if (b){
                    rec_order_list.loadMoreComplete();
                    Toast.makeText(CommonListActivity.this, "已经加载全部数据", Toast.LENGTH_SHORT).show();
                }else {
                    if (isNetConn) {
                        ordersSearchPresenter.loadMore();
                        posS=getRecyclerViewLastPosition(linearLayoutManager,bean);
                    } else {
                        Toast.makeText(CommonListActivity.this, R.string.loadmore_error, Toast.LENGTH_SHORT).show();
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
            Intent intent = getIntent();
            String camera_flag = intent.getStringExtra("camera_flag");
            if (camera_flag != null && camera_flag.equals("camera")) {
                MyBundle intent_order_search = new MyBundle();
                String code = intent.getStringExtra("order_code");
                intent_order_search.put("order_code", code);
                OrdersIntent.orderSearch(intent_order_search);
                this.finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
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
    public void loadMoreComplete(boolean isScroll) {
//        KLog.i(posS[0]+"----"+posS[1]);
        //
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
    public void setAdapter(CommonOrdersResp bean) {
        int rec_list_item;
//        if (isSettlement){
        rec_list_item = R.layout.rec_list_item;
//        }else{
//            rec_list_item = R.layout.rec_list_item;
//        }
        KLog.i("seta");
        this.bean=bean;
        //
        commonListRecAdapter = new CommonListRecAdapter(this, bean, IOrdersProvider.ORDERS_ACT_COMMON_LIST, rec_list_item, is_top_rate, isSettlement);
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
        rateVis = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = getIntent();
            String camera_flag = intent.getStringExtra("camera_flag");
            if (camera_flag != null && camera_flag.equals("camera")) {
                MyBundle intent_order_search = new MyBundle();
                String code = intent.getStringExtra("order_code");
                intent_order_search.put("order_code", code);
                OrdersIntent.orderSearch(intent_order_search);
                this.finish();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setPresenter(CommonListContract.Presenter presenter) {
        this.ordersSearchPresenter = presenter;
    }

    //
    private int[] getRecyclerViewLastPosition(LinearLayoutManager layoutManager,CommonOrdersResp bean) {
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
