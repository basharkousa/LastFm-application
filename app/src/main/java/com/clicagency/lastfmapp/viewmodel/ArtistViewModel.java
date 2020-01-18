package com.clicagency.lastfmapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.Artist;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.ArtistsResponse;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsSearchResponse.ArtistsSearchResponce;
import com.clicagency.lastfmapp.data.remote.repositories.ArtistRepository;

import java.util.List;

public class ArtistViewModel extends ViewModel{

    private MutableLiveData<List<Artist>> mutableLiveDataArtists;

    private String query = "";

    private ArtistRepository artistRepository;

    public void init() {
        artistRepository = ArtistRepository.getInstance();
    }

    public LiveData<List<Artist>> getArtistRepository() {
        return mutableLiveDataArtists = artistRepository.getArtistsRequest();
    }

    public LiveData<List<Artist>> getArtistRepository(String query) {
        return mutableLiveDataArtists = artistRepository.getArtistsSearchRequest(query);
    }


    // VIEW MODEL FACTORY

//    public static class Factory extends ViewModelProvider.NewInstanceFactory {
//
//        @NonNull
//        private final Application mApplication;
//        private final int queySearch;
//
//        public Factory(@NonNull Application application, int cardParentId) {
//            mApplication = application;
//            queySearch = cardParentId;
//        }
//
//        @NonNull
//        @Override
//        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//            return (T) new ArtistViewModel(mApplication, queySearch);
//        }
//    }
//

}
