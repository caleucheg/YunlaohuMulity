package com.yang.yunwang.home.mainhome.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.busevent.CusUserAllcoateEvent;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.homeservice.HomeREService;
import com.yang.yunwang.home.mainhome.bean.cusallcoate.request.CusUserAllcoateReq;
import com.yang.yunwang.home.mainhome.bean.cusallcoate.request.PagingInfo;
import com.yang.yunwang.home.mainhome.bean.cusallcoate.respone.CusUserAllcoateResp;
import com.yang.yunwang.home.mainhome.bean.cusallcoate.respone.Data;
import com.yang.yunwang.home.mainhome.bean.cusallcoate.respone.Model;
import com.yang.yunwang.home.mainhome.contract.CusUserAllcoateContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CusUserAllcoatePresenter implements CusUserAllcoateContract.Presenter {
    private final Context context;
    private final CusUserAllcoateContract.View views;
    private final int page_size = 10;
    private final SimpleDateFormat sdfStart;
    private String start_time;
    private String end_time;
    private Long total_count;
    private int page = 0;
    private CusUserAllcoateResp bean;
    private String remarks;
    private CusUserAllcoateEvent userEvent;

    public CusUserAllcoatePresenter(Context context, CusUserAllcoateContract.View views) {
        this.context = context;
        this.views = views;
        views.setPresenter(this);
        bean = new CusUserAllcoateResp();
        Data data = new Data();
        data.setModel(new ArrayList<Model>());
        bean.setData(data);
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
        start_time = dateStart;
        end_time = dateEnd;


    }

    @Override
    public void initData() {
        views.showDialog();
        searchOrders(false, ConstantUtils.SYS_NO, remarks, start_time, end_time, page, page_size, true, false);
    }

    @Override
    public void loadMore() {
        if (views.getRecItmesCount() < total_count) {
            final int items_count = views.getRecItmesCount();
            if (items_count / page_size < page + 1) {
                //加载当前页
                searchOrders(false, ConstantUtils.SYS_NO, remarks, start_time, end_time, page, page_size, false, false);
            } else {
                //加载下一页
                page++;
                searchOrders(false, ConstantUtils.SYS_NO, remarks, start_time, end_time, page, page_size, false, false);
            }
        } else {
            views.loadMoreComplete(false);
            Toast.makeText(context, "已经加载全部数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refresh() {
        if (userEvent != null && !TextUtils.isEmpty(userEvent.getTimeType())) {
            filterOrder(userEvent);
        } else {
            page = 0;
            final Calendar calendar = Calendar.getInstance(Locale.CHINA);
            String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
            String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
            start_time = dateStart;
            end_time = dateEnd;
            searchOrders(false, ConstantUtils.SYS_NO, remarks, start_time, end_time, page, page_size, false, true);
        }

    }

    @Override
    public void loadMoreN() {
        List<Model> nArr = bean.getData().getModel();
        bean.getData().getModel().addAll(nArr);
        views.dataNotifyChanged();
    }


    private void searchOrders(final boolean isBefore, String sys_no, final String remarks, String time_start,
                              String time_end, long page_number, final long page_size,
                              final boolean isFirstPage, final boolean isRefresh) {
        PagingInfo pagingInfo = new PagingInfo();
        pagingInfo.setPageSize(page_size);
        pagingInfo.setPageNumber(page_number);
//        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        BaseObserver<CusUserAllcoateResp> cusUserAllcoateRespBaseObserver = new BaseObserver<CusUserAllcoateResp>(context) {
            @Override
            protected void doOnNext(CusUserAllcoateResp value) {
                if (value.getData().getModel().size() > 0) {
                    if (isBefore) {
                        KLog.i("before");
                        bean = value;
                        total_count = value.getData().getTotalCount();
                    } else {
                        if (isFirstPage) {
                            KLog.i("first");
                            bean = value;
                            views.setAdapter(bean);
                            total_count = value.getData().getTotalCount();
                            views.dismissDialog();
                        } else if (isRefresh) {
                            bean = value;
                            views.setAdapter(bean);
                            total_count = value.getData().getTotalCount();
                            views.refreshComplete();
                            views.dataNotifyChanged();
                        } else {
//                            KLog.i(bean.getData().getModel().size());
//                            bean.getData().getModel().addAll(value.getData().getModel());
//                            KLog.i(bean.getData().getModel().size());
                            ArrayList tempList = new ArrayList();
                            tempList.addAll(bean.getData().getModel());
                            tempList.addAll(value.getData().getModel());
                            bean.getData().getModel().clear();
                            bean.getData().setModel(tempList);
                            views.setAdapter(bean);
                            views.loadMoreComplete(true);
                            views.dataNotifyChanged();
                        }
                        views.noData(false);
                    }

                } else {
                    views.setAdapter(bean);
                    if (isFirstPage) {
                        views.dismissDialog();
                    }
                    Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
                    views.dataNotifyChanged();
                    views.loadMoreComplete(false);
                    views.noData(true);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                try {
                    Toast.makeText(context, "未找到数据,请重试", Toast.LENGTH_SHORT).show();
                    views.setAdapter(bean);
                    if (isFirstPage) {
                        views.dismissDialog();
                    }
                    if (isRefresh) {
                        views.refreshComplete();
                        views.dataNotifyChanged();
                    }
                    views.dataNotifyChanged();
                    views.loadMoreComplete(false);
                    views.noData(true);
                } catch (Exception e) {
                    KLog.i(e);
                }

            }
        };
        CusUserAllcoateReq accessToken = new CusUserAllcoateReq();
        accessToken.setPagingInfo(pagingInfo);
        accessToken.setRemarks(remarks);
        accessToken.setCustomerSysNo(Long.valueOf(sys_no));
//        accessToken.setLoginName(loginName);
//        accessToken.setDisplayName(displayName);
        accessToken.setTimeStart(time_start);
        accessToken.setTimeEnd(time_end);
        HomeREService.getInstance(context)
                .cusUserAllcoate(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cusUserAllcoateRespBaseObserver);

    }

    @Override
    public void filterOrder(CusUserAllcoateEvent userEvent) {
        String tradeType = userEvent.getTradeType();
        this.userEvent = userEvent;
        remarks = tradeType;
        page = 0;
        String timeType = userEvent.getTimeType();
        if (TextUtils.equals("yestoday", timeType)) {
            final Calendar calendar = Calendar.getInstance(Locale.CHINA);
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, -1);
            String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
            String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
            start_time = dateStart;
            end_time = dateEnd;
        } else if (TextUtils.equals("today", timeType)) {
            final Calendar calendar = Calendar.getInstance(Locale.CHINA);
            String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
            String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
            start_time = dateStart;
            end_time = dateEnd;
        } else if (TextUtils.equals("diy", timeType)) {
            start_time = userEvent.getStartTime();
            end_time = userEvent.getEndTime();
        } else {
            final Calendar calendar = Calendar.getInstance(Locale.CHINA);
            String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
            String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
            start_time = dateStart;
            end_time = dateEnd;
        }

        searchOrders(false, ConstantUtils.SYS_NO, remarks, start_time, end_time, page, page_size, false, true);

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
