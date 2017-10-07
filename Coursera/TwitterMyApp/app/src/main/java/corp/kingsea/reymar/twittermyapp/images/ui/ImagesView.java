package corp.kingsea.reymar.twittermyapp.images.ui;

import java.util.List;

import corp.kingsea.reymar.twittermyapp.entities.Image;

/**
 * Created by reyma on 2/07/2016.
 */
public interface ImagesView {
    void showImages();
    void hideImages();
    void showProgress();
    void hideProgress();

    void onError(String error);
    void setContent(List<Image>items);
}
