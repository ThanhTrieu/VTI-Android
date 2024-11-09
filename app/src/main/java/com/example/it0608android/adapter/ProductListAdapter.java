package com.example.it0608android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.it0608android.R;
import com.example.it0608android.model.Products;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends BaseAdapter implements Filterable {
    public List<Products> products;
    public List<Products> searchPd;
    public Context context;
    public ProductListAdapter(Context context, List<Products> items){
        super();
        this.context = context;
        this.products = items;
    }
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return products.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewProductList;
        if (convertView == null) {
            viewProductList = View.inflate(parent.getContext(), R.layout.product_list, null);
        } else {
            viewProductList = convertView;
        }
        // do du lieu ra product list view
        Products pd = (Products) getItem(position);
        ImageView imgPd = viewProductList.findViewById(R.id.imgProduct);
        TextView tvIdPd = viewProductList.findViewById(R.id.idProduct);
        TextView tvNamePd = viewProductList.findViewById(R.id.nameProduct);
        TextView tvPricePd = viewProductList.findViewById(R.id.priceProduct);

        tvIdPd.setText(String.valueOf(pd.id));
        tvNamePd.setText(pd.name);
        tvPricePd.setText(String.valueOf(pd.price));
        Picasso.get().load(pd.image).into(imgPd);


        return viewProductList;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<Products> results = new ArrayList<>();
                if (searchPd == null) {
                    searchPd = products;
                }
                if (constraint != null) {
                    if (searchPd != null && !searchPd.isEmpty()) {
                        for (final Products g : searchPd) {
                            if (g.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                products = (List<Products>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
