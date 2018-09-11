package com.yang.yunwang.home.homeservice;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yang.yunwang.base.basereq.bean.stafflogin.StaffLoginResp;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.home.homeservice.bean.LoginPageReq;
import com.yang.yunwang.base.basereq.bean.merchlogin.MerchLoginResp;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * description：
 * update by:
 * update day:
 */
@Route(path = IHomeProvider.HOME_MAIN_SERVICE)
public class HomeProvider implements IHomeProvider {
    private Context context;

    @Override
    public void init(Context context) {
        this.context = context;
        Log.i("homeP","homeP");
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void selectedTab(Activity activity,int position) {

    }

    @Override
    public Observable<StaffLoginResp> staffLogin(String user, String password) {
        LoginPageReq accessToken = new LoginPageReq();
        accessToken.setUserName(user);
        accessToken.setPassWord(password);
        return HomeREService.getInstance(context)
                .staffLogin(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MerchLoginResp> merchLogin(String user, String password) {
        LoginPageReq accessToken = new LoginPageReq();
        accessToken.setUserName(user);
        accessToken.setPassWord(password);
        return HomeREService.getInstance(context)
                .merchLogin(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
