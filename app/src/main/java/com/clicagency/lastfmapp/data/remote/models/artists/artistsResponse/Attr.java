
package com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attr implements Serializable
{

    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("perPage")
    @Expose
    private String perPage;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("totalPages")
    @Expose
    private String totalPages;
    private final static long serialVersionUID = 758653716624455159L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Attr() {
    }

    /**
     * 
     * @param total
     * @param perPage
     * @param totalPages
     * @param page
     * @param user
     */
    public Attr(String page, String perPage, String user, String total, String totalPages) {
        super();
        this.page = page;
        this.perPage = perPage;
        this.user = user;
        this.total = total;
        this.totalPages = totalPages;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPerPage() {
        return perPage;
    }

    public void setPerPage(String perPage) {
        this.perPage = perPage;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

}
