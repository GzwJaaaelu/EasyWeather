package com.android.easy.weather.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by admin on 2016/11/14.
 */

public class ProgressDialogFragment extends DialogFragment {

    /**
     * 获取ProgressDialogFragment实例并传过来要显示的数据
     * @param message
     * @return
     */
    public static ProgressDialogFragment newInstance(String message) {
        Bundle args = new Bundle();
        args.putString("message", message);
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        String message = getArguments().getString("message");
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        // etc...
        return dialog;
    }
}
