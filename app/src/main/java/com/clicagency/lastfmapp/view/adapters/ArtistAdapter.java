package com.clicagency.lastfmapp.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.Artist;
import com.clicagency.lastfmapp.databinding.CardArtistBinding;
import com.clicagency.lastfmapp.view.listeners.IOnClick;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private LayoutInflater mInflater;
    private List<Artist> mArtistsList;
    private IOnClick mOnItemClickListener;

    public ArtistAdapter(Context context) {
       // mInflater = LayoutInflater.from(context);
    }

    public ArtistAdapter(Context context, List<Artist> mArtistsList, IOnClick mOnItemClickListener) {
        mInflater = LayoutInflater.from(context);
        this.mArtistsList = mArtistsList;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ArtistAdapter.ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardArtistBinding cardArtistBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.card_artist,parent,false);
        return new ArtistViewHolder(cardArtistBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistAdapter.ArtistViewHolder holder,final int position) {
        if (mArtistsList != null) {
            Artist currentSubjectEntity = mArtistsList.get(position);
            holder.cardArtistBinding.setArtistModel(currentSubjectEntity);


            holder.cardArtistBinding.cardArtist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.itemClicked(v, position);
                    }
                }
            });
        }
    }

    public void setArtistsList(List<Artist> subjectEntities) {
        mArtistsList = subjectEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mArtistsList != null) {
            return mArtistsList.size();
        } else {
            return 0;
        }
    }

    /**
     * Method to get item by position.
     * @param position
     * @return
     */
    @Nullable
    public Artist getItem(int position) {
        return mArtistsList.get(position);
    }


    /**
     * Custom click item listener.
     */

    public void setClickListener(IOnClick itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    public interface onItemClickListener {
        void ItemClicked(View v, int position);
    }


    /**
     * View Holder Class
     */
    class ArtistViewHolder extends RecyclerView.ViewHolder{

        private CardArtistBinding cardArtistBinding;
        private ArtistViewHolder(CardArtistBinding itemView) {
            super(itemView.getRoot());

            cardArtistBinding = itemView;
        }
    }
}
