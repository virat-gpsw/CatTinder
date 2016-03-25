package com.example.cattinder.adapter;

import com.example.cattinder.R;
import com.example.cattinder.data.CatServiceResponse.Cat;
import com.example.cattinder.util.Logger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
public class CatAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final List<Cat> cats;

    public CatAdapter(List<Cat> cats, LayoutInflater inflater) {
        this.cats = cats;
        this.inflater = inflater;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        // TODO - set up the card view here
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.kitty_cat, viewGroup, false);
        }

        Logger.debug("Cats size: " + cats.size());

        return convertView;
    }

    public boolean addCats(List<Cat> moreCats) {
        return cats.addAll(moreCats);
    }

    public List<Cat> getCats() {
        return cats;
    }

    public boolean removeCat(Cat cat) {
        return cats.remove(cat);
    }
}
