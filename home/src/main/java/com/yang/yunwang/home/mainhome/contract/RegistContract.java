package com.yang.yunwang.home.mainhome.contract;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2018/7/17.
 */

public interface RegistContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{
        void setInfo(String user, String user_id, String tel, String customer, String email, String storeID);

        void setProvince(List<String> sysNo, List<String> parentId, List<String> addressName);

        void infoError();

        void onSuccess(String description, String string);

        void onError(String description);

        void setFClass(List<String> sysNo, List<String> classID, List<String> className);
    }

    interface Presenter extends BasePresenter{

        void initData();

        void changeInfo(String userName,
                        String passWord,
                        String shopName,
                        String email,
                        String tenantsRate,
                        String mailCode,
                        String addressDetail,
                        String addressCode,
                        String faxNum,
                        String staffId, String classCode, String classNames, String bankType, String nickNameS, String tenantsRateZ, String zfbPassageWay, String wxType, String zfbType);

        void insertRole(String customer, String description);

        void insertRate(String string);

        void insertPassgeWay(String string);
    }
}
