package corp.kingsea.reymar.photofeed.login;

import com.google.firebase.database.DatabaseError;

import corp.kingsea.reymar.photofeed.domain.FirebaseAPIoriginal;
import corp.kingsea.reymar.photofeed.domain.FirebaseActionListenerCallback;
import corp.kingsea.reymar.photofeed.libs.base.EventBus;
import corp.kingsea.reymar.photofeed.login.events.LoginEvent;

public class LoginRepositoryImpl implements LoginRepository {

    private EventBus eventBus;
    private FirebaseAPIoriginal firebaseAPI;

    public LoginRepositoryImpl(EventBus eventBus, FirebaseAPIoriginal firebaseAPI) {
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
    }

    @Override
    public void signUp(final String email, final String password) {//este se modifico
        firebaseAPI.signup(email, password, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(LoginEvent.onSignUpSuccess);
                signIn(email, password);
            }

            @Override
            public void onError(DatabaseError error) {
                postEvent(LoginEvent.onSignUpError, error.getMessage(), null);
            }
        });
    }

    @Override
    public void signIn(String email, String password) {
        if(email != null && password != null){
            firebaseAPI.login(email, password, new FirebaseActionListenerCallback() {
                @Override
                public void onSuccess() {
                    String email = firebaseAPI.getAuthEmail();
                    postEvent(LoginEvent.onSignInSuccess, email);
                }

                @Override
                public void onError(DatabaseError error) {
                    postEvent(LoginEvent.onSignInError, error.getMessage(), null);
                }
            });
        }else{
            firebaseAPI.checkForSession(new FirebaseActionListenerCallback() {
                @Override
                public void onSuccess() {
                    String email = firebaseAPI.getAuthEmail();
                    postEvent(LoginEvent.onSignInSuccess, email);
                }

                @Override
                public void onError(DatabaseError error) {
                    postEvent(LoginEvent.onFailedToRecoverSession);
                }
            });
        }

    }
    private void postEvent(int type, String errorMessage, String currentUserEmail){
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        loginEvent.setErrorMessage(errorMessage);
        loginEvent.setCurrentUseremail(currentUserEmail);
        eventBus.post(loginEvent);
    }

    private void postEvent(int type) {
        postEvent(type, null, null);
    }

    private void postEvent(int type, String currentUserEmail) {
        postEvent(type, null, currentUserEmail);
    }

}