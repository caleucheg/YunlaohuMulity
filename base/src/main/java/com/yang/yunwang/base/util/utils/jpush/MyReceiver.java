package com.yang.yunwang.base.util.utils.jpush;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.socks.library.KLog;
import com.yang.yunwang.base.R;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.ui.baidu.control.InitConfig;
import com.yang.yunwang.base.ui.baidu.control.MySyntherizer;
import com.yang.yunwang.base.ui.baidu.util.OfflineResource;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.ConstantUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.yang.yunwang.base.ui.baidu.MainHandlerConstant.PRINT;
import static com.yang.yunwang.base.ui.baidu.MainHandlerConstant.UI_CHANGE_INPUT_TEXT_SELECTION;
import static com.yang.yunwang.base.ui.baidu.MainHandlerConstant.UI_CHANGE_SYNTHES_TEXT_SELECTION;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";
    public int i = 0;
    protected Handler mainHandler;
    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    protected TtsMode ttsMode = TtsMode.MIX;
    // assets目录下bd_etts_speech_female.data为离线男声模型；bd_etts_speech_female.data为离线女声模型
    protected String offlineVoice = OfflineResource.VOICE_MALE;
    //    private SpeechSynthesizer speechSynthesizer;
    List<SpeechSynthesizeBag> bags = new ArrayList<SpeechSynthesizeBag>();
    private MySyntherizer synthesizer;

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    KLog.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    KLog.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
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

    private void scrollLog(String message) {
        Spannable colorMessage = new SpannableString(message + "\n");
        colorMessage.setSpan(new ForegroundColorSpan(0xff0000ff), 0, message.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void print(Message msg) {
        String message = (String) msg.obj;
        if (message != null) {
            scrollLog(message);
        }
    }

    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        // 以下参数均为选填
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0"); // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_VOLUME, "5"); // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");// 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");// 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_HIGH_SPEED_NETWORK);
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        return params;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
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
            Bundle bundle = intent.getExtras();
            KLog.i(bundle.toString());
            String regId1 = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            String registrationID = JPushInterface.getRegistrationID(context);
            ConstantUtils.isGetReID = !TextUtils.isEmpty(registrationID);
            if (!ConstantUtils.isGetReID) {
                CommonShare.putJPushID(context, registrationID);
            }
            KLog.i(JPushInterface.getRegistrationID(context) + "---------------id");
            KLog.d(TAG, "[MyReceiver] 接收Registration Id : " + regId1);
            KLog.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
//            LogToFile.d("mPush", "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
            KLog.w(JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction()) + "-------------");
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                KLog.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...
            }
            if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        KLog.i("111111---");
                    } else {
                        speakNotifi(context, intent, bundle);
                    }
                } else {
                    KLog.i("----------");
                    speakNotifi(context, intent, bundle);
                }

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                KLog.d(TAG, "[MyReceiver] 用户点击打开了通知");
                KLog.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
                clickNotifi(context, bundle);
            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                String newType = CommonShare.getTypeData(context);
                if (TextUtils.equals("3", newType)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            KLog.i("111111---");
                        } else {
                            checkConnect(context, intent);
                        }
                    } else {
                        KLog.i("11111111213");
                        checkConnect(context, intent);
                    }
                }
            } else {
                KLog.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }

        } catch (JSONException e) {
            KLog.i("extra data error");
        }

    }

    private void checkConnect(final Context context, Intent intent) {
        boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        KLog.w(TAG, "连接状态改变[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
//        LogToFile.d("mPush", "连接状态改变[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        sendNotifi(context, connected);
        if (connected) {
            SpeechSynthesizer speechSynthesizer = getSpeechSynthesizer(context);
            SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
            speechSynthesizeBag.setText("推送服务以连接");
            String id = System.currentTimeMillis() + "";
            speechSynthesizeBag.setUtteranceId(id.substring(5));
            speechSynthesizer.speak(speechSynthesizeBag);
        } else {
            final SpeechSynthesizer speechSynthesizerj = getSpeechSynthesizer(context);
            SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
            speechSynthesizeBag.setText("推送服务以断开");
            String id = System.currentTimeMillis() + "";
            speechSynthesizeBag.setUtteranceId(id.substring(5));
            speechSynthesizerj.speak(speechSynthesizeBag);
        }
        if (!JPushInterface.getConnectionState(context)) {
            String newType = CommonShare.getTypeData(context);
            if (TextUtils.equals("3", newType)) {
                JPushInterface.resumePush(context);
                KLog.i("resume3");
            }
        }
        if (JPushInterface.isPushStopped(context)) {
            String newType = CommonShare.getTypeData(context);
            if (TextUtils.equals("3", newType)) {
                JPushInterface.resumePush(context);
                KLog.i("resume4");
            }
        }
    }

    @NonNull
    private SpeechSynthesizer getSpeechSynthesizer(Context context) {
        final SpeechSynthesizer speechSynthesizerj;
        speechSynthesizerj = SpeechSynthesizer.getInstance();
        speechSynthesizerj.setContext(context);
        speechSynthesizerj.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
            @Override
            public void onSynthesizeStart(String s) {
                KLog.i(s);
            }

            @Override
            public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
                KLog.i(s);
            }

            @Override
            public void onSynthesizeFinish(String s) {
                KLog.i(s);
            }

            @Override
            public void onSpeechStart(String s) {
                KLog.i(s);
            }

            @Override
            public void onSpeechProgressChanged(String s, int i) {
                KLog.i(s);
            }

            @Override
            public void onSpeechFinish(String s) {
                KLog.i(s);
//                    speechSynthesizerj.release();
            }

            @Override
            public void onError(String s, SpeechError speechError) {
                KLog.e(s);
                KLog.e(speechError.toString());
            }
        });
        //TODO switch version
        speechSynthesizerj.setAppId(ConstantUtils.BAIDU_APP_ID);
        speechSynthesizerj.setApiKey(ConstantUtils.BAIDU_API_KEY, ConstantUtils.BAIDU_SECRET_KEY);
        speechSynthesizerj.auth(TtsMode.MIX);
        speechSynthesizerj.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_HIGH_SPEED_NETWORK);
        speechSynthesizerj.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        OfflineResource offlineResource = null;
        try {
            offlineResource = new OfflineResource(context, OfflineResource.VOICE_MALE);
            speechSynthesizerj.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename()); // 文本模型文件路径 (离线引擎使用)
            speechSynthesizerj.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, offlineResource.getModelFilename());  // 声学模型文件路径 (离线引擎使用)
        } catch (IOException e) {
            e.printStackTrace();
            KLog.i("file-copy-error");
        }
        speechSynthesizerj.initTts(TtsMode.MIX);
        return speechSynthesizerj;
    }

    private void clickNotifi(Context context, Bundle bundle) throws JSONException {
        //打开自定义的Activity
        JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
        KLog.i(json);
        String code = json.has("Out_trade_no") ? json.getString("Out_trade_no") : "";
        String sysNo = json.has("SystemUserSysNo") ? json.getString("SystemUserSysNo") : "";
        String date = json.has("Time_End") ? json.getString("Time_End") : "";
        String timeStart = "";
        String timeEnd = "";
        if (!TextUtils.isEmpty(date)) {
            timeStart = date.substring(0, 10) + " 00:00:00";
            timeEnd = date.substring(0, 10) + " 23:59:59";
        }
        KLog.i(timeStart);
        KLog.i(timeEnd);
//        MyBundle i = new MyBundle();

//        i.put(bundle);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if ((!TextUtils.isEmpty(code)) && (!TextUtils.isEmpty(sysNo))) {
            String sysNoa = CommonShare.getLoginData(context, "SysNo", "");
            if (TextUtils.equals(sysNo, sysNoa)) {
                ARouter.getInstance()
                        .build(IOrdersProvider.ORDERS_ACT_ORDER_LIST_INFO)
                        .withBoolean("noti", true)
                        .withString("Time_Start", timeStart)
                        .withString("Time_End", timeEnd)
                        .withString("code", code)
                        .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .navigation();
//                OrdersIntent.orderListnfo(i);
            } else {
                KLog.e("error-sysNo");
            }
        } else {
            KLog.i("extra data error");
        }
    }

    private void speakNotifi(final Context context, Intent intent, final Bundle bundle) {
        KLog.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        KLog.d(TAG, "[MyReceiver] 接收到推送下来的通知");
        final int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        KLog.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
                final SpeechSynthesizer speechSynthesizer = SpeechSynthesizer.getInstance();
                speechSynthesizer.setContext(context);
                speechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
                    @Override
                    public void onSynthesizeStart(String s) {
                        KLog.i(s);
                    }

                    @Override
                    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
                        KLog.i(s);
                    }

                    @Override
                    public void onSynthesizeFinish(String s) {
                        KLog.i(s);
                    }

                    @Override
                    public void onSpeechStart(String s) {
                        KLog.i(s);
                    }

                    @Override
                    public void onSpeechProgressChanged(String s, int i) {
                        KLog.i(s);
                    }

                    @Override
                    public void onSpeechFinish(String s) {
                        KLog.i(s);
                    }

                    @Override
                    public void onError(String s, SpeechError speechError) {
                        KLog.e(s);
                        KLog.e(speechError.toString());
                    }
                });
                //TODO switch version
                speechSynthesizer.setAppId(ConstantUtils.BAIDU_APP_ID);
                speechSynthesizer.setApiKey(ConstantUtils.BAIDU_API_KEY, ConstantUtils.BAIDU_SECRET_KEY);
                speechSynthesizer.auth(TtsMode.MIX);
                speechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_HIGH_SPEED_NETWORK);
                speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
                speechSynthesizer.setStereoVolume (1.0f, 1.0f);
                OfflineResource offlineResource = null;
                try {
                    offlineResource = new OfflineResource(context, OfflineResource.VOICE_MALE);
                    speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename()); // 文本模型文件路径 (离线引擎使用)
                    speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, offlineResource.getModelFilename());  // 声学模型文件路径 (离线引擎使用)
                } catch (IOException e) {
                    e.printStackTrace();
                    KLog.i("file-copy-error");
                }
                speechSynthesizer.initTts(TtsMode.MIX);
//                Looper.prepare();
                KLog.i(i++);
                SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
                speechSynthesizeBag.setText(alert);
                speechSynthesizeBag.setUtteranceId(notifactionId + "");
                speechSynthesizer.speak(speechSynthesizeBag);
//                Looper.loop();
            }
        }).start();
    }

    private void speakNotifiNew() {

    }


    protected void initialTts(Context context) {
        KLog.i("ainit");
        // 设置初始化参数
//        SpeechSynthesizerListener listener = new UiMessageListener(mainHandler); // 此处可以改为 含有您业务逻辑的SpeechSynthesizerListener的实现类
        SpeechSynthesizerListener listener = new SpeechSynthesizerListener() {
            @Override
            public void onSynthesizeStart(String s) {
                KLog.i(s);
            }

            @Override
            public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
                KLog.i(s);
            }

            @Override
            public void onSynthesizeFinish(String s) {
                KLog.i(s);
            }

            @Override
            public void onSpeechStart(String s) {
                KLog.i(s);
            }

            @Override
            public void onSpeechProgressChanged(String s, int i) {
                KLog.i(s);
            }

            @Override
            public void onSpeechFinish(String s) {
                KLog.i(s);
                synthesizer.release();
            }

            @Override
            public void onError(String s, SpeechError speechError) {
                KLog.e(s);
                KLog.e(speechError.toString());
            }
        };
        Map<String, String> params = getParams();
        //TODO switch version
        InitConfig initConfig = new InitConfig(ConstantUtils.BAIDU_APP_ID,
                ConstantUtils.BAIDU_API_KEY,
                ConstantUtils.BAIDU_SECRET_KEY,
                ttsMode,
                offlineVoice,
                params,
                listener);

        synthesizer = new MySyntherizer(context, initConfig, mainHandler); // 此处可以改为MySyntherizer 了解调用过程
    }

    public boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    public void sendNotifi(Context context, boolean isConnect) {
        KLog.i("speakNotifi");
        //获取NotifactionManager对象
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        //构建一个Notifaction的Builder对象
        Notification.Builder builder = new Notification.Builder(context);
        //设置通知相关信息
        String tickerText;
        String contentText;
        if (isConnect) {
            tickerText = "推送服务已连接";
            contentText = "推送服务已连接.";
        } else {
            tickerText = "推送服务已断开";
            contentText = "推送服务已断开,请稍后自动重连或重启应用恢复推送.";
        }
        builder.setTicker(tickerText);//设置信息提示
        builder.setSmallIcon(R.mipmap.ic_launcher);//设置通知提示图标
        String title = "推送服务";
        builder.setContentTitle(title);//设置标题
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        builder.setContentText(contentText);//设置文本
        builder.setAutoCancel(true);//查看后自动取消
        int requestId = (int) new Date().getTime();
        String type = CommonShare.getTypeData(context);
        if (TextUtils.equals(type, "3")) {
            manager.notify(requestId, builder.build());
        }
    }


    private SpeechSynthesizeBag getSpeechSynthesizeBag(String text, String utteranceId) {
        SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
        //需要合成的文本text的长度不能超过1024个GBK字节。
        speechSynthesizeBag.setText(text);
        speechSynthesizeBag.setUtteranceId(utteranceId);
        return speechSynthesizeBag;
    }
}
