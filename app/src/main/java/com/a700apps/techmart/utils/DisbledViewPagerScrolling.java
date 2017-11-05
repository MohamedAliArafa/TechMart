package com.a700apps.techmart.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by khaled.badawy on 11/1/2017.
 */

public class DisbledViewPagerScrolling extends ViewPager {

    private boolean isPagingEnabled = false;

    public DisbledViewPagerScrolling(Context context) {
        super(context);
    }

    public DisbledViewPagerScrolling(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }
}
