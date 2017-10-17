package com.a700apps.techmart.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by samir.salah on 10/15/2017.
 */

public class CustomeEditText extends android.support.v7.widget.AppCompatEditText{


    public CustomeEditText(Context context) {
        super(context);

        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/" +"dax-light-5978520ee6c5f.otf");
        this.setTypeface(face);
    }

    public CustomeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/" +"dax-light-5978520ee6c5f.otf");
        this.setTypeface(face);
    }

    public CustomeEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(),"fonts/" +"dax-light-5978520ee6c5f.otf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }
}
