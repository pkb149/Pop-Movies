package com.example.coderguru.popmovies;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailedView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MovieData movieData = getIntent().getParcelableExtra("DetailedMovieData");
        TextView title=(TextView)findViewById(R.id.tv_originalTitle);
        ImageView poster=(ImageView)findViewById(R.id.iv_poster);
        TextView releaseDate=(TextView) findViewById(R.id.tv_releasedate);
        TextView voteAverage=(TextView)findViewById(R.id.tv_vote_average);
        TextView overview=(TextView)findViewById(R.id.tv_overview);
        if(movieData!=null){
            title.setText(movieData.getOriginalTitle());
            Picasso.with(this).load("http://image.tmdb.org/t/p/w500" + movieData.getPosterPath())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(poster);
            releaseDate.append(movieData.getRelease_date());
            voteAverage.setText(movieData.getvoteAverage()+"/10");
            //Toast.makeText(this,"vote: "+movieData.getvoteAverage(),Toast.LENGTH_LONG).show();
            overview.setText(movieData.getOverview());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_for_detail_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
