package com.miguelarc.book_store_app.views;

import androidx.fragment.app.Fragment;

public interface FragmentNavigationHandler {
    void setFragment(Fragment fragment);
    void pushFragment(Fragment fragment);
}
