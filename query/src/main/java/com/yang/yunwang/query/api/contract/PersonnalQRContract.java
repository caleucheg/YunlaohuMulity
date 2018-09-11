package com.yang.yunwang.query.api.contract;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

/**
 */

public interface PersonnalQRContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{
        void createQRCode(String url);
        void clearQrCode();
    }

    interface Presenter extends BasePresenter{
        void requestWXURL(String money);

        void requestZFBURL(String money);

        void requestCheckURL(String sysNO);

        void requestDeleteURL(String sysNO);

        void requestSmartCodeURL(String money);
    }
}
