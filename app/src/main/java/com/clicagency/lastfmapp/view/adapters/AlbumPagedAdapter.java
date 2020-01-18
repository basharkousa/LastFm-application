package com.clicagency.lastfmapp.view.adapters;

import android.content.Context;
import android.os.Build;
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
import com.clicagency.lastfmapp.view.listeners.IOnAlbumClick;

public class AlbumPagedAdapter extends PagedListAdapter<Album,AlbumPagedAdapter.AlbumViewHolder> {


    private Context mCtx;
    private IOnAlbumClick mOnItemClickListener;

   public AlbumPagedAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardAlbumBinding cardAlbumBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.card_album,parent,false);
        return new AlbumViewHolder(cardAlbumBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final AlbumViewHolder holder, final int position) {
        final Album item = getItem(position);

        if (item != null) {

            holder.cardAlbumBinding.setAlbumModel(item);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.cardAlbumBinding.ivAlbum.setTransitionName("transition" + position);
            }
            holder.cardAlbumBinding.cardArtist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.itemClicked(item, position,holder.cardAlbumBinding.ivAlbum);
                    }
                }
            });
        }else{
           // Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show();
        }


    }

    private static DiffUtil.ItemCallback<Album> DIFF_CALLBACK =
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

       private CardAlbumBinding cardAlbumBinding;

        public AlbumViewHolder(CardAlbumBinding itemView) {
            super(itemView.getRoot());
           cardAlbumBinding = itemView;
        }
    }

    public void setClickListener(IOnAlbumClick itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }


}

