package corp.kingsea.reymar.chat.contactlist;

/**
 * Created by reyma on 24/06/2016.
 */
public interface ContactListInteractor {
    void subscribe();
    void unsubscribe();
    void destroyListener();
    void removeContact(String email);
}
