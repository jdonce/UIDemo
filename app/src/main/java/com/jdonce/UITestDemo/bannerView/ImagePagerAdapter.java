package com.jdonce.UITestDemo.bannerView;

import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jdonce.UITestDemo.bannerView.custom.BannerPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MJ on 2018/9/11.
 */

public class ImagePagerAdapter extends BannerPagerAdapter {
    private List<String> datas;

    public ImagePagerAdapter(ViewPager viewPager) {
        super(viewPager);
    }

    public void setDatas(List<String> datas) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        this.datas = datas;
    }

    @Override
    public int getRealCount() {
        return this.datas == null ? 0 : this.datas.size();
    }

    @Override
    protected Object instantiateRealItem(ViewGroup container, final int position) {
        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(container.getContext())
                .load(datas.get(position))
                .into(view);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));

        container.addView(view);
        return view;

    }
}
