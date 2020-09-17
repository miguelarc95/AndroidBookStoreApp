package com.miguelarc.book_store_app.network.responsemodels;

import com.miguelarc.book_store_app.models.Book;

import java.util.List;

public class BookListResponse {

    private String kind;
    private int totalItems;
    private List<Book> items;

    public String getKind() {
        return kind;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public List<Book> getItems() {
        return items;
    }
}
