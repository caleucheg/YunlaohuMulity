package com.yang.yunwang.query.api.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.QueryReService;
import com.yang.yunwang.query.api.QueryReStringService;
import com.yang.yunwang.query.api.bean.allcoate.AllcoateInitReq;
import com.yang.yunwang.query.api.bean.allcoate.AllcoateInitRightResp;
import com.yang.yunwang.query.api.bean.allcoate.PagingInfo;
import com.yang.yunwang.query.api.bean.merchinfo.MerchInfoInitReq;
import com.yang.yunwang.query.api.bean.merchinfo.MerchUpdateRateReq;
import com.yang.yunwang.query.api.bean.merchinfo.merchrole.MerchRoleLiftResp;
import com.yang.yunwang.query.api.bean.merchinfo.rates.MerchRateResp;
import com.yang.yunwang.query.api.bean.merchinfo.upuser.AllcoateUserLiftReq;
import com.yang.yunwang.query.api.bean.merchinfo.upuser.left.AllcoateUserLifResp;
import com.yang.yunwang.query.api.bean.merchinfo.upuser.right.AllcoateUserRightResp;
import com.yang.yunwang.query.api.bean.merchinfo.upuserinfo.MerchUpUserInfoResp;
import com.yang.yunwang.query.api.bean.merchsearch.Model;
import com.yang.yunwang.query.api.contract.MerchInfoContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/13.
 */

public class MerchInfoPresenter implements MerchInfoContract.Presenter {
    private final MerchInfoContract.View view;
    private final Context context;
    private ProgressDialog progressDialog;

    public MerchInfoPresenter(MerchInfoContract.View view, Context context) {
        this.view=view;
        this.context=context;
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void initData() {
        Intent intent = view.loadInstance();
        com.yang.yunwang.query.api.bean.merchsearch.Model item= (Model) intent.getSerializableExtra("shop_bean");
        String shop_id = item.getSysNo()+"";
        String shop_user = item.getCustomer();
        String shop_name = item.getCustomerName();
        String shop_tel = item.getPhone()==null ? "" : item.getPhone();
        String shop_email = item.getEmail()==null ? "" : item.getEmail();
        String shop_fax = item.getFax()==null ? "" : item.getFax();
        String rate = item.getRate()==null ? "" : item.getRate();
        String user_rate = item.getUserRate()==null ? "" : item.getUserRate();
        String register_time = item.getRegisterTime();
        String shop_time = "";
        if (register_time!=null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            String date = register_time.substring(6, register_time.length() - 2);
            String param_date = format.format(new Date(Long.parseLong(date)));
            shop_time = param_date;
        } else {
            shop_time = "";
        }
        String shop_address = item.getDwellAddress()==null ? "" : item.getDwellAddress();
        if (shop_id.equals("null")) {
            view.setNameData("");
        } else {
            getUserFromID(shop_id);
        }
        getSHRate(shop_id);
        view.setInfo(shop_id, shop_user, shop_name, shop_time, shop_tel, shop_email, shop_fax, shop_address, rate, user_rate);
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    public void getUserFromID(String id) {
        MerchInfoInitReq accessToken = new MerchInfoInitReq();
        accessToken.setCustomerServiceSysNo(id);
        QueryReService.getInstance(context)
                .getMerchUpUserInfo(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MerchUpUserInfoResp>(context) {
                    @Override
                    protected void doOnNext(MerchUpUserInfoResp value) {
                        String disName="";
                        if (value!=null&&value.getModel().size()>0){
                            disName = value.getModel().get(0).getDisplayName();
                        }
                        view.setNameData(disName);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }

    public void getSHRate(String shop_id) {
        MerchInfoInitReq accessToken = new MerchInfoInitReq();
        accessToken.setCustomerSysNo(shop_id);
        QueryReService.getInstance(context)
                .getMerchRateInfo(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<MerchRateResp>>(context) {
                    @Override
                    protected void doOnNext(ArrayList<MerchRateResp> value) {
                        if (value!=null&&value.size()>0) {
                            view.setRateInfo(value);
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Toast.makeText(context,"获取费率信息失败",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void updateRate(String rate) {
        MerchUpdateRateReq rateReq=new MerchUpdateRateReq();
        rateReq.setSysNo( view.loadInstance().getStringExtra("shop_id"));
        rateReq.setUserRate( rate);
        QueryReStringService.getInstance(context)
                .updateMerchRate(rateReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context) {
                    @Override
                    protected void doOnNext(String result) {
                        if (!TextUtils.isEmpty(result) && !result.equals("")) {
                            KLog.i(result);
                            boolean res = Boolean.parseBoolean(result);
                            if (res) {
                                Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                        view.dismissBottomSheetDialog();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
                        view.dismissBottomSheetDialog();
                    }
                });
    }

    @Override
    public void initDATA(String key1, String url1, final String key2, final String url2, final boolean fromHome) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.show();
        final MyBundle intent1 = new MyBundle();//this, AllocateActivity.class
        intent1.put("shop_id", view.loadInstance().getStringExtra("shop_id"));
        final BaseObserver<AllcoateUserRightResp> observerRight=new BaseObserver<AllcoateUserRightResp>(context) {
            @Override
            protected void doOnNext(AllcoateUserRightResp value) {
                if (value != null && !TextUtils.isEmpty(value.toString())) {
                    if ( value.getModel().size() > 0) {
                        Gson gson=new Gson();
                        String value1 = gson.toJson(value);
                        intent1.put("right_data", value1);
                    } else {
                        Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
                    }
                    if (progressDialog!=null){
                        progressDialog.dismiss();
                    }
                    intent1.put("from_home", fromHome);
                    OrdersIntent.getAllcoateList(intent1);
                }
            }
            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                if (progressDialog!=null){
                    progressDialog.dismiss();
                }
            }
        };
        BaseObserver<AllcoateUserLifResp> observer=new BaseObserver<AllcoateUserLifResp>(context) {
            @Override
            protected void doOnNext(AllcoateUserLifResp value) {
                if (value != null && !TextUtils.isEmpty(value.toString())) {
                    if (value.getModel().size()> 0) {
                        Gson gson=new Gson();
                        String value1 = gson.toJson(value);
                        intent1.put("left_data",value1);
                    } else {
                        Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
                    }
                    rightListAll(observerRight, ConstantUtils.SYS_NO, key2, url2, fromHome);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                if (progressDialog!=null){
                    progressDialog.dismiss();
                }
            }
        };
        liftList(observer, view.loadInstance().getStringExtra("shop_id"), key1, url1);
    }

    private void rightListAll(BaseObserver<AllcoateUserRightResp> observerRight, String customersTopSysNo, String key2, String url2, boolean fromHome) {
        com.yang.yunwang.query.api.bean.merchinfo.upuser.PagingInfo pagingInfo=new com.yang.yunwang.query.api.bean.merchinfo.upuser.PagingInfo();
        pagingInfo.setPageSize(5000L);
        pagingInfo.setPageNumber(0L);
        AllcoateUserLiftReq req=new AllcoateUserLiftReq();
        req.setPagingInfo(pagingInfo);
        req.setCustomerServiceSysNo(customersTopSysNo);
        QueryReService.getInstance(context)
                .getMerchAllUser(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observerRight);
    }

    public void rightList(BaseObserver<AllcoateInitRightResp> listener, String customersTopSysNo, String key2, String url2, boolean fromHome) {
        PagingInfo pagingInfo=new PagingInfo();
        pagingInfo.setPageSize(5000L);
        pagingInfo.setPageNumber(0L);
        AllcoateInitReq req=new AllcoateInitReq();
        req.setPagingInfo(pagingInfo);
        if (!fromHome) {
            req.setSystemUserSysNo(customersTopSysNo);
        }
        QueryReService.getInstance(context)
                .getRightRoleList(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener);
    }

    public void liftList(BaseObserver<AllcoateUserLifResp> listener, String customersTopSysNo, String key1, String url1) {
        com.yang.yunwang.query.api.bean.merchinfo.upuser.PagingInfo pagingInfo=new com.yang.yunwang.query.api.bean.merchinfo.upuser.PagingInfo();
        pagingInfo.setPageSize(5000L);
        pagingInfo.setPageNumber(0L);
        AllcoateUserLiftReq req=new AllcoateUserLiftReq();
        req.setPagingInfo(pagingInfo);
        req.setCustomerServiceSysNo(customersTopSysNo);
        QueryReService.getInstance(context)
                .getMerchUpUser(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener);


    }

    @Override
    public void initDATAString(String key1, String url1, final String key2, final String url2, final boolean fromHome) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.show();
        final MyBundle intent1 = new MyBundle();//this, AllocateActivity.class
        intent1.put("shop_id", view.loadInstance().getStringExtra("shop_id"));
        final BaseObserver<AllcoateInitRightResp> rightObserver=new BaseObserver<AllcoateInitRightResp>(context) {
            @Override
            protected void doOnNext(AllcoateInitRightResp value) {
                if (value != null && !TextUtils.isEmpty(value.toString())) {
                    if (value.getModel().size() > 0) {
                        Gson gson=new Gson();
                        String value1 = gson.toJson(value);
                        intent1.put("right_data", value1);

                    } else {
                        Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
                    }
                    if (progressDialog!=null){
                        progressDialog.dismiss();
                    }
                    intent1.put("from_home", fromHome);
                    OrdersIntent.getAllcoateList(intent1);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                if (progressDialog!=null){
                    progressDialog.dismiss();
                }
                Toast.makeText(context,"获取权限失败,请返回重试",Toast.LENGTH_SHORT).show();
            }
        };
        BaseObserver<ArrayList<MerchRoleLiftResp>> leftObserver=new BaseObserver<ArrayList<MerchRoleLiftResp>>(context) {
            @Override
            protected void doOnNext(ArrayList<MerchRoleLiftResp> value) {
                if (value.size()>0) {
                    Gson gson=new Gson();
                    String value1 = gson.toJson(value);
                    intent1.put("left_data", value1);
                }
                rightList(rightObserver, ConstantUtils.SYS_NO, key2, url2, fromHome);
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                progressDialog.dismiss();
                Toast.makeText(context,"获取权限失败,请返回重试",Toast.LENGTH_SHORT).show();
            }
        };
        liftListString(leftObserver, view.loadInstance().getStringExtra("shop_id"), key1, url1);
    }

    public void liftListString(BaseObserver<ArrayList<MerchRoleLiftResp>> listener, String customersTopSysNo, String key1, String url1) {
        PagingInfo pagingInfo=new PagingInfo();
        pagingInfo.setPageNumber( 0L);
        pagingInfo.setPageSize( 5000L);
        AllcoateInitReq req=new AllcoateInitReq();
        req.setPagingInfo( pagingInfo);
        req.setCustomerServiceSysNo( customersTopSysNo);
        QueryReService.getInstance(context)
                .getMerchRoleLift(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener);
    }
}
