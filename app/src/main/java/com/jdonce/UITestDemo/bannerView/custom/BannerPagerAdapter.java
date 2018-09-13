package com.jdonce.UITestDemo.bannerView.custom;

import android.support.annotation.IntRange;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MJ on 2018/9/11.
 */

public abstract class BannerPagerAdapter<T extends ViewPager> extends PagerAdapter {
    private static final int modulus = 10;
    private T viewPager;

    public BannerPagerAdapter(T viewPager) {
        this.viewPager = viewPager;
    }

    @IntRange(from = 0)
    public abstract int getRealCount();

    protected abstract Object instantiateRealItem(ViewGroup container, int position);

    @Override
    public int getCount() {
        int realCount = getRealCount();
        if (realCount > 1) {
            realCount = realCount * modulus;
            realCount = realCount > Integer.MAX_VALUE ? Integer.MAX_VALUE : realCount;
        }
        return realCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % getRealCount();
        return this.instantiateRealItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if (getCount() <= 1) {
            return;
        }
        int position = viewPager.getCurrentItem();
        if (position == 0) {
            position = getRealCount();
            viewPager.setCurrentItem(position, false);
        } else if (position == getCount() - 1) {
            position = getRealCount() - 1;
            viewPager.setCurrentItem(position, false);
        }
    }
}
