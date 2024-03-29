package com.clicagency.lastfmapp.view.activities;

import android.os.Handler;

import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.view.base.BaseActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    public void init_activity() {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                showActivity(MainActivity.class,true,false);
            }
        }, 2000);

    }

    @Override
    public void init_events() {

    }

    @Override
    public void set_fragment_place() {

    }

    @Override
    protected int getNavigationHostRes() {
        return 0;
    }
}
