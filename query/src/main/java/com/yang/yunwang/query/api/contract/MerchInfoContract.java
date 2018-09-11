package com.yang.yunwang.query.api.contract;

import android.content.Intent;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;
import com.yang.yunwang.query.api.bean.merchinfo.rates.MerchRateResp;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/7/13.
 */

public interface MerchInfoContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{
        void setInfo(String shop_id,
                     String shop_user,
                     String shop_name,
                     String shop_time,
                     String shop_tel,
                     String shop_email,
                     String shop_fax,
                     String shop_address, String rate, String user_rate);

        Intent loadInstance();

        void setNameData(String disName);

        void setRateInfo(ArrayList<MerchRateResp> jsonArray);

        void dismissBottomSheetDialog();
    }

    interface Presenter extends BasePresenter{
        void initData();

        void initDATAString(String customerServiceSysNo, String urlShopRoleList, String s, String urlRoleList, boolean fromHome);

        void initDATA(String key1, String url1, String key2, String url2, boolean fromHome);

        void updateRate(String rate);
    }
}
