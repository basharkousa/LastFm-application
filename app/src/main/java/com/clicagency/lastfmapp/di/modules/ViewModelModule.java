package com.clicagency.lastfmapp.di.modules;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.clicagency.lastfmapp.view.fragments.mainFragment.MainPageViewModel;
import com.clicagency.lastfmapp.viewmodel.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.multibindings.IntoMap;

/**
 * Allows us to inject dependencies via constructor injection
 */
@Module
@InstallIn(ActivityComponent.class)
public abstract class ViewModelModule {

    @Binds
    @Singleton
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(AlbumDetailsViewModel.class)
//    @SuppressWarnings("unused")
//    abstract ViewModel bindsAlbumDetailsViewModel(AlbumDetailsViewModel albumDetailsViewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(AlbumsArtistViewModel.class)
//    @SuppressWarnings("unused")
//    abstract ViewModel bindsAlbumViewModel(AlbumsArtistViewModel albumsArtistViewModel);
//
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SearchArtistViewModel.class)
//    @SuppressWarnings("unused")
//    abstract ViewModel bindsArtistViewModel(SearchArtistViewModel searchArtistViewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(MainPageViewModel.class)
//    @SuppressWarnings("unused")
//    abstract ViewModel bindsMainPageViewModel(MainPageViewModel mainPageViewModel);


}
