package corp.kingsea.reymar.photofeed.main;

import android.location.Location;

/**
 * va a manejar los datos
 */
public interface MainRepository {

    void logout();
    void uploadPhoto(Location location, String path);

}
