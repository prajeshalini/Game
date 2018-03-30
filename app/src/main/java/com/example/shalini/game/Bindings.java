package com.example.shalini.game;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Shalini Prajesh on 28/3/18.
 */

public class Bindings {
    static int count = 0;
    private static final String TAG = "Bindings";

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl){
//        Picasso.with(view.getContext())
//                .load(imageUrl)
//                .resize(200,200)
//                .centerInside()
//                .placeholder(R.mipmap.ic_launcher)
//                .into(view);
        Glide.with(view.getContext())
                .load(imageUrl)
                .crossFade()
                .error(R.mipmap.ic_launcher)
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        count++;
//                        if (count == 9){
//                           listener.setImageLoadingCompleted(true);
//                        }
//                        return false;
//                    }
//                })
                .into(view);
    }
}
