package com.opengl.jackn.testqr;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class kakunin extends Activity {

    TextView textViewDay,textViewHour,TextViewNumber,TextViewName;
    Button send;

    String[] info;

    MainActivity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_kakunin);
        main=new MainActivity();


        textViewDay=findViewById(R.id.textViewDay);
        textViewHour=findViewById(R.id.textViewTime);
        TextViewName=findViewById(R.id.textView6);
        TextViewNumber=findViewById(R.id.textView7);
        send=findViewById(R.id.button2);
        send.setText(main.attendance+"確認" );
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendInformation();
            }
        });

        info=main.number.split(",",0);

        TextViewNumber.setText("社員番号 " +info[1]);
        TextViewName.setText("名前 "+info[0]);

        //改変必須
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());

        textViewDay.setText(df.format(date));
        final Handler mHandler = new Handler(Looper.getMainLooper());
        Timer mTimer = new Timer();
        // 一秒ごとに定期的に実行します。
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        DateFormat df = new SimpleDateFormat("HH:mm");
                        Date date = new Date(System.currentTimeMillis());

                        textViewHour.setText("出勤時間 "+df.format(date));
                    }
                });}
        },0,1000);
    }
    public void sendInformation(){
        Retrofit retro = new Retrofit.Builder()
                .baseUrl("http://ec2-3-115-231-56.ap-northeast-1.compute.amazonaws.com/testAttendance.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_Interface service = retro.create(API_Interface.class);
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        Date date = new Date(System.currentTimeMillis());

        textViewDay.setText(df.format(date));
        //数字　名前
        Call<ContactsContract.Data> btc = service.API(info[1],info[0],df.format(date),main.attendance);

        btc.enqueue(new Callback<ContactsContract.Data>() {
            @Override
            public void onResponse(Call<ContactsContract.Data> call, Response<ContactsContract.Data> response) {
                ContactsContract.Data ticker = response.body();
            }

            @Override
            public void onFailure(Call<ContactsContract.Data> call, Throwable t) {
            }
        });
        finish();
    }
}


