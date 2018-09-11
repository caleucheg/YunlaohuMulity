package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

/**
 * Created by Administrator on 2018/7/10.
 */

public interface CommonListContract {
    interface Model {
    }

    interface View<CommonOrdersResp> extends BaseView<Presenter> {
        void refreshComplete();

        void loadMoreComplete(boolean isScroll);

        void dataNotifyChanged();

        int getRecItmesCount();

        Intent getIntentInstance();

        void setAdapter(CommonOrdersResp bean);

        void finishActivity();

        void setRateVisF();
    }

    interface Presenter extends BasePresenter {

        void initData();

        void loadMore();

        void refresh();
    }
}
