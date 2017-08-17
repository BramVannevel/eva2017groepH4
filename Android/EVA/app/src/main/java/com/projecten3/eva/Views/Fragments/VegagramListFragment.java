package com.projecten3.eva.Views.Fragments;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;

import com.projecten3.eva.Adapters.VegagramAdapter;
import com.projecten3.eva.Data.ApiService;
import com.projecten3.eva.Data.EvaApiBuilder;
import com.projecten3.eva.Model.Post;
import com.projecten3.eva.Model.Posts;
import com.projecten3.eva.R;
import com.projecten3.eva.Views.VegagramActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class VegagramListFragment extends Fragment {

    public static final String TAG = "VegagramListFragment";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PERMISSION_REQUEST = 200;
    private CompositeDisposable compositeDisposableAll = new CompositeDisposable();
    private CompositeDisposable compositeDisposableUser = new CompositeDisposable();

    private ArrayList<Post> allPosts;
    private ArrayList<Post> userPosts;
    private VegagramAdapter adapter;
    protected RecyclerView.LayoutManager llm;

    @BindView(R.id.rvVegagram)
    public RecyclerView rv;

    @BindView(R.id.take_picture)
    FloatingActionButton photo_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vegagram_list,container,false);
        setHasOptionsMenu(true);

        ButterKnife.bind(this,v);
        allPosts = new ArrayList<>();
        userPosts = new ArrayList<>();

        getAllPosts();


        photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkCameraPermission()){
                    takePicture();
                }
            }
        });

        //mockData();
        adapter = new VegagramAdapter(allPosts,getContext());
        rv.setAdapter(adapter);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.vegagram_list_filter_menu, menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch((item.getItemId())){
            case R.id.public_photos:
                Log.i(TAG,"clicked on public");
                updateUI(true);
                return true;
            case R.id.private_photos:
                Log.i(TAG,"clicked on private");
                if(userPosts.size() == 0){
                    getUserPosts();
                }else{
                    updateUI(false);
                }
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void mockData(){
        Post post1 = new Post(null,new Date(), 100, false);
        Post post2 = new Post(null,new Date(), 110, false);
        Post post3 = new Post(null,new Date(), 20, false);
        Post post4 = new Post(null,new Date(), 1692, false);
        Post post5 = new Post(null,new Date(), 2, false);

        allPosts.add(post1);
        allPosts.add(post2);
        allPosts.add(post3);
        allPosts.add(post4);
        allPosts.add(post5);
    }

    public void takePicture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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

    private boolean checkCameraPermission(){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_REQUEST:  {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                   return;
                }
            }
        }
    }

    public void updateUI(boolean showAll){
        if(showAll){
            adapter.setPosts(allPosts);
        }else{
            adapter.setPosts(userPosts);
        }
        adapter.notifyDataSetChanged();
    }

    public void retrieveImages(){

    }

    private void getAllPosts() {
        compositeDisposableAll.add(getObservablePosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Posts>() {
                    @Override
                    public void onNext(Posts value) {
                        Log.i("onNext","retrieved 200 status");
                        allPosts = value.getPosts();
                        Log.i(TAG,"wow");
                        updateUI(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError","with error: \n");
                        e.printStackTrace();
                        //Toast.makeText(getBaseContext(),R.string.error,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.i("onComplete","Completed the call");
                    }
                }));
    }

    /**
     * an observable get request that only launches when it has a subscriber that needs it.
     * .defer docs: http://reactivex.io/documentation/operators/defer.html
     * this creates a new observable for every subscriber, and is easily cancable on screen rotation, back press, ...
     * @return
     */
    private Observable<Posts> getObservablePosts() {
        return Observable.defer(new Callable<ObservableSource<? extends Posts>>() {
            @Override
            public ObservableSource<? extends Posts> call() {
                ApiService service = EvaApiBuilder.getInstance();
                return service.getAllPosts();
            }
        });
    }

    private void getUserPosts() {
        compositeDisposableUser.add(getObservableUserPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Posts>() {
                    @Override
                    public void onNext(Posts value) {
                        Log.i("onNext","retrieved 200 status");
                        userPosts = value.getPosts();
                        updateUI(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError","with error: \n");
                        e.printStackTrace();
                        //Toast.makeText(getBaseContext(),R.string.error,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.i("onComplete","Completed the call");
                    }
                }));
    }

    /**
     * an observable get request that only launches when it has a subscriber that needs it.
     * .defer docs: http://reactivex.io/documentation/operators/defer.html
     * this creates a new observable for every subscriber, and is easily cancable on screen rotation, back press, ...
     * @return
     */
    private Observable<Posts> getObservableUserPosts() {
        return Observable.defer(new Callable<ObservableSource<? extends Posts>>() {
            @Override
            public ObservableSource<? extends Posts> call() {
                ApiService service = EvaApiBuilder.getInstance();
                return service.getUserPosts();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(!compositeDisposableAll.isDisposed()){
            compositeDisposableAll.dispose();
        }
    }
}
