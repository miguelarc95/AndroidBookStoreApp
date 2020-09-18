package com.miguelarc.book_store_app.views;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miguelarc.book_store_app.database.FavoriteBooksDatabase;
import com.miguelarc.book_store_app.FragmentNavigationHandler;
import com.miguelarc.book_store_app.RecyclerViewClickListener;
import com.miguelarc.book_store_app.adapters.BookListAdapter;
import com.miguelarc.book_store_app.R;
import com.miguelarc.book_store_app.models.Book;
import com.miguelarc.book_store_app.network.responsemodels.BookListResponse;
import com.miguelarc.book_store_app.viewmodels.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView bookListRecyclerView;
    private BookListAdapter bookListAdapter;
    private static final int PAGE_SIZE = 20;
    private ProgressBar progressBar;
    private boolean hasReachedEnd = false;
    private List<Book> bookList = new ArrayList<>();
    private FavoriteBooksDatabase favoriteBooksDatabase;
    private RecyclerViewClickListener listener = new RecyclerViewClickListener() {
        @Override
        public void onItemClicked(int position) {
            onBookClicked(position);
        }
    };

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = rootView.findViewById(R.id.main_progress);

        favoriteBooksDatabase = FavoriteBooksDatabase.getInstance(this.getContext());

        initRecyclerView(rootView);
        initScrollListener();

        // Loading initial batch of books
        loadBooks(0);
        return rootView;
    }

    private void initRecyclerView(View rootView) {
        bookListRecyclerView = rootView.findViewById(R.id.book_list_recycler_view);
        bookListAdapter = new BookListAdapter(listener);
        bookListRecyclerView.setAdapter(bookListAdapter);
        bookListRecyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        bookListRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initScrollListener() {
        bookListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == bookListAdapter.getItemCount() - 1) {
                    if (!hasReachedEnd) {
                        progressBar.setVisibility(View.VISIBLE);
                        // Mocking network delay for API call
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadBooks(bookListAdapter.getItemCount());
                            }
                        }, 1000);
                    }
                }
            }
        });
    }

    private void loadBooks(int startIndex) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getBookList(PAGE_SIZE, startIndex).observe(getViewLifecycleOwner(), new Observer<BookListResponse>() {
            @Override
            public void onChanged(BookListResponse bookListResponse) {
                if (bookListResponse.getItems().size() < PAGE_SIZE) {
                    // Reached end of list. App shouldn't be requesting more items.
                    hasReachedEnd = true;
                }
                bookList.addAll(bookListResponse.getItems());
                bookListAdapter.addBookItems(bookListResponse.getItems());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void onBookClicked(int position) {
        Book clickedBook = bookList.get(position);
        BookDetailsFragment bookDetailsFragment = BookDetailsFragment.newInstance(clickedBook);
        if (this.getActivity() != null) {
            ((FragmentNavigationHandler)this.getActivity()).pushFragment(bookDetailsFragment);
        }
    }
}