package com.example.it0608android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.it0608android.adapter.ProductListAdapter;
import com.example.it0608android.model.Products;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetFragment extends Fragment implements SearchView.OnQueryTextListener {

    ListView listView;
    SearchView searchView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BudgetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetFragment newInstance(String param1, String param2) {
        BudgetFragment fragment = new BudgetFragment();
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
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        listView = view.findViewById(R.id.lvProductList);
        searchView = view.findViewById(R.id.searchView);


        List<Products> productsList = new ArrayList<>();
        productsList.add(new Products(1, "Iphone 16 pro max", "https://cdn.tgdd.vn/Products/Images/42/329149/iphone-16-pro-max-tu-nhien-thumb-600x600.jpg", 34900000));
        productsList.add(new Products(2, "Iphone 16 pro", "https://cdn.tgdd.vn/Products/Images/42/329143/iphone-16-pro-titan-tu-nhien.png", 28900000));
        productsList.add(new Products(3, "Iphone 16 Plus", "https://cdn.tgdd.vn/Products/Images/42/329138/iphone-16-plus-xanh-thumb-600x600.jpg", 25900000));
        productsList.add(new Products(4, "Iphone 16", "https://cdn.tgdd.vn/Products/Images/42/305658/iphone-15-pro-max-gold-thumbnew-600x600.jpg", 24900000));
        ProductListAdapter productListAdapter = new ProductListAdapter(getActivity(), productsList);
        listView.setAdapter(productListAdapter);

        listView.setTextFilterEnabled(true);
        setupSearchView();

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Products pd = (Products) listView.getItemAtPosition(position);
                String name = pd.getName();
                int idPd = (int) pd.getId();
                Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void setupSearchView(){
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search here ...");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)){
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText);
        }
        return true;
    }
}