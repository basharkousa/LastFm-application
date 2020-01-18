package com.clicagency.lastfmapp.view.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.Artist;
import com.clicagency.lastfmapp.databinding.FragmentArtistsSerarchBinding;
import com.clicagency.lastfmapp.view.listeners.IOnClick;
import com.clicagency.lastfmapp.tools.BasicTools;
import com.clicagency.lastfmapp.tools.SpacesItemDecoration;
import com.clicagency.lastfmapp.view.adapters.ArtistAdapter;
import com.clicagency.lastfmapp.view.base.BaseFragment;
import com.clicagency.lastfmapp.viewmodel.ArtistViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchArtistsFragment extends BaseFragment<ArtistViewModel, FragmentArtistsSerarchBinding> {


    private ArrayList<Artist> artists = new ArrayList<>();
    private ArtistAdapter adapter;

    public static SearchArtistsFragment newInstance() {
        Bundle args = new Bundle();
        SearchArtistsFragment fragment = new SearchArtistsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected Class<ArtistViewModel> getViewModel() {
        return ArtistViewModel.class;
    }

    @Override
    protected ViewModelProvider.Factory getViewModelFactory() {
        return null;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_artists_serarch;
    }

    @Override
    public void init_events() {

        dataBinding.rootLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //load(false);
                loadArtists();
            }
        });

        dataBinding.ivSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataBinding.edSearch.getText().toString().equals("")) {
                    parent.showTostMessage(R.string.pelase_enter_value);
                } else {
                    BasicTools.hideKeyboard(parent);
                    loadArtists(dataBinding.edSearch.getText().toString());
                    dataBinding.edSearch.setText("");

                }
            }
        });

        dataBinding.edSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    dataBinding.ivSearchBtn.performClick();
                    return true;
                }
                return false;
            }
        });

        adapter.setClickListener(new IOnClick() {
            @Override
            public void itemClicked(View v, int position) {

                Artist artist = adapter.getItem(position);
                AlbumsArtistFragment albumsArtistFragment = new AlbumsArtistFragment();
                albumsArtistFragment.setArtist(artist);
                parent.show_fragment2(albumsArtistFragment, false);


            }
        });

    }

    @Override
    public void init_fragment(Bundle savedInstanceState) {
        initRecycler();
        viewModel.init();
        if (artists.size() == 0)
            loadArtists();

    }

    private void initRecycler() {
        GridLayoutManager layout_manager;
        if (parent.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layout_manager = new GridLayoutManager(parent.getApplicationContext(), 2);
        else
            layout_manager = new GridLayoutManager(parent.getApplicationContext(), 3);

        dataBinding.recycler.setLayoutManager(layout_manager);
        //init_listener();
        if (adapter == null) {
            adapter = new ArtistAdapter(parent);
            adapter.setArtistsList(artists);
            // Open subject when click on it

        }
        dataBinding.recycler.setAdapter(adapter);
        dataBinding.recycler.addItemDecoration(new SpacesItemDecoration((int) parent.getResources().getDimension(R.dimen.d0_4)));

    }

    private void loadArtists() {
        artists.clear();
        dataBinding.rootLayout.setRefreshing(true);
        dataBinding.retryBtn.setVisibility(View.GONE);
        dataBinding.emptyTv.setVisibility(View.GONE);
        if (BasicTools.isConnected(parent)) {
            //viewModel = ViewModelProviders.of(this).get(ArtistViewModel.class);
            viewModel.getArtistRepository().observe(this, new Observer<List<Artist>>() {
                @Override
                public void onChanged(List<Artist> artistsResponse) {
                    dataBinding.rootLayout.setRefreshing(false);
                    if (artistsResponse.size() == 0)
                        dataBinding.emptyTv.setVisibility(View.VISIBLE);
                    artists.addAll(artistsResponse);
                    adapter.setArtistsList(artistsResponse);

                }
            });

        } else {

            parent.showTostMessage(R.string.failed_to_connect);
            dataBinding.rootLayout.setRefreshing(false);

        }

    }

    private void loadArtists(String query) {
        artists.clear();
        dataBinding.rootLayout.setRefreshing(true);
        dataBinding.retryBtn.setVisibility(View.GONE);
        dataBinding.emptyTv.setVisibility(View.GONE);
        if (BasicTools.isConnected(parent)) {

            viewModel.getArtistRepository(query).observe(this, new Observer<List<Artist>>() {
                @Override
                public void onChanged(List<Artist> artistsResponse) {
                    if (artistsResponse == null)
                        parent.showTostMessage("Error");
                    dataBinding.rootLayout.setRefreshing(false);
                    if (artistsResponse.size() == 0)
                        dataBinding.emptyTv.setVisibility(View.VISIBLE);
                    artists.addAll(artistsResponse);
                    adapter.setArtistsList(artistsResponse);
                    adapter.notifyDataSetChanged();
                }
            });

        } else {

            parent.showTostMessage(R.string.failed_to_connect);
            dataBinding.rootLayout.setRefreshing(false);

        }
    }

    @Override
    public boolean on_back_pressed() {
        return true;
    }
}
