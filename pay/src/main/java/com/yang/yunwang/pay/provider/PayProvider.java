package com.yang.yunwang.pay.provider;

import android.content.Context;

import com.yang.yunwang.base.moduleinterface.provider.IPayProvider;

/**
 * Created by Administrator on 2018/7/18.
 */

public class PayProvider implements IPayProvider {
    private Context context;

    @Override
    public void init(Context context) {
        this.context=context;
    }
}
