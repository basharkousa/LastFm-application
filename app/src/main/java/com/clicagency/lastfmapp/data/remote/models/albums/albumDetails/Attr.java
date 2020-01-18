
package com.clicagency.lastfmapp.data.remote.models.albums.albumDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attr {

    @SerializedName("rank")
    @Expose
    private String rank;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Attr() {
    }

    /**
     * 
     * @param rank
     */
    public Attr(String rank) {
        super();
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

}
