package com.opengl.jackn.testqr;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class pushButton extends Button {
    public pushButton(Context context) {
        super(context);
    }
    public pushButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if(pressed){
            this.setScaleY(0.92f);
            this.setScaleX(0.96f);
            this.setAlpha(0.7f);
        }else{
            this.setScaleY(1.0f);
            this.setScaleX(1.0f);
            this.setAlpha(1.0f);
        }
        super.setPressed(pressed);
    }

}
