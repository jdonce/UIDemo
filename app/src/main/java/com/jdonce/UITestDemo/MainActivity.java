package com.jdonce.UITestDemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.eftimoff.androipathview.PathView;
import com.jdonce.UITestDemo.animationView.AnimationViewActivity;
import com.jdonce.UITestDemo.dragView.DragViewActivity;
import com.jdonce.UITestDemo.fingerprint.FingerprintAuthenticationDialogFragment;
import com.jdonce.UITestDemo.gesture.GestureLockHelper;
import com.jdonce.UITestDemo.gesture.SettingGestureLockActivity;
import com.jdonce.UITestDemo.gesture.VerifyGestureLockActivity;
import com.jdonce.UITestDemo.guideView.GuideViewTestActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_fingerprint).setOnClickListener(this);
        findViewById(R.id.btn_gesture).setOnClickListener(this);
        findViewById(R.id.btn_set_gesture).setOnClickListener(this);
        findViewById(R.id.btn_user_guide).setOnClickListener(this);
        findViewById(R.id.btn_animation_view).setOnClickListener(this);
        findViewById(R.id.btn_drag_view).setOnClickListener(this);


        final PathView pathView = findViewById(R.id.pathView);
        //pathView.setFillAfter(true);//填充路径的颜色
        // pathView.useNaturalColors();//使用自身的颜色 则pathColor设置的颜色将无效
        pathView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pathView.getPathAnimator().
                        delay(100).
                        duration(1500).
                        interpolator(new AccelerateDecelerateInterpolator()).
                        start();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_fingerprint:
                FingerprintAuthenticationDialogFragment dialogFragment = new FingerprintAuthenticationDialogFragment();
                dialogFragment.show(getFragmentManager(), "FingerprintAuthenticationDialogFragment");
                break;
            case R.id.btn_gesture:
                if (GestureLockHelper.isHasGesturePassword(MainActivity.this)) {
                    Intent intent = new Intent(MainActivity.this, VerifyGestureLockActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "没有设置手势密码，请先去设置", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_set_gesture:
                startActivity(new Intent(MainActivity.this, SettingGestureLockActivity.class));
                break;
            case R.id.btn_user_guide:
                startActivity(new Intent(MainActivity.this, GuideViewTestActivity.class));
                break;
            case R.id.btn_animation_view:
                startActivity(new Intent(MainActivity.this, AnimationViewActivity.class));
                break;
            case R.id.btn_drag_view:
                startActivity(new Intent(MainActivity.this, DragViewActivity.class));
                break;
        }
    }
}
