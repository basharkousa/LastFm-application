package com.clicagency.lastfmapp.view.adapters;

import android.os.Handler;

import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.view.base.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


public class AlbumsArtistAdapter extends BaseAdapter<Album> {

    private final List<Album> albums = new ArrayList<>();
    private MyArtistAdapter.IOnClick mOnItemClickListener;

    public AlbumsArtistAdapter(MyArtistAdapter.IOnClick mOnItemClickListener) {
//        this.albums = albums;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    protected Album getItemForPosition(int position) {
        return albums.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.card_album;
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public void addItems(List<Album> newItems) {
       albums.addAll(newItems);
    }

//    public void addItems(final List<Album> newItems, final LinearLayoutManager layoutManager, final RecyclerView recyclerView){
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (newItems != null) {
//                    int pre_size = newItems.size();
//                    for (Album item : newItems) {
//                        albums.add(item);
//                        notifyItemInserted(albums.size() - 1);
//                    }
//                    if (albums.size() > 0)
//                        receiver.scroll(pre_size, layoutManager, recyclerView);
//                }
//            }
//        };
//        Handler handler = new Handler(context.getMainLooper());
//        handler.post(runnable);
//    }

}
