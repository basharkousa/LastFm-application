
package com.clicagency.lastfmapp.data.remote.models.albums.albumDetails;

import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.clicagency.lastfmapp.tools.BasicTools;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Track {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("@attr")
    @Expose
    private Attr attr;
    @SerializedName("streamable")
    @Expose
    private Streamable streamable;
    @SerializedName("artist")
    @Expose
    private Artist artist;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Track() {
    }

    /**
     * 
     * @param duration
     * @param streamable
     * @param artist
     * @param name
     * @param attr
     * @param url
     */
    public Track(String name, String url, String duration, Attr attr, Streamable streamable, Artist artist) {
        super();
        this.name = name;
        this.url = url;
        this.duration = duration;
        this.attr = attr;
        this.streamable = streamable;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public Streamable getStreamable() {
        return streamable;
    }

    public void setStreamable(Streamable streamable) {
        this.streamable = streamable;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @BindingAdapter("duration_value")
    public static void formatSecondd(View view , String duration){
        TextView textView = (TextView) view;
        int durationInt =Integer.parseInt( duration);
        textView.setText(BasicTools.formatSeconds(durationInt));

    }

}
