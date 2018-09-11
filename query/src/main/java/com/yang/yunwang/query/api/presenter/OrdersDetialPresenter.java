package com.yang.yunwang.query.api.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.QueryReService;
import com.yang.yunwang.query.api.bean.orderprint.Model;
import com.yang.yunwang.query.api.bean.orderprint.OrderDetialReq;
import com.yang.yunwang.query.api.bean.orderprint.OrdersDetialResp;
import com.yang.yunwang.query.api.bean.orderprint.PagingInfo;
import com.yang.yunwang.query.api.contract.OrdersDetialContract;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/9.
 */

public class OrdersDetialPresenter implements OrdersDetialContract.Presenter {
    private final OrdersDetialContract.View view;
    private final Context context;
    private long page = 0;
    private long total_count;
    private int items_count;
    private ProgressDialog progressDialog;
    private String code;
    private String order_start_time;
    private String order_end_time;
    private ArrayList<JSONObject> pList;
    private final int page_size = 20;
    private final String loginName;
    private final String displayName;
    private final String payType;
    private OrdersDetialResp bean;

    public OrdersDetialPresenter(OrdersDetialContract.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        bean = new OrdersDetialResp();
        bean.setModel(new ArrayList<Model>());
        progressDialog = new ProgressDialog(context);
        pList = new ArrayList<>();
        progressDialog.setCanceledOnTouchOutside(false);
        Intent intent = view.getIntentInstance();
        code = intent.getStringExtra("out_trade_no");
        order_start_time = intent.getStringExtra("Time_Start");
        order_end_time = intent.getStringExtra("Time_end");
        loginName = intent.getStringExtra("LoginName");
        displayName = intent.getStringExtra("DisplayName");
        payType = intent.getStringExtra("Pay_Type");
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
        searchOrders(code,
                order_start_time, order_end_time,
                page, page_size, true, false);
    }

    @Override
    public void loadMore() {
        if( (view.getRecItmesCount() - 2 < total_count)&&(total_count>page_size)) {
            final int items_count = view.getRecItmesCount();
            if (items_count / page_size < page + 1) {
                searchOrders(code,
                        order_start_time, order_end_time,
                        page, page_size, false, false);
            } else {
                page++;
                searchOrders(code,
                        order_start_time, order_end_time,
                        page, page_size, false, false);
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
        String code = intent.getStringExtra("refund_code");
        searchOrders(code,
                order_start_time, order_end_time,
                page, page_size, false, true);
    }

    /**
     * code:订单号
     * customer_no:商户主键
     * sys_no：员工主键
     * order_start_time：订单交易开始时间
     * order_end_time：订单交易结束时间
     * refund_start_time：退款开始时间
     * refund_end_time：退款结束时间
     * transaction_id：微信订单号
     * pay_type：订单类型
     * page_number：当前页数索引
     * page_size：每页行数
     * sort_field：排序字段
     * sort_order：0 倒序 1 正序
     * 请求结果值：Total_fee:总金额,Cash_fee:现金,refund_fee退款金额
     */
    private void searchOrders(String code,
                              String order_start_time, String order_end_time,
                              long page_number, final long page_size,
                              final boolean isInit, final boolean isRefresh) {

        OrderDetialReq accessToken = new OrderDetialReq();
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPageNumber(page_number);
        pagingInfo.setPageSize(page_size);
        accessToken.setPagingInfo(pagingInfo);

        accessToken.setOutTradeNo(code);
        accessToken.setPayType(payType);
        accessToken.setTimeStart(order_start_time);
        accessToken.setTimeEnd(order_end_time);


        BaseObserver<OrdersDetialResp> resp = new BaseObserver<OrdersDetialResp>(context) {
            @Override
            protected void doOnNext(OrdersDetialResp value) {
                if (value.getModel().size() > 0) {
                    KLog.i(value.getModel().size());
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

        if (TextUtils.equals(ConstantUtils.NEW_TYPE, "3")) {
            accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
            accessToken.setCustomer(displayName);
            QueryReService.getInstance(context)
                    .getOrdersDetialStaff(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(resp);
        } else {
            accessToken.setCustomerSysNo(ConstantUtils.SYS_NO);
            accessToken.setDisplayName(displayName);
            accessToken.setLoginName(loginName);
            QueryReService.getInstance(context)
                    .getOrdersDetialCus(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(resp);

        }

    }
}
