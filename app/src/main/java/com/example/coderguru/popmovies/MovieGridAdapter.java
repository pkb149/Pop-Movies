package com.example.coderguru.popmovies;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.PosterViewHolder> {
    final private GridItemClickListener mOnClickListener;

    private int mNumberItems;
    private MovieData[] simpleJsonMovieData;
    private Context context;

    public interface GridItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MovieGridAdapter(int numberOfItems, MovieData[] SimpleJsonMovieData, GridItemClickListener listener, Context context1) {
        mNumberItems = numberOfItems;
        simpleJsonMovieData = SimpleJsonMovieData;
        mOnClickListener = listener;
        context = context1;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.rv_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        PosterViewHolder viewHolder = new PosterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView poster;

        public PosterViewHolder(View itemView) {
            super(itemView);
            // completed connect the image view poster to view using fndviewbyid
            poster = (ImageView) itemView.findViewById(R.id.iv_item_poster);
            // listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }

        void bind(int listIndex) {
            //listItemNumberView.setText(String.valueOf(listIndex));
            // fetch the poster at listIndex position and set into image view using picasso
            //simpleJsonMovieData[listIndex].getPosterPath()
            //"w92", "w154", "w185", "w342", "w500", "w780", or "original". size
            if(simpleJsonMovieData!=null) {

                //todo instead of using getposterpath we need to directly call the field. or parcelable
                Picasso.with(context).load("http://image.tmdb.org/t/p/w500" + simpleJsonMovieData[listIndex].getPosterPath()).into(poster);
            }
        }
    }

    public void setMovieData(MovieData[] movieData) {
        simpleJsonMovieData = movieData;
        notifyDataSetChanged();
    }
}
