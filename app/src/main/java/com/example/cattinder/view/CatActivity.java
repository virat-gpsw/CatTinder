package com.example.cattinder.view;

import com.example.cattinder.R;
import com.example.cattinder.api.CatService;
import com.example.cattinder.data.Cat;
import com.example.cattinder.data.GoogleCatDownloader;
import com.example.cattinder.data.GoogleSearchPaginator;
import com.example.cattinder.data.ICatDataSource;
import com.example.cattinder.logic.CatPresenter;
import com.example.cattinder.logic.ICatPresenter;
import com.example.cattinder.rx.SchedulerFactory;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RestAdapter;

public class CatActivity extends Activity implements ICatView {

    // INSTANCE FIELDS
    private ICatPresenter mCatPresenter;
    private BaseAdapter mCatAdapter;
    private List<Cat> mCats;

    // UI VIEWS
    @Bind(R.id.kittyStack) SwipeFlingAdapterView mFlingContainer;
    @Bind(R.id.yesButton) Button mYesButton;
    @Bind(R.id.noButton) Button mNoButton;

    /**
     * In lieu of a DI framework, we "inject" our dependencies here.
     */
    protected void inject() {
        // Model-related fields
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(CatService.BASE_URL)
                .build();

        ICatDataSource catDataSource = new GoogleCatDownloader(restAdapter.create(CatService.class), //
                                                               new GoogleSearchPaginator() //
        );

        // Presenter-related field
        mCatPresenter = new CatPresenter(this, catDataSource, new SchedulerFactory());

        // View-related fields
        mCats = new ArrayList<>();
        mCatAdapter = new CatAdapter(LayoutInflater.from(this), Picasso.with(this));
        mFlingContainer.setAdapter(mCatAdapter);
        mFlingContainer.setFlingListener(new CatFlingListener());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inject();

        // Wire up some UI widgets
        mYesButton.setOnClickListener(v -> mFlingContainer.getTopCardListener().selectRight());
        mNoButton.setOnClickListener(v -> mFlingContainer.getTopCardListener().selectLeft());

        // Load some Cats!
        mCatPresenter.loadCats();
    }

    @Override
    public void showCats(List<Cat> cats) {
        // Show some Cats!
        mCats.addAll(cats);
        mCatAdapter.notifyDataSetChanged();
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////// CAT ADAPTER ///////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    private class CatAdapter extends BaseAdapter {
        private final LayoutInflater mLayoutInflater;
        private final Picasso mPicasso;

        public CatAdapter(LayoutInflater inflater, Picasso picasso) {
            mLayoutInflater = inflater;
            mPicasso = picasso;
        }

        @Override
        public int getCount() {
            return mCats.size();
        }

        @Override
        public Cat getItem(int index) {
            return mCats.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.kitty_cat, viewGroup, false);
            }

            // Get the current Cat
            Cat cat = mCats.get(position);

            // Set the Cat's image
            ImageView imageView = (ImageView)convertView.findViewById(R.id.image);
            mPicasso.load(cat.link)
                    .resize(290, 290)
                    .centerCrop()
                    .into(imageView);

            // Set the Cat's snippet
            TextView snippet = (TextView)convertView.findViewById(R.id.snippet);
            snippet.setText(cat.snippet);

            return convertView;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////// FLING EVENTS LISTENER ////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    private class CatFlingListener implements SwipeFlingAdapterView.onFlingListener{

        @Override
        public void removeFirstObjectInAdapter() {
            mCats.remove(0);
            mCatAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLeftCardExit(Object o) {
            // Do nothing
        }

        @Override
        public void onRightCardExit(Object o) {
            // Do nothing
        }

        @Override
        public void onAdapterAboutToEmpty(int itemsInAdapter) {
            if(itemsInAdapter == 1) {
                mCatPresenter.loadCats();
            }
        }

        @Override
        public void onScroll(float scrollProgressPercent) {
            View view = mFlingContainer.getSelectedView();
            view.findViewById(R.id.no_toast).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
            view.findViewById(R.id.yes_toast).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
        }
    }
}
