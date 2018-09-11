package com.yang.yunwang.base.moduleinterface.config;


import com.yang.yunwang.base.util.Util;

import java.util.HashMap;
import java.util.Map;

/**
 *  on 2017/4/13.
 *
 * description：
 * update by:
 * update day:
 */
public class ImmutableMap {

    private Map<String, String> mPaths;

    public ImmutableMap() {
        mPaths = new HashMap<>();
    }

    public void add(String key, String value) {
        if (Util.isNull(key, value)) return;
        mPaths.put(key, value);
    }

    public void add(Map<String, String> mPaths) {
        if (mPaths == null) return;
        this.mPaths.putAll(mPaths);
    }

    public boolean containsKey(String key) {
        return mPaths.containsKey(key);
    }

    public String get(String key) {
        return mPaths.get(key);
    }
}
