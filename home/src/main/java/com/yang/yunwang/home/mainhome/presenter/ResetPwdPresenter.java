package com.yang.yunwang.home.mainhome.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.homeservice.HomeREService;
import com.yang.yunwang.home.mainhome.bean.ResetPwdReq;
import com.yang.yunwang.home.mainhome.bean.ResetPwdResp;
import com.yang.yunwang.home.mainhome.contract.ResetPwdContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/16.
 */

public class ResetPwdPresenter implements ResetPwdContract.Presenter {
    private final ResetPwdContract.View view;
    private final Context context;

    public ResetPwdPresenter(ResetPwdContract.View view, Context context) {
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
    public void changePassword(String old_password, String new_password) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.show();
        BaseObserver<ResetPwdResp> observer = new BaseObserver<ResetPwdResp>(context) {
            @Override
            protected void doOnNext(ResetPwdResp value) {
                String code = value.getCode() + "";
                String description = value.getDescription();
                if (code.equals("0")) {
                    Toast.makeText(context, "修改密码成功！", Toast.LENGTH_SHORT).show();
                    view.back();
                } else {
                    Toast.makeText(context, description, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                progressDialog.dismiss();
            }
        };
        ResetPwdReq accessToken = new ResetPwdReq();
        accessToken.setSysNo(ConstantUtils.SYS_NO);
        accessToken.setOldPassWord(old_password);

        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        if (customer_type != null && !customer_type.equals("")) {
//            actionUrl = ConstantUtils.URL_MERCHANT_PASSWORD;
            accessToken.setNewPassWord(new_password);
            HomeREService.getInstance(context)
                    .resetCusPwd(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {
//            actionUrl = ConstantUtils.URL_STAFF_PASSWORD;
            accessToken.setPassword(new_password);
            HomeREService.getInstance(context)
                    .resetStaffPwd(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }


    }
}
