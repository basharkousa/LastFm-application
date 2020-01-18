
package com.clicagency.lastfmapp.data.remote.models.artists.artistsSearchResponse;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results implements Serializable
{

    @SerializedName("opensearch:Query")
    @Expose
    private OpensearchQuery opensearchQuery;
    @SerializedName("opensearch:totalResults")
    @Expose
    private String opensearchTotalResults;
    @SerializedName("opensearch:startIndex")
    @Expose
    private String opensearchStartIndex;
    @SerializedName("opensearch:itemsPerPage")
    @Expose
    private String opensearchItemsPerPage;
    @SerializedName("artistmatches")
    @Expose
    private Artistmatches artistmatches;
    @SerializedName("@attr")
    @Expose
    private Attr attr;
    private final static long serialVersionUID = -6957318598996704377L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Results() {
    }

    /**
     * 
     * @param artistmatches
     * @param attr
     * @param opensearchTotalResults
     * @param opensearchItemsPerPage
     * @param opensearchQuery
     * @param opensearchStartIndex
     */
    public Results(OpensearchQuery opensearchQuery, String opensearchTotalResults, String opensearchStartIndex, String opensearchItemsPerPage, Artistmatches artistmatches, Attr attr) {
        super();
        this.opensearchQuery = opensearchQuery;
        this.opensearchTotalResults = opensearchTotalResults;
        this.opensearchStartIndex = opensearchStartIndex;
        this.opensearchItemsPerPage = opensearchItemsPerPage;
        this.artistmatches = artistmatches;
        this.attr = attr;
    }

    public OpensearchQuery getOpensearchQuery() {
        return opensearchQuery;
    }

    public void setOpensearchQuery(OpensearchQuery opensearchQuery) {
        this.opensearchQuery = opensearchQuery;
    }

    public String getOpensearchTotalResults() {
        return opensearchTotalResults;
    }

    public void setOpensearchTotalResults(String opensearchTotalResults) {
        this.opensearchTotalResults = opensearchTotalResults;
    }

    public String getOpensearchStartIndex() {
        return opensearchStartIndex;
    }

    public void setOpensearchStartIndex(String opensearchStartIndex) {
        this.opensearchStartIndex = opensearchStartIndex;
    }

    public String getOpensearchItemsPerPage() {
        return opensearchItemsPerPage;
    }

    public void setOpensearchItemsPerPage(String opensearchItemsPerPage) {
        this.opensearchItemsPerPage = opensearchItemsPerPage;
    }

    public Artistmatches getArtistmatches() {
        return artistmatches;
    }

    public void setArtistmatches(Artistmatches artistmatches) {
        this.artistmatches = artistmatches;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

}
