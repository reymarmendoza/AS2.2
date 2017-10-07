package corp.kingsea.reymar.twittermyapp.images.events;

import java.util.List;

import corp.kingsea.reymar.twittermyapp.entities.Image;

/**
 * Created by reyma on 2/07/2016.
 */
public class ImagesEvent {

    private String error;
    private List<Image> images;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
