package com.yang.yunwang.query.view.adapter.viewholder;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.allcopage.AllocateBean;
import com.yang.yunwang.query.view.adapter.CommonListRecAdapter;
import com.yang.yunwang.query.view.allcoate.AllocateActivity;

import java.util.List;

public class AllocateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final CommonListRecAdapter adapter;
    private final ImageView image_order_logo;
    private final AllocateBean bean;
    private List<Integer> flag_list;
    private Switch switch_allocate;
    private List<String> login_Name;
    private Context context;
    private TextView text_name;
    private TextView text_money;
    private List<String> display_Name;
    private View item_view;
    private int oldPos = 0;
    private CommonListRecAdapter.MyItemClickListener mListener;
    private boolean fromHome;

    public AllocateViewHolder(CommonListRecAdapter commonListRecAdapter, View itemView, AllocateBean bean, Context context, CommonListRecAdapter.MyItemClickListener listener) {
        super(itemView);
        this.item_view = itemView;
        this.context = context;
        this.adapter = commonListRecAdapter;
        this.mListener = listener;
        this.bean = bean;
        login_Name = bean.getList_cno();
        display_Name = bean.getList_name();
        flag_list = bean.getList_flag();
        image_order_logo = (ImageView) itemView.findViewById(R.id.image_order_logo);
        text_name = (TextView) itemView.findViewById(R.id.text_staff_name);
        switch_allocate = (Switch) itemView.findViewById(R.id.switch_allocate);
        fromHome = ((AllocateActivity) context).isFromHome();
        if (fromHome) {
            image_order_logo.setVisibility(View.GONE);
        }

        this.setIsRecyclable(false);
    }

    public void bind(final int pos) {
        itemView.setOnClickListener(this);
        KLog.i(pos);
        text_name.setText(display_Name.get(pos));
        switch_allocate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (fromHome) {
                    bean.getList_flag().set(pos, isChecked ? 1 : 0);
                } else {
                    for (int i = 0; i < bean.getList_flag().size(); i++) {
                        bean.getList_flag().set(i, 0);
                    }

                    bean.getList_flag().set(pos, 1);
                    KLog.i(bean.getList_cno().get(pos));
                    if (!adapter.isBind()) {
                        adapter.notifyDataSetChanged();
                    }

                }
                KLog.i(bean.getList_flag());
            }
        });
        Integer f = flag_list.get(pos);
        if (f == 1) {
            switch_allocate.setChecked(true);
        } else {
            switch_allocate.setChecked(false);
        }
    }


    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onItemClick(view, getPosition());
        }
    }
}
