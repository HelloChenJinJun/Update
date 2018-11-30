package com.anew.update.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bumptech.glide.Glide;
import com.example.commonlibrary.BaseApplication;
import com.zzhoujay.richtext.callback.BitmapStream;
import com.zzhoujay.richtext.ig.ImageDownloader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/**
 * 项目名称:    Update
 * 创建人:      陈锦军
 * 创建时间:    2018/11/30     17:23
 */
public class DefaultRichTextLoader implements ImageDownloader {


    @Override
    public BitmapStream download(String source) throws IOException {
        return new DefaultBitmapStream(source);
    }


    private class DefaultBitmapStream implements BitmapStream {

        private String url;


        public DefaultBitmapStream(String url) {
            this.url = url;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            Bitmap bm;
            try {
                bm = Glide.with(BaseApplication.getAppComponent().getActivityManager().getCurrentActivity())
                        .asBitmap().load(url).submit().get();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
                return isBm;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void close() throws IOException {

        }
    }
}
