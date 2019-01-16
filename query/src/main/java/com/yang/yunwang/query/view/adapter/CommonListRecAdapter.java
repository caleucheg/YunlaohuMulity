package com.yang.yunwang.query.view.adapter;


import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.allcopage.AllocateBean;
import com.yang.yunwang.query.api.bean.commondaycollect.CommonDayCollectResp;
import com.yang.yunwang.query.api.bean.commonrefund.CommonRefundResp;
import com.yang.yunwang.query.api.bean.commonsearch.CommonOrdersResp;
import com.yang.yunwang.query.api.bean.merchsearch.MerchListResp;
import com.yang.yunwang.query.api.bean.orderprint.OrdersDetialResp;
import com.yang.yunwang.query.api.bean.ordersearch.OrderSearchResp;
import com.yang.yunwang.query.api.bean.ordersettel.OrderSettleResp;
import com.yang.yunwang.query.api.bean.refundsearch.refundlist.RefundListResp;
import com.yang.yunwang.query.api.bean.refundsearchs.RefundListSResp;
import com.yang.yunwang.query.api.bean.staffsearch.StaffListResp;
import com.yang.yunwang.query.api.bean.susersettle.list.SStaffCollectResp;
import com.yang.yunwang.query.view.adapter.viewholder.AllocateViewHolder;
import com.yang.yunwang.query.view.adapter.viewholder.CommonCardActiveAlListViewHolder;
import com.yang.yunwang.query.view.adapter.viewholder.CommonCardCusListViewHolder;
import com.yang.yunwang.query.view.adapter.viewholder.CommonDayCollectHolder;
import com.yang.yunwang.query.view.adapter.viewholder.CommonRateViewHolder;
import com.yang.yunwang.query.view.adapter.viewholder.CommonRefundViewHolder;
import com.yang.yunwang.query.view.adapter.viewholder.DetialsListViewHolder;
import com.yang.yunwang.query.view.adapter.viewholder.MerchantListViewHolder;
import com.yang.yunwang.query.view.adapter.viewholder.OrderListViewHolder;
import com.yang.yunwang.query.view.adapter.viewholder.RefundListViewHolder;
import com.yang.yunwang.query.view.adapter.viewholder.SettlementListViewHolder;
import com.yang.yunwang.query.view.adapter.viewholder.StaffCollectViewHolder;
import com.yang.yunwang.query.view.adapter.viewholder.StaffListViewHolder;
import com.yang.yunwang.query.view.adapter.viewholder.UnRefundListViewHolder;


public class CommonListRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //此类是所有RecyclerView的集合，利用各个RecyclerView所在的Activity包名作为区分
    private final String ORDERS_CLASS = IOrdersProvider.ORDERS_ACT_ORDER_SEARCH;
    private final String REFUND_CLASS = IOrdersProvider.ORDERS_ACT_REFUND_LIST;
    private final String UNREFUND_CLASS =IOrdersProvider.ORDERS_ACT_UNREFUND_LIST;
    private final String SHOP_CLASS = IOrdersProvider.ORDERS_ACT_MERCH_LIST;
    private final String STAFF_SEARCH_CLASS = IOrdersProvider.ORDERS_ACT_STAFF_LIST;
    private final String SETTLEMENT_CLASS = IOrdersProvider.ORDERS_ACT_ORDER_SETTLE;
    private final String STAFF_COLLECT = IOrdersProvider.ORDERS_ACT_SSTAFF_COLLECT_LIST;
    private final String ALLOCATE_CLASS = IOrdersProvider.ORDERS_ACT_ALLCOATE_PAGE;
    private final String COMMON_RATE_CLASS = IOrdersProvider.ORDERS_ACT_COMMON_LIST;
    private final String COMMON_RATE_REFUND_CLASS = IOrdersProvider.ORDERS_ACT_COMMON_REFUND_LIST;
    private final String COMMON_DAY_COLLECT_CLASS = IOrdersProvider.ORDERS_ACT_DAY_COMMON_LIST;
    private final String COMMON_CARD_CUS_CLASS = IOrdersProvider.ORDERS_ACT_COMMON_CARD_CUS_LIST;
    private final String COMMON_CARD_STAFF_CLASS = IOrdersProvider.ORDERS_ACT_COMMON_CARD_STAFF_LIST;
    private final String COMMON_CARD_ACTIVE_CLASS = IOrdersProvider.ORDERS_ACT_COMMON_CARD_ACTICVE_LIST;
    private OrdersDetialResp detialsBean;
    private final String DETIAL_CLASS =IOrdersProvider.ORDERS_ACT_ORDER_DETIALS_LIST;
    private RefundListSResp refund_beanS;
    private boolean hasRefundRole=false;
    private boolean isFromMerHome;
    private CommonDayCollectResp commonDayCollcetBean;
    private boolean isPrintO = false;
    private CommonRefundResp commonRateRefundbean;
    private boolean isSettlement;
    private boolean is_top_rate = false;
    private CommonOrdersResp commonRatebean;
    private AllocateBean allcoateBean;
    private int f = 0;
    private Context context;
    private int layout;
    private OrderSearchResp orders_bean;
    private RefundListResp refund_bean;
    private MerchListResp shop_bean;
    private SStaffCollectResp staff_Collect_Bean;
    private OrderSettleResp settlement_bean;
    private StaffListResp staff_search_bean;
    //TODO count=1
    private int count = 10;
    private String flag;
    private String inner_flag;  //员工二维码入口表示，用于区分员工列表入口是员工二维码进入还是正常菜单功能进入
    private String staff_id;    //员工ID，用于区分汇总列表的入口是由员工列表进入还是正常主菜单进入
    private OrderListViewHolder orderListViewHolder;
    private RefundListViewHolder refundListViewHolder;
    private UnRefundListViewHolder unRefundListViewHolder;
    private MerchantListViewHolder merchantListViewHolder;
    private StaffListViewHolder staffListViewHolder;
    private SettlementListViewHolder settlementListViewHolder;
    private StaffCollectViewHolder staffCollectViewHolder;
    private AllocateViewHolder allocateViewHolder;
    private int oldPos = 0;
    private MyItemClickListener mItemClickListener;
    private DetialsListViewHolder detialsListViewHolder;
    private CommonRateViewHolder commonRateViewHolder;
    private CommonRefundViewHolder commonRefundViewHolder;
    private CommonDayCollectHolder commonDayCollectViewHolder;
    private CommonCardCusListViewHolder commonCardCusListViewHolder;
    private CommonCardActiveAlListViewHolder commonCardActiveListViewHolder;

    public CommonListRecAdapter(Context context, OrderSettleResp orderSettleResp, String flag, int layout) {
        this.context = context;
        this.layout = layout;
        this.flag = flag;
        this.settlement_bean = orderSettleResp;
    }

    public boolean isBind() {
        return isBind;
    }

    private boolean isBind;

    //汇总列表
    public CommonListRecAdapter(Context context, OrderSettleResp settlement_bean, String staff_id, String flag, int layout) {
        this.context = context;
        this.layout = layout;
        this.flag = flag;
        this.settlement_bean = settlement_bean;
        this.staff_id = staff_id;
    }
    //退款列表与退款查询
    public CommonListRecAdapter(Context context, RefundListResp bean, String flag, int layout,boolean hasRole) {
        this.context = context;
        this.refund_bean = bean;
        this.flag = flag;
        this.hasRefundRole=hasRole;
        this.layout = layout;
    }

    public CommonListRecAdapter(Context context, RefundListSResp bean, String flag, int layout) {
        this.context = context;
        this.refund_beanS = bean;
        this.flag = flag;
        this.layout = layout;
    }

    //订单列表
    public CommonListRecAdapter(Context context, OrderSearchResp bean, String flag, int layout) {
        this.context = context;
        this.orders_bean = bean;
        this.layout = layout;
        this.flag = flag;
    }

    public CommonListRecAdapter(Context context, OrdersDetialResp bean, String flag, int layout, boolean isPrintO, boolean isFromMerHome) {
        this.context = context;
        this.detialsBean = bean;
        this.flag = flag;
        this.isPrintO = isPrintO;
        this.isFromMerHome = isFromMerHome;
        this.layout = layout;
    }

    public CommonListRecAdapter(Context context, CommonOrdersResp bean, String flag, int layout, boolean is_top_rate, boolean isSettlement) {
        this.context = context;
        this.commonRatebean = bean;
        this.layout = layout;
        this.flag = flag;
        this.is_top_rate = is_top_rate;
        this.isSettlement = isSettlement;

    }

    public CommonListRecAdapter(Context context, CommonDayCollectResp commonDayCollcetBean, String flag, int layout) {
        this.context = context;
        this.layout = layout;
        this.flag = flag;
        this.commonDayCollcetBean = commonDayCollcetBean;
    }

    public CommonListRecAdapter(Context context, CommonRefundResp bean, String flag, int layout, boolean is_top_rate, boolean isSettlement) {
        this.context = context;
        this.commonRateRefundbean = bean;
        this.layout = layout;
        this.flag = flag;
        this.is_top_rate = is_top_rate;
        this.isSettlement = isSettlement;
    }

    public CommonListRecAdapter(Context context, StaffListResp bean,  String inner_flag, String flag, int layout) {
        this.context = context;
        this.staff_search_bean = bean;
        this.flag = flag;
        this.layout = layout;
        this.inner_flag = inner_flag;
    }

    public CommonListRecAdapter(Context context, MerchListResp bean, String flag, int layout) {
        this.context = context;
        this.shop_bean = bean;
        this.flag = flag;
        this.layout = layout;
    }



//
    //调拨
    public CommonListRecAdapter(Context context, AllocateBean bean, String flag, int layout, MyItemClickListener mItemClickListener) {
        this.context = context;
        this.flag = flag;
        this.allcoateBean = bean;
        this.layout = layout;
        this.mItemClickListener = mItemClickListener;

    }

    public CommonListRecAdapter(Context context, SStaffCollectResp bean, String flag, int layout) {
        this.context = context;
        this.staff_Collect_Bean = bean;
        this.layout = layout;
        this.flag = flag;
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
            case REFUND_CLASS:
                refundListViewHolder.bind(position);
                break;
            case DETIAL_CLASS:
                detialsListViewHolder.bind(position);
                break;
            case UNREFUND_CLASS:
                unRefundListViewHolder.bind(position);
                break;
            case SHOP_CLASS:
                merchantListViewHolder.bind(position);
                break;
            case COMMON_CARD_CUS_CLASS:
                commonCardCusListViewHolder.bind(position);
                break;
            case COMMON_CARD_STAFF_CLASS:
                commonCardCusListViewHolder.bind(position);
                break;
            case COMMON_CARD_ACTIVE_CLASS:
                commonCardActiveListViewHolder.bind(position);
                break;
            case STAFF_SEARCH_CLASS:
                staffListViewHolder.bind(position);
                break;
            case SETTLEMENT_CLASS:
                settlementListViewHolder.bind(position);
                break;
            case STAFF_COLLECT:
                //Log.i("adaSta","bind");
                staffCollectViewHolder.bind(position);
                break;
            case ALLOCATE_CLASS:
//                KLog.i("pos"+position);
                isBind=true;
                allocateViewHolder.bind(position);
                isBind=false;
                break;
            case COMMON_RATE_CLASS:
                commonRateViewHolder.bind(position);
                break;
            case COMMON_RATE_REFUND_CLASS:
                commonRefundViewHolder.bind(position);
                break;
            case COMMON_DAY_COLLECT_CLASS:
                commonDayCollectViewHolder.bind(position);
                break;


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (this.flag) {
            case COMMON_RATE_CLASS:
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    commonRateViewHolder = new CommonRateViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            commonRatebean, context, is_top_rate, isSettlement);
                } else {
                    commonRateViewHolder = new CommonRateViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_list_last_item, parent, false),
                            commonRatebean, context, is_top_rate, isSettlement);
                }
                commonRateViewHolder.setIsRecyclable(false);
                return commonRateViewHolder;

            case COMMON_DAY_COLLECT_CLASS:
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    commonDayCollectViewHolder = new CommonDayCollectHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            commonDayCollcetBean, context);
                } else {
                    commonDayCollectViewHolder = new CommonDayCollectHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_settlment_last_item, parent, false),
                            commonDayCollcetBean, context);
                }
                commonDayCollectViewHolder.setIsRecyclable(false);
                return commonDayCollectViewHolder;
//
            case COMMON_RATE_REFUND_CLASS:
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    commonRefundViewHolder = new CommonRefundViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            commonRateRefundbean, context, is_top_rate, isSettlement);
                } else {
                    commonRefundViewHolder = new CommonRefundViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_list_last_item, parent, false),
                            commonRateRefundbean, context, is_top_rate, isSettlement);
                }
                commonRefundViewHolder.setIsRecyclable(false);
                return commonRefundViewHolder;

            case ORDERS_CLASS:
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    orderListViewHolder = new OrderListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            orders_bean, context);
                } else {
                    orderListViewHolder = new OrderListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_list_last_item, parent, false),
                            orders_bean, context);
                }
                orderListViewHolder.setIsRecyclable(false);
                return orderListViewHolder;
            case REFUND_CLASS:
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    refundListViewHolder = new RefundListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            refund_beanS, context, isPrintO, isFromMerHome);
                } else {
                    refundListViewHolder = new RefundListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_refund_last_item, parent, false),
                            refund_beanS, context, isPrintO, isFromMerHome);
                }
                refundListViewHolder .setIsRecyclable(false);
                return refundListViewHolder;
            case DETIAL_CLASS:
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    detialsListViewHolder = new DetialsListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            detialsBean, context, isPrintO, isFromMerHome);
                } else {
                    detialsListViewHolder = new DetialsListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_refund_last_item, parent, false),
                            detialsBean, context, isPrintO, isFromMerHome);
                }
                detialsListViewHolder .setIsRecyclable(false);
                return detialsListViewHolder;

            case UNREFUND_CLASS:
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    unRefundListViewHolder = new UnRefundListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            refund_bean, context,hasRefundRole);
                } else {
                    unRefundListViewHolder = new UnRefundListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_refund_last_item, parent, false),
                            refund_bean, context,hasRefundRole);
                }
                unRefundListViewHolder .setIsRecyclable(false);
                return unRefundListViewHolder;
            case SHOP_CLASS:
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    merchantListViewHolder = new MerchantListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            shop_bean, context);
                } else {
                    merchantListViewHolder = new MerchantListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shop_last_item, parent, false),
                            shop_bean, context);
                }
                merchantListViewHolder .setIsRecyclable(false);
                return merchantListViewHolder;

            case COMMON_CARD_CUS_CLASS:
//                KLog.i(viewType);
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    commonCardCusListViewHolder = new CommonCardCusListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            shop_bean, context);
                } else {
                    commonCardCusListViewHolder = new CommonCardCusListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_filter_item_last, parent, false),
                            shop_bean, context);
                }
                commonCardCusListViewHolder.setIsRecyclable(false);
                return commonCardCusListViewHolder;

            case COMMON_CARD_STAFF_CLASS:
//                KLog.i(viewType);
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    commonCardCusListViewHolder = new CommonCardCusListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            staff_search_bean, context);
                } else {
                    commonCardCusListViewHolder = new CommonCardCusListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_filter_item_last, parent, false),
                            staff_search_bean, context);
                }
                commonCardCusListViewHolder.setIsRecyclable(false);
                return commonCardCusListViewHolder;

            case COMMON_CARD_ACTIVE_CLASS:
//                KLog.i(viewType);
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    commonCardActiveListViewHolder = new CommonCardActiveAlListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            settlement_bean, context);
                } else {
                    commonCardActiveListViewHolder = new CommonCardActiveAlListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_user_allcoate_new_last_item, parent, false),
                            settlement_bean, context);
                }
                commonCardActiveListViewHolder.setIsRecyclable(false);
                return commonCardActiveListViewHolder;
            case STAFF_SEARCH_CLASS:
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    staffListViewHolder = new StaffListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            staff_search_bean, inner_flag, context);
                } else {
                    staffListViewHolder = new StaffListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_staff_last_item, parent, false),
                            staff_search_bean, inner_flag, context);
                    KLog.i("last");
                }
                staffListViewHolder .setIsRecyclable(false);
                return staffListViewHolder;
            case SETTLEMENT_CLASS:
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    settlementListViewHolder = new SettlementListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            staff_id, settlement_bean, context);
                } else {
                    settlementListViewHolder = new SettlementListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_settlment_last_item, parent, false),
                            staff_id, settlement_bean, context);
                }
                settlementListViewHolder .setIsRecyclable(false);
                return settlementListViewHolder;

            case STAFF_COLLECT:
//                Log.i("adaSta","ssss");
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    staffCollectViewHolder = new StaffCollectViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            staff_Collect_Bean, context);
                } else {
                    staffCollectViewHolder = new StaffCollectViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_settlment_last_item, parent, false),
                            staff_Collect_Bean, context);
                }
                staffCollectViewHolder.setIsRecyclable(false);
                return staffCollectViewHolder;
//
            case ALLOCATE_CLASS:
                if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                    allocateViewHolder = new AllocateViewHolder(this, (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                            allcoateBean, context, mItemClickListener);
                } else {
                    allocateViewHolder = new AllocateViewHolder(this, (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_allocate_item, parent, false),
                            allcoateBean, context, mItemClickListener);
                }
                return allocateViewHolder;
        }
        if (f == 1) {
//            Log.i("adaSta","ssss");
            if (viewType == ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal()) {
                staffCollectViewHolder = new StaffCollectViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false),
                        staff_Collect_Bean, context);
            } else {
                staffCollectViewHolder = new StaffCollectViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_settlment_last_item, parent, false),
                        staff_Collect_Bean, context);
            }
            staffCollectViewHolder .setIsRecyclable(false);
            return staffCollectViewHolder;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        switch (this.flag) {
            case ORDERS_CLASS:
                if (position > count && position == orders_bean.getModel().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }
            case REFUND_CLASS:
                if (position > count && position == refund_beanS.getModel().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }
            case DETIAL_CLASS:
//                KLog.i(detialsBean.getModel().size());
                if (position > count && position == detialsBean.getModel().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }
            case UNREFUND_CLASS:
                if (position > count && position == refund_bean.getModel().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }
            case SHOP_CLASS:
                if (position > count && position == shop_bean.getModel().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }
            case COMMON_CARD_CUS_CLASS:
//                KLog.i(position+">"+count+"&&"+position +"==" +(shop_bean.getModel().size() - 1));
//                KLog.i((position > count) +"&&"+(position == shop_bean.getModel().size() - 1));
                if (position > count && position == shop_bean.getModel().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }
            case COMMON_CARD_STAFF_CLASS:

                if (position > count && position == staff_search_bean.getModel().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }

            case COMMON_CARD_ACTIVE_CLASS:

                if (position > count && position == settlement_bean.getModel().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }
            case STAFF_SEARCH_CLASS:
                if (position > count && position == staff_search_bean.getModel().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }
            case SETTLEMENT_CLASS:
                if (position > count && position == settlement_bean.getModel().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }

            case ALLOCATE_CLASS:
                if (position > count && position == allcoateBean.getList_cno().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }
//
            case COMMON_RATE_CLASS:
                if (position > count && position == commonRatebean.getModel().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }
//
            case COMMON_RATE_REFUND_CLASS:
                if (position > count && position == commonRateRefundbean.getModel().size() - 1) {
                    return ITEM_TYPE.ITEM_TYPE_LAST.ordinal();
                } else {
                    return ITEM_TYPE.ITEM_TYPE_NORMAL.ordinal();
                }
            case COMMON_DAY_COLLECT_CLASS:
                if (position > count && position == commonDayCollcetBean.getModel().size() - 1) {
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
//        KLog.i("itmc");
        switch (this.flag) {
            case ORDERS_CLASS:
                return orders_bean.getModel().size();
            case REFUND_CLASS:
                return refund_beanS.getModel().size();
            case DETIAL_CLASS:
//                KLog.i(detialsBean.getModel().size());
                return detialsBean.getModel().size();
            case UNREFUND_CLASS:
                return refund_bean.getModel().size();
            case SHOP_CLASS:
//                KLog.i(shop_bean.getSys_no().size()+"----------");
                return shop_bean.getModel().size();
            case COMMON_CARD_CUS_CLASS:
//                KLog.i(shop_bean.getSys_no().size()+"----------");
                return shop_bean.getModel().size();
            case COMMON_CARD_STAFF_CLASS:
                return staff_search_bean.getModel().size();
            case COMMON_CARD_ACTIVE_CLASS:
                return settlement_bean.getModel().size();
            case STAFF_SEARCH_CLASS:
                return staff_search_bean.getModel().size();
            case SETTLEMENT_CLASS:
                return settlement_bean.getModel().size();
            case ALLOCATE_CLASS:
                if (allcoateBean.getList_cno() != null) {
                    return allcoateBean.getList_cno().size();
                } else {
                    return 0;
                }
            case STAFF_COLLECT:
                return staff_Collect_Bean.getModel().size();
            case COMMON_RATE_CLASS:
                return commonRatebean.getModel().size();
            case COMMON_RATE_REFUND_CLASS:
                return commonRateRefundbean.getModel().size();
            case COMMON_DAY_COLLECT_CLASS:
                return commonDayCollcetBean.getModel().size();
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
