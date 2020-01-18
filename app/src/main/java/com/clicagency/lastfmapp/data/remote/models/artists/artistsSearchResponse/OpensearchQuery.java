
package com.clicagency.lastfmapp.data.remote.models.artists.artistsSearchResponse;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpensearchQuery implements Serializable
{

    @SerializedName("#text")
    @Expose
    private String text;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("searchTerms")
    @Expose
    private String searchTerms;
    @SerializedName("startPage")
    @Expose
    private String startPage;
    private final static long serialVersionUID = 1441345461659694261L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OpensearchQuery() {
    }

    /**
     * 
     * @param startPage
     * @param role
     * @param searchTerms
     * @param text
     */
    public OpensearchQuery(String text, String role, String searchTerms, String startPage) {
        super();
        this.text = text;
        this.role = role;
        this.searchTerms = searchTerms;
        this.startPage = startPage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(String searchTerms) {
        this.searchTerms = searchTerms;
    }

    public String getStartPage() {
        return startPage;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

}
