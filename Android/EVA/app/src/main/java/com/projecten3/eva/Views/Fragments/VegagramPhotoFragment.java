package com.projecten3.eva.Views.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.projecten3.eva.Data.EvaApiBuilder;
import com.projecten3.eva.R;
import com.projecten3.eva.Views.VegagramActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class VegagramPhotoFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap image;
    File file;

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

        return v;
    }

    @OnClick(R.id.save_photo)
    public void onSaveClicked(){
        file = new File(getContext().getCacheDir(), "images");
        try {
            file.createNewFile();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("fileToUpload", file.getName(), requestFile);
        Call<Void> call = EvaApiBuilder.getInstance().uploadVegagramPost(body,true,0,new Date());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    //error
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.retake_photo)
    public void onRetakeClicked(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
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
            image = bmp;
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
