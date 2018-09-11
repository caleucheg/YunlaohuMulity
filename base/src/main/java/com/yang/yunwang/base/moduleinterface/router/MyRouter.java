package com.yang.yunwang.base.moduleinterface.router;

import android.app.Activity;
import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yang.yunwang.base.BaseApplication;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;

/**
 *  on 2017/4/16.
 *
 * description：
 * update by:
 * update day:
 */
public class MyRouter {

    private Postcard postcard;

    private MyRouter(Postcard postcard) {
        this.postcard = postcard;
    }

    public static MyRouter newInstance(String path) {
        return new MyRouter(ARouter.getInstance().build(path));
    }

    private boolean checkPostcard() {
        if (postcard == null)
            throw new IllegalArgumentException("MyRouter 的 postcard 为null");
        return true;
    }


    public MyRouter withBundle(MyBundle bundle) {
        if (bundle == null) return this;
        checkPostcard();
        postcard.with(bundle.build());
        return this;
    }

    public MyRouter addFlag(int flag) {
        checkPostcard();
        postcard.withFlags(flag);
        return this;
    }

    public Object navigation() {
        return navigation(BaseApplication.getApplication());
    }

    public Object navigation(Context context) {
        return navigation(context, null);
    }

    public void navigation(Activity activity, int requestCode) {
        navigation(activity, requestCode, null);
    }

    public Object navigation(Context context, NavigationCallback callback) {
        checkPostcard();
        return postcard.navigation(context, callback);//.withTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom)
    }


    public void navigation(Activity activity, int requestCode, NavigationCallback callback) {
        postcard.navigation(activity, requestCode, callback);//.withTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom)
    }

}
