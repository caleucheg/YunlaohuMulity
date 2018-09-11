package com.yang.yunwang.home.splash.contract;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;


public interface StartUpContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{
        void showDialog();
        void jumpMain();
        void jumpLogin();
        void dismissDialog();
    }

    interface Presenter extends BasePresenter{
        void init();
        void getAllcoate();
    }
}
