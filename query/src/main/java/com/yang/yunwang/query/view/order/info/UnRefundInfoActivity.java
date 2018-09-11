package com.yang.yunwang.query.view.order.info;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.dao.PassageWay;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.Formater;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.contract.UnrefundInfoContract;
import com.yang.yunwang.query.api.presenter.UnrefundInfoPresenter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Route(path = IOrdersProvider.ORDERS_ACT_UNREFUND_INFO)
public class UnRefundInfoActivity extends BaseActivity implements View.OnClickListener, UnrefundInfoContract.View {

    public static final int MIN_CLICK_DELAY_TIME = 3000;
    private EditText edit_sys_no;
    private EditText edit_code;
    private EditText edit_pay_type;
    private EditText edit_money;
    private EditText edit_order_time;
    private EditText edit_refund_count;
    private EditText edit_refund;
    private EditText edit_cash;
    private TextView status_controller;
    private ImageView image_close;
    private EditText edit_controller_code;
    private EditText edit_controller_refund;
    private EditText edit_controller_password;
    private EditText edit_controller_releas;
    private Button btn_refund;
    private LinearLayout status_background;
    private BottomSheetDialog bottomSheetDialog;
    private String pay_type;
    private String total_fee;
    private View view_actionsheet;
    private int flag = 0;   //判断返回列表页，是否需要列表页面刷新 0不刷新，1刷新
    private UnrefundInfoContract.Presenter presenter;
    private boolean hasRole;
    private long lastClickTime = 0;
    private long lastClickTimeA = 0;
    private ImageView llBasehomeBack;
    private ImageView llBasetitleBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_unrefundinfo);
        setTitleText(this.getString(R.string.unrefund_info_title));
        setHomeBarVisisble(true);
        hasRole = getIntent().getBooleanExtra("hasRole", false);
        init();
    }

    private void init() {
        new UnrefundInfoPresenter(this, this);
        edit_sys_no = (EditText) findViewById(R.id.edit_unrefund_info_sys_no);
        edit_code = (EditText) findViewById(R.id.edit_unrefund_info_code);
        edit_pay_type = (EditText) findViewById(R.id.edit_unrefund_info_pay_type);
        edit_money = (EditText) findViewById(R.id.edit_unrefund_info_money);
        edit_order_time = (EditText) findViewById(R.id.edit_unrefund_info_order_time);
        edit_refund = (EditText) findViewById(R.id.edit_unrefund_info_refund);
        edit_cash = (EditText) findViewById(R.id.edit_unrefund_info_cash);
        edit_refund_count = (EditText) findViewById(R.id.edit_unrefund_info_refund_count);
        status_controller = (TextView) findViewById(R.id.text_unrefund_info_status);
         llBasehomeBack = getLlBasehomeBack();
        llBasehomeBack.setOnClickListener(this);
         llBasetitleBack = getLlBasetitleBack();
        llBasetitleBack.setOnClickListener(this);
        status_background = (LinearLayout) findViewById(R.id.linear_unrefund_status_background);
        presenter.initData();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_home) {

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(llBasehomeBack.getWindowToken(), 0);
                KLog.i("focus");
            }
            HomeIntent.launchHome();
            this.finish();

        } else if (i == R.id.image_back) {
            if (flag == 1) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(llBasetitleBack.getWindowToken(), 0);
                    KLog.i("focus");
                }
                this.setResult(1);
                this.finish();
            } else {
                this.finish();
            }

        } else if (i == R.id.text_unrefund_info_status) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                if (!(TextUtils.equals(pay_type, ConstantUtils.GETED_WX_TYPE) || TextUtils.equals(pay_type, ConstantUtils.GETED_ZFB_TYPE))) {
                    Toast.makeText(this, "非当前通道不允许退款!", Toast.LENGTH_SHORT).show();
                } else {
                    bottomSheetDialog = new BottomSheetDialog(this);
                    bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                imm.hideSoftInputFromWindow(UnRefundInfoActivity.this.getCurrentFocus().getWindowToken(), 0);
                                KLog.i("focus");
                            }
                        }
                    });
                    view_actionsheet = getLayoutInflater().inflate(R.layout.layout_unrefund_actionsheet, null);
                    image_close = (ImageView) view_actionsheet.findViewById(R.id.image_close);
                    edit_controller_code = (EditText) view_actionsheet.findViewById(R.id.edit_unrefund_controller_code);
                    edit_controller_refund = (EditText) view_actionsheet.findViewById(R.id.edit_unrefund_controller_refund);
                    edit_controller_password = (EditText) view_actionsheet.findViewById(R.id.edit_unrefund_controller_password);
                    edit_controller_releas = (EditText) view_actionsheet.findViewById(R.id.edit_unrefund_controller_releas);
                    edit_controller_releas.setText("（可退金额：" + edit_cash.getText().toString() + ")");

                    edit_controller_refund.setText(edit_cash.getText().toString());
                    edit_controller_releas.setText("(可退金额：" + "0.0" + ")");

                    edit_controller_refund.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            if (charSequence.toString().contains(".")) {
                                if (charSequence.length() - 1 - charSequence.toString().indexOf(".") > 2) {
                                    charSequence = charSequence.toString().subSequence(0,
                                            charSequence.toString().indexOf(".") + 3);
                                    edit_controller_refund.setText(charSequence);
                                    edit_controller_refund.setSelection(charSequence.length());
                                }
                            }
                            if (charSequence.toString().trim().substring(0).equals(".")) {
                                charSequence = "0" + charSequence;
                                edit_controller_refund.setText(charSequence);
                                edit_controller_refund.setSelection(2);
                            }

                            if (charSequence.toString().startsWith("0") && charSequence.toString().trim().length() > 1) {
                                if (!charSequence.toString().substring(1, 2).equals(".")) {
                                    edit_controller_refund.setText(charSequence.subSequence(0, 1));
                                    edit_controller_refund.setSelection(1);
                                    return;
                                }
                            }
                            if (!charSequence.toString().equals("")) {
                                if (Double.parseDouble(charSequence.toString()) > Double.parseDouble(edit_cash.getText().toString())) {
                                    edit_controller_releas.setText("输入金额错误！");
                                } else {
                                    BigDecimal bigDecimal_money = new BigDecimal(edit_cash.getText().toString());
                                    BigDecimal bigDecimal_charSequence = new BigDecimal(charSequence.toString());
                                    Double releas = bigDecimal_money.subtract(bigDecimal_charSequence).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    edit_controller_releas.setText("(可退金额：" + releas + ")");
                                }
                            } else {
                                edit_controller_releas.setText("(可退金额：" + edit_cash.getText().toString() + ")");
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    btn_refund = (Button) view_actionsheet.findViewById(R.id.btn_refund);
                    btn_refund.setOnClickListener(this);
                    edit_controller_code.setText(edit_code.getText().toString());
                    image_close.setOnClickListener(this);
                    bottomSheetDialog.show();
                    //此处利用getWindow防止出现键盘遮挡底部对话框的文本输入区域
                    bottomSheetDialog.getWindow().setContentView(view_actionsheet);
                    bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    bottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                    bottomSheetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }
            } else {
                KLog.i("re-click");
                KLog.i(lastClickTime);
                KLog.i(currentTime);
            }

        } else if (i == R.id.image_close) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(image_close.getWindowToken(), 0);
                KLog.i("focus");
            }
            bottomSheetDialog.dismiss();

        } else if (i == R.id.btn_refund) {
            long currentTimea = Calendar.getInstance().getTimeInMillis();
            if (currentTimea - lastClickTimeA > MIN_CLICK_DELAY_TIME) {
                lastClickTimeA = currentTimea;
                String money = edit_controller_refund.getText().toString();
                Pattern p = Pattern.compile("^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,2})?))$");
                Matcher matcher = p.matcher(money);
                if (!matcher.matches()) {
                    money = Formater.formatMoney(money, 2);
                }
                edit_controller_refund.setText(money);
                edit_controller_refund.setSelection(money.length());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String cur_time = formatter.format(curDate);
                KLog.i(pay_type + "-------------" + ConstantUtils.GETED_WX_TYPE + "----0" + ConstantUtils.GETED_ZFB_TYPE);
                if (!(TextUtils.equals(pay_type, ConstantUtils.GETED_WX_TYPE) || TextUtils.equals(pay_type, ConstantUtils.GETED_ZFB_TYPE))) {
                    Toast.makeText(this, "非当前通道不允许退款!", Toast.LENGTH_SHORT).show();
                } else if (edit_controller_refund.getText().toString().equals("")) {
                    Toast.makeText(this, "请输入退款金额！", Toast.LENGTH_SHORT).show();
                } else if (Double.parseDouble(edit_controller_refund.getText().toString()) == 0) {
                    Toast.makeText(this, "退款金额不可以为0元！", Toast.LENGTH_SHORT).show();
                } else if (Float.parseFloat(edit_controller_refund.getText().toString()) > Float.parseFloat(edit_cash.getText().toString())) {
                    Toast.makeText(this, "退款金额不能大于交易金额！", Toast.LENGTH_SHORT).show();
                } else if (edit_controller_password.getText().toString().equals("")) {
                    Toast.makeText(this, "请输入该用户密码！", Toast.LENGTH_SHORT).show();
                } else if (!edit_order_time.getText().toString().substring(0, 10).equals(cur_time) && !hasRole) {
                    Toast.makeText(this, "非当天退款信息不得退款！", Toast.LENGTH_SHORT).show();
                } else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    Toast.makeText(this, "退款中", Toast.LENGTH_SHORT).show();
                    presenter.Refund(edit_controller_password.getText().toString(),
                            edit_controller_code.getText().toString(),
                            edit_controller_refund.getText().toString(),
                            total_fee,
                            edit_sys_no.getText().toString(),
                            ConstantUtils.SYS_NO, pay_type);
                }
            } else {
                KLog.i("re-click");
                KLog.i(lastClickTimeA);
                KLog.i(currentTimea);
            }

        }
    }

    @Override
    public Intent loadIntentInstance() {
        return getIntent();
    }

    @Override
    public void closeActionSheet() {
        bottomSheetDialog.dismiss();
    }

    @Override
    public void changStatus() {
        status_controller.setText(this.getResources().getString(R.string.refunded_status));
        status_background.setBackground(this.getResources().getDrawable(R.drawable.status_refunded));
        status_controller.setOnClickListener(null);
        this.flag = 1;
    }

    @Override
    public void setInfo(String sys_no, String code, String pay_type, String order_time, String money, String refund, String cash, String refund_count, String status, String total_fee, String total_FEE) {
        edit_sys_no.setText(sys_no);
        edit_code.setText(code);
        edit_money.setText(money);
        edit_order_time.setText(order_time);
        edit_refund.setText(refund);
        edit_cash.setText(cash);
        edit_refund_count.setText(refund_count);
        this.pay_type = pay_type;
        this.total_fee = total_fee;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String cur_time = formatter.format(curDate);
        ArrayList<String> types = new ArrayList();
        ArrayList<String> typeNames = new ArrayList();
        for (PassageWay p : ConstantUtils.PASSAGWE_WAYS) {
            types.add(p.getType());
            typeNames.add(p.getTypeName());
        }
        int index = types.indexOf(pay_type);
        if (index > -1) {
            edit_pay_type.setText(typeNames.get(index));
        }
        status_background.setVisibility(View.VISIBLE);
        if (!edit_order_time.getText().toString().substring(0, 10).equals(cur_time) && !hasRole) {
            status_controller.setText(this.getResources().getString(R.string.unrefund_error));
            status_background.setBackground(this.getResources().getDrawable(R.drawable.status_refund_cannot));
            status_controller.setOnClickListener(null);
        } else if (Double.parseDouble(status) == Double.parseDouble(total_FEE)) {
            status_controller.setText(this.getResources().getString(R.string.unrefund_status));
            status_background.setBackground(this.getResources().getDrawable(R.drawable.unrefund_controller_background));
            status_controller.setOnClickListener(this);
        } else if (Double.parseDouble(status) < Double.parseDouble(total_FEE) &&
                Double.parseDouble(status) > 0) {
            status_controller.setText(this.getResources().getString(R.string.unrefund_step));
            status_background.setBackground(this.getResources().getDrawable(R.drawable.unrefund_controller_background));
            status_controller.setOnClickListener(this);
        } else {
            status_controller.setText(this.getResources().getString(R.string.refunded_status));
            status_background.setBackground(this.getResources().getDrawable(R.drawable.status_refunded));
            status_controller.setOnClickListener(null);
        }
        KLog.i(pay_type + "-------------" + ConstantUtils.GETED_WX_TYPE + "----0-" + ConstantUtils.GETED_ZFB_TYPE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (flag == 1) {
                //通知刷新
//                Intent intent = new Intent(this, UnRefundListActivity.class);
//                this.startActivity(intent);
                this.setResult(1);
                this.finish();
            } else {
                //通知不刷新
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setPresenter(UnrefundInfoContract.Presenter presenter) {
        this.presenter=presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
