package com.miguelarc.book_store_app.network;

import androidx.lifecycle.MutableLiveData;

import com.miguelarc.book_store_app.network.responsemodels.BookListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookStoreRepository {

    private static BookStoreRepository bookStoreRepository;
    private BookStoreApi bookStoreApi;

    BookStoreRepository() {
        this.bookStoreApi = RetrofitClient.initBookStoreApi();
    }

    public static BookStoreRepository getInstance() {
        if (bookStoreRepository == null) {
            bookStoreRepository = new BookStoreRepository();
        }
        return bookStoreRepository;
    }

    public MutableLiveData<BookListResponse> getBookList (String searchTerm, int maxResults, int startIndex) {
        final MutableLiveData<BookListResponse> bookListResponse = new MutableLiveData<>();
        bookStoreApi.getBookList(searchTerm, maxResults, startIndex).enqueue(new Callback<BookListResponse>() {
            @Override
            public void onResponse(Call<BookListResponse> call, Response<BookListResponse> response) {
                if (response.isSuccessful()) {
                    bookListResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BookListResponse> call, Throwable t) {
                bookListResponse.setValue(null);
            }
        });
        return bookListResponse;
    }
}
