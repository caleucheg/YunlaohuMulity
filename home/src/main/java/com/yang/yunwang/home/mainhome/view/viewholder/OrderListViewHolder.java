package com.yang.yunwang.home.mainhome.view.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yang.yunwang.base.R;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.respone.CommonNewOrderResp;
import com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.respone.Model;

import java.util.List;

public class OrderListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final CommonNewOrderResp bean;
    private final boolean isFromArouter;
    public TextView text_money;
    public TextView text_code;
    public TextView text_date;
    public ImageView image_logo;
    private ViewGroup item_view;
    private List<String> sys_no_list;
    private List<String> customer_name_list;
    private List<String> time_start_list;
    private Context context;

    public OrderListViewHolder(ViewGroup item_view, CommonNewOrderResp bean, Context context, boolean isFromArouter) {
        super(item_view);
//        KLog.i("bind");
        text_money = (TextView) item_view.findViewById(R.id.tv_money);
        text_code = (TextView) item_view.findViewById(R.id.tv_order_num);
        text_date = (TextView) item_view.findViewById(R.id.tv_date);
        image_logo = (ImageView) item_view.findViewById(R.id.iv_type_logo);
        this.item_view = item_view;
        this.bean = bean;
        this.context = context;
        this.isFromArouter = isFromArouter;
        //防止所有recyclerview的item在被绘制时出现重复绘制的现象
        this.setIsRecyclable(false);
        itemView.setOnClickListener(this);
    }

    public void bind(int pos) {
        String money = "";
        try {
            money = AmountUtils.changeF2YWithDDD(Long.valueOf(bean.getData().getModel().get(pos).getTotalFee()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        text_money.setText(money);
        text_code.setText(bean.getData().getModel().get(pos).getOutTradeNo());
        text_date.setText(bean.getData().getModel().get(pos).getTimeStart());
        String payType = bean.getData().getModel().get(pos).getPayType();
        int resID;
        if (ConstantUtils.WX_Type.contains(payType)) {
            resID = R.drawable.order_item_wx_logo;
        } else if (ConstantUtils.ZFB_Type.contains(payType)) {
            resID = R.drawable.order_item_zfb_logo;
        } else {
            resID = R.drawable.order_item_wx_logo;
        }
        image_logo.setImageDrawable(context.getResources().getDrawable(resID));

        item_view.setTag(pos);
    }

    @Override
    public void onClick(View v) {
        MyBundle myBundle = new MyBundle();
        Gson gson = new Gson();
        Model value = bean.getData().getModel().get(Integer.parseInt(v.getTag().toString()));
        myBundle.put("bean", gson.toJson(value));
        myBundle.put("isNewOrder", true);
        myBundle.put("isFromArouter", isFromArouter);
        OrdersIntent.newOrderListnfo(myBundle);
    }
}
