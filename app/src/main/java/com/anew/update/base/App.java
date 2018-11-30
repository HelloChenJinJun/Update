package com.anew.update.base;

import android.app.Application;
import android.content.Context;

import com.anew.update.dagger.main.DaggerMainComponent;
import com.anew.update.dagger.main.MainComponent;
import com.anew.update.dagger.main.MainModule;
import com.example.commonlibrary.BaseApplication;
import com.example.commonlibrary.bean.news.OtherNewsTypeBean;
import com.example.commonlibrary.bean.news.VideoTabBean;
import com.example.commonlibrary.module.IAppLife;
import com.example.commonlibrary.utils.FileUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:    Update
 * 创建人:      陈锦军
 * 创建时间:    2018/11/29     10:13
 */
public class App implements IAppLife {

    private static MainComponent mMainComponent;

    @Override
    public void attachBaseContext(Context base) {

    }

    @Override
    public void onCreate(Application application) {
        mMainComponent = DaggerMainComponent.builder().appComponent(BaseApplication.getAppComponent()).mainModule(new MainModule())
                .build();
        initDB(application);


    }

    private void initDB(Application application) {
        if (mMainComponent.getDefaultRepositoryManager()
                .getDaoSession().getOtherNewsTypeBeanDao()
                .queryBuilder().build().list().size() == 0) {
            List<OtherNewsTypeBean> result;
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonElements = jsonParser.parse(FileUtil.readData(application, "NewsChannel")).getAsJsonArray();
            result = new ArrayList<>();
            Gson gson = BaseApplication.getAppComponent().getGson();
            for (JsonElement item :
                    jsonElements) {
                OtherNewsTypeBean bean = gson.fromJson(item, OtherNewsTypeBean.class);
                bean.setHasSelected(true);
                result.add(bean);
            }
            mMainComponent.getDefaultRepositoryManager().getDaoSession().getOtherNewsTypeBeanDao()
                    .insertInTx(result);
        }
        if (mMainComponent.getDefaultRepositoryManager()
                .getDaoSession().getVideoTabBeanDao()
                .queryBuilder().buildCount().count() == 0) {
            List<VideoTabBean> list = new ArrayList<>();
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonElements = jsonParser.parse(FileUtil.readData(application, "videotab")).getAsJsonArray();
            Gson gson = BaseApplication.getAppComponent().getGson();
            for (JsonElement item :
                    jsonElements) {
                VideoTabBean bean = gson.fromJson(item, VideoTabBean.class);
                list.add(bean);
            }
            mMainComponent.getDefaultRepositoryManager().getDaoSession().getVideoTabBeanDao()
                    .insertInTx(list);
        }


    }


    public static MainComponent getMainComponent() {
        return mMainComponent;
    }

    @Override
    public void onTerminate(Application application) {

    }
}
