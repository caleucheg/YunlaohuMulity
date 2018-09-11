package com.yang.yunwang.query.api.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.api.QueryReService;
import com.yang.yunwang.query.api.QueryReStringService;
import com.yang.yunwang.query.api.bean.allcoate.AllcoateInitReq;
import com.yang.yunwang.query.api.bean.allcopage.AllocateBean;
import com.yang.yunwang.query.api.bean.allcopage.DeleteRoleReq;
import com.yang.yunwang.query.api.bean.allcopage.DeleteRoleResp;
import com.yang.yunwang.query.api.bean.allcopage.InsertRoleReq;
import com.yang.yunwang.query.api.bean.allcopage.InsertRoleResp;
import com.yang.yunwang.query.api.contract.AllcoatePageContract;
import com.yang.yunwang.query.api.model.AllcoatePageModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/16.
 */

public class AllcoatePagePresenter implements AllcoatePageContract.Presenter {
    private final AllcoatePageContract.View view;
    private final Context context;
    private final String customersTopSysNo;
    private AllocateBean bean;
    private String systemUserSysNo;
    private boolean fromStaff = false;
    private AllcoatePageContract.Model model;
    private int total_count;
    private int leftLength = 0;
    private int leftLengthF = 0;
    private boolean isDelete = false;

    public AllcoatePagePresenter(AllcoatePageContract.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenter(this);
        this.model = new AllcoatePageModel(context);
        bean = model.loadInstance();
        Intent intent = view.loadInstance();
        fromStaff = intent.getBooleanExtra("from_staff", false);
        customersTopSysNo = intent.getStringExtra("shop_id");
        systemUserSysNo = intent.getStringExtra("staff_id");
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void allocateComfirm(boolean fromHome, int leftLength, AllocateBean bean, int leftLengthF) {
        this.bean = bean;
        this.leftLengthF = leftLengthF;
        KLog.i("length----" + leftLengthF);
        if (fromHome) {
            if (leftLengthF == 0 || isDelete) {
                this.leftLength = leftLength;
                insertRole();
            } else {
                deleteRole();
            }
        } else {
            AllcoateInitReq accessToken = new AllcoateInitReq();
            accessToken.setCustomerServiceSysNo(customersTopSysNo);
            accessToken.setSystemUserSysNo(view.getCno());
            QueryReStringService.getInstance(context)
                    .allcoateCus(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<String>(context) {
                        @Override
                        protected void doOnNext(String value) {
                            if (!TextUtils.isEmpty(value)) {
                                KLog.i(value + "-=-=-=-=-");
                                boolean resu = Boolean.parseBoolean(value);
                                if (resu) {
                                    Toast.makeText(context, "调拨成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "调拨失败,稍后再试", Toast.LENGTH_SHORT).show();
                                }
                                view.finishActivity();
                            }
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                            Toast.makeText(context, "调拨失败,稍后再试", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void deleteRole() {
        DeleteRoleReq accessToken = new DeleteRoleReq();
        BaseObserver<DeleteRoleResp> observer = new BaseObserver<DeleteRoleResp>(context) {
            @Override
            protected void doOnNext(DeleteRoleResp value) {
                if (value != null && !TextUtils.isEmpty(value.toString())) {
                    long i = value.getCode();
                    if (i == 0) {
                        insertRole();
                        isDelete = true;
                    } else if (i == 1) {
                        Toast.makeText(context, "失败: " + value.getDescription() + ",请重试.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                Toast.makeText(context, "失败: " + "网络异常" + ",请重试.", Toast.LENGTH_SHORT).show();
            }
        };
        KLog.i(fromStaff);
        if (fromStaff) {
            accessToken.setSystemUserSysNo(systemUserSysNo);
            QueryReService.getInstance(context)
                    .deleteUserRole(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {
            accessToken.setCustomerServiceSysNo(customersTopSysNo);
            QueryReService.getInstance(context)
                    .deleteCusRole(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }

    private void insertRole() {
        ArrayList<InsertRoleReq> params = new ArrayList<>();
        BaseObserver<InsertRoleResp> observer = new BaseObserver<InsertRoleResp>(context) {
            @Override
            protected void doOnNext(InsertRoleResp result) {
                if (result != null && !TextUtils.isEmpty(result.toString())) {
                    long code = result.getCode();
                    if (code == 0) {
                        Toast.makeText(context, "操作成功", Toast.LENGTH_SHORT).show();
                    } else if (code == 1) {
                        Toast.makeText(context, "操作失败: " + result.getDescription(), Toast.LENGTH_SHORT).show();
                    }
                    view.finishActivity();
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                Toast.makeText(context, "操作失败: " + "网络异常", Toast.LENGTH_SHORT).show();
            }
        };
        List<Integer> lsitTemp = bean.getList_flag();
        for (int i = 0; i < lsitTemp.size(); i++) {
            if (lsitTemp.get(i) == 1) {
                InsertRoleReq param = new InsertRoleReq();
                if (fromStaff) {
                    param.setSystemUserSysNo(systemUserSysNo);
                } else {
                    param.setCustomerServiceSysNo(customersTopSysNo);
                }
                param.setSystemRoleSysNo(bean.getList_cno().get(i));
                KLog.i(bean.getList_editor().size() +"-------------"+ bean.getList_cno().size());
                if (bean.getList_editor().size() == bean.getList_cno().size()) {
                    param.setEditUser(bean.getList_editor().get(i));
                    param.setInUser(bean.getList_creater().get(i));
                    KLog.i(bean.getList_editor().get(i) + bean.getList_creater().get(i));
                } else {
                    if (fromStaff) {
                        KLog.i(ConstantUtils.HIGHER_SYS_NO);
                        if (TextUtils.isEmpty(ConstantUtils.HIGHER_SYS_NO)){
                            param.setEditUser("1");
                        }else {
                            param.setEditUser(ConstantUtils.HIGHER_SYS_NO);
                        }
                    } else {
                        param.setEditUser(ConstantUtils.SYS_NO);
                    }
                    KLog.i(ConstantUtils.SYS_NO);
                    param.setInUser("1");
                }
                params.add(param);

            }
        }
        if (params.size() == 0) {
            Toast.makeText(context, "请选择要添加的权限", Toast.LENGTH_SHORT).show();
        } else {
            if (fromStaff) {
                QueryReService.getInstance(context)
                        .insertUserRole(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer);
            } else {
                QueryReService.getInstance(context)
                        .insertCusRole(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer);
            }
        }
    }
}
