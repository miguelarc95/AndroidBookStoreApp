package com.miguelarc.book_store_app.viewmodels;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.miguelarc.book_store_app.database.FavoriteBooksRepository;
import com.miguelarc.book_store_app.models.Book;
import com.miguelarc.book_store_app.network.BookStoreRepository;
import com.miguelarc.book_store_app.network.responsemodels.BookListResponse;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private static final int PAGE_SIZE = 20;
    private MutableLiveData<BookListResponse> bookListResponse;
    private List<Book> bookList = new ArrayList<>();
    BookStoreRepository bookStoreRepository;
    FavoriteBooksRepository favoriteBooksRepository;

    private static final String SEARCH_TERM = "android";

    private Observer<BookListResponse> bookListResponseObserver = new Observer<BookListResponse>() {
        @Override
        public void onChanged(BookListResponse bookListResponse) {
            bookList.addAll(bookListResponse.getItems());
        }
    };

    public HomeViewModel(@NonNull Application application) {
        super(application);
        bookStoreRepository = BookStoreRepository.getInstance();
        favoriteBooksRepository = FavoriteBooksRepository.getInstance(application);
    }

    public LiveData<BookListResponse> loadBookList() {
        if (bookListResponse == null) {
            bookListResponse = new MutableLiveData<>();
        }
        bookListResponse = bookStoreRepository.getBookList(SEARCH_TERM, PAGE_SIZE, bookList.size());
        bookListResponse.observeForever(bookListResponseObserver);
        return bookListResponse;
    }

    public LiveData<List<Book>> loadFavoriteBooks() {
        return favoriteBooksRepository.getFavoriteBooks();
    }

    public List<Book> getBookList() {
        return bookList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        bookListResponse.removeObserver(bookListResponseObserver);
    }

    /**
     * isNetworkAvailable() - Method used to see if the device is online.
     * @param context
     * @return - true if device is online, false otherwise.
     */
    public Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }
}