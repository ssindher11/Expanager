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
    private PieChart totalPC, incomeCatPC, expenseCatPC, expenseModePC;
    private BarChart monthlyBC, incomeCatBC, expenseCatBC, expenseModeBC;
    private RadarChart monthlyRC;
    private LineChart monthlyLC;
    private ToggleSwitch toggleMonthly, toggleIncomeCat, toggleExpenseCat, toggleExpenseMode;

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
        setupIncomeCatBC();
        setupIncomeCatPC();
        setupExpenseCatBC();
        setupExpenseCatPC();
        setupExpenseModeBC();
        setupExpenseModePC();
        animateTotalChart();
        animateMonthlyCharts();
        animateIncomeCatCharts();
        animateExpenseCatCharts();
    }

    private void initViews() {
        backTV = findViewById(R.id.tv_back_stats);
        totalPC = findViewById(R.id.pc_total_stats);
        monthlyBC = findViewById(R.id.bc_monthly_stats);
        monthlyRC = findViewById(R.id.rc_monthly_stats);
        monthlyLC = findViewById(R.id.lc_monthly_stats);
        incomeCatPC = findViewById(R.id.pc_income_stats);
        incomeCatBC = findViewById(R.id.bc_income_stats);
        expenseCatPC = findViewById(R.id.pc_expense_cat_stats);
        expenseCatBC = findViewById(R.id.bc_expense_cat_stats);
        expenseModeBC = findViewById(R.id.bc_expense_mode_stats);
        expenseModePC = findViewById(R.id.pc_expense_mode_stats);
        toggleMonthly = findViewById(R.id.ts_monthly_stats);
        toggleMonthly.setCheckedPosition(0);
        toggleIncomeCat = findViewById(R.id.ts_income_stats);
        toggleIncomeCat.setCheckedPosition(0);
        toggleExpenseCat = findViewById(R.id.ts_expense_cat_stats);
        toggleExpenseCat.setCheckedPosition(0);
        toggleExpenseMode = findViewById(R.id.ts_expense_mode_stats);
        toggleExpenseMode.setCheckedPosition(0);
    }

    private void initListeners() {
        backTV.setOnClickListener(view -> super.onBackPressed());

        toggleMonthly.setOnChangeListener(pos -> {
            animateMonthlyCharts();
            switch (pos) {
                case 0:
                    monthlyBC.animateY(500, Easing.EaseInOutCubic);
                    monthlyBC.setVisibility(View.VISIBLE);
                    monthlyRC.setVisibility(View.INVISIBLE);
                    monthlyLC.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    monthlyRC.animateX(500);
                    monthlyBC.setVisibility(View.INVISIBLE);
                    monthlyRC.setVisibility(View.VISIBLE);
                    monthlyLC.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    monthlyLC.animateXY(750, 500, Easing.EaseInCubic, Easing.EaseInQuad);
                    monthlyBC.setVisibility(View.INVISIBLE);
                    monthlyRC.setVisibility(View.INVISIBLE);
                    monthlyLC.setVisibility(View.VISIBLE);
                    break;
            }
        });

        toggleIncomeCat.setOnChangeListener(pos -> {
            animateIncomeCatCharts();
            switch (pos) {
                case 0:
                    incomeCatBC.animateY(500, Easing.EaseInOutCubic);
                    incomeCatBC.setVisibility(View.VISIBLE);
                    incomeCatPC.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    incomeCatPC.animateX(500, Easing.EaseInOutCubic);
                    incomeCatBC.setVisibility(View.INVISIBLE);
                    incomeCatPC.setVisibility(View.VISIBLE);
                    break;
            }
        });

        toggleExpenseCat.setOnChangeListener(pos -> {
            switch (pos) {
                case 0:
                    expenseCatBC.animateY(500, Easing.EaseInOutCubic);
                    expenseCatBC.setVisibility(View.VISIBLE);
                    expenseCatPC.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    expenseCatPC.animateX(500, Easing.EaseInOutCubic);
                    expenseCatBC.setVisibility(View.INVISIBLE);
                    expenseCatPC.setVisibility(View.VISIBLE);
                    break;
            }
        });

        toggleExpenseMode.setOnChangeListener(pos -> {
            switch (pos) {
                case 0:
                    expenseModeBC.animateY(500, Easing.EaseInOutCubic);
                    expenseModeBC.setVisibility(View.VISIBLE);
                    expenseModePC.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    expenseModePC.animateX(500, Easing.EaseInOutCubic);
                    expenseModeBC.setVisibility(View.INVISIBLE);
                    expenseModePC.setVisibility(View.VISIBLE);
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
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(12);
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

        inSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        exSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        inSet.setLineWidth(2);
        exSet.setLineWidth(2);
        inSet.setCircleRadius(0.1f);
        exSet.setCircleRadius(0.1f);
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

    private void setupIncomeCatBC() {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0.5f, 13800));
        entries.add(new BarEntry(1.5f, 5500));
        entries.add(new BarEntry(2.5f, 2900));
        entries.add(new BarEntry(3.5f, 7200));

        String[] labels = {"Salary", "Rewards", "Cashback", "Misc."};
        BarDataSet dataSet = new BarDataSet(entries, "");

        BarData data = new BarData(dataSet);
        data.setValueTextSize(12);

        incomeCatBC.setData(data);
        incomeCatBC.getDescription().setEnabled(false);
        incomeCatBC.invalidate();
        incomeCatBC.setDrawBorders(true);

        XAxis xAxis = incomeCatBC.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelCount(4);

        YAxis yAxis = incomeCatBC.getAxisRight();
        yAxis.setEnabled(false);

        Legend legend = incomeCatBC.getLegend();
        legend.setEnabled(false);

        int[] col = {Color.parseColor("#22A24A"),
                Color.parseColor("#F31A3F"),
                Color.parseColor("#FA961C"),
                Color.parseColor("#7A5BF8")};

        dataSet.setColors(col);
    }

    private void setupIncomeCatPC() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(13800, "Salary"));
        entries.add(new PieEntry(5500, "Rewards"));
        entries.add(new PieEntry(2900, "Cashback"));
        entries.add(new PieEntry(7200, "Misc."));

        PieDataSet dataSet = new PieDataSet(entries, "");
        PieData data = new PieData(dataSet);
        dataSet.setValueTextColor(Color.WHITE);
        data.setValueTextSize(12);

        dataSet.setValueLinePart1OffsetPercentage(80f);
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.1f);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        incomeCatPC.setData(data);
        incomeCatPC.getDescription().setEnabled(false);
        incomeCatPC.setHoleRadius(60);
        incomeCatPC.setEntryLabelColor(Color.BLACK);
        incomeCatPC.invalidate();

        int[] col = {Color.parseColor("#22A24A"),
                Color.parseColor("#F31A3F"),
                Color.parseColor("#FA961C"),
                Color.parseColor("#7A5BF8")};
        dataSet.setColors(col);

        Legend legend = incomeCatPC.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setTextSize(16);
    }

    private void setupExpenseCatBC() {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0.5f, 4600));
        entries.add(new BarEntry(1.5f, 5000));
        entries.add(new BarEntry(2.5f, 4400));
        entries.add(new BarEntry(3.5f, 3200));
        entries.add(new BarEntry(4.5f, 6800));

        String[] labels = {"Bill", "Food", "Shopping", "Entertainment", "Misc."};
        BarDataSet dataSet = new BarDataSet(entries, "");

        BarData data = new BarData(dataSet);
        data.setValueTextSize(12);

        expenseCatBC.setData(data);
        expenseCatBC.getDescription().setEnabled(false);
        expenseCatBC.invalidate();
        expenseCatBC.setDrawBorders(true);

        XAxis xAxis = expenseCatBC.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelCount(5);

        YAxis yAxis = expenseCatBC.getAxisRight();
        yAxis.setEnabled(false);

        Legend legend = expenseCatBC.getLegend();
        legend.setEnabled(false);

        int[] col = {getColor(R.color.red),
                getColor(R.color.green),
                getColor(R.color.orange),
                getColor(R.color.lightBlue),
                getColor(R.color.violet)};

        dataSet.setColors(col);
    }

    private void setupExpenseCatPC() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(4600, "Bill"));
        entries.add(new PieEntry(5000, "Food"));
        entries.add(new PieEntry(4400, "Shopping"));
        entries.add(new PieEntry(3200, "Entertainment"));
        entries.add(new PieEntry(6800, "Misc."));

        PieDataSet dataSet = new PieDataSet(entries, "");
        PieData data = new PieData(dataSet);
        dataSet.setValueTextColor(Color.WHITE);
        data.setValueTextSize(12);

        dataSet.setValueLinePart1OffsetPercentage(80f);
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.1f);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        expenseCatPC.setData(data);
        expenseCatPC.getDescription().setEnabled(false);
        expenseCatPC.setHoleRadius(65);
        expenseCatPC.setEntryLabelColor(Color.BLACK);
        expenseCatPC.invalidate();

        int[] col = {getColor(R.color.red),
                getColor(R.color.green),
                getColor(R.color.orange),
                getColor(R.color.lightBlue),
                getColor(R.color.violet)};
        dataSet.setColors(col);

        Legend legend = expenseCatPC.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setTextSize(16);
    }

    private void setupExpenseModeBC() {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0.5f, 7500));
        entries.add(new BarEntry(1.5f, 4900));
        entries.add(new BarEntry(2.5f, 9500));
        entries.add(new BarEntry(3.5f, 3500));

        String[] labels = {"Card", "Cash", "UPI", "Wallet"};
        BarDataSet dataSet = new BarDataSet(entries, "");

        BarData data = new BarData(dataSet);
        data.setValueTextSize(12);

        expenseModeBC.setData(data);
        expenseModeBC.getDescription().setEnabled(false);
        expenseModeBC.invalidate();
        expenseModeBC.setDrawBorders(true);

        XAxis xAxis = expenseModeBC.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelCount(4);

        YAxis yAxis = expenseModeBC.getAxisRight();
        yAxis.setEnabled(false);

        Legend legend = expenseModeBC.getLegend();
        legend.setEnabled(false);

        int[] col = {Color.parseColor("#0082CA"),
                Color.parseColor("#12E7A5"),
                Color.parseColor("#6E7BF2"),
                Color.parseColor("#FA961C")};
        dataSet.setColors(col);
    }

    private void setupExpenseModePC() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(7500, "Card"));
        entries.add(new PieEntry(4900, "Cash"));
        entries.add(new PieEntry(9500, "UPI"));
        entries.add(new PieEntry(3500, "Wallet."));

        PieDataSet dataSet = new PieDataSet(entries, "");
        PieData data = new PieData(dataSet);
        dataSet.setValueTextColor(Color.WHITE);
        data.setValueTextSize(12);

        dataSet.setValueLinePart1OffsetPercentage(80f);
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.1f);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        expenseModePC.setData(data);
        expenseModePC.getDescription().setEnabled(false);
        expenseModePC.setHoleRadius(60);
        expenseModePC.setEntryLabelColor(Color.BLACK);
        expenseModePC.invalidate();

        int[] col = {Color.parseColor("#0082CA"),
                Color.parseColor("#12E7A5"),
                Color.parseColor("#6E7BF2"),
                Color.parseColor("#FA961C")};
        dataSet.setColors(col);

        Legend legend = expenseModePC.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setTextSize(16);
    }

    private void animateTotalChart() {
        totalPC.animateX(500, Easing.EaseInOutCubic);
    }

    private void animateMonthlyCharts() {
        monthlyBC.animateY(500, Easing.EaseInOutCubic);
        monthlyRC.animateX(500);
        monthlyLC.animateXY(750, 500, Easing.EaseInCubic, Easing.EaseInQuad);
    }

    private void animateIncomeCatCharts() {
        incomeCatBC.animateY(500, Easing.EaseInOutCubic);
        incomeCatPC.animateX(500, Easing.EaseInOutCubic);
    }

    private void animateExpenseCatCharts() {
        expenseCatBC.animateY(500, Easing.EaseInOutCubic);
        expenseCatPC.animateX(500, Easing.EaseInOutCubic);
    }
}
