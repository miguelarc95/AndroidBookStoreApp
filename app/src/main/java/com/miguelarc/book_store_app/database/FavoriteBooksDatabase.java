package com.miguelarc.book_store_app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.miguelarc.book_store_app.models.Book;

@Database(entities = {Book.class}, version = 1)
public abstract class FavoriteBooksDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "favorite_books_database";
    private static FavoriteBooksDatabase INSTANCE;

    public static FavoriteBooksDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteBooksDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        FavoriteBooksDatabase.class, FavoriteBooksDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }

    public abstract BookDao bookDao();
}
