package com.example.cattinder.data;

import java.util.List;

import rx.Observable;

public interface ICatDataInteractor {

    Observable<List<Cat>> getCats();

}
