package com.yang.yunwang.pay.view.qr;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.basereq.BaseInfoService;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigReq;
import com.yang.yunwang.base.basereq.bean.payconfig.PayConfigResp;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.moduleinterface.provider.IPayProvider;
import com.yang.yunwang.base.ret.BaseObserver;
import com.yang.yunwang.base.ret.ExceptionHandle;
import com.yang.yunwang.base.ui.baidu.UiMessageListener;
import com.yang.yunwang.base.ui.baidu.control.InitConfig;
import com.yang.yunwang.base.ui.baidu.control.MySyntherizer;
import com.yang.yunwang.base.ui.baidu.control.NonBlockSyntherizer;
import com.yang.yunwang.base.ui.baidu.util.OfflineResource;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.Formater;
import com.yang.yunwang.pay.R;
import com.yang.yunwang.pay.contract.QrCodeContract;
import com.yang.yunwang.pay.presenter.QrCodePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.schedulers.Schedulers;

import static com.yang.yunwang.base.ui.baidu.MainHandlerConstant.PRINT;
import static com.yang.yunwang.base.ui.baidu.MainHandlerConstant.UI_CHANGE_INPUT_TEXT_SELECTION;
import static com.yang.yunwang.base.ui.baidu.MainHandlerConstant.UI_CHANGE_SYNTHES_TEXT_SELECTION;

@Route(path = IPayProvider.PAY_ACT_QR_CODE)
public class QRCodeActivity extends BaseActivity implements QrCodeContract.View, View.OnClickListener {

    private final String TAG = "SynthActivity";
    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    protected TtsMode ttsMode = TtsMode.MIX;
    // assets目录下bd_etts_speech_female.data为离线男声模型；bd_etts_speech_female.data为离线女声模型
    protected String offlineVoice = OfflineResource.VOICE_MALE;
    // ===============初始化参数设置完毕，更多合成参数请至getParams()方法中设置 =================
    // 主控制类，所有合成控制方法从这个类开始
    protected MySyntherizer synthesizer;
    protected Handler mainHandler;
    boolean wxIsBank = false;
    boolean zfbIsBank = false;
    private EditText edit_qr_money;
    private TextView text_qr_result;
    private ImageView image_qr_code;
    private ImageView image_qr_wx_logo;
    private ImageView image_qr_zfb_logo;
    private ImageView image_qr;
    //    private ImageView image_back;
    private int QR_WIDTH;
    // 离线发音选择，VOICE_FEMALE即为离线女声发音。
    private int QR_HEIGHT;
    private QrCodeContract.Presenter presenter;
    private int flag = 0;//微信：0；支付宝：1
    private AlertDialog.Builder alertDialog;

    public MySyntherizer getSynthesizer() {
        return synthesizer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_qrcode);
        mainHandler = new Handler() {
            /*
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handle(msg);
            }

        };
        new QrCodePresenter(this, this);
        getPayConfig(ConstantUtils.HIGHER_SYS_NO);
        initPermission();
        initialTts();
        setTitleText(this.getString(R.string.qr_title));
        init();
        initListener();
    }

    private void init() {
        text_qr_result = (TextView) findViewById(R.id.text_qr_result);
        edit_qr_money = (EditText) findViewById(R.id.edit_qr_money);
        image_qr_code = (ImageView) findViewById(R.id.image_qr_code);
        image_qr_wx_logo = (ImageView) findViewById(R.id.image_qr_wx_logo);
        image_qr_zfb_logo = (ImageView) findViewById(R.id.image_qr_zfb_logo);
        image_qr = (ImageView) findViewById(R.id.image_qr);
//        image_back = (ImageView) findViewById(R.id.image_back);
        QR_WIDTH = Math.round(this.getResources().getDimension(R.dimen.qr_width));
        QR_HEIGHT = Math.round(this.getResources().getDimension(R.dimen.qr_heigh));
    }

    private void initListener() {
        image_qr_wx_logo.setOnClickListener(this);
        image_qr_zfb_logo.setOnClickListener(this);
        image_qr.setOnClickListener(this);
//        image_back.setOnClickListener(this);
        getLlBasetitleBack().setOnClickListener(this);
        edit_qr_money.setOnClickListener(this);
        edit_qr_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().contains(".")) {
                    if (charSequence.length() - 1 - charSequence.toString().indexOf(".") > 2) {
                        charSequence = charSequence.toString().subSequence(0,
                                charSequence.toString().indexOf(".") + 3);
                        edit_qr_money.setText(charSequence);
                        edit_qr_money.setSelection(charSequence.length());
                    }
                }
                if (charSequence.toString().trim().substring(0).equals(".")) {
                    charSequence = "0" + charSequence;
                    edit_qr_money.setText(charSequence);
                    edit_qr_money.setSelection(2);
                }

                if (charSequence.toString().length() > 6 && !charSequence.toString().contains(".")) {
                    edit_qr_money.setText(charSequence.toString().substring(0, 6));
                    edit_qr_money.setSelection(6);
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_back) {
            this.finish();

        } else if (i == R.id.image_qr_wx_logo) {
            flag = 0;
            image_qr_zfb_logo.setImageDrawable(this.getResources().getDrawable(R.drawable.qr_zfb_logo_normal));
            image_qr_wx_logo.setImageDrawable(this.getResources().getDrawable(R.drawable.qr_wx_logo_select));
            image_qr_code.setImageResource(0);
            text_qr_result.setText("");
            text_qr_result.setVisibility(View.GONE);
            presenter.closeTimer();

        } else if (i == R.id.image_qr_zfb_logo) {
            flag = 1;
            image_qr_zfb_logo.setImageDrawable(this.getResources().getDrawable(R.drawable.qr_zfb_logo_select));
            image_qr_wx_logo.setImageDrawable(this.getResources().getDrawable(R.drawable.qr_wx_logo_normal));
            image_qr_code.setImageResource(0);
            text_qr_result.setText("");
            text_qr_result.setVisibility(View.GONE);
            presenter.closeTimer();

        } else if (i == R.id.edit_qr_money) {
            if (!edit_qr_money.hasFocus()) {
                edit_qr_money.setFocusable(true);
                edit_qr_money.setFocusableInTouchMode(true);
                edit_qr_money.setCursorVisible(true);
                edit_qr_money.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
            }

        } else if (i == R.id.image_qr) {
            if (TextUtils.equals(ConstantUtils.KOUBEI_ZFB_TAG, ConstantUtils.GETED_ZFB_TYPE) && flag == 1) {
                Toast.makeText(this, "不支持此类型支付", Toast.LENGTH_SHORT).show();
            } else if (!ConstantUtils.Pay_scan_code_payment && flag == 0) {
                Toast.makeText(this, "暂无权限", Toast.LENGTH_SHORT).show();
            } else if (!ConstantUtils.Pay_scan_code_payment_Alipay && flag == 1) {
                Toast.makeText(this, "暂无权限", Toast.LENGTH_SHORT).show();
            } else {
                String money = edit_qr_money.getText().toString();
                Pattern p = Pattern.compile("^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,2})?))$");
                Matcher matcher = p.matcher(money);
                if (!matcher.matches()) {
                    money = Formater.formatMoney(money, 2);
                }
                edit_qr_money.setText(money);
                edit_qr_money.setSelection(money.length());
                if (edit_qr_money.getText().toString().equals("")) {
                    showAlertDialog("请输入金额！");
                } else if (Double.parseDouble(edit_qr_money.getText().toString()) == 0) {
                    showAlertDialog("金额不能为0元！");
                } else {
//                        getPayConfig(ConstantUtils.HIGHER_SYS_NO);
                    KLog.i(zfbIsBank + "-------" + wxIsBank + "----" + ConstantUtils.GETED_ZFB_TYPE + "--" + ConstantUtils.GETED_WX_TYPE);
                    presenter.requestQRURL(flag, edit_qr_money.getText().toString(), wxIsBank, zfbIsBank);
                }
                if (edit_qr_money.hasFocusable()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                        imm.hideSoftInputFromWindow(edit_qr_money.getWindowToken(), 0);
                        KLog.i("y");
                    }else {
                        imm.hideSoftInputFromWindow(edit_qr_money.getWindowToken(), 0);
                        KLog.i("n");
                    }
                    edit_qr_money.clearFocus();
                    edit_qr_money.setFocusable(false);
                }else {
                    KLog.i("no---");
                }
            }

        }
    }

    private void showAlertDialog(String message) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(this.getResources().getString(R.string.alert_title));
            alertDialog.setMessage(message);
            alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    alertDialog = null;
                }
            });
            alertDialog.setPositiveButton(this.getResources().getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    alertDialog = null;
                }
            });
            alertDialog.show();
        }
    }

    @Override
    public void clearQR() {
        image_qr_code.setImageResource(0);
    }

    @Override
    public void setResult(String qr_result, int color, int flag) {
//        Toast.makeText(this, "flag===>" + flag, Toast.LENGTH_SHORT).show();
        if (this.flag == flag) {
            text_qr_result.setVisibility(View.VISIBLE);
            text_qr_result.setText(qr_result);
            text_qr_result.setTextColor(color);
        }
        image_qr_code.setVisibility(View.GONE);
    }

    @Override
    public void creatQRCode(String url) {
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
            if (image_qr_code.getVisibility() == View.GONE) {
                image_qr_code.setVisibility(View.VISIBLE);
            }
            if (text_qr_result.getVisibility() == View.VISIBLE) {
                text_qr_result.setVisibility(View.GONE);
            }
            image_qr_code.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synthesizer.release();
        if (presenter != null) {
            presenter.closeTimer();
            presenter.closeProgressDialog();
        }
    }

    private void getPayConfig(String higherSysNo) {
        PayConfigReq accessToken = new PayConfigReq();
        accessToken.setCustomerSysNo(higherSysNo);
        BaseInfoService.getInstance(this)
                .getPayConfig(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new BaseObserver<List<PayConfigResp>>(this) {
                    @Override
                    protected void doOnNext(List<PayConfigResp> value) {
                        if (value.size() != 0) {
                            for (int i = 0; i < value.size(); i++) {
                                String remarks = value.get(i).getRemarks();
                                if (TextUtils.equals(remarks, "WX")) {
                                    ConstantUtils.GETED_WX_TYPE = value.get(i).getType() + "";
                                    wxIsBank = !TextUtils.equals(value.get(i).getPassageWay() + "", "102");
                                } else if (TextUtils.equals(remarks, "AliPay")) {
                                    ConstantUtils.GETED_ZFB_TYPE = value.get(i).getType() + "";
                                    zfbIsBank = !TextUtils.equals(value.get(i).getPassageWay() + "", "102");
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {

                    }
                });
    }


    /**
     * 初始化引擎，需要的参数均在InitConfig类里
     * <p>
     * DEMO中提供了3个SpeechSynthesizerListener的实现
     * MessageListener 仅仅用log.i记录日志，在logcat中可以看见
     * UiMessageListener 在MessageListener的基础上，对handler发送消息，实现UI的文字更新
     * FileSaveListener 在UiMessageListener的基础上，使用 onSynthesizeDataArrived回调，获取音频流
     */
    protected void initialTts() {
        // 设置初始化参数
        SpeechSynthesizerListener listener = new UiMessageListener(mainHandler); // 此处可以改为 含有您业务逻辑的SpeechSynthesizerListener的实现类

        Map<String, String> params = getParams();

        // appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
        //DONE switch version
        String baiduAppId = DycLibIntent.hasModule() ? ConstantUtils.D_BAIDU_APP_ID : ConstantUtils.BAIDU_APP_ID;
        String baiduApiKey = DycLibIntent.hasModule() ? ConstantUtils.D_BAIDU_API_KEY : ConstantUtils.BAIDU_API_KEY;
        String baiduSecretKey = DycLibIntent.hasModule() ? ConstantUtils.D_BAIDU_SECRET_KEY : ConstantUtils.BAIDU_SECRET_KEY;
        InitConfig initConfig = new InitConfig(baiduAppId,
                baiduApiKey,
                baiduSecretKey,
                ttsMode,
                offlineVoice,
                params,
                listener);

        synthesizer = new NonBlockSyntherizer(this, initConfig, mainHandler); // 此处可以改为MySyntherizer 了解调用过程
    }

    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        // 以下参数均为选填
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0"); // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_VOLUME, "5"); // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");// 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");// 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        return params;
    }

    private void loadModel() {
        if (offlineVoice.equals(OfflineResource.VOICE_FEMALE)) {
            offlineVoice = OfflineResource.VOICE_MALE;
        } else {
            offlineVoice = OfflineResource.VOICE_FEMALE;
        }
        int result = synthesizer.loadModel(offlineVoice);
        checkResult(result, "loadModel");
    }

    private void checkResult(int result, String method) {
        if (result != 0) {
            toPrint("error code :" + result + " method:" + method + ", 错误码文档:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    private void stop() {
        int result = synthesizer.stop();
        checkResult(result, "stop");
    }

    private void initPermission() {
        String permissions[] = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

    protected void toPrint(String str) {
        Message msg = Message.obtain();
        msg.obj = str;
        mainHandler.sendMessage(msg);
    }

    protected void handle(Message msg) {
        int what = msg.what;
        switch (what) {
            case PRINT:
                print(msg);
                break;
            case UI_CHANGE_INPUT_TEXT_SELECTION:

                break;
            case UI_CHANGE_SYNTHES_TEXT_SELECTION:

                break;
            default:
                break;
        }
    }

    private void print(Message msg) {
        String message = (String) msg.obj;
        if (message != null) {
            scrollLog(message);
        }
    }

    private void scrollLog(String message) {
        Spannable colorMessage = new SpannableString(message + "\n");
        colorMessage.setSpan(new ForegroundColorSpan(0xff0000ff), 0, message.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @Override
    public void setPresenter(QrCodeContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
