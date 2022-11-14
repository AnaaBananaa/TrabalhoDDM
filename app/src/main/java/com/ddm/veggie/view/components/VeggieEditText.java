package com.ddm.veggie.view.components;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.ddm.veggie.R;

public class VeggieEditText extends androidx.appcompat.widget.AppCompatEditText {
    private static final int[] STATE_WRONG = {R.attr.state_wrong};
    private boolean isWrong;

    public VeggieEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.veggie_edit_text, context.getTheme()));
        isWrong = false;
    }

    public boolean isWrong() {
        return isWrong;
    }

    public void enableWrong() {
        isWrong = true;
    }

    public void disableWrong() {
        isWrong = false;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 2);
        if (isWrong) {
            mergeDrawableStates(drawableState, STATE_WRONG);
        }
        return drawableState;
    }
}
