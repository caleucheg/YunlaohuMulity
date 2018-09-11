package com.yang.yunwang.query.view.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.commonsearch.CommonOrdersResp;
import com.yang.yunwang.query.view.order.list.CommonListActivity;

public class CommonRateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final boolean isSettlement;
    private final TextView textView12;
    public TextView text_money;
    public TextView text_code;
    public TextView text_date;
    public ImageView image_logo;
    public RelativeLayout relativeLayout;
    private boolean top_rate = false;
    private ViewGroup item_view;
    private Context context;
    private CommonOrdersResp bean;

    public CommonRateViewHolder(ViewGroup item_view, CommonOrdersResp bean, Context context, boolean is_top_rate, boolean isSettlement) {
        super(item_view);
        this.setIsRecyclable(false);
        text_money = (TextView) item_view.findViewById(R.id.text_order_money);
        text_code = (TextView) item_view.findViewById(R.id.text_order_code);
        text_date = (TextView) item_view.findViewById(R.id.text_order_date);
        image_logo = (ImageView) item_view.findViewById(R.id.image_order_logo);
        relativeLayout = (RelativeLayout) item_view.findViewById(R.id.rel_list_item_area);
        textView12 = (TextView) item_view.findViewById(R.id.textView12);
        this.isSettlement = isSettlement;
        this.item_view = item_view;
        this.top_rate = is_top_rate;
        this.bean = bean;

        this.context = context;
        //防止所有recyclerview的item在被绘制时出现重复绘制的现象
        itemView.setOnClickListener(this);
    }

    public void bind(int pos) {
        if (isSettlement) {
            textView12.setVisibility(View.INVISIBLE);
            image_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.staff_logo));
            if (TextUtils.equals(bean.getModel().get(pos).getDisplayName(), null) || TextUtils.equals(bean.getModel().get(pos).getDisplayName(), "null")) {
                text_money.setText(bean.getModel().get(pos).getCustomerName());
            } else {
                text_money.setText(bean.getModel().get(pos).getDisplayName());
            }
            String text = bean.getModel().get(pos).getTradecount() + " 笔";
            text_code.setText(text);
            Long cashFee = bean.getModel().get(pos).getCashFee();
            String text1 = " 元";
            try {
                text1 = AmountUtils.changeF2Y(cashFee) + " 元";
            } catch (Exception e) {
                e.printStackTrace();
            }
            text_date.setText(text1);
        } else {
            Long totalFee = bean.getModel().get(pos).getTotalFee();
            String text = "";
            try {
                text = AmountUtils.changeF2Y(totalFee);
            } catch (Exception e) {
                e.printStackTrace();
            }
            text_money.setText(text);
            text_code.setText(bean.getModel().get(pos).getOutTradeNo());
            text_date.setText(bean.getModel().get(pos).getTimeStart());
            String payType = bean.getModel().get(pos).getPayType();
            int resID = R.drawable.wx_logo;
            if (ConstantUtils.WX_Type.contains(payType)) {
                resID = R.drawable.wx_logo;
            } else if (ConstantUtils.ZFB_Type.contains(payType)) {
                resID = R.drawable.zfb_logo;
            } else {
                resID = R.drawable.wx_logo;
            }
            image_logo.setImageDrawable(context.getResources().getDrawable(resID));
        }
        item_view.setTag(pos);
    }

    @Override
    public void onClick(View v) {
        boolean isMerchHome = ((CommonListActivity) context).isMerchHome();
        boolean isSettlement = ((CommonListActivity) context).isSettlement();
        boolean isRateVis = ((CommonListActivity) context).isRateVis();
        boolean isChangeStaffMerchTitle = ((CommonListActivity) context).isChangeStaffMerchTitle();
        MyBundle intent = new MyBundle();
        intent.put("from_merch_home", isMerchHome);
        intent.put("isSettlement", isSettlement);
        intent.put("isRateVis", isRateVis);
        intent.put("common_bean", bean.getModel().get(Integer.parseInt(v.getTag().toString())));
        intent.put("pos", Integer.parseInt(v.getTag().toString()));
        intent.put("top_rate", top_rate);
        intent.put("code", bean.getModel().get(Integer.parseInt(v.getTag().toString())).getSysNo());
        intent.put("isChangeStaffMerchTitle", isChangeStaffMerchTitle);
        OrdersIntent.getCommonInfo(intent);
    }
}
