package corp.kingsea.reymar.photofeed.libs.base;

/**
 * Created by reyma on 8/07/2016.
 */
public interface ImageStorageFinishedListener {

    void onSuccess();
    void onError(String error);
}
