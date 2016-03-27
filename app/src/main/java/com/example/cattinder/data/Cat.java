package com.example.cattinder.data;

import android.net.Uri;
public class Cat{

    public final Uri link;
    public final String snippet;

    public Cat(Uri link, String snippet) {
        this.link = link;
        this.snippet = snippet;
    }
}
