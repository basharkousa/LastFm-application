package com.clicagency.lastfmapp.data.remote;

import com.clicagency.lastfmapp.data.remote.models.albums.albumDetails.AlbumDetailsRespnse;
import com.clicagency.lastfmapp.data.remote.models.albums.albumsArtist.AlbumsArtistRespnce;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.ArtistsResponse;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsSearchResponse.ArtistsSearchResponce;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface LastFmApi {

    @GET("?method=library.getartists")
    Call<ArtistsResponse> getArtists(@QueryMap Map<String, String> params);

    @GET("?method=artist.search")
    Call<ArtistsSearchResponce> getSearchArtists(@QueryMap Map<String, String> params);

    @GET("?method=artist.gettopalbums")
    Call<AlbumsArtistRespnce> getAlbumsArti(@QueryMap Map<String, String> params);

    @GET("?method=album.getinfo")
    Call<AlbumDetailsRespnse> getAlbumDetails(@QueryMap Map<String, String> params);
}
