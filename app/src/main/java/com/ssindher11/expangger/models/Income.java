package com.ssindher11.expangger.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Income implements Parcelable {

    private double amount;
    private String title;
    private String type;
    private String date;

    public Income() {
    }

    public Income(double amount, String title, String type, String date) {
        this.amount = amount;
        this.title = title;
        this.type = type;
        this.date = date;
    }

    public Income(Parcel in) {
        amount = in.readDouble();
        title = in.readString();
        type = in.readString();
        date = in.readString();
    }

    public static final Creator<Income> CREATOR = new Creator<Income>() {
        @Override
        public Income createFromParcel(Parcel in) {
            return new Income(in);
        }

        @Override
        public Income[] newArray(int size) {
            return new Income[size];
        }
    };

    public static Creator<Income> getCreator() {
        return CREATOR;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(amount);
        parcel.writeString(title);
        parcel.writeString(type);
        parcel.writeString(date);
    }
}
