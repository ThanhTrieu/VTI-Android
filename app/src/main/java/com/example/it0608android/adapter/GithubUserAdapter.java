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
import com.example.it0608android.model.GithubUserModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GithubUserAdapter extends RecyclerView.Adapter<GithubUserAdapter.UserItemViewHolder> {
    public List<GithubUserModel> users;
    public Context context;

    public GithubUserAdapter(List<GithubUserModel> model, Context context){
        this.users = model;
        this.context = context;
    }

    @NonNull
    @Override
    public UserItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_github_user, parent, false);
        return new UserItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemViewHolder holder, int position) {
        GithubUserModel model = users.get(position);
        Picasso.get().load(model.getAvatar_url()).into(holder.ivAvatar);
        holder.tvLogin.setText(model.getLogin());
        holder.tvId.setText(String.valueOf(model.getId()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public static class UserItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivAvatar;
        public TextView tvLogin, tvId;
        public UserItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvLogin = (TextView) itemView.findViewById(R.id.tv_login_name);
            tvId = (TextView) itemView.findViewById(R.id.tv_id);
        }
    }
}
