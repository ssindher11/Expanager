package com.ssindher11.expangger.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ssindher11.expangger.HorizontalCalendarView;
import com.ssindher11.expangger.R;
import com.ssindher11.expangger.models.HorizontalCalendarModel;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HorizontalCalendarAdapter extends RecyclerView.Adapter<HorizontalCalendarAdapter.MyViewHolder> {
    private ArrayList<HorizontalCalendarModel> list;
    private Context mCtx;
    private HorizontalCalendarView.OnCalendarListener onCalendarListener;
    private int mDrawable;

    private int rowIndex = 0;

    public void setOnCalendarListener(HorizontalCalendarView.OnCalendarListener onCalendarListener) {
        this.onCalendarListener = onCalendarListener;
    }

    public HorizontalCalendarAdapter(ArrayList<HorizontalCalendarModel> list, Context mCtx) {
        this.list = list;
        this.mCtx = mCtx;
    }

    public HorizontalCalendarAdapter(ArrayList<HorizontalCalendarModel> list, Context mCtx, int mDrawable) {
        this.list = list;
        this.mCtx = mCtx;
        this.mDrawable = mDrawable;
    }

    public HorizontalCalendarAdapter(ArrayList<HorizontalCalendarModel> list, Context mCtx, int mDrawable, int initialPosition) {
        this.list = list;
        this.mCtx = mCtx;
        this.mDrawable = mDrawable;
        this.rowIndex = initialPosition;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date, month, day;
        LinearLayout parent;

        MyViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.date);
            month = view.findViewById(R.id.month);
            parent = view.findViewById(R.id.parent);
            day = view.findViewById(R.id.day);
        }
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_calendar_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final HorizontalCalendarModel model = list.get(position);

        Display display = ((Activity) mCtx).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        holder.parent.setMinimumWidth(Math.round(width / 7));

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM EEE", Locale.getDefault());
        final SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        holder.date.setText(sdf.format(model.getTimeinmilli()).split(" ")[0]);
        holder.month.setText(sdf.format(model.getTimeinmilli()).split(" ")[1]);
        holder.day.setText(sdf.format(model.getTimeinmilli()).split(" ")[2]);

        if (model.getStatus() == 0) {
            holder.date.setTextColor(mCtx.getColor(R.color.white));
            holder.month.setTextColor(mCtx.getColor(R.color.white));
            holder.day.setTextColor(mCtx.getColor(R.color.white));
            holder.parent.setBackgroundColor(Color.TRANSPARENT);
        } else {
            holder.date.setTextColor(mCtx.getColor(R.color.textColorLight));
            holder.month.setTextColor(mCtx.getColor(R.color.textColorLight));
            holder.day.setTextColor(mCtx.getColor(R.color.textColorLight));
            holder.parent.setBackgroundResource(mDrawable);
        }

        holder.parent.setOnClickListener(view -> {
            onCalendarListener.onDateSelected(sdf1.format(model.getTimeinmilli()));
            rowIndex = position;
            notifyDataSetChanged();
        });

        if (rowIndex == position) {
            holder.date.setTextColor(mCtx.getColor(R.color.textColorLight));
            holder.month.setTextColor(mCtx.getColor(R.color.textColorLight));
            holder.day.setTextColor(mCtx.getColor(R.color.textColorLight));
            holder.parent.setBackgroundResource(mDrawable);
        } else {
            holder.date.setTextColor(mCtx.getColor(R.color.white));
            holder.month.setTextColor(mCtx.getColor(R.color.white));
            holder.day.setTextColor(mCtx.getColor(R.color.white));
            holder.parent.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
