package com.yang.yunwang.home.mainhome.contract;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 */

public interface MainhomeContract {
    interface Model {
    }

    interface View {
        void setAdapter(List<String> tab_list, int[] tab_res, List<Fragment> view_list);
        void showDialog();
        void dismissDialog();
        void showAlert();
        void setPresenter(Presenter presenter);
        void finishAct();
        void jumpLoginPage();
    }

    interface Presenter {
        void initData();

        void getPayConfig(String higherSysNo);
        void getServiceVersionCode();
        void checkPassWord();
    }
}
