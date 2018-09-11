package com.yang.yunwang.query.provider;

import android.content.Context;

import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;

/**
 * Created by Administrator on 2018/6/27.
 */

public class OrderProvider implements IOrdersProvider {
    private Context context;

    @Override
    public void init(Context context) {
        this.context=context;
    }
}
