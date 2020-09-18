package com.miguelarc.book_store_app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.miguelarc.book_store_app.models.Book;

import java.util.List;

@Dao
public interface BookDao {
    @Query("SELECT * FROM BOOK")
    LiveData<List<Book>> loadFavoriteBooks();

    @Insert
    void insertFavoriteBook(Book book);

    @Delete
    void removeFavoriteBook(Book book);

    @Query("SELECT * FROM BOOK WHERE id = :id")
    LiveData<Book> loadBookById(String id);
}
