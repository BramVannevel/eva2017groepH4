package com.projecten3.eva.Views.Fragments;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class VegagramListFragment extends Fragment {

    public static final String TAG = "VegagramListFragment";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PERMISSION_REQUEST = 200;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean currentList = true;

    private ArrayList<Post> allPosts;
    private ArrayList<Post> userPosts;
    private VegagramAdapter adapter;
    protected RecyclerView.LayoutManager llm;

    @BindView(R.id.rvVegagram)
    public RecyclerView rv;

    @BindView(R.id.take_picture)
    FloatingActionButton photo_button;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("listfragment","oncreate called");

        if(savedInstanceState != null && savedInstanceState.containsKey("posts")){
            allPosts = savedInstanceState.getParcelableArrayList("posts");
        }else{
            allPosts = new ArrayList<>();
        }

        if(savedInstanceState != null && savedInstanceState.containsKey("userPosts")){
            allPosts = savedInstanceState.getParcelableArrayList("userPosts");
        }else{
            userPosts = new ArrayList<>();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vegagram_list,container,false);

        setHasOptionsMenu(true);
        ButterKnife.bind(this,v);

        getAllPosts();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItem();
            }
        });

        photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkCameraPermission()){
                    takePicture();
                }
            }
        });

        adapter = new VegagramAdapter(allPosts,getContext(),false);
        rv.setAdapter(adapter);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        return v;
    }

    void refreshItem(){
        if(currentList == true){
            getAllPosts();
        }else{
            getUserPosts();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(allPosts != null && allPosts.size() > 0) {
            outState.putParcelableArrayList("posts", allPosts);
        }
        if(userPosts != null && userPosts.size() > 0) {
            outState.putParcelableArrayList("userPosts", allPosts);
        }
        super.onSaveInstanceState(outState);
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
                currentList = true;
                updateUI(true);
                return true;
            case R.id.private_photos:
                Log.i(TAG,"clicked on private");
                currentList = false;
                if(userPosts.size() == 0){
                    getUserPosts();
                }else{
                    updateUI(false);
                }
                return true;
            default: return super.onOptionsItemSelected(item);
        }
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
            adapter.setPrPosts(false);
        }else{
            adapter.setPosts(userPosts);
            adapter.setPrPosts(true);
        }
        adapter.notifyDataSetChanged();
    }

    private void getAllPosts() {
        compositeDisposable.add(getObservablePosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Posts>() {
                    @Override
                    public void onNext(Posts value) {
                        Log.i("onNext","retrieved 200 status");
                        allPosts = value.getPosts();
                        updateUI(true);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError","with error: \n");
                        e.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
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
        compositeDisposable.add(getObservableUserPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Posts>() {
                    @Override
                    public void onNext(Posts value) {
                        Log.i("onNext","retrieved 200 status");
                        userPosts = value.getPosts();
                        updateUI(false);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError","with error: \n");
                        e.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
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
        if(!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }
}
