package com.zwt.zwttransmit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.zwt.zwttransmit.common.KeyStore;
import com.zwt.zwttransmit.databinding.ActivityLaunchBinding;

public class LaunchActivity extends BaseActivity<ActivityLaunchBinding> {
    @Override
    public void initAllViews() {

    }

    @Override
    public void initAllDatum() {
        internalHandler.sendEmptyMessageDelayed(KeyStore.KEY_SKIP_LUNCH_TO_MAIN, 2000);
    }

    private final Handler internalHandler = new Handler(Looper.myLooper(), (@NonNull Message msg) ->{
        switch (msg.what) {
            case KeyStore.KEY_SKIP_LUNCH_TO_MAIN:
                MainActivity.actionStart(LaunchActivity.this);
                finish();
                break;
            default:
                break;
        }
        return false;
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        initAllViews();
        initAllDatum();
    }
}
