package com.example.cattinder.view;

import com.example.cattinder.data.CatServiceResponse.Cat;

import java.util.List;
public interface ICatView{

    void showCats(List<Cat> cats);

    void catLiked(Cat cat);

}
