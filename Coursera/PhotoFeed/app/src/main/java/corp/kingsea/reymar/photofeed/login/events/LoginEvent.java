package corp.kingsea.reymar.photofeed.login.events;

/**
 * Created by reyma on 19/06/2016.
 */
public class LoginEvent {
    public final static int onSignInError = 0;
    public final static int onSignUpError = 1;
    public final static int onSignInSuccess = 2;
    public final static int onSignUpSuccess = 3;
    public final static int onFailedToRecoverSession = 4;

    private int eventType;
    private String errorMessage;
    private String currentUseremail;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getCurrentUseremail() {
        return currentUseremail;
    }

    public void setCurrentUseremail(String currentUseremail) {
        this.currentUseremail = currentUseremail;
    }
}
