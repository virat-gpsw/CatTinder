package com.example.cattinder.OLD;

import com.example.cattinder.R;
import com.example.cattinder.data.Cat;
import com.example.cattinder.presenter.ICatPresenter;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.List;

public class CatFlingListener implements SwipeFlingAdapterView.onFlingListener {

    private Context context;
    private SwipeFlingAdapterView flingContainer;
    private BaseAdapter adapter;
    private List<Cat> cats;
    private final ICatPresenter catPresenter;

    public CatFlingListener(Context context, ICatPresenter catPresenter, SwipeFlingAdapterView flingContainer, BaseAdapter adapter, List<Cat> cats) {
        this.context = context;
        this.catPresenter = catPresenter;
        this.flingContainer = flingContainer;
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
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        if (itemsInAdapter == 1) {
            catPresenter.loadCats();
        }
    }

    @Override
    public void onScroll(float scrollProgressPercent) {
        View view = flingContainer.getSelectedView();
        view.findViewById(R.id.no_toast).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
        view.findViewById(R.id.yes_toast).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
    }
}
