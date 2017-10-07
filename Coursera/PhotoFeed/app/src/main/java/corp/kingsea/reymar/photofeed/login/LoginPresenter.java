package corp.kingsea.reymar.photofeed.login;

import corp.kingsea.reymar.photofeed.login.events.LoginEvent;

/**
 * Created by reyma on 18/06/2016.
 */
public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
    void onEventMainThread(LoginEvent event);
}
