package com.yang.yunwang.query.view.adapter.viewholder;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kw.rxbus.RxBus;
import com.yang.yunwang.base.busevent.CloseActivityEvent;
import com.yang.yunwang.base.busevent.ReportFilterEvent;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.merchsearch.MerchListResp;
import com.yang.yunwang.query.api.bean.staffsearch.StaffListResp;
import com.yang.yunwang.query.view.commoncard.CommonCardCusActivity;

public class CommonCardCusListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView tv_cus_name;
    private final TextView tv_cus_phone;
    private final boolean isAc;
    private final boolean isCus;
    private MerchListResp beanMerch = new MerchListResp();
    private StaffListResp beanStaff = new StaffListResp();


    private Context context;

    public CommonCardCusListViewHolder(View itemView, Object bean, Context context) {
        super(itemView);
        tv_cus_name = (TextView) itemView.findViewById(R.id.tv_cus_name);
        tv_cus_phone = (TextView) itemView.findViewById(R.id.tv_cus_phone);

        this.context = context;
        isAc = ((CommonCardCusActivity) context).isActive();
        isCus = ((CommonCardCusActivity) context).isCus();
        if (isCus) {
            this.beanStaff = (StaffListResp) bean;
        } else if (isAc) {
            this.beanMerch = (MerchListResp) bean;
        } else {
            this.beanMerch = (MerchListResp) bean;
        }
        this.setIsRecyclable(false);
        itemView.setOnClickListener(this);
    }

    public void bind(int pos) {
        if (isCus) {
            tv_cus_name.setText(beanStaff.getModel().get(pos).getDisplayName());
            tv_cus_phone.setText(beanStaff.getModel().get(pos).getLoginName());
        } else if (isAc) {
            tv_cus_name.setText(beanMerch.getModel().get(pos).getCustomerName());
            tv_cus_phone.setText(beanMerch.getModel().get(pos).getCustomer());
        } else {
            tv_cus_name.setText(beanMerch.getModel().get(pos).getCustomerName());
            tv_cus_phone.setText(beanMerch.getModel().get(pos).getCustomer());
        }
//        tv_cus_name.setText(beanMerch.getModel().get(pos).getCustomerName());
//        tv_cus_phone.setText(beanMerch.getModel().get(pos).getPhone());
        itemView.setTag(pos);
    }

    @Override
    public void onClick(View view) {

        String cusNo;
        String cusName;
        if (isCus) {
            cusNo = beanStaff.getModel().get(Integer.parseInt(view.getTag().toString())).getSysNO() + "";
            cusName = beanStaff.getModel().get(Integer.parseInt(view.getTag().toString())).getDisplayName();
        } else if (isAc) {
            cusNo = beanMerch.getModel().get(Integer.parseInt(view.getTag().toString())).getSysNo() + "";
            cusName = beanMerch.getModel().get(Integer.parseInt(view.getTag().toString())).getCustomerName();
        } else {
            cusNo = beanMerch.getModel().get(Integer.parseInt(view.getTag().toString())).getSysNo() + "";
            cusName = beanMerch.getModel().get(Integer.parseInt(view.getTag().toString())).getCustomerName();
        }
        RxBus.getInstance().send(new ReportFilterEvent(false, cusNo, false, "", "", cusName));
        RxBus.getInstance().send(new CloseActivityEvent(true, "CommonCardCusListViewHolder"));
        ((CommonCardCusActivity) context).finish();
    }
}
