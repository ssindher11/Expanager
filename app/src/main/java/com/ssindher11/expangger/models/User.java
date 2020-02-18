package com.ssindher11.expangger.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class User implements Parcelable {

    private String uname;
    private HashMap<String, Expense> expenses;
    private HashMap<String, Income> incomes;

    public User() {
    }

    public User(String uname) {
        this.uname = uname;
    }

    public User(String uname, HashMap<String, Expense> expenses) {
        this.uname = uname;
        this.expenses = expenses;
    }

    /*public User(String uname, List<Income> incomes) {
        this.uname = uname;
        this.incomes = incomes;
    }*/

    public User(String uname, HashMap<String, Expense> expenses, HashMap<String, Income> incomes) {
        this.uname = uname;
        this.expenses = expenses;
        this.incomes = incomes;
    }

    public User(Parcel in) {
        uname = in.readString();
        expenses = (HashMap<String, Expense>) in.readSerializable();
        incomes = (HashMap<String, Income>) in.readSerializable();
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

    public HashMap<String, Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(HashMap<String, Expense> expenses) {
        this.expenses = expenses;
    }

    public HashMap<String, Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(HashMap<String, Income> incomes) {
        this.incomes = incomes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uname);
        parcel.writeSerializable(expenses);
        parcel.writeSerializable(incomes);
    }
}
