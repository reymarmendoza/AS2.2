package corp.kingsea.reymar.photofeed.main;

import android.location.Location;

/**
 * Created by reyma on 9/07/2016.
 */
public interface UploadInteractor {

    void execute(Location location, String path);
}
