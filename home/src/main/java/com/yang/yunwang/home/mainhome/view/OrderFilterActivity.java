package com.yang.yunwang.home.mainhome.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.ui.ClearEditText;
import com.yang.yunwang.base.util.DateTimePickDialogUtil;
import com.yang.yunwang.home.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@Route(path = IHomeProvider.HOME_ACT_ORDER_FILTER)
public class OrderFilterActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_filter);
        setTitleText(R.string.filter);
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(calendar.getTime());
        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = sdfStart.format(calendar.getTime()) + " 00:00:00";
        mEditOrderStartTime.setText(dateStart);
        mEditOrderEndTime.setText(date);
        mBtnSearch = (Button) findViewById(R.id.btn_search);
        mBtnSearch.setOnClickListener(this);
        initListener();
    }

    private void initListener() {
        mEditOrderStartTime.setOnTouchListener(this);
        mEditOrderEndTime.setOnTouchListener(this);
        mImageClearStarttime.setOnClickListener(this);
        mImageClearEndtime.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_wx_icon) {
            mTvWx.setTextColor(Color.BLACK);
            mTvZfb.setTextColor(Color.GRAY);
        } else if (i == R.id.ll_zfb_icon) {
            mTvZfb.setTextColor(Color.BLACK);
            mTvWx.setTextColor(Color.GRAY);
        } else if (i == R.id.btn_search) {

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
            dateTimePicKDialog.dateTimePicKDialog(common_editext);
            int i = common_editext.getId();
            if (i == R.id.edit_order_start_time) {
                mImageClearStarttime.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_order_end_time) {
                mImageClearEndtime.setVisibility(View.VISIBLE);

            }
        } else {
            dateTimePicKDialog.dateTimePicKDialog(common_editext);
            int i = common_editext.getId();
            if (i == R.id.edit_order_start_time) {
                mImageClearStarttime.setVisibility(View.VISIBLE);

            } else if (i == R.id.edit_order_end_time) {
                mImageClearEndtime.setVisibility(View.VISIBLE);

            }
        }
    }
}
