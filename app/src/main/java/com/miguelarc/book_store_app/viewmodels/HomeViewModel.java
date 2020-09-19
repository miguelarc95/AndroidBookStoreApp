package com.miguelarc.book_store_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.miguelarc.book_store_app.models.Book;
import com.miguelarc.book_store_app.network.BookStoreRepository;
import com.miguelarc.book_store_app.network.responsemodels.BookListResponse;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private static final int PAGE_SIZE = 20;
    private MutableLiveData<BookListResponse> bookListResponse;
    private List<Book> bookList = new ArrayList<>();

    private static final String SEARCH_TERM = "android";

    public LiveData<BookListResponse> getInitialBookList() {
        if (bookListResponse == null) {
            bookListResponse = new MutableLiveData<>();
        }
            BookStoreRepository bookStoreRepository = BookStoreRepository.getInstance();
            bookListResponse = bookStoreRepository.getBookList(SEARCH_TERM, PAGE_SIZE, 0);
        return bookListResponse;
    }

    public LiveData<BookListResponse> getNextBookList() {
        if (bookListResponse == null) {
            bookListResponse = new MutableLiveData<>();
        }
        BookStoreRepository bookStoreRepository = BookStoreRepository.getInstance();
        bookListResponse = bookStoreRepository.getBookList(SEARCH_TERM, PAGE_SIZE, bookList.size());
        return bookListResponse;
    }

}