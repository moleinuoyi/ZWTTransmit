package com.zwt.zwttransmit;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.zwt.zwttransmit.common.KeyStore;

public class LaunchActivity extends BaseActivity {
    @Override
    public void initAllViews() {

    }

    @Override
    public void initAllDatum() {
        internalHandler.sendEmptyMessageDelayed(KeyStore.KEY_SKIP_LUNCH_TO_MAIN, 800);
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

}
