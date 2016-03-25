package com.example.cattinder.data;

import com.example.cattinder.data.CatServiceResponse.Cat;

import java.util.List;

import rx.Observable;

public interface ICatDataSource{

    Observable<List<Cat>> getCats();

    void catLiked(Cat cat);

}
