package com.miguelarc.book_store_app.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miguelarc.book_store_app.FragmentNavigationHandler;
import com.miguelarc.book_store_app.R;
import com.miguelarc.book_store_app.RecyclerViewClickListener;
import com.miguelarc.book_store_app.adapters.BookListAdapter;
import com.miguelarc.book_store_app.database.FavoriteBooksDatabase;
import com.miguelarc.book_store_app.models.Book;
import com.miguelarc.book_store_app.network.responsemodels.BookListResponse;
import com.miguelarc.book_store_app.viewmodels.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView bookListRecyclerView;
    private RecyclerViewClickListener listener = new RecyclerViewClickListener() {
        @Override
        public void onItemClicked(Book book) {
            onBookClicked(book);
        }
    };
    private BookListAdapter bookListAdapter;
    private static final int PAGE_SIZE = 20;
    private ProgressBar progressBar;
    private boolean hasReachedEnd = false;
    private boolean isFilteringByFavorites = false;
    private FavoriteBooksDatabase favoriteBooksDatabase;
    private CompoundButton.OnCheckedChangeListener favoriteCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            onFavoriteCheckClicked(isChecked);
        }
    };
    private Observer<List<Book>> loadFavoriteBookListObserver = new Observer<List<Book>>() {
        @Override
        public void onChanged(List<Book> favoriteBookList) {
            if (isFilteringByFavorites) {
                clearBookList();
                bookListAdapter.addBookItems(favoriteBookList);
            } else {
                clearBookList();
                bookListAdapter.addBookItems(homeViewModel.getBookList());
            }
        }
    };
    private int scrollPosition = 0;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = rootView.findViewById(R.id.main_progress);
        CheckBox favoriteCheckBox = rootView.findViewById(R.id.favorites_check_box);
        favoriteCheckBox.setOnCheckedChangeListener(favoriteCheckListener);
        favoriteBooksDatabase = FavoriteBooksDatabase.getInstance(this.getContext());

        initRecyclerView(rootView);
        initScrollListener();

        // Loading initial batch of books
        loadBooks();

        return rootView;
    }

    private void initRecyclerView(View rootView) {
        bookListRecyclerView = rootView.findViewById(R.id.book_list_recycler_view);
        bookListRecyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        bookListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bookListAdapter = new BookListAdapter(new ArrayList<Book>(), listener);
        bookListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                bookListRecyclerView.scrollToPosition(scrollPosition);
            }
        });
        bookListRecyclerView.setAdapter(bookListAdapter);
    }

    private void initScrollListener() {
        bookListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == bookListAdapter.getItemCount() - 1 && !hasReachedEnd && !isFilteringByFavorites) {
                    progressBar.setVisibility(View.VISIBLE);
                    loadBooks();
                }
            }
        });
    }

    private void loadBooks() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.loadBookList().observe(getViewLifecycleOwner(), new Observer<BookListResponse>() {
            @Override
            public void onChanged(BookListResponse bookListResponse) {
                if (bookListResponse.getItems().size() < PAGE_SIZE) {
                    // Reached end of list. App shouldn't be requesting more items.
                    hasReachedEnd = true;
                }
                bookListAdapter.addBookItems(bookListResponse.getItems());
                scrollPosition = bookListAdapter.getItemCount();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void onBookClicked(Book clickedBook) {
        BookDetailsFragment bookDetailsFragment = BookDetailsFragment.newInstance(clickedBook);
        if (this.getActivity() != null) {
            ((FragmentNavigationHandler)this.getActivity()).pushFragment(bookDetailsFragment);
        }
    }

    private void onFavoriteCheckClicked(final boolean isChecked) {
        isFilteringByFavorites = isChecked;
        favoriteBooksDatabase.bookDao().loadFavoriteBooks().observe(getViewLifecycleOwner(), loadFavoriteBookListObserver);
    }

    private void clearBookList() {
        bookListAdapter.clearBookItems();
    }

    @Override
    public void onPause() {
        super.onPause();
        favoriteBooksDatabase.bookDao().loadFavoriteBooks().removeObserver(loadFavoriteBookListObserver);
    }
}