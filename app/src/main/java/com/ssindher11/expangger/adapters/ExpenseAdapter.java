package com.ssindher11.expangger.adapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ssindher11.expangger.R;
import com.ssindher11.expangger.models.Expense;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenses;

    public ExpenseAdapter(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.expense_list_item, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        final Expense expense = expenses.get(position);
        Resources resources = holder.itemView.getResources();
        String rs = resources.getString(R.string.Rs);
        holder.amount.setText(rs + (int) expense.getAmount());
        holder.title.setText(expense.getTitle());
        holder.date.setText(expense.getDate());
        switch (expense.getType()) {
            case "Food":
                holder.type.setImageResource(R.drawable.food);
                break;

            case "Bill":
                holder.type.setImageResource(R.drawable.bill);
                break;

            case "Shopping":
                holder.type.setImageResource(R.drawable.shopping);
                break;

            case "Entertainment":
                holder.type.setImageResource(R.drawable.entmt);
                break;

            case "Miscellaneous":
                holder.type.setImageResource(R.drawable.misc);
                break;

            default: holder.type.setImageResource(R.drawable.misc);
        }
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {

        CardView container;
        TextView amount;
        TextView title;
        TextView date;
        ImageView type;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.cv_item_container);
            amount = itemView.findViewById(R.id.tv_item_amount);
            title = itemView.findViewById(R.id.tv_item_location);
            date = itemView.findViewById(R.id.tv_item_date);
            type = itemView.findViewById(R.id.iv_item_type);

        }
    }
}
