package com.yang.yunwang.query.view.adapter.viewholder;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.ordersettel.OrderSettleResp;

public class SettlementListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final OrderSettleResp bean;
    private Context context;
    private ImageView image_logo;
    private TextView text_name;
    private TextView text_count;
    private TextView text_money;
    private View item_view;
    private String staff_id;

    public SettlementListViewHolder(View itemView, String staff_id, OrderSettleResp bean, Context context) {
        super(itemView);
        this.item_view = itemView;
        this.context = context;
        this.bean = bean;

        this.staff_id = staff_id;
        image_logo = (ImageView) itemView.findViewById(R.id.image_settlement_logo);
        text_name = (TextView) itemView.findViewById(R.id.text_settlement_name);
        text_money = (TextView) itemView.findViewById(R.id.text_settlement_money);
        text_count = (TextView) itemView.findViewById(R.id.text_settlement_count);
        this.setIsRecyclable(false);
        itemView.setOnClickListener(this);
    }

    public void bind(int pos) {
        String displayName = bean.getModel().get(pos).getDisplayName();
        String customeName = bean.getModel().get(pos).getCustomerName();
        String dname;
        if (!TextUtils.isEmpty(displayName)) {
            dname = displayName;
        } else if (!TextUtils.isEmpty(customeName)) {
            dname = customeName;
        } else {
            dname = "";
        }
        text_name.setText(dname);
        String customer_type = ConstantUtils.CUSTOMERS_TYPE;
        String staff_type = ConstantUtils.STAFF_TYPE;
        if (staff_id != null && !staff_id.equals("")) {
            image_logo.setImageResource(R.drawable.staff_logo);
        } else {
            if (customer_type != null && !customer_type.equals("")) {
                switch (customer_type) {
                    case "0":
                        image_logo.setImageResource(R.drawable.shop_logo);
                        break;
                    case "1":
                        image_logo.setImageResource(R.drawable.staff_logo);
                        break;
                }
            } else {
                switch (staff_type) {
                    case "0":
                        image_logo.setImageResource(R.drawable.shop_logo);
                        break;
                    case "1":
                        image_logo.setImageResource(R.drawable.staff_logo);
                        break;
                }
            }
        }

        String text = "";
        try {
            text = AmountUtils.changeF2Y(bean.getModel().get(pos).getTotalFee()).contains(".") ? AmountUtils.changeF2Y(bean.getModel().get(pos).getTotalFee()) +" 元": AmountUtils.changeF2Y(bean.getModel().get(pos).getTotalFee()) + ".00" + " 元";
        } catch (Exception e) {
            e.printStackTrace();
        }
        text_money.setText(text);
        String text1 = bean.getModel().get(pos).getTradecount() + "笔";
        text_count.setText(text1);
        item_view.setTag(pos);
    }

    @Override
    public void onClick(View view) {
        MyBundle intent = new MyBundle();
        String displayName = bean.getModel().get(Integer.parseInt(view.getTag().toString())).getDisplayName();
        String customeName = bean.getModel().get(Integer.parseInt(view.getTag().toString())).getCustomerName();
        String dname;
        if (!TextUtils.isEmpty(displayName)) {
            dname = displayName;
        } else if (!TextUtils.isEmpty(customeName)) {
            dname = customeName;
        } else {
            dname = "";
        }
        intent.put("names", dname);
        String totalFee ="";
        try {
            totalFee = AmountUtils.changeF2Y(bean.getModel().get(Integer.parseInt(view.getTag().toString())).getTotalFee()).contains(".") ? AmountUtils.changeF2Y(bean.getModel().get(Integer.parseInt(view.getTag().toString())).getTotalFee()) : AmountUtils.changeF2Y(bean.getModel().get(Integer.parseInt(view.getTag().toString())).getTotalFee()) + ".00" + " 元";
        } catch (Exception e) {
            e.printStackTrace();
        }
        intent.put("money", totalFee);
        String fee = null;
        try {
            fee = AmountUtils.changeF2Y( bean.getModel().get(Integer.parseInt(view.getTag().toString())).getFee()).contains(".") ?
                    AmountUtils.changeF2Y( bean.getModel().get(Integer.parseInt(view.getTag().toString())).getFee()) : AmountUtils.changeF2Y( bean.getModel().get(Integer.parseInt(view.getTag().toString())).getFee()) + ".00" + "元";
        } catch (Exception e) {
            e.printStackTrace();
        }
        intent.put("cash", fee);
        intent.put("cny_type", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getCashFeeType());
        intent.put("count", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getTradecount());
        OrdersIntent.settleInfo(intent);
    }
}
