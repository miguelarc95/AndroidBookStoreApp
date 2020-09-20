package com.miguelarc.book_store_app.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miguelarc.book_store_app.models.Book;

import java.util.List;

/**
 * Repository that handles all DB insertions, deletions and queries.
 */
public class FavoriteBooksRepository {

    private final BookDao bookDao;
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private MutableLiveData<Integer> removeResult = new MutableLiveData<>();

    private static FavoriteBooksRepository favoriteBooksRepository;

    public FavoriteBooksRepository(Context context) {
        FavoriteBooksDatabase database = FavoriteBooksDatabase.getInstance(context);
        bookDao = database.bookDao();
    }

    public static FavoriteBooksRepository getInstance(Context context) {
        if (favoriteBooksRepository == null) {
            favoriteBooksRepository = new FavoriteBooksRepository(context);
        }
        return favoriteBooksRepository;
    }

    public LiveData<List<Book>> getFavoriteBooks() {
        return bookDao.loadFavoriteBooks();
    }

    public void insert(Book book) {
        insertAsync(book);
    }

    public void remove(Book book) {
        removeAsync(book);
    }

    public MutableLiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public MutableLiveData<Integer> getRemoveResult() {
        return removeResult;
    }

    private void insertAsync(final Book book) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bookDao.insertFavoriteBook(book);
                    insertResult.postValue(1);
                } catch (Exception e) {
                    insertResult.postValue(0);
                }
            }
        }).start();

    }

    private void removeAsync(final Book book) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bookDao.removeFavoriteBook(book);
                    removeResult.postValue(1);
                } catch (Exception e) {
                    removeResult.postValue(0);
                }
            }
        }).start();

    }
}
