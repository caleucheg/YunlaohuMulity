package com.yang.yunwang.home.mainhome.contract;

import android.os.Bundle;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

public interface MerchHomeContract {
    interface Model {
    }

    interface View {
        void setMenuAdapter(List<String> menu_list, int[] menu_res, int[] menu_res_selected, List<String> actios, Bundle[] bundles);
        void setInfo(String total_fee, int orders_count, String total_cash);

        void setHomeInfo(String cusType, String cashFee, String tradeCount, String orderFee);

        void setHomeActiveCus(String activeCus);
        void setPresenter(Presenter presenter);
    }

    interface Presenter {
        void initData();

        void refreshHomeTopData();
    }
}
