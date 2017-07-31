package com.projecten3.eva;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestoListFragment extends ListFragment {

    public static final String TAG = "RestoListFragment";

    @BindView(R.id.rvResto)
    public RecyclerView rv;

    protected RecyclerView.LayoutManager llm;
    private RestoAdapter adapter;
    private int currentIndex;

    public static MainOnClickListener mainOnClickListener;

    private List<Restaurant> restos;


    public RestoListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        mainOnClickListener = new MainOnClickListener(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resto_list,container,false);

        ButterKnife.bind(this,v);
        restos = new ArrayList<>();

        //voorlopige mock data tot we API hebben
        Restaurant r1 = new Restaurant("de gouden fazant",R.drawable.testafbvegan,"","0499280469","aambeeldstraat 10 amsterdam");
        Restaurant r2 = new Restaurant("de zilveren wortel",R.drawable.testafbvegan2,"","053803317","hoofdstraat 15 heijen");
        r1.setOmschrijving("de gouden fazant is gelegen in een rustige, landelijke omgeving en biedt tal van verschillende gerechten.");
        r2.setOmschrijving("in de zilveren wortel bereiden wij enkel veganistische gerechten, het is hier meestal niet druk");
        restos.add(r1);
        restos.add(r2);
        Log.e(TAG,restos.get(0).getNaam());
        adapter = new RestoAdapter(restos,getContext());
        rv.setAdapter(adapter);



        return v;
    }


    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){

        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
    }

    private class MainOnClickListener implements View.OnClickListener {
        private final Context context;

        public MainOnClickListener(Context context){
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            currentIndex = rv.getChildAdapterPosition(v);

            Log.e(TAG, "clicked on : " + currentIndex);
            Restaurant rest = adapter.getResto(currentIndex);
            Log.e(TAG, rest.toString());

            OnFragmentSwitch listener = (OnFragmentSwitch) getActivity();
            listener.onSelectionChanged(restos.get(currentIndex));


        }


    }

}
