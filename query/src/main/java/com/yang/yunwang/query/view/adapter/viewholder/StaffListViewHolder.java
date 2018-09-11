package com.yang.yunwang.query.view.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.staffsearch.StaffListResp;
import com.yang.yunwang.query.view.staff.StaffListActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StaffListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private final StaffListResp bean;
    public TextView text_staff_name;
    public TextView text_staff_tel;
    public TextView text_staff_time;

    private Context context;
    private String inner_flag;

    public StaffListViewHolder(View itemView, StaffListResp bean, String inner_flag, Context context) {
        super(itemView);
        this.bean=bean;
        text_staff_name = (TextView) itemView.findViewById(R.id.text_staff_name);
        text_staff_tel = (TextView) itemView.findViewById(R.id.text_staff_tel);
        text_staff_time = (TextView) itemView.findViewById(R.id.text_staff_time);
        this.inner_flag = inner_flag;
        this.context = context;
        this.setIsRecyclable(false);
        itemView.setOnClickListener(this);
    }

    public void bind(int pos) {
        text_staff_name.setText(bean.getModel().get(pos).getDisplayName());
        text_staff_tel.setText(bean.getModel().get(pos).getPhoneNumber());
        String date_temp = bean.getModel().get(pos).getInDate();
        String time;
        if (!date_temp.equals("null")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            String date = date_temp.substring(6, date_temp.length() - 2);
            String param_date = format.format(new Date(Long.parseLong(date)));
            time=param_date;
        } else {
            time="";
        }
        text_staff_time.setText(time);
        itemView.setTag(pos);
    }

    @Override
    public void onClick(View view) {
        boolean isFromHome = ((StaffListActivity) context).isFrom_home();
        boolean isFromMerch = ((StaffListActivity) context).isFromMerch();
        boolean isFromHomeDis = ((StaffListActivity) context).isFromHomeDis();
        if (inner_flag != null && inner_flag.equals("intent_merchant_qr")) {
            MyBundle intent = new MyBundle();//context, PersonnelQRActivity.class
            intent.put("sysNO", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getSysNO()+"");
            //TODO switch
            OrdersIntent.persnoalQr(intent);
            //
//            context.startActivity(intent);
        } else {
            MyBundle intent = new MyBundle();//context, StaffInfoActivity.class
            intent.put("login_name", bean.getModel().get(Integer.parseInt(view.getTag().toString())).getLoginName());
            intent.put("staff_bean", bean.getModel().get(Integer.parseInt(view.getTag().toString())));
            intent.put(ConstantUtils.fromHome, isFromHome);
            intent.put(ConstantUtils.fromMerch, isFromMerch);
            intent.put(ConstantUtils.fromHomeDis, isFromHomeDis);
//            context.startActivity(intent);
            OrdersIntent.getStaffInfo(intent);
        }
    }
}
