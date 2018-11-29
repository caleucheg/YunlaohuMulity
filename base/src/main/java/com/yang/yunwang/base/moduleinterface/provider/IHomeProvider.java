package com.yang.yunwang.base.moduleinterface.provider;

import android.app.Activity;

import com.yang.yunwang.base.basereq.bean.merchlogin.MerchLoginResp;
import com.yang.yunwang.base.basereq.bean.stafflogin.StaffLoginResp;

import io.reactivex.Observable;


/**
 *  on 2017/4/16.
 *
 * description：
 * update by:
 * update day:
 */
public interface IHomeProvider extends IBaseProvider {
    //Service
    String HOME_MAIN_SERVICE = "/home/main/service";
    //开屏
    String HOME_ACT_START_UP = "/home/act/startup";
    //home主页
    String HOME_ACT_HOME = "/home/act/homepage";
    String HOME_ACT_LOGIN = "/home/act/login";
    String HOME_TABTYPE = "home_tab_type";
    String HOME_S_ID = "home_staff_id";
    String HOME_F_ID = "home_fact_id";
    String HOME_Fact_NAME = "home_fact_name";
    String HOME_Fact_DATA = "HOME_Fact_DATA";
    String HOME_P_ID = "home_p_id";
    String HOME_T_ID = "home_t_id";
    String HOME_Fact_TIME="home_fact_time";
    String HOME_SEND_BEAN = "home_send_bean";
    String HOME_ACT_STATICS = "/home/act/statics";
    String HOME_ACT_SCAN_PIC = "/home/act/scan/pic";
    String HOME_ACT_UPDATE_INFO = "/home/act/update/info";
    String HOME_ACT_RESET_PWD = "/home/act/reset/pwd";
    String HOME_ACT_SCAN_RESULT = "/home/act/scan/result";
    String HOME_ACT_REGIST = "/home/act/regist";
    String HOME_ACT_ORDER_FILTER = "/home/act/order/filter";


    void toast(String msg);

    void selectedTab(Activity activity, int position);

    Observable<StaffLoginResp> staffLogin(String user, String password);
    Observable<MerchLoginResp> merchLogin(String user, String password);

}
