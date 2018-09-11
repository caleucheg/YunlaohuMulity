package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

/**
 * Created by Administrator on 2018/7/10.
 */

public interface CommenListInfoContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{
        void setInfo(String code, String customer, String orderNo, String money, String rate, String fee, String pay_type, String money_type, String date, String disName, String loginName);
        Intent loadInstance();
    }

    interface Presenter extends BasePresenter{
        void initData();
    }
}
