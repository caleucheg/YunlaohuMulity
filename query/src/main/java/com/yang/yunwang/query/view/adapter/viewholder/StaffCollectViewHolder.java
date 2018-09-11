package com.yang.yunwang.query.view.adapter.viewholder;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.susersettle.list.SStaffCollectResp;

public class StaffCollectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final SStaffCollectResp bean;
    private Context context;
    private ImageView image_logo;
    private TextView text_name;
    private TextView text_count;
    private TextView text_money;

    private View item_view;
    private String staff_id;

    public StaffCollectViewHolder(View itemView, SStaffCollectResp bean, Context context) {
        super(itemView);
        this.item_view = itemView;
        this.context = context;
        this.bean=bean;
        this.staff_id = staff_id;
//        image_logo = (ImageView) itemView.findViewById(R.id.image_settlement_logo);
        text_name = (TextView) itemView.findViewById(R.id.text_order_money);
        text_money = (TextView) itemView.findViewById(R.id.text_order_code);
        text_count = (TextView) itemView.findViewById(R.id.text_order_date);
        this.setIsRecyclable(false);
        itemView.setOnClickListener(this);
    }

    public void bind(int pos) {

//        login_Name.add(item.getString("Login_Name"));
//        display_Name.add(item.getString("Display_Name"));
//        phone_Number.add(item.getString("Phone_Number"));
//        total_fee.add((AmountUtils.changeF2Y(item.getString("Total_fee"))));
//        refund_fee.add((AmountUtils.changeF2Y(item.getString("refund_fee"))));
//        fee.add((AmountUtils.changeF2Y(item.getString("Fee"))));
        text_name.setText(bean.getModel().get(pos).getDisplayName());
        Long total_fee = bean.getModel().get(pos).getTotalFee();
        Long fee = bean.getModel().get(pos).getFee();
        String s = "";
        String s1 = "";
        try {
            s = AmountUtils.changeF2Y(fee);
            s1 = AmountUtils.changeF2Y(total_fee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        text_money.setText("实际金额 ¥ " + s);
        text_count.setText("交易金额 ¥ " + s1);
        item_view.setTag(pos);
    }

    @Override
    public void onClick(View view) {
        MyBundle intent = new MyBundle();//context, StaffCollectInfoActivity.class
        intent.put("login_Name", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getLoginName());
        intent.put("display_Name", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getDisplayName());
        intent.put("phone_Number", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getPhoneNumber());
        Long totalFee = bean.getModel().get(Integer.parseInt(view.getTag().toString())).getTotalFee();
        Long refundFee = bean.getModel().get(Integer.parseInt(view.getTag().toString())).getRefundFee();
        Long fee = bean.getModel().get(Integer.parseInt(view.getTag().toString())).getFee();
        String value = "";
        String value1 = "";
        String value2 = "";
        try {
            value2 = AmountUtils.changeF2Y(fee);
            value1 = AmountUtils.changeF2Y(refundFee);
            value = AmountUtils.changeF2Y(totalFee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        intent.put("total_fee", value);
        intent.put("refund_fee", value1);
        intent.put("fee", value2);
        OrdersIntent.getSStafCollectInfo(intent);
//        context.startActivity(intent);
    }
}
