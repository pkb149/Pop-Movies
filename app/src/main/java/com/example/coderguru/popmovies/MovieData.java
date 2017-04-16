package com.example.coderguru.popmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CoderGuru on 09-04-2017.
 */

class MovieData implements Parcelable {

    private String posterPath=null;
    private String OriginalTitle=null;
    private String overview=null;
    private String release_date=null;
    private String id=null;
    private String voteAverage=null;


    MovieData(Parcel in) {
        this.posterPath = in.readString();
        this.OriginalTitle = in.readString();
        //this.skillSet = new ArrayList<Skill>();
        //in.readTypedList(skillSet, Skill.CREATOR);
        this.overview = in.readString();
        this.release_date = in.readString();
        this.id = in.readString();
        this.voteAverage=in.readString();
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalTitle() {
        return OriginalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getId() {
        return id;
    }

    public String getvoteAverage() {
        return voteAverage;
    }


    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOriginalTitle(String OriginalTitle) {
        this.OriginalTitle = OriginalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setvoteAverage(String voteAverage) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(OriginalTitle);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(id);
        dest.writeString(voteAverage);
    }
    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {

        @Override
        public MovieData createFromParcel(Parcel source) {
            return new MovieData(source);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
}