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
import com.yang.yunwang.query.api.bean.staffsearch.Model;
import com.yang.yunwang.query.api.bean.staffsearch.PagingInfo;
import com.yang.yunwang.query.api.bean.staffsearch.StaffListReq;
import com.yang.yunwang.query.api.bean.staffsearch.StaffListResp;
import com.yang.yunwang.query.api.contract.StaffListContract;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 */

public class StaffListPresenter implements StaffListContract.Presenter {
    private final StaffListContract.View view;
    private final Context context;
    private final long page_size = 20;
    private StaffListResp bean;
    private long page = 0;
    private long total_count;
    private ProgressDialog progressDialog;
    private String staff_customer;
    private String staff_tel;
    private String inner_flag;

    public StaffListPresenter(StaffListContract.View view, Context context) {
        this.view=view;
        this.context=context;
        view.setPresenter(this);
        bean = new StaffListResp();
        bean.setModel(new ArrayList<Model>());
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        Intent intent = view.getIntentInstance();
        staff_customer = intent.getStringExtra("staff_customer");
        staff_tel = intent.getStringExtra("staff_tel");
        inner_flag = intent.getStringExtra("flag");
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


        searchStaff(staff_customer, staff_tel, page, page_size, true, false);
    }

    @Override
    public void loadMore() {

        if ((view.getRecItmesCount() - 2 < total_count) &&(total_count>page_size)){
            final int items_count = view.getRecItmesCount();
            if (items_count / page_size < page + 1) {
                searchStaff(staff_customer, staff_tel, page, page_size, false, false);
            } else {
                page++;
                searchStaff(staff_customer, staff_tel, page, page_size, false, false);
            }
        } else {
            view.loadMoreComplete();
            Toast.makeText(context, "已经加载全部数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refresh() {
        page = 0;
        searchStaff(staff_customer, staff_tel, page, page_size, false, true);
    }

    private void searchStaff(String staff_customer, String staff_tel, long page, final long page_size, final boolean isInit, final boolean isRefresh) {
        BaseObserver<StaffListResp> observer = new BaseObserver<StaffListResp>(context) {
            @Override
            protected void doOnNext(StaffListResp value) {
                if (value.getModel().size() > 0) {
                    if (isInit) {
                        bean = value;
                        view.setAdapter(bean,inner_flag);
                        total_count = value.getTotalCount();
                        progressDialog.dismiss();
                    } else if (isRefresh) {
                        bean = value;
                        view.setAdapter(bean,inner_flag);
                        view.refreshComplete();
                        view.dataNotifyChanged();
                    } else {
                        bean.getModel().addAll(value.getModel());
                        view.dataNotifyChanged();
                        view.loadMoreComplete();
                    }
                } else {
                    view.setAdapter(bean, inner_flag);
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
                view.setAdapter(bean, inner_flag);
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
        PagingInfo pagingInfo=new PagingInfo();
        pagingInfo.setPageNumber(page);
        pagingInfo.setPageSize(page_size);
        StaffListReq req=new StaffListReq();
        req.setPagingInfo(pagingInfo);
        req.setCustomerServiceSysNo( ConstantUtils.SYS_NO);
        req.setLoginName( staff_customer);
        req.setPhoneNumber( staff_tel);
        if (!TextUtils.isEmpty(ConstantUtils.SYS_NO)) {
            QueryReService.getInstance(context)
                    .getStaffList(req)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            Toast.makeText(context, "输入有误,请返回重新查询.", Toast.LENGTH_SHORT).show();
        }
    }
}
