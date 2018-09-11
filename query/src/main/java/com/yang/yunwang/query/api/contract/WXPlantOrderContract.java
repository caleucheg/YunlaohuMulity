package com.yang.yunwang.query.api.contract;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

/**
 * Created by Administrator on 2018/7/3.
 */

public interface WXPlantOrderContract {
    interface Model {
    }

    interface View extends BaseView<Presenter> {
        void showInfo(String code, String pay_type, String money, String money_type, String time, String status, String codePl);

        void clearInfo();
    }

    interface Presenter extends BasePresenter {
        void requestPlatformInfo(String code, boolean isPlOrderNo);
    }
}
