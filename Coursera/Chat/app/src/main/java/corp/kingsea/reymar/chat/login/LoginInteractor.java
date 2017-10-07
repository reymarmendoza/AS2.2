package corp.kingsea.reymar.chat.login;

/**
 * Created by reyma on 18/06/2016.
 */
public interface LoginInteractor {

    void checkSession();
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);

}
