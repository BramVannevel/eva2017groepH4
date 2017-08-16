package com.projecten3.eva.Views.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.projecten3.eva.R;
import com.projecten3.eva.Views.VegagramActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Bram on 11/08/2017.
 */

public class VegagramPhotoFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @BindView(R.id.vegagram_check)
    CheckBox cbVegagram_checkbox;

    @BindView(R.id.facebook_check)
    CheckBox cvFacebook_checkbox;

    @BindView(R.id.link_challenge_check)
    CheckBox cbChallenge_checkbox;

    @BindView(R.id.photograph_taken)
    ImageView imvPhotograph_taken;

    @BindView(R.id.retake_photo)
    Button btnRetakePhoto;

    @BindView(R.id.save_photo)
    Button btnSavePhoto;

    public VegagramPhotoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_taken, container, false);
        ButterKnife.bind(this,v);

        btnRetakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ((VegagramActivity)getActivity()).createPhotoTakenFragement(imageBitmap);
        }
    }
}
