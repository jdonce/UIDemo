package com.jdonce.UITestDemo.bannerView.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jdonce.UITestDemo.R;

/**
 * Created by MJ on 2018/9/12.
 */

public class IndicatorView extends FrameLayout {
    RadioButton btns[];
    int showCount;//指示器显示的数量
    Context context;
    //指示器序号的宽度
    float width;
    //指示器序号的高度
    float height;
    //指示器序号背景
    int background;
    //设置Margin值
    RadioGroup.LayoutParams lp;
    //设置指示器序号是否可点击
    boolean isClick;
    RadioGroup group;

    public IndicatorView(Context context) {
        super(context);
        this.context = context;
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(context, attrs);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_indicator, this);
        group = (RadioGroup) view.findViewById(R.id.radio_group);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        showCount = typedArray.getInt(R.styleable.IndicatorView_show_count, 3);
        //指示器序号背景
        background = typedArray.getResourceId(R.styleable.IndicatorView_indicator_background, R.drawable.indicator_selector);
        //默认值
        float defaultDimen = context.getResources().getDimension(R.dimen.dp_10);
        //指示器序号的宽度
        width = typedArray.getDimension(R.styleable.IndicatorView_indicator_width, defaultDimen);
        //指示器序号的高度
        height = typedArray.getDimension(R.styleable.IndicatorView_indicator_height, defaultDimen);
        //默认的Margins值
        float defaultMargins = context.getResources().getDimension(R.dimen.dp_5);
        //指示器序号横向的间距
        float marginsRight = typedArray.getDimension(R.styleable.IndicatorView_indicator_horizontalSpace, defaultMargins);
        //设置Margin值
        lp = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins((int) defaultMargins, (int) defaultMargins, (int) marginsRight, (int) defaultMargins);
        //设置指示器序号是否可点击
        isClick = typedArray.getBoolean(R.styleable.IndicatorView_indicator_click, false);
       /* //记录所有指示器序号控件
        btns = new RadioButton[showCount];
        //创建指示器序号控件
        for (int i = 0; i < showCount; i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setBackgroundResource(background);
            radioButton.setWidth((int) width);
            radioButton.setHeight((int) height);
            radioButton.setLayoutParams(lp);
            radioButton.setEnabled(isClick);
            radioButton.setButtonDrawable(null);
            btns[i] = radioButton;
            group.addView(radioButton);
        }*/
        typedArray.recycle();
        /*//设置当前默认显示的指示序号
        setCurrentItem(0);*/
    }

    public void setShowCount(int count) {
        this.showCount = count;
        createIndicatorView();


    }

    private void createIndicatorView() {
        //记录所有指示器序号控件
        btns = new RadioButton[showCount];
        //创建指示器序号控件
        for (int i = 0; i < showCount; i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setBackgroundResource(background);
            radioButton.setWidth((int) width);
            radioButton.setHeight((int) height);
            radioButton.setLayoutParams(lp);
            radioButton.setEnabled(isClick);
            radioButton.setButtonDrawable(null);
            btns[i] = radioButton;
            group.addView(radioButton);
        }
        //设置当前默认显示的指示序号
        setCurrentItem(0);
    }

    public void setPageView(ViewPager pager) {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                setCurrentItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    public void setCurrentItem(int position) {
        if (position < 0 || position > showCount) {
            return;
        }
        if (btns[position] != null) {
            btns[position].setChecked(true);
        }
    }
}
