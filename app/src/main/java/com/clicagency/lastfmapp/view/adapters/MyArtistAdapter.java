package com.clicagency.lastfmapp.view.adapters;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.Artist;
import com.clicagency.lastfmapp.view.base.BaseAdapter;

import java.util.List;

public class MyArtistAdapter extends BaseAdapter<Artist> {

    private final List<Artist> data;
    private IOnClick mOnItemClickListener;


    public MyArtistAdapter(List<Artist> data,IOnClick mOnItemClickListener) {
        super();
        this.data = data;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface IOnClick {
        void itemClicked(Artist artist);
    }

    public void onClick(Artist artist){
        Log.e("'AdapterClick'","artist.getName()");
        mOnItemClickListener.itemClicked(artist);
    }

    public void setClickListener(IOnClick itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected Artist getItemForPosition(int position) {
        return data.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.card_artist;
    }
}
