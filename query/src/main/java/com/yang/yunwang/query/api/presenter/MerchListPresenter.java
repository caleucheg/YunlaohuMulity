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
import com.yang.yunwang.query.api.bean.merchsearch.MerchListReq;
import com.yang.yunwang.query.api.bean.merchsearch.MerchListResp;
import com.yang.yunwang.query.api.bean.merchsearch.Model;
import com.yang.yunwang.query.api.bean.merchsearch.PagingInfo;
import com.yang.yunwang.query.api.contract.MerchListContract;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/12.
 */

public class MerchListPresenter implements MerchListContract.Presenter {
    private final MerchListContract.View view;
    private final Context context;
    private long page_size = 20;
    private boolean fromHome = false;
    private MerchListResp bean;
    private long page = 0;
    private long total_count;
    private ProgressDialog progressDialog;
    private String shop_user;
    private String shop_name;
    private String staff_id;

    public MerchListPresenter(MerchListContract.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        bean = new MerchListResp();
        bean.setModel(new ArrayList<Model>());
        Intent intent = view.getIntentInstance();
        shop_user = intent.getStringExtra("shop_user");
        shop_name = intent.getStringExtra("shop_name");
        staff_id = intent.getStringExtra("staff_id");
        fromHome = intent.getBooleanExtra("from_home", false);
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

        searchShop(shop_user, shop_name, staff_id, page, page_size, true, false);
    }

    @Override
    public void loadMore() {
        KLog.i(view.getRecItmesCount() + "------" + total_count);
        if( (view.getRecItmesCount() - 2 < total_count)&&(total_count>page_size)) {
            final int items_count = view.getRecItmesCount();
            if (items_count / page_size < page + 1) {
                searchShop(shop_user, shop_name, staff_id, page, page_size, false, false);
            } else {
                page++;
                searchShop(shop_user, shop_name, staff_id, page, page_size, false, false);
            }
        } else {
            KLog.i(view.getRecItmesCount() + "------" + total_count);
            view.loadMoreComplete();
            Toast.makeText(context, "已经加载全部数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refresh() {
        page = 0;
        searchShop(shop_user, shop_name, staff_id, page, page_size, false, true);
    }

    private void searchShop(String shop_user, String shop_name, String staff_id, long page_number, final long page_size, final boolean isInit, final boolean isRefresh) {
        MerchListReq map_param = new MerchListReq();
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPageNumber(page_number);
        pagingInfo.setPageSize(page_size);
        map_param.setPagingInfo(pagingInfo);
        map_param.setCustomer(shop_user);
        map_param.setCustomerName(shop_name);
        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        if (staff_id != null && !staff_id.equals("")) {
            map_param.setSystemUserSysNo(staff_id);
        } else {
            if (customer_type != null && !customer_type.equals("")) {
                map_param.setCustomersTopSysNo(ConstantUtils.SYS_NO);
            } else {
                map_param.setSystemUserSysNo(ConstantUtils.SYS_NO);
            }
        }
        BaseObserver<MerchListResp> observer = new BaseObserver<MerchListResp>(context) {
            @Override
            protected void doOnNext(MerchListResp value) {
                if (value.getModel().size() > 0) {
                    if (isInit) {
                        bean = value;
                        view.setAdapter(bean);
                        total_count = value.getTotalCount();
                        progressDialog.dismiss();
                    } else if (isRefresh) {
                        bean = value;
                        total_count = value.getTotalCount();
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
                    view.setAdapter(bean);
                    view.refreshComplete();
                    view.dataNotifyChanged();
                }else {

                    view.dataNotifyChanged();
                    view.loadMoreComplete();
                }
            }
        };
        QueryReService.getInstance(context)
                .getMerchList(map_param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
