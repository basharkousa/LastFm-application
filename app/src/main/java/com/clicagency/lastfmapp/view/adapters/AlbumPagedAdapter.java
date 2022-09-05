package com.clicagency.lastfmapp.view.adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.databinding.CardAlbumBinding;
import com.clicagency.lastfmapp.databinding.CardAlbumDatabaseBinding;
import com.clicagency.lastfmapp.view.base.BaseAdapter;
import com.clicagency.lastfmapp.view.listeners.IOnAlbumClick;

public class AlbumPagedAdapter extends PagedListAdapter<Album,AlbumPagedAdapter.AlbumViewHolder> {


    private Context mCtx;
    private BaseAdapter.OnItemClickListener<Album> mOnItemClickListener;


   public AlbumPagedAdapter(Context mCtx,@NonNull BaseAdapter.OnItemClickListener<Album> mOnItemClickListener) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public  void onClick(Album item){
        Log.e("'AdapterClick'","artist.getName()");
        mOnItemClickListener.onItemClick(item);
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardAlbumDatabaseBinding cardAlbumBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.card_album_database,parent,false);
        return new AlbumViewHolder(cardAlbumBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final AlbumViewHolder holder, int position) {
        final Album item = getItem(position);

        if (item != null) {

            holder.cardAlbumBinding.setObj(item);
            holder.cardAlbumBinding.setAdapter(this);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.cardAlbumBinding.ivAlbum.setTransitionName("transition" + position);
            }
            holder.cardAlbumBinding.cardArtist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener != null) {
//                        mOnItemClickListener.itemClicked(item, position,holder.cardAlbumBinding.ivAlbum);
                    }
                }
            });
        }else{
           // Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show();
        }


    }

    private static final DiffUtil.ItemCallback<Album> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Album>() {
                @Override
                public boolean areItemsTheSame(Album oldItem, Album newItem) {
                    return oldItem.getMbid() == newItem.getMbid();
                }

                @Override
                public boolean areContentsTheSame(Album oldItem, Album newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class AlbumViewHolder extends RecyclerView.ViewHolder {

       private CardAlbumDatabaseBinding cardAlbumBinding;

        public AlbumViewHolder(CardAlbumDatabaseBinding itemView) {
            super(itemView.getRoot());
           cardAlbumBinding = itemView;
        }
    }

    public void setClickListener(IOnAlbumClick itemClickListener) {
//        mOnItemClickListener = itemClickListener;
    }



}

