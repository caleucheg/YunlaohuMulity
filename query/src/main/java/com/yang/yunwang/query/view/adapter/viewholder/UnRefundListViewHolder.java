package com.yang.yunwang.query.view.adapter.viewholder;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.refundsearch.refundlist.RefundListResp;
import com.yang.yunwang.query.view.order.list.UnRefundListActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UnRefundListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final RefundListResp bean;
    private final boolean hasRole;
    public TextView text_money;
    public TextView text_code;
    public TextView text_date;
    public TextView text_status;
    public ImageView image_logo;
    private ViewGroup item_view;

    private Context context;
    private UnRefundListActivity activity;

    public UnRefundListViewHolder(ViewGroup item_view, RefundListResp bean, Context context, boolean hasRole) {
        super(item_view);
        text_money = (TextView) item_view.findViewById(R.id.text_order_money);
        text_code = (TextView) item_view.findViewById(R.id.text_order_code);
        text_date = (TextView) item_view.findViewById(R.id.text_order_date);
        text_status = (TextView) item_view.findViewById(R.id.text_order_status);
        image_logo = (ImageView) item_view.findViewById(R.id.image_order_logo);
        this.item_view = item_view;
        this.hasRole = hasRole;
        this.context = context;
        this.bean = bean;
        this.activity = (UnRefundListActivity) context;
        this.setIsRecyclable(false);        //防止item重复复用，数据加载顺序错乱
        item_view.setOnClickListener(this);
    }

    public void bind(int pos) {
        long totalfee = bean.getModel().get(pos).getTotalFee();
        String text = "";
        try {
            text = AmountUtils.changeF2Y(totalfee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        text_money.setText(text);
        text_code.setText(bean.getModel().get(pos).getOutTradeNo());
        text_date.setText(bean.getModel().get(pos).getTimeStart());
        String status = "";

        text_status.setText(filterStatus(status, pos));

        String s = text_status.getText().toString();
        if (s.equals(context.getResources().getString(R.string.unrefund_status)) || s.equals(context.getResources().getString(R.string.unrefund_step))) {
            text_status.setTextColor(context.getResources().getColor(R.color.orange_color));
        } else if (s.equals(context.getResources().getString(R.string.refunded_status))) {
            text_status.setTextColor(context.getResources().getColor(R.color.label_qr_success));
        }
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

    private String filterStatus(String status, int pos) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String cur_time = formatter.format(curDate);
        String order_time = bean.getModel().get(pos).getTimeStart();
        String money = "";
        String money_releas = "";
        try {
            money = AmountUtils.changeF2Y(bean.getModel().get(pos).getTotalFee());
            money_releas = AmountUtils.changeF2Y(bean.getModel().get(pos).getFee());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!order_time.substring(0, 10).equals(cur_time) && !hasRole) {
            //无法退款,非当天订单
            status = context.getString(R.string.unrefund_error);
        } else if (Double.parseDouble(money_releas) == Double.parseDouble(money)) {
            //退款，没有进行过一笔退款
            status = context.getString(R.string.unrefund_status);
        } else if (Double.parseDouble(money_releas) < Double.parseDouble(money) && Double.parseDouble(money_releas) > 0) {
            //部分退款
            status = context.getString(R.string.unrefund_step);
        } else {
            //退款完成
            status = context.getString(R.string.refunded_status);
        }
        return status;

    }


    @Override
    public void onClick(View view) {
        MyBundle intent = new MyBundle();
        intent.put("hasRole", hasRole);
        intent.put("code_list", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getOutTradeNo());
        intent.put("refundbean", bean.getModel().get(Integer.parseInt(view.getTag().toString())));
        OrdersIntent.unRefundInfo(intent, activity, 101);
//        this.activity.finish();
    }
}
