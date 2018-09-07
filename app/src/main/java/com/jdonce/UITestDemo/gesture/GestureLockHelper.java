package com.jdonce.UITestDemo.gesture;

import android.content.Context;
import android.text.TextUtils;

/**
 * 手势密码帮助类 主要提供密码设置和解锁的页面操作逻辑
 * Created by MJ on 2018/8/30.
 */

public class GestureLockHelper {
    private static final int MIN_SIZE = 6;//设置密码点的最少数量
    private static final int MAX_TIMES = 5;//解锁时最多错误次数
    private static final String GESTURE_PWD_KEY = "GESTURE_PWD_KEY";

    private String message;
    private String tmpPwd;
    private int times;
    private boolean isFinish;
    private boolean isOk;

    /**
     * 验证设置
     *
     * @param context
     * @param password
     */
    public void validateForSetting(Context context, String password) {
        this.isFinish = false;
        this.isOk = false;
        if (TextUtils.isEmpty(password) || password.length() < MIN_SIZE) {
            this.tmpPwd = null;
            this.message = getSizeErrorMsg();
            return;
        }
        //第一次输入
        if (TextUtils.isEmpty(this.tmpPwd)) {
            this.tmpPwd = password;
            this.message = getReDrawMsg();
            this.isOk = true;
            return;
        }
        //第二次输入
        if (this.tmpPwd.equalsIgnoreCase(password)) {
            this.message = getSettingSuccessMsg();
            saveToStorage(this.tmpPwd, context);
            this.isOk = true;
            this.isFinish = true;
        } else {
            this.tmpPwd = null;
            this.message = getDiffPreErrorMsg();
        }
    }

    /**
     * 验证解锁
     *
     * @param password
     * @param context
     */
    public void validateForChecking(String password, Context context) {
        this.isOk = false;

        if (TextUtils.isEmpty(password) || (password.length() < MAX_TIMES)) {
            this.times++;
            this.isFinish = this.times >= MAX_TIMES;
            this.message = getPwdErrorMsg();
            return;
        }

        String storagePwd = getFromStorage(context);
        if (!TextUtils.isEmpty(storagePwd) && storagePwd.equalsIgnoreCase(password)) {
            this.message = getCheckingSuccessMsg();
            this.isOk = true;
            this.isFinish = true;
        } else {
            this.times++;
            this.isFinish = this.times >= MAX_TIMES;
            this.message = getPwdErrorMsg();
        }
    }

    /**
     * 是否有手势密码
     *
     * @param context
     * @return
     */
    public static boolean isHasGesturePassword(Context context) {
        String storagePwd = getFromStorage(context);
        return !TextUtils.isEmpty(storagePwd);
    }

    private String getSizeErrorMsg() {
        return String.format("手势密码至少连接个%d点，请重新输入", MIN_SIZE);
    }

    private String getReDrawMsg() {
        return "请再次输入手势密码";
    }

    private String getSettingSuccessMsg() {
        return "设置成功！";
    }

    private String getDiffPreErrorMsg() {
        return "两次输入不一致，请重新输入";
    }

    private String getPwdErrorMsg() {
        return String.format("密码错误，还剩%d次机会", getRemainTimes());
    }

    private void saveToStorage(String gesturePwd, Context context) {
        PreferencesUtils.putString(context, GESTURE_PWD_KEY, gesturePwd);
    }

    private String getCheckingSuccessMsg() {
        return "解锁成功！";
    }

    private static String getFromStorage(Context context) {
        return PreferencesUtils.getString(context, GESTURE_PWD_KEY);
    }

    private int getRemainTimes() {
        return (times < 5) ? (MAX_TIMES - times) : 0;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public boolean isOk() {
        return isOk;
    }
}
