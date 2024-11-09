package com.example.it0608android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it0608android.R;
import com.example.it0608android.model.DemoModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DemoRVAdapter extends RecyclerView.Adapter<DemoRVAdapter.UserItemViewHolder>{
    public List<DemoModel> users;
    public Context context;

    public DemoRVAdapter(List<DemoModel> users, Context c) {
        this.users = users;
        this.context = c;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @NonNull
    @Override
    public UserItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_demo, parent, false);

        return new UserItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserItemViewHolder holder, int position) {
        DemoModel u = users.get(position);
        Picasso.get()
                .load(u.avatar_url)
                .into(holder.ivAvatar);
        holder.tvLoginName.setText(u.login);
        holder.tvId.setText(String.valueOf(u.id));
    }

    public static class UserItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLoginName;
        public TextView tvId;
        public ImageView ivAvatar;

        public UserItemViewHolder(View itemView) {
            super(itemView);
            tvLoginName = (TextView) itemView.findViewById(R.id.tv_login_name);
            tvId = (TextView) itemView.findViewById(R.id.tv_id);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
        }
    }
}
