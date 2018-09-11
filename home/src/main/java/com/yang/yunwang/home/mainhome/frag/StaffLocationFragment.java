package com.yang.yunwang.home.mainhome.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.R;


public class StaffLocationFragment extends Fragment implements View.OnClickListener {
    private EditText edit_shop_user;
    private EditText edit_shop_name;
    private Button btn_search;
    private String staff_id = "";
    private boolean fromHome = false;
    private boolean marchStaff = true;
    private boolean allocate;
    private boolean isFWS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_merchantsearch_framgent, null);

        edit_shop_user = (EditText) view.findViewById(R.id.edit_shop_user);
        edit_shop_name = (EditText) view.findViewById(R.id.edit_shop_name);
        btn_search = (Button) view.findViewById(R.id.btn_shop_search);
        fromHome = false;
        staff_id = "";
        allocate = false;
        isFWS = false;
        marchStaff = true;
        initListener();
        return view;
    }

    private void initListener() {
        btn_search.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_shop_search) {
            MyBundle intent=new MyBundle();
            intent.put("from_home", fromHome);
            intent.put(ConstantUtils.FWS_YUANGONG, isFWS);
            intent.put("shop_user", edit_shop_user.getText().toString());
            intent.put("shop_name", edit_shop_name.getText().toString());
            intent.put("allocate", allocate);
            intent.put(ConstantUtils.merchStaff, marchStaff);
            if (staff_id != null && !staff_id.equals("")) {
                intent.put("staff_id", staff_id);
            }
            if (ConstantUtils.Business_business) {
                HomeIntent.merchantList(intent);
            } else {
                Toast.makeText(getActivity(), "暂无权限", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
