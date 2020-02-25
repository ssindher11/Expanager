package com.ssindher11.expangger.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class IncomeList implements Parcelable {

    public static final Creator<IncomeList> CREATOR = new Creator<IncomeList>() {
        @Override
        public IncomeList createFromParcel(Parcel in) {
            return new IncomeList(in);
        }

        @Override
        public IncomeList[] newArray(int size) {
            return new IncomeList[size];
        }
    };
    private List<Income> incomes;

    public IncomeList() {
    }

    public IncomeList(List<Income> incomes) {
        this.incomes = incomes;
    }

    public IncomeList(Parcel in) {
        incomes = new ArrayList<>();
        in.readList(incomes, Income.class.getClassLoader());
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(incomes);
    }
}
