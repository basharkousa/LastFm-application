
package com.clicagency.lastfmapp.data.remote.models.artists.artistsSearchResponse;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attr implements Serializable
{

    @SerializedName("for")
    @Expose
    private String _for;
    private final static long serialVersionUID = -4254767041230248918L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Attr() {
    }

    /**
     * 
     * @param _for
     */
    public Attr(String _for) {
        super();
        this._for = _for;
    }

    public String getFor() {
        return _for;
    }

    public void setFor(String _for) {
        this._for = _for;
    }

}
