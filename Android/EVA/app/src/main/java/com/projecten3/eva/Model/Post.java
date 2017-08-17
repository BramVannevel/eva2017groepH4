package com.projecten3.eva.Model;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Post implements Parcelable{

    private String _id;

    public String getId() {
        return _id;
    }

    private String imageName;
    private Date posted;
    //private User poster;
    private int likes;
    private boolean is_public;

    public String getImageName() { return imageName; }

    public void setImageName(String image) { this.imageName = image; }

    public Date getPosted() { return posted; }

    public void setPosted(Date posted) { this.posted = posted; }

    public int getLikes() { return likes; }

    public void setLikes(int likes) { this.likes = likes; }

    public boolean is_public() { return is_public; }

    public void setIs_public(boolean is_public) { this.is_public = is_public; }

    public Post(String image, Date posted, int likes, boolean is_public) {
        this.imageName = image;
        this.posted = posted;
        this.likes = likes;
        this.is_public = is_public;
    }

    public void addLike(){
        setLikes(getLikes()+1);
    }

    /**
     * Parcalable methods
     */

    public Post(Parcel in){
        _id = in.readString();
        imageName = in.readString();
        posted = new Date(in.readLong());
        likes = in.readInt();
        is_public = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(imageName);
        parcel.writeLong(posted.getTime());
        parcel.writeInt(likes);
        parcel.writeByte((byte) (is_public ? 1 : 0));
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public static Creator<Post> getCREATOR() {
        return CREATOR;
    }
}
