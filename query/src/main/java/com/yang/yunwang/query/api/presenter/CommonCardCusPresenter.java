package com.yang.yunwang.query.api.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.api.QueryReService;
import com.yang.yunwang.query.api.bean.merchsearch.MerchListReq;
import com.yang.yunwang.query.api.bean.merchsearch.MerchListResp;
import com.yang.yunwang.query.api.bean.merchsearch.Model;
import com.yang.yunwang.query.api.bean.merchsearch.PagingInfo;
import com.yang.yunwang.query.api.bean.ordersettel.OrderSettleReq;
import com.yang.yunwang.query.api.bean.ordersettel.OrderSettleResp;
import com.yang.yunwang.query.api.bean.staffsearch.StaffListReq;
import com.yang.yunwang.query.api.bean.staffsearch.StaffListResp;
import com.yang.yunwang.query.api.contract.CommonCardCusContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CommonCardCusPresenter implements CommonCardCusContract.Presenter {

    private final Context context;
    private final CommonCardCusContract.View views;

    private final String shop_user;
    private final String shop_name;
    private final String staff_id;
    private final boolean fromHome;
    private StaffListResp beanStaff;
    private MerchListResp beanMerch;
    private long page_size = 10;
    private long page = 0;
    private long total_count;
    private boolean isActive;
    private boolean isCus;
    private OrderSettleResp beanActive;


    public CommonCardCusPresenter(Context context, CommonCardCusContract.View views) {
        this.context = context;
        this.views = views;
        views.setPresenter(this);
        beanMerch = new MerchListResp();
        beanMerch.setModel(new ArrayList<Model>());
        beanStaff = new StaffListResp();
        beanStaff.setModel(new ArrayList<com.yang.yunwang.query.api.bean.staffsearch.Model>());
        Intent intent = views.getIntentInstance();
        shop_user = intent.getStringExtra("shop_user");
        shop_name = intent.getStringExtra("shop_name");
        staff_id = intent.getStringExtra("staff_id");
        fromHome = intent.getBooleanExtra("from_home", false);

    }

    @Override
    public void initData(boolean isActive, boolean isCus) {
        views.showDialog();
        this.isActive = isActive;
        this.isCus = isCus;
        searchShop(shop_user, shop_name, staff_id, page, page_size, true, false);
    }

    @Override
    public void loadMore() {
        KLog.i(views.getRecItmesCount() + "------" + total_count);
        if ((views.getRecItmesCount() < total_count) && (total_count > page_size)) {
            final int items_count = views.getRecItmesCount();
            if (items_count / page_size < page + 1) {
                searchShop(shop_user, shop_name, staff_id, page, page_size, false, false);
            } else {
                page++;
                searchShop(shop_user, shop_name, staff_id, page, page_size, false, false);
            }
        } else {
            KLog.i(views.getRecItmesCount() + "------" + total_count);
            views.loadMoreComplete(false);
            Toast.makeText(context, "已经加载全部数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refresh() {
        page = 0;
        searchShop(shop_user, shop_name, staff_id, page, page_size, false, true);
    }

    private void searchShop(String shop_user, String shop_name, String staff_id, long page_number, final long page_size, final boolean isInit, final boolean isRefresh) {
        if (isActive) {
            searchActive(shop_user, shop_name, staff_id, page_number, page_size, isInit, isRefresh);
        } else if (isCus) {
            searchCusUser(shop_user, shop_name, staff_id, page_number, page_size, isInit, isRefresh);
        } else {
            searchMerchent(shop_user, shop_name, staff_id, page_number, page_size, isInit, isRefresh);
        }


    }

    private void searchActive(String shop_user, String shop_name, final String staff_id, long page_number, long page_size, final boolean isInit, final boolean isRefresh) {
        OrderSettleReq accessToken = new OrderSettleReq();
        com.yang.yunwang.query.api.bean.ordersettel.PagingInfo pagingInfo = new com.yang.yunwang.query.api.bean.ordersettel.PagingInfo();
        pagingInfo.setPageNumber(page_number);
        pagingInfo.setPageSize(page_size);
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
        accessToken.setTimeStart(dateStart);
        accessToken.setTimeEnd(dateEnd);
        accessToken.setPagingInfo(pagingInfo);
        String customer_type = ConstantUtils.NEW_TYPE;
        if (customer_type != null && !customer_type.equals("")) {
            //  商户/服务商角色
            switch (customer_type) {
                case "0":
                    accessToken.setCustomersTopSysNo(ConstantUtils.SYS_NO);

                    break;
                case "2":
                    //  服务商员工   SystemUserTopSysNo正式接口有改动
                    accessToken.setCustomersTopSysNo(ConstantUtils.HIGHER_SYS_NO);
                    accessToken.setSystemUserTopSysNo(ConstantUtils.SYS_NO);
                    break;
            }
        }

        BaseObserver<OrderSettleResp> observer = new BaseObserver<OrderSettleResp>(context) {
            @Override
            protected void doOnNext(OrderSettleResp value) {
                if (value.getModel().size() > 0) {
                    if (isInit) {
                        beanActive = value;
                        views.setAdapter(beanActive, staff_id);
                        total_count = value.getTotalCount();
                        views.dismissDialog();
                    } else if (isRefresh) {
                        beanActive = value;
                        views.setAdapter(beanActive, staff_id);
                        total_count = value.getTotalCount();
                        views.refreshComplete();
                        views.dataNotifyChanged();
                    } else {
                        ArrayList tempList = new ArrayList();
                        tempList.addAll(beanActive.getModel());
                        tempList.addAll(value.getModel());
                        beanActive.getModel().clear();
                        beanActive.setModel(tempList);
                        views.setAdapter(beanActive, staff_id);
                        views.dataNotifyChanged();
                        views.loadMoreComplete(true);
                    }
                    views.noData(false);
                } else {
                    views.setAdapter(beanActive, staff_id);
                    if (isInit) {
                        views.dismissDialog();
                    }
                    Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
                    if (isInit) {
                        KLog.i("empty");
                    } else if (isRefresh) {
                        views.dataNotifyChanged();
                        views.refreshComplete();
                    } else {
                        views.dataNotifyChanged();
                        views.loadMoreComplete(false);
                    }
                    views.noData(true);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                try {
                    views.setAdapter(beanActive, staff_id);
                    if (isInit) {
                        views.dismissDialog();
                    } else if (isRefresh) {
                        views.refreshComplete();
                        views.dataNotifyChanged();
                    } else {
                        views.dataNotifyChanged();
                        views.loadMoreComplete(false);
                    }
                    views.noData(true);
                } catch (Exception e) {
                    views.setAdapter(beanActive, staff_id);
                    if (isInit) {
                        views.dismissDialog();
                    } else if (isRefresh) {
                        views.refreshComplete();
                        views.dataNotifyChanged();
                    } else {
                        views.dataNotifyChanged();
                        views.loadMoreComplete(false);
                    }
                    views.noData(true);
                }
//                views.setAdapter(beanActive, staff_id);
//                if (isInit) {
//                    views.dismissDialog();
//                } else if (isRefreshStop) {
//                    views.refreshComplete();
//                    views.dataNotifyChanged();
//                } else {
//                    views.dataNotifyChanged();
//                    views.loadMoreComplete(false);
//                }
//                views.noData(true);
            }
        };
        QueryReService.getInstance(context)
                .getSettlementList(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void searchCusUser(String shop_user, String shop_name, String staff_id, long page_number, long page_size, final boolean isInit, final boolean isRefresh) {
        final String inner_flag = "";
        BaseObserver<StaffListResp> observer = new BaseObserver<StaffListResp>(context) {
            @Override
            protected void doOnNext(StaffListResp value) {
                if (value.getModel().size() > 0) {
                    if (isInit) {
                        beanStaff = value;
                        views.setAdapter(beanStaff, inner_flag);
                        total_count = value.getTotalCount();
                        views.dismissDialog();
                    } else if (isRefresh) {
                        beanStaff = value;
                        views.setAdapter(beanStaff, inner_flag);
                        total_count = value.getTotalCount();
                        views.refreshComplete();
                        views.dataNotifyChanged();
                    } else {
//                        beanStaff.getModel().addAll(value.getModel());
                        ArrayList tempList = new ArrayList();
                        tempList.addAll(beanStaff.getModel());
                        tempList.addAll(value.getModel());
                        beanStaff.getModel().clear();
                        beanStaff.setModel(tempList);
                        views.setAdapter(beanStaff, inner_flag);
                        views.dataNotifyChanged();
                        views.loadMoreComplete(true);
                    }
                    views.noData(false);
                } else {
                    views.setAdapter(beanStaff, inner_flag);
                    if (isInit) {
                        views.dismissDialog();
                    }
                    Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
                    if (isInit) {
                        KLog.i("empty");
                    } else if (isRefresh) {
                        views.dataNotifyChanged();
                        views.refreshComplete();
                    } else {
                        views.dataNotifyChanged();
                        views.loadMoreComplete(false);
                    }
                    views.noData(true);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                try {
                    views.setAdapter(beanStaff, inner_flag);
                    if (isInit) {
                        views.dismissDialog();
                    } else if (isRefresh) {
                        views.refreshComplete();
                        views.dataNotifyChanged();
                    } else {
                        views.dataNotifyChanged();
                        views.loadMoreComplete(false);
                    }
                    views.noData(true);
                } catch (Exception e) {
                    views.setAdapter(beanStaff, inner_flag);
                    if (isInit) {
                        views.dismissDialog();
                    } else if (isRefresh) {
                        views.refreshComplete();
                        views.dataNotifyChanged();
                    } else {
                        views.dataNotifyChanged();
                        views.loadMoreComplete(false);
                    }
                    views.noData(true);
                }


            }
        };
        com.yang.yunwang.query.api.bean.staffsearch.PagingInfo pagingInfo = new com.yang.yunwang.query.api.bean.staffsearch.PagingInfo();
        pagingInfo.setPageNumber(page_number);
        pagingInfo.setPageSize(page_size);
        StaffListReq req = new StaffListReq();
        com.yang.yunwang.query.api.bean.staffsearch.SortingInfo sortingInfo = new com.yang.yunwang.query.api.bean.staffsearch.SortingInfo();
        sortingInfo.setSortField("sysno");
        sortingInfo.setSortOrder("Ascending");
        req.setPagingInfo(pagingInfo);
        req.setCustomerServiceSysNo(ConstantUtils.SYS_NO);
        req.setLoginName(shop_user);
        req.setDisplayName(shop_name);
        req.setSortingInfo(sortingInfo);
        if (!TextUtils.isEmpty(ConstantUtils.SYS_NO)) {
            QueryReService.getInstance(context)
                    .getStaffList(req)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {
            views.dismissDialog();
            Toast.makeText(context, "输入有误,请返回重新查询.", Toast.LENGTH_SHORT).show();
        }
    }

    private void searchMerchent(String shop_user, String shop_name, String staff_id, long page_number, long page_size, final boolean isInit, final boolean isRefresh) {
        MerchListReq map_param = new MerchListReq();
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPageNumber(page_number);
        pagingInfo.setPageSize(page_size);
        map_param.setPagingInfo(pagingInfo);
        map_param.setCustomer(shop_user);
        map_param.setCustomerName(shop_name);
        com.yang.yunwang.query.api.bean.merchsearch.SortingInfo sortingInfo = new com.yang.yunwang.query.api.bean.merchsearch.SortingInfo();
        sortingInfo.setSortOrder("Ascending");
        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        final String inner_flag = "";
        if (staff_id != null && !staff_id.equals("")) {
            map_param.setSystemUserSysNo(staff_id);
            sortingInfo.setSortField("CS.sysno");
        } else {
            if (customer_type != null && !customer_type.equals("")) {
                map_param.setCustomersTopSysNo(ConstantUtils.SYS_NO);
                sortingInfo.setSortField("sysno");
            } else {
                map_param.setSystemUserSysNo(ConstantUtils.SYS_NO);
                sortingInfo.setSortField("CS.sysno");
            }
        }
        map_param.setSortingInfo(sortingInfo);
        BaseObserver<MerchListResp> observer = new BaseObserver<MerchListResp>(context) {
            @Override
            protected void doOnNext(MerchListResp value) {

                if (value.getModel().size() > 0) {
                    if (isInit) {
                        beanMerch = value;
                        views.setAdapter(beanMerch, inner_flag);
                        total_count = value.getTotalCount();
                        views.dismissDialog();
                    } else if (isRefresh) {
                        beanMerch = value;
                        total_count = value.getTotalCount();
                        views.setAdapter(beanMerch, inner_flag);
                        views.refreshComplete();
                        views.dataNotifyChanged();
                    } else {
//                        beanMerch.getModel().addAll(value.getModel());
                        ArrayList tempList = new ArrayList();
                        tempList.addAll(beanMerch.getModel());
                        tempList.addAll(value.getModel());
                        beanMerch.getModel().clear();
                        beanMerch.setModel(tempList);
                        views.setAdapter(beanMerch, inner_flag);
                        views.dataNotifyChanged();
                        views.loadMoreComplete(true);
                    }
                    views.noData(false);
                } else {
                    views.setAdapter(beanMerch, inner_flag);
                    if (isInit) {
                        views.dismissDialog();
                    }
                    Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();

                    if (isInit) {
                        KLog.i("empty");
                    } else if (isRefresh) {
                        views.dataNotifyChanged();
                        views.refreshComplete();
                    } else {
                        views.dataNotifyChanged();
                        views.loadMoreComplete(false);
                    }
                    views.noData(true);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                try {
                    views.setAdapter(beanMerch, inner_flag);
                    if (isInit) {
                        views.dismissDialog();
                    } else if (isRefresh) {
                        views.setAdapter(beanMerch, inner_flag);
                        views.refreshComplete();
                        views.dataNotifyChanged();
                    } else {

                        views.dataNotifyChanged();
                        views.loadMoreComplete(false);
                    }
                    views.noData(true);
                } catch (Exception e) {
                    views.setAdapter(beanMerch, inner_flag);
                    if (isInit) {
                        views.dismissDialog();
                    } else if (isRefresh) {
                        views.setAdapter(beanMerch, inner_flag);
                        views.refreshComplete();
                        views.dataNotifyChanged();
                    } else {

                        views.dataNotifyChanged();
                        views.loadMoreComplete(false);
                    }
                    views.noData(true);
                }

            }
        };
        QueryReService.getInstance(context)
                .getMerchList(map_param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
