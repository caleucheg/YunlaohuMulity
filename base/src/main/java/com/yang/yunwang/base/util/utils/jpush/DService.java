package com.yang.yunwang.base.util.utils.jpush;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.socks.library.KLog;

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
 * Created by Administrator on 2018/1/26.
 */

public class DService extends NotificationListenerService {
    @Override
    public void onCreate() {
        KLog.i("d service");
        super.onCreate();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
//                KLog.d("WorkService");
                KLog.d("Service-connect" + JPushInterface.getConnectionState(getApplicationContext()));
                KLog.d("Service-isPushStop" + JPushInterface.isPushStopped(getApplicationContext()));
//                JPushInterface.init(getApplicationContext());            // 初始化 JPush
//                KLog.i(JPushInterface.getRegistrationID(getApplicationContext()));
//                ConstantUtils.isGetReID = !TextUtils.isEmpty(JPushInterface.getRegistrationID(getApplicationContext()));
//                if (!JPushInterface.getConnectionState(getApplicationContext())) {
//                    JPushInterface.init(getApplicationContext());            // 初始化 JPush
//                    JPushInterface.resumePush(getApplicationContext());
//                }
//                if (JPushInterface.isPushStopped(getApplicationContext())) {
//                    JPushInterface.init(getApplicationContext());            // 初始化 JPush
//                    JPushInterface.resumePush(getApplicationContext());
//                }
//                sendMessage();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 1000, 3000);
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

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

    }
}