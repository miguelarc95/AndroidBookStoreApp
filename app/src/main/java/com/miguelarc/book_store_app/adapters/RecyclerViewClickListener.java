package com.miguelarc.book_store_app.adapters;

import android.view.View;

import com.miguelarc.book_store_app.models.Book;

public interface RecyclerViewClickListener {
    void onItemClicked(Book book);
}
