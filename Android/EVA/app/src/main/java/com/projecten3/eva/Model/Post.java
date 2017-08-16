package com.projecten3.eva.Model;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Post{

    private String image;
    private Date posted;
    //private User poster;
    private int likes;
    private boolean is_public;

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public Date getPosted() { return posted; }

    public void setPosted(Date posted) { this.posted = posted; }

    public int getLikes() { return likes; }

    public void setLikes(int likes) { this.likes = likes; }

    public boolean is_public() { return is_public; }

    public void setIs_public(boolean is_public) { this.is_public = is_public; }

    public Post(String image, Date posted, int likes, boolean is_public) {
        this.image = image;
        this.posted = posted;
        this.likes = likes;
        this.is_public = is_public;
    }

    public void addLike(){
        setLikes(getLikes()+1);
    }
}
