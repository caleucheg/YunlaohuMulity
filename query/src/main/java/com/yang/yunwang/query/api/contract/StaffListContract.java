package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;
import com.yang.yunwang.query.api.bean.staffsearch.StaffListResp;

/**
 * Created by Administrator on 2018/7/12.
 */

public interface StaffListContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{
        void refreshComplete();

        void loadMoreComplete();

        void dataNotifyChanged();

        int getRecItmesCount();

        Intent getIntentInstance();

        void setAdapter(StaffListResp bean, String inner_flag);
    }

    interface Presenter extends BasePresenter{
        void initData();

        void loadMore();

        void refresh();
    }
}
