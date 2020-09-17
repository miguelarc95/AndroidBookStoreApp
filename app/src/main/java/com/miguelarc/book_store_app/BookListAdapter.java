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

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BOOK_ITEM = 0;
    private static final int LOADING_ITEM = 1;
    private List<Book> bookList;
    private boolean isLoading = false;

    public BookListAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case BOOK_ITEM:
                viewHolder = new BookListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false));
                break;
            case LOADING_ITEM:
            default:
                viewHolder = new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false));
                break;
        }
        return viewHolder;
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

    @Override
    public int getItemViewType(int position) {
        return (position == bookList.size() - 1 && isLoading) ? LOADING_ITEM : BOOK_ITEM;
    }

    public void addBookItems (List<Book> newBookItems) {
        int initPosition = bookList.size();
        bookList.addAll(newBookItems);
        notifyItemRangeInserted(initPosition, bookList.size());
    }

    private void populateBookItem (BookListViewHolder holder, int position) {
        Book book = bookList.get(position);

        Glide.with(holder.imageView.getContext())
                .load(book.getVolumeInfo().getImageLinks().getSmallThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    public static class BookListViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public BookListViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.book_thumbnail);
        }
    }

    protected class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }


}
