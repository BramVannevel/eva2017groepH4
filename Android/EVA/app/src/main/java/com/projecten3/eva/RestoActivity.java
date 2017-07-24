package com.projecten3.eva;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class RestoActivity extends AppCompatActivity implements OnFragmentSwitch {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto);


            //Als de activity de framelayout gebruikt -> portrait mode, dus eerste fragment(restoLijst) toevoegen aan de layout
            if (findViewById(R.id.fragment_container) != null){

                if(savedInstanceState != null){
                    return;
                }



                RestoListFragment restoListFrag = new RestoListFragment();
                restoListFrag.setArguments(getIntent().getExtras());

                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, restoListFrag).commit();
            }



    }


    @Override
    public void onSelectionChanged(Restaurant resto){
        RestoInfoFragment restoInfoFragment = (RestoInfoFragment) getSupportFragmentManager().findFragmentById(R.id.infoFrag);


        //Als het infoFragment al bestaat -> content updaten
        if(restoInfoFragment != null && restoInfoFragment.isAdded()){
            restoInfoFragment.setContent(resto);
        // Anders, infoFragment aanmaken met content en vervangen met bestaande lijstFragment
        }else {
            RestoInfoFragment newRestoInfoFragment = new RestoInfoFragment();
            Bundle args = new Bundle();

            args.putParcelable("resto",resto);
            newRestoInfoFragment.setArguments(args);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


            fragmentTransaction.replace(R.id.fragment_container,newRestoInfoFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
