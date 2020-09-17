package com.miguelarc.book_store_app.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miguelarc.book_store_app.BookListAdapter;
import com.miguelarc.book_store_app.R;
import com.miguelarc.book_store_app.models.Book;
import com.miguelarc.book_store_app.network.responsemodels.BookListResponse;
import com.miguelarc.book_store_app.viewmodels.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView bookListRecyclerView;
    private List<Book> bookList = new ArrayList<>();
    private static final int PAGE_SIZE = 20;

    private boolean isLoading = false;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_list, container, false);
        bookListRecyclerView = rootView.findViewById(R.id.book_list_recycler_view);
        bookListRecyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        initScrollListener();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getBookList(PAGE_SIZE, 0).observe(getViewLifecycleOwner(), new Observer<BookListResponse>() {
            @Override
            public void onChanged(BookListResponse bookListResponse) {
                bookList.addAll(bookListResponse.getItems());
                bookListRecyclerView.setAdapter(new BookListAdapter(bookList));
            }
        });

        return rootView;
    }

    private void initScrollListener() {
        bookListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == bookList.size() - 1) {
                        loadMoreBooks();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMoreBooks() {
        homeViewModel.getBookList(PAGE_SIZE, bookList.size()).observe(getViewLifecycleOwner(), new Observer<BookListResponse>() {
            @Override
            public void onChanged(BookListResponse bookListResponse) {
                bookList.addAll(bookListResponse.getItems());
                bookListRecyclerView.setAdapter(new BookListAdapter(bookList));
                isLoading = false;
            }
        });
    }

}