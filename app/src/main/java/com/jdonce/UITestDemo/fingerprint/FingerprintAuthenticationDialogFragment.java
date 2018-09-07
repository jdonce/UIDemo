package com.jdonce.UITestDemo.fingerprint;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jdonce.UITestDemo.R;

/**
 * 指纹解锁dialog
 * Created by MJ on 2018/8/29.
 */

public class FingerprintAuthenticationDialogFragment extends DialogFragment implements FingerprintHelper.Callback {
    private FingerprintHelper fingerprintHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Do not create a new Fragment when the Activity is re-created such as orientation changes.
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fingerprint_dialog, container, false);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        fingerprintHelper = new FingerprintHelper(getContext());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fingerprintHelper.startListening(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        fingerprintHelper.stopListening();
    }

    @Override
    public void onAuthenticated() {
        dismiss();
        Toast.makeText(getActivity(), "验证成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String errString) {
        dismiss();
        Toast.makeText(getActivity(), errString, Toast.LENGTH_SHORT).show();
    }
}
