package com.yang.yunwang.home.mainhome.model.staff;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.bean.StaffBean;
import com.yang.yunwang.home.mainhome.frag.MineFragment;
import com.yang.yunwang.home.mainhome.frag.StaffHomeFragment;
import com.yang.yunwang.home.mainhome.frag.StaffLocationFragment;
import com.yang.yunwang.home.mainhome.frag.StaffRefundFragment;
import com.yang.yunwang.home.mainhome.frag.StaffSignFragment;
import com.yang.yunwang.home.mainhome.model.staff.inter.StaffModelInterface;

import java.util.ArrayList;
import java.util.List;

public class StaffModel implements StaffModelInterface {

    private StaffBean staffBean;
    private Context context;
    private boolean f = false;

    public StaffModel(Context context) {
        f = TextUtils.equals(ConstantUtils.NEW_TYPE, "2");
        staffBean = new StaffBean();
        this.context = context;
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
        StaffHomeFragment homeFragment = new StaffHomeFragment();
        StaffSignFragment signFragment = new StaffSignFragment();
        StaffLocationFragment locationFragment = new StaffLocationFragment();
        StaffRefundFragment refundFragment = new StaffRefundFragment();
        MineFragment mineFragment = new MineFragment();
        List<Fragment> view_list = new ArrayList<Fragment>();
        view_list.add(homeFragment);
        view_list.add(signFragment);
        view_list.add(f ? locationFragment : refundFragment);
        view_list.add(mineFragment);
        staffBean.setView_list(view_list);
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
