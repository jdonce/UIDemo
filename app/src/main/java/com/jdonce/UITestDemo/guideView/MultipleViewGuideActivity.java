package com.jdonce.UITestDemo.guideView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import com.dj.userguide.NewbieGuide;
import com.dj.userguide.core.Controller;
import com.dj.userguide.listener.OnLayoutInflatedListener;
import com.dj.userguide.listener.OnPageChangedListener;
import com.dj.userguide.model.GuidePage;
import com.dj.userguide.model.HighLight;
import com.dj.userguide.model.RelativeGuide;
import com.jdonce.UITestDemo.R;

/**
 * Created by MJ on 2018/9/3.
 */

public class MultipleViewGuideActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_guide_view);

        Animation enterAnimation = new AlphaAnimation(0f, 1f);
        enterAnimation.setDuration(600);
        enterAnimation.setFillAfter(true);

        Animation exitAnimation = new AlphaAnimation(1f, 0f);
        exitAnimation.setDuration(600);
        exitAnimation.setFillAfter(true);

        NewbieGuide.with(this)
                .setLabel("page")
                .setOnPageChangedListener(new OnPageChangedListener() {
                    @Override
                    public void onPageChanged(int page) {
                        Toast.makeText(MultipleViewGuideActivity.this, "当前page" + page, Toast.LENGTH_SHORT).show();
                    }
                })
                .addGuidePage(GuidePage.newInstance()
                                .addHighLight(findViewById(R.id.ll_middle), HighLight.Shape.RECTANGLE,
                                        new RelativeGuide(R.layout.view_guide_relative, Gravity.TOP, 0))
                                .setLayoutRes(R.layout.view_guide_simple)
                                .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                                    @Override
                                    public void onLayoutInflated(View view, final Controller controller) {
                                        Button button = view.findViewById(R.id.btn_next);
                                        button.setText("下一步");
                                        button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                controller.showPage(1);
                                            }
                                        });
                                    }
                                })
                        /*.setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation)//退出动画*/
                )
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(findViewById(R.id.tv1), HighLight.Shape.RECTANGLE,
                                new RelativeGuide(R.layout.view_guide_relative, Gravity.RIGHT))
                        .setLayoutRes(R.layout.view_guide_simple)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, final Controller controller) {
                                Button button = view.findViewById(R.id.btn_next);
                                button.setText("下一步");
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        controller.showPage(2);
                                    }
                                });
                            }
                        })
                )
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(findViewById(R.id.tv_data), HighLight.Shape.RECTANGLE,
                                new RelativeGuide(R.layout.view_guide_relative, Gravity.LEFT))
                        .setLayoutRes(R.layout.view_guide_simple)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, final Controller controller) {
                                Button button = view.findViewById(R.id.btn_next);
                                button.setText("下一步");
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        controller.showPage(3);
                                    }
                                });
                            }
                        })
                )
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(findViewById(R.id.tv3), HighLight.Shape.RECTANGLE,
                                new RelativeGuide(R.layout.view_guide_relative, Gravity.LEFT))
                )
                .alwaysShow(true)
                .show();


    }
}
