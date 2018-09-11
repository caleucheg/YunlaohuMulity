package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

/**
 */

public interface OrderListInfoContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{
        void setInfo(String sys_no, String customer, String code, String pay_type, String money, String date, String loginname, String displayname, String moneyCash);

        Intent loadInstance();
    }

    interface Presenter extends BasePresenter{
        void initData();
    }
}
