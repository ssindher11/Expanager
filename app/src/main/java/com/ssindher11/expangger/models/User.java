package com.ssindher11.expangger.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String uname;
    private Expense[] expenses;
    private Income[] incomes;

    public User() {
    }

    public User(String uname) {
        this.uname = uname;
    }

    public User(String uname, Expense[] expenses) {
        this.uname = uname;
        this.expenses = expenses;
    }

    public User(String uname, Income[] incomes) {
        this.uname = uname;
        this.incomes = incomes;
    }

    public User(String uname, Expense[] expenses, Income[] incomes) {
        this.uname = uname;
        this.expenses = expenses;
        this.incomes = incomes;
    }

    public User(Parcel in) {
        uname = in.readString();
        expenses = in.createTypedArray(Expense.CREATOR);
        incomes = in.createTypedArray(Income.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static Creator<User> getCreator() {
        return CREATOR;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Expense[] getExpenses() {
        return expenses;
    }

    public void setExpenses(Expense[] expenses) {
        this.expenses = expenses;
    }

    public Income[] getIncomes() {
        return incomes;
    }

    public void setIncomes(Income[] incomes) {
        this.incomes = incomes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uname);
        parcel.writeTypedArray(expenses, i);
        parcel.writeTypedArray(incomes, i);
    }
}
