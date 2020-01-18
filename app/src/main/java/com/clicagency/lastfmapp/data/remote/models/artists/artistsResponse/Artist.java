
package com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import java.io.Serializable;
import java.util.List;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artist implements Serializable
{

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("mbid")
    @Expose
    private String mbid;
    @SerializedName("tagcount")
    @Expose
    private String tagcount;
    @SerializedName("playcount")
    @Expose
    private String playcount;
    @SerializedName("streamable")
    @Expose
    private String streamable;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("listeners")
    @Expose
    private String listeners;

    @SerializedName("image")
    @Expose
    private List<Image> image = null;
    private final static long serialVersionUID = -8478863192987923263L;


    private String imageMainUrl;
    /**
     * No args constructor for use in serialization
     * 
     */
    public Artist() {
    }

    /**
     * 
     * @param image
     * @param mbid
     * @param streamable
     * @param playcount
     * @param name
     * @param tagcount
     * @param url
     */
    public Artist(String url, String mbid, String tagcount, String playcount, String streamable, String name, List<Image> image) {
        super();
        this.url = url;
        this.mbid = mbid;
        this.tagcount = tagcount;
        this.playcount = playcount;
        this.streamable = streamable;
        this.name = name;
        this.image = image;
    }

    public Artist(String url, String mbid, String tagcount, String playcount, String streamable, String name, List<Image> image,String listeners) {
        super();
        this.url = url;
        this.mbid = mbid;
        this.tagcount = tagcount;
        this.playcount = playcount;
        this.streamable = streamable;
        this.name = name;
        this.image = image;
        this.listeners = listeners;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getTagcount() {
        return tagcount;
    }

    public void setTagcount(String tagcount) {
        this.tagcount = tagcount;
    }

    public String getPlaycount() {
        return playcount;
    }

    public void setPlaycount(String playcount) {
        this.playcount = playcount;
    }

    public String getStreamable() {
        return streamable;
    }

    public void setStreamable(String streamable) {
        this.streamable = streamable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Image> getImage() {
        return image;
    }

    public String getImageUrl() {
        // The URL will usually come from a model (i.e Profile)
        return image.get(2).getText();
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public String getImageMainUrl() {
        return imageMainUrl;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    public void setImageMainUrl(String imageMainUrl) {
        this.imageMainUrl = imageMainUrl;
    }

    @BindingAdapter("imgUrlz")
    public static void loadImage(View view , String imgUrl){

        ImageView imageView = (ImageView)view;
        Glide.with(imageView.getContext()).asBitmap().load(imgUrl).dontAnimate().into(imageView);

    }
}
