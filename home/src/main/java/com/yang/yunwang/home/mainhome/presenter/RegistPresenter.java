package com.yang.yunwang.home.mainhome.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.homeservice.HomeREService;
import com.yang.yunwang.home.mainhome.bean.CommonClassProReq;
import com.yang.yunwang.home.mainhome.bean.CusClassResp;
import com.yang.yunwang.home.mainhome.bean.ProviceResp;
import com.yang.yunwang.home.mainhome.bean.regist.CusRegistReq;
import com.yang.yunwang.home.mainhome.bean.regist.CusRegistResp;
import com.yang.yunwang.home.mainhome.bean.regist.InsertDefaultRoleReq;
import com.yang.yunwang.home.mainhome.bean.regist.InsertPassageReq;
import com.yang.yunwang.home.mainhome.bean.regist.InsertRateReq;
import com.yang.yunwang.home.mainhome.bean.regist.RegistCommonResp;
import com.yang.yunwang.home.mainhome.contract.RegistContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 */

public class RegistPresenter implements RegistContract.Presenter {
    private final RegistContract.View view;
    private final Context context;
    private ProgressDialog progressDialog;
    private String bankTypeWX;
    private String bankTypeZFB;
    private String tenantsRateA;
    private String tenantsRateAZ;
    private String sysNO;
    private String wxType;
    private String zfbType;

    public RegistPresenter(RegistContract.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        progressDialog = new ProgressDialog(context);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void initData() {
        getProvinces();
        getFClass();
    }


    private void getFClass() {
        CommonClassProReq accessToken = new CommonClassProReq();
        accessToken.setClassID(0L);
        HomeREService.getInstance(context)
                .getCusClass(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<CusClassResp>>(context) {
                    @Override
                    protected void doOnNext(ArrayList<CusClassResp> value) {
                        if (value != null && value.size() > 0) {
                            if (value.size() > 0) {
                                List<String> sysNo = new ArrayList<String>();
                                List<String> classID = new ArrayList<String>();
                                List<String> className = new ArrayList<String>();
                                for (int i = 0; i < value.size(); i++) {
                                    sysNo.add(value.get(i).getSysNo());
                                    classID.add(value.get(i).getClassID());
                                    className.add(value.get(i).getClassName());
                                }
                                view.setFClass(sysNo, classID, className);
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });


    }

    private void getProvinces() {
        CommonClassProReq accessToken = new CommonClassProReq();
        accessToken.setParentId(0 + "");
        HomeREService.getInstance(context)
                .getCusProvice(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<ProviceResp>>(context) {
                    @Override
                    protected void doOnNext(ArrayList<ProviceResp> value) {
                        if (value != null && value.size() > 0) {

                            if (value.size() > 0) {
                                List<String> sysNo = new ArrayList<String>();
                                List<String> parentId = new ArrayList<String>();
                                List<String> addressName = new ArrayList<String>();
                                for (int i = 0; i < value.size(); i++) {
                                    sysNo.add(value.get(i).getSysNo() + "");
                                    parentId.add(value.get(i).getParentId() + "");
                                    addressName.add(value.get(i).getAddressName());
                                }
                                view.setProvince(sysNo, parentId, addressName);
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }

    @Override
    public void changeInfo(String userName, String passWord, String shopName, String email,
                           String tenantsRate, String mailCode, String addressDetail,
                           String addressCode, String faxNum, String staffId, String classCode,
                           String classNames, String bankType, String nickNameS, String tenantsRateZ,
                           String zfbPassageWay, String wxType, String zfbType) {
        this.bankTypeWX = bankType;
        this.bankTypeZFB = zfbPassageWay;
        this.wxType = wxType;
        this.zfbType = zfbType;
        this.tenantsRateA = tenantsRate;
        this.tenantsRateAZ = tenantsRateZ;
        CusRegistReq map = new CusRegistReq();
        map.setCustomer(userName);
        map.setPwd(passWord);
        map.setCustomerName(shopName);
        map.setNickName(nickNameS);
        map.setEmail(email);
        map.setPhone(userName);
        map.setFax(faxNum);
        map.setDwellAddress(addressDetail);
        map.setDwellAddressID(addressCode);
        map.setDwellZip(mailCode);
        map.setCustomersType("1");
        map.setVipCustomerType("1");
        //Done switch version
        if (DycLibIntent.hasModule()){
            map.setCustomerFieldTwo(context.getString(R.string.drigistconfig));
        }else {
            map.setCustomerFieldTwo(context.getString(R.string.rigistconfig));
        }
        map.setSystemUserSysNo(staffId);
        map.setSystemClassName(classNames);
        map.setSystemClassID(classCode);
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.show();
        HomeREService.getInstance(context)
                .registCus(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<CusRegistResp>(context) {
                    @Override
                    protected void doOnNext(CusRegistResp result) {
                        if (result != null && !TextUtils.isEmpty(result.toString())) {
                            KLog.i("zcSCC_____" + result);
                            long code_result = result.getCode();
                            String description = result.getDescription();
                            if (code_result == 0) {
                                RegistPresenter.this.sysNO = result.getData().getCustomerServiceSysNo();
                                view.onSuccess(description, result.getData().getCustomerServiceSysNo());
                            } else {
                                view.onError(description);
                            }
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        progressDialog.dismiss();
                    }
                });

    }


    @Override
    public void insertRole(String customer, String description) {
        KLog.i("获取NO");
        KLog.i(sysNO);
        getCustomerNo(customer, description, sysNO);
    }

    private void getCustomerNo(String customer, final String description, String sysNO) {
        inROLE(sysNO, description);
    }

    private void inROLE(String user, final String description) {
        ArrayList<InsertDefaultRoleReq> params = new ArrayList<>();
        ArrayList<String> type = new ArrayList<>();
        if (!TextUtils.isEmpty(tenantsRateA)) {
            type.add(wxType);
        }
        if (!TextUtils.isEmpty(tenantsRateAZ)) {
            type.add(zfbType);
        }
        for (int i = 0; i < type.size(); i++) {
            InsertDefaultRoleReq param = new InsertDefaultRoleReq();
            param.setCustomerServiceSysNo(user);
            param.setType(type.get(i));
            param.setEditUser(ConstantUtils.SYS_NO);
            param.setInUser("1");
            params.add(param);
        }

        HomeREService.getInstance(context)
                .insertDefaultRole(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<RegistCommonResp>(context) {
                    @Override
                    protected void doOnNext(RegistCommonResp result) {
                        if (result != null && !TextUtils.isEmpty(result.toString())) {
                            long code = result.getCode();
                            if (code == 0) {
                                Toast.makeText(context, description, Toast.LENGTH_SHORT).show();
                            } else if (code == 1) {
                                Toast.makeText(context, "操作失败: " + result.getDescription(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });


    }


    @Override
    public void insertRate(String string) {
//        微信（102） 支付宝（103） 兴业微信（104） 兴业支付宝（105） 浦发微信（106） 浦发支付宝（107）
        ArrayList<InsertRateReq> accessToken = new ArrayList<>();
        if (!TextUtils.isEmpty(tenantsRateA)) {
            InsertRateReq mapA = new InsertRateReq();
            mapA.setCustomerSysNo(string);
            mapA.setPassageWay(bankTypeWX);
            mapA.setType(wxType);
            mapA.setRate(tenantsRateA);
            accessToken.add(mapA);
        }

        if (!TextUtils.isEmpty(tenantsRateAZ)) {
            InsertRateReq mapZ = new InsertRateReq();
            mapZ.setCustomerSysNo(string);
            mapZ.setPassageWay(bankTypeZFB);
            mapZ.setType(zfbType);
            mapZ.setRate(tenantsRateAZ);
            accessToken.add(mapZ);
        }

        HomeREService.getInstance(context)
                .insertCusRate(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<RegistCommonResp>(context) {
                    @Override
                    protected void doOnNext(RegistCommonResp result) {
                        if (result != null && !TextUtils.isEmpty(result.toString())) {
                            if (TextUtils.equals("0", result.getCode() + "")) {
                                KLog.i(result.getDescription());
                            } else {
                                KLog.i(result.getDescription());
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }

    @Override
    public void insertPassgeWay(final String string) {
        if (!TextUtils.isEmpty(tenantsRateA)) {
            InsertPassageReq accessToken = new InsertPassageReq();
            accessToken.setCustomerSysNo(string);
            accessToken.setPassageWay(bankTypeWX);
            accessToken.setType(wxType);
            accessToken.setRemarks("WX");
            accessToken.setRate(tenantsRateA);
            HomeREService.getInstance(context)
                    .insertCusPassageWay(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<RegistCommonResp>(context) {
                        @Override
                        protected void doOnNext(RegistCommonResp result) {
                            if (result != null && !TextUtils.isEmpty(result.toString())) {
                                if (TextUtils.equals("0", result.getCode() + "")) {
                                    KLog.i(result.getDescription());
                                    if (!TextUtils.isEmpty(tenantsRateAZ)) {
                                        insertZPassgeWay(string);
                                    }
                                } else {
                                    KLog.i(result.getDescription());
                                }
                            }
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                        }
                    });
        } else {
            insertZPassgeWay(string);
        }

    }

    private void insertZPassgeWay(String string) {
        InsertPassageReq accessToken = new InsertPassageReq();
        accessToken.setCustomerSysNo(string);
        accessToken.setPassageWay(bankTypeZFB);
        accessToken.setType(zfbType);
        accessToken.setRemarks("AliPay");
        accessToken.setRate(tenantsRateAZ);
        HomeREService.getInstance(context)
                .insertCusPassageWay(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<RegistCommonResp>(context) {
                    @Override
                    protected void doOnNext(RegistCommonResp result) {
                        if (result != null && !TextUtils.isEmpty(result.toString())) {
                            if (TextUtils.equals("0", result.getCode() + "")) {
                                KLog.i(result.getDescription());
                            } else {
                                KLog.i(result.getDescription());
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });


    }
}
