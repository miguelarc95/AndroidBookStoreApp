# Android Book Store App

A simple Android app to showcase the use of some Android Jetpack Library features.
This application makes use of Google Books API in order to fetch and display Android-related books. It also adds the capability to store and filter by your favorite books.
<p align="center">
 <img src="app/screenshots/screenshot_1.PNG?raw=true" width="400">
 <img src="app/screenshots/screenshot_2.PNG?raw=true" width="400">
</p>

It uses the following libraries:
* ViewModel - using lates MVVM pattern to abstract dependencies between view and business logic;
  * https://developer.android.com/reference/android/arch/lifecycle/ViewModel
* LiveData - Observable data holder class used to update view related fields, based on changes happening in ViewModel;
  * https://developer.android.com/topic/libraries/architecture/livedata
* Room - Data persistency library used to persist favorite books;
  * https://developer.android.com/jetpack/androidx/releases/room
* Retrofit - Library used to establish connection to Google's API, in order to get book information;
  * https://square.github.io/retrofit/
* Glide - Image processing library used to process images into ImageViews;
  * https://bumptech.github.io/glide/
