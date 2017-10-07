package corp.kingsea.reymar.chat.login;

import corp.kingsea.reymar.chat.login.events.LoginEvent;

/**
 * Created by reyma on 18/06/2016.
 */
public interface LoginPresenter {

    void onCreate();
    void onDestroy();
    void checkForAuthenticatedUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
    void onEventMainThread(LoginEvent event);

}
