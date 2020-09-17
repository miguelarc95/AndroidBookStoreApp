package com.miguelarc.book_store_app.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/";


    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static BookStoreApi initBookStoreApi () {
        return retrofit.create(BookStoreApi.class);
    }
}
