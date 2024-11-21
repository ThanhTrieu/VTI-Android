package com.example.it0608android;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.it0608android.BottomSheetDialog.ExpenseBottomSheet;
import com.example.it0608android.adapter.ExpenseAdapter;
import com.example.it0608android.database.ExpenseDB;
import com.example.it0608android.model.ExpenseModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<ExpenseModel> expenseModelArrayList;
    private ExpenseAdapter expenseAdapter;
    private ExpenseDB expenseDB;
    private RecyclerView expenseRV;
    private ExpenseModel expenseModel;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ImageButton imgButton = view.findViewById(R.id.btnAddExpense);
        expenseRV = view.findViewById(R.id.rvExpense);

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseBottomSheet bottomSheet = new ExpenseBottomSheet(expenseModel, 0);
                bottomSheet.show(getActivity().getSupportFragmentManager(),
                        "ExpenseBottomSheet");
            }
        });
        expenseModelArrayList = new ArrayList<>();
        expenseDB = new ExpenseDB(getActivity());
        expenseModelArrayList = expenseDB.getListExpenses();
        expenseAdapter = new ExpenseAdapter(expenseModelArrayList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        expenseRV.setLayoutManager(linearLayoutManager);
        expenseRV.setAdapter(expenseAdapter);

        expenseAdapter.setOnClickListener(new ExpenseAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                expenseModel = expenseModelArrayList.get(position);
                ExpenseBottomSheet bottomSheet = new ExpenseBottomSheet(expenseModel, expenseModel.getId());
                bottomSheet.show(getActivity().getSupportFragmentManager(),
                        "ExpenseBottomSheet");
            }
        });

        // delete item
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // this method is called
                // when the item is moved.
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                expenseModel = expenseModelArrayList.get(viewHolder.getAdapterPosition());

                // below line is to get the position
                // of the item at that position.
                int position = viewHolder.getAdapterPosition();

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                expenseModelArrayList.remove(viewHolder.getAdapterPosition());

                // below line is to notify our item is removed from adapter.
                expenseAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                // delete item database
                expenseDB.deleteExpense(expenseModel.getId());

                // below line is to display our snackbar with action.
                Snackbar.make(expenseRV, expenseModel.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(View v) {
                        // adding on click listener to our action of snack bar.
                        // below line is to add our item to array list with a position.
                        expenseModelArrayList.add(position, expenseModel);

                        // below line is to notify item is
                        // added to our adapter class.
                        expenseAdapter.notifyItemInserted(position);

                        expenseDB.addNewExpense(expenseModel.getName(), expenseModel.getPrice(), expenseModel.getDescription());
                    }
                }).show();
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(expenseRV);

        return view;
    }
}