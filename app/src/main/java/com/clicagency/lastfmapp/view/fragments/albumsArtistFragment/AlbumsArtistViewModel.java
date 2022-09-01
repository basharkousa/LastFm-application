package com.clicagency.lastfmapp.view.fragments.albumsArtistFragment;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.models.NetworkState;
import com.clicagency.lastfmapp.data.remote.models.State;
import com.clicagency.lastfmapp.data.remote.models.albums.albumsArtist.AlbumsArtistRespnce;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.Artist;
import com.clicagency.lastfmapp.data.remote.models.artists.artistsResponse.ArtistsResponse;
import com.clicagency.lastfmapp.data.remote.repositories.AlbumRepository;
import com.clicagency.lastfmapp.data.remote.repositories.albumPagedRepository.AlbumDataSource;
import com.clicagency.lastfmapp.data.remote.repositories.albumPagedRepository.AlbumDataSourceFactory;
import com.clicagency.lastfmapp.view.listeners.IResponseListener;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AlbumsArtistViewModel extends ViewModel implements DefaultLifecycleObserver {

    //creating livedata for PagedList and PagedKeyedDataSource

    // LiveData<PageKeyedDataSource<Integer, Album>> liveDataSource;

    private Executor executor;
    private LiveData<PagedList<Album>> albumPagedList;
    private LiveData<NetworkState> networkState;

    SavedStateHandle state ;
    public Artist artistModel;


//    ScrollController scrollController = ScrollController();
    boolean isLoading = false;
    int page = 0 ;
    int limit = 10;
    public AlbumsArtistRespnce albumsList = new AlbumsArtistRespnce();

    public MutableLiveData<State<AlbumsArtistRespnce>> getAlbumsLiveData() {
        return albumsLiveData;
    }

    public void setAlbumsLiveData(MutableLiveData<State<AlbumsArtistRespnce>> albumsLiveData) {
        this.albumsLiveData = albumsLiveData;
    }

    public MutableLiveData<State<AlbumsArtistRespnce>> getPaginationLiveData() {
        return paginationLiveData;
    }

    public void setPaginationLiveData(MutableLiveData<State<AlbumsArtistRespnce>> paginationLiveData) {
        this.paginationLiveData = paginationLiveData;
    }

    MutableLiveData<State<AlbumsArtistRespnce>> albumsLiveData = new MutableLiveData<>();
    MutableLiveData<State<AlbumsArtistRespnce>> paginationLiveData = new MutableLiveData<>();

    @Inject
    AlbumDataSourceFactory albumDataSourceFactory;

//    @Inject
    AlbumRepository repository;


//    SavedStateHandle savedStateHandle;

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onCreate(owner);

    }

    @Inject
    public AlbumsArtistViewModel(AlbumRepository albumRepository, SavedStateHandle savedStateHandle) {

        this.repository = albumRepository;
        artistModel = (Artist) savedStateHandle.get("key");
        Log.e("AlbumsArtistViewModel","Init()");
        Log.e("AlbumsArtisSavedSta",artistModel.getName());


//        scrollController.addListener(() {
//            if (scrollController.position.maxScrollExtent ==
//                    scrollController.position.pixels) {
//                print("End scrolling");
//                // if (!isLoading) {
//                //   print("end_scrolling");
//                //   isLoading = !isLoading;
//                //   widget.homePageBloc.getNewPage();
//                // }
//                getNewPage();
//            }
//        });
//        getAllCoins();

        getInitialAlbums();

//        this.artistModel = artistModel;
//        Log.e("AlbumsArtistViewModel ",artistModel.getName());
//        getAlbums();
        ///Test it for null
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStart(owner);
        printMess();
//        owner.
    }


    void printMess(){
       repository.printMessage("hello_from_repository");

   }

    public void getAlbums() {
        executor = Executors.newFixedThreadPool(5);
        //getting our data source factory
        //after dagger2
        //albumDataSourceFactory = new AlbumDataSourceFactory(artistName);
        albumDataSourceFactory.setArtistName(artistModel.getName());

        networkState = Transformations.switchMap(albumDataSourceFactory.getItemLiveDataSource(),
                dataSource -> dataSource.getNetworkState());

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        //.setInitialLoadSizeHint(10)
                        .setPageSize(AlbumDataSource.PAGE_SIZE).build();
        //Building the paged list
        albumPagedList = (new LivePagedListBuilder(albumDataSourceFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

    public void getInitialAlbums(){
        page = 1;
        albumsList = new AlbumsArtistRespnce();;
        albumsLiveData.postValue(new State<>(State.Status.LOADING,""));
        Log.e("Loadingg\n","");
        repository.getAlbumsArtistsRequest(page,limit,artistModel,new IResponseListener<AlbumsArtistRespnce>() {
            @Override
            public void onSuccess(AlbumsArtistRespnce data) {
                albumsLiveData.postValue(new State<>(State.Status.SUCCESS,data));
                albumsList = data;
                Log.e("Responssss","data.getArtists().getArtist().get(0).getName()");
            }

            @Override
            public void onFailure(String message,Throwable t) {
                albumsLiveData.postValue(new State<>(State.Status.FAILED,""));
                Log.e("FailedBB",message);
                t.printStackTrace();
            }
        });
    }
    public synchronized void getNewPage(){

        if(!isLoading){
            Log.e("getNewPage",page+"");
            page++;
            paginationLiveData.postValue(new State<>(State.Status.LOADING,""));
            isLoading = !isLoading;
            repository.getAlbumsArtistsRequest(page,limit,artistModel,new IResponseListener<AlbumsArtistRespnce>() {
                @Override
                public void onSuccess(AlbumsArtistRespnce data) {
                    albumsList.getTopalbums().getAlbum().addAll(data.getTopalbums().getAlbum());
                    albumsLiveData.postValue(new State<>(State.Status.SUCCESS,albumsList));
                    paginationLiveData.postValue(new State<>(State.Status.SUCCESS,albumsList));
                    isLoading = !isLoading;
                }

                @Override
                public void onFailure(String message,Throwable t) {
                    paginationLiveData.postValue(new State<>(State.Status.FAILED,""));
                    page--;
                    isLoading = !isLoading;
                    Log.e("FailedBB",message);
                }
            });

        }
    }


    public LiveData<PagedList<Album>> getAlbumPagedList() {
        return albumPagedList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

//    @Override
//    protected void onCleared() {
//        super.onCleared();
//        albumDataSourceFactory.getAlbumDataSource().clear();
//    }

    public void retry() {
        albumDataSourceFactory.getAlbumDataSource().retryPagination();
    }

}
