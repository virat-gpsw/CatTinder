package com.example.cattinder.logic;

import com.example.cattinder.data.ICatDataSource;
import com.example.cattinder.rx.SchedulerFactory;
import com.example.cattinder.view.ICatView;
public class CatPresenter implements ICatPresenter {

    private ICatView catView;
    private ICatDataSource catDataSource;
    private SchedulerFactory schedulerFactory;

    public CatPresenter(ICatView catView, ICatDataSource catDataSource, SchedulerFactory schedulerFactory) {
        this.catView = catView;
        this.catDataSource = catDataSource;
        this.schedulerFactory = schedulerFactory;
    }

    @Override
    public void loadCats() {
        catDataSource.getCats()
                .subscribeOn(schedulerFactory.newThread())
                .observeOn(schedulerFactory.mainThread())
                .subscribe(catView::showCats);
    }
}
