package com.yang.yunwang.home.mainhome.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.socks.library.KLog;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.homeservice.HomeREService;
import com.yang.yunwang.home.mainhome.bean.CommonClassProReq;
import com.yang.yunwang.home.mainhome.bean.CusClassResp;
import com.yang.yunwang.home.mainhome.bean.ProviceResp;
import com.yang.yunwang.home.mainhome.bean.update.CommonUpdateResp;
import com.yang.yunwang.home.mainhome.bean.update.MerchUpdateReq;
import com.yang.yunwang.home.mainhome.bean.update.StaffUpdateReq;
import com.yang.yunwang.home.mainhome.bean.update.init.PagingInfo;
import com.yang.yunwang.home.mainhome.bean.update.init.UpdateInitReq;
import com.yang.yunwang.home.mainhome.bean.update.init.merch.MerchInitResp;
import com.yang.yunwang.home.mainhome.bean.update.init.merch.Model;
import com.yang.yunwang.home.mainhome.bean.update.init.staff.StaffInitResp;
import com.yang.yunwang.home.mainhome.bean.update.rate.CommonRateReq;
import com.yang.yunwang.home.mainhome.bean.update.rate.RateResp;
import com.yang.yunwang.home.mainhome.contract.UpdateInfoContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/18.
 */

public class UpdateInfoPresenter implements UpdateInfoContract.Presenter {
    private final UpdateInfoContract.View view;
    private final Context context;
    private ProgressDialog progressDialog;
    private boolean fromMerchant;

    public UpdateInfoPresenter(UpdateInfoContract.View view, Context context) {
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
        fromMerchant = view.loadIntent().getBooleanExtra("from_merchant", false);
        initPersonal(fromMerchant);

    }

    @Override
    public void changeInfo(String userName, String passWord, String shopName, String email, String tenantsRate, String mailCode, String addressDetail, String addressCode, String faxNum, String staffId) {

    }


    private void initPersonal(final Boolean fromMerchant) {
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.show();
        if (fromMerchant) {
            UpdateInitReq accessToken = new UpdateInitReq();
            accessToken.setSysNo(ConstantUtils.SYS_NO);
            PagingInfo pagingInfo = new PagingInfo();
            pagingInfo.setPageNumber(0L);
            pagingInfo.setPageSize(1L);
            accessToken.setPagingInfo(pagingInfo);
            HomeREService.getInstance(context)
                    .initMerchPersonal(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<MerchInitResp>(context) {
                        @Override
                        protected void doOnNext(MerchInitResp value) {
                            if (value != null && !TextUtils.isEmpty(value.toString())) {
                                List<Model> model = value.getModel();
                                if (model.size() != 0) {
                                    KLog.i(model);
                                    Model data = model.get(0);
                                    String dwellAddressIDSet = "0|0|0";
                                    String classIDs = "0|0|0";
                                    if (fromMerchant) {
                                        String user = data.getCustomer();
                                        String user_id = "商户";
                                        String tel = data.getPhone() == null ? "" : data.getPhone();
                                        String customer = data.getCustomerName() == null ? "" : data.getCustomerName();
                                        String email = data.getEmail() == null ? "" : data.getEmail();
                                        String nickName = data.getNickName() == null ? "" : data.getNickName();
                                        String fax = data.getFax() == null ? "" : data.getFax();
                                        String dwellAddress = data.getDwellAddress() == null ? "" : data.getDwellAddress();
                                        String rate = data.getRate() == null ? "" : data.getRate();
                                        String dwellAddressID = data.getDwellAddressID() == null ? "" : data.getDwellAddressID();
                                        String prociveID = data.getProvince() == null ? "" : data.getProvince();
                                        String countyID = data.getCounty() == null ? "" : data.getCounty();
                                        String cityID = data.getCity() == null ? "" : data.getCity();
                                        String classID = data.getSystemClassID() == null ? "" : data.getSystemClassID();
                                        dwellAddressIDSet = data.getDwellAddressID() == null ? "" : data.getDwellAddressID();
                                        classIDs = data.getSystemClassID() == null ? "" : data.getSystemClassID();
                                        String fClass = data.getClassOne() == null ? "" : data.getClassOne();
                                        String sClass = data.getClassTwo() == null ? "" : data.getClassTwo();
                                        String tClass = data.getClassThree() == null ? "" : data.getClassThree();
                                        String typeBB = data.getCustomerFieldOne() == null ? "" : data.getCustomerFieldOne();
                                        String typeC = data.getCustomerFieldThree() == null ? "" : data.getCustomerFieldThree();
//                                KLog.i(typeBB + "__________=====");
                                        view.setMerchInfo(user, user_id, tel, customer, email, fax, dwellAddress, rate, dwellAddressID, prociveID, cityID, countyID, fClass, sClass, tClass, classID, typeBB, nickName, typeC);
                                    }
                                    getProvinces(dwellAddressIDSet);
                                    getFClass(classIDs);
                                } else {
                                    view.infoError();
                                }
                            } else {
                                view.infoError();
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                            view.infoError();
                            progressDialog.dismiss();
                        }
                    });
        } else {

            UpdateInitReq accessToken = new UpdateInitReq();
            accessToken.setSysNo(ConstantUtils.SYS_NO);
            HomeREService.getInstance(context)
                    .initStaffPersonal(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<StaffInitResp>(context) {
                        @Override
                        protected void doOnNext(StaffInitResp value) {
                            if (value != null && !TextUtils.isEmpty(value.toString())) {

                                List<com.yang.yunwang.home.mainhome.bean.update.init.staff.Model> model = value.getModel();
                                if (model.size() != 0) {
                                    KLog.i(model);
                                    com.yang.yunwang.home.mainhome.bean.update.init.staff.Model data = model.get(0);
                                    String dwellAddressIDSet;
                                    String classIDs = "0|0|0";
                                    String user = data.getLoginName();
                                    String user_id = "商户员工";
                                    String tel = data.getPhoneNumber() == null ? "" : data.getPhoneNumber();
                                    String customer = data.getDisplayName() == null ? "" : data.getDisplayName();
                                    String email = data.getEmail() == null ? "" : data.getEmail();
                                    String store_id = data.getAlipayStoreId() == null ? "" : data.getAlipayStoreId();
                                    String dwellAddress = data.getDwellAddress() == null ? "" : data.getDwellAddress();
                                    String rate = data.getRate() == null ? "" : data.getRate();
                                    String dwellAddressID = data.getDwellAddressID() == null ? "" : data.getDwellAddressID();
                                    dwellAddressIDSet = data.getDwellAddressID() == null ? "" : data.getDwellAddressID();
                                    String prociveID = data.getProvince() == null ? "" : data.getProvince();
                                    String countyID = data.getCounty() == null ? "" : data.getCounty();
                                    String cityID = data.getCity() == null ? "" : data.getCity();
                                    view.setInfo(user, user_id, tel, customer, email, store_id, dwellAddress, rate, dwellAddressID, prociveID, cityID, countyID);
                                    getProvinces(dwellAddressIDSet);
                                    getFClass(classIDs);
                                } else {
                                    view.infoError();
                                }
                            } else {
                                view.infoError();
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                            view.infoError();
                            progressDialog.dismiss();
                        }
                    });
        }


    }

    private void getFClass(final String classIDs) {
        final String[] clIds = classIDs.split("[|]");

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
                                view.setFClass(sysNo, classID, className, clIds);
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }

    private void getProvinces(String dwellAddressIDSet) {
        final String[] userIds = dwellAddressIDSet.split("[|]");

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
                                view.setProvince(sysNo, parentId, addressName, userIds);
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });

    }

    @Override
    public void changeInfo(JSONObject map, boolean fromMerchant) {
        if (fromMerchant) {
            Gson gson = new Gson();
            MerchUpdateReq accessToken = gson.fromJson(map.toString(), MerchUpdateReq.class);
            HomeREService.getInstance(context)
                    .updateMerchInfo(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<CommonUpdateResp>(context) {
                        @Override
                        protected void doOnNext(CommonUpdateResp value) {
                            if (value != null && !TextUtils.isEmpty(value.toString())) {
                                long code_result = value.getCode();
                                String description = value.getDescription();
                                if (code_result == 0) {
                                    view.onSuccess(description);
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
        } else {
            Gson gson = new Gson();
            StaffUpdateReq accessToken = gson.fromJson(map.toString(), StaffUpdateReq.class);
            HomeREService.getInstance(context)
                    .updateStaffInfo(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<CommonUpdateResp>(context) {
                        @Override
                        protected void doOnNext(CommonUpdateResp value) {
                            if (value != null && !TextUtils.isEmpty(value.toString())) {
                                long code_result = value.getCode();
                                String description = value.getDescription();
                                if (code_result == 0) {
                                    view.onSuccess(description);
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

        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.show();


    }

    @Override
    public void getSHRate() {
        CommonRateReq accessToken = new CommonRateReq();
        accessToken.setCustomerSysNo(ConstantUtils.SYS_NO);
        HomeREService.getInstance(context)
                .initMerchRate(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<RateResp>>(context) {
                    @Override
                    protected void doOnNext(ArrayList<RateResp> value) {
                        if (value != null && value.size() > 0) {
                            Gson gson = new Gson();
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = new JSONArray(gson.toJson(value));
                                view.setRateInfo(jsonArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "获取费率信息失败,请返回重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Toast.makeText(context, "获取费率信息失败,请返回重试", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
