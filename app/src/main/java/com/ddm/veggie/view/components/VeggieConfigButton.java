package com.ddm.veggie.view.components;

import android.content.Context;
import android.util.AttributeSet;

import com.ddm.veggie.R;

public class VeggieConfigButton extends androidx.appcompat.widget.AppCompatButton {

    public VeggieConfigButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.veggie_config_button, context.getTheme()));
        setTextColor(getResources().getColor(R.color.black, context.getTheme()));
    }
}
