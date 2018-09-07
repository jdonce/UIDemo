package com.jdonce.UITestDemo.guideView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.dj.userguide.NewbieGuide;
import com.dj.userguide.model.GuidePage;
import com.dj.userguide.model.HighLight;
import com.dj.userguide.model.RelativeGuide;
import com.jdonce.UITestDemo.R;

/**
 * Created by MJ on 2018/8/31.
 */

public class SingleViewGuideActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_guide_view);
        NewbieGuide.with(this)
                .setLabel("guide1")
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(findViewById(R.id.ll_middle), HighLight.Shape.RECTANGLE,
                                new RelativeGuide(R.layout.view_guide_relative, Gravity.TOP, 20))
                        .setLayoutRes(R.layout.view_guide_simple, R.id.btn_next))
                .alwaysShow(true)
                .show();

    }
}
