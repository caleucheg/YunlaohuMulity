package com.yang.yunwang.home.mainhome.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.homeservice.HomeREService;
import com.yang.yunwang.home.homeservice.bean.RoleTypeReq;
import com.yang.yunwang.home.homeservice.bean.RoleTypeResp;
import com.yang.yunwang.home.mainhome.bean.StaffHomeBean;
import com.yang.yunwang.home.mainhome.bean.homepage.serverstaff.active.ServerStaffActiveCusReq;
import com.yang.yunwang.home.mainhome.bean.homepage.serverstaff.active.ServerStaffActiveCusResp;
import com.yang.yunwang.home.mainhome.bean.homepage.serverstaff.totalinfo.ServerStaffTotalInfoReq;
import com.yang.yunwang.home.mainhome.bean.homepage.serverstaff.totalinfo.ServerStaffTotalInfoResp;
import com.yang.yunwang.home.mainhome.bean.homepage.staff.StaffTotalInfoReq;
import com.yang.yunwang.home.mainhome.bean.homepage.staff.StaffTotalInfoResp;
import com.yang.yunwang.home.mainhome.contract.StaffHomeContract;
import com.yang.yunwang.home.mainhome.model.StaffHomeModel;

import java.util.Calendar;
import java.util.Locale;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/6/13.
 */

public class StaffHomePresenter implements StaffHomeContract.Presenter {
    private final StaffHomeContract.View view;
    private final Context context;
    private StaffHomeContract.Model staffHomeModel;
    private StaffHomeBean staffHomeBean;
    private boolean isShowToa = false;
    private String pullLoad = "网络连接异常，请检查您的手机网络";


    public StaffHomePresenter(StaffHomeContract.View view, Context context) {
        view.setPresenter(this);
        this.view = view;
        this.context = context;
        staffHomeModel = new StaffHomeModel(context);
        staffHomeBean = staffHomeModel.loadInstance();
    }

    @Override
    public void initData(String sys_no, String customer) {
        staffHomeModel.setInfos(sys_no, customer);
        if (!ConstantUtils.HIGHER_SYS_NO.equals("") && !ConstantUtils.STAFF_TYPE.equals("") && !ConstantUtils.HIGHER_NAME.equals("")) {
            if (ConstantUtils.STAFF_TYPE.equals("0")) {
                //服务商员工
                staffHomeModel.initServiceMainFunctionList();
//                staffHomeModel.initServiceSubFunctionList();
            } else {
                //商户员工
                staffHomeModel.initMainFunctionList();
                staffHomeModel.initSubFunctionList();
            }
            setAdapter();

        } else {
            RoleTypeReq accessToken = new RoleTypeReq();
            accessToken.setSystemUserSysNo(Long.parseLong(sys_no));
            HomeREService.getInstance(context)
                    .getTopInfo(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<RoleTypeResp>(context) {
                        @Override
                        protected void doOnNext(RoleTypeResp value) {
                            String id = null;
                            id = value.getCustomerSysNo();
                            ConstantUtils.HIGHER_SYS_NO = id;
                            String type = value.getCustomersType();
                            ConstantUtils.STAFF_TYPE = type;
                            ConstantUtils.NEW_TYPE = Integer.parseInt(type) + 2 + "";
                            KLog.i(ConstantUtils.NEW_TYPE + "==+[[==");
                            CommonShare.putTypeData(context, ConstantUtils.NEW_TYPE);
                            String name = value.getCustomerName();
                            ConstantUtils.HIGHER_NAME = name;
                            String[] key = new String[]{"HIGHER_SYS_NO", "STAFF_TYPE", "HIGHER_NAME"};
                            String[] valueA = new String[]{ConstantUtils.HIGHER_SYS_NO, ConstantUtils.STAFF_TYPE, ConstantUtils.HIGHER_NAME};
                            CommonShare.putHomeData(context, key, valueA);
                            if (type.equals("0")) {
                                //服务商员工
                                staffHomeModel.initServiceMainFunctionList();
//                                staffHomeModel.initServiceSubFunctionList();
                            } else {
                                //商户员工
                                staffHomeModel.initMainFunctionList();
                                staffHomeModel.initSubFunctionList();
                            }
                            setAdapter();
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                            try {
                                Toast.makeText(context, "获取员工类别失败！", Toast.LENGTH_SHORT).show();
//                            Intent intent_logout = new Intent(context, LoginActivity.class);
                                CommonShare.clear(context);
                                JPushInterface.cleanTags(context, 12);
                                ConstantUtils.SYS_NO = "";
                                ConstantUtils.HIGHER_SYS_NO = "";
                                ConstantUtils.CUSTOMER = "";
                                ConstantUtils.CUSTOMERS_TYPE = "";
                                ConstantUtils.STAFF_TYPE = "";
                                ConstantUtils.HIGHER_NAME = "";
                                ConstantUtils.NEW_TYPE = "-1";
                                HomeIntent.login();
                            } catch (Exception e) {
                                KLog.i(e);
                            }

//                            context.startActivity(intent_logout);
                        }
                    });
        }
    }

    private void initTopDelay() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initHomePage();
            }
        }, 700);
    }

    @Override
    public void initHomePage() {
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
        if (!ConstantUtils.HIGHER_SYS_NO.equals("") && !ConstantUtils.STAFF_TYPE.equals("") && !ConstantUtils.HIGHER_NAME.equals("")) {
            if (ConstantUtils.STAFF_TYPE.equals("0")) {
                //服务商员工
                ServerStaffTotalInfoReq accessToken = new ServerStaffTotalInfoReq();
                accessToken.setTimeStart(time_start);
                accessToken.setTimeEnd(time_end);
                accessToken.setCustomersTopSysNo(Long.valueOf(ConstantUtils.HIGHER_SYS_NO));
                accessToken.setSystemUserTopSysNo(Long.valueOf(ConstantUtils.SYS_NO));
                HomeREService.getInstance(context)
                        .serverStaffTotalInfo(accessToken)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<ServerStaffTotalInfoResp>(context) {
                            @Override
                            protected void doOnNext(ServerStaffTotalInfoResp value) {
                                if (value == null) {
                                    KLog.i("------------null");
                                } else {
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
                                    view.setHomeInfo(ConstantUtils.STAFF_TYPE, cashFeeS, tradecountSWD, totalFeeS);
                                }

                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                try {
                                    if (responeThrowable.code == ExceptionHandle.ERROR.OSD_ERROR) {
                                        view.setHomeInfo(ConstantUtils.STAFF_TYPE, "0.00", "0", "0.00");
                                        KLog.i("error1");
                                    } else {
                                        view.setHomeInfo(ConstantUtils.STAFF_TYPE, "--", "--", "--");
                                        KLog.i("error2");
                                        if (!isShowToa) {
                                            isShowToa = true;
                                            Toast.makeText(context, pullLoad, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                } catch (Exception e) {
                                    KLog.i("error3");
                                    view.setHomeInfo(ConstantUtils.STAFF_TYPE, "--", "--", "--");
                                }
                            }
                        });
                ServerStaffActiveCusReq accessToken1 = new ServerStaffActiveCusReq();
                accessToken1.setSystemUserTopSysNo(Long.valueOf(ConstantUtils.SYS_NO));
                accessToken1.setTimeStart(time_start);
                accessToken1.setTimeEnd(time_end);
                HomeREService.getInstance(context)
                        .serverStaffActiveCus(accessToken1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<ServerStaffActiveCusResp>(context) {
                            @Override
                            protected void doOnNext(ServerStaffActiveCusResp value) {
                                if (value.getCode() == 0) {
                                    String customerCount = value.getData().getCustomerCount();
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
                                    view.setHomeActiveCus(result.reverse().toString());
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
            } else {
                //商户员工
                StaffTotalInfoReq accessToken = new StaffTotalInfoReq();
                accessToken.setSystemUserSysNo(Long.valueOf(ConstantUtils.SYS_NO));
                accessToken.setTimeStart(time_start);
                accessToken.setTimeEnd(time_end);
                HomeREService.getInstance(context)
                        .staffTotalInfo(accessToken)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserver<StaffTotalInfoResp>(context) {
                            @Override
                            protected void doOnNext(StaffTotalInfoResp value) {
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
                                    view.setHomeInfo(ConstantUtils.STAFF_TYPE, cashFeeS, tradecountSWD, totalFeeS);
                                } else {
                                    KLog.i("emp");
                                    view.setHomeInfo(ConstantUtils.STAFF_TYPE, "--", "--", "--");
                                }
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                                try {
                                    if (responeThrowable.code == ExceptionHandle.ERROR.OSD_ERROR) {
                                        view.setHomeInfo(ConstantUtils.STAFF_TYPE, "0.00", "0", "0.00");
                                        KLog.i("error1");
                                    } else {
                                        view.setHomeInfo(ConstantUtils.STAFF_TYPE, "--", "--", "--");
                                        KLog.i("error2");
                                        if (!isShowToa) {
                                            isShowToa = true;
                                            Toast.makeText(context, pullLoad, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                } catch (Exception e) {
                                    KLog.i("error3");
                                    view.setHomeInfo(ConstantUtils.STAFF_TYPE, "--", "--", "--");
                                }

                            }
                        });
            }

        } else {

        }
    }


    private void setAdapter() {
        if (staffHomeBean.getMain_function_list() != null
                && staffHomeBean.getMain_function_res() != null
                && staffHomeBean.getMain_function_res_selected() != null
                && staffHomeBean.getMain_function_list().size() != 0
                && staffHomeBean.getMain_function_res().length != 0
                && staffHomeBean.getMain_function_res_selected().length != 0) {
            view.setMainAdapter(staffHomeBean.getMain_function_list(), staffHomeBean.getMain_function_res(), staffHomeBean.getMain_function_res_selected(), staffHomeBean.getMianActions(),staffHomeBean.getMainBundles());
        }
        if (staffHomeBean.getSub_function_list() != null
                && staffHomeBean.getMain_function_res() != null
                && staffHomeBean.getSub_function_res_selected() != null
                && staffHomeBean.getSub_function_list().size() != 0
                && staffHomeBean.getSub_function_res().length != 0
                && staffHomeBean.getSub_function_res_selected().length != 0) {
            view.setSubAdapter(staffHomeBean.getSub_function_list(), staffHomeBean.getSub_function_res(), staffHomeBean.getSub_function_res_selected(), staffHomeBean.getSubActions(),staffHomeBean.getSubBundles());
        }
        if (!staffHomeBean.getCustomer().equals("")) {
            view.setHeaderTitle(staffHomeBean.getCustomer());
        }
        if (!ConstantUtils.HIGHER_NAME.equals("")) {
            view.setTopName(ConstantUtils.HIGHER_NAME);
        }
        initTopDelay();
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
