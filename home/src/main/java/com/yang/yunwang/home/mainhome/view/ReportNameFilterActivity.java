package com.yang.yunwang.home.mainhome.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kw.rxbus.RxBus;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.busevent.CloseActivityEvent;
import com.yang.yunwang.base.busevent.ReportFilterEvent;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.ui.ClearEditText;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.base.util.utils.EditTextLengthFilter;
import com.yang.yunwang.home.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

@Route(path = IHomeProvider.HOME_ACT_REPORT_NAME_FILTER)
public class ReportNameFilterActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 全部商户
     */
    private TextView tv_all_customer;
    /**
     * 商户查询
     */
    private TextView tv_switch_customer;
    /**
     * 商户登录名
     */
    private ClearEditText et_login_name;
    /**
     * 商户名称
     */
    private ClearEditText et_customer_name;
    /**
     * 查 询
     */
    private Button btn_search;
    private LinearLayout ll_customer_box;
    private boolean isSearchAll = true;
    private String newType;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_name_filter);
        initView();
        initEventBus();
    }

    private void initEventBus() {
        disposable = RxBus.getInstance().register(CloseActivityEvent.class,
                AndroidSchedulers.mainThread(),
                new Consumer<CloseActivityEvent>() {
                    @Override
                    public void accept(CloseActivityEvent userEvent) {
                        if (userEvent.isCloseActivity()) {
                            KLog.i(userEvent.getFromTag());
                            ReportNameFilterActivity.this.finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        KLog.i(throwable.getMessage());
                    }
                });
    }

    private void initView() {
        tv_all_customer = (TextView) findViewById(R.id.tv_all_customer);
        tv_all_customer.setOnClickListener(this);
        tv_switch_customer = (TextView) findViewById(R.id.tv_switch_customer);
        tv_switch_customer.setOnClickListener(this);
        et_login_name = (ClearEditText) findViewById(R.id.et_login_name);
        et_customer_name = (ClearEditText) findViewById(R.id.et_customer_name);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        ll_customer_box = (LinearLayout) findViewById(R.id.ll_customer_box);
        getLlBasetitleBack().setOnClickListener(this);
        newType = ConstantUtils.NEW_TYPE;
        KLog.i("newType+" + newType);
        String titles = "";
        InputFilter specialCharFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String regexStr = "[`#\"$%^&|{}':;,\\[\\].<>/￥…*（）【】‘；：”“’，、]";
                Pattern pattern = Pattern.compile(regexStr);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.matches()) {
                    return "";
                } else {
                    return null;
                }

            }
        };
        EditTextLengthFilter editTextLengthFilter1 = new EditTextLengthFilter(this, 25, "");
        InputFilter[] filters = {editTextLengthFilter1, specialCharFilter};
        if (TextUtils.equals("0", newType) || TextUtils.equals("2", newType)) {
            titles = "选择商户";
            EditTextLengthFilter editTextLengthFilter = new EditTextLengthFilter(this, 11, "");
            InputFilter[] filters1 = {editTextLengthFilter, specialCharFilter};
            et_login_name.setFilters(filters1);
            et_login_name.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (TextUtils.equals("1", newType)) {
            titles = "选择商户员工";
            tv_all_customer.setText("全部员工");
            et_login_name.setHint("员工登录名");
            et_login_name.setFilters(filters);
            et_customer_name.setHint("员工名称");
            tv_switch_customer.setText("员工查询");
        }
        et_customer_name.setFilters(filters);
        setTitleText(titles);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_all_customer) {
            ll_customer_box.setVisibility(View.GONE);
            tv_all_customer.setBackground(getResources().getDrawable(R.drawable.material_button_round_small));
            tv_all_customer.setTextColor(getResources().getColor(R.color.white_color));
            tv_switch_customer.setBackground(getResources().getDrawable(R.drawable.small_coner_shape));
            tv_switch_customer.setTextColor(getResources().getColor(R.color.blue_color_text));
            isSearchAll = true;
        } else if (i == R.id.tv_switch_customer) {
            ll_customer_box.setVisibility(View.VISIBLE);
            tv_switch_customer.setBackground(getResources().getDrawable(R.drawable.material_button_round_small));
            tv_switch_customer.setTextColor(getResources().getColor(R.color.white_color));
            tv_all_customer.setBackground(getResources().getDrawable(R.drawable.small_coner_shape));
            tv_all_customer.setTextColor(getResources().getColor(R.color.blue_color_text));
            isSearchAll = false;
        } else if (i == R.id.btn_search) {
            if (isSearchAll) {
                ReportFilterEvent event = new ReportFilterEvent(true, "", false, "", "", "");
                RxBus.getInstance().send(event);
                this.finish();
            } else {
                String cusNo = et_login_name.getText().toString().trim();
                String cusName = et_customer_name.getText().toString().trim();
                if (TextUtils.isEmpty(cusNo) && TextUtils.isEmpty(cusName)) {
                    Toast.makeText(ReportNameFilterActivity.this, "搜索条件不能为空，请重新输入", Toast.LENGTH_SHORT).show();
                } else {
                    MyBundle intent_order_search = new MyBundle();
                    intent_order_search.put("shop_user", cusNo);
                    intent_order_search.put("shop_name", cusName);
                    if (TextUtils.equals("1", newType)) {
                        intent_order_search.put("isCus", true);
                    }
                    if (NetStateUtils.isNetworkConnected(ReportNameFilterActivity.this)) {
                        OrdersIntent.commonCusCardList(intent_order_search);
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ReportNameFilterActivity.this);
                        dialog.setMessage("网络连接异常，请检查您的手机网络");
                        dialog.setPositiveButton(ReportNameFilterActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }


                }
            }
        } else if (i == R.id.image_back) {
            this.finish();
        } else {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(disposable);
    }
}
