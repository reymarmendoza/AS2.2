package corp.kingsea.reymar.twittermyapp.hashtags.ui;

import java.util.List;

import corp.kingsea.reymar.twittermyapp.entities.Hashtag;

/**
 * Created by reyma on 4/07/2016.
 */
public interface HashtagsView {
    void showImages();
    void hideImages();
    void showProgress();
    void hideProgress();

    void onError(String error);
    void setContent(List<Hashtag> items);
}
