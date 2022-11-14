package com.ddm.veggie.view.components;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;

import com.ddm.veggie.R;

public class LoadingDialog extends DialogFragment {
    public static final String TAG = "LoadingDialog";
    private String action;

    public LoadingDialog(String action) {
        this.action = action;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_LoadingDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading, container, false);
        ViewCompat.setBackground(view, new ColorDrawable(Color.TRANSPARENT));
        TextView txtAction = view.findViewById(R.id.dialog_loading_txt_action);
        txtAction.setText(action);
        return view;
    }
}
