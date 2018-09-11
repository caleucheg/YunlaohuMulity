package com.yang.yunwang.base.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 公共FragmentPager适配器
 */
public class CommonFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> lists;
    private List<String> titles;

    public CommonFragmentPagerAdapter(FragmentManager manager, List<Fragment> lists, List<String> titles) {
        super(manager);
        this.lists = lists;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Fragment getItem(int position) {
        return lists.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

}
