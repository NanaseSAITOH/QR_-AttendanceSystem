package com.opengl.jackn.testqr;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    Button exit,enter,giveQR;
    TextView textView,textView2;

    Boolean isEnter;
    int REQUEST_PERMISSION=1004;

    public static String number,attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enter=findViewById(R.id.enter);
        exit=findViewById(R.id.exit);
        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        giveQR=findViewById(R.id.giveQR);

        if (Build.VERSION.SDK_INT >= 23) {
            checkStoragePermission();
            checkCameraPermission();
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEnter=false;
                StartReadQR();
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEnter=true;
                StartReadQR();
            }
        });

        giveQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseQR();
            }
        });

        //改変必須
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());

        textView2.setText(df.format(date));

        final Handler mHandler = new Handler(getMainLooper());
        Timer mTimer = new Timer();
        // 一秒ごとに定期的に実行します。
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        DateFormat df = new SimpleDateFormat("HH:mm");
                        Date date = new Date(System.currentTimeMillis());

                        textView.setText(df.format(date));
                    }
                });}
        },0,1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                Log.d("readQR", result.getContents());
                number=result.getContents();
                if(isEnter==true){
                    attendance="出勤";
                }else{
                    attendance="退勤";
                }
                Intent intent = new Intent(getApplication(),kakunin.class);
                startActivity(intent);
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void StartReadQR(){
        new IntentIntegrator(MainActivity.this).initiateScan();
    }

    public void releaseQR(){
       Intent intent = new Intent(getApplication(),giveQR.class);
       startActivity(intent);
    }
    public void checkStoragePermission(){
        // 既に許可している
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

        }else {
            requestLocationPermission();
        }
    }

    private void requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }else{
            Toast toast = Toast.makeText(this, "許可されないとアプリが実行できません", Toast.LENGTH_SHORT);
            toast.show();

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,}, REQUEST_PERMISSION);
        }
    }

    public void checkCameraPermission(){
        // 既に許可している
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){

        }else {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},
                    REQUEST_PERMISSION);
        }else{
            Toast toast = Toast.makeText(this, "許可されないとアプリが実行できません", Toast.LENGTH_SHORT);
            toast.show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,}, REQUEST_PERMISSION);
        }
    }
}
