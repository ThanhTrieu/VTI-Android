package com.example.it0608android.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.it0608android.R;
import com.example.it0608android.model.Products;
import com.squareup.picasso.Picasso;


import java.util.List;

public class ProductListAdapter extends BaseAdapter {
    public List<Products> products;
    public ProductListAdapter(List<Products> items){
        super();
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
}
