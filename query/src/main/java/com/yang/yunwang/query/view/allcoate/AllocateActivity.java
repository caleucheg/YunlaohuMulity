package com.yang.yunwang.query.view.allcoate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.bean.allcopage.AllocateBean;
import com.yang.yunwang.query.api.contract.AllcoatePageContract;
import com.yang.yunwang.query.api.model.AllcoatePageModel;
import com.yang.yunwang.query.api.presenter.AllcoatePagePresenter;
import com.yang.yunwang.query.view.adapter.CommonListRecAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

@Route(path = IOrdersProvider.ORDERS_ACT_ALLCOATE_PAGE)
public class AllocateActivity extends BaseActivity implements AllcoatePageContract.View, View.OnClickListener {

    private Button btn_allocate;
    private AllcoatePageContract.Presenter presenter;
    private CommonListRecAdapter commonListRecAdapter;
    private XRecyclerView rec_order_list;
    private Intent intent;
    private AllcoatePageContract.Model model;
    private AllocateBean bean;
    private int oldPos = 0;
    private String cNo;
    private String cNoMark;
    private boolean fromHome = false;
    private String displayName;
    private String loginName;
    private List<String> tempList;
    private boolean fromStaff;
    private int leftLength = 0;
    private int leftLengthF = 0;

    public boolean isFromHome() {
        return fromHome;
    }

    public boolean isFromStaff() {
        return fromStaff;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_allocate_activity);
        intent = getIntent();
        new AllcoatePagePresenter(this, this);
        fromHome = intent.getBooleanExtra("from_home", false);
        fromStaff = intent.getBooleanExtra("from_staff", false);
        if (fromHome) {
            setTitleText(this.getString(R.string.shop_role));
        } else {
            setTitleText(this.getString(R.string.allocate));
        }
        setHomeComfirmVisisble(true);
        setHomeBarVisisble(true);
        init();
        presenter.initData();
    }

    private void init() {

        rec_order_list = (XRecyclerView) findViewById(R.id.xrec_allocate_list);
        rec_order_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        rec_order_list.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        rec_order_list.setRefreshing(false);
        rec_order_list.setPullRefreshEnabled(false);
        rec_order_list.setLoadingMoreEnabled(false);
//        btn_allocate=(Button) findViewById(R.id.btn_allocate_comfirm);

        getLlBasehomeBack().setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
        getComfirmBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KLog.i("click_com", "click--------------");
                leftLength = 0;
                for (int i = 0; i < bean.getList_flag().size(); i++) {
//                    KLog.i("sdllll---" + leftLength);
                    if (bean.getList_flag().get(i) == 1) {
                        leftLength++;
                    }
                }
                KLog.i(bean.getList_flag());
                if (!fromHome){
                    int idx = bean.getList_flag().indexOf(1);
                    KLog.i(cNo);
                    if (idx!=-1){
                        cNo=bean.getList_cno().get(idx);
                    }
                    KLog.i(cNo);
                }
                presenter.allocateComfirm(fromHome, leftLength, bean, leftLengthF);
            }
        });
        parseData();
    }

    private void parseData() {
        model = new AllcoatePageModel(this);
        bean = model.loadInstance();
        String left_data = intent.getStringExtra("left_data");
        String right_data = intent.getStringExtra("right_data");
        KLog.i("left_data", left_data);
        KLog.i("right_data", right_data);
        try {
            if (fromHome) {
                displayName = "RoleName";
                loginName = "SysNo";

                leftData(left_data, displayName, "SystemRoleSysNo");
                rightData(right_data, displayName, loginName);
            } else {
                displayName = "DisplayName";
                loginName = "SysNO";
                leftData(left_data, displayName, loginName);
                rightData(right_data, displayName, loginName);
            }
            this.setAdapter(bean);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void rightData(String right_data, String displayName, String loginName) throws JSONException {
        KLog.i(right_data);
        JSONArray jsonArray;
        JSONObject left = new JSONObject(right_data);
        jsonArray = left.getJSONArray("model");
        List<String> eidter_list = new ArrayList<>();
        List<String> creater_list = new ArrayList<>();
        List<String> name_list = new ArrayList<String>();
        List<String> cno_list = new ArrayList<String>();
        List<Integer> flag_list = new ArrayList<Integer>();
        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                name_list.add(item.getString(displayName));
                cno_list.add(item.getString(loginName));
                flag_list.add(0);
                if (isFromHome()) {
                    if (item.has("InUser")) {
                        if (!TextUtils.isEmpty(item.getString("InUser")) && !TextUtils.equals("null", item.getString("InUser"))) {
                            KLog.i(item.has("InUser") + "-------------------------------");
                            creater_list.add(item.getString("InUser"));
                            eidter_list.add(item.getString("EditUser"));
                        }
//                        else {
//                            creater_list.add(1+"");
//                            eidter_list.add(1+"");
//                        }
                    }

                }
            }
            if (leftLength == 0) {
                model.initOrderListData(name_list, cno_list, flag_list, eidter_list, creater_list);
            } else {
                model.addData(name_list, cno_list, flag_list, eidter_list, creater_list);
            }
        } else {
            Toast.makeText(getApplicationContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
        }
    }

    private void leftData(String left_data, String displayName, String loginName) throws JSONException {
        JSONArray jsonArray;
        if (isFromHome()) {
            if (left_data != null) {
                jsonArray = new JSONArray(left_data);
            }else {
                jsonArray = new JSONArray();
            }
        } else {
            if (left_data != null) {
                JSONObject left = new JSONObject(left_data);
                jsonArray = left.getJSONArray("model");
            } else {
                jsonArray = new JSONArray();
            }
        }
        List<String> eidter_list = new ArrayList<>();
        List<String> creater_list = new ArrayList<>();
        List<String> name_list = new ArrayList<String>();
        List<String> cno_list = new ArrayList<String>();
        List<Integer> flag_list = new ArrayList<Integer>();
        leftLength = jsonArray.length();
        leftLengthF = jsonArray.length();
        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                name_list.add(item.getString(displayName));
                cno_list.add(item.getString(loginName));
                if (!fromHome){
                    cNo=item.getString(loginName);
                }
                flag_list.add(1);
            }


        }
        model.initOrderListData(name_list, cno_list, flag_list, eidter_list, creater_list);
    }

    @Override
    public void setInfo(AllocateBean bean) {

    }

    @Override
    public void refreshComplete() {
        commonListRecAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMoreComplete() {
        commonListRecAdapter.notifyDataSetChanged();
    }

    @Override
    public void dataNotifyChanged() {
        commonListRecAdapter.notifyDataSetChanged();
    }

    @Override
    public int getRecItmesCount() {
        return commonListRecAdapter.getItemCount();
    }

    @Override
    public void setAdapter(final AllocateBean bean) {
        KLog.i("allactivity", bean.toString());
        commonListRecAdapter = new CommonListRecAdapter(this, bean, IOrdersProvider.ORDERS_ACT_ALLCOATE_PAGE, R.layout.layout_allocate_item, new CommonListRecAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                onCheckChange(postion, bean);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rec_order_list.setLayoutManager(linearLayoutManager);
        commonListRecAdapter.setHasStableIds(true);
        rec_order_list.setItemViewCacheSize(500);
//        rec_order_list.getRecycledViewPool().setMaxRecycledViews(commonListRecAdapter.getItemViewType(0),500);
        rec_order_list.setAdapter(commonListRecAdapter);
        rec_order_list.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(this.getResources().getColor(R.color.divide_gray_color))
                .size(getResources().getDimensionPixelSize(R.dimen.divider_1dp))
                .build());
        rec_order_list.setPullRefreshEnabled(false);
        commonListRecAdapter.notifyDataSetChanged();


    }

    private void onCheckChange(int postion, AllocateBean bean) {
//        if (fromHome) {
//            KLog.i("itemclick", "-----" + postion + "--" + bean.getList_flag().size());
//            int f = bean.getList_flag().get(postion - 1) == 1 ? 0 : 1;
//            bean.getList_flag().set(postion - 1, f);
//            KLog.i("itemclick", "-----" + bean.getList_flag().get(postion - 1));
//        } else {
//            for (int i = 0; i < bean.getList_flag().size(); i++) {
//                bean.getList_flag().set(i, 0);
//            }
//
//            bean.getList_flag().set(postion - 1, 1);
//            cNo = bean.getList_cno().get(postion - 1);
//            KLog.i("itemclick", "-----" + postion + "--" + cNo);
//        }
//
//        commonListRecAdapter.notifyDataSetChanged();
    }

    @Override
    public String getCno() {
        return cNo;
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public Intent loadInstance() {
        return getIntent();
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_home) {
            HomeIntent.launchHome();
            this.finish();

        } else if (i == R.id.image_back) {
            this.finish();

        }
    }

    @Override
    public void setPresenter(AllcoatePageContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
