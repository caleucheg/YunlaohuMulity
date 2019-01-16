package com.yang.yunwang.home.loginpage;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jaeger.library.StatusBarUtil;
import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.ui.SlidingTabLayout;
import com.yang.yunwang.base.ui.WrapContentHeightViewPager;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.NetStateUtils;
import com.yang.yunwang.base.view.adapter.LoginViewPagerAdapter;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.loginpage.contract.LoginPageContract;
import com.yang.yunwang.home.loginpage.presenter.LoginPagePresenter;

import java.util.ArrayList;
import java.util.List;

@Route(path = IHomeProvider.HOME_ACT_LOGIN)
public class LoginActivity extends Activity implements LoginPageContract.View, View.OnClickListener {

    //    private TabLayout tabLayout;
    private LoginPageContract.Presenter loginPresenter;
    private WrapContentHeightViewPager viewPager;
    private Button btn_login;
    private LoginViewPagerAdapter loginViewPagerAdapter;
    private int position = 0;
    private ProgressDialog progressDialog;
    private SlidingTabLayout tabLayout;
    private CheckBox cb_remember_p;
    private boolean isRem;
    private int pos;
    private String password;
    private String user;

    private AppCompatCheckBox cb_remember_p1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, getResources().getColor(com.yang.yunwang.base.R.color.white_color), 100);
        setContentView(R.layout.layout_login);
        isRem = CommonShare.getBooleanRememberPwd(this);
        pos = CommonShare.getRememberPos(this);
        new LoginPagePresenter(this, this);
        if (DycLibIntent.hasModule()) {
            ImageView logo = (ImageView) findViewById(R.id.imageView_logo);
            logo.setImageResource(R.drawable.d_login_logo);
        }
        initUI();
        initAdapter();
        initListener();
    }

    private void initUI() {
        tabLayout = (SlidingTabLayout) findViewById(R.id.tab_login);
        viewPager = (WrapContentHeightViewPager) findViewById(R.id.viewpager_login);
        btn_login = (Button) findViewById(R.id.btn_login);
        cb_remember_p = (AppCompatCheckBox) findViewById(R.id.cb_remember_p);
        cb_remember_p1 = (AppCompatCheckBox) findViewById(R.id.cb_remember_p1);
        KLog.i(isRem + "----" + pos);
        if (isRem && pos == 0) {
            cb_remember_p.setChecked(true);
            cb_remember_p.setVisibility(View.VISIBLE);
            cb_remember_p1.setVisibility(View.GONE);
        }
        if (isRem && pos == 1) {
            cb_remember_p1.setChecked(true);
            cb_remember_p1.setVisibility(View.VISIBLE);
            cb_remember_p.setVisibility(View.GONE);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LoginActivity.this.position = position;
                if (position == 0) {
                    cb_remember_p.setVisibility(View.VISIBLE);
                    cb_remember_p1.setVisibility(View.GONE);
                }
                if (position == 1) {
                    cb_remember_p1.setVisibility(View.VISIBLE);
                    cb_remember_p.setVisibility(View.GONE);
                }
//                if (pos == position) {
//                    if (isRem) {
//                        cb_remember_p.setChecked(true);
//                    } else {
//                        cb_remember_p.setChecked(false);
//
//                    }
//                } else {
//                        cb_remember_p.setChecked(false);
//
//
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initAdapter() {
        loginPresenter.initData();
        loginPresenter.setAdapter();
        KLog.i(2 + "posssssss");
    }

    @Override
    public void setDataAdapter(List<String> tab_list, List<View> view_list) {
        loginViewPagerAdapter = new LoginViewPagerAdapter(this, view_list, tab_list);
        viewPager.setAdapter(loginViewPagerAdapter);
        ArrayList<String> datas = new ArrayList<>();
        datas.add("服务商/商户");
        datas.add("员工");
        tabLayout.setData(datas);
        tabLayout.setVisibleTabCount(2);
        if (pos == 0 || pos == 1) {
            tabLayout.setViewPager(viewPager, pos);
        } else {
            tabLayout.setViewPager(viewPager, 0);
        }

        KLog.i(3 + "posssssss");
//        for (int i = 0; i < view_list.size(); i++) {
//            tabLayout.getTabAt(i).setCustomView(getTabCustomView(i));
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        long lastLoginTime = CommonShare.getLastLoginTime(this);
        long nowTime = System.currentTimeMillis();
        int days = (int) ((nowTime - lastLoginTime) / (1000 * 60 * 60 * 24));
        KLog.i(days);
        if (days > 6) {
            CommonShare.clearLogin(this);
            viewPager.setCurrentItem(0);
            cb_remember_p.setChecked(false);
            cb_remember_p1.setChecked(false);
            isRem = false;

        }
        KLog.i(6 + "pspssssssssssss");
    }

    @Override
    public void showDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.login_waiting));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private View getTabCustomView(int position) {
        View view;
        if (position == 0) {
            LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
            view = inflater.inflate(R.layout.tab_item_login_left, null);
        } else {
            LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
            view = inflater.inflate(R.layout.tab_item_login_right, null);
        }
        return view;
    }

    private void initListener() {
        btn_login.setOnClickListener(this);
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 0) {
//                    LinearLayout tab_left = (LinearLayout) tab.getCustomView().findViewById(R.id.linear_tab_left);
//                    TextView tab_title_left = (TextView) tab.getCustomView().findViewById(R.id.tab_title_left);
//                    tab_title_left.setTextColor(getResources().getColor(R.color.blue_color));
//                    tab_left.setBackground(getResources().getDrawable(R.drawable.login_tab_left));
//                } else {
//                    LinearLayout tab_right = (LinearLayout) tab.getCustomView().findViewById(R.id.linear_tab_right);
//                    TextView tab_title_right = (TextView) tab.getCustomView().findViewById(R.id.tab_title_right);
//                    tab_title_right.setTextColor(getResources().getColor(R.color.blue_color));
//                    tab_right.setBackground(getResources().getDrawable(R.drawable.login_tab_right));
//                }
//                position = tab.getPosition();
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 0) {
//                    LinearLayout tab_left = (LinearLayout) tab.getCustomView().findViewById(R.id.linear_tab_left);
//                    TextView tab_title_left = (TextView) tab.getCustomView().findViewById(R.id.tab_title_left);
//                    tab_title_left.setTextColor(getResources().getColor(R.color.white_color));
//                    tab_left.setBackground(null);
//                } else {
//                    LinearLayout tab_right = (LinearLayout) tab.getCustomView().findViewById(R.id.linear_tab_right);
//                    TextView tab_title_right = (TextView) tab.getCustomView().findViewById(R.id.tab_title_right);
//                    tab_title_right.setTextColor(getResources().getColor(R.color.white_color));
//                    tab_right.setBackground(null);
//                }
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//        初始化tab显示
//        LinearLayout tab_left = (LinearLayout) tabLayout.getTabAt(0).getCustomView().findViewById(R.id.linear_tab_left);
//        TextView tab_title_left = (TextView) tabLayout.getTabAt(0).getCustomView().findViewById(R.id.tab_title_left);
//        tab_title_left.setTextColor(getResources().getColor(R.color.blue_color));
//        tab_left.setBackground(getResources().getDrawable(R.drawable.login_tab_left));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_login) {
            if (NetStateUtils.isNetworkConnected(LoginActivity.this)) {
                Object[] data = loginViewPagerAdapter.getData(position);
                String user = data[0].toString();
                String password = data[1].toString();
                KLog.i(user + "____" + password);
                if (user.equals("")) {
                    Toast.makeText(this, "请输入用户名！", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
                } else if (!user.equals("") && !password.equals("") && position != -1) {
                    LoginActivity.this.password = password;
                    LoginActivity.this.user = user;
//                    if (checked) {
////                        CommonShare.putRememberPwd(LoginActivity.this, password);
//                    }
                    if (NetStateUtils.isNetworkConnected(LoginActivity.this)) {
                        loginPresenter.login(user, password, position);
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                        dialog.setMessage("网络连接异常，请检查您的手机网络");
                        dialog.setPositiveButton(LoginActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                }
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                dialog.setMessage("网络连接异常，请检查您的手机网络");
                dialog.setPositiveButton(LoginActivity.this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }
    }

    @Override
    public void setPresenter(LoginPageContract.Presenter presenter) {
        loginPresenter = presenter;
    }

    @Override
    public void loginOnSuccess() {

        boolean checked;
        if (position == 0) {
            checked = cb_remember_p.isChecked();
        } else if (position == 1) {
            checked = cb_remember_p1.isChecked();
        } else {
            checked = false;
        }
        CommonShare.putBooleanRememberPwd(LoginActivity.this, checked);
        CommonShare.putLastLoginTime(this, System.currentTimeMillis());

        CommonShare.putRememberPos(LoginActivity.this, position);
        CommonShare.putRememberName(LoginActivity.this, user);
        if (checked) {
            CommonShare.putRememberPwd(LoginActivity.this, password);
        }
        ConstantUtils.IS_ATFER_LOGIN_INIT = true;
//        Intent intent = new Intent(this, MainHomeActivity.class);
//        this.startActivity(intent);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissDialog();
                HomeIntent.launchHomePage();
                LoginActivity.this.finish();
            }
        }, 800);

    }

    @Override
    public void loginOnError(boolean isOutTime) {
        String string;
        if (isOutTime) {
            string = this.getResources().getString(R.string.alert_login_time_long);
        } else {
            string = this.getResources().getString(R.string.alert_login_error);
        }
        showDialog(string);
    }

    @Override
    public void loginOnNetworkTimeLong() {
        showDialog(this.getResources().getString(R.string.alert_login_time_long));
    }

    @Override
    public void checkLoginDialog() {
        showDialog(this.getResources().getString(R.string.alert_message));
    }

    private void showDialog(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(this.getResources().getString(R.string.alert_title));
        dialog.setMessage(message);
        dialog.setPositiveButton(this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
            dialog.setTitle(this.getResources().getString(R.string.alert_title));
            //Done switch version
            if (DycLibIntent.hasModule()) {
                dialog.setMessage(this.getResources().getString(R.string.dexit));
            } else {
                dialog.setMessage(this.getResources().getString(R.string.exit));
            }
            dialog.setPositiveButton(this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    LoginActivity.this.finish();
                    System.exit(0);
                }
            });
            dialog.setNegativeButton(this.getResources().getString(R.string.alert_native), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
