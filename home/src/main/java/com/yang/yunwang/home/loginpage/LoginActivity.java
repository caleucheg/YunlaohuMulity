package com.yang.yunwang.home.loginpage;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.ui.WrapContentHeightViewPager;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.view.adapter.LoginViewPagerAdapter;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.loginpage.contract.LoginPageContract;
import com.yang.yunwang.home.loginpage.presenter.LoginPagePresenter;

import java.util.List;
@Route(path = IHomeProvider.HOME_ACT_LOGIN)
public class LoginActivity extends Activity implements LoginPageContract.View, View.OnClickListener {

    private TabLayout tabLayout;
    private LoginPageContract.Presenter loginPresenter;
    private WrapContentHeightViewPager viewPager;
    private Button btn_login;
    private LoginViewPagerAdapter loginViewPagerAdapter;
    private int position = 0;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        new LoginPagePresenter(this,this);
        if (DycLibIntent.hasModule()){
            ImageView logo = (ImageView) findViewById(R.id.imageView_logo);
            logo.setImageResource(R.drawable.d_login_logo);
        }
        initUI();
        initAdapter();
        initListener();
    }

    private void initUI() {
        tabLayout = (TabLayout) findViewById(R.id.tab_login);
        viewPager = (WrapContentHeightViewPager) findViewById(R.id.viewpager_login);
        btn_login = (Button) findViewById(R.id.btn_login);

    }

    private void initAdapter() {
        loginPresenter.initData();
        loginPresenter.setAdapter();
    }

    @Override
    public void setDataAdapter(List<String> tab_list, List<View> view_list) {
        loginViewPagerAdapter = new LoginViewPagerAdapter(this, view_list, tab_list);
        viewPager.setAdapter(loginViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < view_list.size(); i++) {
            tabLayout.getTabAt(i).setCustomView(getTabCustomView(i));
        }
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
        if (progressDialog!=null){
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
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    LinearLayout tab_left = (LinearLayout) tab.getCustomView().findViewById(R.id.linear_tab_left);
                    TextView tab_title_left = (TextView) tab.getCustomView().findViewById(R.id.tab_title_left);
                    tab_title_left.setTextColor(getResources().getColor(R.color.blue_color));
                    tab_left.setBackground(getResources().getDrawable(R.drawable.login_tab_left));
                } else {
                    LinearLayout tab_right = (LinearLayout) tab.getCustomView().findViewById(R.id.linear_tab_right);
                    TextView tab_title_right = (TextView) tab.getCustomView().findViewById(R.id.tab_title_right);
                    tab_title_right.setTextColor(getResources().getColor(R.color.blue_color));
                    tab_right.setBackground(getResources().getDrawable(R.drawable.login_tab_right));
                }
                position = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    LinearLayout tab_left = (LinearLayout) tab.getCustomView().findViewById(R.id.linear_tab_left);
                    TextView tab_title_left = (TextView) tab.getCustomView().findViewById(R.id.tab_title_left);
                    tab_title_left.setTextColor(getResources().getColor(R.color.white_color));
                    tab_left.setBackground(null);
                } else {
                    LinearLayout tab_right = (LinearLayout) tab.getCustomView().findViewById(R.id.linear_tab_right);
                    TextView tab_title_right = (TextView) tab.getCustomView().findViewById(R.id.tab_title_right);
                    tab_title_right.setTextColor(getResources().getColor(R.color.white_color));
                    tab_right.setBackground(null);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        初始化tab显示
        LinearLayout tab_left = (LinearLayout) tabLayout.getTabAt(0).getCustomView().findViewById(R.id.linear_tab_left);
        TextView tab_title_left = (TextView) tabLayout.getTabAt(0).getCustomView().findViewById(R.id.tab_title_left);
        tab_title_left.setTextColor(getResources().getColor(R.color.blue_color));
        tab_left.setBackground(getResources().getDrawable(R.drawable.login_tab_left));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_login) {
            Object[] data = loginViewPagerAdapter.getData(position);
            String user = data[0].toString();
            String password = data[1].toString();
            if (user.equals("")) {
                Toast.makeText(this, "请输入用户名！", Toast.LENGTH_SHORT).show();
            } else if (password.equals("")) {
                Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
            } else if (!user.equals("") && !password.equals("") && position != -1) {
                loginPresenter.login(user, password, position);
            }

        }
    }

    @Override
    public void setPresenter(LoginPageContract.Presenter presenter) {
        loginPresenter=presenter;
    }

    @Override
    public void loginOnSuccess() {
        ConstantUtils.IS_ATFER_LOGIN_INIT = true;
//        Intent intent = new Intent(this, MainHomeActivity.class);
//        this.startActivity(intent);
        HomeIntent.launchHomePage();
        this.finish();
    }

    @Override
    public void loginOnError() {
        showDialog(this.getResources().getString(R.string.alert_login_error));
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
            if (DycLibIntent.hasModule()){
                dialog.setMessage(this.getResources().getString(R.string.dexit));
            }else {
                dialog.setMessage(this.getResources().getString(R.string.exit));
            }
            dialog.setPositiveButton(this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
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
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
