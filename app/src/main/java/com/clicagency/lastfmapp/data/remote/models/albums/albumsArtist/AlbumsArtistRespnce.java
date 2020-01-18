
package com.clicagency.lastfmapp.data.remote.models.albums.albumsArtist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlbumsArtistRespnce {

    @SerializedName("topalbums")
    @Expose
    private Topalbums topalbums;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AlbumsArtistRespnce() {
    }

    /**
     * 
     * @param topalbums
     */
    public AlbumsArtistRespnce(Topalbums topalbums) {
        super();
        this.topalbums = topalbums;
    }

    public Topalbums getTopalbums() {
        return topalbums;
    }

    public void setTopalbums(Topalbums topalbums) {
        this.topalbums = topalbums;
    }

}
