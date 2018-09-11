package com.yang.yunwang.dyclib.provider;

import android.content.Context;

import com.yang.yunwang.base.moduleinterface.provider.IDycLibProvider;

/**
 * Created by Administrator on 2018/9/6.
 */

public class DycLibProvider implements IDycLibProvider {
    private Context context;

    @Override
    public void init(Context context) {
        this.context=context;
    }
}
