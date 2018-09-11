package com.yang.yunwang.query.view.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yang.yunwang.base.R;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.api.bean.ordersearch.OrderSearchResp;

import java.util.List;

public class OrderListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final OrderSearchResp bean;
    public TextView text_money;
    public TextView text_code;
    public TextView text_date;
    public ImageView image_logo;
    public RelativeLayout relativeLayout;
    private ViewGroup item_view;
    //    private List<String> money_list;
//    private List<String> code_list;
//    private List<String> date_list;
//    private List<String> pay_type;
    private List<String> sys_no_list;
    private List<String> customer_name_list;
    private List<String> time_start_list;
    private Context context;

    public OrderListViewHolder(ViewGroup item_view, OrderSearchResp bean, Context context) {
        super(item_view);
        text_money = (TextView) item_view.findViewById(R.id.text_order_money);
        text_code = (TextView) item_view.findViewById(R.id.text_order_code);
        text_date = (TextView) item_view.findViewById(R.id.text_order_date);
        image_logo = (ImageView) item_view.findViewById(R.id.image_order_logo);
        relativeLayout = (RelativeLayout) item_view.findViewById(R.id.rel_list_item_area);
        this.item_view = item_view;
        this.bean = bean;
//        this.money_list = bean.getModel().getMoney_list();
//        this.code_list = bean.getCode_list();
//        this.date_list = bean.getDate_list();
//        this.pay_type = bean.getPay_type();
//        this.money_list = money_list;
//        this.code_list = code_list;
//        this.date_list = date_list;
//        this.pay_type = pay_type;
//        this.sys_no_list = sys_no_list;
//        this.customer_name_list = customer_name_list;
//        this.time_start_list = time_start_list;
        this.context = context;
        //防止所有recyclerview的item在被绘制时出现重复绘制的现象
        this.setIsRecyclable(false);
        itemView.setOnClickListener(this);
    }

    public void bind(int pos) {
        String money = "";
        try {
            money = AmountUtils.changeF2Y(bean.getModel().get(pos).getTotalFee());
        } catch (Exception e) {
            e.printStackTrace();
        }
        text_money.setText(money);
        text_code.setText(bean.getModel().get(pos).getOutTradeNo());
        text_date.setText(bean.getModel().get(pos).getTimeStart());
        String payType = bean.getModel().get(pos).getPayType();
        int resID;
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
//        Intent intent = new Intent(context, OrdersListInfoActivity.class);
//        Intent intent = new Intent();
        MyBundle myBundle = new MyBundle();
        myBundle.put("bean", bean.getModel().get(Integer.parseInt(v.getTag().toString())));
        OrdersIntent.orderListnfo(myBundle);
//        intent.putExtra("code", bean.getModel().get(Integer.parseInt(v.getTag().toString())).getOutTradeNo());
//        String date = text_date.getText().toString();
//        if (!TextUtils.isEmpty(date)) {
//            String timeStart = date.substring(0, 10) + " 00:00:00";
//            String timeEnd = date.substring(0, 10) + " 23:59:59";
//            intent.putExtra("Time_Start", timeStart);
//            intent.putExtra("Time_End", timeEnd);
//        }
//        context.startActivity(intent);
    }
}
