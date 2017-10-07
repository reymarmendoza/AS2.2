package corp.kingsea.reymar.pruebafirebase;

/**
 *
 **/
public interface LoginView {

    void loginAcepted();
    void loginError(String error);

    void newUserAcepted();
    void newUserError(String error);

}
