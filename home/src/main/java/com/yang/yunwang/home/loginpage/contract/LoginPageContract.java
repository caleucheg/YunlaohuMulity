package com.yang.yunwang.home.loginpage.contract;

import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 */

public interface LoginPageContract {
    interface Model {
    }

    interface View {
        void setPresenter(Presenter presenter);

        void loginOnSuccess();

        void loginOnError();

        void loginOnNetworkTimeLong();

        void checkLoginDialog();

        void setDataAdapter(List<String> tab_list, List<android.view.View> view_list);

        void showDialog();
        void dismissDialog();
    }

    interface Presenter {
        void login(String user, String password, int position);

        void initData();

        void setAdapter();
    }
}
