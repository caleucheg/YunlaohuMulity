package com.yang.yunwang.home.mainhome.contract;

import android.os.Bundle;

import com.yang.yunwang.home.mainhome.bean.StaffHomeBean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

public interface StaffHomeContract {
    interface Model {
        void initBannerList();

        void initMainFunctionList();

        /**
         * 服务商员工主菜单列表
         */
        void initServiceMainFunctionList();

        void initServiceSubFunctionList();

        void initSubFunctionList();

        StaffHomeBean loadInstance();

        void setInfos(String sys_no, String customer);
    }

    interface View {
        void setPresenter(Presenter presenter);
        void setMainAdapter(List<String> main_list, int[] main_res, int[] main_res_selected, List<String> actios, Bundle[] bundles);

        void setSubAdapter(List<String> sub_list, int[] sub_res, int[] sub_res_selected,  List<String> actios, Bundle[] bundles);

        void setHeaderTitle(String title);

        void setTopName(String top_name);

        void setHomeInfo(String cusType, String cashFee, String tradeCount, String orderFee);

        void setHomeActiveCus(String activeCus);
    }

    interface Presenter {
        void initData(String sys_no, String customer);

        void initHomePage();
    }
}
