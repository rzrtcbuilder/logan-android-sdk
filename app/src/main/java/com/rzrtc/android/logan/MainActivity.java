package com.rzrtc.android.logan;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rzrtc.android.logan.java.api.RzLogan;
import com.rzrtc.android.logan.java.api.RzLoganConfig;
import com.rzrtc.android.logan.java.callback.UploadCallback;
import com.rzrtc.android.logan.java.impl.RzLoganFactory;

public class MainActivity extends AppCompatActivity {
    RzLogan loganInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loganInstance = RzLoganFactory.createRzLoganInstance(MainActivity.this, new RzLoganConfig.Builder()
                        .setDeleteAfterUpload(true)
                        .setFileDir(getExternalFilesDir("logan-dir").getAbsolutePath())
                        .setUploadAddress("https://data-center-dev.duobeiyun.com/test/v1/logan")
                        .setUploadInternal(1000 * 60)
                        .setEncrypt(false)
                        .setUpload(true)
                        .build());
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < 1000; i++) {
                                    try {
//                                        Thread.sleep(100);
                                        loganInstance.log("测试 upload");

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                ).start();
            }
        }); findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loganInstance.uploadAllAsync(new UploadCallback() {
                    @Override
                    public void resultCallback(int errorCode) {
                        Log.e("lzj", "resultCallback: errorCode" + errorCode );
//                        Toast.makeText(MainActivity.this,"errorCode " + errorCode,Toast.LENGTH_LONG ).show();
                    }
                });
            }
        }); findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loganInstance.release();
            }
        });







    }
}