package com.yang.yunwang.home.mainhome.model.staff;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.bean.StaffBean;
import com.yang.yunwang.home.mainhome.model.staff.inter.StaffModelInterface;
import com.yang.yunwang.home.mainhome.view.activity.MineActivity;
import com.yang.yunwang.home.mainhome.view.activity.NewTestActivity;
import com.yang.yunwang.home.mainhome.view.activity.ReportFromActivity;
import com.yang.yunwang.home.mainhome.view.activity.StaffHomeActivity;

import java.util.ArrayList;
import java.util.List;

public class StaffModel implements StaffModelInterface {

    private final LocalActivityManager manager;
    private StaffBean staffBean;
    private Context context;
    private boolean f = false;

    public StaffModel(Context context, LocalActivityManager manager) {
        f = TextUtils.equals(ConstantUtils.NEW_TYPE, "2");
        staffBean = new StaffBean();
        this.context = context;
        this.manager = manager;
    }

    @Override
    public void initTabList() {
        String[] lists = context.getResources().getStringArray(R.array.staff_tab_list);
        int[] reses = new int[]{R.drawable.staff_tab_home_unselect, R.drawable.home_a_order_u,
                R.drawable.home_a_staff_u, R.drawable.staff_tab_mine_unselect};
        List<String> tab_list = new ArrayList<String>();
        for (int i = 0; i < lists.length; i++) {
            tab_list.add(lists[i]);
        }
        staffBean.setTab_list(tab_list);
        staffBean.setTab_res(reses);
    }

    @Override
    public void initViewList() {
//        StaffHomeFragment homeFragment = new StaffHomeFragment();
//        StaffSignFragment signFragment = new StaffSignFragment();
//        StaffLocationFragment locationFragment = new StaffLocationFragment();
//        StaffRefundFragment refundFragment = new StaffRefundFragment();
//        MineFragment mineFragment = new MineFragment();
        List<View> view_list = new ArrayList<View>();
//        view_list.add(homeFragment);
//        view_list.add(signFragment);
//        view_list.add(f ? locationFragment : refundFragment);
//        view_list.add(mineFragment);
        initPager(view_list);
        staffBean.setView_list(view_list);
    }

    private void initPager(List<View> tab_list) {
        Intent merchentHome = new Intent(context, StaffHomeActivity.class);
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
    public StaffBean loadInstance() {
        if (staffBean != null) {
            return staffBean;
        } else {
            return null;
        }
    }
}
