package corp.kingsea.reymar.photofeed.libs.base;

import java.io.File;

/**
 * Created by reyma on 8/07/2016.
 */
public interface ImageStorage {

    String getImageURL(String id);
    void upload(File file, String id, ImageStorageFinishedListener listener);
}
