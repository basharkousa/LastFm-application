
package com.clicagency.lastfmapp.data.remote.models.albums.albumDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wiki {

    @SerializedName("published")
    @Expose
    private String published;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("content")
    @Expose
    private String content;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Wiki() {
    }

    /**
     * 
     * @param summary
     * @param published
     * @param content
     */
    public Wiki(String published, String summary, String content) {
        super();
        this.published = published;
        this.summary = summary;
        this.content = content;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
