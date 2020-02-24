package com.ssindher11.expangger.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;
import com.ssindher11.expangger.R;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    private TextView backTV;
    private PieChart totalPC;
    private BarChart monthlyBC;
    private RadarChart monthlyRC;
    private LineChart monthlyLC;
    private ToggleSwitch toggleMonthly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        initViews();
        initListeners();
        setupTotalPC();
        setupMonthlyBC();
        setupMonthlyRC();
        setupMonthlyLC();
        animateCharts();
    }

    private void initViews() {
        backTV = findViewById(R.id.tv_back_stats);
        totalPC = findViewById(R.id.pc_total_stats);
        monthlyBC = findViewById(R.id.bc_monthly_stats);
        monthlyRC = findViewById(R.id.rc_monthly_stats);
        monthlyLC = findViewById(R.id.lc_monthly_stats);
        toggleMonthly = findViewById(R.id.ts_monthly_stats);
        toggleMonthly.setCheckedPosition(0);
    }

    private void initListeners() {
        backTV.setOnClickListener(view -> super.onBackPressed());

        toggleMonthly.setOnChangeListener(pos -> {
            switch (pos) {
                case 0:
                    animateCharts();
                    monthlyBC.setVisibility(View.VISIBLE);
                    monthlyRC.setVisibility(View.GONE);
                    monthlyLC.setVisibility(View.GONE);
                    break;
                case 1:
                    monthlyBC.setVisibility(View.GONE);
                    monthlyRC.setVisibility(View.VISIBLE);
                    monthlyLC.setVisibility(View.GONE);
                    break;
                case 2:
                    animateCharts();
                    monthlyBC.setVisibility(View.GONE);
                    monthlyRC.setVisibility(View.GONE);
                    monthlyLC.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    private void setupTotalPC() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(15000.0f, "Income"));
        entries.add(new PieEntry(17500.0f, "Expense"));
        PieDataSet set = new PieDataSet(entries, "");
        PieData data = new PieData(set);
        totalPC.setData(data);
        totalPC.setCenterText("TOTAL");
        totalPC.setCenterTextSize(20);
        int[] col = {Color.parseColor("#38D39F"), Color.parseColor("#FF4757")};
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : col) colors.add(c);
        set.setColors(colors);
        totalPC.invalidate();

        Legend legend = totalPC.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setTextSize(16);
        totalPC.getDescription().setEnabled(false);
    }

    private void setupMonthlyBC() {
//        double[] in = new double[5];
        double[] in = {2500, 4000, 3500, 1250, 3750, 2500, 6000};
//        double[] ex = new double[5];
        double[] ex = {4000, 3500, 1000, 2000, 3000, 1500, 4500};
        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};

        /*User u = new User();
        HashMap<String, Income> incomes = u.getIncomes();
        HashMap<String, Expense> expenses = u.getExpenses();

        for (Income i : incomes.values()) {
            int mon = Utils.getMonthFromString(i.getDate());
            in[mon] += i.getAmount();
        }

        for (Expense e : expenses.values()) {
            int mon = Utils.getMonthFromString(e.getDate());
            ex[mon] += e.getAmount();
        }*/

//        Double[] in = new Double[12];
//        Double[] ex = new Double[12];
//        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        ArrayList<BarEntry> ins = new ArrayList<>();
        ArrayList<BarEntry> exs = new ArrayList<>();
        for (int i = 1; i <= in.length; i++) {
            ins.add(new BarEntry(i, (float) in[i - 1]));
            exs.add(new BarEntry(i, (float) ex[i - 1]));
        }

        BarDataSet set1 = new BarDataSet(ins, "Income");
        BarDataSet set2 = new BarDataSet(exs, "Expense");

        float groupSpace = 0.2f;
        float barSpace = 0.02f;
        float barWidth = 0.38f;

        // gs = 1 - ((bw + bs) * n)

        BarData data = new BarData(set1, set2);
        data.setBarWidth(barWidth);
        monthlyBC.setData(data);
        monthlyBC.groupBars(0, groupSpace, barSpace);
        monthlyBC.invalidate();
        monthlyBC.getDescription().setEnabled(false);
        monthlyBC.setDrawBorders(true);
        monthlyBC.setDragEnabled(true);
        monthlyBC.setVisibleXRangeMaximum(5);

        XAxis xAxis = monthlyBC.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(7);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(monthlyBC.getBarData().getGroupWidth(groupSpace, barSpace) * 7);

        YAxis yAxis = monthlyLC.getAxisRight();
        yAxis.setEnabled(false);

        Legend legend = monthlyBC.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setTextSize(16);

        int[] col = {Color.parseColor("#38D39F"), Color.parseColor("#FF4757")};
        set1.setColor(col[0]);
        set2.setColor(col[1]);
    }

    private void setupMonthlyLC() {
        List<Entry> incomes = new ArrayList<>();
        List<Entry> expenses = new ArrayList<>();

        double[] in = {2500, 4000, 3500, 1250, 3750, 2500, 6000};
        double[] ex = {4000, 3500, 1000, 2000, 3000, 1500, 4500};
        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};

        for (int i = 0; i < in.length; i++) {
            incomes.add(new Entry(i, (float) in[i]));
            expenses.add(new Entry(i, (float) ex[i]));
        }

        LineDataSet inSet = new LineDataSet(incomes, "Income");
        inSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet exSet = new LineDataSet(expenses, "Expense");
        exSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        LineData lineData = new LineData(inSet, exSet);
        monthlyLC.setData(lineData);
        monthlyLC.getDescription().setEnabled(false);
        monthlyLC.setDrawBorders(true);
        monthlyLC.invalidate();

        XAxis xAxis = monthlyLC.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxis = monthlyLC.getAxisRight();
        yAxis.setEnabled(false);

        Legend legend = monthlyLC.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(16);

        int[] col = {Color.parseColor("#38D39F"), Color.parseColor("#FF4757")};
        inSet.setColor(col[0]);
        inSet.setCircleColor(col[0]);
        exSet.setColor(col[1]);
        exSet.setCircleColor(col[1]);
    }

    private void setupMonthlyRC() {
        List<RadarEntry> incomes = new ArrayList<>();
        List<RadarEntry> expenses = new ArrayList<>();

        double[] in = {2500, 4000, 3500, 1250, 3750, 2500, 6000};
        double[] ex = {4000, 3500, 1000, 2000, 3000, 1500, 4500};
        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};

        for (int i = 0; i < in.length; i++) {
            incomes.add(new RadarEntry((float) in[i]));
            expenses.add(new RadarEntry((float) ex[i]));
        }

        RadarDataSet inSet = new RadarDataSet(incomes, "Income");
        RadarDataSet exSet = new RadarDataSet(expenses, "Expense");

        RadarData data = new RadarData(inSet, exSet);
        monthlyRC.setData(data);
        monthlyRC.getDescription().setEnabled(false);
        monthlyRC.invalidate();

        XAxis xAxis = monthlyRC.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        Legend legend = monthlyRC.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setTextSize(16);

        int[] col = {Color.parseColor("#38D39F"), Color.parseColor("#FF4757")};
        inSet.setColor(col[0]);
        inSet.setDrawFilled(true);
        inSet.setFillColor(col[0]);
        inSet.setFillAlpha(40);
        exSet.setColor(col[1]);
        exSet.setDrawFilled(true);
        exSet.setFillColor(col[1]);
        exSet.setFillAlpha(40);
    }

    private void animateCharts() {
        monthlyBC.animateY(500, Easing.EaseInOutCubic);

        monthlyRC.animateX(1000);

        monthlyLC.animateXY(750, 500, Easing.EaseInCubic, Easing.EaseInQuad);
    }
}
