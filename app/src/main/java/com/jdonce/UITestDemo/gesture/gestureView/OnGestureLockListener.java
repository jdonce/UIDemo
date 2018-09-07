package com.jdonce.UITestDemo.gesture.gestureView;

/**
 * 手势解锁监听器
 * Created by MJ on 2018/8/30.
 */

public interface OnGestureLockListener {
    /**
     * 监听视图解锁开始（手指按下）
     */
    void onStarted();

    /**
     * 图案解锁内容改变
     *
     * @param progress 解锁进度（数字字符串）
     */
    void onProgress(String progress);

    /**
     * 图案解锁完成
     *
     * @param result 解锁结果（数字字符串）
     */
    void onComplete(String result);
}
