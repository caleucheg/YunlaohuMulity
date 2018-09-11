package com.yang.yunwang.base.moduleinterface.provider;

import android.support.v4.app.Fragment;

/**
 *  on 2017/4/13.
 *
 * description：
 * update by:
 * update day:
 */
public interface IFragmentProvider extends IBaseProvider {

    Fragment newInstance(Object... args);

}
