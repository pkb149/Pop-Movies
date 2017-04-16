package com.example.coderguru.popmovies;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;


/**
 * Created by CoderGuru on 09-04-2017.
 */

public final class ParseJSON {


    public static MovieData[] getSimpleMovieStringsFromJson(Context context, String movieJsonStr)
            throws JSONException {

        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray movieArray = movieJson.getJSONArray("results");

        MovieData parsedMovieData[] = new MovieData[movieArray.length()];
        for (int i = 0; i < movieArray.length(); i++) {
            parsedMovieData[i] = new MovieData(Parcel.obtain());
            JSONObject movie = movieArray.getJSONObject(i);
            if (movie != null) {
                String poster_path = movie.getString("poster_path");
                Log.d("String is ", poster_path);
                if (poster_path != null) {
                    parsedMovieData[i].setPosterPath(poster_path);
                    //not needed// instead of setting posterpath by last lin set everything at once by the write to parcel method
                }
                parsedMovieData[i].setOriginalTitle(movie.getString("original_title"));
                parsedMovieData[i].setOverview(movie.getString("overview"));
                parsedMovieData[i].setRelease_date(movie.getString("release_date"));
                parsedMovieData[i].setId(movie.getString("id"));
                parsedMovieData[i].setvoteAverage(movie.getString("vote_average"));
                //completed set other fields that we require in the next page
                //update movie data class as needed
            }
        }
        return parsedMovieData;
    }
}