# News-Breeze
This is a News App. I build it using MVVM pattern. In this project I have used news API, retrofit, room database, viewmodel & livedata, 
viewBinding, coroutines, fragments, bottom navigation bar, safe args, and much more...

In this project we can get breaking news fron news API and display it in Recycler view. Each news article have an image, source, title, short description of news
& the date-time of news, displayed in Recycler view.

News are sorted, latest news has displayed first according to their publication date. After clicking on each article it shows full article.

All articles in the list have a “floating action button” which saves the articles for reading later until the app is closed.

We can save, delete articles in room database. In this project, I have handle Internet connectivity.
If our app is not connected to Internet then it will gives message that "No Internet Connection".This internet connectivity handle, prevent app from crash.

Also, I have applied different theme to my application. I have used card view for recycler view items.

I use retrofit2 to fetching data fron API and use Recycler View with DiffUtil class to display news in list.

I use four fragments such as breaking news fragment, saved news fragment, search news fragment, article fragment. All fragments are managed using bottom navigation bar.

Breaking news have displayed in Recycler view of breaking news fragment. 
After clicking on each article which is present in breaking news fragment it will open within application web View to read full article & there,
by clicking on floating action button we can save our article in room database.

All Saved articles shows in Recycler view of saved news fragment. We can delete, undo our saved articles from room database. We can delete article by left or right swipe.
After clicking on article which are present in saved news fragment shows full article.

We can search news in all news, we do searching in search news fragment and searched articles shows in list(Recycler view) of search news fragment.
After clicking on each article which are present in search news fragment, open in full article.

In article fragment, I have used web view for read full article. Full article open in web view & there we can save it in room database for reading later until the app is closed.

All three, Breaking news fragment,saved news fragment and search news fragment are navigate to article fragment, so that, After clicking on each news article which
are present in breaking news fragment, saved news fragment & search news fragment will shows full article
