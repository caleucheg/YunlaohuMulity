package com.yang.yunwang.base.util.utils.jpush;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.socks.library.KLog;
import com.yang.yunwang.base.util.CommonShare;
import com.yang.yunwang.base.util.ConstantUtils;
import com.yang.yunwang.base.util.NetStateUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

/**
 */

public class PushSpeakService extends Service {


    private boolean isToast = false;


    @Override
    public void onCreate() {
        super.onCreate();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            Notification.Builder builder = new Notification.Builder(this);
//            builder.setSmallIcon(R.drawable.icon_clear);
//            startForeground(250, builder.build());
//            startService(new Intent(this, CancelService.class));
//        } else {
//            startForeground(250, new Notification());
//        }

    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        KLog.i("startservice");
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String sysNo = CommonShare.getLoginData(getApplicationContext(), "SysNo", "");
                if (!TextUtils.isEmpty(sysNo)) {
                    ConstantUtils.isGetReID = !TextUtils.isEmpty(JPushInterface.getRegistrationID(getApplicationContext()));
                    if (ConstantUtils.isGetReID) {
                        if (!isToast) {
                            isToast = true;
                            String newType = CommonShare.getTypeData(getApplicationContext());
                            if (TextUtils.equals("3", newType)) {
                                ExampleUtil.showToast("推送服务已恢复", getApplicationContext());
                            }
                            KLog.i("insert-id-isToast");
                        } else {
//                        KLog.i("already-insert-isToast");
                        }

                    }
                    if (!JPushInterface.getConnectionState(getApplicationContext())) {
                        String newType = CommonShare.getTypeData(getApplicationContext());
                        if (TextUtils.equals("3", newType)) {
                            KLog.i("resume1");
                            JPushInterface.resumePush(getApplicationContext());
                        }
                    }
                    if (JPushInterface.isPushStopped(getApplicationContext())) {
                        String newType = CommonShare.getTypeData(getApplicationContext());
                        if (TextUtils.equals("3", newType)) {
                            KLog.i("resume2");
                            JPushInterface.resumePush(getApplicationContext());
                        }
                    }
                    if (NetStateUtils.isNetworkConnected(getApplicationContext())) {
//                    sendMessage();
                    }
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
//        Notification notification = builder.build();
//        notification.flags = Notification.FLAG_FOREGROUND_SERVICE;
//        startForeground(0, notification);
        Log.i("Service", "UnreadMessageServices onStartCommand");
        return START_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public void sayService() {
        System.out.println("调用service里面的方法");
    }

    private void sendMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://suibian.yunlaohu.cn/home");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.connect();
                    InputStream inputStream = null;
                    BufferedReader reader = null;
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        inputStream = connection.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(inputStream));
                        final String result = reader.readLine();
                        KLog.i("get-------------http://suibian.yunlaohu.cn/home");
//                        LogToFile.d("mPush","get--------");
                    }
                    //关闭流和连接
                    reader.close();
                    inputStream.close();
                    connection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    class MyBinder extends Binder implements IService {

        @Override
        public void invokeServiceMethod() {
            //调用Service里面的方法
//            sayService();
        }

    }


}
