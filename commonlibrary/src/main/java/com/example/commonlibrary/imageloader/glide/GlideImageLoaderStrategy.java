package com.example.commonlibrary.imageloader.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.commonlibrary.imageloader.base.BaseImageLoaderStrategy;

/**
 * Created by COOTEK on 2017/7/31.
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<GlideImageLoaderConfig> {
    @Override
    public void loadImage(Context context, GlideImageLoaderConfig config) {
        if (config == null || context == null) {
            return;
        }
        RequestBuilder<Drawable> drawableRequestBuilder;
        drawableRequestBuilder = Glide.with(context).load(config.getUrl());
        RequestOptions options = new RequestOptions();
        switch (config.getCacheStrategy()) {
            case GlideImageLoaderConfig.CACHE_ALL:
                options.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case GlideImageLoaderConfig.CACHE_NONE:
                options.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case GlideImageLoaderConfig.CACHE_SOURCE:
                options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case GlideImageLoaderConfig.CACHE_RESULT:
                options.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            default:
                break;
        }
        if (config.isCenterInside()) {
            options = options.fitCenter();
        } else {
            options = options.centerCrop();
        }
        if (config.getBitmapTransformation() != null) {
            options = options.transforms(config.getBitmapTransformation());
        }
        if (config.getErrorResId() != 0) {
            options = options.error(config.getErrorResId());
        }
        if (config.getPlaceHolderResId() != 0) {
            options = options.placeholder(config.getPlaceHolderResId());
        }
        drawableRequestBuilder.apply(options).into(config.getImageView());
    }
}
