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
import com.yang.yunwang.query.api.bean.commonsearch.CommonOrdersReq;
import com.yang.yunwang.query.api.bean.commonsearch.CommonOrdersResp;
import com.yang.yunwang.query.api.bean.commonsearch.Model;
import com.yang.yunwang.query.api.bean.commonsearch.PagingInfo;
import com.yang.yunwang.query.api.contract.CommonListContract;
import com.yang.yunwang.query.view.order.list.CommonListActivity;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/10.
 */

public class CommonListPresenter implements CommonListContract.Presenter {
    private final CommonListContract.View view;
    private final long page_size = 20;
    private final boolean merchStaff;
    private final boolean isFromMerch;
    private final boolean isMerchHome;
    private final boolean isUserRate;
    private final boolean isSettlement;
    private final String customerID;
    private final String SHOP_id;
    private String shop_id;
    private boolean is_top_rate = false;
    private Context context;
    private CommonOrdersResp bean;
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

    public CommonListPresenter(CommonListContract.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        bean = new CommonOrdersResp();
        bean.setModel(new ArrayList<Model>());
        Intent intent = view.getIntentInstance();
        staff_id = intent.getStringExtra("staff_id");
        shop_id = intent.getStringExtra("shop_id");
        SHOP_id = intent.getStringExtra("SHOP_id");
        KLog.i(shop_id);
        customerID = intent.getStringExtra("customer");
        code = intent.getStringExtra("order_code");
        start_time = intent.getStringExtra("order_start_time");
        end_time = intent.getStringExtra("order_end_time");
        customer = intent.getStringExtra("order_search_customer");
        customer_name = intent.getStringExtra("orders_search_customer_name");
        pay_type = intent.getStringExtra("orders_search_pay_type");
        is_top_rate = intent.getBooleanExtra("top_rate", false);
        merchStaff = intent.getBooleanExtra("merch_staff", false);
        isFromMerch = intent.getBooleanExtra("from_merch", false);
        isMerchHome = intent.getBooleanExtra("from_home_merch", false);
        isUserRate = intent.getBooleanExtra("isUserRate", false);
        isSettlement = intent.getBooleanExtra("settlement", false);
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

        KLog.i((view.getRecItmesCount() - 2 < total_count)&&(total_count>page_size));
        if ((view.getRecItmesCount() - 2 < total_count)&&(total_count>page_size)) {
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
        CommonOrdersReq commonOrdersReq = new CommonOrdersReq();
        commonOrdersReq.setPagingInfo(pagingInfo);
        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        BaseObserver<CommonOrdersResp> baseObserver = new BaseObserver<CommonOrdersResp>(context) {
            @Override
            protected void doOnNext(CommonOrdersResp value) {
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
//                        bean.getModel().addAll(value.getModel());

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
//                KLog.i("111111111111111111111111");
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
        selectType(sys_no, code, customer_name, customer, pay_type, time_start, time_end, customer_type, baseObserver, commonOrdersReq);

    }

    private void selectType(String sys_no,
                            String code,
                            String customer_name,
                            String customer,
                            String pay_type,
                            String time_start,
                            String time_end,
                            String customer_type, BaseObserver<CommonOrdersResp> baseObserver, CommonOrdersReq params) {
        if (isUserRate) {
            params.setTimeStart(time_start);
            params.setTimeEnd(time_end);
            params.setPayType(pay_type);
            params.setCustomerName(customer_name);
            params.setCustomer(customer);
            params.setSystemUserSysNo(staff_id);
            if (isSettlement) {
                QueryReService.getInstance(context)
                        .getStaffRateSettlement(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(baseObserver);
            } else {
                QueryReService.getInstance(context)
                        .getStaffRate(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(baseObserver);

            }
            view.setRateVisF();
            KLog.i("员工--费率");
        } else if (isMerchHome) {
            if (TextUtils.equals(ConstantUtils.NEW_TYPE, "1")) {
                params.setLoginName(customer);
                params.setDisplayName(customer_name);
            } else {
                params.setCustomerName(customer_name);
                params.setCustomer(customer);
            }
            params.setTimeStart(time_start);
            params.setTimeEnd(time_end);
            params.setPayType(pay_type);
            params.setOutTradeNo(code);
            if (shop_id == null) {
                shop_id = ConstantUtils.SYS_NO;
            }
            params.setCustomerSysNo(shop_id);
            if (isSettlement) {
                ((CommonListActivity) view).setChangeStaffMerchTitle(true);
                QueryReService.getInstance(context)
                        .getCusRateSettlement(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(baseObserver);
            } else {
                QueryReService.getInstance(context)
                        .getCusRate(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(baseObserver);
            }
            KLog.i("商户--费率");
        } else if (staff_id != null && !staff_id.equals("")) {
//            params.put("SystemUserTopSysNo", staff_id);
            params.setTimeStart(time_start);
            params.setTimeEnd(time_end);
            params.setPayType(pay_type);
            params.setOutTradeNo(code);
            KLog.i(merchStaff + "--------merchStaff");
            if (merchStaff) {
                if (is_top_rate) {
                    params.setSystemUserTopSysNo(staff_id);
                    params.setCustomersTopSysNo(ConstantUtils.HIGHER_SYS_NO);
                    params.setCustomer(shop_id);
                    if (isSettlement) {
                        QueryReService.getInstance(context)
                                .getCusTopRateSettlement(params)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(baseObserver);
                    } else {
                        QueryReService.getInstance(context)
                                .getCusTopRate(params)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(baseObserver);
                    }
                } else {
                    //服务商员工下-商户-费率
                    if (isSettlement) {
                        ((CommonListActivity) view).setChangeStaffMerchTitle(true);
                        KLog.i(SHOP_id + "SHOP_id");
                        params.setCustomerSysNo(shop_id);
                        params.setCustomerName(customer_name);
                        params.setCustomer(customer);

                        QueryReService.getInstance(context)
                                .getCusRateSettlement(params)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(baseObserver);
                    } else {
                        if (!TextUtils.isEmpty(customerID)) {
                            params.setCustomer(customerID);
                        }
                        KLog.i(customerID);
                        params.setCustomersTopSysNo(ConstantUtils.HIGHER_SYS_NO);
                        params.setSystemUserTopSysNo(sys_no);

                        QueryReService.getInstance(context)
                                .getCusTopRate(params)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(baseObserver);
                    }
                }
                KLog.i("服务商员工下-商户-费率");
            } else if (isFromMerch) {
                params.setSystemUserSysNo(staff_id);
                //ddd
//                params.setCustomer(shop_id);
                KLog.i("商户-员工-费率");

                if (isSettlement) {
                    ((CommonListActivity) view).setChangeStaffMerchTitle(true);
                    QueryReService.getInstance(context)
                            .getStaffRateSettlement(params)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(baseObserver);
                } else {
                    view.setRateVisF();
                    QueryReService.getInstance(context)
                            .getStaffRate(params)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(baseObserver);
                }
            } else {
                KLog.i("服务商角色/商户-员工-商户--上级/费率");
                params.setSystemUserTopSysNo(staff_id);
                params.setCustomerName(customer_name);
                params.setCustomer(customer);
                params.setCustomersTopSysNo(sys_no);
                if (isSettlement) {
                    QueryReService.getInstance(context)
                            .getCusTopRateSettlement(params)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(baseObserver);
                } else {
                    QueryReService.getInstance(context)
                            .getCusTopRate(params)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(baseObserver);
                }
            }
        } else {
            if (customer_type != null && !customer_type.equals("")) {
                //  商户/服务商角色
                switch (customer_type) {
                    case "0":
                        params.setCustomerSysNo(shop_id);
                        params.setOutTradeNo(code);
                        params.setCustomerName(customer_name);
                        params.setCustomer(customer);
                        params.setPayType(pay_type);
                        params.setTimeStart(time_start);
                        params.setTimeEnd(time_end);

                        if (isSettlement) {
                            if (!is_top_rate) {
                                ((CommonListActivity) view).setChangeStaffMerchTitle(true);
                            }
                            QueryReService.getInstance(context)
                                    .getCusRateSettlement(params)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(baseObserver);
                        } else {

                            QueryReService.getInstance(context)
                                    .getCusRate(params)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(baseObserver);
                        }
                        KLog.i("服务商角色-商户-费率");
                        break;
                    case "1":
                        params.setCustomerSysNo(sys_no);
                        params.setOutTradeNo(code);
                        params.setCustomerName(customer_name);
                        params.setCustomer(customer);
                        params.setPayType(pay_type);
                        params.setTimeStart(time_start);
                        params.setTimeEnd(time_end);
                        QueryReService.getInstance(context)
                                .getCusRateSettlement(params)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(baseObserver);
                        KLog.i("5--费率");
                        break;
                }
            } else {
                //  员工角色
                String staff_type = ConstantUtils.STAFF_TYPE;
                params.setOutTradeNo(code);
                params.setCustomerName(customer_name);
                if (!TextUtils.isEmpty(shop_id)) {
                    params.setCustomer(shop_id);
                } else {
                    params.setCustomer(customer);
                }
                params.setPayType(pay_type);
                params.setTimeStart(time_start);
                params.setTimeEnd(time_end);

                KLog.i(staff_type);
                switch (staff_type) {
                    case "0":
                        //  服务商员工   SystemUserTopSysNo正式接口有改动
                        if (!TextUtils.isEmpty(customerID)) {
                            params.setCustomer(customerID);
                        }
                        KLog.i(customerID);
                        params.setCustomersTopSysNo(ConstantUtils.HIGHER_SYS_NO);
                        params.setSystemUserTopSysNo(sys_no);
                        if (isSettlement) {
                            QueryReService.getInstance(context)
                                    .getCusTopRateSettlement(params)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(baseObserver);
                        } else {
                            QueryReService.getInstance(context)
                                    .getCusTopRate(params)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(baseObserver);
                        }
                        KLog.i("服务商员工--上级/费率");
                        break;
                    case "1":
                        //  商户员工
                        params.setSystemUserSysNo(sys_no);
                        KLog.i("7--费率");
                        QueryReService.getInstance(context)
                                .getStaffRateSettlement(params)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(baseObserver);
                        break;
                }
            }
        }
    }
}
