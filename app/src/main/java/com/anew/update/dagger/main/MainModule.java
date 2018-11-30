package com.anew.update.dagger.main;

import com.anew.update.interceptor.CacheControlInterceptor;
import com.anew.update.interceptor.IndexInterceptor;
import com.anew.update.util.NewsUtil;
import com.example.commonlibrary.bean.chat.DaoSession;
import com.example.commonlibrary.dagger.scope.PerApplication;
import com.example.commonlibrary.mvp.model.DefaultModel;
import com.example.commonlibrary.repository.DefaultRepositoryManager;
import com.example.commonlibrary.utils.Constant;

import java.io.File;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 项目名称:    Update
 * 创建人:      陈锦军
 * 创建时间:    2018/11/29     10:21
 */
@Module
public class MainModule {
    @Provides
    public DefaultRepositoryManager provideRepositoryManager(@Named("index") Retrofit retrofit, DaoSession daoSession) {
        return new DefaultRepositoryManager(retrofit, daoSession);
    }


    @Provides
    public DefaultModel providerModel(DefaultRepositoryManager defaultRepositoryManager) {
        return new DefaultModel(defaultRepositoryManager);
    }


    @Provides
    @Named("index")
    @PerApplication
    public Retrofit provideRetrofit(@Named("index") OkHttpClient okHttpClient, Retrofit.Builder builder) {
        builder.baseUrl(NewsUtil.BASE_URL).client(okHttpClient);
        return builder.build();
    }


    @Provides
    @Named("index")
    @PerApplication
    public OkHttpClient provideOkHttpClient(@Named("index") IndexInterceptor interceptor, File cacheFile, OkHttpClient.Builder builder) {
        CacheControlInterceptor cacheControlInterceptor = new CacheControlInterceptor();
        builder.addInterceptor(interceptor)
                .addInterceptor(cacheControlInterceptor)
                .addNetworkInterceptor(cacheControlInterceptor)
                .cache(new Cache(new File(cacheFile.getAbsolutePath(), "news"),
                        1024 * 1024 * 100));
        builder.followRedirects(false);
        return builder.build();
    }


    @Provides
    @Named("index")
    @PerApplication
    public IndexInterceptor provideNewsInterceptor() {
        return new IndexInterceptor();
    }

}
