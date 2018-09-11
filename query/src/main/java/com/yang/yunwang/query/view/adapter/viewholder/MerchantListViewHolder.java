package com.yang.yunwang.query.view.adapter.viewholder;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.merchsearch.MerchListResp;
import com.yang.yunwang.query.view.merch.MerchantListActivity;

public class MerchantListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final MerchListResp bean;
    public TextView text_shop_name;

    private Context context;

    public MerchantListViewHolder(View itemView, MerchListResp bean, Context context) {
        super(itemView);
        text_shop_name = (TextView) itemView.findViewById(R.id.text_shop_name);
        this.bean = bean;
        this.context = context;
        this.setIsRecyclable(false);
        itemView.setOnClickListener(this);
    }

    public void bind(int pos) {
        text_shop_name.setText(bean.getModel().get(pos).getCustomerName());
        itemView.setTag(pos);
    }

    @Override
    public void onClick(View view) {
        boolean isFromHome = ((MerchantListActivity) context).isFrom_home();
        boolean merchStaff = ((MerchantListActivity) context).isMerchStaff();
        boolean allocate = ((MerchantListActivity) context).isAllocate();
        boolean isFWS = ((MerchantListActivity) context).isFWS();
        String staff_id = ((MerchantListActivity) context).getStaff_id();
        MyBundle intent = new MyBundle();//context, MerchantInfoActivity.class
        intent.put("shop_id", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getSysNo()+"");
        KLog.i(bean.getModel().get(Integer.parseInt(view.getTag().toString())).getSysNo());
        intent.put("from_home", isFromHome);
        intent.put("allocate", allocate);
        intent.put("staff_id", staff_id);
        intent.put("shop_bean",bean.getModel().get(Integer.parseInt(view.getTag().toString())));
        intent.put(ConstantUtils.FWS_YUANGONG, isFWS);
        intent.put(ConstantUtils.merchStaff, merchStaff);
//        context.startActivity(intent);
        OrdersIntent.getMerchInfo(intent);
    }
}
