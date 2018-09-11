package com.yang.yunwang.home.mainhome;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.PatternUtils;
import com.yang.yunwang.base.util.RegexUtils;
import com.yang.yunwang.base.util.Validation;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.homeservice.HomeREService;
import com.yang.yunwang.home.mainhome.bean.CommonClassProReq;
import com.yang.yunwang.home.mainhome.bean.CusClassResp;
import com.yang.yunwang.home.mainhome.bean.ProviceResp;
import com.yang.yunwang.home.mainhome.contract.RegistContract;
import com.yang.yunwang.home.mainhome.presenter.RegistPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Route(path = IHomeProvider.HOME_ACT_REGIST)
public class RegistInfoActivity extends BaseActivity implements RegistContract.View, View.OnClickListener {


    public String areaNo;
    public boolean provinceB = false;
    public boolean cityB = false;
    public boolean areaB = false;
    public boolean isChoice = false;
    private Button btn_user;
    private EditText common_edit;
    private RegistContract.Presenter presenter;
    private EditText user_name;
    private EditText password;
    private EditText password_comfirm;
    private EditText shop_name;
    private EditText email_address;
    private EditText phone_num;
    private EditText staff_id;
    private EditText tenants_rate;
    private EditText mail_code;
    private EditText address_detial;
    private EditText fax_num;
    //    private Switch check_box_pufa;
//    private Switch check_box_xingye;
    private Spinner spinner_province;
    private Spinner spinner_city;
    private Spinner spinner_area;
    private String provinceNo;
    private String cityNo;
    private String staffId;
    private String provinceS;
    private String cityS;
    private String areaS;
    private boolean isFirstC = true;
    private List<String> fClassNoList;
    private String fClassNo;
    private ArrayAdapter<String> fClassAdapter;
    private boolean isChoiceC = false;
    private boolean fClassB = false;
    private boolean sClassB = false;
    private boolean tClassB = false;
    private String fClassS;
    private List<String> sClassNoList;
    private String sClassS;
    private List<String> tClassNoList;
    private String sClassNo;
    private ArrayAdapter<String> tClassAdapter;
    private String tClassS;
    private String tClassNo;
    private String[] mClassIDs;
    private ArrayAdapter<String> sClassAdapter;
    private Spinner spinner_fClass;
    private Spinner spinner_sClass;
    private Spinner spinner_tClass;
    private String userNO;
    //    private boolean isPuFa = false;
//    private boolean isXingYe = false;
//    private boolean isYoHui = true;
//    private Switch chock_box_bank21;
    private EditText nickName;
    private TextView textView20;
    private TextView textView20z;
    private EditText tenants_rateZ;
    //    private Switch chock_box_bank211;
//    private boolean isPuFaKouBei = false;
    private Spinner spinner_wx;
    private Spinner spinner_zfb;
    private String wxPassageWay;
    private String zfbPassageWay;
    private String wxType;
    private String zfbType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_regist_info);
        setTitleText(this.getString(R.string.regist_info));
        initUI();
    }

    private void initUI() {
        new RegistPresenter(this, this);
        staffId = getIntent().getStringExtra("staff_id");
        user_name = (EditText) findViewById(R.id.edit_mine_userinfo_user);
        password = (EditText) findViewById(R.id.edit_mine_userinfo_userid);
        password_comfirm = (EditText) findViewById(R.id.edit_mine_userinfo_tel);
        shop_name = (EditText) findViewById(R.id.edit_mine_userinfo_name);
        email_address = (EditText) findViewById(R.id.edit_mine_userinfo_email);
        phone_num = (EditText) findViewById(R.id.edit_mine_userinfo_shopid);
        staff_id = (EditText) findViewById(R.id.edit_mine_userinfo_shopid1);
        tenants_rate = (EditText) findViewById(R.id.edit_mine_userinfo_email1);
        tenants_rateZ = (EditText) findViewById(R.id.edit_mine_userinfo_email1z);
        tenants_rate.setText("");
        tenants_rateZ.setText("");
        mail_code = (EditText) findViewById(R.id.edit_mine_userinfo_email2);
        address_detial = (EditText) findViewById(R.id.edit_mine_userinfo_email3);
        fax_num = (EditText) findViewById(R.id.edit_mine_userinfo_email4);
        nickName = (EditText) findViewById(R.id.edit_mine_userinfo_name7);
        textView20 = (TextView) findViewById(R.id.textView20);
        textView20z = (TextView) findViewById(R.id.textView20z);
        textView20.setText("微信费率");
        textView20z.setText("支付宝费率");
        spinner_wx = (Spinner) findViewById(R.id.spinner_wx);
        spinner_zfb = (Spinner) findViewById(R.id.spinner_zfb);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, ConstantUtils.WX_TypeName);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_wx.setAdapter(spinnerAdapter);
        wxPassageWay = ConstantUtils.WX_Passageway.get(0);
        zfbPassageWay = ConstantUtils.ZFB_Passageway.get(0);
        wxType = ConstantUtils.WX_Type.get(0);
        zfbType = ConstantUtils.ZFB_Type.get(0);
        spinner_wx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wxPassageWay = ConstantUtils.WX_Passageway.get(position);
                wxType = ConstantUtils.WX_Type.get(position);
                KLog.i(wxPassageWay + "--=-" + wxType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> spinnerAdapterZ = new ArrayAdapter<String>(this, R.layout.spinner_item, ConstantUtils.ZFB_TypeName);
        spinnerAdapterZ.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_zfb.setAdapter(spinnerAdapterZ);
        spinner_zfb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zfbPassageWay = ConstantUtils.ZFB_Passageway.get(position);
                zfbType = ConstantUtils.ZFB_Type.get(position);
                KLog.i(zfbPassageWay + "--=-" + zfbType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_province = (Spinner) findViewById(R.id.spinner_province);
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        spinner_area = (Spinner) findViewById(R.id.spinner_area);


        spinner_fClass = (Spinner) findViewById(R.id.spinner_province1);
        spinner_sClass = (Spinner) findViewById(R.id.spinner_city1);
        spinner_tClass = (Spinner) findViewById(R.id.spinner_area1);

        KLog.i("staffid-----", staffId);
        staff_id.setText(staffId);
        user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phone_num.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn_user = (Button) findViewById(R.id.btn_user);
        btn_user.setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
        setHomeBarVisisble(true);
        getLlBasehomeBack().setOnClickListener(this);
        presenter.initData();
    }

    @Override
    public void setInfo(String user, String user_id, String tel, String customer, String email, String storeID) {

    }

    @Override
    public void setProvince(final List<String> sysNo, List<String> parentId, final List<String> addressName) {
        this.provinceNo = sysNo.get(0);
// 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, addressName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//绑定 Adapter到控件
        spinner_province.setAdapter(adapter);
        spinner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                isChoice = true;
                provinceB = true;
                cityB = false;
                areaB = false;
                provinceS = addressName.get(pos);
                getCity(pos, sysNo);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getCity(int pos, List<String> sysNo) {
        this.provinceNo = sysNo.get(pos);
        CommonClassProReq accessToken = new CommonClassProReq();
        accessToken.setParentId(sysNo.get(pos));
        HomeREService.getInstance(this)
                .getCusProvice(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<ProviceResp>>(this) {
                    @Override
                    protected void doOnNext(ArrayList<ProviceResp> value) {
                        if (value!=null&&value.size()>0) {
                            if (value.size() > 0) {
                                final List<String> sysNo = new ArrayList<String>();
                                List<String> parentId = new ArrayList<String>();
                                final List<String> addressName = new ArrayList<String>();
                                for (int i = 0; i < value.size(); i++) {

                                    sysNo.add(value.get(i).getSysNo()+"");
                                    parentId.add(value.get(i).getParentId()+"");
                                    addressName.add(value.get(i).getAddressName());
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegistInfoActivity.this, R.layout.spinner_item, addressName);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cityB = true;
                                areaB = false;
                                spinner_city.setAdapter(adapter);
                                spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int pos, long id) {
                                        cityS = addressName.get(pos);
                                        getArea(pos, sysNo);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });


    }

    private void getArea(int pos, List<String> sysNo) {
        this.cityNo = sysNo.get(pos);
        CommonClassProReq accessToken = new CommonClassProReq();
        accessToken.setParentId(sysNo.get(pos));
        HomeREService.getInstance(this)
                .getCusProvice(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<ProviceResp>>(this) {
                    @Override
                    protected void doOnNext(ArrayList<ProviceResp> value) {
                        if (value!=null&&value.size()>0) {
                            if (value.size() > 0) {
                                final List<String> sysNo = new ArrayList<String>();
                                List<String> parentId = new ArrayList<String>();
                                final List<String> addressName = new ArrayList<String>();
                                for (int i = 0; i < value.size(); i++) {

                                    sysNo.add(value.get(i).getSysNo()+"");
                                    parentId.add(value.get(i).getParentId()+"");
                                    addressName.add(value.get(i).getAddressName());
                                }
                                areaB = true;
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegistInfoActivity.this, R.layout.spinner_item, addressName);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_area.setAdapter(adapter);
                                spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int pos, long id) {
                                        areaS = addressName.get(pos);
                                        RegistInfoActivity.this.areaNo = sysNo.get(pos);
                                        KLog.i("province------------", RegistInfoActivity.this.provinceNo + "---" + RegistInfoActivity.this.cityNo + "---" + RegistInfoActivity.this.areaNo);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }

    @Override
    public void infoError() {
//        Toast.makeText(this, "账户信息获取失败！请稍后重试。", Toast.LENGTH_LONG).show();
//        this.finish();
    }

    @Override
    public void onSuccess(String description, String string) {
        KLog.i("注册成功");
        presenter.insertRole(userNO, description);
        presenter.insertRate(string);
        presenter.insertPassgeWay(string);
        this.finish();
    }

    @Override
    public void onError(String description) {
        Toast.makeText(this, description, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_home) {
            HomeIntent.launchHome();
            this.finish();
        } else if (i == R.id.image_back) {
            this.finish();

        } else if (i == R.id.btn_user) {
            if (isChoice && provinceB) {
                if (cityB) {
                    if (areaB) {
                        String userName = user_name.getText().toString().trim();
                        this.userNO = userName;
                        String passWord = password.getText().toString().trim();
                        String passWordComfirm = password_comfirm.getText().toString().trim();
                        String shopName = shop_name.getText().toString().trim();
                        String email = email_address.getText().toString().trim();
                        String tenantsRate = tenants_rate.getText().toString().trim();
                        String tenantsRateZ = tenants_rateZ.getText().toString().trim();
                        String emailBuffer = mail_code.getText().toString().trim();
                        String mailCode = TextUtils.isEmpty(emailBuffer) ? "" : emailBuffer;
                        String addressDetail = address_detial.getText().toString().trim();
                        String faxNumBuffer = fax_num.getText().toString().trim();
                        String faxNum = TextUtils.isEmpty(faxNumBuffer) ? "" : faxNumBuffer;
                        String phoneNum = phone_num.getText().toString().trim();
                        String nickNameS = nickName.getText().toString().trim();
                        if (TextUtils.isEmpty(tenantsRate) && TextUtils.isEmpty(tenantsRateZ)) {
                            Toast.makeText(this, "商户费率尚未正确填写!请至少填写一项!", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(nickNameS) || !RegexUtils.checkChinese(nickNameS) || nickNameS.length() > 6) {
                            Toast.makeText(this, "请输入正确的商户简称,最多六位中文", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.equals(userName, "") || !Validation.isPhone(userName) || !Validation.isMobile(userName)) {
                            Toast.makeText(this, "请输入正确的用户名", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.equals(passWord, "")) {
                            Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.equals(passWordComfirm, "") || !TextUtils.equals(passWord, passWordComfirm)) {
                            Toast.makeText(this, "请确认密码！", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.equals(shopName, "") || TextUtils.isEmpty(shopName)) {
                            KLog.i(shopName + RegexUtils.checkChinese(shopName));
                            Toast.makeText(this, "门店名称尚未正确填写！", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.equals(email, "") || !Validation.isEmail(email)) {
                            Toast.makeText(this, "邮件地址尚未正确填写！", Toast.LENGTH_SHORT).show();
                        } else if (!TextUtils.isEmpty(tenantsRate) && !PatternUtils.isRateNumber(tenantsRate)) {
                            Toast.makeText(this, "商户微信费率格式错误！", Toast.LENGTH_SHORT).show();
                        } else if (!TextUtils.isEmpty(tenantsRateZ) && !PatternUtils.isRateNumber(tenantsRateZ)) {
                            Toast.makeText(this, "商户支付宝费率格式错误！", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.equals(addressDetail, "")) {
                            Toast.makeText(this, "详细地址尚未填写！", Toast.LENGTH_SHORT).show();
                        } else {
                            String addressCode = provinceNo + "|" + cityNo + "|" + areaNo;
                            String addressD = provinceS + cityS + areaS + "-" + addressDetail;
                            if (isChoiceC && fClassB) {
                                if (sClassB) {
                                    if (tClassB) {
                                        String classCode = fClassNo + "|" + sClassNo + "|" + tClassNo;
                                        String classNames = fClassS + "-" + sClassS + "-" + tClassS;
                                        presenter.changeInfo(userName, passWord, shopName,
                                                email, tenantsRate, mailCode, addressD, addressCode, faxNum, staffId,
                                                classCode, classNames, wxPassageWay, nickNameS, tenantsRateZ, zfbPassageWay, wxType, zfbType);
                                    } else {
                                        Toast.makeText(this, "请选择第级三类目", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(this, "请选择第级二类目", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "请选择第级一类目", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else {
                        Toast.makeText(this, "请选择区", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请选择市", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请等待数据刷新", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void setFClass(final List<String> sysNo, List<String> classID, final List<String> className) {
        this.fClassNoList = sysNo;
        this.fClassNo = sysNo.get(0);
        // 建立Adapter并且绑定数据源
        fClassAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, className);
        fClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner_fClass.setAdapter(fClassAdapter);
        spinner_fClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                isChoiceC = true;
                fClassB = true;
                sClassB = false;
                tClassB = false;
                fClassS = className.get(pos);
                KLog.i("fcl_cl");
                getSClass(pos, sysNo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getSClass(int pos, List<String> sysNo) {
        this.fClassNoList = sysNo;
        this.fClassNo = sysNo.get(pos);
        KLog.i(sClassNoList + "_________" + fClassNo);
        CommonClassProReq accessToken = new CommonClassProReq();
        accessToken.setTopSysNO(sysNo.get(pos));
        accessToken.setClassID(1L);
        HomeREService.getInstance(this)
                .getCusClass(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<CusClassResp>>(this) {
                    @Override
                    protected void doOnNext(ArrayList<CusClassResp> value) {
                        if (value!=null&&value.size()>0) {
//                            KLog.i(result);
//                            JSONArray model = new JSONArray(result);
                            if (value.size() > 0) {
                                final List<String> sysNo = new ArrayList<String>();
                                List<String> classID = new ArrayList<String>();
                                final List<String> className = new ArrayList<String>();
                                for (int i = 0; i < value.size(); i++) {
                                    sysNo.add(value.get(i).getSysNo());
                                    classID.add(value.get(i).getClassID());
                                    className.add(value.get(i).getClassName());
                                }
                                sClassAdapter = new ArrayAdapter<String>(RegistInfoActivity.this, R.layout.spinner_item, className);
                                sClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sClassB = true;
                                tClassB = false;
                                spinner_sClass.setAdapter(sClassAdapter);
                                spinner_sClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int pos, long id) {
                                        sClassS = className.get(pos);
                                        KLog.i("sc-_---Cl");
                                        getTclass(pos, sysNo);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                                sClassAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }

    private void getTclass(int pos, List<String> sysNo) {
        this.sClassNoList = sysNo;
        this.sClassNo = sysNo.get(pos);
        KLog.i(tClassNoList + "-----" + sClassNo);
        CommonClassProReq accessToken = new CommonClassProReq();
        accessToken.setTopSysNO(sysNo.get(pos));
        accessToken.setClassID(2L);
        HomeREService.getInstance(this)
                .getCusClass(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<CusClassResp>>(this) {
                    @Override
                    protected void doOnNext(ArrayList<CusClassResp> value) {
                        if (value!=null&&value.size()>0) {
                            if (value.size() > 0) {
                                final List<String> sysNo = new ArrayList<String>();
                                List<String> classID = new ArrayList<String>();
                                final List<String> className = new ArrayList<String>();
                                for (int i = 0; i < value.size(); i++) {
                                    sysNo.add(value.get(i).getSysNo());
                                    classID.add(value.get(i).getClassID());
                                    className.add(value.get(i).getClassName());
                                }
                                tClassB = true;
                                tClassAdapter = new ArrayAdapter<String>(RegistInfoActivity.this, R.layout.spinner_item, className);
                                tClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_tClass.setAdapter(tClassAdapter);
                                spinner_tClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int pos, long id) {
                                        tClassS = className.get(pos);
                                        RegistInfoActivity.this.tClassNoList = sysNo;
                                        RegistInfoActivity.this.tClassNo = sysNo.get(pos);
                                        KLog.i("province------------", RegistInfoActivity.this.fClassNo + "---" + RegistInfoActivity.this.sClassNo + "---" + RegistInfoActivity.this.tClassNo);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                                tClassAdapter.notifyDataSetChanged();
                            }

                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }

    @Override
    public void setPresenter(RegistContract.Presenter presenter) {
        this.presenter=presenter;
    }
}
