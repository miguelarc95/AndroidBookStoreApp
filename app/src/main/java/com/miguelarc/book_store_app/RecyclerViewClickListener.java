package com.miguelarc.book_store_app;

import android.view.View;

import com.miguelarc.book_store_app.models.Book;

public interface RecyclerViewClickListener {
    void onItemClicked(Book book);
}
