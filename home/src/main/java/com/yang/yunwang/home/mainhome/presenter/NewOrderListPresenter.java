package com.yang.yunwang.home.mainhome.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.socks.library.KLog;
import com.yang.yunwang.base.busevent.OrderFilterEvent;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.homeservice.HomeREService;
import com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.request.CommonNewOrderReq;
import com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.request.PagingInfo;
import com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.respone.CommonNewOrderResp;
import com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.respone.Data;
import com.yang.yunwang.home.mainhome.contract.NewOrderListContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewOrderListPresenter implements NewOrderListContract.Presenter {
    private final NewOrderListContract.View view;
    private final Context context;
    private final int page_size = 10;
    private final SimpleDateFormat sdf;
    private final SimpleDateFormat sdfStart;
    private Calendar calendar;

    private String code;
    private String start_time;
    private String end_time;
    private String customer;
    private String customer_name;
    private String pay_type;
    //    private String staff_id;//用于区分是否由员工列表进入订单查询
    private Long total_count;
    private int page = 0;
    private CommonNewOrderResp bean;
    private boolean isFromFilter = false;
    private String customerStore;

    public NewOrderListPresenter(Context context, NewOrderListContract.View view) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        bean = new CommonNewOrderResp();
        Data data = new Data();
        data.setModel(new ArrayList<com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.respone.Model>());
        bean.setData(data);

        //TODO change intent Type
        //{"Customer":"15843263300","CustomersTopSysNo":"1","PagingInfo":{"PageNumber":0,"PageSize":20},"Time_end":"2018-12-03 09:07:10","Time_Start":"2018-12-03 00:00:00"}
        calendar = Calendar.getInstance(Locale.CHINA);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(calendar.getTime());
        sdfStart = new SimpleDateFormat("yyyy-MM-dd");

//        staff_id = intent.getStringExtra("staff_id");


    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void initData() {
        calendar = Calendar.getInstance(Locale.CHINA);
        bean.getData().getModel().clear();
        KLog.i(ConstantUtils.Order_order_search + "||" + TextUtils.equals("0", ConstantUtils.NEW_TYPE));
        isFromFilter = false;
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
        start_time = dateStart;
        end_time = dateEnd;
        Intent intent = view.getIntentInstance();
        code = intent.getStringExtra("order_code");
        customer = intent.getStringExtra("order_search_customer");
        customerStore = intent.getStringExtra("order_search_customer");
        customer_name = intent.getStringExtra("orders_search_customer_name");
        pay_type = intent.getStringExtra("orders_search_pay_type");
        boolean isFromArouter = view.getIntentInstance().getBooleanExtra("isFromArouter", false);
        if (ConstantUtils.Order_order_search || TextUtils.equals("0", ConstantUtils.NEW_TYPE) || isFromArouter) {
            view.showDialog();
            searchOrders(false, ConstantUtils.SYS_NO, code, customer_name, customer, pay_type, start_time, end_time, page, page_size, true, false);
        } else {
            Toast.makeText(context, "该用户没有订单查询权限", Toast.LENGTH_SHORT).show();
            view.dismissLoadMore();
        }
    }

    @Override
    public void loadMore() {
        KLog.i(bean.getData().getTotalCount());
        KLog.i(bean.getData().getModel().size());
        if (view.getRecItmesCount() < total_count) {
            final int items_count = view.getRecItmesCount();
            if (items_count / page_size < page + 1) {
                KLog.i("pages____now----");
                //加载当前页
                searchOrders(false, ConstantUtils.SYS_NO, code, customer_name, customer, pay_type, start_time, end_time, page, page_size, false, false);
            } else {
                //加载下一页
                KLog.i("pages____next---");
                page++;
                searchOrders(false, ConstantUtils.SYS_NO, code, customer_name, customer, pay_type, start_time, end_time, page, page_size, false, false);
            }
            KLog.i("pages____" + page);
        } else {
            KLog.i("pages____" + "no");
            view.loadMoreComplete(false);
            Toast.makeText(context, "已经加载全部数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refresh() {
        page = 0;
//        Intent intent = view.getIntentInstance();
//        String code = intent.getStringExtra("order_code");
//        String start_time = intent.getStringExtra("order_start_time");
//        String end_time = intent.getStringExtra("order_end_time");
//        String customer = intent.getStringExtra("order_search_customer");
//        String customer_name = intent.getStringExtra("orders_search_customer_name");
//        String pay_type = intent.getStringExtra("orders_search_pay_type");
//        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
//        String date = sdf.format(calendar.getTime());
//        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
//        start_time = dateStart;
//        end_time = date;
        searchOrders(false, ConstantUtils.SYS_NO, code, customer_name, customer, pay_type, start_time, end_time, page, page_size, false, true);
    }

    @Override
    public void loadMoreN() {
        List<com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.respone.Model> nArr = bean.getData().getModel();
        bean.getData().getModel().addAll(nArr);
        view.dataNotifyChanged();
    }

    @Override
    public void filterOrder(OrderFilterEvent userEvent) {
        page = 0;
        String tradeType = userEvent.getTradeType();
        //TODO changeType
        String orderNum = userEvent.getOrderNum();
        String cusName = userEvent.getCusName();
        String cusLoginName = userEvent.getCusLoginName();
        code = orderNum;
        boolean isFromArouter = view.getIntentInstance().getBooleanExtra("isFromArouter", false);
        if (TextUtils.equals(ConstantUtils.NEW_TYPE, "1") && isFromArouter) {
            customer = customerStore;
            KLog.i(1 + "---" + customer);
        } else {
            customer = cusLoginName;
            KLog.i(2 + "---" + customer);
        }
        customer_name = cusName;
        pay_type = tradeType;
        start_time = userEvent.getStartTime();
        end_time = userEvent.getEndTime();
        isFromFilter = true;
        initDataNew();

    }

    private void initDataNew() {
        KLog.i("showdia");
        boolean isFromArouter = view.getIntentInstance().getBooleanExtra("isFromArouter", false);
        if (ConstantUtils.Order_order_search || TextUtils.equals("0", ConstantUtils.NEW_TYPE) || isFromArouter) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.showDialog();
                }
            }, 200);

            searchOrders(false, ConstantUtils.SYS_NO, code, customer_name, customer, pay_type, start_time, end_time, page, page_size, true, false);
        } else {
            Toast.makeText(context, "该用户没有订单查询权限", Toast.LENGTH_SHORT).show();
            view.dismissLoadMore();
        }
    }

    private void searchOrders(final boolean isBefore, String sys_no, final String code, String customer_name,
                              String customer, String pay_type, String time_start,
                              String time_end, long page_number, final long page_size,
                              final boolean isFirstPage, final boolean isRefresh) {
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPageSize(page_size);
        pagingInfo.setPageNumber(page_number);
        String customer_type = ConstantUtils.NEW_TYPE;
        BaseObserver<CommonNewOrderResp> searchRespBaseObserver = new BaseObserver<CommonNewOrderResp>(context) {
            @Override
            protected void doOnNext(CommonNewOrderResp value) {
                if (value != null) {
                    Gson gson = new Gson();
                    KLog.json(gson.toJson(value));
                    if (value.getData().getModel().size() > 0) {
                        if (isBefore) {
                            KLog.i("before");
                            bean = value;
                            total_count = value.getData().getTotalCount();
                        } else {
                            if (isFirstPage) {
                                KLog.i("first");
                                bean = value;
                                view.setAdapter(bean);
                                total_count = value.getData().getTotalCount();
                                dismissDialog();
                            } else if (isRefresh) {
                                bean = value;
                                view.setAdapter(bean);
                                total_count = value.getData().getTotalCount();
                                view.refreshComplete();
                                view.dataNotifyChanged();
                                dismissDialog();

                            } else {
                                ArrayList tempList = new ArrayList();
                                tempList.addAll(bean.getData().getModel());
                                tempList.addAll(value.getData().getModel());
                                bean.getData().getModel().clear();
                                bean.getData().setModel(tempList);
                                view.setAdapter(bean);
                                view.dataNotifyChanged();
                                view.loadMoreComplete(true);

//                            KLog.i(bean.getData().getModel().size());
//                            bean.getData().getModel().addAll(value.getData().getModel());
//                            KLog.i(bean.getData().getModel().size());
//                            view.loadMoreComplete(true);
//                            view.dataNotifyChanged();

                            }
                            view.noData(false);
                        }

                    } else {
                        view.setAdapter(bean);
                        if (isFirstPage) {
                            dismissDialog();
                        }
//                    Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
                        view.dataNotifyChanged();
                        view.loadMoreComplete(false);
                        view.noData(true);
                    }
                } else {
                    view.setAdapter(bean);
                    if (isFirstPage) {
                        dismissDialog();
                    }
//                    Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
                    view.dataNotifyChanged();
                    view.loadMoreComplete(false);
                    view.noData(true);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                try {
                    Toast.makeText(context, "未找到数据,请重试", Toast.LENGTH_SHORT).show();
                    view.setAdapter(bean);
                    if (isFirstPage) {
                        dismissDialog();
                    }
                    if (isRefresh) {
                        view.refreshComplete();
                        view.dataNotifyChanged();
                    }
                    view.dataNotifyChanged();
                    view.loadMoreComplete(false);
                    view.noData(true);
                } catch (Exception e) {
                    KLog.i(e);
                }
            }
        };
        CommonNewOrderReq accessToken = new CommonNewOrderReq();
        accessToken.setPagingInfo(pagingInfo);
        accessToken.setRemarks(pay_type);
        accessToken.setTimeStart(time_start);
        accessToken.setTimeEnd(time_end);
        if (!TextUtils.isEmpty(code)) {
            accessToken.setOutTradeNo(code);
        }
        boolean isFromArouter = view.getIntentInstance().getBooleanExtra("isFromArouter", false);
        String shopId = view.getIntentInstance().getStringExtra("SHOP_id");
        if (isFromArouter) {
            if (customer_type != null && !customer_type.equals("")) {
                //  商户/服务商角色
                switch (customer_type) {
                    case "0":
                        if (isFromFilter) {
                            KLog.i(shopId);
                            if (!TextUtils.isEmpty(shopId)) {
                                accessToken.setCustomerSysNo(Long.valueOf(shopId));
                            }

                            if (!TextUtils.isEmpty(customer_name)) {
                                accessToken.setDisplayName(customer_name);
                            }
                            if (!TextUtils.isEmpty(customer)) {
                                accessToken.setLoginName(customer);
                            }
                        } else {
                            accessToken.setCustomersTopSysNo(Long.valueOf(sys_no));
                            if (!TextUtils.isEmpty(customer_name)) {
                                accessToken.setCustomerName(customer_name);
                            }
                            if (!TextUtils.isEmpty(customer)) {
                                accessToken.setCustomer(customer);
                            }
                        }

                        break;
                    case "1":
                        if (isFromFilter) {
                            //TODO
                            accessToken.setCustomerSysNo(Long.valueOf(sys_no));
                            if (!TextUtils.isEmpty(customer_name)) {
                                accessToken.setDisplayName(customer_name);
                            }
                            if (!TextUtils.isEmpty(customer)) {
                                accessToken.setLoginName(customer);
                            }
                        } else {
                            accessToken.setCustomerSysNo(Long.valueOf(sys_no));
                            if (!TextUtils.isEmpty(customer_name)) {
                                accessToken.setDisplayName(customer_name);
                            }
                            if (!TextUtils.isEmpty(customer)) {
                                accessToken.setLoginName(customer);
                            }
                        }

                        break;
                    case "2":
                        if (isFromFilter) {
                            KLog.i(shopId);
                            if (!TextUtils.isEmpty(shopId)) {
                                accessToken.setCustomerSysNo(Long.valueOf(shopId));
                            }
                            if (!TextUtils.isEmpty(customer_name)) {
                                accessToken.setDisplayName(customer_name);
                            }
                            if (!TextUtils.isEmpty(customer)) {
                                accessToken.setLoginName(customer);
                            }
                        } else {
                            accessToken.setSystemUserTopSysNo(Long.valueOf(sys_no));
                            if (!TextUtils.isEmpty(customer_name)) {
                                accessToken.setCustomerName(customer_name);
                            }
                            if (!TextUtils.isEmpty(customer)) {
                                accessToken.setCustomer(customer);
                            }
                        }

                        break;
                    case "3":
                        accessToken.setSystemUserSysNo(Long.valueOf(sys_no));
                        break;
                }
            }
        } else {
            if (customer_type != null && !customer_type.equals("")) {
                //  商户/服务商角色
                switch (customer_type) {
                    case "0":
                        accessToken.setCustomersTopSysNo(Long.valueOf(sys_no));
                        if (!TextUtils.isEmpty(customer_name)) {
                            accessToken.setCustomerName(customer_name);
                        }
                        if (!TextUtils.isEmpty(customer)) {
                            accessToken.setCustomer(customer);
                        }
                        break;
                    case "1":
                        accessToken.setCustomerSysNo(Long.valueOf(sys_no));
                        if (!TextUtils.isEmpty(customer_name)) {
                            accessToken.setDisplayName(customer_name);
                        }
                        if (!TextUtils.isEmpty(customer)) {
                            accessToken.setLoginName(customer);
                        }
                        break;
                    case "2":
                        accessToken.setSystemUserTopSysNo(Long.valueOf(sys_no));
                        if (!TextUtils.isEmpty(customer_name)) {
                            accessToken.setCustomerName(customer_name);
                        }
                        if (!TextUtils.isEmpty(customer)) {
                            accessToken.setCustomer(customer);
                        }
                        break;
                    case "3":
                        accessToken.setSystemUserSysNo(Long.valueOf(sys_no));
                        break;
                }
            }
        }

        KLog.i(accessToken);

        if (!NetStateUtils.isNetworkConnected(context)) {
            dismissDialog();
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("网络连接异常，请检查您的手机网络");
            dialog.setPositiveButton(context.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            view.noData(true);
        } else {
            HomeREService.getInstance(context)
                    .orderNewSearch(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(searchRespBaseObserver);
        }


    }

    private void dismissDialog() {
        view.dismissDialog();

    }

}
