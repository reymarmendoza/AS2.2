package corp.kingsea.reymar.photofeed.main;

import android.location.Location;

import corp.kingsea.reymar.photofeed.main.events.MainEvent;

/**
 * Con estos metodos se va a publicar la foto
 */
public interface MainPresenter {
    //manejar la subscripcion a eventbus y crear o destruir la vista
    void onCreate();
    void onDestroy();

    void logout();
    void uploadPhoto(Location location,String path);//ubicacion y ruta de donde esta la foto
    void onEventMainThread(MainEvent event);
}
