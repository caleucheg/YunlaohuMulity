package com.yang.yunwang.query.view.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.yang.yunwang.query.api.bean.commonrefund.CommonRefundResp;

import java.util.List;

public class CommonRefundViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final boolean isSettlement;
    public TextView text_money;
    public TextView text_code;
    public TextView text_date;
    public ImageView image_logo;
    public RelativeLayout relativeLayout;
    private List<String> orderNoList;
    private boolean top_rate = false;
    private ViewGroup item_view;
    private Context context;
    private CommonRefundResp bean;

    public CommonRefundViewHolder(ViewGroup item_view, CommonRefundResp bean, Context context, boolean is_top_rate, boolean isSettlement) {
        super(item_view);
        text_money = (TextView) item_view.findViewById(R.id.text_order_money);
        text_code = (TextView) item_view.findViewById(R.id.text_order_code);
        text_date = (TextView) item_view.findViewById(R.id.text_order_date);
        image_logo = (ImageView) item_view.findViewById(R.id.image_order_logo);
        relativeLayout = (RelativeLayout) item_view.findViewById(R.id.rel_list_item_area);

        this.isSettlement = isSettlement;
        this.item_view = item_view;
        this.top_rate = is_top_rate;
        this.bean = bean;
        this.context = context;
        //防止所有recyclerview的item在被绘制时出现重复绘制的现象
        this.setIsRecyclable(false);
        itemView.setOnClickListener(this);
    }

    public void bind(int pos) {
        String refundFee = bean.getModel().get(pos).getRefundFee();
        String refundFeeS="";
        try {
             refundFeeS=AmountUtils.changeF2Y(refundFee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        text_money.setText(refundFeeS);
        text_code.setText(bean.getModel().get(pos).getOutTradeNo());
        text_date.setText(bean.getModel().get(pos).getCreateTime());
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
        item_view.setTag(pos);
    }

    @Override
    public void onClick(View v) {
        MyBundle intent = new MyBundle();//context, CommonListInfoRefundActivity.class
        intent.put("common_bean", bean.getModel().get(Integer.parseInt(v.getTag().toString())));
        intent.put("pos", Integer.parseInt(v.getTag().toString()));
        OrdersIntent.getCommonRefundInfo(intent);
    }
}
