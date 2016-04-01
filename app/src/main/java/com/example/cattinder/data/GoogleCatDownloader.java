package com.example.cattinder.data;

import com.example.cattinder.api.CatService;
import com.example.cattinder.data.CatServiceResponse.CatObj;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class GoogleCatDownloader implements ICatDataInteractor{

    private CatService mCatService;
    private GoogleSearchPaginator mPaginator;

    public GoogleCatDownloader(CatService catService, GoogleSearchPaginator paginator) {
        mCatService = catService;
        mPaginator = paginator;
    }

    @Override
    public Observable<List<Cat>> getCats() {
        return downloadCats().flatMap(response -> {

            /**
             * Convert List<CatObj> to List<Cat>
             */
            List<Cat> cats = new ArrayList<>();
            for(CatObj catObj : response.getCats()) {
                cats.add(new Cat(catObj.getImageUri(), catObj.getDescription()));
            }

            return Observable.just(cats);
        });
    }

    private Observable<CatServiceResponse> downloadCats() {
        return mCatService.getCats(mPaginator.getPageAndIncrement());
    }
}
