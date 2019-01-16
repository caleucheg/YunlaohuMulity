package com.yang.yunwang.home.mainhome.contract;

import com.yang.yunwang.base.busevent.ReportFilterEvent;
import com.yang.yunwang.base.view.common.BasePresenter;
import com.yang.yunwang.base.view.common.BaseView;

public interface ReportFromContract {
    interface Model {
    }

    interface View extends BaseView<Presenter> {
        void initView();

        void setReportInfo(String centerCash, String orderFee, String tradeCount, String refundFeeCount, String centerCashWx, String orderFeeWx, String tradeCountWx, String refundFeeCountWx, String centerCashZfb, String orderFeeZfb, String tradeCountZfb, String refundFeeCountZfb);
        void refreshView();

        void showDialog();

        void dismissDialog();

    }

    interface Presenter extends BasePresenter {
        void initData(String type, boolean isAllCus, String cusNo);

        void refreshData(ReportFilterEvent userEvent, String newType, boolean isAllCus, String cusNo);

        void filterOrder(ReportFilterEvent userEvent, String newType, boolean isAllCus, String cusNo);
    }
}
