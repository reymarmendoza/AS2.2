package corp.kingsea.reymar.photofeed.libs.base;

import android.widget.ImageView;

/**
 * Created by reyma on 2/07/2016.
 */
public interface ImageLoader {
    void load(ImageView imageView, String URL);
    void setOnFinishedImageLoadingListener(Object listener);
}
