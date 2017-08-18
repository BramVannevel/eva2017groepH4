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
import android.widget.Toast;

import com.projecten3.eva.Data.ApiService;
import com.projecten3.eva.Data.EvaApiBuilder;
import com.projecten3.eva.Helpers.FilterChallengesForToday;
import com.projecten3.eva.Interfaces.OnFragmentSwitch;
import com.projecten3.eva.Model.Adres;
import com.projecten3.eva.Model.Challenges;
import com.projecten3.eva.Model.Restaurants;
import com.projecten3.eva.R;
import com.projecten3.eva.Model.Restaurant;
import com.projecten3.eva.Adapters.RestoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestoListFragment extends Fragment {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Unbinder unbinder;

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

        unbinder = ButterKnife.bind(this,v);
        restos = new ArrayList<>();

        //mockData();
        getRestaurants();
        initUI();
        return v;
    }

    private void initUI(){
        adapter = new RestoAdapter(restos,getContext());
        rv.setAdapter(adapter);
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

    private void getRestaurants() {
        compositeDisposable.add(getObservableRestaurants()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Restaurants>() {
                    @Override
                    public void onNext(Restaurants value) {
                        Log.i("onNext","retrieved 200 status");
                            restos.addAll(value.getRestaurants());
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError","with error: \n");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.i("onComplete","Completed the call");
                        for (Restaurant r : restos){
                            Log.e("restoo", r.getNaam());

                        }
                    }
                }));
    }

    private Observable<Restaurants> getObservableRestaurants() {
        return Observable.defer(new Callable<ObservableSource<? extends Restaurants>>() {
            @Override
            public ObservableSource<? extends Restaurants> call() {
                ApiService service = EvaApiBuilder.getInstance();
                return service.getRestaurants();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
        if(unbinder != null)
            unbinder.unbind();
    }

}
