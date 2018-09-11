package com.yang.yunwang.pay.view.scan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.socks.library.KLog;
import com.yang.yunwang.base.BaseActivity;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module2.PayIntent;
import com.yang.yunwang.base.moduleinterface.module.module3.DycLibIntent;
import com.yang.yunwang.base.moduleinterface.provider.IPayProvider;
import com.yang.yunwang.base.ui.baidu.UiMessageListener;
import com.yang.yunwang.base.ui.baidu.control.InitConfig;
import com.yang.yunwang.base.ui.baidu.control.MySyntherizer;
import com.yang.yunwang.base.ui.baidu.control.NonBlockSyntherizer;
import com.yang.yunwang.base.ui.baidu.util.OfflineResource;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.pay.R;
import com.yang.yunwang.pay.contract.ScanContract;
import com.yang.yunwang.pay.presenter.ScanPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.yang.yunwang.base.ui.baidu.MainHandlerConstant.PRINT;
import static com.yang.yunwang.base.ui.baidu.MainHandlerConstant.UI_CHANGE_INPUT_TEXT_SELECTION;
import static com.yang.yunwang.base.ui.baidu.MainHandlerConstant.UI_CHANGE_SYNTHES_TEXT_SELECTION;

@Route(path = IPayProvider.PAY_ACT_SCAN_RESULT)
public class ScanResultActivity extends BaseActivity implements ScanContract.View, View.OnClickListener {

    private final String TAG = "SynthActivity";
    //    private ImageView image_back;
//    private Button btn_result_back;
    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    protected TtsMode ttsMode = TtsMode.MIX;
    // assets目录下bd_etts_speech_female.data为离线男声模型；bd_etts_speech_female.data为离线女声模型
    protected String offlineVoice = OfflineResource.VOICE_MALE;
    // ===============初始化参数设置完毕，更多合成参数请至getParams()方法中设置 =================
    // 主控制类，所有合成控制方法从这个类开始
    protected MySyntherizer synthesizer;
    // 离线发音选择，VOICE_FEMALE即为离线女声发音。
    protected Handler mainHandler;
    private ScanContract.Presenter presenter;
    private TextView text_result;
    private ImageView image_result;


    public MySyntherizer getSynthesizer() {
        return synthesizer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scanresult);
        setTitleText(this.getString(R.string.scan_title));
        new ScanPresenter(this, this);
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
        initPermission();
        initialTts();
        text_result = (TextView) findViewById(R.id.text_scan_result);
        image_result = (ImageView) findViewById(R.id.image_scan_result);
        KLog.i("info", "oncreate.....");
        boolean zfbIsBank = !TextUtils.equals(ConstantUtils.GETED_ZFB_TYPE, "103");
        boolean wxIsBank = !TextUtils.equals(ConstantUtils.GETED_WX_TYPE, "102");
        String sysNo = ConstantUtils.SYS_NO;
        String hSysno = ConstantUtils.HIGHER_SYS_NO;
        if (!TextUtils.isEmpty(sysNo) && !TextUtils.isEmpty(hSysno)) {
            KLog.i(!TextUtils.isEmpty(sysNo) + "+" + !TextUtils.isEmpty(hSysno));
            presenter.scanRquest(wxIsBank, zfbIsBank, sysNo, hSysno);
        } else {
            Toast.makeText(this, "获取失败,请退出重试.", Toast.LENGTH_SHORT).show();
            this.finish();
        }
//        }
        initListener();


    }

    private void initListener() {
        getLlBasetitleBack().setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int status = 1;
        if (presenter != null) {
            status = presenter.returnStatus();
        }
        if (status != 1) {
            MyBundle intent = new MyBundle();//this, ScanCodeActivity.class
//                this.startActivity(intent);
            PayIntent.scanCode(intent);
            this.finish();
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        }
    }

    @Override
    public void setResultRes(final int res_id) {
        Handler handler = new Handler(this.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                image_result.setImageDrawable(ScanResultActivity.this.getResources().getDrawable(res_id));
                KLog.i("tu---------");
            }
        });
    }

    @Override
    public void setResultText(final String code, final int color) {
        if (!TextUtils.equals("支付成功", text_result.getText().toString()) && !TextUtils.equals("支付失败", text_result.getText().toString())) {
            text_result.setText(code);
            text_result.setTextColor(ScanResultActivity.this.getResources().getColor(color));
        } else {
            KLog.i("1234");
        }
        KLog.i("wen---------" + code);
        if (TextUtils.equals(code, "支付成功")) {
            image_result.setImageDrawable(ScanResultActivity.this.getResources().getDrawable(R.drawable.scan_success_logo));
            text_result.setText("支付成功");
            KLog.i("wen2---");
            text_result.setTextColor(ScanResultActivity.this.getResources().getColor(R.color.scan_success));
            presenter.sendMessage();
        }
    }

    @Override
    public Intent loadInstance() {
        return getIntent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            int status = 1;
            if (presenter != null) {
                status = presenter.returnStatus();
            }
            if (status != 1) {
                MyBundle intent = new MyBundle();//this, ScanCodeActivity.class
//                this.startActivity(intent);
                PayIntent.scanCode(intent);
                this.finish();
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synthesizer.release();
        if (presenter != null) {
            presenter.closeTimer();
        }
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
        //Done switch version
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
    public void setPresenter(ScanContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
