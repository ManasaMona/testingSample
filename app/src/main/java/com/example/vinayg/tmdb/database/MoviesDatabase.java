package com.example.vinayg.tmdb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vinayg.tmdb.models.Movie;

import java.util.ArrayList;

import static com.example.vinayg.tmdb.database.MovieDBSchema.MoviesTable;
/**
 * Created by manasa.a.
 */
public class MoviesDatabase extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static MoviesDatabase instance;
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "moviesdatabase.db";

    private MoviesDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    public static MoviesDatabase getInstance(Context context){
        if(instance == null){
            instance = new MoviesDatabase(context);
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ MoviesTable.NAME +"( "+
                MoviesTable.Cols.ID +" integer primary key autoincrement, "+
                MoviesTable.Cols.MOVIE_NAME+ " varchar(50) , " +
                MoviesTable.Cols.MOVIE_ID+" integer , "+
                MoviesTable.Cols.MOVIE_IMAGE_URL + " varchar(500) , " +
                MoviesTable.Cols.BACKGROUND_IMG + " varchar(500) , "+
                MoviesTable.Cols.AVG_RATING+ " varchar(20) , "+
                MoviesTable.Cols.CATEGORY+" varchar(20), " +
                MoviesTable.Cols.OVERVIEW+" varchar(1000), "+
                MoviesTable.Cols.GENRE+" varchar(100), "+
                MoviesTable.Cols.RELEASE_DATE+" varchar(10), "+
                MoviesTable.Cols.IS_FAVORITE + " integer default 0 )"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesTable.NAME);
    }
    public void insertMovie(Movie movie){
        db = this.getWritableDatabase();
        ContentValues values = getContentValues(movie);
        db .insert(MoviesTable.NAME, null, values);
    }
    public void deleteMovie(Movie value)
    {
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + MoviesTable.NAME + " WHERE "+MoviesTable.Cols.MOVIE_ID+"='"+value.getMovieId()+"'");
        db.close();
    }
    public  ArrayList<Movie> getUserFavoriteMovies(){
       db= this.getReadableDatabase();
        ArrayList<Movie> favMovieList;
        Cursor cursor =  db.rawQuery("select * from " + MoviesTable.NAME + " where " +MoviesTable.Cols.IS_FAVORITE + "=" + 1 , null);
        favMovieList = getFavMoviesList(cursor);
        cursor.close();
        return favMovieList;
    }

    private ArrayList<Movie> getFavMoviesList(Cursor cursor) {
        ArrayList<Movie> favMovieList = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Movie movie = new Movie();
                    movie.setMovieId(cursor.getInt(cursor.getColumnIndex(MoviesTable.Cols.MOVIE_ID)));
                    movie.setTitle(cursor.getString(cursor.getColumnIndex(MoviesTable.Cols.MOVIE_NAME)));
                    movie.setImageUrl(cursor.getString(cursor.getColumnIndex(MoviesTable.Cols.MOVIE_IMAGE_URL)));
                    movie.setIsFavorite(cursor.getInt(cursor.getColumnIndex(MoviesTable.Cols.IS_FAVORITE)));
                    movie.setBackgroundImage(cursor.getString(cursor.getColumnIndex(MoviesTable.Cols.BACKGROUND_IMG)));
                    movie.setOverview(cursor.getString(cursor.getColumnIndex(MoviesTable.Cols.OVERVIEW)));
                    movie.setRelease_date(cursor.getString(cursor.getColumnIndex(MoviesTable.Cols.RELEASE_DATE)));
                    movie.setGenre(cursor.getString(cursor.getColumnIndex(MoviesTable.Cols.GENRE)));
                    favMovieList.add(movie);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return favMovieList;
    }

    private static ContentValues getContentValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MoviesTable.Cols.MOVIE_NAME, movie.getTitle());
        values.put(MoviesTable.Cols.MOVIE_IMAGE_URL, movie.getImageUrl());
        values.put(MoviesTable.Cols.IS_FAVORITE, movie.getIsFavorite());
        values.put(MoviesTable.Cols.MOVIE_ID,movie.getMovieId());
        values.put(MoviesTable.Cols.BACKGROUND_IMG,movie.getBackgroundImage());
        values.put(MoviesTable.Cols.OVERVIEW,movie.getOverview());
        values.put(MoviesTable.Cols.AVG_RATING,movie.getAverageRating());
        values.put(MoviesTable.Cols.RELEASE_DATE,movie.getRelease_date());
        values.put(MoviesTable.Cols.GENRE,movie.getGenre());
        return values;
    }

    public Boolean checkIfsaved(Movie movie) {
        db= this.getReadableDatabase();
        String selection = MoviesTable.Cols.MOVIE_ID + " = ?";
        String[] selectionArgs = { movie.getMovieId()+"" };
        Cursor cursor = db.query(MoviesTable.NAME,new String[]{MoviesTable.Cols.IS_FAVORITE} ,
                selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
}
