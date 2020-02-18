package com.ssindher11.expangger.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.ssindher11.expangger.HorizontalCalendarView;
import com.ssindher11.expangger.R;
import com.ssindher11.expangger.Utils;
import com.ssindher11.expangger.models.Income;

import java.util.ArrayList;
import java.util.Calendar;

public class AddIncomeActivity extends AppCompatActivity {

    private HorizontalCalendarView hcv;
    private TextView backTV;
    private FloatingActionButton cancelFAB;
    private MaterialButton addIncomeBtn;
    private EditText titleET, amountET;
    private CheckBox rbSalary, rbRewards, rbCashback, rbIncome;
    private LottieAnimationView lav;
    private ImageView bgIV;

    private String mType = "Salary";

    private String mDate = Utils.getFormattedDateToday();

    private SharedPreferences sharedPreferences;

    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        initViews();
        initListerners();
        chooseType();

        mDatabase = FirebaseDatabase.getInstance();

        sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);

        Calendar start = Calendar.getInstance();
        start.add(Calendar.MONTH, -6);
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MONTH, 6);

        ArrayList<String> datesToBeColoured = new ArrayList<>();
        datesToBeColoured.add(Utils.getFormattedDateToday());

        hcv.setUpCalendar(start.getTimeInMillis(), end.getTimeInMillis(),
                datesToBeColoured,
                date -> mDate = date,
                R.drawable.color_status_2);
    }

    private void initViews() {
        hcv = findViewById(R.id.hcv_income);
        backTV = findViewById(R.id.tv_back_income);
        cancelFAB = findViewById(R.id.fab_cancel_income);
        addIncomeBtn = findViewById(R.id.btn_add_income);
        titleET = findViewById(R.id.et_title_income);
        amountET = findViewById(R.id.et_amount_income);
        rbSalary = findViewById(R.id.rb_salary);
        rbRewards = findViewById(R.id.rb_rewards);
        rbCashback = findViewById(R.id.rb_cashback);
        rbIncome = findViewById(R.id.rb_misc_income);
        lav = findViewById(R.id.lav_income);
        bgIV = findViewById(R.id.iv_bg_income);
    }

    private void initListerners() {
        backTV.setOnClickListener(view -> super.onBackPressed());
        cancelFAB.setOnClickListener(view -> super.onBackPressed());
        addIncomeBtn.setOnClickListener(view -> addIncome());
    }

    private void chooseType() {
        final int[] ids = {R.id.rb_salary, R.id.rb_rewards, R.id.rb_cashback, R.id.rb_misc_income};

        rbSalary.setOnClickListener(view1 -> {
            for (int i = 0; i < 4; i++) {
                if (ids[i] != rbSalary.getId()) {
                    CheckBox v = findViewById(ids[i]);
                    if (v.isChecked()) {
                        v.setChecked(false);
                    }
                }
            }
            ((CheckBox) view1).setChecked(true);
            mType = "Salary";
        });

        rbRewards.setOnClickListener(view1 -> {
            for (int i = 0; i < 4; i++) {
                if (ids[i] != rbRewards.getId()) {
                    CheckBox v = findViewById(ids[i]);
                    if (v.isChecked()) {
                        v.setChecked(false);
                    }
                }
            }
            ((CheckBox) view1).setChecked(true);
            mType = "Reward";
        });

        rbCashback.setOnClickListener(view1 -> {
            for (int i = 0; i < 4; i++) {
                if (ids[i] != rbCashback.getId()) {
                    CheckBox v = findViewById(ids[i]);
                    if (v.isChecked()) {
                        v.setChecked(false);
                    }
                }
            }
            ((CheckBox) view1).setChecked(true);
            mType = "Cashback";
        });

        rbIncome.setOnClickListener(view1 -> {
            for (int i = 0; i < 4; i++) {
                if (ids[i] != rbIncome.getId()) {
                    CheckBox v = findViewById(ids[i]);
                    if (v.isChecked()) {
                        v.setChecked(false);
                    }
                }
            }
            ((CheckBox) view1).setChecked(true);
            mType = "Miscellaneous";
        });
    }

    private void addIncome() {
        boolean f1 = true, f2 = true;
        if (titleET.getText().toString().trim().equalsIgnoreCase("")) {
            titleET.setError("Enter a title");
            f1 = false;
        }
        if (amountET.getText().toString().trim().equalsIgnoreCase("")) {
            amountET.setError("Enter the amount");
            f2 = false;
        }

        if (f1 && f2) {
            double amount = Double.parseDouble(amountET.getText().toString());
            String title = titleET.getText().toString();
            Income income = new Income(amount, title, mType, mDate);
            String uid = sharedPreferences.getString("uid", "");

            long tim = Calendar.getInstance().getTimeInMillis();
            String mTime = tim + "";

            bgIV.setVisibility(View.VISIBLE);
            lav.setVisibility(View.VISIBLE);
            lav.playAnimation();

            mDatabase.getReference().child("users").child(uid).child("incomes").child(mTime)
                    .setValue(income)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            super.onBackPressed();
                        } else {
                            Utils.showErrorSnackbar(findViewById(android.R.id.content).getRootView(), "Some error occurred!");
                        }
                    });
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
