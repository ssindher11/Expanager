package com.ssindher11.expangger.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
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
    private BarChart monthlyBC, incomeCatBC, expenseCatBC, expenseModeBC, combinedIncBC, combinedExpBC;
    private RadarChart monthlyRC, combinedIncRC, combinedExpRC;
    private LineChart monthlyLC;
    private BubbleChart combinedIncBBC;
    private ToggleSwitch toggleMonthly, toggleIncomeCat, toggleExpenseCat, toggleExpenseMode, toggleCombinedInc, toggleCombinedExp;

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
        setupCombinedIncomeBC();
        setupCombinedIncomeRC();
        setupCombinedExpenseBC();
        setupCombinedExpenseRC();
        setupCombinedIncomeBBC();
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
        combinedIncBC = findViewById(R.id.bc_combined_inc_stats);
        combinedIncRC = findViewById(R.id.rc_combined_inc_stats);
        combinedIncBBC = findViewById(R.id.bbc_combined_inc_stats);
        combinedExpBC = findViewById(R.id.bc_combined_exp_stats);
        combinedExpRC = findViewById(R.id.rc_combined_exp_stats);
        toggleMonthly = findViewById(R.id.ts_monthly_stats);
        toggleMonthly.setCheckedPosition(0);
        toggleIncomeCat = findViewById(R.id.ts_income_stats);
        toggleIncomeCat.setCheckedPosition(0);
        toggleExpenseCat = findViewById(R.id.ts_expense_cat_stats);
        toggleExpenseCat.setCheckedPosition(0);
        toggleExpenseMode = findViewById(R.id.ts_expense_mode_stats);
        toggleExpenseMode.setCheckedPosition(0);
        toggleCombinedInc = findViewById(R.id.ts_combined_inc_stats);
        toggleCombinedInc.setCheckedPosition(0);
        toggleCombinedExp = findViewById(R.id.ts_combined_exp_stats);
        toggleCombinedExp.setCheckedPosition(0);
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

        toggleCombinedInc.setOnChangeListener(pos -> {
            switch (pos) {
                case 0:
                    combinedIncBC.animateY(500, Easing.EaseInOutCubic);
                    combinedIncBC.setVisibility(View.VISIBLE);
                    combinedIncBBC.setVisibility(View.INVISIBLE);
                    combinedIncRC.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    combinedIncBBC.animateXY(500, 500, Easing.EaseInOutCubic, Easing.EaseInOutCubic);
                    combinedIncBC.setVisibility(View.INVISIBLE);
                    combinedIncBBC.setVisibility(View.VISIBLE);
                    combinedIncRC.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    combinedIncRC.animateX(500);
                    combinedIncBC.setVisibility(View.INVISIBLE);
                    combinedIncBBC.setVisibility(View.INVISIBLE);
                    combinedIncRC.setVisibility(View.VISIBLE);
                    break;
            }
        });

        toggleCombinedExp.setOnChangeListener(pos -> {
            switch (pos) {
                case 0:
                    combinedExpBC.animateY(500, Easing.EaseInOutCubic);
                    combinedExpBC.setVisibility(View.VISIBLE);
                    combinedExpRC.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    combinedExpRC.animateX(500);
                    combinedExpBC.setVisibility(View.INVISIBLE);
                    combinedExpRC.setVisibility(View.VISIBLE);
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
        float[] in = new float[7];
        float[] ex = new float[7];
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
            ins.add(new BarEntry(i, in[i - 1]));
            exs.add(new BarEntry(i, ex[i - 1]));
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

        float[] in = new float[7];
        float[] ex = new float[7];
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
            incomes.add(new Entry(i, in[i]));
            expenses.add(new Entry(i, ex[i]));
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

        float[] in = new float[7];
        float[] ex = new float[7];
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
            incomes.add(new RadarEntry(in[i]));
            expenses.add(new RadarEntry(ex[i]));
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
        float[] vals = new float[4];
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
        entries.add(new BarEntry(0.5f, vals[0]));
        entries.add(new BarEntry(1.5f, vals[1]));
        entries.add(new BarEntry(2.5f, vals[2]));
        entries.add(new BarEntry(3.5f, vals[3]));

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
        float[] vals = new float[4];
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
        entries.add(new PieEntry(vals[0], "Salary"));
        entries.add(new PieEntry(vals[1], "Rewards"));
        entries.add(new PieEntry(vals[2], "Cashback"));
        entries.add(new PieEntry(vals[3], "Misc."));

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
        float[] vals = new float[5];
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
        entries.add(new BarEntry(0.5f, vals[0]));
        entries.add(new BarEntry(1.5f, vals[1]));
        entries.add(new BarEntry(2.5f, vals[2]));
        entries.add(new BarEntry(3.5f, vals[3]));
        entries.add(new BarEntry(4.5f, vals[4]));

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
        float[] vals = new float[5];
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
        entries.add(new PieEntry(vals[0], "Bill"));
        entries.add(new PieEntry(vals[1], "Food"));
        entries.add(new PieEntry(vals[2], "Shopping"));
        entries.add(new PieEntry(vals[3], "Entertainment"));
        entries.add(new PieEntry(vals[4], "Misc."));

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
        float[] vals = new float[4];
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
        entries.add(new BarEntry(0.5f, vals[0]));
        entries.add(new BarEntry(1.5f, vals[1]));
        entries.add(new BarEntry(2.5f, vals[2]));
        entries.add(new BarEntry(3.5f, vals[3]));

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
        float[] vals = new float[4];
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
        entries.add(new PieEntry(vals[0], "Card"));
        entries.add(new PieEntry(vals[1], "Cash"));
        entries.add(new PieEntry(vals[2], "UPI"));
        entries.add(new PieEntry(vals[3], "Wallet"));

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

    private void setupCombinedIncomeBC() {
        float[][] vals = new float[7][4];
        for (Income i : incomeList) {
            int m = Utils.getMonthFromString(i.getDate());
            switch (i.getType()) {
                case "Salary":
                    vals[m][0] += i.getAmount();
                    break;
                case "Reward":
                    vals[m][1] += i.getAmount();
                    break;
                case "Cashback":
                    vals[m][2] += i.getAmount();
                    break;
                case "Miscellaneous":
                    vals[m][3] += i.getAmount();
                    break;
            }
        }

        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};
        String[] stackLabels = {"Salary", "Rewards", "Cashback", "Misc."};

        List<BarEntry> entries = new ArrayList<>();
/*        entries.add(new BarEntry(0 + 0.5f, new float[]{500, 200, 800, 400}));
        entries.add(new BarEntry(1 + 0.5f, new float[]{200, 100, 300, 900}));
        entries.add(new BarEntry(2 + 0.5f, new float[]{100, 300, 500, 200}));
        entries.add(new BarEntry(3 + 0.5f, new float[]{400, 510, 700, 150}));
        entries.add(new BarEntry(4 + 0.5f, new float[]{700, 100, 900, 120}));
        entries.add(new BarEntry(5 + 0.5f, new float[]{50, 50, 500, 200}));
        entries.add(new BarEntry(6 + 0.5f, new float[]{450, 200, 100, 100}));*/

        for (int i = 0; i < labels.length; i++)
            entries.add(new BarEntry(i + 0.5f, vals[i]));

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setStackLabels(stackLabels);

        BarData data = new BarData(dataSet);
        combinedIncBC.setData(data);
        combinedIncBC.invalidate();
        combinedIncBC.getDescription().setEnabled(false);
        combinedIncBC.setDrawBorders(true);
        combinedIncBC.setDragEnabled(true);
        combinedIncBC.setVisibleXRangeMaximum(5);

        XAxis xAxis = combinedIncBC.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(7);
        xAxis.setAxisMinimum(0);

        YAxis yAxis = combinedIncBC.getAxisRight();
        yAxis.setEnabled(false);

        Legend legend = combinedIncBC.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setTextSize(16);

        int[] col = {Color.parseColor("#22A24A"),
                Color.parseColor("#F31A3F"),
                Color.parseColor("#FA961C"),
                Color.parseColor("#7A5BF8")};
        dataSet.setColors(col);
    }

    private void setupCombinedIncomeBBC() {
        float[][] vals = new float[7][4];
        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};
        String[] cat = {"Salary", "Rewards", "Cashback", "Misc."};
        int[] col = {Color.parseColor("#22A24A"),
                Color.parseColor("#F31A3F"),
                Color.parseColor("#FA961C"),
                Color.parseColor("#7A5BF8")};

        /*new float[]{500, 200, 800, 400}));
          new float[]{200, 100, 300, 900}));
          new float[]{100, 300, 500, 200}));
          new float[]{400, 510, 700, 150}));
          new float[]{700, 100, 900, 120}));
          new float[]{ 50,  50, 500, 200}));
          new float[]{450, 200, 100, 100}));*/

        List<BubbleEntry> entries = new ArrayList<>();
        entries.add(new BubbleEntry(0 + 0.5f, 2, 500));
        entries.add(new BubbleEntry(0 + 0.5f, 4, 200));
        entries.add(new BubbleEntry(0 + 0.5f, 6, 800));
        entries.add(new BubbleEntry(0 + 0.5f, 8, 400));
        entries.add(new BubbleEntry(1 + 0.5f, 2, 200));
        entries.add(new BubbleEntry(1 + 0.5f, 4, 100));
        entries.add(new BubbleEntry(1 + 0.5f, 6, 300));
        entries.add(new BubbleEntry(1 + 0.5f, 8, 900));
        entries.add(new BubbleEntry(2 + 0.5f, 2, 100));
        entries.add(new BubbleEntry(2 + 0.5f, 4, 300));
        entries.add(new BubbleEntry(2 + 0.5f, 6, 500));
        entries.add(new BubbleEntry(2 + 0.5f, 8, 200));
        entries.add(new BubbleEntry(3 + 0.5f, 2, 400));
        entries.add(new BubbleEntry(3 + 0.5f, 4, 510));
        entries.add(new BubbleEntry(3 + 0.5f, 6, 700));
        entries.add(new BubbleEntry(3 + 0.5f, 8, 150));
        entries.add(new BubbleEntry(4 + 0.5f, 2, 700));
        entries.add(new BubbleEntry(4 + 0.5f, 4, 100));
        entries.add(new BubbleEntry(4 + 0.5f, 6, 900));
        entries.add(new BubbleEntry(4 + 0.5f, 8, 120));
        entries.add(new BubbleEntry(5 + 0.5f, 2, 50));
        entries.add(new BubbleEntry(5 + 0.5f, 4, 50));
        entries.add(new BubbleEntry(5 + 0.5f, 6, 500));
        entries.add(new BubbleEntry(5 + 0.5f, 8, 200));
        entries.add(new BubbleEntry(6 + 0.5f, 2, 450));
        entries.add(new BubbleEntry(6 + 0.5f, 4, 200));
        entries.add(new BubbleEntry(6 + 0.5f, 6, 100));
        entries.add(new BubbleEntry(6 + 0.5f, 8, 100));

        BubbleDataSet dataSet = new BubbleDataSet(entries, "");
        dataSet.setColors(col, 150);

        BubbleData data = new BubbleData(dataSet);
        combinedIncBBC.setData(data);
        combinedIncBBC.invalidate();
        combinedIncBBC.getDescription().setEnabled(false);
        combinedIncBBC.setDrawBorders(true);
        combinedIncBBC.setDragEnabled(true);
        combinedIncBBC.setVisibleXRangeMaximum(5);

        XAxis xAxis = combinedIncBBC.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(7);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(combinedIncBBC.getData().getXMax() + 0.5f);

        YAxis yAxis = combinedIncBBC.getAxisRight();
        yAxis.setValueFormatter(new IndexAxisValueFormatter(cat));
        yAxis.setEnabled(false);

        Legend legend = combinedIncBBC.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setTextSize(16);
    }

    private void setupCombinedIncomeRC() {
        float[][] vals = new float[4][7];
        for (Income i : incomeList) {
            int m = Utils.getMonthFromString(i.getDate());
            switch (i.getType()) {
                case "Salary":
                    vals[0][m] += i.getAmount();
                    break;
                case "Reward":
                    vals[1][m] += i.getAmount();
                    break;
                case "Cashback":
                    vals[2][m] += i.getAmount();
                    break;
                case "Miscellaneous":
                    vals[3][m] += i.getAmount();
                    break;
            }
        }

        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};
        String[] stackLabels = {"Salary", "Rewards", "Cashback", "Misc."};
        int[] col = {Color.parseColor("#22A24A"),
                Color.parseColor("#F31A3F"),
                Color.parseColor("#FA961C"),
                Color.parseColor("#7A5BF8")};

        RadarData data = new RadarData();
        RadarDataSet[] dataSets = new RadarDataSet[stackLabels.length];
        for (int i = 0; i < stackLabels.length; i++) {
            List<RadarEntry> entries = new ArrayList<>();
            for (int j = 0; j < labels.length; j++) {
                entries.add(new RadarEntry(vals[i][j]));
            }
            dataSets[i] = new RadarDataSet(entries, stackLabels[i]);
            dataSets[i].setColor(col[i]);
            dataSets[i].setDrawFilled(true);
            dataSets[i].setFillColor(col[i]);
            dataSets[i].setFillAlpha(40);
            data.addDataSet(dataSets[i]);
        }

        combinedIncRC.setData(data);
        combinedIncRC.getDescription().setEnabled(false);
        combinedIncRC.invalidate();

        XAxis xAxis = combinedIncRC.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        Legend legend = combinedIncRC.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setTextSize(16);
    }

    private void setupCombinedExpenseBC() {
        float[][] vals = new float[7][5];
        for (Expense e : expenseList) {
            int m = Utils.getMonthFromString(e.getDate());
            switch (e.getType()) {
                case "Bill":
                    vals[m][0] += e.getAmount();
                    break;
                case "Food":
                    vals[m][1] += e.getAmount();
                    break;
                case "Shopping":
                    vals[m][2] += e.getAmount();
                    break;
                case "Entertainment":
                    vals[m][3] += e.getAmount();
                    break;
                case "Miscellaneous":
                    vals[m][4] += e.getAmount();
                    break;
            }
        }

        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};
        String[] stackLabels = {"Bill", "Food", "Shopping", "Entertainment", "Misc."};

        List<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < labels.length; i++)
            entries.add(new BarEntry(i + 0.5f, vals[i]));

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setStackLabels(stackLabels);

        BarData data = new BarData(dataSet);
        combinedExpBC.setData(data);
        combinedExpBC.invalidate();
        combinedExpBC.getDescription().setEnabled(false);
        combinedExpBC.setDrawBorders(true);
        combinedExpBC.setDragEnabled(true);
        combinedExpBC.setVisibleXRangeMaximum(5);

        XAxis xAxis = combinedExpBC.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(7);
        xAxis.setAxisMinimum(0);

        YAxis yAxis = combinedExpBC.getAxisRight();
        yAxis.setEnabled(false);

        Legend legend = combinedExpBC.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setTextSize(16);

        int[] col = {getColor(R.color.red),
                getColor(R.color.green),
                getColor(R.color.orange),
                getColor(R.color.lightBlue),
                getColor(R.color.violet)};
        dataSet.setColors(col);
    }

    private void setupCombinedExpenseRC() {
        float[][] vals = new float[5][7];
        for (Expense e : expenseList) {
            int m = Utils.getMonthFromString(e.getDate());
            switch (e.getType()) {
                case "Bill":
                    vals[0][m] += e.getAmount();
                    break;
                case "Food":
                    vals[1][m] += e.getAmount();
                    break;
                case "Shopping":
                    vals[2][m] += e.getAmount();
                    break;
                case "Entertainment":
                    vals[3][m] += e.getAmount();
                    break;
                case "Miscellaneous":
                    vals[4][m] += e.getAmount();
                    break;
            }
        }

        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};
        String[] stackLabels = {"Bill", "Food", "Shopping", "Entertainment", "Misc."};
        int[] col = {getColor(R.color.red),
                getColor(R.color.green),
                getColor(R.color.orange),
                getColor(R.color.lightBlue),
                getColor(R.color.violet)};

        RadarData data = new RadarData();
        RadarDataSet[] dataSets = new RadarDataSet[stackLabels.length];
        for (int i = 0; i < stackLabels.length; i++) {
            List<RadarEntry> entries = new ArrayList<>();
            for (int j = 0; j < labels.length; j++) {
                entries.add(new RadarEntry(vals[i][j]));
            }
            dataSets[i] = new RadarDataSet(entries, stackLabels[i]);
            dataSets[i].setColor(col[i]);
            dataSets[i].setDrawFilled(true);
            dataSets[i].setFillColor(col[i]);
            dataSets[i].setFillAlpha(40);
            data.addDataSet(dataSets[i]);
        }

        combinedExpRC.setData(data);
        combinedExpRC.getDescription().setEnabled(false);
        combinedExpRC.invalidate();

        XAxis xAxis = combinedExpRC.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        Legend legend = combinedExpRC.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
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
