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
import com.yang.yunwang.query.api.bean.refundsearch.refundlist.Model;
import com.yang.yunwang.query.api.bean.refundsearch.refundlist.PagingInfo;
import com.yang.yunwang.query.api.bean.refundsearch.refundlist.RefundListReq;
import com.yang.yunwang.query.api.bean.refundsearch.refundlist.RefundListResp;
import com.yang.yunwang.query.api.bean.refundsearch.refundrole.Datum;
import com.yang.yunwang.query.api.bean.refundsearch.refundrole.RefundRoleReq;
import com.yang.yunwang.query.api.bean.refundsearch.refundrole.RefundRoleResp;
import com.yang.yunwang.query.api.contract.RefundListContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 */

public class RefundListPresenter implements RefundListContract.Presenter {
    private final RefundListContract.View view;
    private final Context context;
    private boolean hasRole = false;
    private ProgressDialog progressDialog;
    private RefundListResp bean;
    private int page = 0;
    private long total_count;
    private String unrefund_code;
    private String unrefund_start_time;
    private String unrefund_end_time;
    private final int page_size = 20;

    public RefundListPresenter(RefundListContract.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);

        bean = new RefundListResp();
        bean.setModel(new ArrayList<Model>());
        Intent intent = view.getIntentInstance();
        unrefund_code = intent.getStringExtra("unrefund_code");
        unrefund_start_time = intent.getStringExtra("unrefund_start_time");
        unrefund_end_time = intent.getStringExtra("unrefund_end_time");
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void initData() {
        RefundRoleReq accessToken = new RefundRoleReq();
        accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
        QueryReService.getInstance(context)
                .getRefundRole(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<RefundRoleResp>(context) {
                    @Override
                    protected void doOnNext(RefundRoleResp result) {
                        if (result != null && !TextUtils.isEmpty(result.toString())) {
                            KLog.i(result);
                            String code = result.getCode() + "";
                            if (code.equals("0")) {
                                List<Datum> jsonArray = result.getData();
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    hasRole = jsonArray.get(i).getApplicationSysNo() == 1;
                                }
                                initListAfter();
                            } else {
                                //获取权限失败
                                view.onErrorRole();
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        view.onErrorRole();
                    }
                });

    }

    @Override
    public void loadMore() {
        if ((view.getRecItmesCount() - 2 < total_count)&&(total_count>page_size) ){
            final int items_count = view.getRecItmesCount();
            if (items_count / page_size < page + 1) {
                requestData(unrefund_code, unrefund_start_time, unrefund_end_time, page, page_size, false, false);
            } else {
                page++;
                requestData(unrefund_code, unrefund_start_time, unrefund_end_time, page, page_size, false, false);
            }
        } else {
            view.loadMoreComplete(false);
            Toast.makeText(context, "已经加载全部数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refresh() {
        page = 0;
        requestData(unrefund_code, unrefund_start_time, unrefund_end_time, page, page_size, false, true);
    }

    private void requestData(String unrefund_code, String unrefund_start_time, String unrefund_end_time, long page, long page_size, final boolean isInit, final boolean isRefresh) {

        RefundListReq accessToken = new RefundListReq();
        accessToken.setOutTradeNo(unrefund_code);
        accessToken.setSystemUserSysNo(ConstantUtils.SYS_NO);
//        accessToken.setCustomerSysNo(ConstantUtils.HIGHER_SYS_NO);
        accessToken.setTimeEnd(unrefund_end_time);
        accessToken.setTimeStart(unrefund_start_time);
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPageNumber(page);
        pagingInfo.setPageSize(page_size);
        accessToken.setPagingInfo(pagingInfo);

        BaseObserver<RefundListResp> observer = new BaseObserver<RefundListResp>(context) {
            @Override
            protected void doOnNext(RefundListResp value) {
                if (value.getModel().size() > 0) {
                    if (isInit) {
                        bean = value;
                        view.setAdapter(bean, hasRole);
                        total_count = value.getTotalCount();
                        progressDialog.dismiss();
                    } else if (isRefresh) {
                        bean = value;
                        view.setAdapter(bean, hasRole);
                        view.refreshComplete();
                        view.dataNotifyChanged();
                    } else {
                        ArrayList tempList=new ArrayList();
                        tempList.addAll(bean.getModel());
                        tempList.addAll(value.getModel());
                        bean.getModel().clear();
                        bean.setModel(tempList);
                        view.setAdapter(bean,hasRole);
                        view.dataNotifyChanged();
                        view.loadMoreComplete(true);
                    }
                } else {
                    view.setAdapter(bean, hasRole);
                    if (isInit && progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(context, "暂无数据", Toast.LENGTH_SHORT).show();
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
                view.setAdapter(bean, hasRole);
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
                .getUnrefundList(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }


    public void initListAfter() {

        page = 0;
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.show();
        requestData(unrefund_code, unrefund_start_time, unrefund_end_time, page, page_size, true, false);
    }
}
