package com.example.cattinder.data;

public class GoogleSearchPaginator{

    private int mPage;

    public GoogleSearchPaginator() {
        mPage = 1; // pagination starts at index 1
    }

    public int getPageAndIncrement() {
        return mPage += 10;
    }
}
