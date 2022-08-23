package com.clicagency.lastfmapp.view.activities;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;

import androidx.navigation.NavGraph;
import androidx.navigation.NavOptions;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.databinding.ActivityMainBinding;
import com.clicagency.lastfmapp.tools.BasicTools;
import com.clicagency.lastfmapp.view.base.BaseActivity;
import com.clicagency.lastfmapp.view.fragments.mainFragment.MainPageFragment;
import com.clicagency.lastfmapp.view.fragments.searchArtistFragment.SearchArtistsFragment;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    public MainPageFragment main_fragment;
    public SearchArtistsFragment mainSearchFragment;
    public AHBottomNavigation bottomNavigation;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected int getNavigationHostRes() {
        return R.id.nav_host_fragment;
    }

    @Override
    public void init_activity() {

        BasicTools.hideSoftKeyboard_adjust(MainActivity.this);
        initBottomNavigation();
//        dataBinding.bottomNavigation.setCurrentItem(0);


    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    public void init_events() {
        dataBinding.bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                NavOptions navOptions = new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setRestoreState(false)
                        .setPopUpTo(NavGraph.findStartDestination(navController.getGraph()).getId(),
                                false, // inclusive
                                true) // saveState
                        .build();

                if (wasSelected) {
                    switch (position) {
                        case 0:
//                            main_fragment = MainPageFragment.newInstance();
//
//                            show_fragment2(main_fragment, true, false);

//                            navController.popBackStack(R.id.mainPageFragment,true);
//                            navController.navigate(R.id.mainPageFragment);
                            return true;

                        case 1:
//                            mainSearchFragment = SearchArtistsFragment.newInstance();
//                            show_fragment2(mainSearchFragment, true, false);

//                            navController.popBackStack(R.id.searchArtistFragment,true);
//                            navController.navigate(R.id.searchArtistFragment);
                            return true;
                    }
                    return false;
                }

                switch (position) {
                    case 0:
//                        if (main_fragment == null)
//                            main_fragment = MainPageFragment.newInstance();
//                        show_fragment2(main_fragment, true, false);
                        // openFab();

//                        navController.popBackStack();

                        navController.getGraph().setStartDestination(R.id.mainPageFragment);
                        navController.navigate(R.id.mainPageFragment,null,navOptions);
                        return true;

                    case 1:
//                        if (mainSearchFragment == null)
//                            mainSearchFragment = SearchArtistsFragment.newInstance();
//                        show_fragment2(mainSearchFragment, true, false);
//                        navController.popBackStack();
                        navController.navigate(R.id.searchArtistFragment,null,navOptions);

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

    public void showBottomNavigation() {
        dataBinding.bottomNavigation.restoreBottomNavigation();
    }

    public void hideBottomNavigation() {
        dataBinding.bottomNavigation.hideBottomNavigation();
    }
}
