package com.yang.yunwang.query.view.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.commondaycollect.CommonDayCollectResp;

public class CommonDayCollectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView text_name;
    private final TextView text_count;
    public TextView text_money;
    public ImageView image_logo;
    private ViewGroup item_view;
    private Context context;
    private CommonDayCollectResp bean;

    public CommonDayCollectHolder(ViewGroup item_view, CommonDayCollectResp bean, Context context) {
        super(item_view);
        image_logo = (ImageView) itemView.findViewById(R.id.image_settlement_logo);
        text_name = (TextView) itemView.findViewById(R.id.text_settlement_name);
        text_money = (TextView) itemView.findViewById(R.id.text_settlement_money);
        text_count = (TextView) itemView.findViewById(R.id.text_settlement_count);
        this.item_view = item_view;
        this.bean = bean;
        this.context = context;
        //防止所有recyclerview的item在被绘制时出现重复绘制的现象
        this.setIsRecyclable(false);
        KLog.i("click----item" + "---" + getPosition());
        itemView.setOnClickListener(this);
//        KLog.i("---item");
    }

    public void bind(int pos) {
        item_view.setTag(pos);
        image_logo.setImageResource(R.drawable.staff_logo);
        KLog.i(bean.getModel().get(pos));
        Long money = bean.getModel().get(pos).getTotalFee();
        String s = "";
        try {
            s = AmountUtils.changeF2Y(money);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String text = s + " 元";
        text_count.setText(text);
        text_name.setText(bean.getModel().get(pos).getDisplayName());
        text_money.setText(bean.getModel().get(pos).getOrderTime());
    }

    @Override
    public void onClick(View view) {
        int pos = (int) view.getTag();
        KLog.i("click----item" + view.getTag() + "---" + pos);
        MyBundle intent = new MyBundle();//context, DaySettlementInfoActivity.class
        intent.put("loginName", bean.getModel().get(pos).getLoginName());
        intent.put("displayName", bean.getModel().get(pos).getDisplayName());
        intent.put("OrderTime", bean.getModel().get(pos).getOrderTime());
        intent.put("TradeCount", bean.getModel().get(pos).getTradeCount());
        intent.put("Total_fee", bean.getModel().get(pos).getTotalFee());
        intent.put("Cash_fee", bean.getModel().get(pos).getCashFee());
        intent.put("Refund_fee", bean.getModel().get(pos).getRefundFee());
        intent.put("Cash_refund_fee", bean.getModel().get(pos).getCashRefundFee());
        intent.put("Fee", bean.getModel().get(pos).getFee());
        intent.put("Money", bean.getModel().get(pos).getMoney());
        intent.put("Pay_Type", bean.getModel().get(pos).getPayType());
//        context.startActivity(intent);
        OrdersIntent.getDayCommonInfo(intent);
    }
}
