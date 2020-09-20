# Book Store App

A simple Android app to showcase use of Android Jetpack Library usage.
This application makes use of Google Book's API in order to fetch and display Android-related books. It also adds the capability to store and filter by your favorite books.
It uses the following libraries:
* ViewModel - using lates MVVM pattern to abstract dependencies between view and business logic;
* LiveData - Observable data holder class used to update view related fields, based on changes happening in ViewModel;
* Room - Data persistency library used to persist favorite books;
* Retrofit - Library used to establish connection to Google's API, in order to get book information;
* Glide - Image processing library used to process images into ImageViews;
