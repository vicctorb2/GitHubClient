package com.vburak.githubclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vburak.githubclient.model.GitHubUser;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {

    Context mContext;
    List<GitHubUser> mData;
    UsersListFragment usersListFragment = new UsersListFragment();

    public RecyclerViewAdapter(Context mContext, List<GitHubUser> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == R.layout.item_user) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.end_of_list_button, parent, false);
        }
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        if (position == mData.size()) {
            holder.loadMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UsersListFragment.loadJSONfromAPI();
                }
            });
        } else {
            holder.usernameTV.setText(mData.get(position).getUsername());
            holder.accountLinkTV.setText(mData.get(position).getUserAccountLink());
            Picasso.with(mContext).load(mData.get(position).getImage()).placeholder(R.drawable.vicctorb).into(holder.img);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mData.size()) ? R.layout.end_of_list_button : R.layout.item_user;
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView usernameTV;
        private TextView accountLinkTV;
        private ImageView img;
        private Button loadMoreButton;

        public CustomViewHolder(View itemView) {

            super(itemView);
            usernameTV = (TextView) itemView.findViewById(R.id.username_id);
            accountLinkTV = (TextView) itemView.findViewById(R.id.account_link_id);
            img = (ImageView) itemView.findViewById(R.id.user_img_id);
            loadMoreButton = (Button) itemView.findViewById(R.id.button_end);

        }
    }
}
