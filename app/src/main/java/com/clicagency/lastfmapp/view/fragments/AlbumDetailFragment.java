package com.clicagency.lastfmapp.view.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.data.remote.models.albums.albumDetails.AlbumDetailsRespnse;
import com.clicagency.lastfmapp.data.remote.models.albums.albumDetails.Tag;
import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.models.albums.albumDetails.Track;
import com.clicagency.lastfmapp.data.remote.models.albums.albumsArtist.Artist;
import com.clicagency.lastfmapp.databinding.FragmentAlbumDetailsBinding;
import com.clicagency.lastfmapp.tools.AnimationUtilss;
import com.clicagency.lastfmapp.tools.BasicTools;
import com.clicagency.lastfmapp.view.adapters.TracksAdapter;
import com.clicagency.lastfmapp.view.base.BaseFragment;
import com.clicagency.lastfmapp.view.listeners.IOnClick;
import com.clicagency.lastfmapp.viewmodel.AlbumDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailFragment extends BaseFragment<AlbumDetailsViewModel, FragmentAlbumDetailsBinding> {

    private Album album;
    private String albumName = "";

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    protected Class<AlbumDetailsViewModel> getViewModel() {
        return AlbumDetailsViewModel.class;
    }

    @Override
    protected ViewModelProvider.Factory getViewModelFactory() {

        if (album != null) {
            if (album.getArtist() != null)
                albumName = album.getArtist().getName();
            else
                albumName = album.getArtist_name();
        }


        return new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new AlbumDetailsViewModel(parent.getApplication(), album.getName(), albumName);
            }
        };
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_album_details;
    }

    @Override
    public void init_events() {
        dataBinding.btnBookmarkActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.delete(album);
            }
        });

        dataBinding.btnBookmarkNotActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAlbumInDatabase(album);
            }
        });

        dataBinding.ivRetryContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAlbumDetails();
            }
        });

    }

    private void saveAlbumInDatabase(Album album) {
        if (album.getArtist() != null && album.getMbid()!=null) {
            album.setArtist_name(album.getArtist().getName());
            album.setImage_url_database(album.getImageUrl());
            viewModel.insert(album);
        }

    }

    @Override
    public void init_fragment(Bundle savedInstanceState) {
        loadAlbumDetails();
        handleBookmarkState();
    }


    @Override
    public boolean on_back_pressed() {
        return true;
    }


    private void loadAlbumDetails() {
        dataBinding.layoutPrgrs.setVisibility(View.VISIBLE);
        dataBinding.layoutContent.setVisibility(View.GONE);
        disableBookMarkBtns();
        if (BasicTools.isConnected(parent)) {
            viewModel.getAlbumDetails().observe(this, new Observer<AlbumDetailsRespnse>() {
                @Override
                public void onChanged(AlbumDetailsRespnse albumDetailsRespnse) {
                    dataBinding.setAlbumDetails(albumDetailsRespnse.getAlbum());
                    //showTags(albumDetailsRespnse);
                    fillAllAlbumsItems(albumDetailsRespnse);

                }
            });
        } else {
            parent.showTostMessage(R.string.failed_to_connect);
            disableBookMarkBtns();
            dataBinding.layoutPrgrs.setVisibility(View.GONE);
            dataBinding.layoutContent.setVisibility(View.GONE);
            dataBinding.ivRetryContent.setVisibility(View.VISIBLE);

        }
    }

    private void fillAllAlbumsItems(AlbumDetailsRespnse albumDetailsRespnse) {
        Artist artist = new Artist();
        if (albumDetailsRespnse.getAlbum().getTracks().getTrack().size() > 0)
            artist.setName(albumDetailsRespnse.getAlbum().getTracks().getTrack().get(0).getArtist().getName());
        album.setArtist(artist);
        enableBookmarkBtns();
        if (albumDetailsRespnse.getAlbum().getTracks().getTrack() != null)
            loadTracks(albumDetailsRespnse);
        dataBinding.layoutPrgrs.setVisibility(View.GONE);
        dataBinding.layoutContent.setVisibility(View.VISIBLE);
        long listenersLong = Long.parseLong(albumDetailsRespnse.getAlbum().getListeners());
        dataBinding.tvListeners.setText(BasicTools.format(listenersLong));
    }

    private void loadTracks(AlbumDetailsRespnse albumDetailsRespnse) {
        if (albumDetailsRespnse.getAlbum().getTracks().getTrack().size() > 0) {
            TracksAdapter adapter = new TracksAdapter(parent);
            adapter.setTracksList(albumDetailsRespnse.getAlbum().getTracks().getTrack());
            dataBinding.recyclerTrackList.setLayoutManager(new LinearLayoutManager(parent));
            dataBinding.recyclerTrackList.setHasFixedSize(true);
            dataBinding.recyclerTrackList.setAdapter(adapter);
            dataBinding.tvNoTracks.setVisibility(View.GONE);
            adapter.setClickListener(new IOnClick() {
                @Override
                public void itemClicked(View v, int position) {
                    //parent.showToastMessageShort(position+"");
                }
            });

            int sumDuration = 0;
            for (Track t:albumDetailsRespnse.getAlbum().getTracks().getTrack()){
                sumDuration+= Integer.parseInt(t.getDuration());
            }
            String length_value = albumDetailsRespnse.getAlbum().getTracks().getTrack().size()+" Tracks, "+BasicTools.formatSeconds(sumDuration);
            dataBinding.tvLength.setText(length_value);

        } else {
            dataBinding.tvNoTracks.setVisibility(View.VISIBLE);
        }

    }

    private void handleBookmarkState() {
        if (album != null && album.getMbid() != null) {
            viewModel.isAlbumExist(album).observe(this, new Observer<Album>() {
                @Override
                public void onChanged(Album album) {
                    if (album != null) {
                        showBookMarkActive();
                        dataBinding.btnBookmarkNotActive.setVisibility(View.GONE);
                    } else {
                        dataBinding.btnBookmarkActive.setVisibility(View.GONE);
                        showBookMarkNotActive();
                    }
                }
            });
        } else {
            parent.showTostMessage(R.string.album_id_is_null);
            dataBinding.btnBookmarkNotActive.setEnabled(false);
            dataBinding.btnBookmarkActive.setEnabled(false);
        }


    }

    //the library is deprecated for android p -_-
    private void showTags(AlbumDetailsRespnse albumDetailsRespnse) {
        if (albumDetailsRespnse.getAlbum().getTags() != null && albumDetailsRespnse.getAlbum().getTags().getTag() != null) {
            List<Tag> tags = albumDetailsRespnse.getAlbum().getTags().getTag();
            List<String> tagsStr = new ArrayList<>();
            for (int i = 0; i < tags.size(); i++) {
                String tag_name = tags.get(i).getName();
                tagsStr.add(tag_name);
            }
            dataBinding.tagcontainerLayout.setTags(tagsStr);
        }

    }

    private void showBookMarkActive() {
        dataBinding.btnBookmarkActive.setAnimation(AnimationUtilss.getScaleAnimation());
        dataBinding.btnBookmarkActive.setVisibility(View.VISIBLE);
    }

    private void showBookMarkNotActive() {
        dataBinding.btnBookmarkNotActive.setAnimation(AnimationUtilss.getScaleAnimation());
        dataBinding.btnBookmarkNotActive.setVisibility(View.VISIBLE);
    }

    private void enableBookmarkBtns() {
        dataBinding.btnBookmarkNotActive.setEnabled(true);
        dataBinding.btnBookmarkActive.setEnabled(true);
    }

    private void disableBookMarkBtns() {
        dataBinding.btnBookmarkNotActive.setEnabled(false);
        dataBinding.btnBookmarkActive.setEnabled(false);
    }

}
