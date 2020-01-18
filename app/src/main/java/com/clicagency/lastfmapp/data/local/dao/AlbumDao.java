package com.clicagency.lastfmapp.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.clicagency.lastfmapp.data.local.entity.Album;

import java.util.List;

@Dao
public interface AlbumDao {

    @Query("SELECT * FROM albums")
    LiveData<List<Album>> getAllAlbums();

    // Using Paging
    @Query("SELECT * FROM albums")
    DataSource.Factory<Integer, Album> getAllAlbumsPerPage();

    @Query("SELECT * FROM albums WHERE mbid = :id")
    LiveData<Album> getAlbumById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbum(Album AlbumEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAlbum(Album AlbumEntity);

    @Delete
    void deleteAlbum(Album albumEntity);

    @Query("DELETE FROM albums")
    void deleteAll();
}
