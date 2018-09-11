package com.yang.yunwang.base.util;


import com.yang.yunwang.base.dao.PayLogBean;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Administrator on 2017/3/10.
 */
public class PayLogQueu extends LinkedBlockingDeque<PayLogBean> {
    private volatile static PayLogQueu instance;

    private PayLogQueu() {
        super(10);
    }

    public static PayLogQueu getInstance() {
        if (instance == null) {
            synchronized (PayLogQueu.class) {
                if (instance == null)
                    instance = new PayLogQueu();//instance为volatile，现在没问题了
            }
        }
        return instance;
    }


}
