//package com.a700apps.techmart.utils;
//
//import android.content.Context;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.GlideBuilder;
//import com.bumptech.glide.load.DecodeFormat;
//import com.bumptech.glide.module.GlideModule;
//
///**
// * Created by khaled.badawy on 10/25/2017.
// */
//
//public class GlideConfiguration implements GlideModule {
//
//    @Override
//    public void applyOptions(Context context, GlideBuilder builder) {
//        // Apply options to the builder here.
//        // Glide default Bitmap Format is set to RGB_565 since it
//        // consumed just 50% memory footprint compared to ARGB_8888.
//        // Increase memory usage for quality with:
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
//    }
//
//    @Override
//    public void registerComponents(Context context, Glide glide) {
//
//    }
//
//}