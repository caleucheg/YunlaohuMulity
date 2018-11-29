package com.yang.yunwang.home.loginpage.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.socks.library.KLog;
import com.yang.yunwang.base.basereq.bean.merchlogin.MerchLoginResp;
import com.yang.yunwang.base.basereq.bean.staffinfo.StaffInfoReq;
import com.yang.yunwang.base.basereq.bean.stafflogin.StaffLoginResp;
import com.yang.yunwang.base.dao.PassageWay;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.JPUtils;
import com.yang.yunwang.base.util.JsonUtils;
import com.yang.yunwang.home.BuildConfig;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.homeservice.HomeREService;
import com.yang.yunwang.home.homeservice.bean.JidInsertReq;
import com.yang.yunwang.home.homeservice.bean.JidInsertResp;
import com.yang.yunwang.home.homeservice.bean.LoginPageReq;
import com.yang.yunwang.home.homeservice.bean.RoleTypeReq;
import com.yang.yunwang.home.homeservice.bean.RoleTypeResp;
import com.yang.yunwang.home.loginpage.contract.LoginPageContract;
import com.yang.yunwang.home.loginpage.model.LoginModel;
import com.yang.yunwang.home.loginpage.model.intf.LoginModelInterface;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/6/12.
 */

public class LoginPagePresenter implements LoginPageContract.Presenter {
    private final LoginPageContract.View view;
    private final Context context;
    private int time = 3;
    private Timer timer;
    private boolean isSavedWx = false;
    private static final String ACTION_INTENT_TEST = "com.yunwang.temp";
    private LoginModelInterface loginModel;
    private Handler handler;

    public LoginPagePresenter(LoginPageContract.View view, final Context context) {
        view.setPresenter(this);
        this.view = view;
        this.context = context;
        loginModel = new LoginModel(context);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (time > 0) {
                    ConstantUtils.isGetReID = !TextUtils.isEmpty(JPushInterface.getRegistrationID(context));
                    String jpushID = JPushInterface.getRegistrationID(context);
                    if (ConstantUtils.isGetReID) {
                        CommonShare.putJPushID(context, jpushID);
                        boolean isPushIDInsert = CommonShare.getJpushIDBoolean(context);
                        if (!isPushIDInsert) {
                            KLog.i("insert-id");
                            String sysNo = CommonShare.getLoginData(context, "SysNo", "");
                            if (!TextUtils.isEmpty(sysNo)) {
                                insertJpushId(sysNo, jpushID);
                                if (timer != null) {
                                    timer.cancel();
                                }
                            }
                        } else {
                            KLog.i("already-insert");
                        }
                    }
                } else {
                    if (timer != null) {
                        timer.cancel();
                    }
                    if (time<-3){
                        if (timer != null) {
                            timer.cancel();
                        }
                    }else {
                        sentBroadcast(context);
                    }
                    KLog.i(time);
                }
            }
        };

    }

    @Override
    public void login(final String user, final String password, final int position) {
        view.showDialog();
        LoginPageReq accessToken = new LoginPageReq();
        accessToken.setUserName(user);
        accessToken.setPassWord(password);

        if (position == 0) {

            HomeREService.getInstance(context)
                    .merchLogin(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<MerchLoginResp>(context) {
                        @Override
                        protected void doOnNext(MerchLoginResp value) {
                            if (value.getCode() == 0) {
                                ConstantUtils.SYS_NO = value.getData().getSysNo() + "";
                                ConstantUtils.CUSTOMER = value.getData().getCustomer();
                                ConstantUtils.CUSTOMERS_TYPE = value.getData().getCustomersType() + "";
                                ConstantUtils.NEW_TYPE = value.getData().getCustomersType() + "";
                                CommonShare.putTypeData(context, ConstantUtils.NEW_TYPE);
                                KLog.i("user---"+user);
                                KLog.i("user---"+value.getData().getCustomer());
                                //TODO userDisplay
                                ConstantUtils.USER = user;
//                              ConstantUtils.EMAIL = data.getString("Email");
                                String[] key = new String[]{"User", "SysNo", "Customer", "CustomersType", "PassWord", "Position"};
                                String[] valueA = new String[]{user, ConstantUtils.SYS_NO, ConstantUtils.CUSTOMER, ConstantUtils.CUSTOMERS_TYPE, password, position + ""};
                                CommonShare.putLoginData(context, key, valueA);
                                view.loginOnSuccess();
                                String newType = CommonShare.getTypeData(context);
                                KLog.i(newType);
                                //done switch version
                                if (!DycLibIntent.hasModule()){
                                    KLog.i("startService");
                                    if (TextUtils.equals("3", newType)) {
                                        JPushInterface.resumePush(context);
                                    }
                                }
                                initMarkWX();
                            } else {
                                view.loginOnError();
                                view.dismissDialog();
                            }
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                            view.loginOnError();
                            view.dismissDialog();
                        }
                    });

        } else {
            KLog.i(accessToken);
            HomeREService.getInstance(context)
                    .staffLogin(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<StaffLoginResp>(context) {
                        @Override
                        protected void doOnNext(StaffLoginResp value) {
                            if (value.getCode() == 0) {
                                KLog.i(value);
                                ConstantUtils.SYS_NO = value.getData().getSysNO() + "";
                                ConstantUtils.CUSTOMER = value.getData().getDisplayName();
                                ConstantUtils.CUSTOMERS_TYPE = "";
                                getType(ConstantUtils.SYS_NO);
                                //TODO userDisplay
                                KLog.i("user---"+user);
                                KLog.i("user---"+value.getData().getLoginName());
                                ConstantUtils.USER = user;
//                                    ConstantUtils.EMAIL = data.getString("Email");
                                String[] key = new String[]{"User", "SysNo", "Customer", "CustomersType", "PassWord", "Position"};
                                String[] valueA = new String[]{user, ConstantUtils.SYS_NO, ConstantUtils.CUSTOMER, ConstantUtils.CUSTOMERS_TYPE, password, position + ""};
                                CommonShare.putLoginData(context, key, valueA);
                            } else {
                                view.loginOnError();
                                view.dismissDialog();
                            }
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                            view.loginOnError();
                            view.dismissDialog();
                        }
                    });

        }

    }

    @Override
    public void initData() {
        loginModel.initTabList();
        loginModel.initViewList();
    }

    @Override
    public void setAdapter() {
        List<String> list_tabs = loginModel.loadInstance().getTab_list();
        List<View> list_views = loginModel.loadInstance().getView_list();
        if (list_tabs.size() != 0 && list_views.size() != 0) {
            view.setDataAdapter(list_tabs, list_views);
        }
        KLog.i(4 + "posssssss");
    }

    private void sentBroadcast(Context context) {
        Intent intent = new Intent(ACTION_INTENT_TEST);
        intent.putExtra("alert", true);
        context.sendBroadcast(intent);
    }

    public void getType(String sys_no) {
        RoleTypeReq accessToken = new RoleTypeReq();
        accessToken.setSystemUserSysNo(Long.parseLong(sys_no));
        HomeREService.getInstance(context)
                .getRoleType(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<RoleTypeResp>(context) {
                    @Override
                    protected void doOnNext(RoleTypeResp value) {
                        if (!TextUtils.isEmpty(value.getCustomerSysNo())) {
                            String id = value.getCustomerSysNo();
                            ConstantUtils.HIGHER_SYS_NO = id;
                            String type = value.getCustomersType();
                            ConstantUtils.STAFF_TYPE = type;
                            ConstantUtils.NEW_TYPE = Integer.parseInt(type) + 2 + "";
                            KLog.i(ConstantUtils.NEW_TYPE + "==+[[==");
                            CommonShare.putTypeData(context, ConstantUtils.NEW_TYPE);
                            view.loginOnSuccess();
                            KLog.i(1);
                            String newType = CommonShare.getTypeData(context);
                            KLog.i(newType);
                            //done switch version
                            if (!DycLibIntent.hasModule()){
                                KLog.i("startService");
                                if (TextUtils.equals("3", newType)) {
                                    JPushInterface.resumePush(context);
                                }
                            }
                            initMarkWX();
                            KLog.i(2);
                            String name = value.getCustomerName();
                            ConstantUtils.HIGHER_NAME = name;
                            String[] key = new String[]{"HIGHER_SYS_NO", "STAFF_TYPE", "HIGHER_NAME"};
                            String[] valueA = new String[]{ConstantUtils.HIGHER_SYS_NO, ConstantUtils.STAFF_TYPE, ConstantUtils.HIGHER_NAME};
                            CommonShare.putHomeData(context, key, valueA);
                            if (TextUtils.equals("3", ConstantUtils.NEW_TYPE)) {
                                getJpushID();
                                //Done switch version
                                if (!DycLibIntent.hasModule()){
                                    KLog.i("startService");
                                    JPushInterface.init(context);
                                    JPUtils.setTags(context, ConstantUtils.SYS_NO);
                                }
                            }
                        } else {
                            Toast.makeText(context, "获取员工类别失败！", Toast.LENGTH_SHORT).show();
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
                        }

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Toast.makeText(context, "获取员工类别失败！", Toast.LENGTH_SHORT).show();
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
                    }
                });


    }

    private void getJpushID() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
                time = time - 1;

            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 2000);

    }

    private void insertJpushId(String sysNo, String jpushID) {
        JidInsertReq accessToken = new JidInsertReq();
        accessToken.setSystemUserSysNo(sysNo);
        accessToken.setRegistrationId(jpushID);
        HomeREService.getInstance(context)
                .jidInsert(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<JidInsertResp>(context) {
                    @Override
                    protected void doOnNext(JidInsertResp value) {
                        long code = value.getCode();
                        if (code == 0) {
                            CommonShare.putJPushIDBoolean(context, true);
                            KLog.i("insert-success");
                        } else {
                            sentBroadcast(context);
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        sentBroadcast(context);
                    }
                });
    }

    private void initMarkWX() {
        ConstantUtils.PASSAGWE_WAYS.clear();
        ConstantUtils.WX_Passageway.clear();
        ConstantUtils.WX_Type.clear();
        ConstantUtils.WX_TypeName.clear();
        ConstantUtils.WX_PassageWayName.clear();
        ConstantUtils.ZFB_Passageway.clear();
        ConstantUtils.ZFB_Type.clear();
        ConstantUtils.ZFB_TypeName.clear();
        ConstantUtils.ZFB_PassageWayName.clear();
        HomeREService.getInstance(context)
                .getPassageWay(new StaffInfoReq())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new BaseObserver<JsonArray>(context) {
                    @Override
                    protected void doOnNext(JsonArray value) {
                        try {
                            String result = value.toString();
                            if (!TextUtils.isEmpty(result) && result.length() > 2) {
                                if (JsonUtils.isGoodJson(result)) {
                                    ConstantUtils.isGetPassageMark = true;
                                    CommonShare.putPassageDataW(context, result);
                                    saveWxPassage(result);
                                } else {
                                    try {
                                        saveWxPassage(context.getString(R.string.store_wx_json));
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            } else {
                                String s = CommonShare.getPassageDataW(context);
                                if (TextUtils.isEmpty(s)) {
                                    try {
                                        saveWxPassage(context.getString(R.string.store_wx_json));
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                } else {
                                    try {
                                        saveWxPassage(s);
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                        try {
                                            saveWxPassage(context.getString(R.string.store_wx_json));
                                        } catch (JSONException e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            KLog.e("exception", e.getMessage());
                            String s = CommonShare.getPassageDataW(context);
                            if (TextUtils.isEmpty(s)) {
                                try {
                                    saveWxPassage(context.getString(R.string.store_wx_json));
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            } else {
                                try {
                                    saveWxPassage(s);
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                    try {
                                        saveWxPassage(context.getString(R.string.store_wx_json));
                                    } catch (JSONException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        String s = CommonShare.getPassageDataW(context);
                        if (TextUtils.isEmpty(s)) {
                            try {
                                saveWxPassage(context.getString(R.string.store_wx_json));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            try {
                                saveWxPassage(s);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                                try {
                                    saveWxPassage(context.getString(R.string.store_wx_json));
                                } catch (JSONException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }

                    }
                });
    }

    private void saveWxPassage(String result) throws JSONException {
        if (!isSavedWx) {
            isSavedWx = true;
            ConstantUtils.PASSAGWE_WAYS.clear();
            ConstantUtils.WX_Passageway.clear();
            ConstantUtils.WX_Type.clear();
            ConstantUtils.WX_TypeName.clear();
            ConstantUtils.WX_PassageWayName.clear();
            ConstantUtils.ZFB_Passageway.clear();
            ConstantUtils.ZFB_Type.clear();
            ConstantUtils.ZFB_TypeName.clear();
            ConstantUtils.ZFB_PassageWayName.clear();
            ArrayList<String> listPassageWay = new ArrayList();
            ArrayList<String> listType = new ArrayList();
            ArrayList<String> listTypeName = new ArrayList();
            ArrayList<String> listPassageWayName = new ArrayList();
            ArrayList<String> zListPassageWay = new ArrayList();
            ArrayList<String> zListType = new ArrayList();
            ArrayList<String> zListTypeName = new ArrayList();
            ArrayList<String> zListPassageWayName = new ArrayList();
            JsonParser parser = new JsonParser();
            JsonArray jsonArrayS = parser.parse(result).getAsJsonArray();
            Gson gson = new Gson();
            for (JsonElement user : jsonArrayS) {
                filterState(listPassageWay, listType, listTypeName, listPassageWayName, gson, user, zListPassageWay, zListPassageWayName, zListType, zListTypeName);
                KLog.i(ConstantUtils.PASSAGWE_WAYS);
            }
            KLog.i(result);
            ConstantUtils.WX_Passageway = listPassageWay;
            ConstantUtils.WX_Type = listType;
            ConstantUtils.WX_TypeName = listTypeName;
            ConstantUtils.WX_PassageWayName = listPassageWayName;
            ConstantUtils.ZFB_Passageway = zListPassageWay;
            ConstantUtils.ZFB_Type = zListType;
            ConstantUtils.ZFB_TypeName = zListTypeName;
            ConstantUtils.ZFB_PassageWayName = zListPassageWayName;
            KLog.i(ConstantUtils.WX_Passageway);
            KLog.i(ConstantUtils.WX_Type);
            KLog.i(ConstantUtils.WX_TypeName);
            KLog.i(ConstantUtils.WX_PassageWayName);
            KLog.i(ConstantUtils.ZFB_Passageway);
            KLog.i(ConstantUtils.ZFB_Type);
            KLog.i(ConstantUtils.ZFB_TypeName);
            KLog.i(ConstantUtils.ZFB_PassageWayName);
        } else {
            KLog.i("ERROR1");
        }
    }

    private void filterState(ArrayList<String> listPassageWay, ArrayList<String> listType, ArrayList<String> listTypeName, ArrayList<String> listPassageWayName, Gson gson, JsonElement user, ArrayList<String> zListPassageWay, ArrayList<String> zListPassageWayName, ArrayList<String> zListType, ArrayList<String> zListTypeName) {
        PassageWay userBean = gson.fromJson(user, PassageWay.class);
        if (BuildConfig.DEBUG) {
            if (!ConstantUtils.PASSAGWE_WAYS.contains(userBean)) {
                ConstantUtils.PASSAGWE_WAYS.add(userBean);
            } else {
                KLog.i("ERROR2");
            }
            String remarks = userBean.getRemarks();
            if (TextUtils.equals("WX", remarks)) {
                if (!listType.contains(userBean.getType())) {
                    listPassageWay.add(userBean.getPassageWay());
                    listType.add(userBean.getType());
                    listTypeName.add(userBean.getTypeName());
                    listPassageWayName.add(userBean.getPassageWayName());
                } else {
                    KLog.i("ERROR3");
                }
            } else if (TextUtils.equals("AliPay", remarks)) {
                if (!zListType.contains(userBean.getType())) {
                    zListPassageWay.add(userBean.getPassageWay());
                    zListType.add(userBean.getType());
                    zListTypeName.add(userBean.getTypeName());
                    zListPassageWayName.add(userBean.getPassageWayName());
                } else {
                    KLog.i("ERROR4");
                }
            } else {
                KLog.i("ERROR5");
            }

        } else {
            if (TextUtils.equals(userBean.getState(), "0")) {
                if (!ConstantUtils.PASSAGWE_WAYS.contains(userBean)) {
                    ConstantUtils.PASSAGWE_WAYS.add(userBean);
                }
                String remarks = userBean.getRemarks();
                if (TextUtils.equals("WX", remarks)) {
                    if (!listType.contains(userBean.getType())) {
                        listPassageWay.add(userBean.getPassageWay());
                        listType.add(userBean.getType());
                        listTypeName.add(userBean.getTypeName());
                        listPassageWayName.add(userBean.getPassageWayName());
                    }
                } else if (TextUtils.equals("AliPay", remarks)) {
                    if (!zListType.contains(userBean.getType())) {
                        zListPassageWay.add(userBean.getPassageWay());
                        zListType.add(userBean.getType());
                        zListTypeName.add(userBean.getTypeName());
                        zListPassageWayName.add(userBean.getPassageWayName());
                    }
                } else {
                    KLog.e("error-");
                }
            }
        }
    }

}
