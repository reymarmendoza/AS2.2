package corp.kingsea.reymar.photofeed.login;

import org.greenrobot.eventbus.Subscribe;

import corp.kingsea.reymar.photofeed.libs.base.EventBus;
import corp.kingsea.reymar.photofeed.login.events.LoginEvent;
import corp.kingsea.reymar.photofeed.login.ui.LoginView;

/**
 * Created by reyma on 19/06/2016.
 **/
public class LoginPresenterImpl implements LoginPresenter {

    private EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(EventBus eventBus, LoginView loginView, LoginInteractor loginInteractor) {
        this.eventBus = eventBus;
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void onCreate(){
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
        eventBus.unregister(this);
    }


    @Override
    public void validateLogin(String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignIn(email, password);
    }

    @Override
    public void registerNewUser(String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignUp(email, password);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event){
        switch(event.getEventType()){
            case LoginEvent.onSignInSuccess:
                onSignInSuccess(event.getCurrentUseremail());
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;
        }
    }

    private void onFailedToRecoverSession(){
        if(loginView != null){
            loginView.hideProgress();
            loginView.enabledInputs();
        }
    }

    private void onSignInSuccess(String email){
        if(loginView != null){
            loginView.setUserEmail(email);
            loginView.navigateToMainScreen();
        }
    }

    private void onSignUpSuccess(){
        if(loginView != null){
            loginView.newUserSuccess();
        }
    }

    private void onSignInError(String error){
        if(loginView != null){
            loginView.hideProgress();
            loginView.enabledInputs();
            loginView.loginError(error);
        }
    }

    private void onSignUpError(String error){
        if(loginView != null){
            loginView.hideProgress();
            loginView.enabledInputs();
            loginView.newUserError(error);
        }
    }
}
