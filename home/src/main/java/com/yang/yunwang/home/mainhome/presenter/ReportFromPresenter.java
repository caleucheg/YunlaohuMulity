package com.yang.yunwang.home.mainhome.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.socks.library.KLog;
import com.yang.yunwang.base.busevent.ReportFilterEvent;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.homeservice.HomeREService;
import com.yang.yunwang.home.mainhome.bean.report.CommonReportReq;
import com.yang.yunwang.home.mainhome.bean.report.resp.CommonReportResp;
import com.yang.yunwang.home.mainhome.bean.report.resp.Datum;
import com.yang.yunwang.home.mainhome.contract.ReportFromContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReportFromPresenter implements ReportFromContract.Presenter {
    private final Context context;
    private final ReportFromContract.View view;


    public ReportFromPresenter(Context context, ReportFromContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData(String cusType, boolean isAllCus, String cusNo) {
        CommonReportReq accessToken = new CommonReportReq();
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
        accessToken.setTimeStart(dateStart);
        accessToken.setTimeEnd(dateEnd);
        Long customersTopSysNo = Long.valueOf(cusNo);
        if (isAllCus) {
            switch (cusType) {
                case "0":
                    accessToken.setCustomersTopSysNo(customersTopSysNo);
                    break;
                case "1":
                    accessToken.setCustomerSysNo(customersTopSysNo);
                    break;
                case "2":
                    accessToken.setSystemUserTopSysNo(customersTopSysNo);
                    break;
                case "3":
                    accessToken.setSystemUserSysNo(customersTopSysNo);
                    break;
            }
        } else {
            switch (cusType) {
                case "0":
                    accessToken.setCustomerSysNo(customersTopSysNo);
                    break;
                case "1":
                    accessToken.setSystemUserSysNo(customersTopSysNo);
                    break;
                case "2":
                    accessToken.setCustomerSysNo(customersTopSysNo);
                    break;
                case "3":
                    accessToken.setSystemUserSysNo(customersTopSysNo);
                    break;
            }
        }


        reqReportInfo(accessToken);

    }

    private void reqReportInfo(CommonReportReq accessToken) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.showDialog();
            }
        }, 100);
        final String[] centerCash = {"0.00"};
        final String[] orderFee = {"0.00"};
        final String[] tradeCount = {"0"};
        final String[] refundFeeCount = {"0.00|0"};
        final String[] centerCashWx = {"0.00"};
        final String[] orderFeeWx = {"0.00"};
        final String[] tradeCountWxS = {"0"};
        final String[] refundFeeCountWx = {"0.00|0"};
        final String[] centerCashZfb = {"0.00"};
        final String[] orderFeeZfb = {"0.00"};
        final String[] tradeCountZfbS = {"0"};
        final String[] refundFeeCountZfb = {"0.00|0"};

        final Long[] tradeCountWx = {0L};
        final Long[] tradeCountZfb = {0L};
        final Long[] refundFeeWx = {0L};
        final Long[] refundCountWx = {0L};
        final Long[] refundFeeZfb = {0L};
        final Long[] refundCountZfb = {0L};
        final Long[] wxCashLong = {0L};
        final Long[] wxOrderLong = {0L};
        final Long[] zfbCashLong = {0L};
        final Long[] zfbOrderLong = {0L};
        BaseObserver<CommonReportResp> observer = new BaseObserver<CommonReportResp>(context) {
            @Override
            protected void doOnNext(CommonReportResp value) {
                if (value != null) {
                    if (value.getCode() == 0) {
                        List<Datum> daList = value.getData();
                        if (daList.size() == 0) {
                            setErrorReportInfo(false);
                        } else {
                            try {
                                for (int i = 0; i < daList.size(); i++) {
                                    Datum datam = daList.get(i);
                                    if (TextUtils.equals(datam.getRemarks(), "WX")) {
                                        wxCashLong[0] = Long.valueOf(datam.getFee());
                                        centerCashWx[0] = AmountUtils.changeF2YWithDDD(wxCashLong[0]);
                                        wxOrderLong[0] = Long.valueOf(datam.getTotalFee());
                                        orderFeeWx[0] = AmountUtils.changeF2YWithDDD(wxOrderLong[0]);
                                        tradeCountWx[0] = Long.valueOf(datam.getTradeCount());
                                        tradeCountWxS[0] = datam.getTradeCount();
                                        refundFeeWx[0] = Long.valueOf(datam.getRefundFee());
                                        refundCountWx[0] = Long.valueOf(datam.getRMACount());
                                        String stringWithD = datam.getRMACount();
                                        refundFeeCountWx[0] = AmountUtils.changeF2YWithDDD(Long.valueOf(datam.getRefundFee())) + "|" + stringWithD;
                                    } else if (TextUtils.equals(datam.getRemarks(), "AliPay")) {
                                        zfbCashLong[0] = Long.valueOf(datam.getFee());
                                        centerCashZfb[0] = AmountUtils.changeF2YWithDDD(zfbCashLong[0]);
                                        zfbOrderLong[0] = Long.valueOf(datam.getTotalFee());
                                        orderFeeZfb[0] = AmountUtils.changeF2YWithDDD(zfbOrderLong[0]);
                                        tradeCountZfb[0] = Long.valueOf(datam.getTradeCount());
                                        tradeCountZfbS[0] = datam.getTradeCount();
                                        refundFeeZfb[0] = Long.valueOf(datam.getRefundFee());
                                        refundCountZfb[0] = Long.valueOf(datam.getRMACount());
                                        String stringWithD = datam.getRMACount();
                                        refundFeeCountZfb[0] = AmountUtils.changeF2YWithDDD(Long.valueOf(datam.getRefundFee())) + "|" + stringWithD;
                                    }
                                }

                                centerCash[0] = AmountUtils.changeF2YWithDDD(wxCashLong[0] + zfbCashLong[0]);
                                orderFee[0] = AmountUtils.changeF2YWithDDD(wxOrderLong[0] + zfbOrderLong[0]);
                                tradeCount[0] = String.valueOf(tradeCountWx[0] + tradeCountZfb[0]);
                                String stringWithD = String.valueOf(refundCountWx[0] + refundCountZfb[0]);
                                refundFeeCount[0] = AmountUtils.changeF2YWithDDD(refundFeeWx[0] + refundFeeZfb[0]) + "|" + stringWithD;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            view.setReportInfo(centerCash[0], orderFee[0], tradeCount[0], refundFeeCount[0],
                                    centerCashWx[0], orderFeeWx[0], tradeCountWxS[0], refundFeeCountWx[0],
                                    centerCashZfb[0], orderFeeZfb[0], tradeCountZfbS[0], refundFeeCountZfb[0]
                            );
                        }

                    } else {
                        setErrorReportInfo(true);
                    }
                } else {
                    setErrorReportInfo(true);
                }

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                try {
                    setErrorReportInfo(true);
                } catch (Exception e) {
                    KLog.i(e);
                }


            }
        };
        if (!NetStateUtils.isNetworkConnected(context)) {
            view.setReportInfo("--", "--", "--", "--|--",
                    "--", "--", "--", "--|--",
                    "--", "--", "--", "--|--"
            );
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("网络连接异常，请检查您的手机网络");
            dialog.setPositiveButton(context.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            HomeREService.getInstance(context)
                    .commonReport(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }

    }

    private void setErrorReportInfo(boolean connectError) {
        if (connectError) {
            view.setReportInfo("--", "--", "--", "--|--",
                    "--", "--", "--", "--|--",
                    "--", "--", "--", "--|--"
            );
        } else {
            view.setReportInfo("0.00", "0.00", "0", "0.00|0",
                    "0.00", "0.00", "0", "0.00|0",
                    "0.00", "0.00", "0", "0.00|0"
            );
        }

    }

    @Override
    public void refreshData(ReportFilterEvent userEvent, String newType, boolean isAllCus, String cusNo) {
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
        CommonReportReq accessToken = new CommonReportReq();
        if (TextUtils.isEmpty(cusNo)) {
            cusNo = ConstantUtils.SYS_NO;
        }
        Long customersTopSysNo = Long.valueOf(cusNo);
        accessToken.setTimeEnd(dateEnd);
        accessToken.setTimeStart(dateStart);
        if (!isAllCus) {
            switch (newType) {
                case "0":
                    accessToken.setCustomerSysNo(customersTopSysNo);
                    break;
                case "1":
                    accessToken.setSystemUserSysNo(customersTopSysNo);
                    break;
                case "2":
                    accessToken.setCustomerSysNo(customersTopSysNo);
                    break;
                case "3":
                    accessToken.setSystemUserSysNo(customersTopSysNo);
                    break;
            }
        } else {
            switch (newType) {
                case "0":
                    accessToken.setCustomersTopSysNo(customersTopSysNo);
                    break;
                case "1":
                    accessToken.setCustomerSysNo(customersTopSysNo);
                    break;
                case "2":
                    accessToken.setSystemUserTopSysNo(customersTopSysNo);
                    break;
                case "3":
                    accessToken.setSystemUserSysNo(customersTopSysNo);
                    break;
            }
        }
        reqReportInfo(accessToken);
    }

    @Override
    public void filterOrder(ReportFilterEvent userEvent, String newType, boolean isAllCus, String cusNo) {
        KLog.i(userEvent);
        KLog.i(isAllCus);
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
        CommonReportReq accessToken = new CommonReportReq();
        Long customersTopSysNo = Long.valueOf(cusNo);
        String timeEnd = userEvent.getTimeEnd();
        if (!TextUtils.isEmpty(timeEnd)) {
            timeEnd = userEvent.getTimeEnd();
        } else {
            timeEnd = dateEnd;
        }
        accessToken.setTimeEnd(timeEnd);
        String timeStart = userEvent.getTimeStart();
        if (!TextUtils.isEmpty(timeStart)) {
            timeStart = userEvent.getTimeStart();
        } else {
            timeStart = dateStart;
        }
        accessToken.setTimeStart(timeStart);
        if (!isAllCus) {
            switch (newType) {
                case "0":
                    accessToken.setCustomerSysNo(customersTopSysNo);
                    break;
                case "1":
                    accessToken.setSystemUserSysNo(customersTopSysNo);
                    break;
                case "2":
                    accessToken.setCustomerSysNo(customersTopSysNo);
                    break;
                case "3":
                    accessToken.setSystemUserSysNo(customersTopSysNo);
                    break;
            }
        } else {
            switch (newType) {
                case "0":
                    accessToken.setCustomersTopSysNo(customersTopSysNo);
                    break;
                case "1":
                    accessToken.setCustomerSysNo(customersTopSysNo);
                    break;
                case "2":
                    accessToken.setSystemUserTopSysNo(customersTopSysNo);
                    break;
                case "3":
                    accessToken.setSystemUserSysNo(customersTopSysNo);
                    break;
            }
        }
        KLog.i(accessToken);
        reqReportInfo(accessToken);
    }

    private String getStringWithD(String customerCount) {
        StringBuffer result = new StringBuffer();
        if (customerCount.length() > 2) {
            for (int i = 1; i <= customerCount.length(); i++) {
                if ((i - 1) % 3 == 0 && i != 1) {
                    result.append(",");
                }
                result.append(customerCount.substring(customerCount.length() - i, customerCount.length() - i + 1));
            }
        } else {
            result.append(customerCount).reverse();
        }
//        KLog.i("tradeC===" + result);
        return result.reverse().toString();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
