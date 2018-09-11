package com.yang.yunwang.query.api.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.QueryReService;
import com.yang.yunwang.query.api.bean.commondaycollect.CommonDayCollectReq;
import com.yang.yunwang.query.api.bean.commondaycollect.CommonDayCollectResp;
import com.yang.yunwang.query.api.bean.commondaycollect.Model;
import com.yang.yunwang.query.api.bean.commondaycollect.PagingInfo;
import com.yang.yunwang.query.api.contract.CommonDayCollectContract;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 */

public class CommonDayCollectPresenter implements CommonDayCollectContract.Presenter {
    private final CommonDayCollectContract.View view;
    private final Context context;

    private final long page_size = 20;
    private CommonDayCollectResp bean;
    private ProgressDialog progressDialog;
    private String start_time;
    private String end_time;
    private String customer;
    private String customer_name;
    private String pay_type;
    private long total_count;
    private long page = 0;

    public CommonDayCollectPresenter(CommonDayCollectContract.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        bean = new CommonDayCollectResp();
        bean.setModel(new ArrayList<Model>());
        Intent intent = view.getIntentInstance();
        start_time = intent.getStringExtra("order_start_time");
        end_time = intent.getStringExtra("order_end_time");
        customer = intent.getStringExtra("order_search_customer");
        customer_name = intent.getStringExtra("orders_search_customer_name");
        pay_type = intent.getStringExtra("orders_search_pay_type");
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void initData() {
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.show();
        searchOrders(ConstantUtils.SYS_NO, customer_name, customer, pay_type, start_time, end_time, page, page_size, true, false);
    }

    @Override
    public void loadMore() {
        if( (view.getRecItmesCount() - 2 < total_count)&&(total_count>page_size)) {
            final int items_count = view.getRecItmesCount();

            if (items_count / page_size < page + 1) {
                //加载当前页
                searchOrders(ConstantUtils.SYS_NO, customer_name, customer, pay_type, start_time, end_time, page, page_size, false, false);
            } else {
                //加载下一页
                page++;
                searchOrders(ConstantUtils.SYS_NO, customer_name, customer, pay_type, start_time, end_time, page, page_size, false, false);
            }
        } else {
            view.loadMoreComplete(false);
            Toast.makeText(context, "已经加载全部数据", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void refresh() {
        page = 0;
        Intent intent = view.getIntentInstance();
        String code = intent.getStringExtra("order_code");
        String start_time = intent.getStringExtra("order_start_time");
        String end_time = intent.getStringExtra("order_end_time");
        String customer = intent.getStringExtra("order_search_customer");
        String customer_name = intent.getStringExtra("orders_search_customer_name");
        String pay_type = intent.getStringExtra("orders_search_pay_type");
        searchOrders(ConstantUtils.SYS_NO, customer_name, customer, pay_type, start_time, end_time, page, page_size, false, true);
    }


    /**
     * sys_no:主键
     * customer_name:商户用户名
     * customer：商户名称
     * pay_type:支付类型 微信/支付宝
     * time_start:订单起始时间
     * time_end:订单结束时间
     */
    private void searchOrders(String sys_no, String customer_name,
                              String customer, String pay_type, String time_start,
                              String time_end, long page_number, final long page_size,
                              final boolean isInit, final boolean isRefresh) {


        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPageSize(page_size);
        pagingInfo.setPageNumber(page_number);
        BaseObserver<CommonDayCollectResp> baseObserver = new BaseObserver<CommonDayCollectResp>(context) {
            @Override
            protected void doOnNext(CommonDayCollectResp value) {
                if (value.getModel().size() > 0) {
                    if (isInit) {
                        bean = value;
                        view.setAdapter(bean);
                        total_count = value.getTotalCount();
                        progressDialog.dismiss();
                    } else if (isRefresh) {
                        bean = value;
                        view.setAdapter(bean);
                        view.refreshComplete();
                        view.dataNotifyChanged();
                    } else {
                        ArrayList tempList=new ArrayList();
                        tempList.addAll(bean.getModel());
                        tempList.addAll(value.getModel());
                        bean.getModel().clear();
                        bean.setModel(tempList);
                        view.setAdapter(bean);
                        view.dataNotifyChanged();
                        view.loadMoreComplete(true);
                    }
                } else {
                    view.setAdapter(bean);
                    if (isInit && progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
                    if (isInit){
                        KLog.i("empty");
                    }else if (isRefresh){
                        view.dataNotifyChanged();
                        view.refreshComplete();
                    }else {
                        view.dataNotifyChanged();
                        view.loadMoreComplete(false);
                    }
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                view.setAdapter(bean);
                if (isInit && progressDialog != null) {
                    progressDialog.dismiss();
                }else if (isRefresh) {
                    view.refreshComplete();
                    view.dataNotifyChanged();
                }else {
                    view.dataNotifyChanged();
                    view.loadMoreComplete(false);

                }
            }
        };
        selectType(customer_name, customer, pay_type, time_start, time_end, customer_type, baseObserver, pagingInfo);


    }

    private void selectType(String customer_name,
                            String customer,
                            String pay_type,
                            String time_start,
                            String time_end,
                            String customer_type, BaseObserver<CommonDayCollectResp> baseObserver, PagingInfo pagingInfo) {
        CommonDayCollectReq accessToken = new CommonDayCollectReq();
        accessToken.setPagingInfo(pagingInfo);
        accessToken.setTimeStart(time_start);
        accessToken.setTimeEnd(time_end);
        accessToken.setPayType(pay_type);
        accessToken.setDisplayName(customer_name);
        accessToken.setLoginName(customer);
        accessToken.setCustomerSysNo(ConstantUtils.SYS_NO);

        QueryReService.getInstance(context)
                .getCommonDayCollectList(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);
    }
}
