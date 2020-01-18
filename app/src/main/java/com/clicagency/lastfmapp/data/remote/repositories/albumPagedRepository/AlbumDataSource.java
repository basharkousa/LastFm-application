package com.clicagency.lastfmapp.data.remote.repositories.albumPagedRepository;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.clicagency.lastfmapp.data.remote.ApiClient;
import com.clicagency.lastfmapp.data.remote.LastFmApi;
import com.clicagency.lastfmapp.data.local.entity.Album;
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
    ;
    private Map<String, String> options;

    public AlbumDataSource(String artist) {
        this.ARTIST_NAME = artist;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Album> callback) {

// method=artist.gettopalbums&artist=cher&api_key=d0cdc76eace35e6fd33ae8a50f84d802&format=json&page=1&limit=10
        if (options == null)
            options = new HashMap<>();
        options.put("api_key", Constants.API_KEY);
        options.put("format", "json");
        options.put("limit",  PAGE_SIZE+ "");
        options.put("page", FIRST_PAGE + "");
        options.put("artist",  ARTIST_NAME+ "");

        Call<AlbumsArtistRespnce> callBack = lastFmAPI.getAlbumsArti(options);
        callBack.enqueue(new Callback<AlbumsArtistRespnce>() {

            @Override
            public void onResponse(@NonNull Call<AlbumsArtistRespnce> call, @NonNull Response<AlbumsArtistRespnce> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        TOTAL_PAGES =Integer.parseInt(response.body().getTopalbums().getAttr().getTotal());


                        callback.onResult(response.body().getTopalbums().getAlbum(), null, FIRST_PAGE + 1);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<AlbumsArtistRespnce> call, Throwable t) {

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

        options = new HashMap<>();
        options.put("api_key", Constants.API_KEY);
        options.put("format", "json");
        options.put("limit",  PAGE_SIZE+ "");
        options.put("page", params.key + "");
        options.put("artist",  ARTIST_NAME+ "");

        Call<AlbumsArtistRespnce> callBack = lastFmAPI.getAlbumsArti(options);
        callBack.enqueue(new Callback<AlbumsArtistRespnce>() {

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
                        }

                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<AlbumsArtistRespnce> call, Throwable t) {
            }
        });
    }
}
