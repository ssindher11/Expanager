package com.ssindher11.expangger.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ExpenseList implements Parcelable {

    public static final Creator<ExpenseList> CREATOR = new Creator<ExpenseList>() {
        @Override
        public ExpenseList createFromParcel(Parcel in) {
            return new ExpenseList(in);
        }

        @Override
        public ExpenseList[] newArray(int size) {
            return new ExpenseList[size];
        }
    };
    private List<Expense> expenses;

    public ExpenseList() {
    }

    public ExpenseList(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public ExpenseList(Parcel in) {
        expenses = new ArrayList<>();
        in.readList(expenses, Expense.class.getClassLoader());
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(expenses);
    }
}
