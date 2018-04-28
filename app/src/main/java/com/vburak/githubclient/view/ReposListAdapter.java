package com.vburak.githubclient.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vburak.githubclient.R;
import com.vburak.githubclient.model.Repository;

import java.util.List;
/**Adapter for repositories list view**/
public class ReposListAdapter extends BaseAdapter {


    Context mContext;
    List<Repository> mData;

    public ReposListAdapter(Context context, List<Repository> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Repository getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.repo_item, parent, false);
            TextView name = convertView.findViewById(R.id.repo_name_id);
            TextView description = convertView.findViewById(R.id.repo_description_id);

            //Button (link) to go to the browser for watching user's profile
            final ImageButton goToBrowser = convertView.findViewById(R.id.open_in_browser);
            goToBrowser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //starting browser activity
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //setiing url data for intent
                    intent.setData(Uri.parse(mData.get(position).getUrl()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    goToBrowser.setPressed(true);
                }
            });
            if (mData.get(position).getName()!=null){
                name.setText(mData.get(position).getName());
            }
            if (mData.get(position).getDescription()!=null){
                description.setText(mData.get(position).getDescription());
            }

        }
        return convertView;
    }

}
