package com.projecten3.eva;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestoInfoFragment extends Fragment {


    private final static String KEY_RESTO = "resto";


    private Restaurant resto;
    @BindView(R.id.naamResto)
    TextView naam;
    @BindView(R.id.imvFoto)
    ImageView foto;
    @BindView(R.id.adressResto)
    TextView adressResto;
    @BindView(R.id.tel)
    ImageButton btnTel;
    @BindView(R.id.omschrijving)
    TextView omschrijving;



    public RestoInfoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_resto_info, container, false);
        ButterKnife.bind(this,v);


        return v;
    }



    @Override
    public void onStart(){
        super.onStart();

        Bundle args = getArguments();
        if(args != null && args.containsKey("resto")){
        setContent((Restaurant)args.getParcelable("resto"));
        }
    }



    public void setContent(Restaurant resto){

        this.resto = resto;

        naam.setText(resto.getNaam());
        Glide.with(getContext())
                .load(resto.getFoto())
                .centerCrop()
                .into(foto);
        adressResto.setText(resto.getLocation());

        omschrijving.setText(resto.getOmschrijving());


    }



    @OnClick(R.id.tel)
    public void belRestaurent(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+resto.getTelefoonNr()));
        startActivity(intent);




    }



}
