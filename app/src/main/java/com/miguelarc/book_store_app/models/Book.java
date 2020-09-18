package com.miguelarc.book_store_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "book")
public class Book implements Parcelable {
    private String kind;
    @PrimaryKey @NonNull private String id = "";
    private String etag;
    private String selfLink;
    @Embedded
    private VolumeInfo volumeInfo;
    @Embedded
    private SaleInfo saleInfo;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public SaleInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kind);
        dest.writeString(this.id);
        dest.writeString(this.etag);
        dest.writeString(this.selfLink);
        dest.writeParcelable(this.volumeInfo, flags);
        dest.writeParcelable(this.saleInfo, flags);

    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.kind = in.readString();
        this.id = in.readString();
        this.etag = in.readString();
        this.selfLink = in.readString();
        this.volumeInfo = in.readParcelable(VolumeInfo.class.getClassLoader());
        this.saleInfo = in.readParcelable(SaleInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
