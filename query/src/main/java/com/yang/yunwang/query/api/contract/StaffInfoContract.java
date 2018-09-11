package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

/**
 * Created by Administrator on 2018/7/12.
 */

public interface StaffInfoContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{
        void setInfo(String staff_id,
                     String staff_customer,
                     String staff_display,
                     String staff_tel,
                     String staff_email,
                     String staff_shopid,
                     String staff_time);

        Intent loadInstance();
    }

    interface Presenter extends BasePresenter{
        void initData();
         void initDATAString(String key1, String url1, final String key2, final String url2, final boolean fromHome,String staffID);
    }
}
