package com.example.cattinder.adapter;

import com.example.cattinder.R;
import com.example.cattinder.data.Cat;
import com.example.cattinder.data.CatServiceResponse;
import com.example.cattinder.data.CatServiceResponse.CatObj;
import com.example.cattinder.util.Logger;
import com.squareup.picasso.Picasso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CatAdapter extends BaseAdapter {

    private final List<Cat> cats;
    private final LayoutInflater inflater;
    private final Picasso picasso;

    public CatAdapter(List<Cat> cats, LayoutInflater inflater, Picasso picasso) {
        this.cats = cats;
        this.inflater = inflater;
        this.picasso = picasso;
    }

    @Override
    public int getCount() {
        return cats.size();
    }

    @Override
    public Cat getItem(int index) {
        return cats.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.kitty_cat, viewGroup, false);
        }

        // Get the current Cat
        Cat cat = cats.get(position);

        // Set the Cat's image
        ImageView imageView = (ImageView)convertView.findViewById(R.id.image);
        picasso.load(cat.link)
                .resize(290, 290)
                .centerCrop()
                .into(imageView);

        // Set the Cat's snippet
        TextView snippet = (TextView)convertView.findViewById(R.id.snippet);
        snippet.setText(cat.snippet);

        return convertView;
    }
}