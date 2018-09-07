package com.jdonce.UITestDemo.guideView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jdonce.UITestDemo.R;

/**
 * Created by MJ on 2018/8/31.
 */

public class GuideViewTestActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_view_test);
        findViewById(R.id.btn_single).setOnClickListener(this);
        findViewById(R.id.btn_multiple).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_single:
                startActivity(new Intent(GuideViewTestActivity.this, SingleViewGuideActivity.class));
                break;
            case R.id.btn_multiple:
                startActivity(new Intent(GuideViewTestActivity.this, MultipleViewGuideActivity.class));

                break;
        }
    }
}
