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
import com.ssindher11.expangger.models.Income;

import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder> {

    private List<Income> incomes;

    public IncomeAdapter(List<Income> incomes) {
        this.incomes = incomes;
    }

    @NonNull
    @Override
    public IncomeAdapter.IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.income_list_item, parent, false);
        return new IncomeAdapter.IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeAdapter.IncomeViewHolder holder, int position) {
        final Income income = incomes.get(position);
        Resources resources = holder.itemView.getResources();
        String rs = resources.getString(R.string.Rs);
        holder.amount.setText(rs + (int) income.getAmount());
        holder.title.setText(income.getTitle());
        holder.date.setText(income.getDate());
        switch (income.getType()) {
            case "Salary":
                holder.type.setImageResource(R.drawable.bg_salary);
                break;

            case "Reward":
                holder.type.setImageResource(R.drawable.bg_reward);
                break;

            case "Cashback":
                holder.type.setImageResource(R.drawable.bg_cashback);
                break;

            case "Miscellaneous":
                holder.type.setImageResource(R.drawable.bg_misc);
                break;

            default:
                holder.type.setImageResource(R.drawable.misc);
        }
    }

    @Override
    public int getItemCount() {
        return incomes.size();
    }

    class IncomeViewHolder extends RecyclerView.ViewHolder {

        CardView container;
        TextView amount;
        TextView title;
        TextView date;
        ImageView type;

        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.cv_item_container);
            amount = itemView.findViewById(R.id.tv_item_amount);
            title = itemView.findViewById(R.id.tv_item_location);
            date = itemView.findViewById(R.id.tv_item_date);
            type = itemView.findViewById(R.id.iv_item_type);

        }
    }
}
