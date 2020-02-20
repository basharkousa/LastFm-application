package com.clicagency.lastfmapp;


import com.clicagency.lastfmapp.di.components.AppComponent;
import com.clicagency.lastfmapp.di.components.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class ZLastFmApplication extends DaggerApplication {

    private static ZLastFmApplication sInstance;
    public static ZLastFmApplication getAppContext() {
        return sInstance;
    }

    private static synchronized void setInstance(ZLastFmApplication app) {
        sInstance = app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

}
