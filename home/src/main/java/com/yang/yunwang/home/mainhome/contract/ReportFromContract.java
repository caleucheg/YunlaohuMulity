package com.yang.yunwang.home.mainhome.contract;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

public interface ReportFromContract {
    interface Model {
    }

    interface View extends BaseView<Presenter> {
        void initView();

        void refreshView();

    }

    interface Presenter extends BasePresenter {
        void initData();

        void refreshData();
    }
}
