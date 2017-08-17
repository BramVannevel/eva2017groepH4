package com.projecten3.eva.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.projecten3.eva.R;
import com.projecten3.eva.Views.Fragments.VegagramListFragment;
import com.projecten3.eva.Views.Fragments.VegagramPhotoFragment;

import java.io.ByteArrayOutputStream;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VegagramActivity extends AppCompatActivity {

    public static final String TAG = "VegagramListActivity";
    private Unbinder unbinder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegagram);
        unbinder = ButterKnife.bind(this);

        if (findViewById(R.id.fragment_vegagram) != null){
            if(savedInstanceState != null){
                return;
            }
            VegagramListFragment vegagramListFrag = new VegagramListFragment();
            vegagramListFrag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_vegagram, vegagramListFrag).commit();
        }
    }

    public void createPhotoTakenFragement(Bitmap imgBitmap){
        VegagramPhotoFragment vegagramPhotoFragment = new VegagramPhotoFragment();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Bundle args = new Bundle();

        args.putByteArray("photo", byteArray);
        vegagramPhotoFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.fragment_vegagram,vegagramPhotoFragment).addToBackStack(null).commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
    }
}
