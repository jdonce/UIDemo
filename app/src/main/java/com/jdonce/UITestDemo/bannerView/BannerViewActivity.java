package com.jdonce.UITestDemo.bannerView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jdonce.UITestDemo.R;
import com.jdonce.UITestDemo.bannerView.custom.BannerViewPager;
import com.jdonce.UITestDemo.bannerView.custom.IndicatorView;
import com.jdonce.UITestDemo.bannerView.custom1.LoopViewPager;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MJ on 2018/9/10.
 */

public class BannerViewActivity extends AppCompatActivity {
    private List<String> imageViews = new ArrayList<>();
    Banner banner;
    BannerViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_view);
        /**================================测试1====================================================*/
        viewPager = findViewById(R.id.view_pager);
        final IndicatorView indicatorView = findViewById(R.id.indicator_view);
        initData();
        final ImagePagerAdapter pagerAdapter = new ImagePagerAdapter(viewPager);
        pagerAdapter.setDatas(imageViews);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(true, new ScalePageTransformer());
        viewPager.setAdapter(pagerAdapter);
        indicatorView.setShowCount(imageViews.size());
        // 设置轮播时间
        viewPager.setTimeOut(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("===", "onPageSelected: " + position % pagerAdapter.getRealCount());
                position = position % pagerAdapter.getRealCount();
                indicatorView.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 开启轮播
        //viewPager.startAutoPlay();

        // ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, imageViews, viewPager);

        /**=====================================测试2=============================================*/
        banner = findViewById(R.id.banner);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        banner.setPageTransformer(true, new ScalePageTransformer());
        banner.setOffscreenPageLimit(3);
        banner.setImages(imageViews);
        banner.start();
        /**====================================测试3=================================================*/
        LoopViewPager loopViewPager = findViewById(R.id.loop_view_pager);
        NormalPagerAdapter normalPagerAdapter = new NormalPagerAdapter(imageViews);
        loopViewPager.setAdapter(normalPagerAdapter);
        loopViewPager.setOffscreenPageLimit(3);
        loopViewPager.setPageTransformer(true, new ScalePageTransformer());

    }

    private void initData() {
        imageViews = Arrays.asList(getResources().getStringArray(R.array.image_url));
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();

        viewPager.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
