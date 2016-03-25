package com.example.cattinder.presenter;

import com.example.cattinder.data.CatServiceResponse.Cat;
public interface ICatPresenter{

    void loadCats();

    void catLiked(Cat cat);

}
