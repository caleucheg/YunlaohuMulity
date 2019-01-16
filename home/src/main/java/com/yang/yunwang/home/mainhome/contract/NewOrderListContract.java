package com.yang.yunwang.home.mainhome.contract;

import android.content.Intent;

import com.yang.yunwang.base.busevent.OrderFilterEvent;
import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

public interface NewOrderListContract {
    interface Model {
    }

    interface View<T> extends BaseView<Presenter> {
        void refreshComplete();

        void loadMoreComplete(boolean hasMore);

        void dataNotifyChanged();

        int getRecItmesCount();

        Intent getIntentInstance();

        void setAdapter(T bean);

        void noData(boolean noData);

        void showDialog();

        void dismissDialog();

        void dismissLoadMore();
    }

    interface Presenter extends BasePresenter {

        void initData();

        void loadMore();

        void refresh();

        void loadMoreN();

        void filterOrder(OrderFilterEvent userEvent);
    }
}
