package com.yang.yunwang.home.mainhome.presenter;

import android.content.Context;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.homeservice.HomeREService;
import com.yang.yunwang.home.homeservice.bean.RoleTypeReq;
import com.yang.yunwang.home.homeservice.bean.RoleTypeResp;
import com.yang.yunwang.home.mainhome.bean.StaffHomeBean;
import com.yang.yunwang.home.mainhome.contract.StaffHomeContract;
import com.yang.yunwang.home.mainhome.model.StaffHomeModel;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/6/13.
 */

public class StaffHomePresenter implements StaffHomeContract.Presenter {
    private final StaffHomeContract.View view;
    private final Context context;
    private StaffHomeContract.Model staffHomeModel;
    private StaffHomeBean staffHomeBean;


    public StaffHomePresenter(StaffHomeContract.View view, Context context) {
        view.setPresenter(this);
        this.view = view;
        this.context = context;
        staffHomeModel = new StaffHomeModel(context);
        staffHomeBean = staffHomeModel.loadInstance();
    }

    @Override
    public void initData(String sys_no, String customer) {
        staffHomeModel.setInfos(sys_no, customer);
        if (!ConstantUtils.HIGHER_SYS_NO.equals("") && !ConstantUtils.STAFF_TYPE.equals("") && !ConstantUtils.HIGHER_NAME.equals("")) {
            if (ConstantUtils.STAFF_TYPE.equals("0")) {
                //服务商员工
                staffHomeModel.initServiceMainFunctionList();
                staffHomeModel.initServiceSubFunctionList();
            } else {
                //商户员工
                staffHomeModel.initMainFunctionList();
                staffHomeModel.initSubFunctionList();
            }
            setAdapter();
        } else {
            RoleTypeReq accessToken = new RoleTypeReq();
            accessToken.setSystemUserSysNo(Long.parseLong(sys_no));
            HomeREService.getInstance(context)
                    .getTopInfo(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<RoleTypeResp>(context) {
                        @Override
                        protected void doOnNext(RoleTypeResp value) {
                            String id = null;
                            id = value.getCustomerSysNo();
                            ConstantUtils.HIGHER_SYS_NO = id;
                            String type = value.getCustomersType();
                            ConstantUtils.STAFF_TYPE = type;
                            ConstantUtils.NEW_TYPE = Integer.parseInt(type) + 2 + "";
                            KLog.i(ConstantUtils.NEW_TYPE + "==+[[==");
                            CommonShare.putTypeData(context, ConstantUtils.NEW_TYPE);
                            String name = value.getCustomerName();
                            ConstantUtils.HIGHER_NAME = name;
                            String[] key = new String[]{"HIGHER_SYS_NO", "STAFF_TYPE", "HIGHER_NAME"};
                            String[] valueA = new String[]{ConstantUtils.HIGHER_SYS_NO, ConstantUtils.STAFF_TYPE, ConstantUtils.HIGHER_NAME};
                            CommonShare.putHomeData(context, key, valueA);
                            if (type.equals("0")) {
                                //服务商员工
                                staffHomeModel.initServiceMainFunctionList();
                                staffHomeModel.initServiceSubFunctionList();
                            } else {
                                //商户员工
                                staffHomeModel.initMainFunctionList();
                                staffHomeModel.initSubFunctionList();
                            }
                            setAdapter();
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                            Toast.makeText(context, "获取员工类别失败！", Toast.LENGTH_SHORT).show();
//                            Intent intent_logout = new Intent(context, LoginActivity.class);
                            CommonShare.clear(context);
                            JPushInterface.cleanTags(context, 12);
                            ConstantUtils.SYS_NO = "";
                            ConstantUtils.HIGHER_SYS_NO = "";
                            ConstantUtils.CUSTOMER = "";
                            ConstantUtils.CUSTOMERS_TYPE = "";
                            ConstantUtils.STAFF_TYPE = "";
                            ConstantUtils.HIGHER_NAME = "";
                            ConstantUtils.NEW_TYPE = "-1";
                            HomeIntent.login();
//                            context.startActivity(intent_logout);
                        }
                    });
        }
    }

    private void setAdapter() {
        if (staffHomeBean.getMain_function_list() != null
                && staffHomeBean.getMain_function_res() != null
                && staffHomeBean.getMain_function_res_selected() != null
                && staffHomeBean.getMain_function_list().size() != 0
                && staffHomeBean.getMain_function_res().length != 0
                && staffHomeBean.getMain_function_res_selected().length != 0) {
            view.setMainAdapter(staffHomeBean.getMain_function_list(), staffHomeBean.getMain_function_res(), staffHomeBean.getMain_function_res_selected(), staffHomeBean.getMianActions(),staffHomeBean.getMainBundles());
        }
        if (staffHomeBean.getSub_function_list() != null
                && staffHomeBean.getMain_function_res() != null
                && staffHomeBean.getSub_function_res_selected() != null
                && staffHomeBean.getSub_function_list().size() != 0
                && staffHomeBean.getSub_function_res().length != 0
                && staffHomeBean.getSub_function_res_selected().length != 0) {
            view.setSubAdapter(staffHomeBean.getSub_function_list(), staffHomeBean.getSub_function_res(), staffHomeBean.getSub_function_res_selected(), staffHomeBean.getSubActions(),staffHomeBean.getSubBundles());
        }
        if (!staffHomeBean.getCustomer().equals("")) {
            view.setHeaderTitle(staffHomeBean.getCustomer());
        }
        if (!ConstantUtils.HIGHER_NAME.equals("")) {
            view.setTopName(ConstantUtils.HIGHER_NAME);
        }
    }
}
