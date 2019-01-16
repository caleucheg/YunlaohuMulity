package com.yang.yunwang.home.mainhome.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kw.rxbus.RxBus;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.busevent.CusUserAllcoateEvent;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.bean.cusallcoate.respone.CusUserAllcoateResp;
import com.yang.yunwang.home.mainhome.contract.CusUserAllcoateContract;
import com.yang.yunwang.home.mainhome.presenter.CusUserAllcoatePresenter;
import com.yang.yunwang.home.mainhome.view.adapter.CommonHomeListRecAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

@Route(path = IHomeProvider.HOME_ACT_CUS_USER_AL_LIST)
public class CusUserAlListActivity extends BaseActivity implements CusUserAllcoateContract.View<CusUserAllcoateResp> {
    private LinearLayout ll_no_data;
    /**
     * 2018-12-12 12:11:11~2018-12-12 12:11:11
     */
    private TextView time_range;
    private XRecyclerView xrec_order_list;
    private CusUserAllcoateContract.Presenter presenter;
    private Disposable disposable;
    private CommonHomeListRecAdapter commonListRecAdapter;
    private RelativeLayout rl_rec_list;
    private ProgressDialog progressDialog;
    private SimpleDateFormat sdfStart;
    private int[] posS;
    /**
     * 更多订单点击右上角“筛选”查询
     */
    private TextView tv_more;
    private LinearLayoutManager linearLayoutManager;
    private CusUserAllcoateResp bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cus_user_list_allcoate);
        new CusUserAllcoatePresenter(this, this);
        setTitleText("员工交易汇总");
        initView();
        initRxBus();
        initListener();
        presenter.initData();
    }

    private void initRxBus() {
        disposable = RxBus.getInstance().register(CusUserAllcoateEvent.class,
                AndroidSchedulers.mainThread(),
                new Consumer<CusUserAllcoateEvent>() {
                    @Override
                    public void accept(CusUserAllcoateEvent userEvent) {
                        KLog.i(userEvent);
                        Log.d("AActivity", "onNext:" + Thread.currentThread().getName());
                        String tradeType = userEvent.getTradeType();
                        String timeType = userEvent.getTimeType();
                        if (TextUtils.equals("yestoday", timeType)) {
                            final Calendar calendar = Calendar.getInstance(Locale.CHINA);
                            calendar.setTime(new Date());
                            calendar.add(Calendar.DATE, -1);
                            String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
                            String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
                            time_range.setText(dateStart + "~" + dateEnd);
                        } else if (TextUtils.equals("today", timeType)) {
                            final Calendar calendar = Calendar.getInstance(Locale.CHINA);
                            String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
                            String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
                            time_range.setText(dateStart + "~" + dateEnd);
                        } else if (TextUtils.equals("diy", timeType)) {
                            time_range.setText(userEvent.getStartTime() + "~" + userEvent.getEndTime());

                        }
                        presenter.filterOrder(userEvent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        KLog.i(throwable.getMessage());
//                        Toast.makeText(getBaseContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        ll_no_data = (LinearLayout) findViewById(R.id.ll_no_data);
        time_range = (TextView) findViewById(R.id.time_range);
        xrec_order_list = (XRecyclerView) findViewById(R.id.xrec_order_list);
        TextView comfirmBack = getComfirmBack();
        comfirmBack.setText("筛选");
        setHomeComfirmVisisble(true);
        comfirmBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBundle intent_result = new MyBundle();
                HomeIntent.cusUserAlFilter(intent_result);
            }
        });
        getLlBasetitleBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CusUserAlListActivity.this.finish();
            }
        });
        rl_rec_list = (RelativeLayout) findViewById(R.id.rl_rec_list);
        sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
        time_range.setText(dateStart + "~" + dateEnd);
        tv_more = (TextView) findViewById(R.id.tv_more);
        tv_more.setText("更多交易汇总点击右上角“筛选”查询");
    }

    private void initListener() {

//        getLlBasetitleBack().setOnClickListener(this);
//        getLlBasehomeBack().setOnClickListener(this);
        xrec_order_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(CusUserAlListActivity.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    presenter.refresh();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CusUserAlListActivity.this);
                    dialog.setMessage("网络连接异常，请检查您的手机网络");
                    dialog.setPositiveButton(CusUserAlListActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    xrec_order_list.refreshComplete();
                }
            }

            @Override
            public void onLoadMore() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(CusUserAlListActivity.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    presenter.loadMore();
                    posS = getRecyclerViewLastPosition(linearLayoutManager, bean);
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CusUserAlListActivity.this);
                    dialog.setMessage("网络连接异常，请检查您的手机网络");
                    dialog.setPositiveButton(CusUserAlListActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    xrec_order_list.loadMoreComplete();
                }
            }
        });
    }


    @Override
    public Intent getIntentInstance() {
        return this.getIntent();
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

    @Override
    public int getRecItmesCount() {
        return xrec_order_list.getAdapter().getItemCount();
    }

    @Override
    public void dataNotifyChanged() {
        commonListRecAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAdapter(CusUserAllcoateResp bean) {
        KLog.i("set a");
        this.bean = bean;
        commonListRecAdapter = new CommonHomeListRecAdapter(this, bean, IHomeProvider.HOME_ACT_CUS_USER_AL_LIST, R.layout.cus_user_allcoate_item);
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
        KLog.i(noData);
        if (noData) {
            ll_no_data.setVisibility(View.VISIBLE);
            rl_rec_list.setVisibility(View.GONE);
            time_range.setVisibility(View.GONE);
        } else {
            ll_no_data.setVisibility(View.GONE);
            rl_rec_list.setVisibility(View.VISIBLE);
            time_range.setVisibility(View.VISIBLE);
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
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(disposable);
    }

    @Override
    public void setPresenter(CusUserAllcoateContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private int[] getRecyclerViewLastPosition(LinearLayoutManager layoutManager, CusUserAllcoateResp bean) {
        KLog.i(bean.getData().getModel().size());
        int[] pos = new int[2];
        pos[0] = layoutManager.findFirstCompletelyVisibleItemPosition();
        OrientationHelper orientationHelper = OrientationHelper.createOrientationHelper(layoutManager, OrientationHelper.VERTICAL);
        int fromIndex = 0;
        int toIndex = bean.getData().getModel().size();
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

}
