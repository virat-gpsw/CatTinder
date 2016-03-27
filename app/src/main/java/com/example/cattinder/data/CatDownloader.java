package com.example.cattinder.data;

import com.example.cattinder.api.CatService;
import com.example.cattinder.data.CatServiceResponse.CatObj;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class CatDownloader implements ICatDataSource {

    private final CatService catService;
    private GoogleSearchPaginator paginator;

    public CatDownloader(CatService catService, GoogleSearchPaginator paginator) {
        this.catService = catService;
        this.paginator = paginator;
    }

    @Override
    public Observable<List<Cat>> getCats() {
        return downloadCats().flatMap(response -> {

            // Convert List<CatObj> to List<Cat>
            List<Cat> cats = new ArrayList<>();
            for(CatObj catObj : response.getCats()) {
                cats.add(new Cat(catObj.getImageUri(), catObj.getDescription()));
            }

            return Observable.just(cats);
        });
    }

    private Observable<CatServiceResponse> downloadCats() {
        return catService.getCats(paginator.getPageAndIncrement());
    }
}
