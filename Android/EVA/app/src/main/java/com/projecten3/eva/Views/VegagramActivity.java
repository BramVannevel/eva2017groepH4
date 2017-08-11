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

public class VegagramActivity extends AppCompatActivity {

    public static final String TAG = "VegagramListActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegagram);

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

        fragmentTransaction.replace(R.id.fragment_vegagram,vegagramPhotoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
