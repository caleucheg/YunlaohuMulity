package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

/**
 */

public interface RefundListSContract {
    interface Model {
    }

    interface View<T> extends BaseView<Presenter>{
        void refreshComplete();

        void loadMoreComplete(boolean isScroll);

        void dataNotifyChanged();

        int getRecItmesCount();

        Intent getIntentInstance();

        void setAdapter(T bean);
    }

    interface Presenter extends BasePresenter{
        void initData();

        void loadMore();

        void refresh();
    }
}
