package com.example.it0608android.BottomSheetDialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.it0608android.MenuActivity;
import com.example.it0608android.R;
import com.example.it0608android.database.ExpenseDB;
import com.example.it0608android.model.ExpenseModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class ExpenseBottomSheet extends BottomSheetDialogFragment {
    ExpenseDB expenseDB;
    ExpenseModel expenseModel;
    private final int id;
    public ExpenseBottomSheet(ExpenseModel model, int idExpense){
        this.expenseModel = model;
        this.id = idExpense;
    }

    @Nullable
    @Override
    @SuppressLint("NewApi")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.expense_bottom_sheet, container, false);

        ImageButton btnClose = view.findViewById(R.id.btnCancelExpense);
        ImageButton btnAdd = view.findViewById(R.id.btnAddSubmit);
        EditText edtName = view.findViewById(R.id.edtNameExpense);
        EditText edtMoney = view.findViewById(R.id.edtPriceExpense);
        EditText edtDescription = view.findViewById(R.id.edtDescriptionExpense);
        expenseDB = new ExpenseDB(getActivity());

        // update Expense
        if (id > 0){
            edtName.setText(expenseModel.getName());
            edtMoney.setText(String.valueOf(expenseModel.getPrice()));
            edtDescription.setText(expenseModel.getDescription());
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String price = edtMoney.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    edtName.setError("Name can not be empty");
                    return;
                }
                if (TextUtils.isEmpty(price)){
                    edtMoney.setError("Money can not be empty");
                    return;
                }
                if (TextUtils.isEmpty(description)){
                    edtDescription.setError("Description can not be empty");
                    return;
                }
                int priceEx = Integer.parseInt(price);
                if (id > 0){
                    // update
                    int update = expenseDB.updateExpense(name, priceEx, description, id);
                    if (update == -1){
                        // update failed
                        Toast.makeText(getActivity(), "Update Expense Failure", Toast.LENGTH_SHORT).show();
                    } else {
                        // update successful
                        Toast.makeText(getActivity(), "Update Expense Successful", Toast.LENGTH_SHORT).show();
                        edtName.setText("");
                        edtMoney.setText("");
                        edtDescription.setText("");
                        dismiss(); // close
                        Intent intent = new Intent(getActivity(), MenuActivity.class);
                        startActivity(intent);
                    }
                } else {
                    // insert
                    long insert = expenseDB.addNewExpense(name, priceEx, description);
                    if (insert == -1){
                        Toast.makeText(getActivity(), "Insert Failure", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Insert Successfully", Toast.LENGTH_SHORT).show();
                        edtName.setText("");
                        edtMoney.setText("");
                        edtDescription.setText("");
                        dismiss(); // close
                        Intent intent = new Intent(getActivity(), MenuActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setText("");
                edtMoney.setText("");
                edtDescription.setText("");
                dismiss(); // close
            }
        });
        return view;
    }
}
