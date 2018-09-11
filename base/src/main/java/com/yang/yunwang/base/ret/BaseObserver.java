package com.yang.yunwang.base.ret;

import android.content.Context;
import android.widget.Toast;

import com.yang.yunwang.base.util.NetStateUtils;
import com.socks.library.KLog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 */

public abstract class BaseObserver<T> implements Observer<T> {
    private final Context mContext;
    private Disposable disposable;

    public BaseObserver(Context context) {
        this.mContext = context;
    }


    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
//        KLog.i("tag", "BaseObserver.onStart()");
        //接下来可以检查网络连接等操作
        if (!NetStateUtils.isNetworkConnected(mContext)) {
            Toast.makeText(mContext, "当前网络不可用，请检查网络情况", Toast.LENGTH_SHORT).show();
            // 取消本次Subscriber订阅
            if (!d.isDisposed()) {
                d.dispose();
            }
            return;
        }
    }

    @Override
    public void onNext(T value) {
        doOnNext(value);
    }

    protected abstract void doOnNext(T value);

    @Override
    public void onError(Throwable e) {
        KLog.e("tag", "BaseObserver.throwable =" + e.toString());
        KLog.e("tag", "BaseObserver.throwable =" + e.getMessage());

        if (e instanceof Exception) {
            //访问获得对应的Exception
            onError(ExceptionHandle.handleException(e));
        } else {
            //将Throwable 和 未知错误的status code返回
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public abstract void onError(ExceptionHandle.ResponeThrowable responeThrowable);

    @Override
    public void onComplete() {
//        KLog.i("tag", "BaseObserver.onComplete()");
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
//            KLog.i(disposable.isDisposed());
        }
    }
}
