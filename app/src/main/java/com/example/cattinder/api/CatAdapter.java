package com.example.cattinder.api;

import com.example.cattinder.data.CatServiceResponse;
import com.example.cattinder.data.CatServiceResponse.Cat;
import com.example.cattinder.rx.SchedulerFactory;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
public class CatAdapter {

    private CatService mCatService;
    private SchedulerFactory mSchedulerFactory;

    public CatAdapter(CatService catService, SchedulerFactory schedulerFactory) {
        mCatService = catService;
        mSchedulerFactory = schedulerFactory;
    }

    public Observable<List<Cat>> getCats(int startIndex) {
        return Observable.create(new Observable.OnSubscribe<List<Cat>>(){
            @Override
            public void call(Subscriber<? super List<Cat>> subscriber) {
                CatServiceResponse response = mCatService.getCats(startIndex);

                subscriber.onNext(response.getCats());
                subscriber.onCompleted();
            }
        }).subscribeOn(mSchedulerFactory.newThread());
    }
}
