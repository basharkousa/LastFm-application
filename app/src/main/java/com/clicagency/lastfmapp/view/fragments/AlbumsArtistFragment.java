package com.clicagency.lastfmapp.view.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.Artist;
import com.clicagency.lastfmapp.databinding.FragmentAlbumsArtistBinding;
import com.clicagency.lastfmapp.tools.BasicTools;
import com.clicagency.lastfmapp.tools.SpacesItemDecoration;
import com.clicagency.lastfmapp.view.adapters.AlbumPagedAdapter;
import com.clicagency.lastfmapp.view.base.BaseFragment;
import com.clicagency.lastfmapp.view.listeners.IOnAlbumClick;
import com.clicagency.lastfmapp.viewmodel.AlbumViewModel;

public class AlbumsArtistFragment extends BaseFragment<AlbumViewModel, FragmentAlbumsArtistBinding> {


    private Artist artist;
    private final AlbumPagedAdapter adapter = new AlbumPagedAdapter(parent);
    private GridLayoutManager layout_manager;

    public static AlbumsArtistFragment newInstance() {
        Bundle args = new Bundle();
        AlbumsArtistFragment fragment = new AlbumsArtistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Class<AlbumViewModel> getViewModel() {
        return AlbumViewModel.class;
    }

    @Override
    protected ViewModelProvider.Factory getViewModelFactory() {
        return new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new AlbumViewModel(parent.getApplication(),artist.getName());
            }
        };
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_albums_artist;
    }

    @Override
    public void init_events() {
        dataBinding.retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAlbums();
            }
        });
        adapter.setClickListener(new IOnAlbumClick() {
            @Override
            public void itemClicked(Album album, int position,View view) {

                if(BasicTools.isConnected(parent)){
                    AlbumDetailFragment albumDetailFragment = new AlbumDetailFragment() ;
                    albumDetailFragment.setAlbum(album);
                    Bundle args = new Bundle();
                    args.putString("transitionName", "transition" + position);
                    albumDetailFragment.setArguments(args);
                    parent.show_fragment(albumDetailFragment,view,"transition" + position);
                    //parent.show_fragment2(albumDetailFragment,false);
                }else {
                    parent.showToastMessageShort(R.string.failed_to_connect);
                }


            }
        });

        dataBinding.rootLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //load(false);
                loadAlbums();
            }
        });

        dataBinding.retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAlbums();
            }
        });
    }

    @Override
    public void init_fragment(Bundle savedInstanceState) {

        if (artist != null) if (artist.getName() != null)
                dataBinding.tvArtistName.setText(artist.getName()+"'s");

        initRecycler();
        loadAlbums();
    }

    private void loadAlbums() {
        dataBinding.retryBtn.setVisibility(View.GONE);
        dataBinding.rootLayout.setRefreshing(true);
        if(BasicTools.isConnected(parent)){
            viewModel.getAlbumPagedList().observe(this, new Observer<PagedList<Album>>() {
                @Override
                public void onChanged(@Nullable PagedList<Album> items) {

                    adapter.submitList(items);
                    dataBinding.rootLayout.setRefreshing(false);
                    dataBinding.retryBtn.setVisibility(View.GONE);
                }
            });
            //setting the adapter

        }else {
            parent.showTostMessage(R.string.failed_to_connect);
            dataBinding.retryBtn.setVisibility(View.VISIBLE);

        }

    }

    private void initRecycler() {

        if (parent.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layout_manager = new GridLayoutManager(parent.getApplicationContext(), 2);
        else
            layout_manager = new GridLayoutManager(parent.getApplicationContext(), 3);

        dataBinding.recycler.setLayoutManager(layout_manager);
        dataBinding.recycler.setAdapter(adapter);
        dataBinding.recycler.addItemDecoration(new SpacesItemDecoration((int) parent.getResources().getDimension(R.dimen.d0_4)));

    }

    @Override
    public boolean on_back_pressed() {
        return true;
    }

    public void setArtist(Artist artistnew) {
      artist = artistnew;
    }
}
