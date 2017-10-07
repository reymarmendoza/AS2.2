package corp.kingsea.reymar.chat.contactlist;

import corp.kingsea.reymar.chat.contactlist.events.ContactListEvent;

/**
 * Created by reyma on 24/06/2016.
 */
public interface ContactListPresenter {
    void onPause();
    void onCreate();
    void onDestroy();
    void onResume();

    void signOff();
    String getCurrentUserEmail();
    void removeContacts(String email);
    void onEventMainThread(ContactListEvent event);
}
