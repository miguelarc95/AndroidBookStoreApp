package com.miguelarc.book_store_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.miguelarc.book_store_app.R;
import com.miguelarc.book_store_app.RecyclerViewClickListener;
import com.miguelarc.book_store_app.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Book> bookList = new ArrayList<>();
    private RecyclerViewClickListener listener;

    public BookListAdapter(RecyclerViewClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false), listener);
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
        String imageUrl = null;
        if (book.getVolumeInfo().getImageLinks() != null) {
            imageUrl = book.getVolumeInfo().getImageLinks().getSmallThumbnail();
        }
        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .fallback(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    public static class BookListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private RecyclerViewClickListener listener;
        public BookListViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            this.listener = listener;
            view.setOnClickListener(this);
            imageView = view.findViewById(R.id.book_thumbnail);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClicked(getAdapterPosition());
        }
    }
}
