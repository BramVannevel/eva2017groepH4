package com.projecten3.eva.Views.Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.projecten3.eva.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bram on 11/08/2017.
 */

public class VegagramPhotoFragment extends Fragment {

    @BindView(R.id.public_check)
    CheckBox cbPublic_checkbox;

    @BindView(R.id.link_challenge_check)
    CheckBox cbChallenge_checkbox;

    @BindView(R.id.photograph_taken)
    ImageView imvPhotograph_taken;

    public VegagramPhotoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_taken, container, false);
        ButterKnife.bind(this,v);

        return v;
    }

    public void setContent(Bitmap imgBitmap){
        imvPhotograph_taken.setImageBitmap(imgBitmap);
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if(args != null && args.containsKey("photo")){
            byte[] byteArray = getArguments().getByteArray("photo");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            setContent(bmp);
        }
    }

    public void saveImage(View v){

    }
}
