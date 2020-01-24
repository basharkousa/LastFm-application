package com.clicagency.lastfmapp.data.remote.repositories.albumPagedRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.clicagency.lastfmapp.data.remote.ApiClient;
import com.clicagency.lastfmapp.data.remote.LastFmApi;
import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.models.NetworkState;
import com.clicagency.lastfmapp.data.remote.models.albums.albumsArtist.AlbumsArtistRespnce;
import com.clicagency.lastfmapp.tools.Constants;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumDataSource extends PageKeyedDataSource<Integer, Album> {

    public static int TOTAL_PAGES = 0;
    public static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;
    private String ARTIST_NAME = "cher";

    private final LastFmApi lastFmAPI = ApiClient.getClient();
    private Map<String, String> options;

    private MutableLiveData networkState;
    private MutableLiveData initialLoading;

    private LoadCallback<Integer, Album> callback;
    private LoadParams<Integer> params;

    public AlbumDataSource(String artist) {
        this.ARTIST_NAME = artist;
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Album> callback) {

        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);


        if (options == null)
            options = new HashMap<>();
        options.put("api_key", Constants.API_KEY);
        options.put("format", "json");
        options.put("limit",  PAGE_SIZE+ "");
        options.put("page", FIRST_PAGE + "");
        options.put("artist",  ARTIST_NAME+ "");

        Call<AlbumsArtistRespnce> callBackApi = lastFmAPI.getAlbumsArti(options);
        callBackApi.enqueue(new Callback<AlbumsArtistRespnce>() {

            @Override
            public void onResponse(@NonNull Call<AlbumsArtistRespnce> call, @NonNull Response<AlbumsArtistRespnce> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        TOTAL_PAGES =Integer.parseInt(response.body().getTopalbums().getAttr().getTotal());
                        callback.onResult(response.body().getTopalbums().getAlbum(), null, FIRST_PAGE + 1);

                        initialLoading.postValue(NetworkState.LOADED);
                        networkState.postValue(NetworkState.LOADED);
                    }
                } else {
                    initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));

                }
            }

            @Override
            public void onFailure(Call<AlbumsArtistRespnce> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));

            }
        });


    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Album> callback) {
//        RetrofitClient.getInstance()
//                .getApi().getAnswers(params.key, PAGE_SIZE, SITE_NAME)
//                .enqueue(new Callback<StackApiResponse>() {
//                    @Override
//                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
//
//                        //if the current page is greater than one
//                        //we are decrementing the page number
//                        //else there is no previous page
//                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
//                        if (response.body() != null) {
//
//                            //passing the loaded data
//                            //and the previous page key
//                            callback.onResult(response.body().items, adjacentKey);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<StackApiResponse> call, Throwable t) {
//
//                    }
//                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Album> callback) {

        this.callback = callback;
        this.params = params;

        networkState.postValue(NetworkState.LOADING);

        options = new HashMap<>();
        options.put("api_key", Constants.API_KEY);
        options.put("format", "json");
        options.put("limit",  PAGE_SIZE+ "");
        options.put("page", params.key + "");
        options.put("artist",  ARTIST_NAME+ "");

        Call<AlbumsArtistRespnce> callBackApi = lastFmAPI.getAlbumsArti(options);
        callBackApi.enqueue(new Callback<AlbumsArtistRespnce>() {

            @Override
            public void onResponse(@NonNull Call<AlbumsArtistRespnce> call, @NonNull Response<AlbumsArtistRespnce> response) {

                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        //if the response has next page
                        //incrementing the next page number

                        if(TOTAL_PAGES > 0 ){
                            Integer key =  params.key + 1 ;
                            TOTAL_PAGES--;
                            //passing the loaded data and next page value
                            callback.onResult(response.body().getTopalbums().getAlbum(), key);

                            networkState.postValue(NetworkState.LOADED);
                        }



                    }
                } else {

                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<AlbumsArtistRespnce> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    public void retryPagination(){
        loadAfter(params, callback);
    }

    public void clear() {
        //todo
    }
}
