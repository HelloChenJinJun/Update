package com.anew.update.dagger.main;

import com.example.commonlibrary.dagger.component.AppComponent;
import com.example.commonlibrary.dagger.scope.PerApplication;
import com.example.commonlibrary.repository.DefaultRepositoryManager;

import dagger.Component;

/**
 * 项目名称:    Update
 * 创建人:      陈锦军
 * 创建时间:    2018/11/29     10:20
 */
@PerApplication
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {
    public DefaultRepositoryManager getDefaultRepositoryManager();
}
