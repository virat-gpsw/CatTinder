package com.example.cattinder.data;

import java.util.List;

import rx.Observable;

public interface ICatDataSource {

    Observable<List<Cat>> getCats();

}
