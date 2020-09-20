package com.miguelarc.book_store_app.database;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.miguelarc.book_store_app.models.Book;
import com.miguelarc.book_store_app.testutils.LiveDataTestUtil;
import com.miguelarc.book_store_app.viewmodels.BookDetailsViewModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class BookDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private BookDetailsViewModel bookDetailsViewModel;
    private BookDao bookDao;
    private FavoriteBooksDatabase database;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        database = Room.inMemoryDatabaseBuilder(context, FavoriteBooksDatabase.class).build();
        bookDao = database.bookDao();
    }

    @After
    public void cleanUp() {
        database.close();
    }

    @Test
    public void testDatabaseIsEmpty() throws InterruptedException{
        List<Book> bookList = LiveDataTestUtil.getValue(bookDao.loadFavoriteBooks());
        Assert.assertTrue(bookList.isEmpty());
    }

    @Test
    public void testDatabaseInsert() throws InterruptedException {
        String bookId = "1234";
        Book book = new Book();
        book.setId(bookId);
        bookDao.insertFavoriteBook(book);
        Assert.assertEquals(1, LiveDataTestUtil.getValue(bookDao.loadFavoriteBooks()).size());
    }

    @Test
    public void testDatabaseRemove() throws InterruptedException {
        String bookId = "1234";
        Book book = new Book();
        book.setId(bookId);
        bookDao.insertFavoriteBook(book);
        bookDao.removeFavoriteBook(book);
        Assert.assertNull(LiveDataTestUtil.getValue(bookDao.loadBookById(bookId)));
    }
}
