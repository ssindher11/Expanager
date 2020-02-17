package com.ssindher11.expangger.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ssindher11.expangger.R;
import com.ssindher11.expangger.adapters.ExpenseAdapter;
import com.ssindher11.expangger.models.Expense;

import java.util.ArrayList;
import java.util.List;

import ng.max.slideview.SlideView;

public class MainActivity extends AppCompatActivity {

    private SlideView incomeSV, expenseSV;
    private CircularImageView profileCIV, profileOpenCIV;
    private TextView incomeTV, expenseTV, nameTV, emailTV;
    private ConstraintLayout profileCL;
    private MaterialButton logoutBtn;
    private RecyclerView expenseRV;

    private SharedPreferences sharedPreferences;

    private List<Expense> expenseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();

        sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
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

        String def = getResources().getString(R.string.Rs) + " " + addThousandSeparator("15000");
        incomeTV.setText(def);
        def = getResources().getString(R.string.Rs) + " " + addThousandSeparator("17500");
        expenseTV.setText(def);

        populateList();
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
        expenseList.clear();
        expenseList.add(new Expense(205, "20-08-2019", "Vodafone", "UPI", "https://www.vodafone.com.mt/file.aspx?f=14091", "Bill"));
        expenseList.add(new Expense(5, "20-08-2019", "Rangalya Royale", "Card", "", "Food"));
        expenseList.add(new Expense(95, "20-08-2019", "John Doe", "Cash", "", "Loan to John Doe", "Entertainment"));
        expenseList.add(new Expense(1000, "20-08-2019", "Almart", "UPI", "", "Miscellaneous"));
        expenseList.add(new Expense(200, "20-08-2019", "Zara", "Cash", "https://www.slhn.org/-/media/slhn/Billpay/Image/General/sample_Single_Bill_for_SLPG_and_Hospital_Patients.jpg?la=en&hash=2373A997159E68A1340FE67324AB4F2610747172", "Birthday gift for Jane Doe", "Shopping"));
        expenseList.add(new Expense(20, "20-08-2019", "Almart", "Cash", "", "Bill"));
        expenseList.add(new Expense(25, "20-08-2019", "Almart", "UPI", "", "Polish", "Miscellaneous"));
        expenseList.add(new Expense(599, "20-08-2019", "Almart", "Cash", "", "Bill"));

        ExpenseAdapter expenseAdapter = new ExpenseAdapter(expenseList);
        expenseRV.setAdapter(expenseAdapter);
    }
}
