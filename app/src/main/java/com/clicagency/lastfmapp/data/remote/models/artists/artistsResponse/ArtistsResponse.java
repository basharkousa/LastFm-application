
package com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArtistsResponse implements Serializable
{

    @SerializedName("artists")
    @Expose
    private Artists artists;
    private final static long serialVersionUID = 6791865729836454567L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ArtistsResponse() {
    }

    /**
     * 
     * @param artists
     */
    public ArtistsResponse(Artists artists) {
        super();
        this.artists = artists;
    }

    public Artists getArtists() {
        return artists;
    }

    public void setArtists(Artists artists) {
        this.artists = artists;
    }

}
