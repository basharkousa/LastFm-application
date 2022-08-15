package com.clicagency.lastfmapp.view.fragments.mainFragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.clicagency.lastfmapp.R;
import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.databinding.FragmentMainPageBinding;
import com.clicagency.lastfmapp.tools.BasicTools;
import com.clicagency.lastfmapp.tools.SpacesItemDecoration;
import com.clicagency.lastfmapp.view.adapters.AlbumPagedAdapter;
import com.clicagency.lastfmapp.view.base.BaseFragment;
import com.clicagency.lastfmapp.view.fragments.albumDetailsFragment.AlbumDetailFragment;
import com.clicagency.lastfmapp.view.listeners.IOnAlbumClick;

public class MainPageFragment extends BaseFragment<MainPageViewModel, FragmentMainPageBinding> {

    private final AlbumPagedAdapter adapter = new AlbumPagedAdapter(parent);
    private GridLayoutManager layout_manager;

    public static MainPageFragment newInstance() {
        Bundle args = new Bundle();
        MainPageFragment fragment = new MainPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Class<MainPageViewModel> getViewModel() {
        return MainPageViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main_page;
    }

    @Override
    public void initEvents() {
        dataBinding.retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAlbums();
            }
        });
        adapter.setClickListener(new IOnAlbumClick() {
            @Override
            public void itemClicked(Album album, int position, View view) {

                if (BasicTools.isConnected(parent)) {
//
//                  albumDetailFragment.setArguments(args);
//                  parent.show_fragment(albumDetailFragment, view, "transition" + position);
                    Bundle args = new Bundle();
                    args.putString("transitionName", "transition" + position);
                    args.putSerializable("Album",album);
//                    albumDetailFragment.setArguments(args);
//                    parent.show_fragment(albumDetailFragment, view, "transition" + position);
                    parent.navController.navigate(R.id.albumDetailsFragment,args);

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

    }

    @Override
    public void initFragment(Bundle savedInstanceState) {
        initRecycler();
        loadAlbums();
    }


    private void initRecycler() {

        if (parent.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layout_manager = new GridLayoutManager(parent.getApplicationContext(), 2);
        else
            layout_manager = new GridLayoutManager(parent.getApplicationContext(), 3);

        dataBinding.recycler.setLayoutManager(layout_manager);
        dataBinding.recycler.addItemDecoration(new SpacesItemDecoration((int) parent.getResources().getDimension(R.dimen.d0_4)));
        dataBinding.recycler.setAdapter(adapter);
    }


    private void loadAlbums() {
        dataBinding.retryBtn.setVisibility(View.GONE);
        //dataBinding.rootLayout.setRefreshing(true);

            viewModel.getmAlbumsPerPage().observe(this, new Observer<PagedList<Album>>() {
                @Override
                public void onChanged(@Nullable PagedList<Album> items) {
                    if(items != null){
                        if (items.size() == 0) {
                            dataBinding.emptyTv.setVisibility(View.VISIBLE);
                        } else {
                            dataBinding.emptyTv.setVisibility(View.GONE);
                        }
                        adapter.submitList(items);
                        dataBinding.rootLayout.setRefreshing(false);
                        dataBinding.retryBtn.setVisibility(View.GONE);
                    }
                }


            });

    }


    @Override
    public void onResume() {
        Log.e("OnResume","");
        super.onResume();
    }

//    parent.getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
//        @Override
//        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
//            // We use a String here, but any type that can be put in a Bundle is supported
//            String result = bundle.getString("bundleKey");
//            // Do something with the result
//        }
//    });

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
