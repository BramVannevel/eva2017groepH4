package com.projecten3.eva.Views.Fragments;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class RestoInfoFragment extends Fragment implements OnMapReadyCallback{
    private static final String TAG = "RestoInfoFragment";
    private final static String KEY_RESTO = "resto";


    //google maps
    @BindView(R.id.mapView)
    MapView mapView;
    GoogleMap googleMap;
    LatLng latLng;
    //
    private Restaurant resto;
    @BindView(R.id.naamResto)
    TextView naam;
    @BindView(R.id.imvFoto)
    ImageView foto;
    @BindView(R.id.adressResto)
    TextView adressResto;
    @BindView(R.id.tel)
    Button btnTel;
    @BindView(R.id.omschrijving)
    TextView omschrijving;
    @BindView(R.id.routeBeschrijving)
    Button routeBeschrijving;

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


        mapView.onCreate(savedInstanceState);

        return v;
    }


    /**
     * Users of the mapview class must forward all the life cycle methods from the Activity or Fragment containing this view to the corresponding ones in this class.
     * source : https://developers.google.com/android/reference/com/google/android/gms/maps/MapView
     */

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    @Override
    public final void onResume(){
        super.onResume();
        mapView.onResume();
    }
    @Override
    public final void onPause(){
        super.onPause();
        mapView.onPause();

    }
    @Override
    public final void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        outState.putParcelable(KEY_RESTO,resto);
    }
    @Override
    public final void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public final void onStop(){
        super.onStop();

    }

    @Override
    public void onStart(){
        super.onStart();

        Bundle args = getArguments();
        if(args != null && args.containsKey("resto")){
        setContent((Restaurant)args.getParcelable("resto"));
            Log.i(TAG,"resto has: " + resto.getNaam() + "\nwith location: " + resto.getLocation() + "\nand description: " + resto.getOmschrijving());
        }
    }



    public void setContent(Restaurant resto){
        mapView.getMapAsync(this);
        this.resto = resto;

        naam.setText(resto.getNaam());
        Glide.with(getContext())
                .load(resto.getFoto())
                .centerCrop()
                .into(foto);
        adressResto.setText(resto.getLocation());
        btnTel.setText(getString(R.string.bel)+ " " + formatTelefoonNr(resto.getTelefoonNr()));
        omschrijving.setText(resto.getOmschrijving());


    }

    public String formatTelefoonNr(String tel){
        String nr;
        if (tel.length() == 9){
            nr = String.format("%s / %s.%s.%s",tel.substring(0,3),tel.substring(3,5),tel.substring(5,7),tel.substring(7,9));
        } else if(tel.length() == 10){
            nr = String.format("%s / %s.%s.%s", tel.substring(0,4), tel.substring(4,6), tel.substring(6,8),tel.substring(8,10));
        }else{
            nr = tel;
        }

        return nr;
    }

    @Override
    public void onMapReady(GoogleMap map){
        googleMap = map;

            latLng = getLocationFromAddress(resto.getLocation());
        try {
            Log.i(TAG, "latlng = " + latLng);
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(resto.getNaam()));

            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setMapToolbarEnabled(false);
            MapsInitializer.initialize(this.getActivity());
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(latLng);
            LatLngBounds bounds = builder.build();
            int padding = 0;
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            googleMap.moveCamera(cameraUpdate);
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        } catch (Exception e) {
            Toast.makeText(getContext(),R.string.google_maps_error,Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Geeft de coördinaten van een string terug
     * @param strAddress kan zijn: adres, naam belangrijk gebouw,stad, ...
     * @return
     */
    public LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(getContext());
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);

            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @OnClick(R.id.tel)
    public void belRestaurent(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+resto.getTelefoonNr()));
        startActivity(intent);




    }

    @OnClick(R.id.routeBeschrijving)
    public void startRoute(){
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/dir/?api=1&destination="+resto.getLocation().replace(" ","+")));
        startActivity(intent);
    }




}
