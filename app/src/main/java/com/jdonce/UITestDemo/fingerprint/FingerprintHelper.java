package com.jdonce.UITestDemo.fingerprint;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.util.Log;

/**
 * 指纹解锁帮助类
 * Created by MJ on 2018/8/29.
 */
@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHelper extends FingerprintManager.AuthenticationCallback {

    private final String TAG = "FingerprintHelper";

    private Callback callback;

    private KeyguardManager keyguardManager;
    private FingerprintManager mFingerprintManager;
    private CancellationSignal mCancellationSignal;


    private boolean mSelfCancelled;


    public FingerprintHelper(Context context) {
        mFingerprintManager = getFingerprintManager(context);
        keyguardManager = getKeyguardManager(context);
    }


    private FingerprintManager getFingerprintManager(Context context) {
        return context.getSystemService(FingerprintManager.class);
    }

    private KeyguardManager getKeyguardManager(Context context) {
        return context.getSystemService(KeyguardManager.class);
    }

    /**
     * 设备硬件是否支持
     *
     * @return
     */
    public boolean isHardwareDetected() {
        return mFingerprintManager.isHardwareDetected();
    }

    /**
     * 是否录入至少一个指纹
     *
     * @return
     */
    public boolean isHasEnrolledFingerprints() {
        return mFingerprintManager.hasEnrolledFingerprints();
    }

    /**
     * 判断设备是否有设置屏幕保护锁
     *
     * @return
     */
    public boolean isKeyguardSecure() {
        return keyguardManager != null && keyguardManager.isKeyguardSecure();
    }

    /**
     * 判断硬件是否支持且录入至少一个指纹
     *
     * @return
     */
    private boolean isFingerprintAuthAvailable() {
        return mFingerprintManager.isHardwareDetected()
                && mFingerprintManager.hasEnrolledFingerprints();
    }

    public void startListening(Callback callback) {
        this.callback = callback;
        Log.d(TAG, "startListening: ");
        if (!isFingerprintAuthAvailable()) {
            if (callback != null) {
                callback.onError(isHardwareDetected() ? "没有设置指纹" : "设备不支持");
            }
            return;
        }
        mCancellationSignal = new CancellationSignal();
        mSelfCancelled = false;
        mFingerprintManager
                .authenticate(null, mCancellationSignal, 0, this, null);
    }

    public void stopListening() {
        if (mCancellationSignal != null) {
            mSelfCancelled = true;
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        if (!mSelfCancelled) {
            Log.e(TAG, "onAuthenticationError: " + errString);
            if (callback != null) {
                callback.onError(String.valueOf(errString));
            }
        }
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Log.e(TAG, "onAuthenticationHelp: " + helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        Log.e(TAG, "onAuthenticationFailed: ");
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Log.e(TAG, "onAuthenticationSucceeded: ");
        if (callback != null) {
            callback.onAuthenticated();
        }
    }

    public interface Callback {

        void onAuthenticated();

        void onError(String errString);
    }
}
