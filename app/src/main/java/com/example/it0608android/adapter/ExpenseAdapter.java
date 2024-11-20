package com.example.it0608android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it0608android.R;
import com.example.it0608android.database.ExpenseDB;
import com.example.it0608android.model.ExpenseModel;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseItemViewHolder> {
    public ArrayList<ExpenseModel> expenseModels;
    public Context context;
    public OnClickListener onClickListener;

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

        // Set click listener on the item view
        holder.itemView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseModels.size();
    }


    // Setter for the click listener
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener =  onClickListener;
    }

    // Interface for the click listener
    public interface OnClickListener {
        void onClick(int position);
    }
    public class ExpenseItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameExpense, tvPriceExpense, tvDesExpense;
        View itemView;
        public ExpenseItemViewHolder(@NonNull View itemView) {
            super(itemView.getRootView());
            this.itemView = itemView;
            tvNameExpense = itemView.findViewById(R.id.tvNameExpense);
            tvPriceExpense = itemView.findViewById(R.id.tvPriceExpense);
            tvDesExpense = itemView.findViewById(R.id.tvDesExpense);

            // Set click listener on the ViewHolder's item view
            itemView.setOnClickListener(view -> {
                if (onClickListener != null) {
                    onClickListener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
