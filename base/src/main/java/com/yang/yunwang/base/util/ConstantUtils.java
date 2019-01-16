package com.yang.yunwang.base.util;


import com.yang.yunwang.base.dao.PassageWay;

import java.util.ArrayList;

public class ConstantUtils {
    //百度appId
    public static final String BAIDU_APP_ID = "9429247";
    public static final String BAIDU_API_KEY = "Ns6H6NMdZcYxfwFRDrVGRVpTTHgxvG8x";
    public static final String BAIDU_SECRET_KEY = "TU4omOGkz4hDQgmcbnr3coNdhcs8q9ou";
    public static final String D_BAIDU_APP_ID = "10326402";
    public static final String D_BAIDU_API_KEY = "g5uRj7jCo1IHAH3YpenYBXZG";
    public static final String D_BAIDU_SECRET_KEY = "28e6d820eccc4ccac331d550d3d1c838";
    public static final ArrayList<PassageWay> PASSAGWE_WAYS = new ArrayList<>();
    //                public static final String BASE = "http://pos.yunlaohu.cn/";
//    public static final String BASE_TEST = "http://pos.yunlaohu.cn/";
    //    public static final String BASE_TEST = "http://androidapi.yunlaohu.cn/";
    //   public static final String BASE_R = "http://androidapi.yunlaohu.cn/";
    public static final String TAG_MERCHANT_LOGIN = "login_merchant";
    public static final String TAG_STAFF_LOGIN = "login_staff";
    public static final String TAG_STAFF_RETRIEVE_SUPERIOR = "staff_retrieve_superior";
    public static final String TAG_ORDERLIST_SHOPUSERSP = "orderlist_shopusersp";
    public static final String TAG_ORDERLIST_USERSP = "orderlist_usersp";
    public static final String TAG_ORDERLIST_SP = "orderlist_sp";
    public static final String TAG_ORDERLIST_SHOPSP = "orderlist_shopsp";
    public static final String TAG_WX_ORDERLIST_QUERY = "wx_orderlist_query";
    public static final String TAG_ZFB_ORDERLIST_QUERY = "zfb_orderlist_query";
    public static final String TAG_REFUNDSEARCH = "refund_orders";
    public static final String TAG_UNREFUNDSEARCH = "unrefund_orders";
    public static final String TAG_POSREFUNDINSERT = "posrefundinsert";
    public static final String TAG_ALIPAYREFUND = "alipayrefund";
    public static final String TAG_WXQRCREATE = "wxqrcreate";
    public static final String TAG_ZFBQRCREATE = "zfbqrcreate";
    public static final String PRINT_F_STAFF = "FROM_STAFF";
    public static final String TAG_SHOPLIST = "shoplist";
    public static final String TAG_STAFFLIST = "stafflist";
    public static final String TAG_WXSCANCODE = "wxscancode";
    public static final String TAG_ZFBSCANCODE = "zfbscancode";
    //    public static final String URL_SETTLEMENT_TEST = BASE_TEST + "IPP3Order/IPP3OrderListcollect";
//    public static final String URL_SHOP_REGIST_TEST = BASE_TEST + "IPP3Customers/IPP3CustomerShopInsert";
//    public static final String URL_UPDATE_SHOP_INFO_TEST = BASE_TEST + "IPP3Customers/IPP3CustomerUpd";
    public static final String FWS_YUANGONG = "is_fws_yuangong";
    //员工微信配置查询
//    public static final String URL_CUSTOMER_WEIXIN_CONFIG_TEST = BASE_TEST + "IPP3Customers/IPP3WxconfigBySUsysNo";
//    public static final String URL_GET_WEIXIN_ORDER_XINGYE_TEST = BASE_TEST + "IPP3OrderSysNo/SPGetWXOrder";
//    public static final String URL_GET_PER_PAYID_XINGYE_TEST = BASE_TEST + "IPP3OrderSysNo/SPGetWXOrder";
//    public static final String URL_GET_MARCH_CLASS_TEST = BASE_TEST + "IPP3Customers/SystemClassList";
//    public static final String URL_GET_RIGIST_CNO_TEST = BASE_TEST + "IPP3Customers/IPP3CustomerList";
//    public static final String URL_INSERT_SH_RATE_TEST = BASE_TEST + "IPP3Customers/CustomerServiceRateADD";
//    public static final String URL_QUERY_SH_RATE_TEST = BASE_TEST + "IPP3Customers/CustomerServiceRateList";
    public static final String ACTIVEBTN = "activeBtn";
    public static final String fromHome = "from_home";
    public static final String fromMerch = "from_merch";
    public static final String fromMerchHome = "from_home_merch";
    public static final String merchStaff = "merch_staff";
    public static final String fromHomeDis = "from_home_dis";
    public static final String orderExtend_order_extend = "/OrderExtend/order_extend";//  "大学-交易订单查询"
    public static final String permission_permission_business = "/Permission/permission_business";//"商户权限分配",
    public static final String summary_summary_search = "/Summary/summary_search";// "服务商员工汇总",
    public static final String orderFund_orderfund_Top_1 = "/OrderFund/orderfund?Top=1";// "上级费率订单查询",
    public static final String orderFund_orderfund = "/OrderFund/orderfund";//   "费率订单查询",
    public static final String wxpay_native = "/Wxpay/native";// "支付二维码",
    public static final String qrcode_index = "/Qrcode/index";//"员工二维码",
    public static final String staff_staff_list = "/Staff/staff_list";// "员工列表",
    public static final String staff_staff_register = "/Staff/staff_register";//  "员工注册",
    public static final String permission_permission_assignment = "/Permission/permission_assignment";// "员工权限分配",
    public static final String business_business = "/Business/business";//  "商户查询",
    public static final String pay_scan_code_payment_Alipay = "/Pay/scan_code_payment_Alipay";//  "支付宝扫码支付",
    public static final String pay_scan_code_payment = "/Pay/scan_code_payment";// "微信扫码支付",
    public static final String refund_refund = "/Refund/refund";//  "退款",
    public static final String refundSearch_refund_search = "/RefundSearch/refund_search";// "退款查询",
    public static final String order_order_search_alipay = "/Order/order_search_alipay";//"支付宝订单平台查询",
    public static final String order_platform_order_search = "/Order/platform_order_search";// "微信订单平台查询",
    public static final String order_order_search = "/Order/order_search";//  "交易订单查询",
    public static final String conff_zfbConfig = "/Conff/zfbConfig";//  "支付宝-服务商配置",
    public static final String conff_wxConfig = "/Conff/wxConfig";// "微信-服务商配置",
    //    public static final String XY_PAY_WAY = "104";
//    public static final String PF_PAY_WAY = "106";
//    public static final String XY_PAY_WAY_Z = "105";
//    public static final String PF_PAY_WAY_Z = "107";
    public static final String BANK_PAYWAY = "BANK_PAY_WAY";
    public static final String YIHUI_PAYWAY = "YI_HUI_WAY";
    //员工二维码生成
    //核销
    public static final String URL_CHECK_QR = "http://web.yunlaohu.cn/index.php/GetQrcode/getQrcode";
    //删除
    public static final String URL_DELETE_QR = "http://web.yunlaohu.cn/index.php/GetQrcode/getQrcode";
    //智能
    public static final String URL_SMART_PAY_QR = "http://web.yunlaohu.cn/index.php/Intel/index?systemUserSysNo=";

    //核销
    public static final String URL_D_CHECK_QR = "http://web.duoyoucai.com.cn/index.php/GetQrcode/getQrcode";
    //删除
    public static final String URL_D_DELETE_QR = "http://web.duoyoucai.com.cn/index.php/GetQrcode/getQrcode";
    //智能
    public static final String URL_D_SMART_PAY_QR = "http://web.duoyoucai.com.cn/index.php/Intel/index?systemUserSysNo=";
    public static final String CLICKABLE = "CLICKABLE";
    public static final String FROM_HOME = "fHome";
    public static final String BASE_WEB = "http://web.yunlaohu.cn/index.php/";
    public static final String BASE_WEB_D = "http://web.duoyoucai.com.cn/index.php/";
    public static boolean IS_TAG_SET = false;
    public static String GETED_ZFB_TYPE = "";
    public static String GETED_WX_TYPE = "";
    public static String PAY_WAY = "";
    public static boolean isGetPayType = false;
    public static String PayType;
    public static boolean INIT_ALLOCATE = false;
    public static String SYS_NO = "";//角色主键
    public static String HIGHER_SYS_NO = "";//角色上级主键
    public static String CUSTOMER = "";//角色名称
    public static String CUSTOMERS_TYPE = "";//角色类型,区分员工和服务商，商户角色
    /**
     * 查询入口标识
     */
    public static String CUSTOMERS_TYPE_FROM = "";
    public static String STAFF_TYPE = "";//员工类型
    public static String NEW_TYPE = "";//员工类型
    public static String USER = "";//角色登录名
    public static String HIGHER_NAME = "";//员工类型
    public static boolean IS_ATFER_LOGIN_INIT = false;//是否初始化商户/服务商首界面汇总数据
    public static String[] ALLOCATES = {
            "/OrderExtend/order_extend", "/Permission/permission_business", "/Summary/summary_search", "/OrderFund/orderfund?Top=1",
            "/OrderFund/orderfund", "/Wxpay/native", "/Qrcode/index", "/Staff/staff_list", "/Staff/staff_register",
            "/Permission/permission_assignment", "/Business/business", "/Pay/scan_code_payment_Alipay", "/Pay/scan_code_payment",
            "/Refund/refund", "/RefundSearch/refund_search", "/Order/order_search_alipay", "/Order/platform_order_search",
            "/Order/order_search", "/Conff/zfbConfig", "/Conff/wxConfig"};
    public static boolean OrderExtend_order_extend = false;
    public static boolean Permission_permission_business = false;
    public static boolean Summary_summary_search = false;
    public static boolean OrderFund_orderfund_Top_1 = false;
    public static boolean OrderFund_orderfund = false;
    public static boolean Wxpay_native = false;
    public static boolean Qrcode_index = false;
    public static boolean Staff_staff_list = false;
    public static boolean Staff_staff_register = false;
    public static boolean Permission_permission_assignment = false;
    public static boolean Business_business = false;
    public static boolean Pay_scan_code_payment_Alipay = false;
    public static boolean Pay_scan_code_payment = false;
    public static boolean Refund_refund = false;
    public static boolean RefundSearch_refund_search = false;
    public static boolean Order_order_search_alipay = false;
    public static boolean Order_platform_order_search = false;
    public static boolean Order_order_search = false;
    public static boolean Conff_zfbConfig = false;
    public static boolean Conff_wxConfig = false;
    public static String isShowBTN = "isShowBTn";
    public static String order_statistics = "/OrderStatistics/order_statistics";
    public static boolean Order_statistics = false;
    public static boolean IPP3OrderByDayList = false;
    public static String DAY_COLLECT = "DAY_COLLECT";
    public static String IPP3OrderByDayListS = "/Order/orderbyday";
    public static boolean IPP3OrderFundList_Shop_SP = false;
    public static String IPP3OrderFundList_Shop_SP_S = "/BusinessRefund/refund";
    public static ArrayList<String> ZFB_Passageway = new ArrayList<>();
    public static ArrayList<String> ZFB_Type = new ArrayList<>();
    public static ArrayList<String> ZFB_TypeName = new ArrayList<>();
    public static ArrayList<String> ZFB_PassageWayName = new ArrayList<>();
    public static ArrayList<String> WX_Passageway = new ArrayList<>();
    public static ArrayList<String> WX_Type = new ArrayList<>();
    public static ArrayList<String> WX_TypeName = new ArrayList<>();
    public static ArrayList<String> WX_PassageWayName = new ArrayList<>();
    public static boolean isGetPassageMark = false;
    public static boolean isGetReID = false;
    //        private static String BASE = "https://pos.yunlaohu.cn/";
    public static String BASE = "http://css.yunlaohu.cn/";
//    public static String BASE = "http://suibian.yunlaohu.cn/";
//    public static String BASE = "https://androidapi.yunlaohu.cn/";
    //    private static String BASE_TEST_S = "http://suibian.yunlaohu.cn/";
    //支付类型码表
    public static final String URL_PASSAGE_WAY_LIST = BASE + "IPP3Customers/SystemPassageWayList";
    //  服务商/商户角色登录
//    正式环境  http://payapi.yunlaohu.cn/  测试环境 http://androidapi.yunlaohu.cn/
    public static final String URL_MERCHANT_LOGIN = BASE + "IPP3Customers/IPP3CustomerLogin";
    //商户支付通道新增
    public static final String URL_INSERT_SH_PASSAGE_WAY = BASE + "IPP3Customers/CustomerServicePassageWayInsert";
    //商户支付通道查询
    public static final String URL_SEARCH_SH_PASSAGE_WAY = BASE + "IPP3Customers/CustomerServicePassageWayList";
    //员工获取支付通道
    public static final String URL_GET_CUSTOMER_PASSAGE_WAY = BASE + "IPP3Customers/GetPayConfig";
    //  员工角色登录
    public static final String URL_STAFF_LOGIN = BASE + "IPP3Customers/IPP3Login";
    //  根据员工主键查询上级信息
    public static final String URL_STAFF_RETRIEVE_SUPERIOR = BASE + "IPP3Customers/IPP3GetCustomerServiceSysNo";
    //  根据员工主键查询上级信息--新
    public static final String URL_STAFF_RETRIEVE_SUPERIOR_NEW = BASE + "IPP3Customers/IPP3GetCustomerServiceSysNoList";
    //  订单商户员工查询
    public static final String URL_ORDERLIST_SHOPUSERSP = BASE + "IPP3Order/IPP3OrderListShopUserSP";
    //    public static String EMAIL = "";//角色邮件地址
//    public static String TEL = "";//角色电话
//    public static String PASSWORD = "";//角色登录密码
//    public static String STOREID = "";//角色门店ID
    //  订单服务商员工查询
    public static final String URL_ORDERLIST_USERSP = BASE + "IPP3Order/IPP3OrderListCustomerUserSP";
    //  订单服务商查询
    public static final String URL_ORDERLIST_SP = BASE + "IPP3Order/IPP3OrderListCustomerSP";
    //  订单商户查询
    public static final String URL_ORDERLIST_SHOPSP = BASE + "IPP3Order/IPP3OrderListShopSP";
    //    微信订单查询
    public static final String URL_WX_ORDERLIST_QUERY = BASE + "Payment/Payments/QueryWxOrder";
    //    支付宝订单
    public static final String URL_ZFB_ORDERLIST_QUERY = BASE + "IPP3AliPay/AliPayquery";
    //    退款查询
//    public static final String URL_REFUNDSEARCH = BASE + "POS/POSRefundList";
    //    退款
    public static final String URL_UNREFUNDSEARCH = BASE + "IPP3Order/IPP3OrderFundListSP";
    //  微信退款
    public static final String URL_POSREFUNDINSERT = BASE + "POS/POSRefundInsert";
    //  支付宝退款
    public static final String URL_ALIPAYREFUND = BASE + "IPP3AliPay/AliPayRefundUnion";
    //  微信生成二维码接口
    public static final String URL_WXQRCREATE = BASE + "Payment/Payments/GetPayUrl";
    //  支付宝生成二维码接口
    public static final String URL_ZFBQRCREATE = BASE + "IPP3AliPay/GetAliPayUrl";
    //  商户查询
    public static final String URL_SHOPLIST = BASE + "IPP3Customers/IPP3CustomerShopList";
    //  人员查询
    public static final String URL_STAFFLIST = BASE + "IPP3Customers/IPP3SystemUserListCSsysno";
    //  微信扫码支付
    public static final String URL_WXSCANCODE = BASE + "POS/POSOrderInsert";
    //  支付宝扫码支付
    public static final String URL_ZFBSCANCODE = BASE + "IPP3AliPay/BarcodePayUnion";
    //  汇总
    public static final String URL_SETTLEMENT = BASE + "IPP3Order/IPP3OrderListcollect";
    //  员工修改密码
    public static final String URL_STAFF_PASSWORD = BASE + "IPP3Customers/IPP3SystemUserUpdatePwd";
    //  服务商商户修改密码
    public static final String URL_MERCHANT_PASSWORD = BASE + "IPP3Customers/IPP3CustomerUpdPwd";
    //  员工修改信息
    public static final String URL_STAFF_INFO_CHANGE = BASE + "IPP3Customers/IPP3SystemUserUpdate";
    //  员工内部查询
    public static final String URL_STAFF_INNER_SEARCH = BASE + "IPP3Customers/IPP3SystemUserList";
    //  微信订单关闭
    public static final String URL_WX_ORDER_CLOSE = BASE + "Payment/Payments/WXCloseOrder";
    //  支付宝订单取消
    public static final String URL_ZFB_ORDER_CANCEL = BASE + "IPP3AliPay/AliPayCancel";
    //支付宝订单关闭
    public static final String URL_ZFB_ORDER_CLOSE = BASE + "IPP3AliPay/AliPayClose";
    //员工汇总
    public static final String URL_STAFF_COLLECT = BASE + "IPP3Order/IPP3Order_Group_CustomerUserList";
    //服务商员工列表
    public static final String URL_ALLOCATE_ALL_COLLECT = BASE + "IPP3Customers/IPP3SystemUserListCSsysno";
    //调拨
    public static final String URL_ALLOCATE = BASE + "IPP3Customers/IPP3CustomerUserUpdate";
    //服务商上级员工查询
    public static final String URL_ALLOCATE_UP_COLLECT = BASE + "IPP3Customers/IPP3SystemUserByCSsysNo";
    //商户费率查询
    public static final String URL_SHOP_RATE_COLLECT = BASE + "IPP3Order/IPP3Order_Fund_ShopSPRate";
    //商户员工费率查询
    public static final String URL_SHOP_USER_RATE_COLLECT = BASE + "IPP3Order/IPP3Order_Fund_ShopUserRateSP";
    //服务商员工费率查询
    public static final String URL_CUSTOMER_USER_RATE_COLLECT = BASE + "IPP3Order/IPP3OrderListCustomerUserRateSP";
    //服务商员工费率查询汇总
    public static final String URL_CUSTOMER_USER_RATE_COLLECT_SETTLEMENT = BASE + "IPP3Order/IPP3Order_Fund_CustomerUserRateSP";
    //服务商员工费率汇总查询
    public static final String URL_CUSTOMER_USER_TOP_RATE_SETTLEMENT = BASE + "IPP3Order/IPP3Order_Fund_CustomerUserRateSP";
    //服务商员工下 商户上级费率查询
    public static final String URL_CUSTOMER_USER_SHOP_TOP_RATE_COLLECT = BASE + "IPP3Order/IPP3OrderListCustomerUserRateSP";
    //服务商员工下 商户上级费率查 汇总
    public static final String URL_CUSTOMER_USER_SHOP_TOP_RATE_COLLECT_SETTLEMENT = BASE + "IPP3Order/IPP3Order_Fund_CustomerUserRateSP";
    //商户员工费率订单
    public static final String URL_MERCHANT_USER_RATE_COLLECT = BASE + "IPP3Order/IPP3OrderListShopUserRateSP";
    //商户员工费率订单汇总
    public static final String URL_MERCHANT_USER_RATE_COLLECT_SETTLEMENT = BASE + "IPP3Order/IPP3Order_Fund_ShopUserRateSP";
    //商户费率订单
    public static final String URL_MERCHANT_RATE_COLLECT = BASE + "IPP3Order/IPP3OrderListShopSPRate";
    //商户费率订单汇总
    public static final String URL_MERCHANT_RATE_COLLECT_SETTLEMENT = BASE + "IPP3Order/IPP3Order_Fund_ShopSPRate";
    //获取省市区
    public static final String URL_GET_PROVINCE = BASE + "IPP3Customers/IPP3GetAddress";
    //商户注册
    public static final String URL_SHOP_REGIST = BASE + "IPP3Customers/IPP3CustomerShopInsert";
    //商户权限查询
    public static final String URL_SHOP_ROLE_LIST = BASE + "IPP3Customers/IPP3CustomerRoleList";
    //权限查询
    public static final String URL_ROLE_LIST = BASE + "IPP3Customers/IPP3SystemRoleList";
    //员工权限查询
    public static final String URL_STAFF_ROLE_LIST = BASE + "IPP3Customers/IPP3UserRoleList";
    //商户权限批量删除
    public static final String URL_SHOP_ROLE_DELETE = BASE + "IPP3Customers/IPP3CustomerRoleDelete";
    //商户权限批量增加
    public static final String URL_SHOP_ROLE_INSERT = BASE + "IPP3Customers/IPP3CustomerRoleInsert";
    //员工权限批量删除
    public static final String URL_USER_ROLE_DELETE = BASE + "IPP3Customers/IPP3UserRoleDelete";
    //员工权限批量增加
    public static final String URL_USER_ROLE_INSERT = BASE + "IPP3Customers/IPP3UserRoleInsert";
    //修改上级费率
    public static final String URL_UPDATE_RATE = BASE + "IPP3Customers/IPP3CustomerUserRateUpdate";
    //修改商户信息
    public static final String URL_UPDATE_SHOP_INFO = BASE + "IPP3Customers/IPP3CustomerUpd";
    //修改员工信息
    public static final String URL_UPDATE_USER_INFO = BASE + "IPP3Customers/IPP3SystemUserUpdate";
    //服务商商户退款查询
    public static final String URL_REFUND_MERCH_SEARCH = BASE + "IPP3Order/IPP3RMA_RequestSP";
    //员工微信配置查询
    public static final String URL_CUSTOMER_WEIXIN_CONFIG = BASE + "IPP3Customers/IPP3WxconfigBySUsysNo";
    //推送消息地址
    public static final String URL_PUSH_MESSAGE = BASE + "Payment/Payments/SendTemplateMessage";
    //微信扫码订单号生成
    public static final String URL_GET_WX_ORDER = BASE + "IPP3OrderSysNo/GetWXOrder";
    //支付宝扫码订单号生成
    public static final String URL_GET_ZFB_ORDER = BASE + "IPP3OrderSysNo/GetAliOrder";
    //插入订单日志
    public static final String URL_INSERT_ORDER_LOG = BASE + "IPP3Order/IPP3So_MasterLog";
    //兴业   获取微信订单号
    public static final String URL_GET_WEIXIN_ORDER_XINGYE = BASE + "IPP3OrderSysNo/SPGetWXOrder";
    //兴业 获取perPayId
    public static final String URL_GET_PER_PAYID_XINGYE = BASE + "IPP3OrderSysNo/SPGetWXOrder";
    //获取商户类目
    public static final String URL_GET_MARCH_CLASS = BASE + "IPP3Customers/SystemClassList";
    //注册后查询
    public static final String URL_GET_RIGIST_CNO = BASE + "IPP3Customers/IPP3CustomerList";
    //首页商户/服务商汇总信息
    public static final String URL_CUSTOMER_ORDER_T = BASE + "IPP3Order/IPP3Order_Group_Customer";
    public static final String URL_SHOP_ORDER_T = BASE + "IPP3Order/IPP3Order_Group_Shop";
    //银行通道扫码支付
    public static final String URL_BANK_SCAN_PAY = BASE + "IPP3Swiftpass/BarcodePayApiUnion";
    //银行通道微信二维码支付
    public static final String URL_BANK_WX_QR_PAY = BASE + "IPP3Swiftpass/GetWxQRCodeUrl";
    //银行通道支付宝二维码支付
    public static final String URL_BANK_ZFB_QR_PAY = BASE + "IPP3Swiftpass/GetAliQRCodeUrl";
    //银行通道退款
    public static final String URL_BANK_REFUND_PAY = BASE + "IPP3Swiftpass/RefundApiUnion";
    //银行通道微信订单号生成
    public static final String URL_BANK_WX_GET_ORDER = BASE + "IPP3OrderSysNo/SPGetWXOrder";
    //银行通道支付宝订单号生成
    public static final String URL_BANK_ZFB_GET_ORDER = BASE + "IPP3OrderSysNo/SPGetAliOrder";
    //银行通道订单号查询
    public static final String URL_BANK_QUERY_ORDER = BASE + "IPP3Swiftpass/OrderQueryApi";
    //银行通道订单号关闭
    public static final String URL_BANK_CLOSE_ORDER = BASE + "IPP3Swiftpass/ReverseApi";
    //网商银行
    //网商银行刷卡支付
    public static final String URL_NETBANK_SCAN_PAY = BASE + "IPP3WSOrder/WSBarcodePayUnion";
    //网商银行二维码支付
    public static final String URL_NETBANK_QR_ORDER_PAY = BASE + "IPP3WSOrder/WSJsPayCreateQrCode";
    //网商银行订单关闭
    public static final String URL_NETBANK_CLOSE_ORDER = BASE + "IPP3WSOrder/WSPayClose";
    //网商银行退款
    public static final String URL_NETBANK_REFUND_ORDER = BASE + "IPP3WSOrder/WSPayRefundUnion";
    //网商银行退款查询
    public static final String URL_NETBANK_REFUND_QUERY_ORDER = BASE + "IPP3WSOrder/WSPayRefundQueryUnion";
    //网商银行订单查询
    public static final String URL_NETBANK_QUERY_ORDER = BASE + "IPP3WSOrder/WSPayQuery";
    //JpushId新增
    public static final String URL_JPUSH_ID_INSERT = BASE + "IPP3Customers/WSJPushIDInsert";
    //订单关闭日志插入
    public static final String URL_CLOSE_ORDER_LOG_INSERT = BASE + "IPP3Customers/PayQRCodeLogInsert";
    //商户所有订单查询(退款,非退款)
    public static final String URL_REFUND_ORDER_PRINT = BASE + "IPP3Order/IPP3OrderFundList_Shop_SP";
    //员工所有订单查询
    public static final String URL_REFUND_ORDER_PRINT_STAFF = BASE + "IPP3Order/IPP3OrderFundListSP";
    //商户注册增加商户费率
    public static final String URL_INSERT_SH_RATE = BASE + "IPP3Customers/CustomerServiceRateADD";
    //商户查询个人信息商户费率
    public static final String URL_QUERY_SH_RATE = BASE + "IPP3Customers/CustomerServiceRateList";
    //雪花算法订单号生成
    public static final String URL_SNOW_FLAKE_ORDER_CREAT = BASE + "IPP3OrderSysNo/IPP3Snowflake";
    //商户日汇总
    public static final String URL_MERCH_DAY_COLLECT = BASE + "IPP3Order/IPP3OrderByDayList";
    //商户注册权限默认新增
    public static final String URL_INSERT_MERCH_DEFULT_ALLCOATE = BASE + "IPP3Customers/IPP3CustomerAndSystemDefaultRoles";
    //    private static String BASE_pos="http://pos.yunlaohu.cn/";
    //获取当前版本号
    public static final String URL_VERSION_CODE = BASE + "IPP3Customers/SystemVersionList";
    public static final String URL_VERSION_CODE_INSERT = BASE + "IPP3Customers/SystemVersionUpd";
    //获取员工隔天退款权限
    public static final String URL_GET_STAFF_REFUND_ROLE = BASE + "IPP3Customers/IPP3RoleApplicationList";
    //    private static String BASE_test= "http://suibian.yunlaohu.cn/";
    //获取Transaction_id
    public static final String URL_GET_Transaction_id = BASE + "IPP3Order/So_MasterQuery";
    public static String allocate="allocate";
    public static String from_home="from_home";
    public static String CustomersTopSysNo="CustomersTopSysNo";
    public static final CharSequence KOUBEI_ZFB_TAG = "111";

    public static void initAllocate() {
        OrderExtend_order_extend = false;
        Permission_permission_business = false;
        Summary_summary_search = false;
        OrderFund_orderfund_Top_1 = false;
        OrderFund_orderfund = false;
        Wxpay_native = false;
        Qrcode_index = false;
        Staff_staff_list = false;
        Staff_staff_register = false;
        Permission_permission_assignment = false;
        Business_business = false;
        Pay_scan_code_payment_Alipay = false;
        Pay_scan_code_payment = false;
        Refund_refund = false;
        RefundSearch_refund_search = false;
        Order_order_search_alipay = false;
        Order_platform_order_search = false;
        Order_order_search = false;
        Conff_zfbConfig = false;
        Conff_wxConfig = false;
        Order_statistics = false;
        IPP3OrderByDayList = false;
        IPP3OrderFundList_Shop_SP = false;
    }
}
