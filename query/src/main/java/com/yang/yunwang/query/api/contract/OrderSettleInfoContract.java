package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

/**
 * Created by Administrator on 2018/7/5.
 */

public interface OrderSettleInfoContract {
    interface Model {
    }

    interface View extends BaseView<Presenter> {
        void setInfo(String name, String money, String cash, String cny, String count);

        Intent loadInstance();
    }

    interface Presenter extends BasePresenter {
        void initData();
    }
}
