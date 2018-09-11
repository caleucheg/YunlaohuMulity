package com.yang.yunwang.base.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yang.yunwang.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
public class MySpinnerAdapter extends BaseAdapter {

    private final Context context;
    private final List<String> list;
    private ArrayList<String> typeNameR;
    private ArrayList<String> rates;

    @Deprecated
    public MySpinnerAdapter(Context context, String[] jsonArray, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public MySpinnerAdapter(Context context, ArrayList<String> rates, ArrayList<String> list, ArrayList<String> typeNameR) {
        this.context = context;
        this.list = list;
        this.typeNameR = typeNameR;
        this.rates = rates;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater _LayoutInflater = LayoutInflater.from(context);
        view = _LayoutInflater.inflate(R.layout.spinner_base_rate, null);
        if (view != null) {
            TextView tv_rate_type = (TextView) view.findViewById(R.id.tv_rate_type);
            TextView tv_rate_num = (TextView) view.findViewById(R.id.tv_rate_num);
            String way = rates.get(i);
            String wayS = typeNameR.get(i);
            String rate;
            Double d = Double.parseDouble(way);
            if (d < 1) {
                if (d == 0) {
                    rate = "0.0000";
                } else {
                    rate = rates.get(i);
                }
            } else {
                rate = "0.0000";
            }
            tv_rate_type.setText(wayS);
            tv_rate_num.setText(rate);
        }
        return view;
    }
}
