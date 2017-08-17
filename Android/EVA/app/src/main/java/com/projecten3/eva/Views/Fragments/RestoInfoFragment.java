package com.projecten3.eva.Views.Fragments;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projecten3.eva.R;
import com.projecten3.eva.Model.Restaurant;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestoInfoFragment extends Fragment {


    private final static String KEY_RESTO = "resto";
    private String imageURL = "https://maps.googleapis.com/maps/api/streetview?size=600x300&location=strijmeers101,vlierzele&pitch=-0.76&key=AIzaSyBy1W27MKbrzbn54k1s49s5wfGku_hhV5w";


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
    @BindView(R.id.fab)
    FloatingActionButton fab;

    /**
     * required empty constructor for fragments
     */
    public RestoInfoFragment() {
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

        System.out.println(getString(R.string.streetview_api_url));
        naam.setText(resto.getNaam());

       /* Glide.with(getContext())
                .load(getString(R.string.streetview_api_url,resto.().replace(" ","")))
                .into(foto);
        adressResto.setText(resto.getLocation());

        omschrijving.setText(resto.getOmschrijving());

*/
    }

    @OnClick(R.id.tel)
    public void belRestaurent(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+resto.getTelefoon()));
        startActivity(intent);
    }

    @OnClick(R.id.fab)
    public void openRoute(){
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/dir/?api=1&destination="+resto.getAdres().getStraat()));
        startActivity(intent);

    }



}
