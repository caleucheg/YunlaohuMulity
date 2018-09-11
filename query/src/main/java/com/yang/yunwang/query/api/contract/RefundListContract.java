package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;
import com.yang.yunwang.query.api.bean.refundsearch.refundlist.RefundListResp;

/**
 * Created by Administrator on 2018/7/5.
 */

public interface RefundListContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{
        void refreshComplete();

        void loadMoreComplete(boolean isScroll);

        void dataNotifyChanged();

        int getRecItmesCount();

        Intent getIntentInstance();

        void setAdapter(RefundListResp bean, boolean hasRole);

        void onErrorRole();
    }

    interface Presenter extends BasePresenter{
        void initData();

        void loadMore();

        void refresh();

    }
}
