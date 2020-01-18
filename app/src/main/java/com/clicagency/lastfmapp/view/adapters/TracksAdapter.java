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
import com.clicagency.lastfmapp.data.remote.models.albums.albumDetails.Track;
import com.clicagency.lastfmapp.databinding.CardTrackBinding;
import com.clicagency.lastfmapp.view.listeners.IOnClick;

import java.util.List;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TracksViewHolder> {

    private List<Track> mTracksList;
    private IOnClick mOnItemClickListener;

    public TracksAdapter(Context context) {
    }

    public TracksAdapter(Context context, List<Track> mArtistsList, IOnClick mOnItemClickListener) {
        this.mTracksList = mArtistsList;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public TracksAdapter.TracksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardTrackBinding cardTrackBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.card_track,parent,false);
        return new TracksViewHolder(cardTrackBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TracksAdapter.TracksViewHolder holder,final int position) {
        if (mTracksList != null) {
            Track currentTracktEntity = mTracksList.get(position);
            int num = position;
            num++;
            holder.cardTrackBinding.tvNum.setText(num+"");
            holder.cardTrackBinding.setTrackModel(currentTracktEntity);


            holder.cardTrackBinding.layoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.itemClicked(v, position);
                    }
                }
            });
        }
    }

    public void setTracksList(List<Track> subjectEntities) {
        mTracksList = subjectEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTracksList != null) {
            return mTracksList.size();
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
    public Track getItem(int position) {
        return mTracksList.get(position);
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
    class TracksViewHolder extends RecyclerView.ViewHolder{

        private CardTrackBinding cardTrackBinding;
        private TracksViewHolder(CardTrackBinding itemView) {
            super(itemView.getRoot());

            cardTrackBinding = itemView;
        }
    }
}
