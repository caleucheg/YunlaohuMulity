package com.yang.yunwang.pay.contract;

import android.content.Intent;

import com.yang.yunwang.base.dao.PayLogBean;
import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

/**
 * Created by Administrator on 2018/7/18.
 */

public interface ScanContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{
        void setResultRes(int res_id);

        void setResultText(String code, int color);

        Intent loadInstance();
    }

    interface Presenter extends BasePresenter{
        void scanRquest(boolean wxIsBank, boolean zfbIsBank, String sysNo, String hSysno);

        void closeTimer();

        int returnStatus();

        void sendMessage();

        void reinsertLog(PayLogBean bean);
    }
}
