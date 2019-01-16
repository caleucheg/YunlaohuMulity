package com.yang.yunwang.home.mainhome.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kw.rxbus.RxBus;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.busevent.OrderFilterEvent;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.ui.ClearEditText;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.DateTimePickDialogUtil;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.base.util.utils.EditTextLengthFilter;
import com.yang.yunwang.home.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Route(path = IHomeProvider.HOME_ACT_ORDER_FILTER)
public class NewOrderFilterActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    /**
     * 1345654654654654644464674
     */
    private ClearEditText mEtOrderNum;
    /**
     * 商户登录名
     */
    private ClearEditText mEtLoginName;
    /**
     * 商户名称
     */
    private ClearEditText mEtCustomerName;
    private LinearLayout mLlWxIcon;
    private LinearLayout mLlZfbIcon;
    /**
     * 请选择时间
     */
    private EditText mEditOrderStartTime;
    private ImageView mImageClearStarttime;
    /**
     * 请选择时间
     */
    private EditText mEditOrderEndTime;
    private ImageView mImageClearEndtime;
    /**
     * 微信
     */
    private TextView mTvWx;
    /**
     * 支付宝
     */
    private TextView mTvZfb;
    /**
     * 查 询
     */
    private Button mBtnSearch;
    private EditText common_editext;
    private DateTimePickDialogUtil dateTimePicKDialog;
    private ImageView iv_wx_icon;
    private ImageView iv_zfb_icon;
    private boolean isWxSel = false;
    private boolean isZfbSel = false;
    private SimpleDateFormat sdf;
    private LinearLayout ll_login;
    private LinearLayout ll_display;
    private boolean isFromArouter;
    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_filter);
        setTitleText("订单筛选");
        initView();


    }


    private void initView() {
        mEtOrderNum = (ClearEditText) findViewById(R.id.et_order_num);
        mEtLoginName = (ClearEditText) findViewById(R.id.et_login_name);
        mEtCustomerName = (ClearEditText) findViewById(R.id.et_customer_name);
        mLlWxIcon = (LinearLayout) findViewById(R.id.ll_wx_icon);
        mLlWxIcon.setOnClickListener(this);
        mLlZfbIcon = (LinearLayout) findViewById(R.id.ll_zfb_icon);
        mLlZfbIcon.setOnClickListener(this);
        mEditOrderStartTime = (EditText) findViewById(R.id.edit_order_start_time);
        mImageClearStarttime = (ImageView) findViewById(R.id.image_clear_starttime);
        mEditOrderEndTime = (EditText) findViewById(R.id.edit_order_end_time);
        mImageClearEndtime = (ImageView) findViewById(R.id.image_clear_endtime);
        mTvWx = (TextView) findViewById(R.id.tv_wx);
        mTvZfb = (TextView) findViewById(R.id.tv_zfb);
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        //获取日期
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = sdf.format(calendar.getTime());
        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
        mEditOrderStartTime.setText(dateStart);
        mEditOrderEndTime.setText(dateEnd);
        mBtnSearch = (Button) findViewById(R.id.btn_search);
        mBtnSearch.setOnClickListener(this);
        initListener();
        iv_wx_icon = (ImageView) findViewById(R.id.iv_wx_icon);
        iv_zfb_icon = (ImageView) findViewById(R.id.iv_zfb_icon);
        ll_login = (LinearLayout) findViewById(R.id.ll_login);
        ll_display = (LinearLayout) findViewById(R.id.ll_display);
        isFromArouter = getIntent().getBooleanExtra("isFromArouter", false);
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

        EditTextLengthFilter editTextLengthFilter = new EditTextLengthFilter(this, 25, "");
        InputFilter[] filters25 = {editTextLengthFilter, specialCharFilter};
        mEtCustomerName.setFilters(filters25);
        EditTextLengthFilter editTextLengthFilter35 = new EditTextLengthFilter(this, 35, "");
        InputFilter[] filters35 = {editTextLengthFilter35, specialCharFilter};
        mEtOrderNum.setFilters(filters35);
        EditTextLengthFilter editTextLengthFilter11 = new EditTextLengthFilter(this, 11, "");
        InputFilter[] filters11 = {editTextLengthFilter11, specialCharFilter};

        if (isFromArouter) {
            String userType = ConstantUtils.NEW_TYPE;
            if (TextUtils.equals("0", userType)) {
                mEtLoginName.setHint("员工登录名");
                mEtLoginName.setFilters(filters25);
                mEtCustomerName.setHint("员工名称");
            } else if (TextUtils.equals("1", userType)) {
                ll_display.setVisibility(View.GONE);
                ll_login.setVisibility(View.GONE);
            } else if (TextUtils.equals("2", userType)) {
                mEtLoginName.setHint("员工登录名");
                mEtLoginName.setFilters(filters25);
                mEtCustomerName.setHint("员工名称");
            } else if (TextUtils.equals("3", userType)) {
                ll_display.setVisibility(View.GONE);
                ll_login.setVisibility(View.GONE);
            } else {
                mEtLoginName.setFilters(filters11);
                mEtLoginName.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
        } else {
            String userType = ConstantUtils.NEW_TYPE;
            if (TextUtils.equals("0", userType)) {
                mEtLoginName.setFilters(filters11);
                mEtLoginName.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (TextUtils.equals("1", userType)) {
                mEtLoginName.setHint("员工登录名");
                mEtLoginName.setFilters(filters25);
                mEtCustomerName.setHint("员工名称");
            } else if (TextUtils.equals("2", userType)) {
                mEtLoginName.setFilters(filters11);
                mEtLoginName.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (TextUtils.equals("3", userType)) {
                ll_display.setVisibility(View.GONE);
                ll_login.setVisibility(View.GONE);
            } else {
                mEtLoginName.setFilters(filters11);
                mEtLoginName.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
        }

    }

    private void initListener() {
        mEditOrderStartTime.setOnTouchListener(this);
        mEditOrderEndTime.setOnTouchListener(this);
        mImageClearStarttime.setOnClickListener(this);
        mImageClearEndtime.setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_wx_icon) {
            if (isWxSel) {
                clickWx(false);
            } else {
                clickWx(true);
                clickZfb(false);
            }
        } else if (i == R.id.ll_zfb_icon) {
            if (isZfbSel) {
                clickZfb(false);
            } else {
                clickZfb(true);
                clickWx(false);
            }
        } else if (i == R.id.btn_search) {
            String startTime = mEditOrderStartTime.getText().toString().trim();
            String endTime = mEditOrderEndTime.getText().toString().trim();
            int days = 0;
            boolean erTime = false;
            try {
                Date startDate = sdf.parse(startTime);
                Date endDate = sdf.parse(endTime);
                long dateTime = endDate.getTime();
                long startDateTime = startDate.getTime();
                erTime = startDateTime > dateTime;
                days = (int) ((dateTime - startDateTime) / (1000 * 60 * 60 * 24));
                KLog.i(days);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String orderNum = mEtOrderNum.getText().toString().trim();
            String cusLoginName = mEtLoginName.getText().toString().trim();
            String cusName = mEtCustomerName.getText().toString().trim();
            String tradeType = "";
            if (isWxSel) {
                tradeType = "WX";
            } else if (isZfbSel) {
                tradeType = "AliPay";
            } else {
                tradeType = "";
            }
            if (erTime) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("起始时间大于结束时间,请重新选择");
                dialog.setPositiveButton(this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            } else if (days > 30) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("最多可支持查询31天数据");
                dialog.setPositiveButton(this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            } else {

                if (NetStateUtils.isNetworkConnected(NewOrderFilterActivity.this)) {
                    if (ConstantUtils.Order_order_search || TextUtils.equals("0", ConstantUtils.NEW_TYPE) || isFromArouter) {
                        OrderFilterEvent event = new OrderFilterEvent(orderNum, cusLoginName, cusName, tradeType, startTime, endTime, isFromArouter);
                        RxBus.getInstance().send(event);
                        this.finish();
                    } else {
                        Toast.makeText(this, "该用户没有订单查询权限", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(NewOrderFilterActivity.this);
                    dialog.setMessage("网络连接异常，请检查您的手机网络");
                    dialog.setPositiveButton(NewOrderFilterActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }

            }
        } else if (i == R.id.image_back) {
            this.finish();
//            if (ConstantUtils.Order_order_search || TextUtils.equals("0", ConstantUtils.NEW_TYPE)||isFromArouter) {
//                KLog.i("NewOrderFilterActivity.finish");
//                RxBus.getInstance().send(new OrderRefreshEvent(true,"NewOrderFilterActivity",isFromArouter));
//            }else {
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "该用户没有订单查询权限", Toast.LENGTH_SHORT).show();
//                    }
//                }, 500);
//
//            }

        } else {

        }
    }

    private void clickWx(boolean select) {
        if (select) {
            mTvWx.setTextColor(Color.BLACK);
            iv_wx_icon.setImageResource(R.drawable.order_filter_wx_se);
            isWxSel = true;
        } else {
            mTvWx.setTextColor(Color.GRAY);
            iv_wx_icon.setImageResource(R.drawable.order_filter_wx_un);
            isWxSel = false;
        }
    }

    private void clickZfb(boolean select) {
        if (select) {
            mTvZfb.setTextColor(Color.BLACK);
            iv_zfb_icon.setImageResource(R.drawable.order_filter_zfb_se);
            isZfbSel = true;
        } else {
            mTvZfb.setTextColor(Color.GRAY);
            iv_zfb_icon.setImageResource(R.drawable.order_filter_zfb_un);
            isZfbSel = false;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            long nowClickTime = System.currentTimeMillis();
            int i1 = 500;
            KLog.i((nowClickTime - lastClickTime) > i1);
            if ((nowClickTime - lastClickTime) > i1) {
                KLog.i(nowClickTime - lastClickTime);
                lastClickTime = System.currentTimeMillis();
                int i = v.getId();
                if (i == R.id.edit_order_start_time) {
                    showDate(R.id.edit_order_start_time);
                } else if (i == R.id.edit_order_end_time) {
                    showDate(R.id.edit_order_end_time);
                }
            } else {
                KLog.i(nowClickTime - lastClickTime);
            }
        }
        return true;
    }

    private void showDate(int id) {
        common_editext = (EditText) findViewById(id);
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        //获取日期
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (dateTimePicKDialog == null) {
            dateTimePicKDialog = new DateTimePickDialogUtil(
                    this, common_editext.getText().toString().trim());
            dateTimePicKDialog.dateTimePicKDialog(common_editext, common_editext.getText().toString().trim());
            int i = common_editext.getId();
            if (i == R.id.edit_order_start_time) {
//                mImageClearStarttime.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_order_end_time) {
//                mImageClearEndtime.setVisibility(View.VISIBLE);

            }
        } else {
            dateTimePicKDialog.dateTimePicKDialog(common_editext, common_editext.getText().toString().trim());
            int i = common_editext.getId();
            if (i == R.id.edit_order_start_time) {
//                mImageClearStarttime.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_order_end_time) {
//                mImageClearEndtime.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            if (ConstantUtils.Order_order_search || TextUtils.equals("0", ConstantUtils.NEW_TYPE)||isFromArouter) {
//                KLog.i("NewOrderFilterActivity.finish");
//                RxBus.getInstance().send(new OrderRefreshEvent(true,"NewOrderFilterActivity",isFromArouter));
//            }else {
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "该用户没有订单查询权限", Toast.LENGTH_SHORT).show();
//                    }
//                }, 500);
//            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
