package com.yang.yunwang.home.mainhome.model.merchant;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.bean.MerchantBean;
import com.yang.yunwang.home.mainhome.model.merchant.intf.MerchantModelInterface;
import com.yang.yunwang.home.mainhome.view.activity.MerchantHomeActivity;
import com.yang.yunwang.home.mainhome.view.activity.MineActivity;
import com.yang.yunwang.home.mainhome.view.activity.NewTestActivity;
import com.yang.yunwang.home.mainhome.view.activity.ReportFromActivity;

import java.util.ArrayList;
import java.util.List;

public class MerchantModel implements MerchantModelInterface {

    private final LocalActivityManager manager;
    private MerchantBean merchantBean;
    private Context context;

    public MerchantModel(Context context, LocalActivityManager manager) {
        this.context = context;
        this.manager = manager;
        merchantBean = new MerchantBean();
    }

    @Override
    public void initTabList() {
        String[] lists = context.getResources().getStringArray(R.array.staff_tab_list_fs);
        int[] reses = new int[]{R.drawable.staff_tab_home_unselect, R.drawable.home_a_order_u,
                R.drawable.home_a_staff_u, R.drawable.staff_tab_mine_unselect};
        List<String> tab_list = new ArrayList<String>();
        for (int i = 0; i < lists.length; i++) {
            tab_list.add(lists[i]);
        }
        merchantBean.setTab_list(tab_list);
        merchantBean.setTab_res(reses);
    }

    @Override
    public void initViewList() {
//        MerchantHomeFragment homeFragment = new MerchantHomeFragment();
////        MerchantSignFragment signFragment = new MerchantSignFragment();
////        NewOrderFramgent signFragment=new NewOrderFramgent();
//        FixNewOrderFramgent signFragment=new FixNewOrderFramgent();
//        MerchantLocationFragment locationFragment = new MerchantLocationFragment();
//        MineFragment mineFragment = new MineFragment();
//        view_list.add(homeFragment);
//        view_list.add(signFragment);
//        view_list.add(locationFragment);
//        view_list.add(mineFragment);

        List<android.view.View> view_list = new ArrayList<android.view.View>();
        initPager(view_list);
        merchantBean.setView_list(view_list);
    }

    private void initPager(List<View> tab_list) {
        Intent merchentHome = new Intent(context, MerchantHomeActivity.class);
        tab_list.add(getViews("merchentHome", merchentHome));
        Intent newOrder = new Intent(context, NewTestActivity.class);
        tab_list.add(getViews("newOrder", newOrder));
        Intent reportFrom = new Intent(context, ReportFromActivity.class);
        tab_list.add(getViews("reportFrom", reportFrom));
        Intent mines = new Intent(context, MineActivity.class);
        tab_list.add(getViews("mines", mines));

    }

    private View getViews(String ids, Intent regist) {
        return manager.startActivity(ids, regist).getDecorView();
    }

    @Override
    public MerchantBean loadInstance() {
        if (merchantBean != null) {
            return merchantBean;
        } else {
            return null;
        }
    }
}
