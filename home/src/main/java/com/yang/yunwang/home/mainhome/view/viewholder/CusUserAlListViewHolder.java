package com.yang.yunwang.home.mainhome.view.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yang.yunwang.base.R;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.home.mainhome.bean.cusallcoate.respone.CusUserAllcoateResp;

import java.util.List;

public class CusUserAlListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final CusUserAllcoateResp bean;
    private final TextView tv_money;
    public TextView tv_cus_name;
    public TextView tv_cus_phone;
    public TextView tv_trade_count;
    private ViewGroup item_view;
    private List<String> sys_no_list;
    private List<String> customer_name_list;
    private List<String> time_start_list;
    private Context context;

    public CusUserAlListViewHolder(ViewGroup item_view, CusUserAllcoateResp bean, Context context) {
        super(item_view);
//        KLog.i("bind");
        tv_cus_name = (TextView) item_view.findViewById(R.id.tv_cus_name);
        tv_cus_phone = (TextView) item_view.findViewById(R.id.tv_cus_phone);
        tv_trade_count = (TextView) item_view.findViewById(R.id.tv_trade_count);
        tv_money = (TextView) item_view.findViewById(R.id.tv_money);
        this.item_view = item_view;
        this.bean = bean;
        this.context = context;
        //防止所有recyclerview的item在被绘制时出现重复绘制的现象
        this.setIsRecyclable(false);
        itemView.setOnClickListener(this);
    }

    public void bind(int pos) {
        String money = "";
        try {
            money = AmountUtils.changeF2Y(Long.valueOf(bean.getData().getModel().get(pos).getFee()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_money.setText(money + "元");
        tv_trade_count.setText(bean.getData().getModel().get(pos).getTotalCount() + "笔");
        tv_cus_name.setText(bean.getData().getModel().get(pos).getDisplayName());
        tv_cus_phone.setText(bean.getData().getModel().get(pos).getLoginName());


        item_view.setTag(pos);
    }

    @Override
    public void onClick(View v) {
//        MyBundle myBundle = new MyBundle();
//        Gson gson=new Gson();
//        Model value = bean.getModel().get(Integer.parseInt(v.getTag().toString()));
//        myBundle.put("bean", gson.toJson(value));
//        myBundle.put("isNewOrder",true);
//        OrdersIntent.newOrderListnfo(myBundle);
    }
}
