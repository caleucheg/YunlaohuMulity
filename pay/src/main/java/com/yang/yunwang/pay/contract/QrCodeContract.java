package com.yang.yunwang.pay.contract;

import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

/**
 * Created by Administrator on 2018/7/18.
 */

public interface QrCodeContract {
    interface Model {
    }

    interface View extends BaseView<Presenter>{

        void creatQRCode(String url);

        void clearQR();

        void setResult(String qr_result, int color, int flag);
    }

    interface Presenter extends BasePresenter{

        void requestQRURL(int flag, String total_fee, boolean wxIsBank, boolean zfbIsBank);

        void closeTimer();

        void closeProgressDialog();
    }
}
