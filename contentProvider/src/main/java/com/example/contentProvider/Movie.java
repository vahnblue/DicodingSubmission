package com.example.contentProvider;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String id;
    private String photo;
    private String title;
    private String description;
    private String dateRelease;

    public Movie() {

    }

    public Movie(String photo, String title, String description, String dateRelease) {
        this.photo = photo;
        this.title = title;
        this.description = description;
        this.dateRelease = dateRelease;
    }

    public Movie(String id, String photo, String title, String description, String dateRelease) {
        this.id = id;
        this.photo = photo;
        this.title = title;
        this.description = description;
        this.dateRelease = dateRelease;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(String dateRelease) {
        this.dateRelease = dateRelease;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.photo);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.dateRelease);
    }

    protected Movie(Parcel in) {
        this.id = in.readString();
        this.photo = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.dateRelease = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
