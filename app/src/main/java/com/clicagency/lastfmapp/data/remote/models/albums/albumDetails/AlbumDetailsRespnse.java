
package com.clicagency.lastfmapp.data.remote.models.albums.albumDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlbumDetailsRespnse {

    @SerializedName("album")
    @Expose
    private Album album;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AlbumDetailsRespnse() {
    }

    /**
     * 
     * @param album
     */
    public AlbumDetailsRespnse(Album album) {
        super();
        this.album = album;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

}
