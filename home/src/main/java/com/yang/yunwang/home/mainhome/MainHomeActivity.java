package com.yang.yunwang.home.mainhome;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jaeger.library.StatusBarUtil;
import com.joker.api.Permissions4M;
import com.joker.api.wrapper.ListenerWrapper;
import com.joker.api.wrapper.Wrapper;
import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.view.adapter.CommonFragmentPagerAdapter;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.contract.MainhomeContract;
import com.yang.yunwang.home.mainhome.presenter.MainhomePresenter;

import java.util.List;

@Route(path = IHomeProvider.HOME_ACT_HOME)
public class MainHomeActivity extends AppCompatActivity implements MainhomeContract.View {

    private static final int READ_CONTACTS_CODE = 1;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ProgressDialog progressDialog;
    private MyBroadcast broadcastReceiver;
    private MainhomeContract.Presenter mainHomePresenter;
    private boolean isRecivePush = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_staffhome);
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(com.yang.yunwang.base.R.color.blue_color));
        init();
        mainHomePresenter.getServiceVersionCode();
        mainHomePresenter.checkPassWord();
        KLog.i("Login_type", ConstantUtils.CUSTOMERS_TYPE + "----" + ConstantUtils.STAFF_TYPE);
        initData();
        initListener();
    }

    private void init() {
        viewPager = (ViewPager) findViewById(R.id.viewpager_staff);
        tabLayout = (TabLayout) findViewById(R.id.tab_staff);
        new MainhomePresenter(this, this);
        broadcastReceiver = new MyBroadcast();
        IntentFilter filter = new IntentFilter("com.yunwang.temp");
        registerReceiver(broadcastReceiver, filter);
        mainHomePresenter.initData();
    }

    private void initData() {
        mainHomePresenter.initData();
    }

    private void initListener() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                boolean fwsyg = TextUtils.equals(ConstantUtils.NEW_TYPE, "2");
                boolean shyg = TextUtils.equals(ConstantUtils.NEW_TYPE, "3");
                int res;
                int resUid;
                KLog.i("type--" + ConstantUtils.NEW_TYPE + "--" + fwsyg + "---" + shyg);
                if (fwsyg) {
                    res = R.drawable.home_a_merch_s;
                    resUid = R.drawable.home_a_merch_u;
                } else if (shyg) {
                    res = R.drawable.home_a_refund_s;
                    resUid = R.drawable.home_a_refund_u;
                } else {
                    res = R.drawable.home_a_staff_s;
                    resUid = R.drawable.home_a_staff_u;
                }
                switch (tab.getPosition()) {
                    case 0:
                        if (!TextUtils.isEmpty(ConstantUtils.HIGHER_SYS_NO) && shyg) {
                            mainHomePresenter.getPayConfig(ConstantUtils.HIGHER_SYS_NO);
                        }
                        tab.setIcon(R.drawable.staff_tab_home_selected);
                        tabLayout.getTabAt(1).setIcon(R.drawable.home_a_order_u);
                        tabLayout.getTabAt(2).setIcon(resUid);
                        tabLayout.getTabAt(3).setIcon(R.drawable.staff_tab_mine_unselect);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.home_a_order_s);
                        tabLayout.getTabAt(0).setIcon(R.drawable.staff_tab_home_unselect);
                        tabLayout.getTabAt(2).setIcon(resUid);
                        tabLayout.getTabAt(3).setIcon(R.drawable.staff_tab_mine_unselect);
                        break;
                    case 2:
                        if (!TextUtils.isEmpty(ConstantUtils.HIGHER_SYS_NO) && shyg) {
                            mainHomePresenter.getPayConfig(ConstantUtils.HIGHER_SYS_NO);
                        }
                        tab.setIcon(res);
                        tabLayout.getTabAt(0).setIcon(R.drawable.staff_tab_home_unselect);
                        tabLayout.getTabAt(1).setIcon(R.drawable.home_a_order_u);
                        tabLayout.getTabAt(3).setIcon(R.drawable.staff_tab_mine_unselect);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.staff_tab_mine_selected);
                        tabLayout.getTabAt(0).setIcon(R.drawable.staff_tab_home_unselect);
                        tabLayout.getTabAt(1).setIcon(R.drawable.home_a_order_u);
                        tabLayout.getTabAt(2).setIcon(resUid);
                        break;
                }
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                KLog.i("info", "position====>" + position);
            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
                Intent intent = new Intent("pageChange");
                intent.putExtra("position", position);
                LocalBroadcastManager.getInstance(MainHomeActivity.this)
                        .sendBroadcast(intent);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void setAdapter(List<String> tab_list, int[] tab_res, List<Fragment> view_list) {
        CommonFragmentPagerAdapter commonFragmentPagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), view_list, tab_list);
        viewPager.setAdapter(commonFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < view_list.size(); i++) {
            tabLayout.getTabAt(i).setIcon(tab_res[i]);
        }
        tabLayout.getTabAt(0).setIcon(R.drawable.staff_tab_home_selected);
    }

    @Override
    public void showDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle(this.getResources().getString(R.string.alert_title));
        progressDialog.setMessage(this.getResources().getString(R.string.orders_search_waitting));
        if (progressDialog != null && this.hasWindowFocus()) {
            progressDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void parseMineFragment() {
        if (viewPager != null) {
            viewPager.setCurrentItem(3, true);
        }
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
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void showAlert() {
        KLog.i("req_per");
        showAlertTest();
//        showAlertOld();
    }

    @Override
    public void setPresenter(MainhomeContract.Presenter presenter) {
        this.mainHomePresenter = presenter;
    }

    @Override
    public void finishAct() {
        this.finish();
    }

    @Override
    public void jumpLoginPage() {
//
        ARouter.getInstance().build(IHomeProvider.HOME_ACT_LOGIN).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK).navigation();
//        Intent intent = new Intent(context, LoginActivity.class);
//        context.startActivity(intent);
        this.finish();
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        activity.startActivity(intent);

    }


    private void showAlertTest() {
        Permissions4M.get(MainHomeActivity.this)
                .requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .requestCodes(READ_CONTACTS_CODE)
                .requestListener(new ListenerWrapper.PermissionRequestListener() {
                    @Override
                    public void permissionGranted(int code) {
                        KLog.i("读写文件权限成功 in activity with listener");
                        CommonShare.putPermissionBoolean(MainHomeActivity.this, true);
                    }

                    @Override
                    public void permissionDenied(int code) {
                        KLog.i(code);
                        KLog.i("读写文件权失败 in activity with listener");
                    }

                    @Override
                    public void permissionRationale(int code) {
                        KLog.i("请打开读写文件权限 in activity with listener");
                    }
                })
                .requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
                .requestPage(new Wrapper.PermissionPageListener() {
                    @Override
                    public void pageIntent(int code, final Intent intent) {
                        new AlertDialog.Builder(MainHomeActivity.this)
                                .setMessage("我们需要您开启读写文件权限申请：\n请点击前往设置页面\n")
                                .setPositiveButton("前往设置页面", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                })
                .request();
        KLog.i("--");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Permissions4M.onRequestPermissionsResult(MainHomeActivity.this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public class MyBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            intent.getBooleanExtra("", false);
            if(!DycLibIntent.hasModule()){
                if (!isRecivePush) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainHomeActivity.this);
                    builder.setTitle("连接推送失败");
                    builder.setMessage("连接推送失败,为了避免影响正常使用,请重新尽快重新登录");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            KLog.i("click-sure");
                            MainHomeActivity.this.isRecivePush = true;
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }else {
                MainHomeActivity.this.isRecivePush = true;
            }

        }
    }
}
