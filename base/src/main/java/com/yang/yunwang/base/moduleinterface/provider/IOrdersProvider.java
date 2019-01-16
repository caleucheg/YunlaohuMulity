package com.yang.yunwang.base.moduleinterface.provider;

/**
 *  on 2017/4/16.
 *
 * description：
 * update by:
 * update day:
 */
public interface IOrdersProvider extends IBaseProvider {
    //服务
    String ORDRES_MAIN_SERVICE = "/orders/main/service";
    //作为Fragment被添加时候的key
    String MODULE1_KEY_FRAGMENT = "module1_key_fragment";
    String ORDERS_ACT_ORDER_SEARCH = "/orders/act/ordersearch";
    String ORDERS_ACT_ORDER_LIST = "/orders/act/orderlist";
    String ORDERS_ACT_ORDER_SETTLE= "/orders/act/ordersettle";
    String ORDERS_ACT_ORDER_LIST_INFO = "/orders/act/orderinfo";
    String ORDERS_ACT_NEW_ORDER_LIST_INFO = "/orders/act/new/orderinfo";
    String ORDERS_ACT_WX_PLANT = "/orders/act/wx/plant";
    String ORDERS_ACT_ZFB_PLANT_SEARCH = "/orders/act/zfb/plant";
    String ORDERS_ACT_ORDER_SETTLE_INFO = "/orders/act/ordersettle/info";
    String ORDERS_ACT_UNREFUND_LIST = "/orders/act/unrefund/list";
    String ORDERS_ACT_UNREFUND_SEARCH = "/orders/act/unrefund/search";
    String ORDERS_ACT_UNREFUND_INFO = "/orders/act/unrefund/info";
    String ORDERS_ACT_REFUND_LIST = "/orders/act/refund/list";
    String ORDERS_ACT_REFUND_SEARCH = "/orders/act/refund/search";
    String ORDERS_ACT_REFUND_INFO = "/orders/act/refund/info";
    String ORDERS_ACT_PERSONAL_QR = "/orders/act/personal/qrcode";
    String ORDERS_ACT_ORDER_DETIALS_LIST = "/orders/act/detials/list";
    String ORDERS_ACT_ORDER_DETIALS_SEARCH = "/orders/act/detials/search";
    String ORDERS_ACT_ORDER_DETIALS_INFO = "/orders/act/detials/info";
    String ORDERS_ACT_COMMON_LIST = "/orders/act/common/list";
    String ORDERS_ACT_COMMON_SEARCH = "/orders/act/common/search";
    String ORDERS_ACT_COMMON_INFO = "/orders/act/common/info";
    String ORDERS_ACT_DAY_COMMON_LIST = "/orders/act/day/common/list";
    String ORDERS_ACT_DAY_COMMON_INFO = "/orders/act/day/common/info";
    String ORDERS_ACT_COMMON_REFUND_LIST = "/orders/act/common/refund/list";
    String ORDERS_ACT_COMMON_REFUND_INFO = "/orders/act/common/refund/info";
    String ORDERS_ACT_COMMON_REFUND_SEARCH = "/orders/act/common/refund/search";
    String ORDERS_ACT_STAFF_LIST = "/orders/act/staff/list";
    String ORDERS_ACT_STAFF_SEARCH = "/orders/act/staff/search";
    String ORDERS_ACT_STAFF_INFO = "/orders/act/staff/info";
    String ORDERS_ACT_MERCH_SEARCH = "/orders/act/merch/search";
    String ORDERS_ACT_MERCH_LIST = "/orders/act/merch/list";
    String ORDERS_ACT_MERCH_INFO = "/orders/act/merch/info";
    String ORDERS_ACT_REGIST_PAGE = "/orders/act/regist/page";
    String ORDERS_ACT_ALLCOATE_LIST = "/orders/act/allcoate/list";
    String ORDERS_ACT_ALLCOATE_PAGE = "/orders/act/allcoate/page";
    String ORDERS_ACT_SSTAFF_COLLECT_LIST = "/orders/act/sstaff/collect/list";
    String ORDERS_ACT_SSTAFF_COLLECT_SEARCH = "/orders/act/sstaff/collect/search";
    String ORDERS_ACT_SSTAFF_COLLECT_INFO = "/orders/act/sstaff/collect/info";
    String ORDERS_ACT_COMMON_CARD_CUS_LIST = "/orders/act/common/card/cus/list";
    String ORDERS_ACT_COMMON_CARD_STAFF_LIST = "/orders/act/common/card/staff/list";
    String ORDERS_ACT_COMMON_CARD_ACTICVE_LIST = "/orders/act/common/card/active/list";
}
