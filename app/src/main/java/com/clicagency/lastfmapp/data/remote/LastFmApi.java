package com.clicagency.lastfmapp.data.remote;

import com.clicagency.lastfmapp.data.remote.models.albums.albumDetails.AlbumDetailsRespnse;
import com.clicagency.lastfmapp.data.remote.models.albums.albumsArtist.AlbumsArtistRespnce;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.ArtistsResponse;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsSearchResponse.ArtistsSearchResponce;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface LastFmApi {

    //@Query("page") int page
    //@QueryMap Map<String, String> params

    @GET("?method=library.getartists")
    Call<ArtistsResponse> getArtists(@Query("user") String artist);

    @GET("?method=artist.search")
    Call<ArtistsSearchResponce> getSearchArtists(@Query("artist") String artist);

    @GET("?method=artist.gettopalbums")
    Call<AlbumsArtistRespnce> getAlbumsArti(@Query("artist") String artist,
                                            @Query("page") String page,@Query("limit") String limit);

    @GET("?method=album.getinfo")
    Call<AlbumDetailsRespnse> getAlbumDetails(@Query("artist") String artist,@Query("album") String album);
}
