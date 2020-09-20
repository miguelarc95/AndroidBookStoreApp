package com.miguelarc.book_store_app.network;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.miguelarc.book_store_app.network.responsemodels.BookListResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import retrofit2.Response;

@RunWith(JUnit4.class)
public class BookStoreRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private BookStoreApi bookStoreApi;
    private BookStoreRepository bookStoreRepository;

    @Before
    public void setUp() throws IOException {
        bookStoreApi = RetrofitClient.initBookStoreApi();
        bookStoreRepository = new BookStoreRepository();

    }

    @Test
    public void testGetBookListRequest() {

        try {
            Response<BookListResponse> response = bookStoreApi.getBookList("android", 20, 0).execute();

            Assert.assertTrue(response.isSuccessful());
            Assert.assertEquals(20,response.body().getItems().size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetBookList_fromRepository() {
        MutableLiveData<BookListResponse> getBookListResponse = bookStoreRepository.getBookList("android", 20, 0);
        Observer<BookListResponse> bookListResponseObserver = new Observer<BookListResponse>() {
            @Override
            public void onChanged(BookListResponse bookListResponse) {
                Assert.assertEquals(20, bookListResponse.getTotalItems());
            }
        };
        getBookListResponse.observeForever(bookListResponseObserver);
    }
}
