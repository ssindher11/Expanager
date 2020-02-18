package com.ssindher11.expangger.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
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
import com.ssindher11.expangger.models.Expense;

import java.util.ArrayList;
import java.util.Calendar;


public class AddExpenseActivity extends AppCompatActivity {

    private HorizontalCalendarView hcv;
    private TextView backTV;
    private CheckBox rbBill, rbFood, rbShopping, rbEntmt, rbMisc;
    private FloatingActionButton cancelFAB;
    private MaterialButton addExpenseBtn;
    private EditText titleET, amountET;
    private ImageView cardIV, cashIV, upiIV, walletIV, bgIV;
    private LottieAnimationView lav;

    private int m1 = 0, m2 = 0, m3 = 0, m4 = 0;
    private String mType = "Bill";
    private String mMode = "Card";
    private String mDate = Utils.getFormattedDateToday();

    private SharedPreferences sharedPreferences;

    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        initViews();
        initListeners();
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
                R.drawable.color_status_1);
    }

    private void initViews() {
        hcv = findViewById(R.id.hcv_expense);
        backTV = findViewById(R.id.tv_back_expense);
        rbBill = findViewById(R.id.rb_bill);
        rbFood = findViewById(R.id.rb_food);
        rbShopping = findViewById(R.id.rb_shopping);
        rbEntmt = findViewById(R.id.rb_entmt);
        rbMisc = findViewById(R.id.rb_misc);
        cancelFAB = findViewById(R.id.fab_cancel_expense);
        addExpenseBtn = findViewById(R.id.btn_add_expense);
        titleET = findViewById(R.id.et_title_expense);
        amountET = findViewById(R.id.et_amount_expense);
        cardIV = findViewById(R.id.iv_card_expense);
        cashIV = findViewById(R.id.iv_cash_expense);
        upiIV = findViewById(R.id.iv_upi_expense);
        walletIV = findViewById(R.id.iv_wallet_expense);
        lav = findViewById(R.id.lav_expense);
        bgIV = findViewById(R.id.iv_bg_expense);
    }

    private void initListeners() {
        backTV.setOnClickListener(view -> super.onBackPressed());
        cancelFAB.setOnClickListener(view -> super.onBackPressed());

        addExpenseBtn.setOnClickListener(view -> addExpense());
        cardIV.setOnClickListener(view -> {
            if (m1 == 0) {
                m1 = 1;
                m2 = 0;
                m3 = 0;
                m4 = 0;
                cardIV.animate().alpha(1f).setInterpolator(new AccelerateInterpolator());
                cashIV.animate().alpha(0.4f).setInterpolator(new AccelerateInterpolator());
                upiIV.animate().alpha(0.4f).setInterpolator(new AccelerateInterpolator());
                walletIV.animate().alpha(0.4f).setInterpolator(new AccelerateInterpolator());
                mMode = "Card";
            }
        });
        cashIV.setOnClickListener(view -> {
            if (m2 == 0) {
                m1 = 0;
                m2 = 1;
                m3 = 0;
                m4 = 0;
                cardIV.animate().alpha(0.4f).setInterpolator(new AccelerateInterpolator());
                cashIV.animate().alpha(1f).setInterpolator(new AccelerateInterpolator());
                upiIV.animate().alpha(0.4f).setInterpolator(new AccelerateInterpolator());
                walletIV.animate().alpha(0.4f).setInterpolator(new AccelerateInterpolator());
                mMode = "Cash";
            }
        });
        upiIV.setOnClickListener(view -> {
            if (m3 == 0) {
                m1 = 0;
                m2 = 0;
                m3 = 1;
                m4 = 0;
                cardIV.animate().alpha(0.4f).setInterpolator(new AccelerateInterpolator());
                cashIV.animate().alpha(0.4f).setInterpolator(new AccelerateInterpolator());
                upiIV.animate().alpha(1f).setInterpolator(new AccelerateInterpolator());
                walletIV.animate().alpha(0.4f).setInterpolator(new AccelerateInterpolator());
                mMode = "UPI";
            }
        });
        walletIV.setOnClickListener(view -> {
            if (m4 == 0) {
                m1 = 0;
                m2 = 0;
                m3 = 0;
                m4 = 1;
                cardIV.animate().alpha(0.4f).setInterpolator(new AccelerateInterpolator());
                cashIV.animate().alpha(0.4f).setInterpolator(new AccelerateInterpolator());
                upiIV.animate().alpha(0.4f).setInterpolator(new AccelerateInterpolator());
                walletIV.animate().alpha(1f).setInterpolator(new AccelerateInterpolator());
                mMode = "Wallet";
            }
        });
    }

    private void chooseType() {
        final int[] ids = {R.id.rb_bill, R.id.rb_food, R.id.rb_shopping, R.id.rb_entmt, R.id.rb_misc};

        rbBill.setOnClickListener(view1 -> {
            for (int i = 0; i < 5; i++) {
                if (ids[i] != rbBill.getId()) {
                    CheckBox v = findViewById(ids[i]);
                    if (v.isChecked()) {
                        v.setChecked(false);
                    }
                }
            }
            ((CheckBox) view1).setChecked(true);
            mType = "Bill";
        });

        rbFood.setOnClickListener(view1 -> {
            for (int i = 0; i < 5; i++) {
                if (ids[i] != rbFood.getId()) {
                    CheckBox v = findViewById(ids[i]);
                    if (v.isChecked()) {
                        v.setChecked(false);
                    }
                }
            }
            ((CheckBox) view1).setChecked(true);
            mType = "Food";
        });

        rbShopping.setOnClickListener(view1 -> {
            for (int i = 0; i < 5; i++) {
                if (ids[i] != rbShopping.getId()) {
                    CheckBox v = findViewById(ids[i]);
                    if (v.isChecked()) {
                        v.setChecked(false);
                    }
                }
            }
            ((CheckBox) view1).setChecked(true);
            mType = "Shopping";
        });

        rbEntmt.setOnClickListener(view1 -> {
            for (int i = 0; i < 5; i++) {
                if (ids[i] != rbEntmt.getId()) {
                    CheckBox v = findViewById(ids[i]);
                    if (v.isChecked()) {
                        v.setChecked(false);
                    }
                }
            }
            ((CheckBox) view1).setChecked(true);
            mType = "Entertainment";
        });

        rbMisc.setOnClickListener(view1 -> {
            for (int i = 0; i < 5; i++) {
                if (ids[i] != rbMisc.getId()) {
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

    private void addExpense() {
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
            Expense expense = new Expense(amount, mDate, title, mMode, "bill", mType);
            String uid = sharedPreferences.getString("uid", "");

            long tim = Calendar.getInstance().getTimeInMillis();
            String mTime = tim + "";

            bgIV.setVisibility(View.VISIBLE);
            lav.setVisibility(View.VISIBLE);
            lav.playAnimation();

            mDatabase.getReference().child("users").child(uid).child("expenses").child(mTime)
                    .setValue(expense)
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
