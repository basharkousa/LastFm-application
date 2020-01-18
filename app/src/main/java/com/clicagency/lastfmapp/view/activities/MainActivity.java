package com.clicagency.lastfmapp.view.activities;

import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.databinding.ActivityMainBinding;
import com.clicagency.lastfmapp.tools.BasicTools;
import com.clicagency.lastfmapp.view.base.BaseActivity;
import com.clicagency.lastfmapp.view.fragments.MainPageFragment;
import com.clicagency.lastfmapp.view.fragments.SearchArtistsFragment;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    public MainPageFragment main_fragment;
    public SearchArtistsFragment mainSearchFragment;
    public AHBottomNavigation bottomNavigation;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void init_activity() {
        BasicTools.hideSoftKeyboard_adjust(MainActivity.this);
        initBottomNavigation();
        dataBinding.bottomNavigation.setCurrentItem(0);

    }


    @Override
    public void init_events() {
        dataBinding.bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if (wasSelected) {
                    switch (position) {
                        case 0:
                            main_fragment = MainPageFragment.newInstance();
                            show_fragment2(main_fragment, true, false);
                            return true;

                        case 1:
                            mainSearchFragment = SearchArtistsFragment.newInstance();
                            show_fragment2(mainSearchFragment, true, false);
                            return true;
                    }
                    return false;
                }

                switch (position) {
                    case 0:
                        if (main_fragment == null)
                            main_fragment = MainPageFragment.newInstance();
                        show_fragment2(main_fragment, true, false);
                        // openFab();
                        return true;

                    case 1:
                        if (mainSearchFragment == null)
                            mainSearchFragment = SearchArtistsFragment.newInstance();
                        show_fragment2(mainSearchFragment, true, false);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void set_fragment_place() {
        this.fragment_place = (ViewGroup) dataBinding.fragmentPlace;
    }

    public void initBottomNavigation() {
        Log.e("initBottomNavigation", "success");
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getResources().getString(R.string.home), R.drawable.home_new);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getResources().getString(R.string.search), R.drawable.serchh);

        dataBinding.bottomNavigation.addItem(item1);
        dataBinding.bottomNavigation.addItem(item2);

        //bottomNavigation.disableItemAtPosition(4);

        //bottomNavigation.setCurrentItem(1);
        dataBinding.bottomNavigation.setBehaviorTranslationEnabled(false);

        // Manage titles
        //dataBinding. bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        dataBinding.bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        //bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        dataBinding.bottomNavigation.setDefaultBackgroundColor(Color.WHITE);
        dataBinding.bottomNavigation.setAccentColor(fetchColor(R.color.colorAccent));
        dataBinding.bottomNavigation.setInactiveColor(fetchColor(R.color.medium_light_gray));

    }

    public void showBottomNavigation(){
        dataBinding.bottomNavigation.restoreBottomNavigation();
    }
    public void hideBottomNavigation(){
        dataBinding.bottomNavigation.hideBottomNavigation();
    }
}
