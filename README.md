# GitHub Users Search

Android application to search users via GitHub API. Project demonstrates `how-to` use Kotlin, Android Architecture Components, Dagger hilt and Coroutine to perform remote API calls and cache data to the local DB.

#### Structure

Application contains two screens:

1. Home screen - performs list user via GitHub API, saves result to the local DB and displays the result. The list of users can be filtered using the search feature
3. Details screen - shows user name, bio and repositories list.


#### Used language and libraries
 * [Kotlin](https://kotlinlang.org/docs/tutorials/kotlin-android.html) - primary project language.
 * [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) - the core of [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) pattern.
 * [Coroutine]([https://github.com/ReactiveX/RxJava](https://developer.android.com/kotlin/coroutines?hl=id)) - simple way to manage data chains.
 * [Dagger Hilt]([https://google.github.io/dagger/](https://developer.android.com/training/dependency-injection/hilt-android?hl=id)) - dependency injection framework.
 * [Retrofit](http://square.github.io/retrofit/) - to perform API call.
 * [Room]([http://greenrobot.org/greendao/](https://developer.android.com/jetpack/androidx/releases/room?hl=id)) - cache data in local DB Room.
