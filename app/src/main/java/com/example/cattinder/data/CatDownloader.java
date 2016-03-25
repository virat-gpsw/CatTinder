package com.example.cattinder.data;

import com.example.cattinder.api.CatService;
import com.example.cattinder.data.CatServiceResponse.Cat;
import com.example.cattinder.rx.SchedulerFactory;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class CatDownloader implements ICatDataSource {

    private final CatService catService;
    private CatPaginator catPaginator;

    public CatDownloader(CatService catService, CatPaginator catPaginator) {
        this.catService = catService;
        this.catPaginator = catPaginator;
    }

    @Override
    public Observable<List<Cat>> getCats() {
        return downloadCats().flatMap(response -> Observable.just(response.getCats()));
    }

    @Override
    public void catLiked(Cat cat) {
        // If we had a database, this would be the function to persist Cat Likes
    }

    private Observable<CatServiceResponse> downloadCats() {
        return catService.getCats(catPaginator.getPage());
    }
}
