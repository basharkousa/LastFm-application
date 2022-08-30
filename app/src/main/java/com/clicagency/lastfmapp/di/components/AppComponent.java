package com.clicagency.lastfmapp.di.components;

import android.app.Application;

import com.clicagency.lastfmapp.ZLastFmApplication;
import com.clicagency.lastfmapp.di.builders.ActivityBuilderModule;
//import com.clicagency.lastfmapp.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * The main application component which initializes all the dependent modules
 * <p>
 * To reduce boilerplate code review this link
 * https://medium.com/@iammert/new-android-injector-with-dagger-2-part-1-8baa60152abe
 */

//@Singleton
//@Component(modules = {AppModule.class, AndroidInjectionModule.class, ActivityBuilderModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(ZLastFmApplication app);

    @Override
    void inject(DaggerApplication instance);

//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        Builder application(Application application);
//
//        AppComponent build();
//    }
}