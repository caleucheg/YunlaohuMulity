package com.yang.yunwang.home.mainhome.frag;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yang.yunwang.base.ui.DividerGridItemDecoration;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.view.adapter.CommonImageTextRecAdapter;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.MainHomeActivity;
import com.yang.yunwang.home.mainhome.contract.StaffHomeContract;
import com.yang.yunwang.home.mainhome.presenter.StaffHomePresenter;

import java.util.List;


public class StaffHomeFragment extends Fragment implements StaffHomeContract.View, View.OnClickListener {

    private RecyclerView recyclerView_main;
    private RecyclerView recyclerView_sub;
    private TextView text_top_name;
    private TextView title;
    private StaffHomeContract.Presenter staffHomePresenter;
    private ImageView image_staff_portrait;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantUtils.HIGHER_SYS_NO = CommonShare.getHomeData(this.getContext(), "HIGHER_SYS_NO", "");
        ConstantUtils.STAFF_TYPE = CommonShare.getHomeData(this.getContext(), "STAFF_TYPE", "");
        ConstantUtils.HIGHER_NAME = CommonShare.getHomeData(this.getContext(), "HIGHER_NAME", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        recyclerView_main = (RecyclerView) view.findViewById(R.id.recycle_staff_main);
        recyclerView_sub = (RecyclerView) view.findViewById(R.id.recycle_staff_sub);
        image_staff_portrait = (ImageView) view.findViewById(R.id.image_staff_portrait);
        title = (TextView) view.findViewById(R.id.text_staff_customer);
        text_top_name = (TextView) view.findViewById(R.id.text_staff_top_name);
        initData();
        initListener();
        return view;
    }

    private void initData() {
      new StaffHomePresenter(this, this.getContext());
        //装配数据
        staffHomePresenter.initData(ConstantUtils.SYS_NO, ConstantUtils.CUSTOMER);
    }

    private void initListener() {
        image_staff_portrait.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        MainHomeActivity activity = (MainHomeActivity) getActivity();
        activity.parseMineFragment();
    }

    @Override
    public void setPresenter(StaffHomeContract.Presenter presenter) {
        staffHomePresenter = presenter;
    }

    @Override
    public void setMainAdapter(List<String> main_list, int[] main_res, int[] main_res_selected, List<String> actios, Bundle[] bundles) {
        CommonImageTextRecAdapter commonImageTextRecAdapter = new CommonImageTextRecAdapter(this.getContext(), main_list, main_res, main_res_selected, actios, bundles, R.layout.main_item);
        recyclerView_main.setLayoutManager(new GridLayoutManager(this.getContext(), main_list.size()));
        recyclerView_main.setAdapter(commonImageTextRecAdapter);
    }

    @Override
    public void setSubAdapter(List<String> sub_list, int[] sub_res, int[] sub_res_selected, List<String> actios, Bundle[] bundles) {
        CommonImageTextRecAdapter commonImageTextRecAdapter = new CommonImageTextRecAdapter(this.getContext(), sub_list, sub_res, sub_res_selected, actios, bundles, R.layout.sub_item);
        recyclerView_sub.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        recyclerView_sub.setAdapter(commonImageTextRecAdapter);
        recyclerView_sub.addItemDecoration(new DividerGridItemDecoration(this.getContext()));
    }


    @Override
    public void setHeaderTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void setTopName(String top_name) {
        this.text_top_name.setText(top_name);
    }


    public void requestOnNetworkTimeLong() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.getContext());
        dialog.setTitle(this.getResources().getString(R.string.alert_title));
        dialog.setMessage(this.getResources().getString(R.string.request_too_long));
        dialog.setPositiveButton(this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
