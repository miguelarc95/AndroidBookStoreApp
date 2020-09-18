package com.miguelarc.book_store_app.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SaleInfo implements Parcelable {
    private String buyLink;

    public String getBuyLink() {
        return buyLink;
    }

    public SaleInfo(String buyLink) {
        this.buyLink = buyLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.buyLink);
    }

    protected SaleInfo(Parcel in) {
        this.buyLink = in.readString();
    }

    public static final Parcelable.Creator<SaleInfo> CREATOR = new Parcelable.Creator<SaleInfo>() {
        @Override
        public SaleInfo createFromParcel(Parcel source) {
            return new SaleInfo(source);
        }

        @Override
        public SaleInfo[] newArray(int size) {
            return new SaleInfo[size];
        }
    };
}
