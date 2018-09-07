package com.jdonce.UITestDemo.dragView;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/**
 * 拖动view验证的帮助类
 * Created by MJ on 2018/9/5.
 */

public class DragVerifyHelper implements View.OnTouchListener {

    private View dragView;//移动的View
    private View targetView;//目标View
    /**
     * 限制可移动的范围
     */
    private int borderLeft;//移动范围的左边界
    private int borderTop;//移动范围的上边界
    private int borderRight;//移动范围的右边界
    private int borderBottom;//移动范围的下边界

    private OnTouchCallBack onTouchCallBack;//移动后的回调

    /**
     * 记录按下的坐标
     */

    private float downX;
    private float downY;
    /**
     * 记录移动view的初始位置  用于当移动view未到达指定区域时 回到初始位置
     */
    private int originTop;//记录移动View最初的顶部位置
    private int originBottom;//记录移动View最初的下部位置
    private int originLeft;//记录移动View最初的左边位置
    private int originRight;//记录移动View最初的右边位置

    private boolean isIntersect;//记录两view是否相交在指定区域   如若不符合要求 则需更改下面的判断

    /**
     * @param dragView        被移动的view
     * @param targetView      目标view  也就是移动view最终需到达的view
     * @param containerView   容器view  根据此view限制移动view的移动范围
     * @param onTouchCallBack 回调方法
     */

    public DragVerifyHelper(View dragView, View targetView, View containerView,
                            OnTouchCallBack onTouchCallBack) {
        if (dragView == null) {
            throw new RuntimeException("dragView is null");
        }
        if (targetView == null) {
            throw new RuntimeException("targetView is null");
        }

        if (containerView == null) {
            throw new RuntimeException("containerView is null");
        }

        this.dragView = dragView;

        this.originTop = dragView.getTop();
        this.originBottom = dragView.getBottom();
        this.originLeft = dragView.getLeft();
        this.originRight = dragView.getRight();

        this.targetView = targetView;

        this.borderLeft = containerView.getLeft();
        this.borderTop = containerView.getTop();
        this.borderRight = containerView.getRight();
        this.borderBottom = containerView.getBottom();

        this.onTouchCallBack = onTouchCallBack;
    }

    /**
     * 开始拖动验证
     */
    public void startDragVerify() {
        if ((this.borderLeft == 0 && this.borderRight == 0
                || this.borderTop == 0 && this.borderBottom == 0)
                && this.onTouchCallBack != null) {
            onTouchCallBack.onError("containerView no size");
            return;
        }
        this.dragView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                v.setPressed(true);
                v.setScaleX((float) 1.2);
                v.setScaleY((float) 1.2);
                break;
            case MotionEvent.ACTION_MOVE:
                final float xDistance = event.getX() - downX;
                final float yDistance = event.getY() - downY;
                if (xDistance != 0 && yDistance != 0) {
                    int l = (int) (v.getLeft() + xDistance);
                    int r = (int) (v.getRight() + xDistance);
                    int t = (int) (v.getTop() + yDistance);
                    int b = (int) (v.getBottom() + yDistance);

                    if (l < borderLeft) {
                        l = borderLeft;
                        r = l + v.getWidth();
                    }
                    if (r > borderRight) {
                        r = borderRight;
                        l = r - v.getWidth();
                    }
                    if (t < borderTop) {
                        t = borderTop;
                        b = t + v.getHeight();
                    }
                    if (b > borderBottom) {
                        b = borderBottom;
                        t = b - v.getHeight();
                    }
                    v.layout(l, t, r, b);
                    //判断移动的view与指定区域（在目标view范围内 比目标view范围小）是否有交集 如要求只需移动的view与目标view是否有交集请使用isHasIntersectArea(v)
                    isIntersect = isIntersectAssignArea(v);
                    if (onTouchCallBack != null) {
                        onTouchCallBack.impactResult(isIntersect);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                v.setPressed(false);
                v.setScaleX(1);
                v.setScaleY(1);
                int left;
                int top;
                int right;
                int bottom;
                if (isIntersect) {
                    left = targetView.getLeft();
                    top = targetView.getTop();
                    right = targetView.getRight();
                    bottom = targetView.getBottom();
                } else {
                    left = originLeft;
                    top = originTop;
                    right = originRight;
                    bottom = originBottom;
                }
                v.layout(left, top, right, bottom);
                if (onTouchCallBack != null) {
                    onTouchCallBack.verifyResult(isIntersect);
                }
                break;
        }
        return true;
    }


    /**
     * 判断移动的view与目标view是否有交集
     *
     * @param view 被移动的view
     * @return
     */
    private boolean isHasIntersectArea(View view) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        Rect targetRect = new Rect();
        targetView.getGlobalVisibleRect(targetRect);
        return Rect.intersects(rect, targetRect);
    }

    /**
     * 判断移动的view与指定区域是否有交集  指定区域以目标区域范围为参考设置  视感上会受view背景图影响  如view背景图四周有空白  后续需根据背景图做相应调整
     *
     * @param view 被移动的view
     * @return
     */
    private boolean isIntersectAssignArea(View view) {
        int width = targetView.getWidth() / 3; //目标区域内部的三分之一宽
        int height = targetView.getHeight() / 3;//目标区域内部的三分之一高
        //设置目标区域可触发的范围
        int limitLeft = targetView.getLeft() + width;
        int limitRight = targetView.getRight() - width;
        int limitTop = targetView.getTop() + height;
        int limitBottom = targetView.getBottom() - height;
        Rect limitRect = new Rect(limitLeft, limitTop, limitRight, limitBottom);
        //获取移动view的范围
        int left = view.getLeft();
        int right = view.getRight();
        int top = view.getTop();
        int bottom = view.getBottom();
        Rect rect = new Rect(left, top, right, bottom);
        //比较两者范围是否有交集
        return Rect.intersects(rect, limitRect);
    }

    interface OnTouchCallBack {
        //碰撞目标view的结果回调
        void impactResult(boolean isImpact);

        //验证结果  移动view与目标view叠在一起为成功
        void verifyResult(boolean isSuccess);

        void onError(String msg);
    }
}

