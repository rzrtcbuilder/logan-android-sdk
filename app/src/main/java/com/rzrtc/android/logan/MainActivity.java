package com.rzrtc.android.logan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.rzrtc.android.logan.java.api.LoganException;
import com.rzrtc.android.logan.java.api.RzLogan;
import com.rzrtc.android.logan.java.api.RzLoganConfig;
import com.rzrtc.android.logan.java.callback.UploadCallback;
import com.rzrtc.android.logan.java.impl.RzLoganFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            RzLogan loganInstance = RzLoganFactory.createRzLoganInstance(this, new RzLoganConfig.Builder()
                    .setDeleteAfterUpload(true)
                    .setFileDir(getExternalFilesDir("logan-dir").getAbsolutePath())
                    .setUploadAddress("https://data-center-dev.duobeiyun.com/test/v1/logan")
                    .setUploadInternal(1000 * 602)
                    .setEncrypt(false)
                    .setUpload(true)
                    .build());
            loganInstance.log("测试 upload");
            loganInstance.uploadAllAsync(new UploadCallback() {
                @Override
                public void resultCallback(int errorCode) {
                    Log.e("lzj", "resultCallback: errorCode" + errorCode );
                    loganInstance.release();
                }
            });

        } catch (LoganException e) {
            e.printStackTrace();
        }

    }
}