package com.projecten3.eva.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.projecten3.eva.Data.EvaApiBuilder;
import com.projecten3.eva.Model.Post;
import com.projecten3.eva.Model.Restaurant;
import com.projecten3.eva.R;
import com.projecten3.eva.Views.Fragments.VegagramListFragment;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.FacebookSdk.getClientToken;

public class VegagramAdapter extends RecyclerView.Adapter<VegagramAdapter.VegagramViewHolder>{

    private Context context;
    private List<Post> posts;
    private boolean prPosts;
    private static String link = "https://evabeheer.herokuapp.com/vegagram/uploads/";

    public VegagramAdapter(List<Post> posts, Context context, boolean prPosts){
        this.context = context;
        this.posts = posts;
        this.prPosts = prPosts;
    }

    public void setPosts(ArrayList<Post> posts){
        this.posts = posts;
    }

    public void setPrPosts(boolean pr){ this.prPosts = pr;}

    @Override
    public VegagramViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_vegagram,viewGroup,false);

        return new VegagramViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VegagramViewHolder holder, final int position) {
        Glide.with(context)
                .load(link + posts.get(position).getImageName())
                .centerCrop()
                .into(holder.photograph);

        String formatted_date = (String) android.text.format.DateFormat.format("yyyy-MM-dd HH:mm", posts.get(position).getPosted());

        holder.date_posted.setText(formatted_date);
        holder.likes.setText(String.valueOf(getPost(position).getLikes()));
        if(prPosts) {
            holder.photo_menu_button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.photo_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
                    popup.show();
                }
            });
        }
        holder.like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likePost(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public Post getPost(int index){
        return posts.get(index);
    }

    public void remove(int index){
        posts.remove(index);
        notifyDataSetChanged();
    }

    public void likePost(int position){
        getPost(position).addLike();
        notifyDataSetChanged();
    }

    public static class VegagramViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_poster)
        TextView name_poster;
        @BindView(R.id.date_posted)
        TextView date_posted;
        @BindView(R.id.photograph)
        ImageView photograph;
        @BindView(R.id.likes)
        TextView likes;
        @BindView(R.id.popup_menu_photo)
        ImageButton photo_menu_button;
        @BindView(R.id.likes_button)
        ImageView like_button;

        public VegagramViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int position;
        public MyMenuItemClickListener(int positon) {
            this.position=positon;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.delete_photo:
                    Call<ResponseBody> call = EvaApiBuilder.getInstance().deleteVegagramPost(posts.get(position).getId());

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                posts.remove(position);
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                    remove(position);
                    return true;
                default:
            }
            return false;
        }
    }
}


