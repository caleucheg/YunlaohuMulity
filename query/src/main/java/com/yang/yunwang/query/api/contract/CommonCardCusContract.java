package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

public interface CommonCardCusContract {
    interface Model {
    }

    interface View<T> extends BaseView<Presenter> {

        void refreshComplete();

        void loadMoreComplete(boolean hasMore);

        void dataNotifyChanged();

        int getRecItmesCount();

        Intent getIntentInstance();

        void setAdapter(T bean, String inner_flag);

        void noData(boolean b);

        void showDialog();

        void dismissDialog();
    }

    interface Presenter extends BasePresenter {
        void initData(boolean isActive, boolean isCus);

        void loadMore();

        void refresh();
    }
}
