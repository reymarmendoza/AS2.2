package corp.kingsea.reymar.twittermyapp.images;

import corp.kingsea.reymar.twittermyapp.images.events.ImagesEvent;

/**
 * Created by reyma on 2/07/2016.
 */
public interface ImagesPresenter {
    void onResume();
    void onPause();
    void onDestroy();
    void getImageTweets();
    void onEventMainThread(ImagesEvent event);

}
