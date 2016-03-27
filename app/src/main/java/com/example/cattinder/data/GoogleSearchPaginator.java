package com.example.cattinder.data;

public class GoogleSearchPaginator{

    private int page;

    public GoogleSearchPaginator() {
        page = 1; // pagination starts at index 1
    }

    public int getPageAndIncrement() {
        return page += 10;
    }
}
