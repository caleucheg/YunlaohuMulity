package com.yang.yunwang.home.mainhome.presenter;

import android.content.Context;

import com.yang.yunwang.home.mainhome.contract.ReportFromContract;

public class ReportFromPresenter implements ReportFromContract.Presenter {
    private final Context context;
    private final ReportFromContract.View view;

    public ReportFromPresenter(Context context, ReportFromContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void refreshData() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
