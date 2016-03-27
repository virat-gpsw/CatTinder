package com.example.cattinder.OLD;

import com.example.cattinder.data.Cat;
import com.example.cattinder.util.Logger;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.List;

public class CatFlingListener implements SwipeFlingAdapterView.onFlingListener {

    private Context context;
    private BaseAdapter adapter;
    private List<Cat> cats;

    public CatFlingListener(Context context, BaseAdapter adapter, List<Cat> cats) {
        this.context = context;
        this.adapter = adapter;
        this.cats = cats;
    }

    @Override
    public void removeFirstObjectInAdapter() {
        cats.remove(0);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLeftCardExit(Object o) {
        Toast.makeText(context, "Disliked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRightCardExit(Object o) {
        Toast.makeText(context, "Liked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdapterAboutToEmpty(int i) {
        Logger.debug("onAdapterAboutToEmpty");
        // TODO - download more cats and add them to view adapter
    }

    @Override
    public void onScroll(float v) {

    }
}
