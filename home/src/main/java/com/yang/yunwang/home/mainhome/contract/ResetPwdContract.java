package com.yang.yunwang.home.mainhome.contract;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

/**
 * Created by Administrator on 2018/7/16.
 */

public interface ResetPwdContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{
        void back();
    }

    interface Presenter extends BasePresenter{
        void changePassword(String old_password, String new_password);
    }
}
