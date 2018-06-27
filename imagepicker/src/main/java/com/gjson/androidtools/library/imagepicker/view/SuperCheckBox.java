package com.gjson.androidtools.library.imagepicker.view;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.SoundEffectConstants;

/**
 Created by gjson on 2018/6/22.
 * 描    述：带声音的CheckBox
 */
public class SuperCheckBox extends AppCompatCheckBox {

    public SuperCheckBox(Context context) {
        super(context);
    }

    public SuperCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean performClick() {
        final boolean handled = super.performClick();
        if (!handled) {
            // View only makes a sound effect if the onClickListener was
            // called, so we'll need to make one here instead.
            playSoundEffect(SoundEffectConstants.CLICK);
        }
        return handled;
    }
}
