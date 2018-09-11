package com.yang.yunwang.query.view.persnoalqr;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.module.home.HomeIntent;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.query.R;
import com.yang.yunwang.query.api.contract.PersonnalQRContract;
import com.yang.yunwang.query.api.presenter.PersonnalQRPresenter;

import java.util.Hashtable;

@Route(path = IOrdersProvider.ORDERS_ACT_PERSONAL_QR)
public class PersonnelQRActivity extends BaseActivity implements View.OnClickListener, PersonnalQRContract.View {

    private ImageView image_qr;
    //    private ImageView image_back;
    private Button btn_qr_smart;
    private Button btn_qr_wx;
    private Button btn_qr_zfb;
    private Button btn_qr_check;
    private Button btn_qr_delete;
    private Button[] btns;
    private int QR_WIDTH;
    private int QR_HEIGHT;
    private PersonnalQRContract.Presenter presenter;
    private String sysNO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_personnelqr);
        setHomeBarVisisble(true);
        if (TextUtils.equals("3", ConstantUtils.NEW_TYPE)) {
            setTitleText(getString(R.string.staff_pay_qrcode));
        } else {
            setTitleText(this.getString(R.string.personel_qr_title));
        }
        new PersonnalQRPresenter(this, this);
        init();
        initListener();
    }

    private void init() {
        image_qr = (ImageView) findViewById(R.id.image_qr);
        btn_qr_smart = (Button) findViewById(R.id.btn_qr_smart);
        btn_qr_wx = (Button) findViewById(R.id.btn_qr_wx);
        btn_qr_zfb = (Button) findViewById(R.id.btn_qr_zfb);
        btn_qr_check = (Button) findViewById(R.id.btn_qr_check);
        btn_qr_delete = (Button) findViewById(R.id.btn_qr_delete);
        btns = new Button[]{btn_qr_smart, btn_qr_wx, btn_qr_zfb, btn_qr_check, btn_qr_delete};
        QR_WIDTH = Math.round(this.getResources().getDimension(R.dimen.qr_width));
        QR_HEIGHT = Math.round(this.getResources().getDimension(R.dimen.qr_heigh));

        sysNO = getIntent().getStringExtra("sysNO");
    }

    private void initListener() {
        for (int i = 0; i < btns.length; i++) {
            btns[i].setOnClickListener(this);
        }
        getLlBasetitleBack().setOnClickListener(this);
        getLlBasehomeBack().setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_home) {
            HomeIntent.launchHome();
            this.finish();

        } else if (i == R.id.btn_qr_smart) {
            image_qr.setImageResource(0);
            if (!TextUtils.isEmpty(sysNO)) {
                presenter.requestSmartCodeURL(sysNO);
            } else {
                Toast.makeText(PersonnelQRActivity.this, "获取失败,请重试", Toast.LENGTH_SHORT).show();
            }

        } else if (i == R.id.btn_qr_check) {
            image_qr.setImageResource(0);
            if (!TextUtils.isEmpty(sysNO)) {
                presenter.requestCheckURL(sysNO);
            } else {
                Toast.makeText(PersonnelQRActivity.this, "获取失败,请重试", Toast.LENGTH_SHORT).show();
            }

        } else if (i == R.id.btn_qr_delete) {
            image_qr.setImageResource(0);
            if (!TextUtils.isEmpty(sysNO)) {
                presenter.requestDeleteURL(sysNO);
            } else {
                Toast.makeText(PersonnelQRActivity.this, "获取失败,请重试", Toast.LENGTH_SHORT).show();
            }

        } else if (i == R.id.image_back) {
            this.finish();

        }
        changeBtnStatus(view.getId());
    }

    @Override
    public void createQRCode(String url) {
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            KLog.i(pixels.length);
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            image_qr.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearQrCode() {
        image_qr.setImageResource(0);
    }

    private void changeBtnStatus(int id) {
        for (int i = 0; i < btns.length; i++) {
            if (btns[i].getId() != id) {
                btns[i].setBackground(this.getResources().getDrawable(R.drawable.material_button_grey));
            } else {
                btns[i].setBackground(this.getResources().getDrawable(R.drawable.material_button_blue));
            }
        }
    }

    @Override
    public void setPresenter(PersonnalQRContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
