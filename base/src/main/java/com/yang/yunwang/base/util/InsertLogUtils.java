package com.yang.yunwang.base.util;

import android.content.Context;

import com.socks.library.KLog;
import com.yang.yunwang.base.basereq.BaseInfoStringService;
import com.yang.yunwang.base.dao.PayLogBean;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/13.
 */
public class InsertLogUtils {
    public static void reinsertLog(PayLogBean bean, final Context context) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("SystemUserSysNo", ConstantUtils.SYS_NO);
        map.put("CustomerServiceSysNo", ConstantUtils.HIGHER_SYS_NO);
        map.put("Total_fee", bean.getTotal_feeI());
        map.put("Out_trade_no", bean.getCode());
        String type;
        boolean isQr = bean.isQR();
        if (bean.isWx()) {
            if (isQr) {
                type = bean.getGetedWxType() + "-Android[QRCode]";
            } else {
                type = bean.getGetedWxType() + "-Android";
            }
        } else {
            if (isQr) {
                type = bean.getGetedZfbType() + "-Android[QRCode]";
            } else {
                type = bean.getGetedZfbType() + "-Android";
            }

        }
        map.put("Pay_Type", type);
//        String actionUrl = ConstantUtils.URL_INSERT_ORDER_LOG;
        JSONObject jsonObject = new JSONObject(map);
//        Object[] objects = new Object[]{actionUrl, jsonObject};
        //
        BaseInfoStringService.getInstance(context)
                .reinsertLog(jsonObject.toString())
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<String>(context) {
                    @Override
                    protected void doOnNext(String value) {
                        boolean b = Boolean.parseBoolean(value);
                        if (b) {
                            KLog.i("重新日志插入成功");
                        } else {
                            KLog.i("insertPayLog------failure");
                        }
                    }
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }
}
