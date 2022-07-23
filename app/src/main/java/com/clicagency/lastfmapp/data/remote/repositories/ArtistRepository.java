package com.clicagency.lastfmapp.data.remote.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.clicagency.lastfmapp.data.remote.LastFmApi;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.Artist;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.ArtistsResponse;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsSearchResponse.ArtistsSearchResponce;
import com.clicagency.lastfmapp.view.listeners.IResponseListener;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ArtistRepository {

    private final LastFmApi lastFmAPI;

    @Inject
    public ArtistRepository(LastFmApi lastFmApi) {
        this.lastFmAPI = lastFmApi;
    }

    public MutableLiveData<List<Artist>> getArtistsRequest() {

        final MutableLiveData<List<Artist>> data = new MutableLiveData<>();

        Call<ArtistsResponse> callBack = lastFmAPI.getArtists("joanofarctan");
        callBack.enqueue(new Callback<ArtistsResponse>() {

            @Override
            public void onResponse(@NonNull Call<ArtistsResponse> call, @NonNull Response<ArtistsResponse> response) {

                if (response.isSuccessful()) {
                    data.setValue(response.body().getArtists().getArtist());
                } else {

                }
            }

            @Override
            public void onFailure(Call<ArtistsResponse> call, Throwable t) {

            }
        });
        return data;
    }

    public ArtistsResponse getArtistsRequest2(IResponseListener<ArtistsResponse> listener) {

        final ArtistsResponse data = new ArtistsResponse();

        Call<ArtistsResponse> callBack = lastFmAPI.getArtists("joanofarctan");
        callBack.enqueue(new Callback<ArtistsResponse>() {

            @Override
            public void onResponse(@NonNull Call<ArtistsResponse> call, @NonNull Response<ArtistsResponse> response) {

                if (response.isSuccessful()) {
//                    data.setValue(response.body().getArtists().getArtist());
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ArtistsResponse> call, Throwable t) {
                listener.onFailure(call.toString());

            }
        });
        return data;
    }

    public MutableLiveData<List<Artist>> getArtistsSearchRequest(String query) {

        final MutableLiveData<List<Artist>> data = new MutableLiveData<>();

        final Call<ArtistsSearchResponce> callBack = lastFmAPI.getSearchArtists(query);
        callBack.enqueue(new Callback<ArtistsSearchResponce>() {

            @Override
            public void onResponse(@NonNull Call<ArtistsSearchResponce> call, @NonNull Response<ArtistsSearchResponce> response) {

                if (response.isSuccessful()) {
                    data.setValue(response.body().getResults().getArtistmatches().getArtist());
                }
            }

            @Override
            public void onFailure(Call<ArtistsSearchResponce> call, Throwable t) {

            }
        });
        return data;
    }

}
