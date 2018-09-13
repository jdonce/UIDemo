package com.jdonce.UITestDemo.bannerView;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by MJ on 2018/9/10.
 */

public class ScalePageTransformer implements ViewPager.PageTransformer {
    public static final float MIN_SCALE = 0.9f;

    @Override
    public void transformPage(View page, float position) {
        ViewPager viewPager = (ViewPager) page.getParent();
        int scrollX = viewPager.getScrollX();
        int clientWidth = viewPager.getMeasuredWidth() -
                viewPager.getPaddingLeft() - viewPager.getPaddingRight();
        int offsetX = page.getLeft() - scrollX;
        int parentWidth = viewPager.getMeasuredWidth();
        int childWidth = page.getWidth();
        float deltaX = (float) (parentWidth - childWidth) / 2;
        float transformPos = (offsetX - deltaX) / clientWidth;

        if (transformPos < -1) { // [-Infinity,-1) 屏幕之外
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);

        } else if (transformPos <= 1) { // [-1,1]
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(transformPos));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

        } else { // (1,+Infinity] 屏幕之外
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);

        }

    }
}
