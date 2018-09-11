package com.yang.yunwang.base.view.scan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.socks.library.KLog;
import com.yang.yunwang.base.R;
import com.yang.yunwang.base.moduleinterface.config.MyBundle;
import com.yang.yunwang.base.moduleinterface.module.module1.OrdersIntent;
import com.yang.yunwang.base.moduleinterface.module.module2.PayIntent;
import com.yang.yunwang.base.moduleinterface.provider.IAppProvider;
import com.yang.yunwang.base.moduleinterface.provider.IOrdersProvider;
import com.yang.yunwang.base.view.scan.zxing.camera.CameraManager;
import com.yang.yunwang.base.view.scan.zxing.decoding.CaptureActivityHandler;
import com.yang.yunwang.base.view.scan.zxing.decoding.InactivityTimer;
import com.yang.yunwang.base.view.scan.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
@Route(path = IAppProvider.APP_ACT_CAPTURE)
public class MipcaActivityCapture extends Activity implements Callback {

    private static final float BEEP_VOLUME = 0.10f;
    private static final long VIBRATE_DURATION = 200L;
    private final String ORDERS_CLASS = IOrdersProvider.ORDERS_ACT_ORDER_SEARCH;
    private final String REFUND_CLASS = IOrdersProvider.ORDERS_ACT_REFUND_SEARCH;
    private final String UNREFUND_CLASS = IOrdersProvider.ORDERS_ACT_UNREFUND_SEARCH;
    private final String WX_ORDER = IOrdersProvider.ORDERS_ACT_WX_PLANT;
    private final String ZFB_ORDER = IOrdersProvider.ORDERS_ACT_ZFB_PLANT_SEARCH;
    private final String HOME_REFUND_CLASS = "com.yang.yunwang.home.mainhome.frag.StaffRefundFragment";
    private final String HOME_ORDER_CLASS = "com.yang.yunwang.home.mainhome.frag.MerchantSignFragment";
    private final String HOME_ORDER_S_CLASS = "com.yang.yunwang.home.mainhome.frag.StaffSignFragment";
    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private boolean vibrate;
    private String result_temp = "";
    private String order_start_time = "";
    private String order_end_time = "";
    private String order_search_customer = "";
    private String orders_search_customer_name = "";
    private String staff_id = "";
    private String refund_start_time = "";
    private String refund_end_time = "";
    private String unrefund_start_time = "";
    private String unrefund_end_time = "";
    private boolean isFrag;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.i("onCreate111");
        setContentView(R.layout.activity_capture);

        Intent intent_params = getIntent();
        KLog.i(intent_params.getStringExtra("flag_from"));
        order_start_time = intent_params.getStringExtra("order_start_time");
        order_end_time = intent_params.getStringExtra("order_end_time");
        order_search_customer = intent_params.getStringExtra("order_search_customer");
        orders_search_customer_name = intent_params.getStringExtra("orders_search_customer_name");
        staff_id = intent_params.getStringExtra("staff_id");
        refund_start_time = intent_params.getStringExtra("refund_start_time");
        refund_end_time = intent_params.getStringExtra("refund_end_time");
        unrefund_start_time = intent_params.getStringExtra("unrefund_start_time");
        unrefund_end_time = intent_params.getStringExtra("unrefund_end_time");
        isFrag=intent_params.getBooleanExtra("isFrag",false);
        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        checkCarmerPermission();
        ImageView mButtonBack = (ImageView) findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                MipcaActivityCapture.this.finish();
            }
        });
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        KLog.i("onCreate222");
    }

    private void checkCarmerPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "请进入手机设置,开启应用所需相机权限", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                CameraManager.init(getApplication());
            }
        } else {
            CameraManager.init(getApplication());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (!resultString.equals("")) {
            KLog.i("info", "Scan result==>" + result);
            if (!resultString.equals(result_temp)) {
                Intent intent = getIntent();
                String flag_from = intent.getStringExtra("flag_from");
                if (flag_from != null && !flag_from.equals("")) {
                    switch (flag_from) {
                        case ORDERS_CLASS:
//                            Intent intent_order = new Intent(this, OrdersListActivity.class);
                            MyBundle intent_order = new MyBundle();
                            intent_order.put("order_code", resultString);
                            intent_order.put("order_start_time", order_start_time);
                            intent_order.put("order_end_time", order_end_time);
                            intent_order.put("order_search_customer", order_search_customer);
                            intent_order.put("orders_search_customer_name", orders_search_customer_name);
                            intent_order.put("staff_id", staff_id);
                            intent_order.put("camera_flag", "camera");
                            OrdersIntent.orderList(intent_order);
//                            this.startActivity(intent_order);
                            this.finish();
                            break;
                        case HOME_ORDER_CLASS:
//                            Intent intent_orderh = new Intent(this, OrdersListActivity.class);
                            MyBundle intent_orderh = new MyBundle();
                            intent_orderh.put("order_code", resultString);
                            intent_orderh.put("order_start_time", order_start_time);
                            intent_orderh.put("order_end_time", order_end_time);
                            intent_orderh.put("order_search_customer", order_search_customer);
                            intent_orderh.put("orders_search_customer_name", orders_search_customer_name);
                            intent_orderh.put("staff_id", staff_id);
                            intent_orderh.put("camera_flag", "camera");
                            intent_orderh.put("isFrag",isFrag);
                            OrdersIntent.orderList(intent_orderh);
//                            this.startActivity(intent_orderh);
                            this.finish();
                            break;
                        case HOME_ORDER_S_CLASS:
//                            Intent intent_orders = new Intent(this, OrdersListActivity.class);
                            MyBundle intent_orders = new MyBundle();
                            intent_orders.put("order_code", resultString);
                            intent_orders.put("order_start_time", order_start_time);
                            intent_orders.put("order_end_time", order_end_time);
                            intent_orders.put("order_search_customer", order_search_customer);
                            intent_orders.put("orders_search_customer_name", orders_search_customer_name);
                            intent_orders.put("staff_id", staff_id);
                            intent_orders.put("camera_flag", "camera");
                            OrdersIntent.orderList(intent_orders);
//                            this.startActivity(intent_orders);
                            this.finish();
                            break;
                        case UNREFUND_CLASS:
//                            Intent intent_unrefund = new Intent(this, UnRefundListActivity.class);
                            MyBundle intent_unrefund = new MyBundle();
                            intent_unrefund.put("unrefund_code", resultString);
                            intent_unrefund.put("unrefund_start_time", unrefund_start_time);
                            intent_unrefund.put("unrefund_end_time", unrefund_end_time);
                            intent_unrefund.put("camera_flag", "camera");
                            OrdersIntent.unRefundList(intent_unrefund);
//                            this.startActivity(intent_unrefund);
                            this.finish();
                            break;
                        case HOME_REFUND_CLASS:
//                            Intent home = new Intent(this, UnRefundListActivity.class);
                            MyBundle home = new MyBundle();
                            home.put("unrefund_code", resultString);
                            home.put("unrefund_start_time", unrefund_start_time);
                            home.put("unrefund_end_time", unrefund_end_time);
                            home.put("camera_flag", "camera");
                            OrdersIntent.unRefundList(home);
//                            this.startActivity(home);
                            this.finish();
                            break;
                        case REFUND_CLASS:
//                            Intent intent_refund = new Intent(this, RefundListActivity.class);
                            MyBundle intent_refund = new MyBundle();
                            intent_refund.put("refund_code", resultString);
                            intent_refund.put("refund_start_time", refund_start_time);
                            intent_refund.put("refund_end_time", refund_end_time);
                            intent_refund.put("camera_flag", "camera");
                            OrdersIntent.refundList(intent_refund);
//                            this.startActivity(intent_refund);
                            this.finish();
                            break;
                        case WX_ORDER:
                            MyBundle intent_wx = new MyBundle();
                            intent_wx.put("wx_code", resultString);
                            intent_wx.put("camera_flag", "camera");
                            OrdersIntent.wxPlantorderSearch(intent_wx);

                            this.finish();
                            break;
                        case ZFB_ORDER:
                            MyBundle intent_zfb = new MyBundle();
                            intent_zfb.put("zfb_code", resultString);
                            intent_zfb.put("camera_flag", "camera");
                            OrdersIntent.zfbPlantorderSearch(intent_zfb);

                            this.finish();
                            break;
                    }
                } else {
                    String total_fee = intent.getStringExtra("total_fee");
//                    Intent intent_result = new Intent(this, ScanResultActivity.class);
                    MyBundle intent_result = new MyBundle();
                    intent_result.put("total_fee", total_fee);
                    intent_result.put("result", resultString);
                    PayIntent.scanResult(intent_result);
//                    MipcaActivityCapture.this.startActivity(intent_result);
                    MipcaActivityCapture.this.finish();
                }
            }
        }
        result_temp = resultString;
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

}