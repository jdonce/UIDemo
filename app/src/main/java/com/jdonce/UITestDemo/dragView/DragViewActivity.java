package com.jdonce.UITestDemo.dragView;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jdonce.UITestDemo.R;

/**
 * Created by MJ on 2018/9/5.
 */

public class DragViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_view);
        final LinearLayout linearLayout = findViewById(R.id.ll_container);

        linearLayout.post(new Runnable() {
            @Override
            public void run() {

                Button dragView = findViewById(R.id.iv_drag);
                final ImageView ivTarget = findViewById(R.id.iv_target);
                final Animator animator = AnimatorInflater.loadAnimator(DragViewActivity.this, R.animator.btn_anim);
                animator.setTarget(ivTarget);
                animator.start();
                DragVerifyHelper dragVerifyHelper = new DragVerifyHelper(dragView, ivTarget, linearLayout,
                        new DragVerifyHelper.OnTouchCallBack() {
                            @Override
                            public void impactResult(boolean isImpact) {
                                ivTarget.setBackgroundResource(isImpact ? R.drawable.frame1
                                        : R.drawable.frame);
                            }

                            @Override
                            public void verifyResult(boolean isSuccess) {
                                if (isSuccess) {
                                    animator.cancel();
                                    finish();
                                }
                            }

                            @Override
                            public void onError(String msg) {
                                Log.d("===", "onError: ");
                            }
                        });
                dragVerifyHelper.startDragVerify();
            }
        });

    }
}
