package com.example.cattinder.view;

import com.example.cattinder.R;
import com.example.cattinder.api.CatService;
import com.example.cattinder.data.CatDownloader;
import com.example.cattinder.data.CatPaginator;
import com.example.cattinder.data.CatServiceResponse.Cat;
import com.example.cattinder.data.ICatDataSource;
import com.example.cattinder.presenter.CatPresenter;
import com.example.cattinder.presenter.ICatPresenter;
import com.example.cattinder.rx.SchedulerFactory;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit.RestAdapter;

public class CatFragment extends Fragment implements ICatView{

    private ICatPresenter catPresenter;

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
        return inflater.inflate(R.layout.fragment_hotornot, container, false);
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
        // TODO: 3/25/16 load the cats into the adapter
    }



    @Override
    public void catLiked(Cat cat) {
        catPresenter.catLiked(cat);
    }
}
