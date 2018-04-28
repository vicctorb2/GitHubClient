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
/** List activity with custom animation for illustarte the list of user reposittories**/
public class RepositoriesActivity extends ListActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //trying to get user from intent parcelable data
        List<Repository> data = (List<Repository>) getIntent().getSerializableExtra("data");
        //custom animation for repos list
        SwingRightInAnimationAdapter swingBottomInAdapter = new SwingRightInAnimationAdapter(new ReposListAdapter(getBaseContext(), data));
        swingBottomInAdapter.setAbsListView(getListView());
        getListView().setAdapter(swingBottomInAdapter);
    }
}
