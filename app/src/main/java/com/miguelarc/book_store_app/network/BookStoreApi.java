package com.miguelarc.book_store_app.network;

import com.miguelarc.book_store_app.network.responsemodels.BookListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookStoreApi {

    @GET("volumes")
    Call<BookListResponse> getBookList(@Query("q") String searchTerm,
                                       @Query("maxResults") int maxResults,
                                       @Query("startIndex") int startIndex);
}
