package com.clicagency.lastfmapp.data.remote.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.clicagency.lastfmapp.data.remote.ApiClient;
import com.clicagency.lastfmapp.data.remote.LastFmApi;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.Artist;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.ArtistsResponse;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsSearchResponse.ArtistsSearchResponce;
import com.clicagency.lastfmapp.tools.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistRepository {

    private static ArtistRepository artistRepository;

    private final LastFmApi lastFmAPI;

    private Map<String, String> options;

    private ArtistRepository() {
        this.lastFmAPI = ApiClient.getClient();
        options = new HashMap<>();
    }

    public static ArtistRepository getInstance() {

        if (artistRepository == null) {
            synchronized (ArtistRepository.class) {
                if (artistRepository == null) {
                    artistRepository = new ArtistRepository();
                }
            }
        }
        return artistRepository;
    }

    public MutableLiveData<List<Artist>> getArtistsRequest() {

        final MutableLiveData<List<Artist>> data = new MutableLiveData<>();
        if (options == null)
            options = new HashMap<>();
        options.put("api_key", Constants.API_KEY);
        options.put("format", "json");
        options.put("user", "joanofarctan");


        Call<ArtistsResponse> callBack = lastFmAPI.getArtists(options);
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

    public MutableLiveData<List<Artist>> getArtistsSearchRequest(String query) {

        final MutableLiveData<List<Artist>> data = new MutableLiveData<>();
        options = new HashMap<>();
        options.put("api_key", Constants.API_KEY);
        options.put("format", "json");
        options.put("artist", query);

        final Call<ArtistsSearchResponce> callBack = lastFmAPI.getSearchArtists(options);
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
