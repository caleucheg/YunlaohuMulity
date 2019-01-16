package com.yang.yunwang.home.mainhome.view.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kw.rxbus.RxBus;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.busevent.LoginOutEvent;
import com.yang.yunwang.base.busevent.MainHomeDialogEvent;
import com.yang.yunwang.base.busevent.OrderFilterEvent;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.respone.CommonNewOrderResp;
import com.yang.yunwang.home.mainhome.contract.NewOrderListContract;
import com.yang.yunwang.home.mainhome.presenter.NewOrderListPresenter;
import com.yang.yunwang.home.mainhome.view.adapter.CommonHomeListRecAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Route(path = IHomeProvider.HOME_ACT_NEW_ORDER_LIST)
public class NewTestActivity extends BaseActivity implements NewOrderListContract.View<CommonNewOrderResp>, View.OnClickListener {
    NewOrderListContract.Presenter ordersSearchPresenter;
    private XRecyclerView rec_order_list;
    private CommonHomeListRecAdapter commonListRecAdapter;
    private View imageBack;
    private TextView tv_filter;
    private View ll_no_data;
    private View rl_rec_list;
    private Disposable disposable;
    private boolean isFromArouter = false;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver br;
    private Disposable disposableL;
    private Disposable disposableA;
    private ProgressDialog progressDialog;
    private CommonNewOrderResp bean;
    private int[] posS;
    private int pageSize = 10;
    private LinearLayoutManager linearLayoutManager;
    private PullToRefreshScrollView pullToRefreshScrollView;
    /**
     * 点击加载更多
     */
    private TextView textView_more_data;
    private LinearLayout ll_no;

    //    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_orderslist_new);
        initView();
        setTitleText("订单流水");
        setHomeComfirmVisisble(true);
        getComfirmBack().setText("筛选");
        getLlBasetitleBack().setVisibility(View.INVISIBLE);
        isFromArouter = getIntent().getBooleanExtra("isFromArouter", false);
        new NewOrderListPresenter(this, this);
        if (!isFromArouter) {
            onHomePageChange();
        }
        init();
        initListener();
//        ordersSearchPresenter.initData();
        initRxBusMes();
        if (isFromArouter) {
            ordersSearchPresenter.initData();
            getLlBasetitleBack().setVisibility(View.VISIBLE);
            getLlBasetitleBack().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewTestActivity.this.finish();
                }
            });
        }
        KLog.i("create");
    }

    private void initRxBusMes() {
        if (!isFromArouter) {
            disposable = RxBus.getInstance().register(OrderFilterEvent.class,
                    AndroidSchedulers.mainThread(),
                    new Consumer<OrderFilterEvent>() {
                        @Override
                        public void accept(OrderFilterEvent userEvent) {
                            ll_no_data.setVisibility(View.GONE);
                            ll_no.setVisibility(View.GONE);
                            pullToRefreshScrollView.setVisibility(View.GONE);
                            rl_rec_list.setVisibility(View.VISIBLE);
                            KLog.i(userEvent);
                            Log.d("AActivity", "onNext:" + Thread.currentThread().getName());
                            if (!userEvent.isFromA()) {
                                KLog.i(userEvent.isFromA());
                                if (bean != null) {
                                    bean.getData().getModel().clear();
                                }
                                if (commonListRecAdapter != null && bean != null) {
                                    bean.getData().getModel().clear();
                                    commonListRecAdapter.notifyDataSetChanged();
                                }
                                ordersSearchPresenter.filterOrder(userEvent);
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            KLog.i(throwable.getMessage());
//                Toast.makeText(getBaseContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        }
        if (isFromArouter) {
            disposableA = RxBus.getInstance().register(OrderFilterEvent.class,
                    AndroidSchedulers.mainThread(),
                    new Consumer<OrderFilterEvent>() {
                        @Override
                        public void accept(OrderFilterEvent userEvent) {
                            ll_no_data.setVisibility(View.GONE);
                            ll_no.setVisibility(View.GONE);
                            pullToRefreshScrollView.setVisibility(View.GONE);
                            rl_rec_list.setVisibility(View.VISIBLE);
                            KLog.i(userEvent);
                            Log.d("AActivity", "onNext:" + Thread.currentThread().getName());
                            if (userEvent.isFromA()) {
                                if (bean != null) {
                                    bean.getData().getModel().clear();
                                }
                                if (commonListRecAdapter != null && bean != null) {
                                    bean.getData().getModel().clear();
                                    commonListRecAdapter.notifyDataSetChanged();
                                }
                                ordersSearchPresenter.filterOrder(userEvent);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            KLog.i(throwable.getMessage());
//                Toast.makeText(getBaseContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        }


        disposableL = RxBus.getInstance().register(LoginOutEvent.class, Schedulers.newThread(),
                new Consumer<LoginOutEvent>() {
                    @Override
                    public void accept(LoginOutEvent userEvent) {
                        if (userEvent.isLogunOut()) {
                            KLog.i(userEvent.isLogunOut());
                            if (localBroadcastManager != null && br != null) {
                                localBroadcastManager.unregisterReceiver(br);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        KLog.i(throwable.getMessage());
                    }
                });
    }

    private void onHomePageChange() {
        localBroadcastManager = LocalBroadcastManager
                .getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        KLog.i("onHomepage111");
        intentFilter.addAction("pageChange");
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int pos = intent.getIntExtra("position", -1);
                KLog.i(pos);
                if (pos == 1) {
                    KLog.i("onHomepage1112222");
                    KLog.i("page-change");
                    refreshDataHameChage();
                }
            }

        };
        localBroadcastManager.registerReceiver(br, intentFilter);
    }

    private void refreshDataHameChage() {
        if (bean != null) {
            bean.getData().getModel().clear();
        }
        if (commonListRecAdapter != null && bean != null) {
            bean.getData().getModel().clear();
            commonListRecAdapter.notifyDataSetChanged();
        }
        if (NetStateUtils.isNetworkConnected(NewTestActivity.this)) {
            if (ordersSearchPresenter != null) {
                ordersSearchPresenter.initData();
            }
        } else {
//                        noData(true);
            KLog.i("nodata1");
            AlertDialog.Builder dialog = new AlertDialog.Builder(NewTestActivity.this);
            dialog.setMessage("网络连接异常，请检查您的手机网络");
            dialog.setPositiveButton(NewTestActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pullToRefreshScrollView.invalidate();
                    dialog.dismiss();
                }
            });
            dialog.show();
            ll_no.setVisibility(View.VISIBLE);
            ll_no_data.setVisibility(View.INVISIBLE);
            pullToRefreshScrollView.setVisibility(View.VISIBLE);
            textView_more_data.setVisibility(View.VISIBLE);
            rl_rec_list.setVisibility(View.GONE);
            textView_more_data.setClickable(true);
            textView_more_data.setFocusable(true);
            textView_more_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetStateUtils.isNetworkConnected(NewTestActivity.this)) {
                        if (ordersSearchPresenter != null) {
                            ordersSearchPresenter.initData();
                        }
                    } else {
//                    noData(true);
                        ll_no.setVisibility(View.VISIBLE);
                        ll_no_data.setVisibility(View.INVISIBLE);
                        pullToRefreshScrollView.setVisibility(View.VISIBLE);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(NewTestActivity.this);
                        dialog.setMessage("网络连接异常，请检查您的手机网络");
                        dialog.setPositiveButton(NewTestActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pullToRefreshScrollView.invalidate();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        pullToRefreshScrollView.onRefreshComplete();
                        rec_order_list.refreshComplete();
                    }
                }
            });
            pullToRefreshScrollView.invalidate();
//                        Toast.makeText(NewTestActivity.this, "网络连接异常，请检查您的手机网络！", Toast.LENGTH_SHORT).show();
//                        pullToRefreshScrollView.onRefreshComplete();
//                        rec_order_list.refreshComplete();

        }
    }

    private void init() {
        rec_order_list = (XRecyclerView) findViewById(R.id.xrec_order_list);
        imageBack = findViewById(R.id.imageView_no_data);
        ll_no_data = findViewById(R.id.ll_no_data);
        rec_order_list.setLoadingMoreEnabled(true);
        rec_order_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rec_order_list.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        tv_filter = (TextView) findViewById(R.id.tv_filter);
        rl_rec_list = findViewById(R.id.rl_rec_list);
        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
    }

    private void initListener() {
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                ConstantUtils.IS_ATFER_LOGIN_INIT = true;
                if (NetStateUtils.isNetworkConnected(NewTestActivity.this)) {
                    if (ordersSearchPresenter != null) {
                        ordersSearchPresenter.initData();
                    }
                } else {
//                    noData(true);
                    ll_no.setVisibility(View.VISIBLE);
                    ll_no_data.setVisibility(View.INVISIBLE);
                    pullToRefreshScrollView.setVisibility(View.VISIBLE);
                    rl_rec_list.setVisibility(View.GONE);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(NewTestActivity.this);
                    dialog.setMessage("网络连接异常，请检查您的手机网络");
                    dialog.setPositiveButton(NewTestActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pullToRefreshScrollView.invalidate();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    pullToRefreshScrollView.onRefreshComplete();
                    rec_order_list.refreshComplete();
                }
                KLog.i("nodata2");

            }
        });

        rec_order_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if (commonListRecAdapter != null) {
                    bean.getData().getModel().clear();
                    commonListRecAdapter.notifyDataSetChanged();
                }
                boolean isNetConn = NetStateUtils.isNetworkConnected(NewTestActivity.this);
                KLog.i(isNetConn);
                if (isNetConn) {
                    ordersSearchPresenter.refresh();
                } else {
//                    Toast.makeText(NewTestActivity.this, R.string.refresh_error, Toast.LENGTH_SHORT).show();
                    refreshComplete();
                    textView_more_data.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (NetStateUtils.isNetworkConnected(NewTestActivity.this)) {
                                if (ordersSearchPresenter != null) {
                                    ordersSearchPresenter.initData();
                                }
                            } else {
//                    noData(true);
                                ll_no.setVisibility(View.VISIBLE);
                                ll_no_data.setVisibility(View.INVISIBLE);
                                pullToRefreshScrollView.setVisibility(View.VISIBLE);
                                AlertDialog.Builder dialog = new AlertDialog.Builder(NewTestActivity.this);
                                dialog.setMessage("网络连接异常，请检查您的手机网络");
                                dialog.setPositiveButton(NewTestActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        pullToRefreshScrollView.invalidate();
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                                pullToRefreshScrollView.onRefreshComplete();
                                rec_order_list.refreshComplete();
                            }
                        }
                    });
                    rl_rec_list.setVisibility(View.GONE);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(NewTestActivity.this);
                    dialog.setMessage("网络连接异常，请检查您的手机网络");
                    dialog.setPositiveButton(NewTestActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ll_no.setVisibility(View.VISIBLE);
                            ll_no_data.setVisibility(View.INVISIBLE);
                            pullToRefreshScrollView.setVisibility(View.VISIBLE);
                            textView_more_data.setVisibility(View.VISIBLE);
                            rl_rec_list.setVisibility(View.GONE);
                            textView_more_data.setClickable(true);
                            textView_more_data.setFocusable(true);
                            pullToRefreshScrollView.invalidate();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
            }

            @Override
            public void onLoadMore() {
                boolean isNetConn = NetStateUtils.isNetworkConnected(NewTestActivity.this);
                boolean b = bean.getData().getModel().get(0).getDataCount() <= pageSize;
                if (b) {
                    rec_order_list.loadMoreComplete();
                    Toast.makeText(NewTestActivity.this, "已经加载全部数据", Toast.LENGTH_SHORT).show();
                } else {
                    KLog.i(isNetConn);
                    if (isNetConn) {
                        ordersSearchPresenter.loadMore();
                        posS = getRecyclerViewLastPosition(linearLayoutManager, bean);
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(NewTestActivity.this);
                        dialog.setMessage("网络连接异常，请检查您的手机网络");
                        dialog.setPositiveButton(NewTestActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pullToRefreshScrollView.invalidate();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        rec_order_list.loadMoreComplete();
                    }
                }
                KLog.i("nodata3");

            }
        });
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBundle intent_result = new MyBundle();
                intent_result.put("isFromArouter", isFromArouter);
                HomeIntent.orderFilter(intent_result);
            }
        });
        getComfirmBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBundle intent_result = new MyBundle();
                intent_result.put("isFromArouter", isFromArouter);
                HomeIntent.orderFilter(intent_result);
            }
        });

    }

    @Override
    public void onClick(View view) {

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
    public void loadMoreComplete(boolean hasMore) {
        if (posS != null && posS.length > 0) {
            if (hasMore) {
                linearLayoutManager.scrollToPositionWithOffset(posS[0], posS[1]);
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
    public void setAdapter(CommonNewOrderResp bean) {
        KLog.i("set a");
        this.bean = bean;
        if (textView_more_data.getVisibility() == View.VISIBLE) {
            textView_more_data.setVisibility(View.GONE);
            textView_more_data.setClickable(false);
            textView_more_data.setFocusable(false);
        }
        rl_rec_list.setVisibility(View.VISIBLE);
        commonListRecAdapter = new CommonHomeListRecAdapter(this, bean, IHomeProvider.NEW_ORDER, R.layout.new_rec_order_item, isFromArouter);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commonListRecAdapter.setHasStableIds(true);
        rec_order_list.setItemViewCacheSize(5000);
        rec_order_list.setLayoutManager(linearLayoutManager);
        rec_order_list.setAdapter(commonListRecAdapter);
        commonListRecAdapter.notifyDataSetChanged();
    }

    private int[] getRecyclerViewLastPosition(LinearLayoutManager layoutManager, CommonNewOrderResp bean) {
        int[] pos = new int[2];
        pos[0] = layoutManager.findFirstCompletelyVisibleItemPosition();
        OrientationHelper orientationHelper = OrientationHelper.createOrientationHelper(layoutManager, OrientationHelper.VERTICAL);
        int fromIndex = 0;
        int toIndex = bean.getData().getModel().size();
        final int start = orientationHelper.getStartAfterPadding();
        final int end = orientationHelper.getEndAfterPadding();
        final int next = toIndex > fromIndex ? 1 : -1;
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

    @Override
    public void noData(boolean noData) {
        KLog.i("noData" + noData);
        if (noData) {
            ll_no_data.setVisibility(View.VISIBLE);
            ll_no.setVisibility(View.VISIBLE);
            pullToRefreshScrollView.setVisibility(View.VISIBLE);
            rl_rec_list.setVisibility(View.GONE);
            pullToRefreshScrollView.onRefreshComplete();
        } else {
            ll_no_data.setVisibility(View.GONE);
            ll_no.setVisibility(View.GONE);
            pullToRefreshScrollView.setVisibility(View.GONE);
            rl_rec_list.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showDialog() {
        if (isFromArouter) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle(this.getResources().getString(R.string.alert_title));
            progressDialog.setMessage(this.getResources().getString(R.string.orders_search_waitting));
            progressDialog.show();
        } else {
            KLog.i("sendDia");
            RxBus.getInstance().send(new MainHomeDialogEvent(true));
        }

    }

    @Override
    public void dismissDialog() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFromArouter) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                } else {
                    RxBus.getInstance().send(new MainHomeDialogEvent(false));
                }

//                if (progressDialog != null) {
//                    progressDialog.dismiss();
//                }
            }
        }, 200);
    }

    @Override
    public void dismissLoadMore() {
        if (pullToRefreshScrollView != null) {
            pullToRefreshScrollView.onRefreshComplete();
        }
        if (rec_order_list != null) {
            rec_order_list.refreshComplete();
        }
        if (textView_more_data != null) {
            textView_more_data.setVisibility(View.GONE);
        }
    }

    @Override
    public void setPresenter(NewOrderListContract.Presenter presenter) {
        this.ordersSearchPresenter = presenter;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            RxBus.getInstance().unregister(disposable);
        }
        if (disposableA != null) {
            RxBus.getInstance().unregister(disposableA);
        }
        RxBus.getInstance().unregister(disposableL);
        if (localBroadcastManager != null && br != null) {
            localBroadcastManager.unregisterReceiver(br);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isFromArouter) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(this.getResources().getString(R.string.alert_title));
                //Done switch version
                if (DycLibIntent.hasModule()) {
                    dialog.setMessage(this.getResources().getString(R.string.dexit));
                } else {
                    dialog.setMessage(this.getResources().getString(R.string.exit));
                }
                dialog.setPositiveButton(this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
                dialog.setNegativeButton(this.getResources().getString(R.string.alert_native), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        KLog.i("resume");


    }

    @Override
    protected void onPause() {
        super.onPause();
        KLog.i("onPause");

    }

    private void initView() {
        textView_more_data = (TextView) findViewById(R.id.textView_more_data);
        ll_no = (LinearLayout) findViewById(R.id.ll_no);
    }
}
