package com.miguelarc.book_store_app.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.miguelarc.book_store_app.database.FavoriteBooksRepository;
import com.miguelarc.book_store_app.models.Book;

import java.util.List;

public class BookDetailsViewModel extends AndroidViewModel {
    private FavoriteBooksRepository favoriteBooksRepository;
    private LiveData<Integer> insertResult;
    private LiveData<Integer> removeResult;

    public BookDetailsViewModel(@NonNull Application application) {
        super(application);
        favoriteBooksRepository = FavoriteBooksRepository.getInstance(application);
        insertResult = favoriteBooksRepository.getInsertResult();
        removeResult = favoriteBooksRepository.getRemoveResult();
    }
    public void insert(Book book) {
        favoriteBooksRepository.insert(book);
    }

    public LiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public void remove(Book book) {
        favoriteBooksRepository.remove(book);
    }

    public LiveData<Integer> getRemoveResult() {
        return removeResult;
    }

    public String getFormattedAuthors(List<String> authorsList) {
        if (authorsList!= null) {
            return authorsList.toString().replace("[","").replace("]","");
        }
        return ";";
    }
}