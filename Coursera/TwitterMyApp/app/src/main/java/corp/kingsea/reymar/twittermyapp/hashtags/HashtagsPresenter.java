package corp.kingsea.reymar.twittermyapp.hashtags;

import corp.kingsea.reymar.twittermyapp.hashtags.events.HashtagsEvent;

/**
 * Created by reyma on 2/07/2016.
 */
public interface HashtagsPresenter {
    void onResume();
    void onPause();
    void onDestroy();
    void getHashtagsTweets();
    void onEventMainThread(HashtagsEvent event);

}
