package com.yang.yunwang.pay.view.scan;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.basereq.BaseInfoService;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigReq;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigResp;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IAppProvider;
import com.yang.yunwang.base.moduleinterface.provider.IPayProvider;
import com.yang.yunwang.base.moduleinterface.router.MyRouter;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.Formater;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.pay.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.schedulers.Schedulers;

@Route(path = IPayProvider.PAY_ACT_SCAN_CODE)
public class ScanCodeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView image_scan;
    private EditText edit_scan_money;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scancode);
        setTitleText(this.getString(R.string.scan_title));
        getPayConfig(ConstantUtils.HIGHER_SYS_NO);
        image_scan = (ImageView) findViewById(R.id.image_scan);
        KLog.i(ConstantUtils.SYS_NO + "---" + ConstantUtils.HIGHER_SYS_NO);
        edit_scan_money = (EditText) findViewById(R.id.edit_scan_money);
        edit_scan_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().contains(".")) {
                    if (charSequence.length() - 1 - charSequence.toString().indexOf(".") > 2) {
                        charSequence = charSequence.toString().subSequence(0,
                                charSequence.toString().indexOf(".") + 3);
                        edit_scan_money.setText(charSequence);
                        edit_scan_money.setSelection(charSequence.length());
                    }
                }
                if (charSequence.toString().trim().substring(0).equals(".")) {
                    charSequence = "0" + charSequence;
                    edit_scan_money.setText(charSequence);
                    edit_scan_money.setSelection(2);
                }
                if (charSequence.toString().length() > 6 && !charSequence.toString().contains(".")) {
                    edit_scan_money.setText(charSequence.toString().substring(0, 6));
                    edit_scan_money.setSelection(6);
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//        image_back.setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
        image_scan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_back) {
            HomeIntent.launchHome();
            this.finish();

        } else if (i == R.id.image_scan) {
            String money = edit_scan_money.getText().toString();
            Pattern p = Pattern.compile("^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,2})?))$");
            Matcher matcher = p.matcher(money);
            if (!matcher.matches()) {
                money = Formater.formatMoney(money, 2);
            }
            edit_scan_money.setText(money);
            edit_scan_money.setSelection(money.length());
            if (edit_scan_money.getText().toString().equals("")) {
                showAlertDialog("请输入金额！");
            } else if (Double.parseDouble(edit_scan_money.getText().toString()) == 0) {
                showAlertDialog("金额不能为0元！");
            } else {
                boolean netC = NetStateUtils.isNetworkConnected(ScanCodeActivity.this);
                if (netC) {
                    MyBundle intent = new MyBundle();//this, MipcaActivityCapture.class
                    intent.put("total_fee", edit_scan_money.getText().toString());
                    MyRouter.newInstance(IAppProvider.APP_ACT_CAPTURE)
                            .withBundle(intent)
                            .navigation();
//                    this.startActivity(intent);
                } else {
                    Toast.makeText(ScanCodeActivity.this, "当前网络已断开,请检查后再试", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private void showAlertDialog(String message) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(this.getResources().getString(R.string.alert_title));
            alertDialog.setMessage(message);
            alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    alertDialog = null;
                }
            });
            alertDialog.setPositiveButton(this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    alertDialog = null;
                }
            });
            alertDialog.show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            HomeIntent.launchHome();
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getPayConfig(String higherSysNo) {
        PayConfigReq accessToken = new PayConfigReq();
        accessToken.setCustomerSysNo(higherSysNo);
        BaseInfoService.getInstance(this)
                .getPayConfig(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new BaseObserver<List<PayConfigResp>>(this) {
                    @Override
                    protected void doOnNext(List<PayConfigResp> value) {
                        if (value.size() != 0) {
                            for (int i = 0; i < value.size(); i++) {
                                String remarks = value.get(i).getRemarks();
                                if (TextUtils.equals(remarks, "WX")) {
                                    ConstantUtils.GETED_WX_TYPE = value.get(i).getType() + "";
                                } else if (TextUtils.equals(remarks, "AliPay")) {
                                    ConstantUtils.GETED_ZFB_TYPE = value.get(i).getType() + "";
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
