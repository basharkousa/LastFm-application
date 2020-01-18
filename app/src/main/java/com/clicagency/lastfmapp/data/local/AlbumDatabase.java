package com.clicagency.lastfmapp.data.local;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.clicagency.lastfmapp.data.local.converter.DateConverter;
import com.clicagency.lastfmapp.data.local.dao.AlbumDao;
import com.clicagency.lastfmapp.data.local.entity.Album;

@Database(entities = {Album.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AlbumDatabase extends RoomDatabase {

    private static final String TAG = AlbumDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "lastfmdb";
    private static AlbumDatabase sInstance;

    public static AlbumDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "getInstance: Creating a new database instance");
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AlbumDatabase.class,
                        AlbumDatabase.DATABASE_NAME)
                        .addCallback(sRoomDatabaseCallback) // to populate database
                        .build();
            }
        }
        Log.d(TAG, "getInstance: Getting the database instance, no need to recreated it.");
        return sInstance;
    }

    public abstract AlbumDao albumDao();

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(sInstance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AlbumDao mSubjectDao;

        PopulateDbAsync(AlbumDatabase db) {
            mSubjectDao = db.albumDao();

        }

        @Override
        protected Void doInBackground(final Void... params) {
           // for add fake data to database
            return null;
        }
    }

}
