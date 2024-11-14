package com.example.it0608android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it0608android.R;
import com.example.it0608android.model.ExpenseModel;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseItemViewHolder> {
    public ArrayList<ExpenseModel> expenseModels;
    public Context context;

    public ExpenseAdapter(ArrayList<ExpenseModel> expenses, Context context){
        this.expenseModels = expenses;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpenseItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        return new ExpenseItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseItemViewHolder holder, int position) {
        ExpenseModel model = expenseModels.get(position);
        holder.tvNameExpense.setText(model.getName());
        holder.tvPriceExpense.setText(String.valueOf(model.getPrice()));
        holder.tvDesExpense.setText(model.getDescription());
    }

    @Override
    public int getItemCount() {
        return expenseModels.size();
    }
    public static class ExpenseItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameExpense, tvPriceExpense, tvDesExpense;
        public ExpenseItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameExpense = itemView.findViewById(R.id.tvNameExpense);
            tvPriceExpense = itemView.findViewById(R.id.tvPriceExpense);
            tvDesExpense = itemView.findViewById(R.id.tvDesExpense);
        }

    }
}
