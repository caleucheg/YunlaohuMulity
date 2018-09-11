package com.yang.yunwang.home.mainhome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.provider.IHomeProvider;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.PatternUtils;
import com.yang.yunwang.base.util.RegexUtils;
import com.yang.yunwang.base.util.Validation;
import com.yang.yunwang.base.view.adapter.MySpinnerAdapter;
import com.yang.yunwang.home.R;
import com.yang.yunwang.home.homeservice.HomeREService;
import com.yang.yunwang.home.mainhome.bean.CommonClassProReq;
import com.yang.yunwang.home.mainhome.bean.CusClassResp;
import com.yang.yunwang.home.mainhome.bean.ProviceResp;
import com.yang.yunwang.home.mainhome.bean.update.rate.CommonRateReq;
import com.yang.yunwang.home.mainhome.bean.update.rate.PassageWayResp;
import com.yang.yunwang.home.mainhome.contract.UpdateInfoContract;
import com.yang.yunwang.home.mainhome.presenter.UpdateInfoPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Route(path = IHomeProvider.HOME_ACT_UPDATE_INFO)
public class UpdateInfoActivity extends BaseActivity implements UpdateInfoContract.View, View.OnClickListener {


    public String areaNo;
    public boolean provinceB = false;
    public boolean cityB = false;
    public boolean areaB = false;
    public boolean isChoice = false;
    private Button btn_user;
    private EditText common_edit;
    private UpdateInfoContract.Presenter presenter;
    private EditText user_name;
    private EditText userType;
    private EditText userPhone;
    private EditText shopIDName;
    private EditText email_address;
    private EditText faxNum;
    private EditText staff_id;
    private EditText tenants_rate;
    private EditText mail_code;
    private EditText address_detial;
    private EditText fax_num;
    private CheckBox check_box;
    private Spinner spinner_fClass;
    private Spinner spinner_sClass;
    private Spinner spinner_tClass;
    private Spinner spinner_province;
    private Spinner spinner_city;
    private Spinner spinner_area;
    private String provinceNo;
    private String cityNo;
    private String staffId;
    private String provinceS;
    private String cityS;
    private String areaS;
    private boolean fromMerchant;
    private TextView textView18;
    private List<String> proviceNoList;
    private List<String> cityNoList;
    private List<String> areaNoList;
    private ArrayAdapter<String> proviceAdapter;
    private ArrayAdapter<String> cityAdapter;
    private ArrayAdapter<String> areaAdapter;
    private String[] merchIds;
    private String[] userIds;
    private TextView textView16;
    private boolean isFirst = true;
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
    private EditText edit_mine_userinfo_userid6;
    private EditText nick_name;
    private Spinner sp_rate;
    private String typeC;
    private EditText edit_mine_userinfo_userid61;
    private boolean isFirstCity = true;
    private boolean isFirstProvice = true;
    private boolean isFirstFClass = true;
    private boolean isFirstSClass = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_info);
        setTitleText(this.getString(R.string.update_info));
        initUI();
    }

    private void initUI() {
        new UpdateInfoPresenter(this, this);
        fromMerchant = getIntent().getBooleanExtra("from_merchant", false);
        staffId = getIntent().getStringExtra("staff_id");
        user_name = (EditText) findViewById(R.id.edit_mine_userinfo_user);
        userType = (EditText) findViewById(R.id.edit_mine_userinfo_userid);
        userPhone = (EditText) findViewById(R.id.edit_mine_userinfo_tel);
        shopIDName = (EditText) findViewById(R.id.edit_mine_userinfo_name);
        email_address = (EditText) findViewById(R.id.edit_mine_userinfo_email);
        faxNum = (EditText) findViewById(R.id.edit_mine_userinfo_shopid);
        staff_id = (EditText) findViewById(R.id.edit_mine_userinfo_shopid1);
        tenants_rate = (EditText) findViewById(R.id.edit_mine_userinfo_email1);
        sp_rate = (Spinner) findViewById(R.id.sp_rate);
        LinearLayout ll_p1 = (LinearLayout) findViewById(R.id.ll_p1);
        View view_110 = findViewById(R.id.view_110);
        if (TextUtils.equals(ConstantUtils.NEW_TYPE, "1")) {
            View view_61 = findViewById(R.id.view_61);
            RelativeLayout rel_mine_userinfo_item_62 = (RelativeLayout) findViewById(R.id.rel_mine_userinfo_item_62);
            View view_611 = findViewById(R.id.view_611);
            RelativeLayout rel_mine_userinfo_item_621 = (RelativeLayout) findViewById(R.id.rel_mine_userinfo_item_621);
            view_611.setVisibility(View.VISIBLE);
            rel_mine_userinfo_item_621.setVisibility(View.VISIBLE);
            view_61.setVisibility(View.VISIBLE);
            rel_mine_userinfo_item_62.setVisibility(View.VISIBLE);
            ll_p1.setVisibility(View.VISIBLE);
            view_110.setVisibility(View.VISIBLE);
            View view_73 = findViewById(R.id.view_73);
            RelativeLayout rel_mine_userinfo_item_74 = (RelativeLayout) findViewById(R.id.rel_mine_userinfo_item_74);
            view_73.setVisibility(View.VISIBLE);
            rel_mine_userinfo_item_74.setVisibility(View.VISIBLE);
            sp_rate.setVisibility(View.VISIBLE);
            tenants_rate.setVisibility(View.GONE);
            tenants_rate.setClickable(false);
            tenants_rate.setFocusable(false);
            tenants_rate.setTextColor(Color.parseColor("#a3a3a3"));
        }
        nick_name = (EditText) findViewById(R.id.edit_mine_userinfo_name7);
        edit_mine_userinfo_userid6 = (EditText) findViewById(R.id.edit_mine_userinfo_userid6);
        edit_mine_userinfo_userid61 = (EditText) findViewById(R.id.edit_mine_userinfo_userid61);
        mail_code = (EditText) findViewById(R.id.edit_mine_userinfo_email2);
        address_detial = (EditText) findViewById(R.id.edit_mine_userinfo_email3);
        fax_num = (EditText) findViewById(R.id.edit_mine_userinfo_email4);
        check_box = (CheckBox) findViewById(R.id.chock_box_bank);
        spinner_province = (Spinner) findViewById(R.id.spinner_province);
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        spinner_area = (Spinner) findViewById(R.id.spinner_area);

        spinner_fClass = (Spinner) findViewById(R.id.spinner_province1);
        spinner_sClass = (Spinner) findViewById(R.id.spinner_city1);
        spinner_tClass = (Spinner) findViewById(R.id.spinner_area1);

        textView18 = (TextView) findViewById(R.id.textView18);
        textView16 = (TextView) findViewById(R.id.textView16);
//        KLog.i("staffid-----", staffId);
        staff_id.setText(staffId);
        btn_user = (Button) findViewById(R.id.btn_user);
        btn_user.setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
        presenter.initData();
//        KLog.i(ConstantUtils.NEW_TYPE);
        if (ConstantUtils.NEW_TYPE.equals("3")) {
            TextView textView20 = (TextView) findViewById(R.id.textView20);
            textView20.setText("员工费率");
        }

    }

    @Override
    public void setInfo(String user, String user_id, String tel, String customer, String email, String storeID, String dwellAddress, String rate, String dwellAddressID, String prociveID, String cityID, String countyID) {
        this.provinceNo = prociveID;
        this.cityNo = cityID;
        this.areaNo = countyID;
        textView16.setText(R.string.regist_info_shopid);
        user_name.setText(user);
        userType.setText(user_id);
        userPhone.setText(tel);
        shopIDName.setText(storeID);
        email_address.setText(email);
        faxNum.setText(customer);
        textView18.setText(R.string.regist_info_name);
        String[] ads = dwellAddress.split("-");
//        KLog.i(ads[0]+"--"+ads[1]);
        address_detial.setText(ads[1]);
        tenants_rate.setText(rate);
        KLog.i(dwellAddressID);
        userIds = dwellAddressID.split("[|]");
//        resetAddress();
    }

    private void resetAddress() {
//        notifySpinner();
        spinner_province.setSelection(getPos(proviceNoList, userIds[0]), true);
        spinner_city.setSelection(getPos(cityNoList, userIds[1]), true);
        spinner_area.setSelection(getPos(areaNoList, userIds[2]), true);
    }

    private void notifySpinner() {
//        KLog.i("notify");
        proviceAdapter.notifyDataSetChanged();
        cityAdapter.notifyDataSetChanged();
        areaAdapter.notifyDataSetChanged();
    }

    private int getPos(List<String> noList, String i) {
//        KLog.i(noList);
//        KLog.i(i);
        for (int a = 0; a < noList.size(); a++) {
            if (noList.get(a).equals(i)) {
                return a;
            }
        }
        return 0;
    }

    @Override
    public Intent loadIntent() {
        return getIntent();
    }

    @Override
    public void setProvince(final List<String> sysNo, List<String> parentId, final List<String> addressName, final String[] userIds) {
        this.proviceNoList = sysNo;
//        this.provinceNo = sysNo.get(0);
        this.provinceNo = userIds[0];
// 建立Adapter并且绑定数据源

        proviceAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, addressName);
        proviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//绑定 Adapter到控件
        spinner_province.setAdapter(proviceAdapter);
        spinner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                isChoice = true;
                provinceB = true;
                cityB = false;
                areaB = false;
                provinceS = addressName.get(pos);
                getCity(pos, sysNo, userIds);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_province.setSelection(getPos(proviceNoList, userIds[0]), true);
    }

    private void getCity(int pos, List<String> sysNo, final String[] userIds) {
//        this.cityNoList = sysNo;
        this.provinceNo = sysNo.get(pos);
        CommonClassProReq accessToken = new CommonClassProReq();
        if (isFirstProvice) {
            accessToken.setParentId(userIds[0]);
            isFirstProvice = false;
        } else {
            accessToken.setParentId(Integer.parseInt(sysNo.get(pos)) + "");
        }
        HomeREService.getInstance(this)
                .getCusProvice(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<ProviceResp>>(this) {
                    @Override
                    protected void doOnNext(ArrayList<ProviceResp> value) {
                        if (value != null && value.size() > 0) {
                            if (value.size() > 0) {
                                final List<String> sysNo = new ArrayList<String>();
                                List<String> parentId = new ArrayList<String>();
                                final List<String> addressName = new ArrayList<String>();
                                for (int i = 0; i < value.size(); i++) {

                                    sysNo.add(value.get(i).getSysNo() + "");
                                    parentId.add(value.get(i).getParentId() + "");
                                    addressName.add(value.get(i).getAddressName());
                                }
                                cityAdapter = new ArrayAdapter<String>(UpdateInfoActivity.this, R.layout.spinner_item, addressName);
                                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cityB = true;
                                areaB = false;
                                spinner_city.setAdapter(cityAdapter);
                                spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int pos, long id) {
                                        cityS = addressName.get(pos);
                                        getArea(pos, sysNo, userIds);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
//                            cityAdapter.notifyDataSetChanged();
                                if (isFirstCity) {
                                    spinner_city.setSelection(getPos(sysNo, userIds[1]), true);
                                    isFirstCity = false;
                                }
                            }

                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }

    private void getArea(int pos, List<String> sysNo, final String[] userIds) {
        this.cityNoList = sysNo;
//        this.areaNoList = sysNo;
        this.cityNo = sysNo.get(pos);

        CommonClassProReq commonClassProReq = new CommonClassProReq();
        if (isFirstCity) {
            commonClassProReq.setParentId(userIds[1]);
        } else {
            commonClassProReq.setParentId(Integer.parseInt(sysNo.get(pos)) + "");
        }
        HomeREService.getInstance(this)
                .getCusProvice(commonClassProReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<ProviceResp>>(this) {
                    @Override
                    protected void doOnNext(ArrayList<ProviceResp> value) {
                        if (value != null && value.size() > 0) {
                            if (value.size() > 0) {
                                final List<String> sysNo = new ArrayList<String>();
                                List<String> parentId = new ArrayList<String>();
                                final List<String> addressName = new ArrayList<String>();
                                for (int i = 0; i < value.size(); i++) {
                                    sysNo.add(value.get(i).getSysNo() + "");
                                    parentId.add(value.get(i).getParentId() + "");
                                    addressName.add(value.get(i).getAddressName());
                                }
                                areaB = true;
                                UpdateInfoActivity.this.areaNoList = sysNo;
                                areaAdapter = new ArrayAdapter<String>(UpdateInfoActivity.this, R.layout.spinner_item, addressName);
                                areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_area.setAdapter(areaAdapter);
                                spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int pos, long id) {
                                        areaS = addressName.get(pos);
                                        UpdateInfoActivity.this.areaNo = sysNo.get(pos);
//                                    KLog.i("province------------", UpdateInfoActivity.this.provinceNo + "---" + UpdateInfoActivity.this.cityNo + "---" + UpdateInfoActivity.this.areaNo);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
//                            areaAdapter.notifyDataSetChanged();
                                if (isFirst) {
                                    spinner_area.setSelection(getPos(areaNoList, userIds[2]), true);
                                    isFirst = false;
                                }

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
        Toast.makeText(this, "账户信息获取失败！请稍后重试。", Toast.LENGTH_LONG).show();
        this.finish();
    }

    @Override
    public void onSuccess(String description) {
        Toast.makeText(this, description, Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void onError(String description) {
        Toast.makeText(this, description, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setMerchInfo(String user, String user_id, String tel, String customer, String email, String fax, String dwellAddress, String rate, String dwellAddressID, String prociveID, String cityID, String countyID, String fClass, String sClass, String tClass, String classID, String type, String nickName, String typeC) {
        this.provinceNo = prociveID;
        this.cityNo = cityID;
        this.areaNo = countyID;
        this.fClassNo = fClass;
        this.sClassNo = sClass;
        this.tClassNo = tClass;
        this.typeC = typeC;
        userPhone.setClickable(false);
        userPhone.setFocusable(false);
//        userPhone.setBackgroundColor(getResources().getColor(R.color.grey_error));
        user_name.setText(user);
        nick_name.setText(nickName);
        userType.setText(user_id);
        userPhone.setText(tel);
        shopIDName.setText(customer);
        email_address.setText(email);
        getPassageWay();
        faxNum.setText(fax);
        String[] ads = dwellAddress.split("-");
//        KLog.i(ads.length);
        if (ads.length < 2) {
            address_detial.setText("");
        } else {
            address_detial.setText(ads[1]);
        }
        tenants_rate.setText(rate);
        merchIds = dwellAddressID.split("[|]");
        mClassIDs = classID.split("[|]");
        presenter.getSHRate();
        KLog.i(merchIds.length);
//        KLog.i(getPos(proviceNoList, merchIds[0]) + getPos(cityNoList, merchIds[1]) + getPos(areaNoList, merchIds[2]));
//        resetMAddress();
    }

    private void getPassageWay() {
        CommonRateReq accessToken = new CommonRateReq();
        accessToken.setCustomerSysNo(ConstantUtils.SYS_NO);
        HomeREService.getInstance(this)
                .initMerchPassage(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<PassageWayResp>>(this) {
                    @Override
                    protected void doOnNext(ArrayList<PassageWayResp> value) {
                        if (value != null && value.size() > 0) {

                            for (int i = 0; i < value.size(); i++) {
                                PassageWayResp jsonObjecta = value.get(i);
                                String remarks = jsonObjecta.getRemarks();
                                if (TextUtils.equals(remarks, "WX")) {
                                    String type = jsonObjecta.getType() + "";
                                    for (int j = 0; j < ConstantUtils.WX_Type.size(); j++) {
                                        if (TextUtils.equals(type, ConstantUtils.WX_Type.get(j))) {
                                            edit_mine_userinfo_userid6.setText(ConstantUtils.WX_TypeName.get(j));
                                        }
                                    }
                                } else if (TextUtils.equals(remarks, "AliPay")) {
                                    String type = jsonObjecta.getType() + "";
                                    for (int j = 0; j < ConstantUtils.ZFB_Type.size(); j++) {
                                        if (TextUtils.equals(type, ConstantUtils.ZFB_Type.get(j))) {
                                            edit_mine_userinfo_userid61.setText(ConstantUtils.ZFB_TypeName.get(j));
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });


    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_back) {
            this.finish();
        } else if (i == R.id.btn_user) {
            if (isChoice && provinceB) {
                if (cityB) {
                    if (areaB) {
                        String passWordComfirm = userPhone.getText().toString().trim();
                        String shopName = shopIDName.getText().toString().trim();
                        String email = email_address.getText().toString().trim();
                        String tenantsRate = tenants_rate.getText().toString().trim();
                        String addressDetail = address_detial.getText().toString().trim();
                        String phoneNum = this.faxNum.getText().toString().trim();
                        String nickName = nick_name.getText().toString().trim();
                        if (!fromMerchant && !TextUtils.equals(passWordComfirm, "") && (!Validation.isPhone(passWordComfirm) || !Validation.isMobile(passWordComfirm))) {
                            Toast.makeText(this, "联系电话未正确填写！", Toast.LENGTH_SHORT).show();
                        } else if (!TextUtils.equals(email, "") && !Validation.isEmail(email)) {
                            Toast.makeText(this, "邮件地址不正确！", Toast.LENGTH_SHORT).show();
                        } else {
                            if (fromMerchant) {
                                if (TextUtils.isEmpty(shopName)) {
                                    Toast.makeText(this, "真实姓名未正确填写！", Toast.LENGTH_SHORT).show();
                                } else if (TextUtils.isEmpty(addressDetail)) {
                                    Toast.makeText(this, "详细地址未正确填写！", Toast.LENGTH_SHORT).show();
                                } else if (TextUtils.isEmpty(nickName) || !RegexUtils.checkChinese(nickName) || nickName.length() > 6) {
                                    Toast.makeText(this, "请输入正确的商户简称,最多六位中文", Toast.LENGTH_SHORT).show();
                                } else if (isChoiceC && fClassB) {
                                    if (sClassB) {
                                        if (tClassB) {
                                            Map<String, Object> map = new HashMap<String, Object>();
                                            String addressCode = provinceNo + "|" + cityNo + "|" + areaNo;
                                            String addressD = provinceS + cityS + areaS + "-" + addressDetail;
                                            map.put("SysNo", ConstantUtils.SYS_NO);
                                            map.put("Email", email);
                                            map.put("Phone", passWordComfirm);
                                            map.put("CustomerName", shopName);
//                                                map.put("Rate", tenantsRate);
                                            map.put("NickName", nickName);
                                            map.put("DwellAddress", addressD);
                                            map.put("DwellAddressID", addressCode);
                                            String classCode = fClassNo + "|" + sClassNo + "|" + tClassNo;
                                            String classNames = fClassS + "-" + sClassS + "-" + tClassS;
                                            map.put("SystemClassName", classNames);
                                            map.put("SystemClassID", classCode);
                                            map.put("Customer_field_three", Integer.parseInt(typeC));
                                            JSONObject jsonObject = new JSONObject(map);
                                            KLog.i(jsonObject.toString());
                                            presenter.changeInfo(jsonObject, fromMerchant);
                                        } else {
                                            Toast.makeText(this, "请选择第级三类目", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(this, "请选择第级二类目", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(this, "请选择第级一类目", Toast.LENGTH_SHORT).show();
                                }
                            } else {
//                                    if (TextUtils.isEmpty(shopName)) {
//                                        Toast.makeText(this, "门店ID未正确填写！", Toast.LENGTH_SHORT).show();
//                                    } else
                                if (TextUtils.isEmpty(phoneNum)) {
                                    Toast.makeText(this, "真实姓名未正确填写！", Toast.LENGTH_SHORT).show();
                                } else if (TextUtils.isEmpty(addressDetail)) {
                                    Toast.makeText(this, "详细地址未正确填写！", Toast.LENGTH_SHORT).show();
                                } else if (!TextUtils.equals(tenantsRate, "") && !PatternUtils.isFourRateNumber(tenantsRate)) {
                                    Toast.makeText(this, "商户费率尚未正确填写！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    String addressCode = provinceNo + "|" + cityNo + "|" + areaNo;
                                    String addressD = provinceS + cityS + areaS + "-" + addressDetail;
                                    map.put("PhoneNumber", passWordComfirm);
                                    map.put("SysNo", ConstantUtils.SYS_NO);
                                    map.put("DisplayName", phoneNum);
                                    map.put("Email", email);
                                    if (!TextUtils.isEmpty(shopName)) {
                                        map.put("Alipay_store_id", shopName);
                                    }
                                    map.put("Rate", tenantsRate);
                                    map.put("DwellAddress", addressD);
                                    map.put("DwellAddressID", addressCode);
                                    JSONObject jsonObject = new JSONObject(map);
                                    KLog.i(jsonObject.toString());
                                    presenter.changeInfo(jsonObject, fromMerchant);
                                }
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
    public void setFClass(final List<String> sysNo, List<String> classID, final List<String> className, final String[] classIDs) {
        this.fClassNoList = sysNo;
        this.fClassNo = classIDs[0];
//        this.fClassNo = sysNo.get(0);
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
//                KLog.i("fcl_cl");
                getSClass(pos, sysNo, classIDs);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_fClass.setSelection(getPos(fClassNoList, classIDs[0]), true);
    }

    private void getSClass(int pos, List<String> sysNo, final String[] classIDs) {
        this.fClassNoList = sysNo;
        this.fClassNo = sysNo.get(pos);
        CommonClassProReq accessToken = new CommonClassProReq();
        if (isFirstFClass) {
            accessToken.setTopSysNO(classIDs[0]);
            isFirstFClass = false;
        } else {
            accessToken.setTopSysNO(sysNo.get(pos));
        }
        accessToken.setClassID(1L);

        HomeREService.getInstance(this)
                .getCusClass(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<CusClassResp>>(this) {
                    @Override
                    protected void doOnNext(ArrayList<CusClassResp> value) {
                        if (value != null && value.size() > 0) {
                            if (value.size() > 0) {
                                final List<String> sysNo = new ArrayList<String>();
                                List<String> classID = new ArrayList<String>();
                                final List<String> className = new ArrayList<String>();
                                for (int i = 0; i < value.size(); i++) {
                                    sysNo.add(value.get(i).getSysNo());
                                    classID.add(value.get(i).getClassID());
                                    className.add(value.get(i).getClassName());
                                }
                                sClassAdapter = new ArrayAdapter<String>(UpdateInfoActivity.this, R.layout.spinner_item, className);
                                sClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sClassB = true;
                                tClassB = false;
                                spinner_sClass.setAdapter(sClassAdapter);
                                spinner_sClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int pos, long id) {
                                        sClassS = className.get(pos);
//                                    KLog.i("sc-_---Cl");
                                        getTclass(pos, sysNo, classIDs);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
//                            sClassAdapter.notifyDataSetChanged();
                                if (isFirstSClass) {
                                    spinner_sClass.setSelection(getPos(sysNo, classIDs[1]), true);
                                    isFirstSClass = false;
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }

    private void getTclass(int pos, List<String> sysNo, final String[] classIDs) {
        this.sClassNoList = sysNo;
        this.sClassNo = sysNo.get(pos);

        CommonClassProReq accessToken = new CommonClassProReq();
        if (isFirstSClass) {
            KLog.i("22");
            accessToken.setTopSysNO(classIDs[1]);
        } else {
            accessToken.setTopSysNO(sysNo.get(pos));
        }
        accessToken.setClassID(2L);

        HomeREService.getInstance(this)
                .getCusClass(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<CusClassResp>>(this) {
                    @Override
                    protected void doOnNext(ArrayList<CusClassResp> value) {
                        if (value != null && value.size() > 0) {
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
                                tClassAdapter = new ArrayAdapter<String>(UpdateInfoActivity.this, R.layout.spinner_item, className);
                                tClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_tClass.setAdapter(tClassAdapter);
                                spinner_tClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int pos, long id) {
                                        tClassS = className.get(pos);
                                        UpdateInfoActivity.this.tClassNoList = sysNo;
                                        UpdateInfoActivity.this.tClassNo = sysNo.get(pos);
//                                    KLog.i("class-----", UpdateInfoActivity.this.fClassNo + "---" + UpdateInfoActivity.this.sClassNo + "---" + UpdateInfoActivity.this.tClassNo);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
//                            tClassAdapter.notifyDataSetChanged();
                                if (isFirstC) {
//                                resetTClass();
                                    spinner_tClass.setSelection(getPos(sysNo, classIDs[2]), true);
                                    isFirstC = false;
                                }

                            }

                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });

    }

    @Override
    public void setRateInfo(final JSONArray jsonArray) {
        ArrayList<String> typeName = new ArrayList<String>();
        typeName.addAll(ConstantUtils.WX_TypeName);
        typeName.addAll(ConstantUtils.ZFB_TypeName);
        ArrayList<String> typeNameR = new ArrayList<String>();
        for (int i = 0; i < typeName.size(); i++) {
            typeNameR.add(typeName.get(i) + "费率");
        }
        ArrayList<String> list;
        list = new ArrayList<>();
        list.addAll(ConstantUtils.WX_Type);
        list.addAll(ConstantUtils.ZFB_Type);
        ArrayList<String> rates = new ArrayList<String>();
        rates.addAll(list);
        for (int mi = 0; mi < jsonArray.length(); mi++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(mi);
                String way = jsonObject.getString("Type");
                int index = list.indexOf(way);
                if (index > -1) {
                    rates.set(index, jsonObject.getString("Rate"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        MySpinnerAdapter adapter = new MySpinnerAdapter(this, rates, list, typeNameR);
        sp_rate.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(UpdateInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
