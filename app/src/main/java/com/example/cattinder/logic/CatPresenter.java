package com.example.cattinder.logic;

import com.example.cattinder.data.Cat;
import com.example.cattinder.data.ICatDataInteractor;
import com.example.cattinder.rx.SchedulerFactory;
import com.example.cattinder.view.ICatView;

import java.util.List;

import rx.functions.Action1;
public class CatPresenter implements ICatPresenter {

    private ICatView mCatView;
    private ICatDataInteractor mDataInteractor;
    private SchedulerFactory mSchedulerFactory;

    public CatPresenter(ICatView catView, ICatDataInteractor dataInteractor, SchedulerFactory schedulerFactory) {
        mCatView = catView;
        mDataInteractor = dataInteractor;
        mSchedulerFactory = schedulerFactory;
    }

    @Override
    public void loadCats() {
        mDataInteractor.getCats()
                .subscribeOn(mSchedulerFactory.newThread())
                .observeOn(mSchedulerFactory.mainThread())
                .subscribe(new Action1<List<Cat>>(){
                    @Override
                    public void call(List<Cat> cats) {
                        mCatView.showCats(cats);
                    }
                });
    }
}
