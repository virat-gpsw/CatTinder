package com.example.cattinder.activity;

import com.example.cattinder.R;
import com.example.cattinder.api.CatAdapter;
import com.example.cattinder.api.CatService;
import com.example.cattinder.data.CatServiceResponse;
import com.example.cattinder.data.CatServiceResponse.Cat;
import com.example.cattinder.rx.SchedulerFactory;
import com.example.cattinder.util.Logger;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // TODO: your code here
      RestAdapter restAdapter = new RestAdapter.Builder()
              .setEndpoint(CatService.BASE_URL)
              .build();
      CatService service = restAdapter.create(CatService.class);
      SchedulerFactory schedulerFactory = new SchedulerFactory();
      CatAdapter catAdapter = new CatAdapter(service, schedulerFactory);

      loadCats(catAdapter, schedulerFactory);
  }

    private void loadCats(CatAdapter adapter, SchedulerFactory factory) {
        Observable<List<Cat>> cats = adapter.getCats(1);
        cats.observeOn(factory.mainThread()).subscribe(cats1 -> {
            Logger.debug("MainActivity", cats1.size());
        });
    }
}
