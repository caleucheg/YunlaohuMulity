package com.yang.yunwang.home.mainhome.pwdpage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.mainhome.contract.ResetPwdContract;
import com.yang.yunwang.home.mainhome.presenter.ResetPwdPresenter;

@Route(path = IHomeProvider.HOME_ACT_RESET_PWD)
public class PasswordActivity extends BaseActivity implements ResetPwdContract.View, View.OnClickListener {

    private EditText edit_mine_password_user;
    private EditText edit_mine_password_old;
    private EditText edit_mine_password_new;
    private EditText edit_mine_password_repeat;
    private Button btn_password;
    //    private ImageView imageView;
    private ResetPwdContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_password);
        setTitleText(this.getString(R.string.mine_password_title));

        initUI();
    }

    private void initUI() {
        new ResetPwdPresenter(this, this);
        edit_mine_password_user = (EditText) findViewById(R.id.edit_mine_password_user);
        edit_mine_password_old = (EditText) findViewById(R.id.edit_mine_password_old);
        edit_mine_password_new = (EditText) findViewById(R.id.edit_mine_password_new);
        edit_mine_password_repeat = (EditText) findViewById(R.id.edit_mine_password_repeat);
//        imageView = (ImageView) findViewById(R.id.image_back);
        btn_password = (Button) findViewById(R.id.btn_password);
        edit_mine_password_user.setText(ConstantUtils.USER);
        btn_password.setOnClickListener(this);
//        imageView.setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
    }

    @Override
    public void back() {
        this.finish();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_back) {
            this.finish();

        } else if (i == R.id.btn_password) {
            if (edit_mine_password_old.getText().toString().equals("")) {
                Toast.makeText(this, this.getResources().getString(R.string.toast_password_old), Toast.LENGTH_SHORT).show();
            } else if (edit_mine_password_new.getText().toString().equals("")) {
                Toast.makeText(this, this.getResources().getString(R.string.toast_password_new), Toast.LENGTH_SHORT).show();
            } else if (edit_mine_password_repeat.getText().toString().equals("")) {
                Toast.makeText(this, this.getResources().getString(R.string.toast_password_repeat), Toast.LENGTH_SHORT).show();
            } else if (!edit_mine_password_repeat.getText().toString().equals(edit_mine_password_new.getText().toString())) {
                Toast.makeText(this, this.getResources().getString(R.string.toast_password_error), Toast.LENGTH_SHORT).show();
            } else {
                presenter.changePassword(edit_mine_password_old.getText().toString(), edit_mine_password_new.getText().toString());
            }

        }
    }

    @Override
    public void setPresenter(ResetPwdContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
