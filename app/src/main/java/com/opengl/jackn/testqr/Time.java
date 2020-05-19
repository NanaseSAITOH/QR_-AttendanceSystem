package com.opengl.jackn.testqr;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Time extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Handler mHandler = new Handler(Looper.getMainLooper());
        Timer mTimer = new Timer();
        // 一秒ごとに定期的に実行します。
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        DateFormat df = new SimpleDateFormat("HH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());

                        //textView.setText(df.format(date));
                    }
                });}
        },0,1000);
    }
}
