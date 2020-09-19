package com.miguelarc.book_store_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.miguelarc.book_store_app.views.HomeFragment;

public class MainActivity extends AppCompatActivity implements FragmentNavigationHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFragment(HomeFragment.newInstance());
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, fragment.getTag());
        transaction.commit();
    }

    public void pushFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment, fragment.getTag());
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }
}