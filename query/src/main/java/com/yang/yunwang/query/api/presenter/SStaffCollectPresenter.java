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
import com.yang.yunwang.query.api.bean.susersettle.list.Model;
import com.yang.yunwang.query.api.bean.susersettle.list.PagingInfo;
import com.yang.yunwang.query.api.bean.susersettle.list.SStaffCollectReq;
import com.yang.yunwang.query.api.bean.susersettle.list.SStaffCollectResp;
import com.yang.yunwang.query.api.contract.SStaffCollectContract;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 */

public class SStaffCollectPresenter implements SStaffCollectContract.Presenter {
    private final SStaffCollectContract.View view;
    private final Context context;

    private final long page_size = 20;
    private String phone_num;
    private SStaffCollectResp bean;
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

    public SStaffCollectPresenter(SStaffCollectContract.View view, Context context) {
        this.view=view;
        this.context=context;
        view.setPresenter(this);
        bean = new SStaffCollectResp();
        bean.setModel(new ArrayList<Model>());
        Intent intent = view.getIntentInstance();
        staff_id = intent.getStringExtra("staff_id");
        code = intent.getStringExtra("order_code");
        start_time = intent.getStringExtra("order_start_time");
        end_time = intent.getStringExtra("order_end_time");
        customer = intent.getStringExtra("order_search_customer");
        customer_name = intent.getStringExtra("orders_search_customer_name");
        pay_type = intent.getStringExtra("orders_search_pay_type");
        phone_num = intent.getStringExtra("edit_staff_phone");
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
        searchOrders(ConstantUtils.SYS_NO, phone_num, customer, customer_name, pay_type, start_time, end_time, page, page_size,true,false);
    }

    @Override
    public void loadMore() {
        if ((view.getRecItmesCount() < total_count)&&(total_count>page_size)) {
            Intent intent = view.getIntentInstance();
            String code = intent.getStringExtra("order_code");
            String start_time = intent.getStringExtra("order_start_time");
            String end_time = intent.getStringExtra("order_end_time");
            String customer = intent.getStringExtra("order_search_customer");
            String customer_name = intent.getStringExtra("orders_search_customer_name");
            String pay_type = intent.getStringExtra("orders_search_pay_type");
            String phone_num = intent.getStringExtra("edit_staff_phone");
            final int items_count = view.getRecItmesCount();

            if (items_count / page_size < page + 1) {
                //加载当前页
                searchOrders(ConstantUtils.SYS_NO, phone_num, customer, customer_name, pay_type, start_time, end_time, page, page_size,false,false);
            } else {
                //加载下一页
                page++;
                searchOrders(ConstantUtils.SYS_NO, phone_num, customer, customer_name, pay_type, start_time, end_time, page, page_size, false,false);
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
        String phone_num = intent.getStringExtra("edit_staff_phone");

        searchOrders(ConstantUtils.SYS_NO, phone_num, customer, customer_name, pay_type, start_time, end_time, page, page_size,false,true);
    }


    /**
     * sys_no:主键
     * customer_name:商户用户名
     * customer：商户名称
     * pay_type:支付类型 微信/支付宝
     * time_start:订单起始时间
     * time_end:订单结束时间
     * 请求结果值：Total_fee:总金额,Cash_fee:现金,refund_fee退款金额
     */
    private void searchOrders(String sys_no, String phoneNumber, String loginName,
                              String displayName, String pay_type, String time_start,
                              String time_end, long page_number, final long page_size,
                              final boolean isInit, final boolean isRefresh) {
        PagingInfo pagingInfo=new PagingInfo();
        pagingInfo.setPageSize(page_size);
        pagingInfo.setPageNumber(page_number);
        SStaffCollectReq params=new SStaffCollectReq();
        params.setCustomersTopSysNo( sys_no);
        params.setPhoneNumber( phoneNumber);
        params.setLoginName( loginName);
        params.setDisplayName( displayName);
        params.setPayType( pay_type);
        params.setTimeStart( time_start);
        params.setTimeEnd( time_end);
        params.setPagingInfo( pagingInfo);
        BaseObserver<SStaffCollectResp> observer = new BaseObserver<SStaffCollectResp>(context) {
            @Override
            protected void doOnNext(SStaffCollectResp value) {
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
                        bean.getModel().addAll(value.getModel());
                        view.dataNotifyChanged();
                        view.loadMoreComplete();
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
                        view.loadMoreComplete();
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
                    view.loadMoreComplete();
                }
            }
        };
        QueryReService.getInstance(context)
                .getSStaffCollectList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
