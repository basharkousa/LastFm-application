
package com.clicagency.lastfmapp.data.remote.models.artists.artistsSearchResponse;

import java.io.Serializable;
import java.util.List;

import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.Artist;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artistmatches implements Serializable
{

    @SerializedName("artist")
    @Expose
    private List<Artist> artist = null;
    private final static long serialVersionUID = -395354382206376744L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Artistmatches() {
    }

    /**
     * 
     * @param artist
     */
    public Artistmatches(List<Artist> artist) {
        super();
        this.artist = artist;
    }

    public List<Artist> getArtist() {
        return artist;
    }

    public void setArtist(List<Artist> artist) {
        this.artist = artist;
    }

}
