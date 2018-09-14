package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

/**
 * Created by Administrator on 2018/7/5.
 */

public interface UnrefundInfoContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{
        Intent loadIntentInstance();

        void closeActionSheet();

        void setInfo(String sys_no, String code, String pay_type, String order_time, String money, String refund, String cash, String refund_count, String status, String total_fee, String total_FEE);

        void changStatus();
    }

    interface Presenter extends BasePresenter{
        void initData(boolean isFirst);

        void Refund(String password, String code, String refund, String total_fee, String sys_no, String staff_sys_no, String type);
    }
}
