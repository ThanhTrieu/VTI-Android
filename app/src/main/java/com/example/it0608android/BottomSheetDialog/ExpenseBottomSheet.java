package com.example.it0608android.BottomSheetDialog;

import android.annotation.SuppressLint;
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

import com.example.it0608android.R;
import com.example.it0608android.database.ExpenseDB;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ExpenseBottomSheet extends BottomSheetDialogFragment {
    ExpenseDB expenseDB;
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
                long insert = expenseDB.addNewExpense(name, priceEx, description);
                if (insert == -1){
                    Toast.makeText(getActivity(), "Insert Failure", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Insert Successfully", Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    edtMoney.setText("");
                    edtDescription.setText("");
                    dismiss(); // close
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
