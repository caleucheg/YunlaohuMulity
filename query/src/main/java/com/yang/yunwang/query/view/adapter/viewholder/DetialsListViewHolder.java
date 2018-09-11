package com.yang.yunwang.query.view.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.moduleinterface.router.MyRouter;
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.orderprint.OrdersDetialResp;
import com.yang.yunwang.query.view.order.list.RefundListPrintActivity;


public class DetialsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final boolean isPrintO;
    private final boolean isFromMerHome;
    private final OrdersDetialResp bean;
    public TextView text_money;
    public TextView text_code;
    public TextView text_date;
    public TextView text_status;
    public ImageView image_logo;
    private ViewGroup item_view;

    private Context context;

    public DetialsListViewHolder(ViewGroup item_view, OrdersDetialResp bean, Context context, boolean isPrintO, boolean isFromMerHome) {
        super(item_view);
        this.isPrintO = isPrintO;
        this.isFromMerHome = isFromMerHome;
        this.bean = bean;
        text_money = (TextView) item_view.findViewById(R.id.text_order_money);
        text_code = (TextView) item_view.findViewById(R.id.text_order_code);
        text_date = (TextView) item_view.findViewById(R.id.text_order_date);
        text_status = (TextView) item_view.findViewById(R.id.text_order_status);
        image_logo = (ImageView) item_view.findViewById(R.id.image_order_logo);
        this.item_view = item_view;
        this.context = context;
        this.setIsRecyclable(false);//防止item重复复用，数据加载顺序错乱
        item_view.setOnClickListener(this);
    }

    public void bind(int pos) {
        String status_result = "";
        String money = "";
        String money_releas = "";
        try {
            money = AmountUtils.changeF2Y(bean.getModel().get(pos).getTotalFee());
            money_releas = AmountUtils.changeF2Y(bean.getModel().get(pos).getFee());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Double.parseDouble(money_releas) == Double.parseDouble(money)) {
            //退款，没有进行过一笔退款
            status_result = context.getString(R.string.unrefund_status_print);
        } else if (Double.parseDouble(money_releas) < Double.parseDouble(money) && Double.parseDouble(money_releas) > 0) {
            //部分退款
            status_result = context.getString(R.string.unrefund_step);
        } else {
            //退款完成
            status_result = context.getString(R.string.refunded_status);
        }
        String textA = "";
        String text = "";
        try {
            textA = AmountUtils.changeF2Y(bean.getModel().get(pos).getTotalFee());
            text = AmountUtils.changeF2Y(bean.getModel().get(pos).getRefundFee());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isPrintO) {
            text_money.setText(textA);
        } else {
            text_money.setText(text);
        }
        text_code.setText(bean.getModel().get(pos).getOutTradeNo());
        text_date.setText(bean.getModel().get(pos).getTimeStart());
        text_status.setText(status_result);
        if (isPrintO) {
            if (status_result.equals(context.getResources().getString(R.string.unrefund_status_print))
                    || status_result.equals(context.getResources().getString(R.string.unrefund_step))) {
                text_status.setTextColor(context.getResources().getColor(R.color.orange_color));
            } else if (status_result.equals(context.getResources().getString(R.string.refunded_status))) {
                text_status.setTextColor(context.getResources().getColor(R.color.label_qr_success));
            }
        } else {
            if (!status_result.equals("处理完毕")) {
                text_status.setTextColor(context.getResources().getColor(R.color.orange_color));
            }
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

    @Override
    public void onClick(View view) {
//        Intent intent = new Intent(context, RefundInfoActivity.class);
        MyBundle intent = new MyBundle();
        intent.put("isPrintO", isPrintO);
        intent.put("FROM_MER_HOME", isFromMerHome);
        if (isFromMerHome || isPrintO) {
            intent.put("Old_sys_no", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getOldSysNo());
            intent.put("Fee_list", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getFee());
            intent.put("Refund_Count_list", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getRefundCount());
        }
        intent.put("code_list", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getOutTradeNo());
        intent.put("money_list", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getTotalFee());
        intent.put("pay_type", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getPayType());
        intent.put("order_time_list", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getTimeStart());
        intent.put("refund_date_list", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getTimeEnd());
        intent.put("refund_list", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getRefundFee());
        String status_result = "";
        String money = "";
        String money_releas = "";
        try {
            money = AmountUtils.changeF2Y(bean.getModel().get(Integer.parseInt(view.getTag().toString())).getTotalFee());
            money_releas = AmountUtils.changeF2Y(bean.getModel().get(Integer.parseInt(view.getTag().toString())).getFee());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Double.parseDouble(money_releas) == Double.parseDouble(money)) {
            //退款，没有进行过一笔退款
            status_result = context.getString(R.string.unrefund_status_print);
        } else if (Double.parseDouble(money_releas) < Double.parseDouble(money) && Double.parseDouble(money_releas) > 0) {
            //部分退款
            status_result = context.getString(R.string.unrefund_step);
        } else {
            //退款完成
            status_result = context.getString(R.string.refunded_status);
        }
        intent.put("sys_no_list", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getSysNo());
        intent.put("order_status_list", status_result);
        intent.put("Transaction_id_list", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getTransactionId());
        if (isFromMerHome) {
//            ((RefundListPrintActivity) context).startActivityForResult(intent, 103);
            MyRouter.newInstance(IOrdersProvider.ORDERS_ACT_REFUND_INFO)
                    .withBundle(intent)
                    .navigation((RefundListPrintActivity)context,103);
        } else {
            OrdersIntent.refundInfo(intent);
        }
    }
}
