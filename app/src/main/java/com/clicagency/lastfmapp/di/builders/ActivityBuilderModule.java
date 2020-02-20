package com.clicagency.lastfmapp.di.builders;

import com.clicagency.lastfmapp.view.activities.MainActivity;
import com.clicagency.lastfmapp.view.activities.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


/**
 * The module which provides the android injection service to Activities.
 *
 */
@Module
public abstract class ActivityBuilderModule {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract MainActivity mainActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract SplashActivity splashActivity();


}
