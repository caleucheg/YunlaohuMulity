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
import com.yang.yunwang.query.api.bean.ordersearch.Model;
import com.yang.yunwang.query.api.bean.ordersearch.OrderSearchReq;
import com.yang.yunwang.query.api.bean.ordersearch.OrderSearchResp;
import com.yang.yunwang.query.api.bean.ordersearch.PagingInfo;
import com.yang.yunwang.query.api.contract.OrderSearchContract;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 */
public class OrderSearchPresenter implements OrderSearchContract.Presenter {
    private final OrderSearchContract.View view;
    private final Context context;

    private final int page_size = 20;
    private ProgressDialog progressDialog;
    private String code;
    private String start_time;
    private String end_time;
    private String customer;
    private String customer_name;
    private String pay_type;
    private String staff_id;//用于区分是否由员工列表进入订单查询
    private Long total_count;
    private int page = 0;
    private OrderSearchResp bean;

    public OrderSearchPresenter(Context context, OrderSearchContract.View view) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        bean=new OrderSearchResp();
        bean.setModel(new ArrayList<Model>());
        Intent intent = view.getIntentInstance();
        staff_id = intent.getStringExtra("staff_id");
        code = intent.getStringExtra("order_code");
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
        searchOrders(ConstantUtils.SYS_NO, code, customer_name, customer, pay_type, start_time, end_time, page, page_size,true,false);
    }

    @Override
    public void loadMore() {
        if (view.getRecItmesCount() - 2 < total_count) {
            Intent intent = view.getIntentInstance();
            String code = intent.getStringExtra("order_code");
            String start_time = intent.getStringExtra("order_start_time");
            String end_time = intent.getStringExtra("order_end_time");
            String customer = intent.getStringExtra("order_search_customer");
            String customer_name = intent.getStringExtra("orders_search_customer_name");
            String pay_type = intent.getStringExtra("orders_search_pay_type");
            final int items_count = view.getRecItmesCount();
            if (items_count / page_size < page + 1) {
                //加载当前页
                searchOrders(ConstantUtils.SYS_NO, code, customer_name, customer, pay_type, start_time, end_time, page, page_size,false,false);
            } else {
                //加载下一页
                page++;
                searchOrders(ConstantUtils.SYS_NO, code, customer_name, customer, pay_type, start_time, end_time, page, page_size,false,false);
            }
        } else {
            view.loadMoreComplete();
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
        searchOrders(ConstantUtils.SYS_NO, code, customer_name, customer, pay_type, start_time, end_time, page, page_size,false,true);
    }

    private void searchOrders(String sys_no, final String code, String customer_name,
                              String customer, String pay_type, String time_start,
                              String time_end, long page_number, final long page_size,
                              final boolean isFirstPage, final boolean isRefresh) {
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPageSize(page_size);
        pagingInfo.setPageNumber(page_number);
        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        BaseObserver<OrderSearchResp> searchRespBaseObserver = new BaseObserver<OrderSearchResp>(context) {
            @Override
            protected void doOnNext(OrderSearchResp value) {
                if (value.getModel().size() > 0) {
                    if (isFirstPage) {
                        bean = value;
                        view.setAdapter(bean);
                        total_count=value.getTotalCount();
                        progressDialog.dismiss();
                    } else if (isRefresh) {
                        bean = value;
                        view.setAdapter(bean);
                        view.refreshComplete();
                        view.dataNotifyChanged();
                    } else {
                        KLog.i(bean.getModel().size());
                        bean.getModel().addAll(value.getModel());
                        KLog.i(bean.getModel().size());
                        view.dataNotifyChanged();
                        view.loadMoreComplete();
                    }
                } else {
                    view.setAdapter(bean);
                    if (isFirstPage && progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
                    view.dataNotifyChanged();
                    view.loadMoreComplete();
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                Toast.makeText(context,"未找到数据,请重试",Toast.LENGTH_SHORT).show();
                view.setAdapter(bean);
                if (isFirstPage && progressDialog != null) {
                    progressDialog.dismiss();
                }
                if (isRefresh) {
                    view.refreshComplete();
                    view.dataNotifyChanged();
                }
                view.dataNotifyChanged();
                view.loadMoreComplete();
            }
        };
        OrderSearchReq accessToken = new OrderSearchReq();
        accessToken.setPagingInfo(pagingInfo);
        accessToken.setPayType(pay_type);
        accessToken.setTimeStart(time_start);
        accessToken.setTimeEnd(time_end);
        accessToken.setOutTradeNo(code);
        if (staff_id != null && !staff_id.equals("")) {
            accessToken.setSystemUserSysNo(staff_id);
            accessToken.setCustomerName(customer_name);
            accessToken.setCustomer(customer);
            QueryReService.getInstance(context)
                    .getStaffOrders(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(searchRespBaseObserver);
        } else {
            if (customer_type != null && !customer_type.equals("")) {
                //  商户/服务商角色
                switch (customer_type) {
                    case "0":
                        accessToken.setCustomersTopSysNo(sys_no);
                        accessToken.setCustomerName(customer_name);
                        accessToken.setCustomer(customer);
                        QueryReService.getInstance(context)
                                .getCustomerOrders(accessToken)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(searchRespBaseObserver);
                        break;
                    case "1":
                        accessToken.setCustomerSysNo(sys_no);
                        accessToken.setDisplayName(customer_name);
                        accessToken.setLoginName(customer);
                        QueryReService.getInstance(context)
                                .getMerchOrders(accessToken)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(searchRespBaseObserver);
                        break;
                }
            } else {
                //  员工角色
                String staff_type = ConstantUtils.STAFF_TYPE;
                switch (staff_type) {
                    case "0":
                        //  服务商员工   SystemUserTopSysNo正式接口有改动
                        accessToken.setCustomersTopSysNo(ConstantUtils.HIGHER_SYS_NO);
                        accessToken.setSystemUserTopSysNo(sys_no);
                        accessToken.setCustomerName(customer_name);
                        accessToken.setCustomer(customer);
                        QueryReService.getInstance(context)
                                .getCustomerUserOrders(accessToken)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(searchRespBaseObserver);
                        break;
                    case "1":
                        accessToken.setSystemUserSysNo(sys_no);
                        accessToken.setCustomerName(customer_name);
                        accessToken.setCustomer(customer);
                        QueryReService.getInstance(context)
                                .getStaffOrders(accessToken)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(searchRespBaseObserver);
                        break;
                }
            }
        }
    }
}
