package com.clicagency.lastfmapp.databinding;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.clicagency.lastfmapp.tools.BasicTools;

final class BindingAdapters {

    private BindingAdapters() {
    }

    @BindingAdapter("imgUrl")
    public static void loadImage(View view , String imgUrl){
        ImageView imageView = (ImageView)view;
        Glide.with(imageView.getContext()).asBitmap().load(imgUrl).dontAnimate().into(imageView);
    }

    @BindingAdapter("duration_value")
    public static void formatSecondd(View view , String duration){
        TextView textView = (TextView) view;
        int durationInt =Integer.parseInt(duration);
        textView.setText(BasicTools.formatSeconds(durationInt));
    }

//    @BindingAdapter("app:goneUnless")
//    public static void goneUnless(View view, Boolean visible) {
//        view.visibility = visible ? View.VISIBLE : View.GONE;
//    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view,Boolean visible) {
        if (visible) {
            view.setVisibility( View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }



}
