package com.miguelarc.book_store_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.miguelarc.book_store_app.models.Book;
import com.miguelarc.book_store_app.models.VolumeInfo;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BOOK_ITEM = 0;
    private static final int LOADING_ITEM = 1;
    private List<Book> bookList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookListViewHolder) {
            populateBookItem((BookListViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return bookList == null ? 0 : bookList.size();
    }

    public void addBookItems (List<Book> newBookItems) {
        if (newBookItems != null) {
            bookList.addAll(newBookItems);
            notifyDataSetChanged();
        }
    }

    private void populateBookItem (BookListViewHolder holder, int position) {
        Book book = bookList.get(position);
        switch (getItemViewType(position)) {
            case BOOK_ITEM:
                String imageUrl = null;
                if (book.getVolumeInfo().getImageLinks() != null) {
                    imageUrl = book.getVolumeInfo().getImageLinks().getSmallThumbnail();
                }
                Glide.with(holder.imageView.getContext())
                        .load(imageUrl)
                        .fallback(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.imageView);
                break;
            case LOADING_ITEM:
            default:
                // Do Nothing
                break;
        }
    }

    public static class BookListViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public BookListViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.book_thumbnail);
        }
    }
}
