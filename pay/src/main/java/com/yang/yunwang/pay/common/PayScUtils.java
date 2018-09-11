package com.yang.yunwang.pay.common;

import android.content.Context;
import android.media.MediaPlayer;

import com.yang.yunwang.base.ui.baidu.control.MySyntherizer;
import com.yang.yunwang.pay.R;


public class PayScUtils {
    @Deprecated
    public static void success(Context context) {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.tts1);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
        mp.start();
    }

    public static void success(String text, MySyntherizer speechSynthesizer, boolean isWX) {
        if (isWX) {
            speechSynthesizer.speak("微信到账" + text + "元");
        } else {
            speechSynthesizer.speak("支付宝到账" + text + "元");
        }
    }
}
