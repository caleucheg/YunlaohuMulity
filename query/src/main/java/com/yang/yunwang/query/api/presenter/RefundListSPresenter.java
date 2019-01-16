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
import com.yang.yunwang.query.api.bean.refundsearchs.Model;
import com.yang.yunwang.query.api.bean.refundsearchs.PagingInfo;
import com.yang.yunwang.query.api.bean.refundsearchs.RefundListSReq;
import com.yang.yunwang.query.api.bean.refundsearchs.RefundListSResp;
import com.yang.yunwang.query.api.contract.RefundListSContract;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/6.
 */

public class RefundListSPresenter implements RefundListSContract.Presenter {
    private final Context context;
    private final RefundListSContract.View view;
    private int page = 0;
    private long total_count;
    private int items_count;
    private ProgressDialog progressDialog;
    private String code;
    private String order_start_time;
    private String order_end_time;
    private String refund_start_time;
    private String refund_end_time;
    private final int page_size = 20;
    private final String pay_type;
    private RefundListSResp bean;

    public RefundListSPresenter(RefundListSContract.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        bean = new RefundListSResp();
        bean.setModel(new ArrayList<Model>());
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        Intent intent = view.getIntentInstance();
        code = intent.getStringExtra("refund_code");
        order_start_time = intent.getStringExtra("order_start_time");
        order_end_time = intent.getStringExtra("order_end_time");
        refund_start_time = intent.getStringExtra("refund_start_time");
        refund_end_time = intent.getStringExtra("refund_end_time");
        pay_type = intent.getStringExtra("pay_type");
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
        searchOrders(code, ConstantUtils.HIGHER_SYS_NO, ConstantUtils.SYS_NO,
                order_start_time, order_end_time,
                refund_start_time, refund_end_time,
                pay_type, page, page_size, true,false);
    }

    @Override
    public void loadMore() {
        if ((view.getRecItmesCount() - 2 < total_count)&&(total_count>page_size)) {
            final int items_count = view.getRecItmesCount();
            KLog.i(view.getRecItmesCount());
            KLog.i(items_count / page_size < page + 1);
            if (items_count / page_size < page + 1) {
                searchOrders(code, ConstantUtils.HIGHER_SYS_NO, ConstantUtils.SYS_NO,
                        order_start_time, order_end_time,
                        refund_start_time, refund_end_time,
                        pay_type, page, page_size,false,false);
            } else {
                page++;
                searchOrders(code, ConstantUtils.HIGHER_SYS_NO, ConstantUtils.SYS_NO,
                        order_start_time, order_end_time,
                        refund_start_time, refund_end_time,
                        pay_type, page, page_size,false,false);
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
        String order_start_time = intent.getStringExtra("order_start_time");
        String order_end_time = intent.getStringExtra("order_end_time");
        String refund_start_time = intent.getStringExtra("refund_start_time");
        String refund_end_time = intent.getStringExtra("refund_end_time");
        String pay_type = intent.getStringExtra("pay_type");

        searchOrders(code, ConstantUtils.HIGHER_SYS_NO, ConstantUtils.SYS_NO,
                order_start_time, order_end_time,
                refund_start_time, refund_end_time,
                pay_type, page, page_size,false,true);
    }

    private void searchOrders(String code, String customer_no, String sys_no,
                              String order_start_time, String order_end_time,
                              String refund_start_time, String refund_end_time,
                              String pay_type,
                              long page_number, final long page_size,
                              final boolean isInit, final boolean isRefresh) {


        RefundListSReq accessToken = new RefundListSReq();
        accessToken.setOutTradeNo(code);
        accessToken.setCustomerSysNo(customer_no);
        accessToken.setSystemUserSysNo(sys_no);
        accessToken.setCreateTimeEnd(refund_end_time);
        accessToken.setCreateTimeStart(refund_start_time);
        accessToken.setPayType(pay_type);
        if (!TextUtils.isEmpty(order_start_time)){
            accessToken.setTimeStart(order_start_time);
        }
        if (!TextUtils.isEmpty(order_end_time)){
            accessToken.setTimeEnd(order_end_time);
        }
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPageSize(page_size);
        pagingInfo.setPageNumber(page_number);
        accessToken.setPagingInfo(pagingInfo);
        QueryReService.getInstance(context)
                .getRefundListS(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<RefundListSResp>(context) {
                    @Override
                    protected void doOnNext(RefundListSResp value) {
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
                });

    }


}
