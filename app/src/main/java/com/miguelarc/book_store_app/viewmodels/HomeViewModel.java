package com.miguelarc.book_store_app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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

    private Observer<BookListResponse> bookListResponseObserver = new Observer<BookListResponse>() {
        @Override
        public void onChanged(BookListResponse bookListResponse) {
            bookList.addAll(bookListResponse.getItems());
        }
    };

    public LiveData<BookListResponse> loadBookList() {
        if (bookListResponse == null) {
            bookListResponse = new MutableLiveData<>();
        }
            BookStoreRepository bookStoreRepository = BookStoreRepository.getInstance();
            bookListResponse = bookStoreRepository.getBookList(SEARCH_TERM, PAGE_SIZE, bookList.size());
            bookListResponse.observeForever(bookListResponseObserver);
        return bookListResponse;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        bookListResponse.removeObserver(bookListResponseObserver);
    }
}