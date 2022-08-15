package com.clicagency.lastfmapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.clicagency.lastfmapp.view.fragments.albumsArtistFragment.AlbumsArtistViewModel;

public class ViewModelNewInstanceFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private Object[] mParams;

    public ViewModelNewInstanceFactory(Application application, Object... params) {
        mApplication = application;
        mParams = params;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
//        if (modelClass == AlbumsArtistViewModel.class) {
//            return (T) new ViewModel1(mApplication, (String) mParams[0]);
//        } else if (modelClass == ViewModel2.class) {
//            return (T) new ViewModel2(mApplication, (Integer) mParams[0]);
//        } else if (modelClass == ViewModel3.class) {
//            return (T) new ViewModel3(mApplication, (Integer) mParams[0], (String) mParams[1]);
//        } else {
            return super.create(modelClass);
//        }
    }
}