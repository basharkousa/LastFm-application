
package com.clicagency.lastfmapp.data.remote.models.albums.albumDetails;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Album {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("mbid")
    @Expose
    private String mbid;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private List<Image> image = null;
    @SerializedName("listeners")
    @Expose
    private String listeners;
    @SerializedName("playcount")
    @Expose
    private String playcount;
    @SerializedName("tracks")
    @Expose
    private Tracks tracks;
    @SerializedName("tags")
    @Expose
    private Tags tags;
    @SerializedName("wiki")
    @Expose
    private Wiki wiki;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Album() {
    }

    /**
     * 
     * @param image
     * @param mbid
     * @param listeners
     * @param artist
     * @param playcount
     * @param wiki
     * @param name
     * @param url
     * @param tracks
     * @param tags
     */
    public Album(String name, String artist, String mbid, String url, List<Image> image, String listeners, String playcount, Tracks tracks, Tags tags, Wiki wiki) {
        super();
        this.name = name;
        this.artist = artist;
        this.mbid = mbid;
        this.url = url;
        this.image = image;
        this.listeners = listeners;
        this.playcount = playcount;
        this.tracks = tracks;
        this.tags = tags;
        this.wiki = wiki;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Image> getImage() {
        return image;
    }

    public String getImageUrl() {
        // The URL will usually come from a model (i.e Profile)
        return image.get(2).getText();
    }

    @BindingAdapter("imgUrl")
    public static void loadImage(View view , String imgUrl){

        ImageView imageView = (ImageView)view;
        Glide.with(imageView.getContext()).asBitmap().load(imgUrl).dontAnimate().into(imageView);

    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    public String getPlaycount() {
        return playcount;
    }

    public void setPlaycount(String playcount) {
        this.playcount = playcount;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public Wiki getWiki() {
        return wiki;
    }

    public void setWiki(Wiki wiki) {
        this.wiki = wiki;
    }

}
