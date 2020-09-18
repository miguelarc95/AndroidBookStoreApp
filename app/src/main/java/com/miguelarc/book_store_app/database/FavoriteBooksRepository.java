package com.miguelarc.book_store_app.database;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.miguelarc.book_store_app.models.Book;

public class FavoriteBooksRepository {

    private final BookDao bookDao;
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private MutableLiveData<Integer> removeResult = new MutableLiveData<>();

    public FavoriteBooksRepository(Context context) {
        FavoriteBooksDatabase database = FavoriteBooksDatabase.getInstance(context);
        bookDao = database.bookDao();
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
