package corp.kingsea.reymar.chat.addcontact.events;

/**
 * Created by reyma on 25/06/2016.
 */
public class AddContactEvent {
    boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
