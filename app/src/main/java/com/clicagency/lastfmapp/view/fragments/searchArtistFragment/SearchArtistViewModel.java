package com.clicagency.lastfmapp.view.fragments.searchArtistFragment;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.clicagency.lastfmapp.data.remote.models.NetworkState;
import com.clicagency.lastfmapp.data.remote.models.State;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.Artist;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.ArtistsResponse;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsSearchResponse.ArtistsSearchResponce;
import com.clicagency.lastfmapp.data.remote.repositories.ArtistRepository;
import com.clicagency.lastfmapp.view.listeners.IResponseListener;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SearchArtistViewModel extends ViewModel{

    private MutableLiveData<List<Artist>> mutableLiveDataArtists;

    private final MutableLiveData<State<ArtistsResponse>> mutableLiveDataArtistsResponse = new MutableLiveData<>();

    public MutableLiveData<State<ArtistsResponse>> getMutableLiveDataArtistsResponse() {
        return mutableLiveDataArtistsResponse;
    }

    public void getArtists(){
//        State state = new State(State.Status.FAILED,"");
        mutableLiveDataArtistsResponse.postValue(new State<>(State.Status.LOADING,""));
        Log.e("Loadingg\n","");
        artistRepository.getArtistsRequest2(new IResponseListener<ArtistsResponse>() {
            @Override
            public void onSuccess(ArtistsResponse data) {
                mutableLiveDataArtistsResponse.postValue(new State<>(State.Status.SUCCESS,data));
                Log.e("Responssss","data.getArtists().getArtist().get(0).getName()");
            }

            @Override
            public void onFailure(String message) {
                mutableLiveDataArtistsResponse.postValue(new State<>(State.Status.FAILED,""));
                Log.e("FailedBB","");
            }
        });



    }

    private String query = "";

    private ArtistRepository artistRepository;

    @Inject
    public SearchArtistViewModel(ArtistRepository artistRepository) {
       this.artistRepository = artistRepository;
        Log.e("SearchArtistViewModel","Init()");
        init();
    }

    public void init() {
        getArtists();
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
