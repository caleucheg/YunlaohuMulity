package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;
import com.yang.yunwang.query.api.bean.ordersettel.OrderSettleResp;

/**
 * Created by Administrator on 2018/7/5.
 */

public interface OrderSettlementContract {
    interface Model {
    }

    interface View extends BaseView<Presenter> {
        void refreshComplete();

        void loadMoreComplete(boolean isScroll);

        void dataNotifyChanged();

        int getRecItmesCount();

        Intent getIntentInstance();

        void setAdapter(OrderSettleResp bean, String staff_id);
    }

    interface Presenter extends BasePresenter {
        void initData();

        void loadMore();

        void refresh();
    }
}
