package com.wx.logcatviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hjq.logcat.LogcatActivity;
import com.wx.logcatviewer.util.LogcatUploadUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent();
                inte.setClass(MainActivity.this, LogcatActivity.class);
                startActivity(inte);
            }
        });

        LogcatUploadUtil.getInstance().initLogUpload(getBaseContext());
    }
}