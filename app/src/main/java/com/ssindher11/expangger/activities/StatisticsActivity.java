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
import com.ssindher11.expangger.Utils;
import com.ssindher11.expangger.models.Expense;
import com.ssindher11.expangger.models.ExpenseList;
import com.ssindher11.expangger.models.Income;
import com.ssindher11.expangger.models.IncomeList;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    private TextView backTV;
    private PieChart totalPC, incomeCatPC, expenseCatPC, expenseModePC;
    private BarChart monthlyBC, incomeCatBC, expenseCatBC, expenseModeBC;
    private RadarChart monthlyRC;
    private LineChart monthlyLC;
    private ToggleSwitch toggleMonthly, toggleIncomeCat, toggleExpenseCat, toggleExpenseMode;

    private List<Expense> expenseList = new ArrayList<>();
    private List<Income> incomeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        initViews();
        initListeners();

        ExpenseList eList = getIntent().getParcelableExtra("expenses");
        if (eList != null) {
            expenseList = eList.getExpenses();
        }
        IncomeList iList = getIntent().getParcelableExtra("incomes");
        if (iList != null) {
            incomeList = iList.getIncomes();
        }

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
        entries.add(new PieEntry(getTotalIncome(incomeList), "Income"));
        entries.add(new PieEntry(getTotalExpense(expenseList), "Expense"));
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
        double[] in = new double[7];
        double[] ex = new double[7];
        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};

        for (Income i : incomeList) {
            int m = Utils.getMonthFromString(i.getDate());
            in[m] += i.getAmount();
        }

        for (Expense e : expenseList) {
            int m = Utils.getMonthFromString(e.getDate());
            ex[m] += e.getAmount();
        }

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

        YAxis yAxis = monthlyBC.getAxisRight();
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

        double[] in = new double[7];
        double[] ex = new double[7];
        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};

        for (Income i : incomeList) {
            int m = Utils.getMonthFromString(i.getDate());
            in[m] += i.getAmount();
        }

        for (Expense e : expenseList) {
            int m = Utils.getMonthFromString(e.getDate());
            ex[m] += e.getAmount();
        }

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

        double[] in = new double[7];
        double[] ex = new double[7];
        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};

        for (Income i : incomeList) {
            int m = Utils.getMonthFromString(i.getDate());
            in[m] += i.getAmount();
        }

        for (Expense e : expenseList) {
            int m = Utils.getMonthFromString(e.getDate());
            ex[m] += e.getAmount();
        }

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
        double[] vals = new double[4];
        for (Income i : incomeList) {
            switch (i.getType()) {
                case "Salary":
                    vals[0] += i.getAmount();
                    break;
                case "Reward":
                    vals[1] += i.getAmount();
                    break;
                case "Cashback":
                    vals[2] += i.getAmount();
                    break;
                case "Miscellaneous":
                    vals[3] += i.getAmount();
                    break;
            }
        }
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0.5f, (float) vals[0]));
        entries.add(new BarEntry(1.5f, (float) vals[1]));
        entries.add(new BarEntry(2.5f, (float) vals[2]));
        entries.add(new BarEntry(3.5f, (float) vals[3]));

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
        double[] vals = new double[4];
        for (Income i : incomeList) {
            switch (i.getType()) {
                case "Salary":
                    vals[0] += i.getAmount();
                    break;
                case "Reward":
                    vals[1] += i.getAmount();
                    break;
                case "Cashback":
                    vals[2] += i.getAmount();
                    break;
                case "Miscellaneous":
                    vals[3] += i.getAmount();
                    break;
            }
        }

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) vals[0], "Salary"));
        entries.add(new PieEntry((float) vals[1], "Rewards"));
        entries.add(new PieEntry((float) vals[2], "Cashback"));
        entries.add(new PieEntry((float) vals[3], "Misc."));

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
        double[] vals = new double[5];
        for (Expense e : expenseList) {
            switch (e.getType()) {
                case "Bill":
                    vals[0] += e.getAmount();
                    break;
                case "Food":
                    vals[1] += e.getAmount();
                    break;
                case "Shopping":
                    vals[2] += e.getAmount();
                    break;
                case "Entertainment":
                    vals[3] += e.getAmount();
                    break;
                case "Miscellaneous":
                    vals[4] += e.getAmount();
                    break;
            }
        }

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0.5f, (float) vals[0]));
        entries.add(new BarEntry(1.5f, (float) vals[1]));
        entries.add(new BarEntry(2.5f, (float) vals[2]));
        entries.add(new BarEntry(3.5f, (float) vals[3]));
        entries.add(new BarEntry(4.5f, (float) vals[4]));

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
        double[] vals = new double[5];
        for (Expense e : expenseList) {
            switch (e.getType()) {
                case "Bill":
                    vals[0] += e.getAmount();
                    break;
                case "Food":
                    vals[1] += e.getAmount();
                    break;
                case "Shopping":
                    vals[2] += e.getAmount();
                    break;
                case "Entertainment":
                    vals[3] += e.getAmount();
                    break;
                case "Miscellaneous":
                    vals[4] += e.getAmount();
                    break;
            }
        }

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) vals[0], "Bill"));
        entries.add(new PieEntry((float) vals[1], "Food"));
        entries.add(new PieEntry((float) vals[2], "Shopping"));
        entries.add(new PieEntry((float) vals[3], "Entertainment"));
        entries.add(new PieEntry((float) vals[4], "Misc."));

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
        double[] vals = new double[4];
        for (Expense e : expenseList) {
            switch (e.getPayment_mode()) {
                case "Card":
                    vals[0] += e.getAmount();
                    break;
                case "Cash":
                    vals[1] += e.getAmount();
                    break;
                case "UPI":
                    vals[2] += e.getAmount();
                    break;
                case "Wallet":
                    vals[3] += e.getAmount();
                    break;
            }
        }

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0.5f, (float) vals[0]));
        entries.add(new BarEntry(1.5f, (float) vals[1]));
        entries.add(new BarEntry(2.5f, (float) vals[2]));
        entries.add(new BarEntry(3.5f, (float) vals[3]));

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
        double[] vals = new double[4];
        for (Expense e : expenseList) {
            switch (e.getPayment_mode()) {
                case "Card":
                    vals[0] += e.getAmount();
                    break;
                case "Cash":
                    vals[1] += e.getAmount();
                    break;
                case "UPI":
                    vals[2] += e.getAmount();
                    break;
                case "Wallet":
                    vals[3] += e.getAmount();
                    break;
            }
        }

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) vals[0], "Card"));
        entries.add(new PieEntry((float) vals[1], "Cash"));
        entries.add(new PieEntry((float) vals[2], "UPI"));
        entries.add(new PieEntry((float) vals[3], "Wallet"));

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

    private float getTotalIncome(List<Income> i) {
        float total = 0;
        for (Income income : i)
            total += income.getAmount();
        return total;
    }

    private float getTotalExpense(List<Expense> e) {
        float total = 0;
        for (Expense expense : e)
            total += expense.getAmount();
        return total;
    }
}
