package com.yang.yunwang.home.mainhome.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.view.adapter.CommonImageTextRecAdapter;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.contract.MerchHomeContract;
import com.yang.yunwang.home.mainhome.presenter.MerchHomePresenter;

import java.util.List;

public class MerchantHomeFragment extends Fragment implements MerchHomeContract.View {

    private TextView text_total_fee;
    private TextView text_orders_count;
    private TextView text_total_cash;
    private RecyclerView rec_menu;
    private PullToRefreshScrollView pullToRefreshScrollView;

    private MerchHomeContract.Presenter presnter;
    private TextView title_cash_num;
    private TextView right_cash_num;
    private TextView left_cash_num;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchanthome, null);
        new MerchHomePresenter(this, this.getContext());
        pullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                ConstantUtils.IS_ATFER_LOGIN_INIT = true;
                if (presnter != null) {
                    presnter.initData();
                } else {
                    new MerchHomePresenter(MerchantHomeFragment.this, MerchantHomeFragment.this.getContext());
                    presnter.initData();
                }
            }
        });
        text_total_fee = (TextView) view.findViewById(R.id.text_total_fee);
        text_orders_count = (TextView) view.findViewById(R.id.text_orders_count);
        text_total_cash = (TextView) view.findViewById(R.id.text_total_cash);
        rec_menu = (RecyclerView) view.findViewById(R.id.rec_menu);
        title_cash_num = (TextView) view.findViewById(R.id.title_cash_num);
        left_cash_num = (TextView) view.findViewById(R.id.left_cash_num);
        right_cash_num = (TextView) view.findViewById(R.id.right_cash_num);
        presnter.initData();
        return view;
    }

    @Override
    public void setMenuAdapter(List<String> menu_list, int[] menu_res, int[] menu_res_selected, List<String> actios, Bundle[] bundles) {
        CommonImageTextRecAdapter commonImageTextRecAdapter = new CommonImageTextRecAdapter(this.getContext(), menu_list, menu_res, menu_res_selected, actios, bundles, R.layout.rec_menu_item_new);
        rec_menu.setLayoutManager(new GridLayoutManager(this.getContext(), 4));
        rec_menu.setAdapter(commonImageTextRecAdapter);
    }

    @Override
    public void setInfo(String total_fee, int orders_count, String total_cash) {
        text_total_fee.setText(total_fee);
        text_orders_count.setText("交易笔数：" + orders_count);
        text_total_cash.setText("汇总金额：" + total_cash);
//        AmountUtils.changeF2YWithDDD(total_fee);
//        title_cash_num.setText(total_fee);
//        right_cash_num.setText(total_fee);
//        left_cash_num.setText(total_fee);
        pullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void setPresenter(MerchHomeContract.Presenter presenter) {
        this.presnter = presenter;
    }

}
