package com.yang.yunwang.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

/**
 * on 2017/3/21.
 * <p>
 * description：
 * update by:
 * update day:
 */
public class BaseActivity extends AppCompatActivity {

    //    private Unbinder unbinder;
    private RelativeLayout llRoot;
    private RelativeLayout llContent;
    private FrameLayout frame_homebar;
    private TextView text_title;
    private ImageView image_back;
    private ImageView image_home;
    private TextView comfirm_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.layout_title);
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.blue_color));
        text_title = (TextView) findViewById(R.id.text_order_search_title);
        image_back = (ImageView) findViewById(R.id.image_back);
        llRoot = (RelativeLayout) findViewById(R.id.ll_basetitle_root);
        llContent = (RelativeLayout) findViewById(R.id.rel_content);
        image_home = (ImageView) findViewById(R.id.image_home);
        frame_homebar = (FrameLayout) findViewById(R.id.frame_homebar);
        comfirm_button = (TextView) findViewById(R.id.btn_home_comfirm);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        lp.addRule(RelativeLayout.BELOW, R.id.rel_order_search_header);
        if (null != llContent)
            llContent.addView(view, lp);
    }

    /**
     * 设置中间标题文字
     *
     * @param c
     */
    public void setTitleText(CharSequence c) {
        if (text_title != null)
            text_title.setText(c);
    }

    /**
     * 设置中间标题文字
     *
     * @param resId
     */
    public void setTitleText(int resId) {
        if (text_title != null)
            text_title.setText(resId);
    }

    public void setHomeBarVisisble(boolean show) {
        if (frame_homebar != null) {
            if (show) {
                frame_homebar.setVisibility(View.VISIBLE);
            } else {
                frame_homebar.setVisibility(View.GONE);
            }
        }
    }

    public void setHomeComfirmVisisble(boolean show) {
        if (comfirm_button != null) {
            if (show) {
                comfirm_button.setVisibility(View.VISIBLE);
            } else {
                comfirm_button.setVisibility(View.GONE);
            }
        }
    }

    public TextView getComfirmBack() {
        return comfirm_button;
    }

    public ImageView getLlBasetitleBack() {
        return image_back;
    }

    public ImageView getLlBasehomeBack() {
        return image_home;
    }

    @Override
    protected void onPause() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            if (this.getCurrentFocus() != null) {
                if (this.getCurrentFocus().getWindowToken() != null) {
                    inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
                }
            }
        }
        super.onPause();
    }
}
