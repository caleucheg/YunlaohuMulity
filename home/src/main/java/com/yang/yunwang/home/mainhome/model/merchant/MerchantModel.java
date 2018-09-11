package com.yang.yunwang.home.mainhome.model.merchant;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.bean.MerchantBean;
import com.yang.yunwang.home.mainhome.frag.MerchantHomeFragment;
import com.yang.yunwang.home.mainhome.frag.MerchantLocationFragment;
import com.yang.yunwang.home.mainhome.frag.MerchantSignFragment;
import com.yang.yunwang.home.mainhome.frag.MineFragment;
import com.yang.yunwang.home.mainhome.model.merchant.intf.MerchantModelInterface;

import java.util.ArrayList;
import java.util.List;

public class MerchantModel implements MerchantModelInterface {

    private MerchantBean merchantBean;
    private Context context;

    public MerchantModel(Context context) {
        this.context = context;
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
        MerchantHomeFragment homeFragment = new MerchantHomeFragment();
        MerchantSignFragment signFragment = new MerchantSignFragment();
        MerchantLocationFragment locationFragment = new MerchantLocationFragment();
        MineFragment mineFragment = new MineFragment();
        List<Fragment> view_list = new ArrayList<Fragment>();
        view_list.add(homeFragment);
        view_list.add(signFragment);
        view_list.add(locationFragment);
        view_list.add(mineFragment);
        merchantBean.setView_list(view_list);
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
