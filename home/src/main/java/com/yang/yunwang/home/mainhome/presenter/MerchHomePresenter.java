package com.yang.yunwang.home.mainhome.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.homeservice.HomeREService;
import com.yang.yunwang.home.homeservice.bean.MerchHomeCusReq;
import com.yang.yunwang.home.homeservice.bean.MerchHomeFragReq;
import com.yang.yunwang.home.homeservice.bean.MerchHomeFragResp;
import com.yang.yunwang.home.mainhome.bean.MerchantHomeBean;
import com.yang.yunwang.home.mainhome.bean.homepage.customer.ShopTotalInfoReq;
import com.yang.yunwang.home.mainhome.bean.homepage.customer.ShopTotalInfoResp;
import com.yang.yunwang.home.mainhome.bean.homepage.services.activie.ServiceActiveCusReq;
import com.yang.yunwang.home.mainhome.bean.homepage.services.activie.ServiceActiveCusResp;
import com.yang.yunwang.home.mainhome.bean.homepage.services.totalinfo.ServiceTotalInfoReq;
import com.yang.yunwang.home.mainhome.bean.homepage.services.totalinfo.ServiceTotalResp;
import com.yang.yunwang.home.mainhome.contract.MerchHomeContract;
import com.yang.yunwang.home.mainhome.model.merchant.MerchantHomeModel;
import com.yang.yunwang.home.mainhome.model.merchant.intf.MerchantHomeModelInterface;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/6/13.
 */

public class MerchHomePresenter implements MerchHomeContract.Presenter {
    private static double TOTAL_MONEY = 0;
    private static double TOTAL_CASH = 0;
    private static int COUNT = 0;
    private final MerchHomeContract.View view;
    private final Context context;
    private MerchantHomeModelInterface model;
    private MerchantHomeBean bean;
    private boolean isShowToa = false;
    private String pullLoad = "网络连接异常，请检查您的手机网络";

    public MerchHomePresenter(MerchHomeContract.View view, Context context) {
        view.setPresenter(this);
        this.view = view;
        this.context = context;
        model = new MerchantHomeModel(context);
        bean = model.loadInstance();
    }

    @Override
    public void initData() {
        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        if (customer_type != null && !customer_type.equals("")) {
            switch (customer_type) {
                case "0":
                    model.initServiceRecMenu();
                    break;
                case "1":
                    model.initMerchantRecMenu();
                    break;
            }
        }
        view.setMenuAdapter(bean.getMenu_list(), bean.getReses(), bean.getReses_select(), bean.getActions(), bean.getBundles());
        if (ConstantUtils.IS_ATFER_LOGIN_INIT) {
            ConstantUtils.IS_ATFER_LOGIN_INIT = false;
            refreshHomeTopData();
        } else {
            KLog.i("" + TOTAL_MONEY + "--" + COUNT, "" + "===" + TOTAL_CASH);
            view.setInfo("" + TOTAL_MONEY, COUNT, "" + TOTAL_CASH);
        }
    }

    @Override
    public void refreshHomeTopData() {
        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        if (customer_type != null && !customer_type.equals("")) {
            final Calendar calendar = Calendar.getInstance(Locale.CHINA);
            //获取日期
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String date = year + "-";
            if (month + 1 >= 10) {
                date += (month + 1) + "-";
            } else {
                date += "0" + (month + 1) + "-";
            }
            if (day < 10) {
                date += "0" + day;
            } else {
                date += day;
            }
            String time_start = date + " 00:00:00";
            String time_end = date + " 23:59:59";
            KLog.i(time_start + "--=-=" + time_end);
//                requestTotalInfo(customer_type, ConstantUtils.SYS_NO, time_start, time_end);
            requsetNewTotalInfo(customer_type, ConstantUtils.SYS_NO, time_start, time_end);
        }
    }

    private void requestTotalInfo(String customer_type, String sys_no, String time_start, String time_end) {
        if (customer_type != null && !customer_type.equals("")) {
            //  商户/服务商角色
            switch (customer_type) {
                case "0":
                    MerchHomeFragReq mrchHomeFragReq = new MerchHomeFragReq();
                    mrchHomeFragReq.setCustomersTopSysNo(sys_no);
                    mrchHomeFragReq.setTimeStart(time_start);
                    mrchHomeFragReq.setTimeEnd(time_end);
                    HomeREService.getInstance(context)
                            .getSerMerchHomeFragStatics(mrchHomeFragReq)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<MerchHomeFragResp>(context) {
                                @Override
                                protected void doOnNext(MerchHomeFragResp value) {
                                    int total_count = 0;
                                    double total_money = 0;
                                    double total_cash = 0;
                                    BigDecimal bigDecimal_money = new BigDecimal(0);
                                    BigDecimal bigDecimal_cash = new BigDecimal(0);
                                    String money = "0";
                                    String cash = "0";
                                    try {
                                        money = AmountUtils.changeF2Y(value.getCashFee() + "");
                                        cash = AmountUtils.changeF2Y(value.getTotalFee() + "");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    BigDecimal bigDecimal_money_temp = new BigDecimal(money);
                                    BigDecimal bigDecimal_cash_temp = new BigDecimal(cash);
                                    bigDecimal_money = bigDecimal_money.add(bigDecimal_money_temp);
                                    bigDecimal_cash = bigDecimal_cash.add(bigDecimal_cash_temp);
                                    total_money = bigDecimal_money.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    total_cash = bigDecimal_cash.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    total_count = value.getTradecount();
                                    KLog.i(total_money + "total_money");
                                    KLog.i(total_cash + "total_cash");
                                    KLog.i(total_count + "total_count");
                                    if (total_count != 0) {
                                        TOTAL_MONEY = total_money;
                                        TOTAL_CASH = total_cash;
                                        COUNT = total_count;
                                        view.setInfo("" + total_money, total_count, "" + total_cash);
                                    } else {
                                        view.setInfo("0", 0, "0");
                                    }
                                }

                                @Override
                                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                    KLog.i("0-0-0-0");
                                    view.setInfo("0", 0, "0");
                                }
                            });
                    break;
                case "1":
                    MerchHomeCusReq mrchHomeFragReq1 = new MerchHomeCusReq();
                    mrchHomeFragReq1.setCustomerSysNo(sys_no);
                    mrchHomeFragReq1.setTimeStart(time_start);
                    mrchHomeFragReq1.setTimeEnd(time_end);
                    HomeREService.getInstance(context)
                            .getCusMerchHomeFragStatics(mrchHomeFragReq1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<MerchHomeFragResp>(context) {
                                @Override
                                protected void doOnNext(MerchHomeFragResp value) {
                                    int total_count = 0;
                                    double total_money = 0;
                                    double total_cash = 0;
                                    BigDecimal bigDecimal_money = new BigDecimal(0);
                                    BigDecimal bigDecimal_cash = new BigDecimal(0);
                                    String money = "0";
                                    String cash = "0";
                                    try {
                                        //TODO 10000000 -----1E7
                                        money = AmountUtils.changeF2Y(value.getCashFee());
                                        cash = AmountUtils.changeF2Y(value.getTotalFee());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    BigDecimal bigDecimal_money_temp = new BigDecimal(money);
                                    BigDecimal bigDecimal_cash_temp = new BigDecimal(cash);
                                    bigDecimal_money = bigDecimal_money.add(bigDecimal_money_temp);
                                    bigDecimal_cash = bigDecimal_cash.add(bigDecimal_cash_temp);
                                    total_money = bigDecimal_money.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    total_cash = bigDecimal_cash.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    total_count = value.getTradecount();
                                    KLog.i(total_money + "total_money");
                                    KLog.i(total_cash + "total_cash");
                                    KLog.i(total_count + "total_count");
                                    if (total_count != 0) {
                                        TOTAL_MONEY = total_money;
                                        TOTAL_CASH = total_cash;
                                        COUNT = total_count;
                                        view.setInfo("" + total_money, total_count, "" + total_cash);
                                    } else {
                                        view.setInfo("0", 0, "0");
                                    }
                                }

                                @Override
                                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                    view.setInfo("0", 0, "0");
                                }
                            });
                    break;
            }
        }
    }


    private void requsetNewTotalInfo(final String customer_type, String sys_no, String time_start, String time_end) {
        if (customer_type != null && !customer_type.equals("")) {
            //  商户/服务商角色
            switch (customer_type) {
                case "0":
                    ServiceTotalInfoReq accessToken = new ServiceTotalInfoReq();
                    accessToken.setCustomersTopSysNo(Long.valueOf(sys_no));
                    accessToken.setTimeStart(time_start);
                    accessToken.setTimeEnd(time_end);
                    HomeREService.getInstance(context)
                            .serviceTotalInfo(accessToken)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<ServiceTotalResp>(context) {
                                @Override
                                protected void doOnNext(ServiceTotalResp value) {
                                    if (value != null) {
                                        Long cashFee = value.getCashFee();
                                        Long tradecount = value.getTradecount();
                                        Long totalFee = value.getTotalFee();
                                        String cashFeeS = "";
                                        String tradecountS = String.valueOf(tradecount);
                                        String tradecountSWD = getStringWithD(tradecountS);
                                        String totalFeeS = "";
                                        try {
                                            cashFeeS = AmountUtils.changeF2YWithDDD(cashFee);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        view.setHomeInfo(customer_type, cashFeeS, tradecountSWD, totalFeeS);
                                    } else {
                                        view.setHomeInfo(customer_type, "--", "--", "--");
                                    }

                                }

                                @Override
                                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                    try {
                                        if (responeThrowable.code == ExceptionHandle.ERROR.OSD_ERROR) {
                                            view.setHomeInfo(customer_type, "0.00", "0", "0.00");
                                            KLog.i("error1");
                                        } else {
                                            view.setHomeInfo(customer_type, "--", "--", "--");
                                            KLog.i("error2");
                                            if (!isShowToa) {
                                                isShowToa = true;
                                                Toast.makeText(context, pullLoad, Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    } catch (Exception e) {
                                        KLog.i("error3");
                                        view.setHomeInfo(customer_type, "--", "--", "--");
                                    }
                                }
                            });
                    ServiceActiveCusReq accessToken2 = new ServiceActiveCusReq();
                    accessToken2.setCustomersTopSysNo(Long.valueOf(sys_no));
                    accessToken2.setTimeStart(time_start);
                    accessToken2.setTimeEnd(time_end);
                    HomeREService.getInstance(context)
                            .serviceActive(accessToken2)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<ServiceActiveCusResp>(context) {
                                @Override
                                protected void doOnNext(ServiceActiveCusResp value) {
                                    if (value.getCode() == 0) {
                                        String customerCount = value.getData().getCustomerCount();
                                        String activeCus = getStringWithD(customerCount);
                                        view.setHomeActiveCus(activeCus);
                                    } else {
                                        view.setHomeActiveCus("--");
                                    }
                                }

                                @Override
                                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                    try {
                                        view.setHomeActiveCus("--");
                                        if (!isShowToa) {
                                            isShowToa = true;
                                            Toast.makeText(context, pullLoad, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        KLog.i(e);
                                    }
                                }
                            });
                    break;
                case "1":
                    ShopTotalInfoReq accessToken1 = new ShopTotalInfoReq();
                    accessToken1.setCustomerSysNo(Long.valueOf(sys_no));
                    accessToken1.setTimeStart(time_start);
                    accessToken1.setTimeEnd(time_end);
                    HomeREService.getInstance(context)
                            .shopTotalInfo(accessToken1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseObserver<ShopTotalInfoResp>(context) {
                                @Override
                                protected void doOnNext(ShopTotalInfoResp value) {
                                    if (value != null) {
                                        Long cashFee = value.getCashFee();
                                        Long tradecount = value.getTradecount();
                                        Long totalFee = value.getTotalFee();
                                        String cashFeeS = "";
                                        String tradecountS = String.valueOf(tradecount);
                                        String tradecountSWD = getStringWithD(tradecountS);
                                        String totalFeeS = "";
                                        try {
                                            cashFeeS = AmountUtils.changeF2YWithDDD(cashFee);
                                            totalFeeS = AmountUtils.changeF2YWithDDD(totalFee);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        view.setHomeInfo(customer_type, cashFeeS, tradecountSWD, totalFeeS);
                                    } else {
                                        KLog.i("emp");
                                        view.setHomeInfo(customer_type, "0.00", "0", "0.00");
                                    }

                                }

                                @Override
                                public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                    try {
                                        if (responeThrowable.code == ExceptionHandle.ERROR.OSD_ERROR) {
                                            view.setHomeInfo(customer_type, "0.00", "0", "0.00");
                                            KLog.i("error1");
                                        } else {
                                            view.setHomeInfo(customer_type, "--", "--", "--");
                                            KLog.i("error2");
                                            if (!isShowToa) {
                                                isShowToa = true;
                                                Toast.makeText(context, pullLoad, Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    } catch (Exception e) {
                                        KLog.i("error3");
                                        view.setHomeInfo(customer_type, "--", "--", "--");
                                    }
                                }
                            });
                    break;
            }
        }
    }

    @NonNull
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
        KLog.i("tradeC===" + result);
        return result.reverse().toString();
    }
}
