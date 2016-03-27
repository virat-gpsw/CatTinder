package com.example.cattinder.view;

import com.example.cattinder.OLD.CatFlingListener;
import com.example.cattinder.R;
import com.example.cattinder.adapter.CatAdapter;
import com.example.cattinder.api.CatService;
import com.example.cattinder.data.Cat;
import com.example.cattinder.data.CatDownloader;
import com.example.cattinder.data.CatPaginator;
import com.example.cattinder.data.ICatDataSource;
import com.example.cattinder.presenter.CatPresenter;
import com.example.cattinder.presenter.ICatPresenter;
import com.example.cattinder.rx.SchedulerFactory;
import com.example.cattinder.util.Logger;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.squareup.picasso.Picasso;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import retrofit.RestAdapter;

public class CatFragment extends Fragment implements ICatView{

    private ICatPresenter catPresenter;

    // UI FIELDS
    private SwipeFlingAdapterView flingContainer;
    private Button yesButton;
    private Button noButton;

    protected void inject() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CatService.BASE_URL)
                .build();

        ICatDataSource catDataSource = new CatDownloader(restAdapter.create(CatService.class), //
                                                         new CatPaginator() //
        );

        catPresenter = new CatPresenter(this, catDataSource, new SchedulerFactory());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotornot, container, false);

        flingContainer = (SwipeFlingAdapterView)view.findViewById(R.id.kittyStack);
        
        yesButton = (Button)view.findViewById(R.id.yesButton);
        yesButton.setOnClickListener(v -> flingContainer.getTopCardListener().selectRight());

        noButton = (Button)view.findViewById(R.id.noButton);
        noButton.setOnClickListener(v -> flingContainer.getTopCardListener().selectLeft());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inject();

        // Load the initial set of Catz
        catPresenter.loadCats();
    }

    @Override
    public void showCats(List<Cat> cats) {
        Logger.debug("Cats size: " + cats.size());

        CatAdapter adapter = new CatAdapter(cats, LayoutInflater.from(getActivity()), Picasso.with(getActivity()));
        flingContainer.setAdapter(adapter);
        flingContainer.setFlingListener(new CatFlingListener(getActivity(), adapter, cats));

        // Show some Catz!
        adapter.notifyDataSetChanged();
    }
}