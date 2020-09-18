package com.miguelarc.book_store_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.miguelarc.book_store_app.database.DataTypeConverter;

import java.util.List;

public  class VolumeInfo implements Parcelable {
    private String title;
    private String description;
    @Embedded
    private ImageLinks imageLinks;
    @TypeConverters(DataTypeConverter.class)
    private List<String> authors;

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

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public static class ImageLinks implements Parcelable {
        /**
         * smallThumbnail : http://books.google.com/books/content?id=8u9wJowXfdUC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api
         * thumbnail : http://books.google.com/books/content?id=8u9wJowXfdUC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api
         */

        private String smallThumbnail;
        private String thumbnail;

        public String getSmallThumbnail() {
            return smallThumbnail;
        }

        public void setSmallThumbnail(String smallThumbnail) {
            this.smallThumbnail = smallThumbnail;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.smallThumbnail);
            dest.writeString(this.thumbnail);
        }

        public ImageLinks() {
        }

        protected ImageLinks(Parcel in) {
            this.smallThumbnail = in.readString();
            this.thumbnail = in.readString();
        }

        public static final Creator<ImageLinks> CREATOR = new Creator<ImageLinks>() {
            @Override
            public ImageLinks createFromParcel(Parcel source) {
                return new ImageLinks(source);
            }

            @Override
            public ImageLinks[] newArray(int size) {
                return new ImageLinks[size];
            }
        };
    }

    public VolumeInfo(String title, String description, ImageLinks imageLinks, List<String> authors) {
        this.title = title;
        this.description = description;
        this.imageLinks = imageLinks;
        this.authors = authors;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeParcelable(this.imageLinks, flags);
        dest.writeStringList(this.authors);
    }

    protected VolumeInfo(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.imageLinks = in.readParcelable(ImageLinks.class.getClassLoader());
        this.authors = in.createStringArrayList();
    }

    public static final Parcelable.Creator<VolumeInfo> CREATOR = new Parcelable.Creator<VolumeInfo>() {
        @Override
        public VolumeInfo createFromParcel(Parcel source) {
            return new VolumeInfo(source);
        }

        @Override
        public VolumeInfo[] newArray(int size) {
            return new VolumeInfo[size];
        }
    };
}
