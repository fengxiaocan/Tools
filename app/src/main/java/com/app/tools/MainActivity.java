package com.app.tools;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.app.tools.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_LOG_CONTENT_SIZE =1024 * 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},4);
        }

    }

    public static final void LOGE(String TAG,String msg){
        String message = msg;
        while(message.length() > MAX_LOG_CONTENT_SIZE){
            Log.v(TAG,message.substring(0, MAX_LOG_CONTENT_SIZE));
            message = message.substring(MAX_LOG_CONTENT_SIZE);
        }
        Log.e(TAG,message);
    }
    public static final void LOGV(String TAG,String msg){
        String message = msg;
        while(message.length() > MAX_LOG_CONTENT_SIZE){
            Log.v(TAG,message.substring(0, MAX_LOG_CONTENT_SIZE));
            message = message.substring(MAX_LOG_CONTENT_SIZE);
        }
        Log.v(TAG,message);
    }
}
