package com.vburak.githubclient.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vburak.githubclient.R;
import com.vburak.githubclient.api.Client;
import com.vburak.githubclient.api.Service;
import com.vburak.githubclient.model.GitHubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vburak.githubclient.view.MainActivity.authHeader;


/** An adapter for usersList recycler view**/
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {

    Context mContext;
    List<GitHubUser> mData;
    UsersListFragment usersListFragment = new UsersListFragment();
    GitHubUser detailedUser;

    public RecyclerViewAdapter(Context mContext, List<GitHubUser> mData) {
        if (mData == null) {
            mData.add(new GitHubUser("vicctorb2", "https://avatars2.githubusercontent.com/u/17727453?v=4", "https://github.com/vicctorb2"));
            this.mContext = mContext;
        } else {
            this.mData = mData;
            this.mContext = mContext;
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        holder.usernameTV.setText(mData.get(position).getUsername());
        holder.accountLinkTV.setText(mData.get(position).getUserAccountLink());
        Picasso.with(mContext).load(mData.get(position).getImage()).placeholder(R.drawable.logo).into(holder.img);

        //setting on click listener for showing the detailed info of the user
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gets the user
                GitHubUser detailedUser = mData.get(position);
                getFullInfoAndStartActivity(detailedUser.getUsername());
            }
        };
        holder.itemView.setOnClickListener(onClickListener);

    }

    //this method gets full info of detailed user using the username and starts the activity to show info
    private void getFullInfoAndStartActivity(String username) {
        Service apiService = Client.getClient().create(Service.class);
        Call<GitHubUser> call = apiService.getSingleUser(authHeader, username);
        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                try {
                    if (response.isSuccessful() && response.code() != 403) {
                        detailedUser = response.body();
                        Intent intent = new Intent(mContext, UserDetails.class);
                        intent.putExtra("detailedUser", detailedUser);
                        mContext.startActivity(intent);
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_user;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

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
