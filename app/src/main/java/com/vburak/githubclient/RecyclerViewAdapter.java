package com.vburak.githubclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vburak.githubclient.model.GitHubUser;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {

    Context mContext;
    List<GitHubUser> mData;

    public RecyclerViewAdapter(Context mContext, List<GitHubUser> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_user,parent,false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.usernameTV.setText(mData.get(position).getUsername());
        holder.accountLinkTV.setText(mData.get(position).getUserAccountLink());
        holder.img.setImageResource(mData.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{

        private TextView usernameTV;
        private TextView accountLinkTV;
        private ImageView img;
        public CustomViewHolder(View itemView) {

            super(itemView);

            usernameTV = (TextView) itemView.findViewById(R.id.username_id);
            accountLinkTV = (TextView) itemView.findViewById(R.id.account_link_id);
            img = (ImageView) itemView.findViewById(R.id.user_img_id);
        }
    }
}
