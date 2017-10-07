package corp.kingsea.reymar.chat.contactlist;

/**
 * Created by reyma on 24/06/2016.
 */
public interface ContactListRepository {
    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void subscribeToContactListEvents();
    void unsubscribeToContactListEvents();
    void destroyListener();
    void changeConnectionStatus(boolean online);
}
