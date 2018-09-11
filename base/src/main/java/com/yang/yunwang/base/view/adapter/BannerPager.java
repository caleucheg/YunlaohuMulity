package com.yang.yunwang.base.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yang.yunwang.base.R;

import java.util.List;

public class BannerPager extends PagerAdapter {

    private Context context;
    private List<View> banner_list;
    private int[] reses;

    public BannerPager(Context context, List<View> banner_list, int[] reses) {
        this.context = context;
        this.banner_list = banner_list;
        this.reses = reses;
    }

    @Override
    public int getCount() {
        return banner_list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = (ImageView) banner_list.get(position).findViewById(R.id.image_banner);
        container.addView(banner_list.get(position));
        imageView.setImageDrawable(context.getResources().getDrawable(reses[position]));
        return banner_list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        container.removeView(banner_list.get(position));//删除页卡
    }

}
