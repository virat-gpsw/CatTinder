package com.example.cattinder.data;

public class CatPaginator {

    private int page;

    public CatPaginator() {
        page = 1; // pages start at index 1
    }

    public int getPageAndIncrement() {
        return page++;
    }
}
