package com.ddm.veggie.view.components;

import android.content.Context;
import android.util.AttributeSet;

import com.ddm.veggie.R;

public class VeggieButton extends androidx.appcompat.widget.AppCompatButton {

    public VeggieButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.veggie_button, context.getTheme()));
        setTextColor(getResources().getColor(R.color.white, context.getTheme()));
    }
}
