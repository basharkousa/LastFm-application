
package com.clicagency.lastfmapp.data.local.entity;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.Objects;

import com.bumptech.glide.Glide;
import com.clicagency.lastfmapp.data.remote.models.albums.albumsArtist.Artist;
import com.clicagency.lastfmapp.data.remote.models.albums.albumsArtist.Image;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "albums")
public class Album {

    @PrimaryKey()
    @ColumnInfo(name = "mbid")
    @SerializedName("mbid")
    @Expose
    @NonNull
    private String mbid;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    private String name;

    @ColumnInfo(name = "playcount")
    @SerializedName("playcount")
    @Expose
    private String playcount;

    @ColumnInfo(name = "url")
    @SerializedName("url")
    @Expose
    private String url;

    @ColumnInfo(name = "artist_name")
    @Expose
    private String artist_name = "";

    @ColumnInfo(name = "image_url")
    @Expose
    private String image_url_database = "";

    @SerializedName("artist")
    @Expose
    @Ignore
    private Artist artist;

    @SerializedName("image")
    @Expose
    @Ignore
    private List<Image> image = null;

    /**
     * No args constructor for use in serialization
     */
    @Ignore
    public Album() {
    }

    /**
     * @param image
     * @param mbid
     * @param artist
     * @param playcount
     * @param name
     * @param url
     */
    @Ignore
    public Album(String name, String playcount, String mbid, String url, Artist artist, List<Image> image) {
        super();
        this.name = name;
        this.playcount = playcount;
        this.mbid = mbid;
        this.url = url;
        this.artist = artist;
        this.image = image;
    }

    public Album(String mbid, String name, String playcount, String url, String artist_name, String image_url_database) {
        this.mbid = mbid;
        this.name = name;
        this.playcount = playcount;
        this.url = url;
        this.artist_name = artist_name;
        this.image_url_database = image_url_database;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaycount() {
        return playcount;
    }

    public void setPlaycount(String playcount) {
        this.playcount = playcount;
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

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getImage_url_database() {
        return image_url_database;
    }

    public void setImage_url_database(String image_url_database) {
        this.image_url_database = image_url_database;
    }

    public String getImageUrl() {
        // The URL will usually come from a model
        if (image != null)
            return image.get(2).getText();
        else
            return image_url_database;
    }

    @BindingAdapter("imgUrlz")
    public static void loadImage(View view, String imgUrl) {

        ImageView imageView = (ImageView) view;
        Glide.with(imageView.getContext()).asBitmap().load(imgUrl).dontAnimate().into(imageView);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        Album album = (Album) o;
        return mbid.equals(album.mbid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mbid);
    }
}
