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
import com.yang.yunwang.base.util.AmountUtils;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.Formater;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.contract.MerchRefundInfoContract;
import com.yang.yunwang.query.api.presenter.MerchRefundInfoPresenter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Route(path = IOrdersProvider.ORDERS_ACT_REFUND_INFO)
public class RefundInfoActivity extends BaseActivity implements View.OnClickListener, MerchRefundInfoContract.View {

    public static final int MIN_CLICK_DELAY_TIME = 5000;
    private EditText edit_sys_no;
    private EditText edit_code;
    private EditText edit_pay_type;
    private EditText edit_money;
    private EditText edit_order_time;
    private EditText edit_refund_time;
    private EditText edit_refund;
    private EditText edit_status;
    //    private ImageView image_home;
    private boolean isPrintO;
    private boolean isFromMerHome;
    private TextView status_controller;
    private LinearLayout status_background;
    private BottomSheetDialog bottomSheetDialog;
    private View view_actionsheet;
    private ImageView image_close;
    private EditText edit_controller_code;
    private EditText edit_controller_refund;
    private EditText edit_controller_password;
    private EditText edit_controller_releas;
    private Button btn_refund;
    private String pay_type;
    private boolean hasRole;
    private String old_sys_no;
    private String fee;
    private String money;
    private int flag = 0;
    private Context context;
    private MerchRefundInfoContract.Presenter presenter;
    private TextView refundCount;
    private TextView feeRelase;
    private Intent intent;
    private String transaction_id_list;
    private long lastClickTime = 0;
    private long lastClickTimeA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_refundinfo);
        setTitleText(this.getString(R.string.refund_search_info_title));
        intent = getIntent();
        context = this;
        if (getIntent().getBooleanExtra("FROM_MER_HOME", false)) {
            new MerchRefundInfoPresenter(this, this);
            presenter.initData(intent);
        }
        try {
            fee = intent.getLongExtra("Fee_list", 0L) + "";
        } catch (ClassCastException e) {
            KLog.i("11111");
            fee = intent.getStringExtra("Fee_list");
        }
        try {
            money = intent.getLongExtra("money_list", 0L) + "";
        } catch (ClassCastException e) {
            KLog.i("11111");
            money = intent.getStringExtra("money_list");
        }

        setHomeBarVisisble(true);
        edit_sys_no = (EditText) findViewById(R.id.edit_refund_info_sys_no);
        edit_code = (EditText) findViewById(R.id.edit_refund_info_code);
        edit_pay_type = (EditText) findViewById(R.id.edit_refund_info_pay_type);
        edit_money = (EditText) findViewById(R.id.edit_refund_info_money);
        edit_order_time = (EditText) findViewById(R.id.edit_refund_info_order_time);
        edit_refund_time = (EditText) findViewById(R.id.edit_refund_info_time);
        edit_refund = (EditText) findViewById(R.id.edit_refund_info_refund);
        edit_status = (EditText) findViewById(R.id.edit_refund_info_status);
        getLlBasetitleBack().setOnClickListener(this);
        getLlBasehomeBack().setOnClickListener(this);
        String sys_no;
        try {
            sys_no = intent.getLongExtra("sys_no_list", 0L) + "";
        } catch (ClassCastException e) {
            sys_no = intent.getStringExtra("sys_no_list") ;
        }

        String code = intent.getStringExtra("code_list");
        pay_type = intent.getStringExtra("pay_type");
        String order_time = intent.getStringExtra("order_time_list");
        String refund_time = intent.getStringExtra("refund_date_list");
        String refund;
        try {
            refund = intent.getLongExtra("refund_list", 0L) + "";
        } catch (ClassCastException e) {
            refund = intent.getStringExtra("refund_list") + "";
        }

        String status = intent.getStringExtra("order_status_list");
        isPrintO = intent.getBooleanExtra("isPrintO", false);
        transaction_id_list = intent.getStringExtra("Transaction_id_list");
        if (TextUtils.isEmpty(status)) {
            findViewById(R.id.rel_order_search_item_9).setVisibility(View.GONE);
            findViewById(R.id.view_8).setVisibility(View.GONE);
        }
        KLog.i(TextUtils.isEmpty(sys_no)+"---"+sys_no);
        if (TextUtils.isEmpty(sys_no)||TextUtils.equals(sys_no,"0")) {
            findViewById(R.id.rel_order_search_item_1).setVisibility(View.GONE);
            findViewById(R.id.view_1).setVisibility(View.GONE);
        }
        if (isPrintO) {
            setTitleText("订单详情");
            findViewById(R.id.view_5).setVisibility(View.GONE);
            findViewById(R.id.rel_order_search_item_6).setVisibility(View.GONE);
        }
        edit_sys_no.setText(sys_no);
        edit_code.setText(code);
        String s = "";
        try {
            s = AmountUtils.changeF2Y(money);
        } catch (Exception e) {
            e.printStackTrace();
        }
        edit_money.setText(s);
        String s1 = "";
        try {
            s1 = AmountUtils.changeF2Y(refund);
        } catch (Exception e) {
            e.printStackTrace();
        }
        edit_refund.setText(s1);
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
        edit_order_time.setText(order_time);
        edit_refund_time.setText(refund_time);
        edit_status.setText(status);

        isFromMerHome = intent.getBooleanExtra("FROM_MER_HOME", false);
        KLog.i(isFromMerHome + "----------------------------");
        if (isFromMerHome || isPrintO) {
            findViewById(R.id.view_10).setVisibility(View.VISIBLE);
            findViewById(R.id.rel_order_search_item_11).setVisibility(View.VISIBLE);
            findViewById(R.id.view_11).setVisibility(View.VISIBLE);
            findViewById(R.id.rel_order_search_item_12).setVisibility(View.VISIBLE);
            refundCount = (TextView) findViewById(R.id.text_unrefund_info_status12);
            feeRelase = (TextView) findViewById(R.id.text_unrefund_info_status10);
            String refund_count_list;
            try {
                refund_count_list = intent.getLongExtra("Refund_Count_list", 0L) + "";
            }catch (ClassCastException e){
                refund_count_list = intent.getStringExtra("Refund_Count_list") + "";
            }
            refundCount.setText(refund_count_list);
            long fee_list;
            try {
                fee_list = intent.getLongExtra("Fee_list", 0L);
            }catch (ClassCastException e){
                fee_list = Long.parseLong(intent.getStringExtra("Fee_list"));
            }
            String s2 = "";
            try {
                s2 = AmountUtils.changeF2Y(fee_list);
            } catch (Exception e) {
                e.printStackTrace();
            }
            feeRelase.setText(s2);
        }
        if (isFromMerHome) {
            findViewById(R.id.rel_order_search_item_10).setVisibility(View.VISIBLE);
            findViewById(R.id.view_9).setVisibility(View.VISIBLE);
            status_controller = (TextView) findViewById(R.id.text_unrefund_info_status);
            status_controller.setOnClickListener(this);
            status_background = (LinearLayout) findViewById(R.id.linear_unrefund_status_background);
        }
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_home) {
            HomeIntent.launchHome();
            this.finish();

        } else if (i == R.id.image_back) {
            if (flag == 1) {
                this.setResult(1);
                this.finish();
            } else {
                this.finish();
            }

        } else if (i == R.id.text_unrefund_info_status) {
            long currentTimea = Calendar.getInstance().getTimeInMillis();
            if (currentTimea - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTimea;

                if (!(TextUtils.equals(pay_type, ConstantUtils.GETED_WX_TYPE) || TextUtils.equals(pay_type, ConstantUtils.GETED_ZFB_TYPE))) {
                    Toast.makeText(this, "非当前通道不允许退款!", Toast.LENGTH_SHORT).show();
                } else {
                    bottomSheetDialog = new BottomSheetDialog(this);
                    bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                imm.hideSoftInputFromWindow(RefundInfoActivity.this.getCurrentFocus().getWindowToken(), 0);
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
                    edit_controller_releas.setText("（可退金额：" + feeRelase.getText().toString() + ")");
                    edit_controller_refund.setText(feeRelase.getText().toString());
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
                                if (Double.parseDouble(charSequence.toString()) > Double.parseDouble(feeRelase.getText().toString())) {
                                    edit_controller_releas.setText("输入金额错误！");
                                } else {
                                    BigDecimal bigDecimal_money = new BigDecimal(feeRelase.getText().toString());
                                    BigDecimal bigDecimal_charSequence = new BigDecimal(charSequence.toString());
                                    Double releas = bigDecimal_money.subtract(bigDecimal_charSequence).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    edit_controller_releas.setText("(可退金额：" + releas + ")");
                                }
                            } else {
                                edit_controller_releas.setText("(可退金额：" + feeRelase.getText().toString() + ")");
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
            }

        } else if (i == R.id.image_close) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(image_close.getWindowToken(), 0);
                KLog.i("focus");
            }
            bottomSheetDialog.dismiss();

        } else if (i == R.id.btn_refund) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTimeA > MIN_CLICK_DELAY_TIME) {
                lastClickTimeA = currentTime;
                String money = edit_controller_refund.getText().toString();
//                String s = ;
//                KLog.i(s);
                Pattern p = Pattern.compile("^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,2})?))$");
                Matcher matcher = p.matcher(money);
                if (!matcher.matches()) {
                    money = Formater.formatMoney(money.replace("元", ""), 2);
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
                } else if (Float.parseFloat(edit_controller_refund.getText().toString()) > Float.parseFloat(edit_money.getText().toString())) {
                    Toast.makeText(this, "退款金额不能大于交易金额！", Toast.LENGTH_SHORT).show();
                } else if (edit_controller_password.getText().toString().equals("")) {
                    Toast.makeText(this, "请输入该用户密码！", Toast.LENGTH_SHORT).show();
                } else if (!edit_order_time.getText().toString().substring(0, 10).equals(cur_time) && !hasRole) {
                    Toast.makeText(this, "非当天退款信息不得退款！", Toast.LENGTH_SHORT).show();
                } else {
                    KLog.i("re=------------");
                    Toast.makeText(this, "退款中,请稍等！", Toast.LENGTH_SHORT).show();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    presenter.refundFee(edit_controller_password.getText().toString(),
                            edit_controller_code.getText().toString(),
                            edit_controller_refund.getText().toString(),
                            RefundInfoActivity.this.money,
                            edit_sys_no.getText().toString(), pay_type, transaction_id_list);
                }
            } else {
                KLog.i("re-click");
            }

        }
    }

    @Override
    public void closeActionSheet() {
        bottomSheetDialog.dismiss();
    }

    @Override
    public void refreshData(String total_FEE, String status, String refund, String refund_count) {
        edit_money.setText(total_FEE);
        edit_refund.setText(refund);
        refundCount.setText(refund_count);
        KLog.i(status);
        feeRelase.setText(status);
        initButton(hasRole, status, total_FEE);
    }

    @Override
    public void changStatus() {
        status_controller.setText(this.getResources().getString(R.string.refunded_status));
        status_background.setBackground(this.getResources().getDrawable(R.drawable.status_refunded));
        status_controller.setOnClickListener(null);
        this.flag = 1;
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
    public void init(boolean hasRole, boolean b) {
        if (!b) {
            Toast.makeText(this, "获取信息失败,请返回重试", Toast.LENGTH_SHORT).show();
            this.finish();
        } else {
            this.hasRole = hasRole;
            initButton(hasRole, fee, money);
        }
    }

    public void initButton(boolean hasRole, String fee, String money) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String cur_time = formatter.format(curDate);
        if (!hasRole && !edit_order_time.getText().toString().substring(0, 10).equals(cur_time)) {
            KLog.i("123");
            KLog.i(fee+"+"+money);
            if (Double.parseDouble(fee)==0){
                status_controller.setText(context.getResources().getString(R.string.refunded_status));
                status_background.setBackground(context.getResources().getDrawable(R.drawable.status_refunded));
                status_controller.setOnClickListener(null);
            }else{
                status_controller.setText(context.getResources().getString(R.string.unrefund_error));
                status_background.setBackground(context.getResources().getDrawable(R.drawable.status_refund_cannot));
                status_controller.setOnClickListener(null);
            }
        } else if (Double.parseDouble(fee) == Double.parseDouble(money)) {
            status_controller.setText(context.getResources().getString(R.string.unrefund_status));
            status_background.setBackground(context.getResources().getDrawable(R.drawable.unrefund_controller_background));
            status_controller.setOnClickListener(RefundInfoActivity.this);
        } else if (Double.parseDouble(fee) < Double.parseDouble(money) &&
                Double.parseDouble(fee) > 0) {
            status_controller.setText(context.getResources().getString(R.string.unrefund_step));
            status_background.setBackground(context.getResources().getDrawable(R.drawable.unrefund_controller_background));
            status_controller.setOnClickListener(RefundInfoActivity.this);
        } else {
            status_controller.setText(context.getResources().getString(R.string.refunded_status));
            status_background.setBackground(context.getResources().getDrawable(R.drawable.status_refunded));
            status_controller.setOnClickListener(null);
        }
    }

    @Override
    public void setPresenter(MerchRefundInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(edit_sys_no.getWindowToken(), 0);
        }
    }
}
