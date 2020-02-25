package com.ssindher11.expangger.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ssindher11.expangger.R;
import com.ssindher11.expangger.Utils;
import com.ssindher11.expangger.adapters.ExpenseAdapter;
import com.ssindher11.expangger.models.Expense;
import com.ssindher11.expangger.models.ExpenseList;
import com.ssindher11.expangger.models.Income;
import com.ssindher11.expangger.models.IncomeList;
import com.ssindher11.expangger.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ng.max.slideview.SlideView;

public class MainActivity extends AppCompatActivity {

    private SlideView incomeSV, expenseSV;
    private CircularImageView profileCIV, profileOpenCIV;
    private TextView incomeTV, expenseTV, nameTV, emailTV;
    private ConstraintLayout profileCL;
    private MaterialButton logoutBtn;
    private RecyclerView expenseRV;
    private ImageButton statsIB;
    private ProgressBar pb;

    private SharedPreferences sharedPreferences;

    private boolean isDataFetched = false;

    private List<Expense> expenseList = new ArrayList<>();
    private List<Income> incomeList = new ArrayList<>();
    private ExpenseList eList = new ExpenseList();
    private IncomeList iList = new IncomeList();

    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();

        sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("users").child(sharedPreferences.getString("uid", ""));
        setupViews();
    }

    private void initViews() {
        incomeSV = findViewById(R.id.sv_income);
        expenseSV = findViewById(R.id.sv_expense);
        profileCIV = findViewById(R.id.civ_dp);
        incomeTV = findViewById(R.id.tv_income);
        expenseTV = findViewById(R.id.tv_expense);
        profileOpenCIV = findViewById(R.id.civ_profile);
        nameTV = findViewById(R.id.tv_name);
        emailTV = findViewById(R.id.tv_email);
        profileCL = findViewById(R.id.cl_profile);
        logoutBtn = findViewById(R.id.btn_logout);
        expenseRV = findViewById(R.id.rv_expense_main);
        statsIB = findViewById(R.id.ib_stats);
        pb = findViewById(R.id.pb_main);
    }

    private void initListeners() {
        incomeSV.setOnSlideCompleteListener(view -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(100);
            }
            startActivity(new Intent(MainActivity.this, AddIncomeActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        expenseSV.setOnSlideCompleteListener(slideView -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(100);
            }
            startActivity(new Intent(MainActivity.this, AddExpenseActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        profileCIV.setOnClickListener(view -> profileCL.setVisibility(View.VISIBLE));

        profileCL.setOnClickListener(view -> profileCL.setVisibility(View.GONE));

        logoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(MainActivity.this, SplashActivity.class));
            finishAfterTransition();
        });

        statsIB.setOnClickListener(view -> {
            if (isDataFetched) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                intent.putExtra("expenses", eList);
                intent.putExtra("incomes", iList);
                startActivity(intent);
            } else
                Utils.makeSnackbar(findViewById(android.R.id.content).getRootView(), "Fetching data, please wait");
        });
    }

    private void setupViews() {
        if (sharedPreferences.getString("photo", null) != null) {
            Glide.with(this)
                    .load(Uri.parse(sharedPreferences.getString("photo", null)))
                    .placeholder(R.drawable.ic_user_dp)
                    .into(profileCIV);

            Glide.with(this)
                    .load(Uri.parse(sharedPreferences.getString("photo", null)))
                    .placeholder(R.drawable.ic_user_dp)
                    .into(profileOpenCIV);
        }

        nameTV.setText(sharedPreferences.getString("name", ""));
        emailTV.setText(sharedPreferences.getString("email", ""));

        populateList();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        expenseRV.setLayoutManager(layoutManager);
    }

    private String addThousandSeparator(String amt) {
        String integer;
        String decimal;
        if (amt.contains(".")) {
            integer = amt.substring(0, amt.indexOf("."));   //Separate int and dec part of the amount
            decimal = amt.substring(amt.indexOf("."));
        } else {
            integer = amt;
            decimal = "";
        }
        if (integer.length() > 3) {
            StringBuilder tmp = new StringBuilder(integer);
            int i = integer.length() - 3;
            while (i > 0) {
                tmp.insert(i, ",");
                i -= 3;
            }
            integer = tmp.toString();
        }
        return integer + decimal;
    }

    private void populateList() {

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        incomeList = getTotalIncome(user.getIncomes());
                        expenseList = getTotalExpense(user.getExpenses());
                        ExpenseAdapter expenseAdapter = new ExpenseAdapter(expenseList);
                        expenseRV.setAdapter(expenseAdapter);
                        eList.setExpenses(expenseList);
                        iList.setIncomes(incomeList);
                        isDataFetched = true;
                        pb.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Utils.makeToast(MainActivity.this, e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Utils.showErrorSnackbar(findViewById(android.R.id.content).getRootView(), "Network Error!");
            }
        });
    }

    private List<Income> getTotalIncome(HashMap<String, Income> incomeHM) {
        List<Income> list = new ArrayList<>();
        double total = 0;
        for (Map.Entry mapElement : incomeHM.entrySet()) {
            Income income = (Income) mapElement.getValue();
            total += income.getAmount();
            list.add(income);
        }
        String inc = getResources().getString(R.string.Rs) + " " + addThousandSeparator((int) total + "");
        incomeTV.setText(inc);

        return list;
    }

    private List<Expense> getTotalExpense(HashMap<String, Expense> expenseHM) {
        List<Expense> list = new ArrayList<>();
        double total = 0;
        for (Map.Entry mapElement : expenseHM.entrySet()) {
            Expense expense = (Expense) mapElement.getValue();
            total += expense.getAmount();
            list.add(expense);
        }
        String exp = getResources().getString(R.string.Rs) + " " + addThousandSeparator((int) total + "");
        expenseTV.setText(exp);

        return list;
    }
}
