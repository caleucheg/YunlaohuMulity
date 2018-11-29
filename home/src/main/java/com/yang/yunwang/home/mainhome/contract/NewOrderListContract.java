package com.yang.yunwang.home.mainhome.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

public interface NewOrderListContract {
    interface Model {
    }

    interface View<T> extends BaseView<Presenter> {
        void refreshComplete();

        void loadMoreComplete();

        void dataNotifyChanged();

        int getRecItmesCount();

        Intent getIntentInstance();

        void setAdapter(T bean);

        void noData(boolean noData);
    }

    interface Presenter extends BasePresenter {

        void initData();

        void loadMore();

        void refresh();
    }
}
