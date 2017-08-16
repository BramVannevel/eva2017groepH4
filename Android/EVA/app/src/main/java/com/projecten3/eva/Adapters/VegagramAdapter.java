package com.projecten3.eva.Adapters;

import android.content.Context;
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
import com.projecten3.eva.Model.Post;
import com.projecten3.eva.Model.Restaurant;
import com.projecten3.eva.R;
import com.projecten3.eva.Views.Fragments.VegagramListFragment;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.FacebookSdk.getClientToken;

public class VegagramAdapter extends RecyclerView.Adapter<VegagramAdapter.VegagramViewHolder>{

    private Context context;
    private List<Post> posts;

    public VegagramAdapter(List<Post> posts, Context context){
        this.context = context;
        this.posts = posts;
    }

    @Override
    public VegagramViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_vegagram,viewGroup,false);

        return new VegagramViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VegagramViewHolder holder, final int position) {
        /*Glide.with(context)
                .load(restos.get(i).getFoto())
                .centerCrop()
                .into(restoViewHolder.imvResto);*/
        //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

        String formatted_date = (String) android.text.format.DateFormat.format("yyyy-MM-dd HH:mm", posts.get(position).getPosted());

        holder.date_posted.setText(formatted_date);
        holder.likes.setText(String.valueOf(posts.get(position).getLikes()));
        holder.photo_menu_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.photo_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
                popup.show();
            }
        });

        holder.like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posts.get(position).addLike();
                notifyDataSetChanged();
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
        posts.get(position).addLike();
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

                case R.id.item_share_facebook:
                    //share code
                    return true;
                case R.id.item_share_vegagram:
                    //share code
                    return true;
                case R.id.delete_photo:

                    return true;
                default:
            }
            return false;
        }
    }
}


