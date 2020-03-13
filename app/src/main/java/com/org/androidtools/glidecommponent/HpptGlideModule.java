package com.org.androidtools.glidecommponent;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.org.androidtools.retrofit.HttpMethod;

import java.io.InputStream;

/**
 *
 <meta-data
 android:name="com.synwing.chealth.glidecomponent.HttpGlideModule"
 android:value="AppGlideModule" />
 配置在manifest里面
 */
public class HpptGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory(HttpMethod.getClient()));
    }
}
