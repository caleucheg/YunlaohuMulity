package com.yang.yunwang.home.mainhome.view.adapter;


import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.bean.cusallcoate.respone.CusUserAllcoateResp;
import com.yang.yunwang.home.mainhome.bean.ordersearch.ordernew.respone.CommonNewOrderResp;
import com.yang.yunwang.home.mainhome.view.viewholder.CusUserAlListViewHolder;
import com.yang.yunwang.home.mainhome.view.viewholder.OrderListViewHolder;


public class CommonHomeListRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //此类是所有RecyclerView的集合，利用各个RecyclerView所在的Activity包名作为区分
    private final String ORDERS_CLASS = IHomeProvider.NEW_ORDER;
    private final String CUS_USER_AL_LIST_CLASS = IHomeProvider.HOME_ACT_CUS_USER_AL_LIST;

    private final String DETIAL_CLASS = IOrdersProvider.ORDERS_ACT_ORDER_DETIALS_LIST;
    private boolean isFromArouter = false;
    private CusUserAllcoateResp cusUserAlBean;

    private boolean hasRefundRole = false;
    private boolean isFromMerHome;

    private boolean isPrintO = false;

    private boolean isSettlement;
    private boolean is_top_rate = false;

    private int f = 0;
    private Context context;
    private int layout;
    private CommonNewOrderResp orders_bean;

    private int count = 10;
    private String flag;
    private String inner_flag;  //员工二维码入口表示，用于区分员工列表入口是员工二维码进入还是正常菜单功能进入
    private String staff_id;    //员工ID，用于区分汇总列表的入口是由员工列表进入还是正常主菜单进入
    private OrderListViewHolder orderListViewHolder;
    private CusUserAlListViewHolder cusUserAlListViewHolder;
    private int oldPos = 0;
    private MyItemClickListener mItemClickListener;
    private boolean isBind;

    //订单列表
    public CommonHomeListRecAdapter(Context context, CommonNewOrderResp bean, String flag, int layout, boolean isFromArouter) {
        this.context = context;
        this.orders_bean = bean;
//        KLog.i(bean.getModel().size());
        this.layout = layout;
        this.flag = flag;
        this.isFromArouter = isFromArouter;
    }

    public CommonHomeListRecAdapter(Context context, CusUserAllcoateResp bean, String flag, int layout) {
        this.context = context;
        this.cusUserAlBean = bean;
//        KLog.i(bean.getModel().size());
        this.layout = layout;
        this.flag = flag;
    }

    public boolean isBind() {
        return isBind;
    }

    public boolean is_top_rate() {
        return is_top_rate;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (this.flag) {
            case ORDERS_CLASS:
                orderListViewHolder.bind(position);
                break;

            case CUS_USER_AL_LIST_CLASS:
                cusUserAlListViewHolder.bind(position);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        KLog.i(this.flag);
//        KLog.i(ORDERS_CLASS);
        switch (this.flag) {
            case ORDERS_CLASS:
//                KLog.i("bind a");
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    orderListViewHolder = new OrderListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            orders_bean, context, isFromArouter);
                } else {
                    orderListViewHolder = new OrderListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.new_rec_order_last_item, parent, false),
                            orders_bean, context, isFromArouter);
                }
                orderListViewHolder.setIsRecyclable(false);
                return orderListViewHolder;

            case CUS_USER_AL_LIST_CLASS:
//                KLog.i("bind a");
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    cusUserAlListViewHolder = new CusUserAlListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            cusUserAlBean, context);
                } else {
                    cusUserAlListViewHolder = new CusUserAlListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_user_allcoate_last_item, parent, false),
                            cusUserAlBean, context);
                }
                cusUserAlListViewHolder.setIsRecyclable(false);
                return cusUserAlListViewHolder;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        switch (this.flag) {
            case ORDERS_CLASS:
                if (position > count && position == orders_bean.getData().getModel().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }

            case CUS_USER_AL_LIST_CLASS:
                if (position > count && position == cusUserAlBean.getData().getModel().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }
            default:
                return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
//        KLog.i(orders_bean.getModel().size());
        switch (this.flag) {
            case ORDERS_CLASS:
                return orders_bean.getData().getModel().size();
            case CUS_USER_AL_LIST_CLASS:
                return cusUserAlBean.getData().getModel().size();
        }
        return 0;
    }

    public void specialUpdate() {
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        };
        handler.post(r);
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        KLog.i("set click");
        this.mItemClickListener = listener;
    }

    private enum ITEM_TYPE {
        ITEM_TYPE_NORMAL, ITEM_TYPE_LAST
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int postion);
    }
}
