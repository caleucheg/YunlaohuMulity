package com.yang.yunwang.home.mainhome.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kw.rxbus.RxBus;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.busevent.CusUserAllcoateEvent;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.util.DateTimePickDialogUtil;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.contract.CusUserAllcoateContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Route(path = IHomeProvider.HOME_ACT_CUS_USER_AL_FILTER)
public class CusUserAllcoateFilterActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    private LinearLayout ll_wx_icon;
    private LinearLayout ll_zfb_icon;
    /**
     * 昨日
     */
    private TextView tv_date_yestoday;
    /**
     * 今日
     */
    private TextView tv_date_today;
    /**
     * 自定义
     */
    private TextView tv_date_switch;
    /**
     * 请选择时间
     */
    private EditText edit_order_start_time;
    /**
     * 请选择时间
     */
    private EditText edit_order_end_time;
    /**
     * 查 询
     */
    private Button btn_search;
    private LinearLayout ll_time_box;
    private CusUserAllcoateContract.Presenter presenter;
    private boolean isWxSel = false;
    private boolean isZfbSel = false;
    private SimpleDateFormat sdf;
    /**
     * 微信
     */
    private TextView tv_wx;
    /**
     * 支付宝
     */
    private TextView tv_zfb;
    private ImageView iv_wx_icon;
    private ImageView iv_zfb_icon;
    private EditText common_editext;
    private DateTimePickDialogUtil dateTimePicKDialog;
    private String timeType = "today";
    private String displayName = "";
    private String loginName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_user_allcoate_filter);
        setTitleText("订单筛选");
        initView();
    }

    private void initView() {
        ll_wx_icon = (LinearLayout) findViewById(R.id.ll_wx_icon);
        ll_wx_icon.setOnClickListener(this);
        ll_zfb_icon = (LinearLayout) findViewById(R.id.ll_zfb_icon);
        ll_zfb_icon.setOnClickListener(this);
        tv_date_yestoday = (TextView) findViewById(R.id.tv_date_yestoday);
        tv_date_yestoday.setOnClickListener(this);
        tv_date_today = (TextView) findViewById(R.id.tv_date_today);
        tv_date_today.setOnClickListener(this);
        tv_date_switch = (TextView) findViewById(R.id.tv_date_switch);
        tv_date_switch.setOnClickListener(this);
        edit_order_start_time = (EditText) findViewById(R.id.edit_order_start_time);
        edit_order_end_time = (EditText) findViewById(R.id.edit_order_end_time);
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        //获取日期
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(calendar.getTime());
        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        String dateEnd = sdfStart.format(calendar.getTime()) + " 23:59:59";
        edit_order_start_time.setText(dateStart);
        edit_order_end_time.setText(dateEnd);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        ll_time_box = (LinearLayout) findViewById(R.id.ll_time_box);
        tv_wx = (TextView) findViewById(R.id.tv_wx);
        tv_zfb = (TextView) findViewById(R.id.tv_zfb);
        iv_wx_icon = (ImageView) findViewById(R.id.iv_wx_icon);
        iv_zfb_icon = (ImageView) findViewById(R.id.iv_zfb_icon);
        getLlBasetitleBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CusUserAllcoateFilterActivity.this.finish();
            }
        });
        edit_order_start_time.setOnTouchListener(this);
        edit_order_end_time.setOnTouchListener(this);
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
        } else if (i == R.id.tv_date_yestoday) {
            ll_time_box.setVisibility(View.GONE);
            timeType = "yestoday";
            tv_date_yestoday.setBackground(getResources().getDrawable(R.drawable.material_button_round_small));
            tv_date_today.setBackground(getResources().getDrawable(R.drawable.small_coner_shape));
            tv_date_switch.setBackground(getResources().getDrawable(R.drawable.small_coner_shape));
            tv_date_yestoday.setTextColor(getResources().getColor(R.color.white_color));
            tv_date_today.setTextColor(getResources().getColor(R.color.blue_color_text));
            tv_date_switch.setTextColor(getResources().getColor(R.color.blue_color_text));
        } else if (i == R.id.tv_date_today) {
            ll_time_box.setVisibility(View.GONE);
            timeType = "today";
            tv_date_today.setBackground(getResources().getDrawable(R.drawable.material_button_round_small));
            tv_date_yestoday.setBackground(getResources().getDrawable(R.drawable.small_coner_shape));
            tv_date_switch.setBackground(getResources().getDrawable(R.drawable.small_coner_shape));
            tv_date_today.setTextColor(getResources().getColor(R.color.white_color));
            tv_date_yestoday.setTextColor(getResources().getColor(R.color.blue_color_text));
            tv_date_switch.setTextColor(getResources().getColor(R.color.blue_color_text));
        } else if (i == R.id.tv_date_switch) {
            ll_time_box.setVisibility(View.VISIBLE);
            timeType = "diy";
            tv_date_switch.setBackground(getResources().getDrawable(R.drawable.material_button_round_small));
            tv_date_yestoday.setBackground(getResources().getDrawable(R.drawable.small_coner_shape));
            tv_date_today.setBackground(getResources().getDrawable(R.drawable.small_coner_shape));
            tv_date_switch.setTextColor(getResources().getColor(R.color.white_color));
            tv_date_yestoday.setTextColor(getResources().getColor(R.color.blue_color_text));
            tv_date_today.setTextColor(getResources().getColor(R.color.blue_color_text));
        } else if (i == R.id.btn_search) {
            String startTime = edit_order_start_time.getText().toString().trim();
            String endTime = edit_order_end_time.getText().toString().trim();
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
//                Toast.makeText(this, "选择区间不能大于31天", Toast.LENGTH_SHORT).show();
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
                if (NetStateUtils.isNetworkConnected(CusUserAllcoateFilterActivity.this)) {
                    CusUserAllcoateEvent event = new CusUserAllcoateEvent(tradeType, timeType, startTime, endTime, displayName, loginName, tradeType);
                    RxBus.getInstance().send(event);
                    this.finish();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CusUserAllcoateFilterActivity.this);
                    dialog.setMessage("网络连接异常，请检查您的手机网络");
                    dialog.setPositiveButton(CusUserAllcoateFilterActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }

            }
        } else {

        }
    }

    private void clickWx(boolean select) {
        if (select) {
            tv_wx.setTextColor(getResources().getColor(R.color.black_color_text));
            iv_wx_icon.setImageResource(R.drawable.order_filter_wx_se);
            isWxSel = true;
        } else {
            tv_wx.setTextColor(getResources().getColor(R.color.grey_color_text_sw));
            iv_wx_icon.setImageResource(R.drawable.order_filter_wx_un);
            isWxSel = false;
        }
    }

    private void clickZfb(boolean select) {
        if (select) {
            tv_zfb.setTextColor(getResources().getColor(R.color.black_color_text));
            iv_zfb_icon.setImageResource(R.drawable.order_filter_zfb_se);
            isZfbSel = true;
        } else {
            tv_zfb.setTextColor(getResources().getColor(R.color.grey_color_text_sw));
            iv_zfb_icon.setImageResource(R.drawable.order_filter_zfb_un);
            isZfbSel = false;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int i = v.getId();
            if (i == R.id.edit_order_start_time) {
                showDate(R.id.edit_order_start_time);

            } else if (i == R.id.edit_order_end_time) {
                showDate(R.id.edit_order_end_time);

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
}
