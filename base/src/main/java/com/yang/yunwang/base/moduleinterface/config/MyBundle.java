package com.yang.yunwang.base.moduleinterface.config;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.Serializable;

/**
 *  on 2017/4/16.
 *
 * description：链式调用
 * update by:
 * update day:
 */
public class MyBundle {
    private Bundle bundle;

    public MyBundle() {
        bundle = new Bundle();
    }

    public MyBundle put(String key, int value) {
        bundle.putInt(key, value);
        return this;
    }

    public MyBundle put(String key, long value) {
        bundle.putLong(key, value);
        return this;
    }

    public MyBundle put(String key, String value) {
        if (TextUtils.isEmpty(value)) {
            return this;
        }
        bundle.putString(key, value);
        return this;
    }

    public MyBundle put(String key, Serializable value) {
        if (value == null) {
            return this;
        }
        bundle.putSerializable(key, value);
        return this;
    }

    public MyBundle put(String key, Parcelable value) {
        if (value == null) {
            return this;
        }
        bundle.putParcelable(key, value);
        return this;
    }

    public MyBundle put(String key, String[] arrays) {
        if (arrays == null) {
            return this;
        }
        bundle.putStringArray(key, arrays);
        return this;
    }

    public Bundle build() {
        return bundle;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
