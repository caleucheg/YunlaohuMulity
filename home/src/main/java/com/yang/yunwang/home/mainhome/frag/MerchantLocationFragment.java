package com.yang.yunwang.home.mainhome.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.R;


public class MerchantLocationFragment extends Fragment implements View.OnClickListener {
    private EditText edit_staff_search_customer;
    private EditText edit_staff_search_tel;
    private Button btn_staff_search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.layout_staffsearch_fragment, null);
        init(view);
        initListener();
        return view;
    }

    private void init(View view) {
        edit_staff_search_customer = (EditText) view.findViewById(R.id.edit_staff_search_customer);
        edit_staff_search_tel = (EditText) view.findViewById(R.id.edit_staff_search_tel);
        btn_staff_search = (Button) view.findViewById(R.id.btn_staff_search);
    }

    private void initListener() {
        btn_staff_search.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_staff_search) {
            MyBundle bundle = new MyBundle();
            bundle.put("staff_customer", edit_staff_search_customer.getText().toString());
            bundle.put("staff_tel", edit_staff_search_tel.getText().toString());
            bundle.put(ConstantUtils.fromHomeDis, false);
            if (TextUtils.equals(ConstantUtils.NEW_TYPE, "1")) {
                bundle.put(ConstantUtils.fromMerch, true);
                bundle.put(ConstantUtils.fromHome, true);
            }
            if (ConstantUtils.NEW_TYPE.equals("0")) {
                KLog.i(ConstantUtils.NEW_TYPE + "------type");
                HomeIntent.searchStaffs(bundle);
            } else {
                if (ConstantUtils.Staff_staff_list) {
                    HomeIntent.searchStaffs(bundle);
                } else {
                    Toast.makeText(getActivity(), "暂无权限", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }


}
