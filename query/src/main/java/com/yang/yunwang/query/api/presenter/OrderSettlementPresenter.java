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
import com.yang.yunwang.query.api.bean.ordersettel.Model;
import com.yang.yunwang.query.api.bean.ordersettel.OrderSettleReq;
import com.yang.yunwang.query.api.bean.ordersettel.OrderSettleResp;
import com.yang.yunwang.query.api.bean.ordersettel.PagingInfo;
import com.yang.yunwang.query.api.contract.OrderSettlementContract;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 */

public class OrderSettlementPresenter implements OrderSettlementContract.Presenter {
    private final OrderSettlementContract.View view;
    private final Context context;
    private OrderSettleResp bean;
    private ProgressDialog progressDialog;
    private String code;
    private String start_time;
    private String end_time;
    private String customer;
    private String customer_name;
    private String pay_type;
    private String staff_id;
    private long total_count;
    private long page = 0;
    private final long page_size = 20;

    public OrderSettlementPresenter(OrderSettlementContract.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        bean = new OrderSettleResp();
        bean.setModel(new ArrayList<Model>());
        Intent intent = view.getIntentInstance();
        code = intent.getStringExtra("order_code");
        staff_id = intent.getStringExtra("staff_id");
        start_time = intent.getStringExtra("order_start_time");
        end_time = intent.getStringExtra("order_end_time");
        customer = intent.getStringExtra("order_search_customer");
        customer_name = intent.getStringExtra("orders_search_customer_name");
        pay_type = intent.getStringExtra("orders_search_pay_type");
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
        requestSettlement(code, customer, customer_name, pay_type, start_time, end_time, page, page_size, true, false);
    }

    @Override
    public void loadMore() {
        if( (view.getRecItmesCount() - 2 < total_count)&&(total_count>page_size)) {
            final int items_count = view.getRecItmesCount();
            if (items_count / page_size < page + 1) {
                requestSettlement(code, customer, customer_name, pay_type, start_time, end_time, page, page_size, false, false);
            } else {
                page++;
                requestSettlement(code, customer, customer_name, pay_type, start_time, end_time, page, page_size, false, false);
            }

        } else {
            view.loadMoreComplete(false);
            Toast.makeText(context, "已经加载全部数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refresh() {
        page = 0;

        requestSettlement(code, customer, customer_name, pay_type, start_time, end_time, page, page_size, false, true);
    }

    private void requestSettlement(String code, String customer, String customer_name, String pay_type,
                                   String time_start, String time_end,
                                   long page_number, long page_size,
                                   final boolean isInit, final boolean isRefresh) {
        OrderSettleReq accessToken = new OrderSettleReq();
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPageNumber(page_number);
        pagingInfo.setPageSize(page_size);
        accessToken.setPayType(pay_type);
        accessToken.setTimeStart(time_start);
        accessToken.setTimeEnd(time_end);
        accessToken.setPagingInfo(pagingInfo);
        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        if (staff_id != null && !staff_id.equals("")) {
            accessToken.setSystemUserSysNo(staff_id);
            accessToken.setOutTradeNo(code);
            accessToken.setCustomer(customer);
            accessToken.setCustomerName(customer_name);

        } else {
            if (customer_type != null && !customer_type.equals("")) {
                //  商户/服务商角色
                switch (customer_type) {
                    case "0":
                        accessToken.setCustomersTopSysNo(ConstantUtils.SYS_NO);
                        accessToken.setOutTradeNo(code);
                        accessToken.setCustomer(customer);
                        accessToken.setCustomerName(customer_name);

                        break;
                    case "1":
                        accessToken.setCustomerSysNo(ConstantUtils.SYS_NO);
                        accessToken.setOutTradeNo(code);

                        accessToken.setLoginName(customer);
                        accessToken.setDisplayName(customer_name);

                        break;
                }
            } else {
                //  员工角色
                String staff_type = ConstantUtils.STAFF_TYPE;
                switch (staff_type) {
                    case "0":
                        //  服务商员工   SystemUserTopSysNo正式接口有改动
                        accessToken.setCustomersTopSysNo(ConstantUtils.HIGHER_SYS_NO);
                        accessToken.setSystemUserTopSysNo(ConstantUtils.SYS_NO);
                        accessToken.setOutTradeNo(code);
                        accessToken.setCustomer(customer);
                        accessToken.setCustomerName(customer_name);

                        break;
                    case "1":
                        //  商户员工
                        accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
                        accessToken.setOutTradeNo(code);
                        accessToken.setCustomer(customer);
                        accessToken.setCustomerName(customer_name);

                        break;
                }
            }
        }
        BaseObserver<OrderSettleResp> observer = new BaseObserver<OrderSettleResp>(context) {
            @Override
            protected void doOnNext(OrderSettleResp value) {
                if (value.getModel().size() > 0) {
                    if (isInit) {
                        bean = value;
                        view.setAdapter(bean, staff_id);
                        total_count = value.getTotalCount();
                        progressDialog.dismiss();
                    } else if (isRefresh) {
                        bean = value;
                        view.setAdapter(bean, staff_id);
                        view.refreshComplete();
                        view.dataNotifyChanged();
                    } else {
                        ArrayList tempList=new ArrayList();
                        tempList.addAll(bean.getModel());
                        tempList.addAll(value.getModel());
                        bean.getModel().clear();
                        bean.setModel(tempList);
                        view.setAdapter(bean,staff_id);
                        view.dataNotifyChanged();
                        view.loadMoreComplete(true);
                    }
                } else {
                    view.setAdapter(bean, staff_id);
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
                view.setAdapter(bean, staff_id);
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
        QueryReService.getInstance(context)
                .getSettlementList(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
