
package com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artists implements Serializable
{

    @SerializedName("artist")
    @Expose
    private List<Artist> artist = null;
    @SerializedName("@attr")
    @Expose
    private Attr attr;
    private final static long serialVersionUID = -6835238101144901327L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Artists() {
    }

    /**
     * 
     * @param artist
     * @param attr
     */
    public Artists(List<Artist> artist, Attr attr) {
        super();
        this.artist = artist;
        this.attr = attr;
    }

    public List<Artist> getArtist() {
        return artist;
    }

    public void setArtist(List<Artist> artist) {
        this.artist = artist;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

}
