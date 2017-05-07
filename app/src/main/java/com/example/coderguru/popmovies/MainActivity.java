package com.example.coderguru.popmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements MovieGridAdapter.GridItemClickListener {
    MovieData[] simpleJsonMovieData;
    Toast mToast;
    MovieGridAdapter.GridItemClickListener listener;
    private MovieGridAdapter mAdapter;
    private RecyclerView mPosterGrid;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listener = this;
        mPosterGrid = (RecyclerView) findViewById(R.id.rv_posters);
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            mPosterGrid.setLayoutManager(layoutManager);
        }
        else{
            GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
            mPosterGrid.setLayoutManager(layoutManager);
        }
        mPosterGrid.setHasFixedSize(true);

        // create a loader in activity main
        // make it visible
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        new MovieDataTask().execute("popular");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    URL UriBuilder(String type) {
        final String BASE_URL = "https://api.themoviedb.org/3/movie";
                //"https://api.themoviedb.org/3/discover/movie";
        //String apiKey="0beb13856cb6a24fc8bd27b4dbfcbfe9";
        //String language="en-US";
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(type)
                .appendQueryParameter("api_key", getString(R.string.api_key))
                .appendQueryParameter("language", getString(R.string.language))
                .appendQueryParameter("page", "1")
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        //mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        //mToast.show();

        Context context = this;
        Class destinationClass = DetailedView.class;// create activity and change this line
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("DetailedMovieData",simpleJsonMovieData[clickedItemIndex]);
        startActivity(intentToStartDetailActivity);

        //  Starting new activity dynamically
    }

    public class MovieDataTask extends AsyncTask<String, Void, MovieData[]> {
        @Override
        protected MovieData[] doInBackground(String... sortBy) {
            if (sortBy.length == 0) {
                return null;
            }
            String preference = sortBy[0];
            URL reqestURL = UriBuilder(preference);
            if (reqestURL == null || reqestURL.equals("")) {
                Log.d(this.toString(), "URL is NULL");
            } else {
                Log.d(this.toString(), reqestURL.toString());
            }
                try {
                    String Response = getResponseFromHttpUrl(reqestURL);
                    simpleJsonMovieData = ParseJSON.getSimpleMovieStringsFromJson(getApplicationContext(), Response);
                    return simpleJsonMovieData;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
        }

        @Override
        protected void onPostExecute(MovieData[] simpleJsonMovieData) {

            if (simpleJsonMovieData != null) {
                //for(MovieData movieData:simpleJsonMovieData){
                //ImageView imageView=(ImageView) findViewById(R.id.iv_item_poster);
                //String posterLink=movieData.getPosterPath();
                //Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185"+movieData.getPosterPath()).into(imageView);
                //completed(1) set image view using Picasso
                mAdapter = new MovieGridAdapter(simpleJsonMovieData.length, simpleJsonMovieData, listener, getApplicationContext());
                mPosterGrid.setAdapter(mAdapter);
                progressBar.setVisibility(View.INVISIBLE);
                // set the loader invisible
                // }
            }
            else{
                Toast.makeText(getApplicationContext(),"Couldn't load data! Please check your internet connection and try again.",Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.popularity) {
            // clear the rvadapter
            new MovieDataTask().execute("popular");
            // completed Start Async Task
            progressBar.setVisibility(View.VISIBLE);
            return true;
        }
        else if (item.getItemId() == R.id.rating) {
            // clear the rv adapter
            new MovieDataTask().execute("top_rated");
            // completed Start Async Task
            progressBar.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
