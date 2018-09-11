package com.yang.yunwang.base.basereq;

import android.content.Context;

import com.yang.yunwang.base.basereq.bean.merchinfo.MerchInfoReq;
import com.yang.yunwang.base.basereq.bean.merchinfo.MerchInfoResp;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigReq;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigResp;
import com.yang.yunwang.base.basereq.bean.staffinfo.StaffInfoReq;
import com.yang.yunwang.base.basereq.bean.staffinfo.StaffInfoResp;
import com.yang.yunwang.base.ret.HttpsServiceGenerator;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/6/11.
 */

public class BaseInfoService {
    private static BaseInfoService balanceModel;
    private APIBase mBalanceService;

    /**
     * Singleton
     */
    public static BaseInfoService getInstance(Context context, int READ_TIMEOUT, int WRIT_TIMEOUT , int CONNECT_TIMEOUT) {
        if (balanceModel == null) {
            balanceModel = new BaseInfoService(context, READ_TIMEOUT,  WRIT_TIMEOUT , CONNECT_TIMEOUT);
        }
        return balanceModel;
    }

    public static BaseInfoService getInstance(Context context) {
        if (balanceModel == null) {
            balanceModel = new BaseInfoService(context);
        }
        return balanceModel;
    }
    private BaseInfoService(Context context,int READ_TIMEOUT, int WRIT_TIMEOUT ,int CONNECT_TIMEOUT) {
        mBalanceService =  HttpsServiceGenerator.createService(APIBase.class, READ_TIMEOUT,  WRIT_TIMEOUT , CONNECT_TIMEOUT);
    }

    private BaseInfoService(Context context) {
        mBalanceService =  HttpsServiceGenerator.createService(APIBase.class);
    }

    public Observable<StaffInfoResp> getStaffInfo(StaffInfoReq accessToken) {
        return mBalanceService.checkStaffPWD(accessToken);
    }

    public Observable<MerchInfoResp> getMerchInfo(MerchInfoReq accessToken) {
        return mBalanceService.checkMerchPWD(accessToken);
    }
    public Observable<List<PayConfigResp>> getPayConfig(PayConfigReq accessToken) {
        return mBalanceService.getPayConfig(accessToken);
    }

}
