package com.jdonce.UITestDemo.gesture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jdonce.UITestDemo.R;
import com.jdonce.UITestDemo.gesture.gestureView.CustomGestureLockPainter;
import com.jdonce.UITestDemo.gesture.gestureView.GestureLockView;
import com.jdonce.UITestDemo.gesture.gestureView.OnGestureLockListener;

/**
 * 手势密码设置
 * Created by MJ on 2018/8/30.
 */

public class SettingGestureLockActivity extends AppCompatActivity implements OnGestureLockListener {
    GestureLockView gestureLockView;
    TextView tvMsg;
    GestureLockHelper gestureLockHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_gesture_lock);
        gestureLockView = findViewById(R.id.glv);
        tvMsg = findViewById(R.id.tv_msg);

        gestureLockView.setPainter(new CustomGestureLockPainter());
        gestureLockView.setGestureLockListener(this);

        gestureLockHelper = new GestureLockHelper();
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onProgress(String progress) {

    }

    @Override
    public void onComplete(String result) {
        gestureLockHelper.validateForSetting(SettingGestureLockActivity.this, result);
        boolean isOk = gestureLockHelper.isOk();
        tvMsg.setText(gestureLockHelper.getMessage());
        if (isOk) {
            gestureLockView.clearView();
            if (gestureLockHelper.isFinish()) {
                finish();
            }
        } else {
            gestureLockView.showErrorStatus(300);
        }
    }
}
