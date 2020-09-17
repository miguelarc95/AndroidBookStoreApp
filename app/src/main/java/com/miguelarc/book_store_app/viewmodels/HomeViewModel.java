package com.miguelarc.book_store_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.miguelarc.book_store_app.network.BookStoreRepository;
import com.miguelarc.book_store_app.network.responsemodels.BookListResponse;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<Error> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<BookListResponse> bookListResponse;

    private static final String SEARCH_TERM = "android";

    public LiveData<BookListResponse> getBookList(int maxResults, int startIndex) {
        if (bookListResponse == null) {
            bookListResponse = new MutableLiveData<>();
        }
            BookStoreRepository bookStoreRepository = BookStoreRepository.getInstance();
            bookListResponse = bookStoreRepository.getBookList(SEARCH_TERM, maxResults, startIndex);
        return bookListResponse;
    }

}