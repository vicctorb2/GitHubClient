package com.vburak.githubclient.view;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;
import com.vburak.githubclient.model.Repository;

import java.util.List;

public class RepositoriesActivity extends ListActivity {


    ImageView avatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Repository> data = (List<Repository>) getIntent().getSerializableExtra("data");
        SwingRightInAnimationAdapter swingBottomInAdapter = new SwingRightInAnimationAdapter(new ReposListAdapter(getBaseContext(), data));
        swingBottomInAdapter.setAbsListView(getListView());
        getListView().setAdapter(swingBottomInAdapter);
    }
}
