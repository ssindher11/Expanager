package com.ssindher11.expangger.models;

public class HorizontalCalendarModel {

    private long timeinmilli;
    private int status = 0; //0->no color, 1->green, 2-> blue

    public HorizontalCalendarModel(long timeinmilli) {
        this.timeinmilli = timeinmilli;
    }

    public long getTimeinmilli() {
        return timeinmilli;
    }

    public void setTimeinmilli(long timeinmilli) {
        this.timeinmilli = timeinmilli;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
