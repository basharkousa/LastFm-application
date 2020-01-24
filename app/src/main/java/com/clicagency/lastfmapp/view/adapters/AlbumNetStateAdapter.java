package com.clicagency.lastfmapp.view.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.models.NetworkState;
import com.clicagency.lastfmapp.databinding.CardAlbumBinding;
import com.clicagency.lastfmapp.databinding.NetworkStateItemBinding;

public class AlbumNetStateAdapter extends PagedListAdapter<Album, RecyclerView.ViewHolder> {

    private NetworkState networkState;
    private IOnCLickNetStateAdapter itemClickListener;

    public AlbumNetStateAdapter(IOnCLickNetStateAdapter itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == R.layout.card_album) {

            CardAlbumBinding cardAlbumBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.card_album, parent, false);
            AlbumViewHolder viewHolder = new AlbumViewHolder(cardAlbumBinding, itemClickListener);

            return viewHolder;
        } else if (viewType == R.layout.network_state_item) {
            NetworkStateItemBinding networkStateItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.network_state_item, parent, false);
            return new NetworkStateItemViewHolder(networkStateItemBinding, itemClickListener);
        } else {
            throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case R.layout.card_album:
                ((AlbumViewHolder) holder).bindTo(getItem(position), position);
                break;
            case R.layout.network_state_item:
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }

    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.card_album;
        }
    }


    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public void setClickListener(IOnCLickNetStateAdapter itemListener) {
        itemClickListener = itemListener;
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

    public class AlbumViewHolder extends RecyclerView.ViewHolder {

        public Album album;
        private CardAlbumBinding cardAlbumBinding;
        private IOnCLickNetStateAdapter iOnClick;
        private int position;

        public AlbumViewHolder(CardAlbumBinding itemView, IOnCLickNetStateAdapter itemClickListener) {
            super(itemView.getRoot());
            cardAlbumBinding = itemView;
            iOnClick = itemClickListener;
        }

        public void bindTo(Album album, int position) {
            this.album = album;
            this.position = position;
            cardAlbumBinding.setAlbumModel(album);

            cardAlbumBinding.cardArtist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (iOnClick != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            cardAlbumBinding.ivAlbum.setTransitionName("transition" + position);
                        }
                        iOnClick.itemClicked(album, position, cardAlbumBinding.ivAlbum); // call the onClick in the OnItemClickListener
                    }
                }
            });

        }


    }

    public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        private NetworkStateItemBinding itemBinding;
        private IOnCLickNetStateAdapter itemClickListener;

        public NetworkStateItemViewHolder(NetworkStateItemBinding itemBindingg, IOnCLickNetStateAdapter itemClickListener) {
            super(itemBindingg.getRoot());
            itemBinding = itemBindingg;
            this.itemClickListener = itemClickListener;

        }

        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                itemBinding.progressBar.setVisibility(View.VISIBLE);
            } else {
                itemBinding.progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
//                itemBinding.errorMsg.setVisibility(View.VISIBLE);
//                itemBinding.errorMsg.setText(networkState.getMsg());
                itemBinding.btnRetry.setVisibility(View.VISIBLE);
            } else {
//                itemBinding.errorMsg.setVisibility(View.GONE);
                itemBinding.btnRetry.setVisibility(View.GONE);

            }

            itemBinding.btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null)
                        itemClickListener.onRetryClicked();
                }
            });
        }
    }


    public interface IOnCLickNetStateAdapter {

        void onRetryClicked();
        void itemClicked(Album album, int position, View view);
    }

}
