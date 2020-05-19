package com.opengl.jackn.testqr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class giveQR extends Activity {
    Button QR;
    EditText name,number,mail;

    Bitmap bitmap;

    String filePath,mailto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_giveqr);

        name=findViewById(R.id.editTextName);
        number=findViewById(R.id.editTextNumber);
        mail=findViewById(R.id.editTextMail);
        QR=findViewById(R.id.button);

        QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().contains(",")||number.getText().toString().contains(",")){
                    Context context = getApplicationContext();
                    Toast.makeText(context , "，を含まないでください", Toast.LENGTH_LONG).show();
                }else {
                    makeQR();
                    callMail();
                }
            }
        });
    }
    public void makeQR(){
        System.out.println(name.getText().toString());
        //QRコード化する文字列
        String data = name.getText().toString()+","+number.getText().toString();
//QRコード画像の大きさを指定(pixel)
        int size = 500;


        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Map<EncodeHintType,Object> hint = new HashMap<>();
            hint.put(EncodeHintType.CHARACTER_SET,"UTF-8");
            //QRコードをBitmapで作成=new Has
            bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, size, size,hint);
            StorageQR(bitmap);

        } catch (WriterException e) {
            throw new AndroidRuntimeException("Barcode Error.", e);
        }
    }

    public void StorageQR(Bitmap save){
        File cameraFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Attendance");
        cameraFolder.mkdir();
        filePath = cameraFolder.getPath()+"/" + "QR" +".jpg";
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        save.compress(Bitmap.CompressFormat.JPEG,100,output);
    }

    public void callMail(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        File fileIn = new File(filePath);
        //Uri u = Uri.fromFile(fileIn);
        Uri u = FileProvider.getUriForFile(
                giveQR.this
                ,getApplicationContext().getPackageName() + ".provider"
                , fileIn);
        mailto=mail.getText().toString();
        intent.putExtra(Intent.EXTRA_EMAIL, mailto);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_STREAM, u);
        startActivity(intent);
    }
}
