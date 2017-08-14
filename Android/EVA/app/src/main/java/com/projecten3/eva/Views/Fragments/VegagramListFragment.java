package com.projecten3.eva.Views.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.projecten3.eva.Adapters.VegagramAdapter;
import com.projecten3.eva.Model.Post;
import com.projecten3.eva.R;
import com.projecten3.eva.Views.VegagramActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Bram on 8/08/2017.
 */

public class VegagramListFragment extends Fragment {

    public static final String TAG = "VegagramListFragment";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private List<Post> posts;
    private VegagramAdapter adapter;
    protected RecyclerView.LayoutManager llm;

    @BindView(R.id.rvVegagram)
    public RecyclerView rv;

    @BindView(R.id.take_picture)
    FloatingActionButton photo_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vegagram_list,container,false);
        setHasOptionsMenu(true);

        ButterKnife.bind(this,v);
        posts = new ArrayList<>();

        photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        mockData();
        adapter = new VegagramAdapter(posts,getContext());
        rv.setAdapter(adapter);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.vegagram_list_filter_menu, menu);
        return;
    }

    private void mockData(){
        Post post1 = new Post(null,new Date(), 100, false);
        Post post2 = new Post(null,new Date(), 110, false);
        Post post3 = new Post(null,new Date(), 20, false);
        Post post4 = new Post(null,new Date(), 1692, false);
        Post post5 = new Post(null,new Date(), 2, false);

        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ((VegagramActivity)getActivity()).createPhotoTakenFragement(imageBitmap);
        }
    }
}
