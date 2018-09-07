package com.jdonce.UITestDemo.gesture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jdonce.UITestDemo.R;
import com.jdonce.UITestDemo.gesture.gestureView.CustomGestureLockPainter;
import com.jdonce.UITestDemo.gesture.gestureView.GestureLockView;
import com.jdonce.UITestDemo.gesture.gestureView.OnGestureLockListener;

/**
 * 验证手势锁
 * Created by MJ on 2018/8/30.
 */

public class VerifyGestureLockActivity extends AppCompatActivity implements OnGestureLockListener {
    GestureLockView gestureLockView;
    GestureLockHelper gestureLockHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_gesture_lock);
        gestureLockView = findViewById(R.id.glv);
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
        gestureLockHelper.validateForChecking(result, VerifyGestureLockActivity.this);
        String message = gestureLockHelper.getMessage();
        Toast.makeText(VerifyGestureLockActivity.this, message, Toast.LENGTH_SHORT).show();
        boolean isOk = gestureLockHelper.isOk();
        if (isOk) {
            gestureLockView.clearView();
        } else {
            gestureLockView.showErrorStatus(300);
        }
        if (gestureLockHelper.isFinish()) {
            finish();
        }
    }
}
