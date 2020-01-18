
package com.clicagency.lastfmapp.data.remote.models.artists.artistsSearchResponse;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArtistsSearchResponce implements Serializable
{

    @SerializedName("results")
    @Expose
    private Results results;
    private final static long serialVersionUID = -1730542958794896166L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ArtistsSearchResponce() {
    }

    /**
     * 
     * @param results
     */
    public ArtistsSearchResponce(Results results) {
        super();
        this.results = results;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

}
