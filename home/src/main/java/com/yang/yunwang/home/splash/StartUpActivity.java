package com.yang.yunwang.home.splash;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.JPUtils;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.base.util.utils.jpush.PushSpeakService;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.splash.contract.StartUpContract;
import com.yang.yunwang.home.splash.presenter.StartUpPresenter;


/**
 */
@Route(path = IHomeProvider.HOME_ACT_START_UP)
public class StartUpActivity extends AppCompatActivity implements StartUpContract.View {
    private StartUpContract.Presenter presenter;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_startup);
        //done switch version
        //done change pic
        new StartUpPresenter(this, this);
        if (DycLibIntent.hasModule()){
            ImageView imageCenter = (ImageView) findViewById(R.id.image_center);
            ImageView imageBottom = (ImageView) findViewById(R.id.image_bottom);
            imageBottom.setImageResource(R.drawable.dyclib_label);
            imageCenter.setImageResource(R.drawable.dyclib_logo);
        }
        KLog.i("hasModule"+ !DycLibIntent.hasModule());
        boolean netV = NetStateUtils.isNetworkConnected(this);
        if (!netV) {
            new AlertDialog.Builder(this)
                    .setTitle("网络异常")
                    .setMessage("当前网络连接异常,请检查网络后重新登录")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            StartUpActivity.this.finish();
                        }
                    })
                    .create().show();
        } else {
            CommonShare.putJPushIDBoolean(getApplicationContext(), false);
            String sysNo = CommonShare.getLoginData(this, "SysNo", "");
            KLog.i(sysNo);
            //done switch version
            if (!DycLibIntent.hasModule()){
                KLog.i("startService");
                Intent service = new Intent(this, PushSpeakService.class);
                try {
                    startService(service);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(sysNo)) {
                    JPUtils.setTags(this, sysNo);
                }
            }

            boolean netV1 = NetStateUtils.isNetworkConnected(this);
            if (!netV1) {
                Toast.makeText(this, R.string.check_net, Toast.LENGTH_LONG).show();
            } else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        presenter.getAllcoate();
                    }
                }, 1500);
            }
//            initMark();
            presenter.init();
        }

    }


    @Override
    public void setPresenter(StartUpContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle(getResources().getString(R.string.alert_title));
        progressDialog.setMessage(getResources().getString(R.string.orders_search_waitting));
        progressDialog.show();
    }

    @Override
    public void jumpMain() {
        HomeIntent.launchHomePage();
        KLog.i("home");
        this.finish();

    }

    @Override
    public void jumpLogin() {
        HomeIntent.login();
        KLog.i("login");
        this.finish();
    }

    @Override
    public void dismissDialog() {
        progressDialog.dismiss();
    }


}
