
package com.clicagency.lastfmapp.data.remote.models.albums.albumsArtist;

import java.util.List;

import com.clicagency.lastfmapp.data.local.entity.Album;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Topalbums {

    @SerializedName("album")
    @Expose
    private List<Album> album = null;
    @SerializedName("@attr")
    @Expose
    private Attr attr;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Topalbums() {
    }

    /**
     * 
     * @param album
     * @param attr
     */
    public Topalbums(List<Album> album, Attr attr) {
        super();
        this.album = album;
        this.attr = attr;
    }

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

}
