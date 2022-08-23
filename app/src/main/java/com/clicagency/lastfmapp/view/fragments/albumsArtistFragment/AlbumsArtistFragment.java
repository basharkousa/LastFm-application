package com.clicagency.lastfmapp.view.fragments.albumsArtistFragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.Artist;
import com.clicagency.lastfmapp.databinding.FragmentAlbumsArtistBinding;
import com.clicagency.lastfmapp.tools.BasicTools;
import com.clicagency.lastfmapp.tools.SpacesItemDecoration;
import com.clicagency.lastfmapp.view.adapters.AlbumNetStateAdapter;
import com.clicagency.lastfmapp.view.base.BaseFragment;

public class AlbumsArtistFragment extends BaseFragment<AlbumsArtistViewModel, FragmentAlbumsArtistBinding> {

    private Artist artist;
    private AlbumNetStateAdapter adapter;
    private GridLayoutManager layout_manager;


    @Override
    protected Class<AlbumsArtistViewModel> getViewModel() {
        return AlbumsArtistViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_albums_artist;
    }

    @Override
    public void initEvents() {
        dataBinding.retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAlbums();
            }
        });
        adapter = new AlbumNetStateAdapter(new AlbumNetStateAdapter.IOnCLickNetStateAdapter() {

            @Override
            public void onRetryClicked() {
                viewModel.retry();
            }

            @Override
            public void itemClicked(Album album, int position, View view) {

                if (BasicTools.isConnected(parent)) {
//                    AlbumDetailFragment albumDetailFragment = new AlbumDetailFragment();
//                    albumDetailFragment.setAlbum(album);
                    Bundle args = new Bundle();
                    args.putString("transitionName", "transition" + position);
                    args.putSerializable("Album",album);
//                    albumDetailFragment.setArguments(args);
//                    parent.show_fragment(albumDetailFragment, view, "transition" + position);
//                    parent.navController.navigate(R.id.albumDetailsFragment,args);
                    parent.navigateTo(R.id.albumDetailsFragment,args);


                    //parent.show_fragment2(albumDetailFragment,false);
                } else {
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

        dataBinding.tvArtistName.setOnClickListener(view -> {
            parent.onBackPressed();
        });
    }

    @Override
    public void initFragment(Bundle savedInstanceState) {
         Bundle bundle = getArguments();
        if (bundle != null){
          artist = (Artist) bundle.getSerializable("key");
          dataBinding.tvArtistName.setText(artist.getName()+"'s");
        }
        getLifecycle().addObserver(viewModel);

        initRecycler();
        loadAlbums();
//        viewModel.printMess();
        viewModel.getNetworkState().observe(this, networkState -> adapter.setNetworkState(networkState));

    }

    //This must be observe, code it
    private void loadAlbums() {
        dataBinding.retryBtn.setVisibility(View.GONE);
        dataBinding.rootLayout.setRefreshing(true);
        if (BasicTools.isConnected(parent)) {
//            viewModel.getAlbums(artist.getName());
            viewModel.getAlbumPagedList().observe(this, new Observer<PagedList<Album>>() {
                @Override
                public void onChanged(@Nullable PagedList<Album> items) {

                    adapter.submitList(items);
                    dataBinding.rootLayout.setRefreshing(false);
                    dataBinding.retryBtn.setVisibility(View.GONE);
                }
            });
            //setting the adapter

        } else {
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



    //    @ArtistName
//    public String getArtistName() {
////        if(artist != null){
////            if (artist != null)
////                return artist;
////        }
//            return "ArtistName";
//    }


    @Override
    public boolean onBackPressed() {
        BasicTools.logMessage("fragment","fragment_onBackPressed");
        return false;
    }

    public void setArtist(String artistnew) {
//        artist = artistnew;
    }

//    @Override
//    protected ViewModelProvider.Factory getViewModelFactory() {
//        return new ViewModelProvider.Factory() {
//            @NonNull
//            @Override
//            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//                return (T) new AlbumViewModel(parent.getApplication(), artist.getName());
//            }
//        };
//    }
}
