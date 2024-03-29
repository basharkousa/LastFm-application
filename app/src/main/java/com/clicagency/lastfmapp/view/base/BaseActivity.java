package com.clicagency.lastfmapp.view.base;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.transition.Fade;
import androidx.transition.TransitionInflater;

import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.tools.BasicTools;
import com.clicagency.lastfmapp.tools.DataMessage;
import com.clicagency.lastfmapp.tools.ObjectMessage;

import dagger.android.support.DaggerAppCompatActivity;
import dagger.hilt.android.AndroidEntryPoint;


public abstract class BaseActivity<D extends ViewDataBinding> extends AppCompatActivity
//        extends DaggerAppCompatActivity
{

    @SuppressWarnings("unused")
    public D dataBinding;
    protected ViewGroup fragment_place;
    protected BaseFragment current_fragment;

    public NavController navController;



    @LayoutRes
    protected abstract int getLayoutRes();


    protected abstract int getNavigationHostRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, getLayoutRes());
//        navHostFragment =
//                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//        navController = navHostFragment.getNavController();
//        navController = Navigation.findNavController(this,R.id.navigation_header_container);

        if(getNavigationHostRes()>0){
            // Get the navigation host fragment from this Activity
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(getNavigationHostRes());
            // Instantiate the navController using the NavHostFragment
            navController = navHostFragment.getNavController();
        }


        set_fragment_place();
        init_events();
        init_activity();
    }


    public abstract void init_activity();

    public abstract void init_events();

    public abstract void set_fragment_place();



    @Override
    public void onBackPressed() {
        BasicTools.logMessage("Activity","onBackPressed "+navController.getBackQueue().getSize());

        if (current_fragment != null && current_fragment.onBackPressed()) {
            current_fragment.cancel();
            //todo
            //current_fragment.setForward(false);
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                //finish();
                doubleClickToExit();
            } else {
                current_fragment = (BaseFragment) getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1);
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    private static long back_pressed;

    private void doubleClickToExit() {
        if ((back_pressed + 2000) > System.currentTimeMillis())
            finish();
            //super.onBackPressed();
        else
            showToastMessageShort(R.string.double_click);
        back_pressed = System.currentTimeMillis();

    }

    public void navigateTo(int fragmentId,Bundle args){
//        current_fragment = (BaseFragment) navController.getContext(). ;
//        NavBackStackEntry navBackStackEntry;
//        navBackStackEntry.
//        navController.getCurrentBackStackEntry().
        navController.navigate(fragmentId,args);
    }

    public void show_fragment2(final BaseFragment fragment, final boolean clearStack) {
        show_fragment2(fragment, clearStack, false);
    }

    public void clearStack(int remaining) {
        while (getSupportFragmentManager().getBackStackEntryCount() > remaining) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    public void show_fragment2(final BaseFragment fragment, final boolean clearStack, final boolean main) {
        if (current_fragment != fragment) {
            if (fragment_place != null) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (current_fragment != null)
                            current_fragment.removeSnackbar();
                        if (clearStack) {
                            while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                                getSupportFragmentManager().popBackStackImmediate();
                            }
                            if (main) {
                                while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                                    getSupportFragmentManager().popBackStackImmediate();
                                }
                            }
                        }
                        replaceFragment(fragment);
                        current_fragment = fragment;
                    }
                };
                runOnUiThread(runnable);
            }
        }
    }

    public void show_fragment(final BaseFragment fragment) {
        show_fragment(fragment, true);
    }

    public void show_fragment(final BaseFragment fragment, final boolean forward) {
        if (current_fragment != fragment) {
            if (fragment_place != null) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (current_fragment != null)
                            current_fragment.removeSnackbar();
                        //fragment.setForward(forward);
                        replaceFragment(fragment);
                        current_fragment = fragment;
                    }
                };
                runOnUiThread(runnable);
            }
        }
    }

    public void show_fragment(final BaseFragment fragment, final View originView, final String sharedView, final boolean forward) {
        if (current_fragment != fragment) {
            if (fragment_place != null) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (current_fragment != null)
                            current_fragment.removeSnackbar();
                        //fragment.setForward(forward);
                        replaceFragment(fragment, originView, sharedView);
                        current_fragment = fragment;
                    }
                };
                runOnUiThread(runnable);
            }
        }
    }

    public void show_fragment(final BaseFragment fragment, final View originView, final String sharedView) {
        show_fragment(fragment, originView, sharedView, true);
    }

    private void replaceFragment(BaseFragment fragment) {
        String backStateName = fragment.getClass().getName() + System.currentTimeMillis();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(fragment_place.getId(), fragment);
            transaction.addToBackStack(backStateName);
            transaction.commit();
        }
    }

    private void replaceFragment(BaseFragment fragment, View originView, String sharedView) {
        String backStateName = fragment.getClass().getName() + System.currentTimeMillis();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
            fragment.setEnterTransition(new Fade());
            current_fragment.setExitTransition(new Fade());
            fragment.setSharedElementReturnTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
        }
        if (!fragmentPopped) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(fragment_place.getId(), fragment);
            transaction.addSharedElement(originView, sharedView);
            transaction.addToBackStack(backStateName);
            transaction.commit();
        }
    }

    public void showActivity(Class toActivity, boolean clearStack, boolean withAnimation) {
        Intent intent = new Intent(BaseActivity.this, toActivity);

        if (clearStack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(intent);
        if (withAnimation) overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public void showActivityWithIntent(Intent intent, boolean clearStack, boolean withAnimation) {
        Intent intentt = intent;
        if (clearStack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(intent);
        if (withAnimation) overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public void showTostMessage(String msg) {
        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        View view = toast.getView();
        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(fetchColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(fetchColor(R.color.tabs_white));
        //BasicTools.setDroidFont(this,text);
        toast.show();
    }

    public void showTostMessage(int msg) {
        //Toast.makeText(getApplicationContext(), getResources().getString(msg), Toast.LENGTH_LONG).show();
        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(msg), Toast.LENGTH_LONG);
        View view = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
//        view.getBackground().setColorFilter(fetchColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be editted
//        TextView text = view.findViewById(android.R.id.message);
//        text.setTextColor(fetchColor(R.color.tabs_white));
        //BasicTools.setDroidFont(this,text);

        toast.show();
    }

    public void showToastMessageShort(String msg) {
        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        View view = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
//        view.getBackground().setColorFilter(fetchColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be editted
//        TextView text = view.findViewById(android.R.id.message);
//        text.setTextColor(fetchColor(R.color.tabs_white));
        //BasicTools.setDroidFont(this,text);


        toast.show();
    }

    public void showToastMessageShort(int msg) {
        //Toast.makeText(getApplicationContext(), getResources().getString(msg), Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(msg), Toast.LENGTH_SHORT);
        View view = toast.getView();
        //Gets the actual oval background of the Toast then sets the colour filter
//        view.getBackground().setColorFilter(fetchColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be editted
//        TextView text = view.findViewById(android.R.id.message);
//        text.setTextColor(fetchColor(R.color.tabs_white));
        //BasicTools.setDroidFont(this,text);

        toast.show();
    }

    public int fetchColor(@ColorRes int color) {
        return ContextCompat.getColor(this, color);
    }


    public void send_data(DataMessage message) {
        message.get_receiver().onReceive(message.get_extra());
    }

    public void send_data(ObjectMessage message) {
        message.get_receiver().onReceive(message.get_object());
    }
}

