package com.a700apps.techmart.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by samir.salah on 9/26/2017.
 */

public class CustomButton extends android.support.v7.widget.AppCompatButton{

    public CustomButton(Context context) {
        super(context);

        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/" +"dax-bold-59784f71222f0.ttf");
        this.setTypeface(face);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/" +"dax-bold-59784f71222f0.ttf");
        this.setTypeface(face);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/" +"dax-bold-59784f71222f0.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }
}
