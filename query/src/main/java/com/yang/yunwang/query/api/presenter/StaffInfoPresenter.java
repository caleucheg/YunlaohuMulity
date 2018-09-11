package com.yang.yunwang.query.api.presenter;

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
import com.yang.yunwang.query.api.QueryReService;
import com.yang.yunwang.query.api.bean.allcoate.AllcoateInitLeftResp;
import com.yang.yunwang.query.api.bean.allcoate.AllcoateInitReq;
import com.yang.yunwang.query.api.bean.allcoate.AllcoateInitRightResp;
import com.yang.yunwang.query.api.bean.allcoate.Model;
import com.yang.yunwang.query.api.bean.allcoate.PagingInfo;
import com.yang.yunwang.query.api.contract.StaffInfoContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/12.
 */

public class StaffInfoPresenter implements StaffInfoContract.Presenter {
    private final StaffInfoContract.View view;
    private final Context context;

    public StaffInfoPresenter(StaffInfoContract.View view, Context context) {
        this.view = view;
        this.context = context;
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
//        String login_name = intent.getStringExtra("login_name");
//        final ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
//        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
//        progressDialog.show();
        com.yang.yunwang.query.api.bean.staffsearch.Model item= (com.yang.yunwang.query.api.bean.staffsearch.Model) intent.getSerializableExtra("staff_bean");
        String staff_id = item.getSysNO()+"";
        String login_name = item.getLoginName();
        String display_name = item.getDisplayName();
        String tel = item.getPhoneNumber();
        String email = item.getEmail();
        String shop_id = item.getAlipayStoreId()==null ? "" : item.getAlipayStoreId();
        String staff_time = "";
        String date_temp = item.getInDate();
        if (date_temp!=null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            String date = date_temp.substring(6, date_temp.length() - 2);
            String param_date = format.format(new Date(Long.parseLong(date)));
            staff_time = param_date;
        } else {
            staff_time = "";
        }
        view.setInfo(staff_id, login_name, display_name, tel, email, shop_id, staff_time);
    }

    private void rightList(BaseObserver<AllcoateInitRightResp> listener, String customersTopSysNo, String key2, String url2, boolean fromHome) {
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

    @Override
    public void initDATAString(String key1, String url1, final String key2, final String url2, final boolean fromHome, String staffId) {
        final MyBundle intent1 = new MyBundle();//this, AllocateActivity.class
        intent1.put("staff_id", staffId);
        intent1.put("from_home", true);
        intent1.put("from_staff", true);
        final BaseObserver<AllcoateInitRightResp> baseObserver = new BaseObserver<AllcoateInitRightResp>(context) {
            @Override
            protected void doOnNext(AllcoateInitRightResp value) {
                if (value != null && !TextUtils.isEmpty(value.toString())) {
                    List<Model> jsonArray = value.getModel();
                    if (jsonArray.size() > 0) {
                        Gson gson=new Gson();
                        String value1 = gson.toJson(value);
                        intent1.put("right_data", value1);

                    } else {
                        Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
                    }
                    intent1.put("from_home", fromHome);
                    OrdersIntent.getAllcoateList(intent1);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                KLog.e("error", responeThrowable.getMessage());
                Toast.makeText(context,"获取权限失败,请返回重试",Toast.LENGTH_SHORT).show();
            }
        };
        BaseObserver<ArrayList<AllcoateInitLeftResp>> baseObserver1 = new BaseObserver<ArrayList<AllcoateInitLeftResp>>(context) {
            @Override
            protected void doOnNext(ArrayList<AllcoateInitLeftResp> value) {
                if (value != null) {
                    Gson gson=new Gson();
                    String value1 = gson.toJson(value);
                    intent1.put("left_data", value1);
                    rightList(baseObserver, ConstantUtils.SYS_NO, key2, url2, fromHome);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                Toast.makeText(context,"获取权限失败,请返回重试",Toast.LENGTH_SHORT).show();
            }
        };
        liftListString(baseObserver1, staffId, key1, url1);
    }

    private void liftListString(BaseObserver<ArrayList<AllcoateInitLeftResp>> listener, String customersTopSysNo, String key1, String url1) {
        PagingInfo pagingInfo=new PagingInfo();
        pagingInfo.setPageSize(5000L);
        pagingInfo.setPageNumber(0L);
        AllcoateInitReq req=new AllcoateInitReq();
        req.setPagingInfo(pagingInfo);
        req.setSystemUserSysNo(customersTopSysNo);
        QueryReService.getInstance(context)
                .getLiftRoleList(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener);
    }
}
