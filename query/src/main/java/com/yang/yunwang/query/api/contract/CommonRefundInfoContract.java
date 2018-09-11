package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

/**
 * Created by Administrator on 2018/7/11.
 */

public interface CommonRefundInfoContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{
        Intent loadInstance();

        void setInfo(String loginName, String realName, String outOrderNo, String tradeType, String refundFee, String merchName, String refundTime, String moneyType, String tradeTime, String tradeFee);

    }

    interface Presenter extends BasePresenter{
        void initData();
    }
}
