package com.yang.yunwang.query.api.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.QrReService;
import com.yang.yunwang.query.api.bean.personalqr.PersonalQrReq;
import com.yang.yunwang.query.api.contract.PersonnalQRContract;

import java.net.URLDecoder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/9.
 */

public class PersonnalQRPresenter implements PersonnalQRContract.Presenter {
    private final PersonnalQRContract.View view;
    private final Context context;

    public PersonnalQRPresenter(PersonnalQRContract.View view, Context context) {
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
    public void requestWXURL(String money) {

    }

    @Override
    public void requestZFBURL(String money) {

    }

    @Override
    public void requestCheckURL(String sysNO) {
        //Done switch
        PersonalQrReq accessToken = new PersonalQrReq();
        accessToken.setSysNo(sysNO);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.show();
        QrReService.getInstance(context)
                .getQRCode(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context) {
                    @Override
                    protected void doOnNext(String result) {
                        progressDialog.dismiss();
                        try {
                            if (result != null && !TextUtils.isEmpty(result)) {
                                KLog.i(result);
                                if (!TextUtils.isEmpty(result)) {
                                    String qrURL = URLDecoder.decode(result, "GBK");
                                    String replaceURL = qrURL.replace("\"", "");
                                    view.createQRCode(replaceURL);
                                } else {
                                    Toast.makeText(context, "请求URL失败！", Toast.LENGTH_SHORT).show();
                                    view.clearQrCode();
                                }
                            } else {
                                Toast.makeText(context, "请求URL失败！", Toast.LENGTH_SHORT).show();
                                view.clearQrCode();
                            }
                        } catch (Exception ex) {
                            view.clearQrCode();
                            Log.e("error", ex.getMessage());
                            ex.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        view.clearQrCode();
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void requestDeleteURL(String sysNO) {
//Done switch
        PersonalQrReq accessToken = new PersonalQrReq();
        accessToken.setSysNo(sysNO);
        accessToken.setDeletClear(1L);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(context.getResources().getString(R.string.orders_search_waitting));
        progressDialog.show();
        QrReService.getInstance(context)
                .getQRCode(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context) {
                    @Override
                    protected void doOnNext(String result) {
                        progressDialog.dismiss();
                        try {
                            if (result != null && !TextUtils.isEmpty(result)) {
                                KLog.i(result);
                                if (!TextUtils.isEmpty(result)) {
                                    String qrURL = URLDecoder.decode(result, "GBK");
                                    String replaceURL = qrURL.replace("\"", "");
                                    view.createQRCode(replaceURL);
                                } else {
                                    Toast.makeText(context, "请求URL失败！", Toast.LENGTH_SHORT).show();
                                    view.clearQrCode();
                                }
                            } else {
                                Toast.makeText(context, "请求URL失败！", Toast.LENGTH_SHORT).show();
                                view.clearQrCode();
                            }
                        } catch (Exception ex) {
                            Log.e("error", ex.getMessage());
                            ex.printStackTrace();
                            view.clearQrCode();
                        }

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        view.clearQrCode();
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void requestSmartCodeURL(String sysNO) {
        //Done switch
        String urlSmartPayQr = DycLibIntent.hasModule()?ConstantUtils.URL_D_SMART_PAY_QR:ConstantUtils.URL_SMART_PAY_QR;
        String actionUrl = urlSmartPayQr + sysNO;
        view.createQRCode(actionUrl);
    }
}
