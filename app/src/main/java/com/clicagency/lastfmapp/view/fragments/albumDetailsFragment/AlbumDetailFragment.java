package com.clicagency.lastfmapp.view.fragments.albumDetailsFragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
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
import com.clicagency.lastfmapp.view.activities.MainActivity;
import com.clicagency.lastfmapp.view.adapters.TracksAdapter;
import com.clicagency.lastfmapp.view.base.BaseFragment;
import com.clicagency.lastfmapp.view.listeners.IOnClick;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AlbumDetailFragment extends BaseFragment<AlbumDetailsViewModel, FragmentAlbumDetailsBinding> {

    private Album album;
    private String artistName = "";
    private String albumName= "";

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    protected Class<AlbumDetailsViewModel> getViewModel() {
        return AlbumDetailsViewModel.class;
    }

    @Override
    protected ViewModelProvider.Factory getViewModelFactory() {
        return null;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_album_details;
    }

    @Override
    public void initEvents() {
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
                getAlbumsDetails();
            }
        });

    }

    @Override
    public void initFragment(Bundle savedInstanceState) {

//        if (album != null) {
//            albumName = album.getName();
//            if (album.getArtist() != null)
//                artistName = album.getArtist().getName();
//            else
//                artistName = album.getArtist_name();
//        }

        ((MainActivity)parent).dataBinding.bottomNavigation.hideBottomNavigation();

        Bundle bundle = getArguments();
        if (bundle != null){
            album = (Album) bundle.getSerializable("Key");

            albumName = album.getName();
            if (album.getArtist() != null)
                artistName = album.getArtist().getName();
            else
                artistName = album.getArtist_name();

        }


        getAlbumsDetails();
        observeAlbumDetails();
        observeErrorMessage();
        handleBookmarkState();
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    private void getAlbumsDetails() {
        dataBinding.ivRetryContent.setVisibility(View.GONE);
        dataBinding.layoutPrgrs.setVisibility(View.VISIBLE);
        dataBinding.layoutContent.setVisibility(View.GONE);
        disableBookMarkBtns();
        if (BasicTools.isConnected(parent)) {
            viewModel.getAlbumDetails(albumName,artistName);
        } else {
            parent.showTostMessage(R.string.failed_to_connect);
            disableBookMarkBtns();
            dataBinding.layoutPrgrs.setVisibility(View.GONE);
            dataBinding.layoutContent.setVisibility(View.GONE);
            dataBinding.ivRetryContent.setVisibility(View.VISIBLE);

        }
    }



    private void observeAlbumDetails() {
        viewModel.getMutableLiveDataAlbum().observe(this, albumDetailsRespnse -> {
            dataBinding.setAlbumDetails(albumDetailsRespnse.getAlbum());
            //showTags(albumDetailsRespnse);
            fillAllAlbumsItems(albumDetailsRespnse);

        });
    }

    private void observeErrorMessage() {
        viewModel.getErrorMessageReceived().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dataBinding.layoutPrgrs.setVisibility(View.GONE);
                dataBinding.layoutContent.setVisibility(View.GONE);
                dataBinding.ivRetryContent.setVisibility(View.VISIBLE);
                disableBookMarkBtns();
                parent.showToastMessageShort(s);
            }
        });
    }

    private void fillAllAlbumsItems(AlbumDetailsRespnse albumDetailsRespnse) {

        enableBookmarkBtns();
        dataBinding.layoutPrgrs.setVisibility(View.GONE);
        dataBinding.layoutContent.setVisibility(View.VISIBLE);

        //for set artist name to album.
        Artist artist = new Artist();
        if (albumDetailsRespnse.getAlbum().getTracks().getTrack().size() > 0)
            artist.setName(albumDetailsRespnse.getAlbum().getTracks().getTrack().get(0).getArtist().getName());
        album.setArtist(artist);

        //for tracks
        if (albumDetailsRespnse.getAlbum().getTracks().getTrack() != null)
            loadTracks(albumDetailsRespnse);

        long listenersLong = Long.parseLong(albumDetailsRespnse.getAlbum().getListeners());
        dataBinding.tvListeners.setText(BasicTools.format(listenersLong));

        //for description
        if (albumDetailsRespnse.getAlbum().getWiki() != null) {
            String desc = albumDetailsRespnse.getAlbum().getWiki().getSummary();
            BasicTools.setTextViewHTML(dataBinding.tvDesc,"<div>" + desc + "</div>",parent);
        } else {
            dataBinding.tvDesc.setText(R.string.no_description);
        }

    }

    private void saveAlbumInDatabase(Album album) {
        if (album.getArtist() != null && album.getMbid() != null) {
            album.setArtist_name(album.getArtist().getName());
            album.setImage_url_database(album.getImageUrl());
            viewModel.insert(album);
        }

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
            for (Track t : albumDetailsRespnse.getAlbum().getTracks().getTrack()) {
                sumDuration += Integer.parseInt(t.getDuration());
            }
            String length_value = albumDetailsRespnse.getAlbum().getTracks().getTrack().size() + " Tracks, " + BasicTools.formatSeconds(sumDuration);
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
//            dataBinding.tagcontainerLayout.setTags(tagsStr);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)parent).dataBinding.bottomNavigation.restoreBottomNavigation();
    }
}
