package com.projecten3.eva.Model;

import java.util.ArrayList;

public class Posts {
    private ArrayList<Post> posts;

    public Posts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }
}
