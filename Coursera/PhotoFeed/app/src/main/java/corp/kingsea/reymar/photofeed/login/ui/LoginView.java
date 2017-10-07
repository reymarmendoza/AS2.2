package corp.kingsea.reymar.photofeed.login.ui;

/**
 * Created by reyma on 18/06/2016.
 **/
public interface LoginView {
    void enabledInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    void handleSignUp();
    void handleSignIn();

    void navigateToMainScreen();
    void loginError(String error);

    void newUserSuccess();
    void newUserError(String error);

    void setUserEmail(String email);
}
