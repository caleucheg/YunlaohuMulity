package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

/**
 * Created by Administrator on 2018/7/6.
 */

public interface MerchRefundInfoContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{
        void init(boolean hasRole, boolean b);

        void changStatus();

        void closeActionSheet();

        void refreshData(String total_FEE, String status, String refund, String refund_count);
    }

    interface Presenter extends BasePresenter{
        void initData(Intent intent);

        void refreshData();

        void refundFee(String password, final String code, final String refund, final String total_fee, final String sys_no, final String staff_sys_no, String transaction_id_list);

    }
}
