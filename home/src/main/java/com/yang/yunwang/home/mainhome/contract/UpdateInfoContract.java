package com.yang.yunwang.home.mainhome.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2018/7/18.
 */

public interface UpdateInfoContract {
    interface Model {
    }

    interface View extends BaseView<Presenter> {
        Intent loadIntent();

        void setInfo(String user, String user_id, String tel, String customer, String email, String storeID, String dwellAddress, String rate, String dwellAddressID, String prociveID, String cityID, String countyID);

        void setProvince(List<String> sysNo, List<String> parentId, List<String> addressName, String[] userIds);

        void infoError();

        void onSuccess(String description);

        void onError(String description);

        void setMerchInfo(String user, String user_id, String tel, String customer, String email, String fax, String dwellAddress, String rate, String dwellAddressID, String prociveID, String cityID, String countyID, String fClass, String sClass, String tClass, String classID, String type, String nickName, String typeC);

        void setFClass(List<String> sysNo, List<String> classID, List<String> className, String[] classIDs);

        void setRateInfo(JSONArray jsonArray);
    }

    interface Presenter extends BasePresenter {
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
                        String staffId);

        void changeInfo(JSONObject map, boolean fromMerchant);

        void getSHRate();
    }
}
