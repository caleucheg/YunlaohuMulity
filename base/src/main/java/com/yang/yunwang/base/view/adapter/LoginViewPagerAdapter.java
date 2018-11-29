package com.yang.yunwang.base.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.socks.library.KLog;
import com.yang.yunwang.base.R;
import com.yang.yunwang.base.util.CommonShare;

import java.util.List;


public class LoginViewPagerAdapter extends PagerAdapter implements ViewItemData {

    private List<View> mViewList;
    private List<String> mTitleList;
    private Context context;
    private EditText edit_merchant_user;
    private EditText edit_staff_user;
    private EditText edit_merchant_password;
    private EditText edit_staff_password;

    public LoginViewPagerAdapter(Context context, List<View> mViewList, List<String> mTitleList) {
        this.mViewList = mViewList;
        this.mTitleList = mTitleList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mViewList.size();//页卡数
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //0服务商，1员工
        if (position == 0) {
            KLog.i(1 + "posssssss");
            edit_merchant_user = (EditText) mViewList.get(position).findViewById(R.id.edit_merchant_user);
            edit_merchant_password = (EditText) mViewList.get(position).findViewById(R.id.edit_merchant_password);
        } else {
            edit_staff_user = (EditText) mViewList.get(position).findViewById(R.id.edit_staff_user);
            edit_staff_password = (EditText) mViewList.get(position).findViewById(R.id.edit_staff_password);
        }
        container.addView(mViewList.get(position));//添加页卡
        boolean isRem = CommonShare.getBooleanRememberPwd(context);
        KLog.i("-aaaaaaaaaaaa-111");
        if (isRem) {
            int pos = CommonShare.getRememberPos(context);
            KLog.i("-aaaaaaaaaaaa-111" + pos);
            if (pos == 0 || pos == 1) {
                String name = CommonShare.getRememberName(context);
                String pwd = CommonShare.getRememberPwd(context);
                KLog.i(name + "-aaaaaaaaaaaa-" + pwd + "--" + pos);
//                KLog.i((!TextUtils.isEmpty(name))&&(TextUtils.isEmpty(pwd)));
                if ((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(pwd))) {
                    setNameAndPwd(name, pwd, pos);
                }
            }
        }
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));//删除页卡
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);//页卡标题
    }

    @Override
    public Object[] getData(int position) {
        Object[] data = new Object[]{};
        KLog.i(position);
        if (position == 0) {
            if (edit_merchant_user != null && edit_merchant_password != null) {
                data = new Object[]{edit_merchant_user.getText().toString(), edit_merchant_password.getText().toString()};
            }
        } else {
            if (edit_staff_user != null && edit_staff_password != null) {
                data = new Object[]{edit_staff_user.getText().toString(), edit_staff_password.getText().toString()};
            }
        }
        return data;
    }

    public void setNameAndPwd(String name, String pwd, int pos) {
        if (pos == 0) {
            if (edit_merchant_user != null && edit_merchant_password != null) {
                KLog.i("-aaaaaaaaaaaa-1231");
                edit_merchant_user.setText(name);
                edit_merchant_password.setText(pwd);
            }
        } else if (pos == 1) {
            if (edit_staff_user != null && edit_staff_password != null) {
                KLog.i("-aaaaaaaaaaaa-12312");
                edit_staff_user.setText(name);
                edit_staff_password.setText(pwd);
            }
        }
    }
}
