package com.projecten3.eva.Views.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projecten3.eva.Interfaces.OnFragmentSwitch;
import com.projecten3.eva.Model.Adres;
import com.projecten3.eva.R;
import com.projecten3.eva.Model.Restaurant;
import com.projecten3.eva.Adapters.RestoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestoListFragment extends Fragment {

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

        mockData();
        adapter = new RestoAdapter(restos,getContext());
        rv.setAdapter(adapter);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        return v;
    }

    private void mockData(){
        //voorlopige mock data tot we API hebben
        Restaurant r1 = new Restaurant("1","Gouden fazant","123456", new Adres("werkstraat","9","Gent","9000"));
        Restaurant r2 = new Restaurant("1","Gouden fazant","123456", new Adres("werkstraat","9","Gent","9000"));
         restos.add(r1);
        restos.add(r2);
        Log.e(TAG,restos.get(0).getNaam());
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
