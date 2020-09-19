package com.miguelarc.book_store_app.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.miguelarc.book_store_app.R;
import com.miguelarc.book_store_app.database.FavoriteBooksDatabase;
import com.miguelarc.book_store_app.models.Book;
import com.miguelarc.book_store_app.viewmodels.BookDetailsViewModel;

public class BookDetailsFragment extends Fragment {

    private BookDetailsViewModel bookDetailsViewModel;
    private static final String BOOK_KEY = "BOOK_KEY";
    private ImageView bookCover;
    private ImageView favoriteIcon;
    private TextView bookTitleLabel;
    private TextView bookAuthorLabel;
    private TextView bookDescriptionLabel;
    private Button buyNowButton;
    private Book selectedBook;
    private FavoriteBooksDatabase favoriteBooksDatabase;
    private boolean isFavorite = false;

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BOOK_KEY, book);
        bookDetailsFragment.setArguments(bundle);
        return bookDetailsFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_details, container, false);
        bookCover = rootView.findViewById(R.id.book_cover);
        favoriteIcon = rootView.findViewById(R.id.favorite_icon);
        bookTitleLabel = rootView.findViewById(R.id.book_title);
        bookAuthorLabel = rootView.findViewById(R.id.book_author);
        bookDescriptionLabel = rootView.findViewById(R.id.book_description);
        buyNowButton = rootView.findViewById(R.id.buy_now_button);
        favoriteBooksDatabase = FavoriteBooksDatabase.getInstance(this.getContext());

        bookDetailsViewModel = new ViewModelProvider(this).get(BookDetailsViewModel.class);

        loadBookInformation();
        initFavoriteIcon();


        return rootView;
    }

    private void initFavoriteIcon() {

        favoriteBooksDatabase.bookDao().loadBookById(selectedBook.getId()).observe(getViewLifecycleOwner(), new Observer<Book>() {
            @Override
            public void onChanged(Book book) {
                if (book != null) {
                    isFavorite = true;
                    favoriteIcon.setImageResource(R.drawable.baseline_favorite_24);
                    return;
                }
                favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_24);
            }
        });

        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavoriteIconClicked();
            }
        });
    }

    private void onFavoriteIconClicked() {
        if (isFavorite) {
            bookDetailsViewModel.getRemoveResult().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer result) {
                    if (result == 1) {
                        isFavorite = false;
                        setFavoriteIcon();
                    } else {
                        Toast.makeText(getContext(), "Favorite remove: Database error!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            bookDetailsViewModel.remove(selectedBook);
        } else {
            bookDetailsViewModel.getInsertResult().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer result) {
                    if (result == 1) {
                        isFavorite = true;
                        setFavoriteIcon();
                    } else {
                        Toast.makeText(getContext(), "Favorite insert: Database error!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            bookDetailsViewModel.insert(selectedBook);

        }
    }

    void setFavoriteIcon() {
        if (isFavorite) {
            favoriteIcon.setImageResource(R.drawable.baseline_favorite_24);
            Toast.makeText(this.getContext(), "Marked as favorite!", Toast.LENGTH_SHORT).show();
        } else {
            favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_24);
            Toast.makeText(this.getContext(), "Removed from favorites!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadBookInformation() {
        if (this.getArguments() != null) {
            Bundle bundle = this.getArguments();
            selectedBook = bundle.getParcelable(BOOK_KEY);
            loadBookCover();
            bookTitleLabel.setText(selectedBook.getVolumeInfo().getTitle());
            bookAuthorLabel.setText(selectedBook.getVolumeInfo().getAuthors().toString());
            bookDescriptionLabel.setText(selectedBook.getVolumeInfo().getDescription());

            if (selectedBook.getSaleInfo() != null && selectedBook.getSaleInfo().getBuyLink() != null) {
                buyNowButton.setVisibility(View.VISIBLE);
                buyNowButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedBook.getSaleInfo().getBuyLink()));
                        startActivity(browserIntent);
                    }
                });
            }
        }
    }

    private void loadBookCover() {
        String imageUrl = null;
        if (selectedBook != null) {
            if (selectedBook.getVolumeInfo().getImageLinks() != null) {
                imageUrl = selectedBook.getVolumeInfo().getImageLinks().getThumbnail();
            }
            Glide.with(bookCover.getContext())
                    .load(imageUrl)
                    .fallback(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(bookCover);
        }
    }
}