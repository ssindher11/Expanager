package com.ssindher11.expangger.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Expense implements Parcelable {

    private double amount;
    private String date;
    private String location;
    private String payment_mode;
    private String bill_url;        //optional
    private String description;     //optional
    private String type;

    public Expense(Parcel in) {
        amount = in.readDouble();
        date = in.readString();
        location = in.readString();
        payment_mode = in.readString();
        bill_url = in.readString();
        description = in.readString();
        type = in.readString();
    }

    public Expense() {
    }

    public Expense(double amount, String date, String location, String payment_mode, String bill_url, String type) {
        this.amount = amount;
        this.date = date;
        this.location = location;
        this.payment_mode = payment_mode;
        this.bill_url = bill_url;
        this.type = type;
    }

    public Expense(double amount, String date, String location, String payment_mode, String bill_url, String description, String type) {
        this.amount = amount;
        this.date = date;
        this.location = location;
        this.payment_mode = payment_mode;
        this.bill_url = bill_url;
        this.description = description;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getBill_url() {
        if (bill_url != null) {
            return bill_url;
        }
        return "";
    }

    public void setBill_url(String bill_url) {
        this.bill_url = bill_url;
    }

    public String getDescription() {
        if (description != null) {
            return description;
        }
        return "";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel parcel) {
            return new Expense(parcel);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    public static Creator<Expense> getCreator() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(amount);
        parcel.writeString(date);
        parcel.writeString(location);
        parcel.writeString(payment_mode);
        parcel.writeString(bill_url);
        parcel.writeString(description);
        parcel.writeString(type);
    }
}
