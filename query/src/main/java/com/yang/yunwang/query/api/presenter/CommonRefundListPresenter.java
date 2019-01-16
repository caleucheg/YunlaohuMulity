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
import com.yang.yunwang.query.api.bean.commonrefund.CommonRefundReq;
import com.yang.yunwang.query.api.bean.commonrefund.CommonRefundResp;
import com.yang.yunwang.query.api.bean.commonrefund.Model;
import com.yang.yunwang.query.api.bean.commonrefund.PagingInfo;
import com.yang.yunwang.query.api.contract.CommonRefundListContract;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/11.
 */

public class CommonRefundListPresenter implements CommonRefundListContract.Presenter {
    private final CommonRefundListContract.View view;
    private final long page_size = 20;
    private final String customerID;
    private String customersTopSysNo;
    private String customerSysNo;
    private String systemUserSysNo;
    private String shop_id;
    private boolean is_top_rate = false;
    private Context context;

    private CommonRefundResp bean;
    private ProgressDialog progressDialog;
    private String code;
    private String start_time;
    private String end_time;
    private String customer;
    private String customer_name;
    private String pay_type;
    private String staff_id;//用于区分是否由员工列表进入订单查询
    private long total_count;
    private long page = 0;

    public CommonRefundListPresenter(CommonRefundListContract.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        bean = new CommonRefundResp();
        bean.setModel(new ArrayList<Model>());
        Intent intent = view.getIntentInstance();
        staff_id = intent.getStringExtra("staff_id");
        shop_id = intent.getStringExtra("shop_id");
        KLog.i(shop_id);
        customerID = intent.getStringExtra("customer");
        code = intent.getStringExtra("order_code");
        start_time = intent.getStringExtra("order_start_time");
        end_time = intent.getStringExtra("order_end_time");
        customer = intent.getStringExtra("order_search_customer");
        customer_name = intent.getStringExtra("orders_search_customer_name");
        pay_type = intent.getStringExtra("orders_search_pay_type");
        customersTopSysNo = intent.getStringExtra("CustomersTopSysNo");
        customerSysNo = intent.getStringExtra("CustomerSysNo");
        systemUserSysNo = intent.getStringExtra("SystemUserSysNo");
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

        searchOrders(ConstantUtils.SYS_NO, code, customer_name, customer, pay_type, start_time, end_time, page, page_size, true, false);
    }

    @Override
    public void loadMore() {
        if( (view.getRecItmesCount() < total_count) &&(total_count>page_size)){
            Intent intent = view.getIntentInstance();
            String code = intent.getStringExtra("order_code");
            String start_time = intent.getStringExtra("order_start_time");
            String end_time = intent.getStringExtra("order_end_time");
            String customer = intent.getStringExtra("order_search_customer");
            String customer_name = intent.getStringExtra("orders_search_customer_name");
            String pay_type = intent.getStringExtra("orders_search_pay_type");
            final int items_count = view.getRecItmesCount();
            //分页处理，由于服务端是根据web版本的页数来处理分页，不是根据手机显示条数来处理，所以这里做了一下处理。
            // 防止出现在长时间停留此页面时，若没有退出本界面直接上拉加载本页，本页面条数不满20条，需要根据页面页数（计算得出）加载剩余几条，使当前页面条数满20条。
            //再次加载时加载按照正常加载20条的规律加载
            if (items_count / page_size < page + 1) {
                //加载当前页
                searchOrders(ConstantUtils.SYS_NO, code, customer_name, customer, pay_type, start_time, end_time, page, page_size, false, false);
            } else {
                //加载下一页
                page++;
                searchOrders(ConstantUtils.SYS_NO, code, customer_name, customer, pay_type, start_time, end_time, page, page_size, false, false);
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
        searchOrders(ConstantUtils.SYS_NO, code, customer_name, customer, pay_type, start_time, end_time, page, page_size, false, true);
    }

    /**
     * sys_no:主键
     * customer_name:商户用户名
     * customer：商户名称
     * pay_type:支付类型 微信/支付宝
     * time_start:订单起始时间
     * time_end:订单结束时间
     */
    private void searchOrders(String sys_no, String code, String customer_name,
                              String customer, String pay_type, String time_start,
                              String time_end, long page_number, final long page_size,
                              final boolean isInit, final boolean isRefresh) {
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPageNumber(page_number);
        pagingInfo.setPageSize(page_size);
        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        CommonRefundReq req = new CommonRefundReq();
        req.setPagingInfo(pagingInfo);
        BaseObserver<CommonRefundResp> baseObserver = new BaseObserver<CommonRefundResp>(context) {
            @Override
            protected void doOnNext(CommonRefundResp value) {
                if (value.getModel().size() > 0) {
                    if (isInit) {
                        bean = value;
                        view.setAdapter(bean);
                        total_count = value.getTotalCount();
                        progressDialog.dismiss();
                    } else if (isRefresh) {
                        bean = value;
                        view.setAdapter(bean);
                        total_count = value.getTotalCount();
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
        selectType(sys_no, code, customer_name, customer, pay_type, time_start, time_end, customer_type, req, baseObserver);

    }

    private void selectType(String sys_no,
                            String code,
                            String customer_name,
                            String customer,
                            String pay_type,
                            String time_start,
                            String time_end,
                            String customer_type, CommonRefundReq params, BaseObserver<CommonRefundResp> baseObserver) {


        if (!TextUtils.isEmpty(customersTopSysNo)) {
            params.setCustomersTopSysNo(customersTopSysNo);
        } else if (!TextUtils.isEmpty(customerSysNo)) {
            params.setCustomerSysNo(customerSysNo);
        } else if (!TextUtils.isEmpty(systemUserSysNo)) {
            params.setSystemUserSysNo(systemUserSysNo);
        }
        if (!TextUtils.isEmpty(customer)) {
            params.setCustomer(customer);
        }
        if (!TextUtils.isEmpty(customer_name)) {
            params.setCustomerName(customer_name);
        }
        params.setPayType(pay_type);
        params.setOutTradeNo(code);
        params.setCreateTimeStart(time_start);
        params.setCreateTimeEnd(time_end);
        QueryReService.getInstance(context)
                .getCommonRefundList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);


    }
}
