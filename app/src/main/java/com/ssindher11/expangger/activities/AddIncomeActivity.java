package com.ssindher11.expangger.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ssindher11.expangger.HorizontalCalendarView;
import com.ssindher11.expangger.R;
import com.ssindher11.expangger.Utils;

import java.util.ArrayList;
import java.util.Calendar;

public class AddIncomeActivity extends AppCompatActivity {

    private HorizontalCalendarView hcv;
    private TextView backTV;
    private FloatingActionButton cancelFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        initViews();
        initListerners();

        Calendar start = Calendar.getInstance();
        start.add(Calendar.MONTH, -6);
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MONTH, 6);

        ArrayList<String> datesToBeColoured = new ArrayList<>();
        datesToBeColoured.add(Utils.getFormattedDateToday());

        hcv.setUpCalendar(start.getTimeInMillis(), end.getTimeInMillis(),
                datesToBeColoured, date -> {
//                    Toast.makeText(AddIncomeActivity.this, "Date: " + date, Toast.LENGTH_SHORT).show();
                    Log.d("tapped date", date);
                    /*datesToBeColoured.remove(0);
                    datesToBeColoured.add(date);*/
                }, R.drawable.color_status_2);
    }

    private void initViews() {
        hcv = findViewById(R.id.hcv_income);
        backTV = findViewById(R.id.tv_back_income);
        cancelFAB = findViewById(R.id.fab_cancel_income);
    }

    private void initListerners() {
        backTV.setOnClickListener(view -> super.onBackPressed());
        cancelFAB.setOnClickListener(view -> super.onBackPressed());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
