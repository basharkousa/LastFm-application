package com.clicagency.lastfmapp.view.fragments.albumsArtistFragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.models.State;
import com.clicagency.lastfmapp.data.remote.models.albums.albumsArtist.AlbumsArtistRespnce;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.Artist;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.ArtistsResponse;
import com.clicagency.lastfmapp.databinding.FragmentAlbumsArtistBinding;
import com.clicagency.lastfmapp.tools.BasicTools;
import com.clicagency.lastfmapp.tools.SpacesItemDecoration;
import com.clicagency.lastfmapp.view.adapters.AlbumNetStateAdapter;
import com.clicagency.lastfmapp.view.adapters.AlbumsArtistAdapter;
import com.clicagency.lastfmapp.view.adapters.MyArtistAdapter;
import com.clicagency.lastfmapp.view.base.BaseFragment;
import com.clicagency.lastfmapp.view.listeners.OnBottomReached;
import com.clicagency.lastfmapp.viewmodel.ViewModelFactory;
import com.clicagency.lastfmapp.viewmodel.ViewModelNewInstanceFactory;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AlbumsArtistFragment extends BaseFragment<AlbumsArtistViewModel, FragmentAlbumsArtistBinding> {


    private AlbumsArtistAdapter adapter;
    private GridLayoutManager layout_manager;


    @Override
    protected Class<AlbumsArtistViewModel> getViewModel() {
        return AlbumsArtistViewModel.class;
    }


    @Override
    protected ViewModelProvider.Factory getViewModelFactory() {
//        return new ViewModelNewInstanceFactory(artist);
        return  null;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_albums_artist;
    }

    @Override
    public void initEvents() {

//        adapter = new AlbumNetStateAdapter(new AlbumNetStateAdapter.IOnCLickNetStateAdapter() {
//
//            @Override
//            public void onRetryClicked() {
//                viewModel.retry();
//            }
//
//            @Override
//            public void itemClicked(Album album, int position, View view) {
//
//                if (BasicTools.isConnected(parent)) {
////                    AlbumDetailFragment albumDetailFragment = new AlbumDetailFragment();
////                    albumDetailFragment.setAlbum(album);
//                    Bundle args = new Bundle();
//                    args.putString("transitionName", "transition" + position);
//                    args.putSerializable("Album",album);
////                    albumDetailFragment.setArguments(args);
////                    parent.show_fragment(albumDetailFragment, view, "transition" + position);
////                    parent.navController.navigate(R.id.albumDetailsFragment,args);
//                    parent.navigateTo(R.id.albumDetailsFragment,args);
//
//
//                    //parent.show_fragment2(albumDetailFragment,false);
//                } else {
//                    parent.showToastMessageShort(R.string.failed_to_connect);
//                }
//
//
//            }
//        });
//        adapter = new AlbumsArtistAdapter();

        dataBinding.rootLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //load(false);
//                loadAlbums();
                viewModel.getInitialAlbums();
            }
        });
        dataBinding.retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewModel.getInitialAlbums();
            }
        });

        dataBinding.appBar.tvArtistName.setOnClickListener(view -> {
            parent.onBackPressed();
        });
    }

    @Override
    public void initFragment(Bundle savedInstanceState) {

//         Bundle bundle = getArguments();
        if (savedInstanceState != null){
//          artist = (Artist) savedInstanceState.getSerializable("key");
//          artist.setName(artist.getName()+"'s");
        }
//        viewModel = ViewModelProviders.of(this,new ViewModelNewInstanceFactory(artist)).get(AlbumsArtistViewModel.class);
//        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel());

//        viewModel = new ViewModelProvider(this,new ViewModelNewInstanceFactory(artist)).get(AlbumsArtistViewModel.class);


//        dataBinding.setLifecycleOwner(this);
//        getLifecycle().addObserver(viewModel);
        getLifecycle().addObserver((LifecycleObserver) viewModel);
//        dataBinding.setViewModel(viewModel);
        initRecycler();
        observeAlbums();
        BasicTools.setBottomListener(layout_manager, dataBinding.recycler, adapter, new OnBottomReached() {
            @Override
            public void onReachBottom() {
//                if (current_page >= first_page) {
//                    if (more && updating)
//                        prgrs_more.setVisibility(View.VISIBLE);
//                    if (more && !updating)
//                        load(true);
//                }
                viewModel.getNewPage();
            }

            @Override
            public void onScrolledUp() {

//                prgrs_more.setVisibility(View.GONE);
            }
        });


//        loadAlbums();
//        viewModel.printMess();
//        viewModel.getNetworkState().observe(this, networkState -> adapter.setNetworkState(networkState));

    }

    private void observeAlbums() {
        adapter = new AlbumsArtistAdapter((album) -> {
//            AlbumsArtistFragment albumsArtistFragment = new AlbumsArtistFragment();
//            if (artist != null)
//                if (artist.getName() != null)
//                    albumsArtistFragment.setArtist(artist.getName());
            Bundle bundle = new Bundle();
            bundle.putSerializable("key",album);
//            parent.show_fragment2(albumsArtistFragment, false);
//            parent.navController.navigate(R.id.albumsArtistFragment,bundle);
        });
        viewModel.getAlbumsLiveData().observe(this, new Observer<State<AlbumsArtistRespnce>>() {
            @Override
            public void onChanged(State<AlbumsArtistRespnce> albumsResponseState) {
                switch (albumsResponseState.getStatus()) {
                    case LOADING:
                        buildLoadingSection();
                        break;
                    case SUCCESS:
                        buildContent(albumsResponseState.getData());
                        break;
                    case FAILED:
                        buildFailed();
//                    default:
//                        buildContent(artistsResponseState.getData());
                }
            }
        });
    }

    private void buildContent(AlbumsArtistRespnce data) {

        dataBinding.rootLayout.setRefreshing(false);
        dataBinding.recycler.setVisibility(View.VISIBLE);
        if (data.getTopalbums().getAlbum().size() == 0)
            dataBinding.emptyTv.setVisibility(View.VISIBLE);
//        artists.addAll(data.getArtists().getArtist());

        adapter.addItems(data.getTopalbums().getAlbum());
        dataBinding.recycler.setAdapter(adapter);
//        adapter.setArtistsList(data.getArtists().getArtist());
        dataBinding.rootLayout.setRefreshing(false);

    }

    private void buildLoadingSection() {
        dataBinding.rootLayout.setRefreshing(true);
//        dataBinding.recycler.setVisibility(View.GONE);
//        dataBinding.retryBtn.setVisibility(View.GONE);
//        dataBinding.emptyTv.setVisibility(View.GONE);
//        dataBinding.progressMain.setVisibility(View.VISIBLE);
    }

    private void buildFailed() {
//        dataBinding.progressMain.setVisibility(View.GONE);
//        dataBinding.recycler.setVisibility(View.GONE);
//        dataBinding.retryBtn.setVisibility(View.VISIBLE);

        parent.showTostMessage(R.string.failed_to_connect);
        dataBinding.rootLayout.setRefreshing(false);

    }


    //This must be observe, code it
    private void loadAlbums() {
//        dataBinding.retryBtn.setVisibility(View.GONE);
        dataBinding.rootLayout.setRefreshing(true);
        if (BasicTools.isConnected(parent)) {
//            viewModel.getAlbums(artist.getName());
            viewModel.getAlbumPagedList().observe(this, new Observer<PagedList<Album>>() {
                @Override
                public void onChanged(@Nullable PagedList<Album> items) {

//                    adapter.submitList(items);
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
        dataBinding.recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

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
