package corp.kingsea.reymar.chat.contactlist;

/**
 * Created by reyma on 24/06/2016.
 */
public interface ContactListSessionInteractor {
    void signOff();
    String getCurrentUserEmail();
    void changeConnectionStatus(boolean online);
}
