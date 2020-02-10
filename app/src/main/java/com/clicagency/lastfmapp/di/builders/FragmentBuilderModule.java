package com.clicagency.lastfmapp.di.builders;

import com.clicagency.lastfmapp.view.fragments.AlbumDetailFragment;
import com.clicagency.lastfmapp.view.fragments.AlbumsArtistFragment;
import com.clicagency.lastfmapp.view.fragments.MainPageFragment;
import com.clicagency.lastfmapp.view.fragments.SearchArtistsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This builder provides android injector service to fragments
 *
 */
@Module
public abstract class FragmentBuilderModule {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract AlbumDetailFragment contributeAlbumDetailFragment();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract AlbumsArtistFragment contributeAlbumsArtistFragment();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract MainPageFragment contributeMainPageFragment();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract SearchArtistsFragment contributeSearchArtistsFragment();

}
